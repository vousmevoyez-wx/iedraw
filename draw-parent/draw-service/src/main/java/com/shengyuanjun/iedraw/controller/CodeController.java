package com.shengyuanjun.iedraw.controller;


import com.shengyuanjun.iedraw.domain.*;
import com.shengyuanjun.iedraw.service.*;
import com.shengyuanjun.iedraw.util.postutil.URLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @program: participate
 * @description: 二维码Controller类
 * @author: gq544
 * @create: 2019-08-07 15:06
 */

@RestController
@PropertySource("classpath:constants/constant.properties")
@RequestMapping("/cd")
public class CodeController {

    private static final Logger logger = LoggerFactory.getLogger(CodeController.class);


    @Resource
    private TokenService tokenServiceImpl;

    @Resource
    private IPrizeRecordService prizeRecordServiceImpl;

    @Resource
    private ICustomizeService customizeServiceImpl;

    @Resource
    private IPrizeService prizeServiceImpl;

    @Value("${url.activecqcode}")
    private String activecodeurl;

    @Value("${url.customizeeurl}")
    private String customizepictureurl;

    @Value("${url.project}")
    private String projurl;

    @Value("${goodscode}")
    private String goodscodestatic;

    @Autowired
    public ITokenuserService tokenuserService;


    /**
     *@Description: 私人定制  前端自动打印以及后台重置
     *@Param: [request]
     *@return: net.sf.json.JSONObject
     *@Author: gq544
     *@date: 2019/8/15 0:17
     */
    @RequestMapping("/myprz")
    @Transactional
    public Map MyShowGoods(HttpServletRequest request,String token) {
        Map map = new HashMap();
        String customizetime = request.getParameter("customizetime");
        String printchart = request.getParameter("printchart");
        String printstatus = request.getParameter("printstatus");
        String goodscode = request.getParameter("goodscode");
        if(goodscode==null){
            map.put("code",209);
            map.put("msg","未输入提货码");
            map.put("success","fail");
            return map;
        }if(printstatus==null || "".equals(printstatus)){
            map.put("code",207);
            map.put("msg","无状态值的参数保存失败");
            map.put("success","fail");
            return map;
        }
        //判断提货码权限
        if(!goodscode.equals(goodscodestatic)){
            if(!"3".equals(printstatus)){
                System.out.println("token = " + token);
                if(token!=null){
                    Tokenuser tokenu = tokenuserService.selectByToken(token);
                    System.out.println("tokenu  = " + tokenu );
                    if (tokenu==null){
                        //如果查询token为空，返回无效的token
                        map.put("code",201);
                        map.put("msg","token验证失败");
                        map.put("success",false);
                        return map;
                    }else{
                        //如果查询token有值，判断token有效期
                        if (!(tokenu.getId()==1)){
                            if((new Date().getTime() - tokenu.getTokenDate().getTime()) > 60*60*1000){
                                map.put("code",201);
                                map.put("msg","token已失效");
                                map.put("success",false);
                                return map;
                            }
                            else{
                                //如果token验证成功，刷新token时间
                                tokenu.setTokenDate(new Date());
                                tokenuserService.updateById(tokenu);
                            }
                        }
                    }
                }else{ //如果查询token为空，返回无效的token
                    map.put("code",203);
                    map.put("msg","token参数无效或为空，验证失败");
                    map.put("success",false);
                    return map;
                }
            }


            Customize c = new Customize();


            boolean isok = true;
            for(int i = 0 ; i < goodscode.length();i++){
                if(!((goodscode.charAt(i) >= 65 &&  goodscode.charAt(i) <= 90) || (goodscode.charAt(i) >= 97 &&  goodscode.charAt(i) <= 122)
                        ||  (goodscode.charAt(i) >= 48 &&  goodscode.charAt(i) <= 57))){
                    isok = false;
                    break;
                }
            }if(!isok){
                map.put("code",222);
                map.put("msg","格式无效的提货码");
                map.put("success",false);
                return map;
            }

            c.setGoodscode(goodscode);
            c = customizeServiceImpl.selectByGoodsCode(c);
            System.out.println("c = " + c);
            if(c==null){
                PrizeRecord cus = new PrizeRecord();
                cus.setGoodscode(goodscode);
                //通过goodscode查询出对应的领奖记录
                System.out.println("cus = " + cus);
                PrizeRecord pzs = prizeRecordServiceImpl.selectByGoodsCode(cus);
                System.out.println("pzs = " + pzs);
                if(pzs==null){
                    map.put("code",201);
                    map.put("msg","无兑换码记录");
                    map.put("success",false);
                }else{
                    Customize c1 = new Customize();

                    Long time = Long.parseLong(customizetime);

                    c1.setCustomizetime(time);
                    c1.setGoodscode(goodscode);

                    System.out.println("printchart = " + printchart);
                    c1.setPrintchart(printchart);

                    pzs.setGoodscode(goodscode);

                    //领取成功过后，修改取货码扫码状态，记录已被领取的状态
                    if("1".equals(printstatus)||"2".equals(printstatus)){
                        //打印的时候修改中奖记录状态
                        pzs.setStatus(Integer.parseInt(printstatus));
                        prizeRecordServiceImpl.updateByGoodsCode(pzs);
                    }
                    //查询修改对应奖品数量并修改保存
                    Prize pr = prizeServiceImpl.selectPrizeById(pzs.getPrizeid());
                    pr.setStock(pr.getStock() - 1);
                    prizeServiceImpl.updateStockByPrizeWinner(pr);
                    c1.setPrintstatus(Integer.parseInt(printstatus));

                    if(c1.getPrintstatus()==0){
                        map.put("code",200);
                        map.put("msg","添加成功");
                        customizeServiceImpl.addCustomize(c1);
                        customizeServiceImpl.updateCustomize(c1);


                        map.put("success",true);

                    }else  if(c1.getPrintstatus()==1){
                        map.put("code",200);
                        map.put("msg","已成功打印");
                        map.put("success",true);
                        customizeServiceImpl.addCustomize(c1);


                    }else  if(c1.getPrintstatus()==2){
                        map.put("code",201);
                        map.put("msg","已过期的私人定制");
                        map.put("success",false);
                    }else  if(c1.getPrintstatus()==3){
                        map.put("code",205);
                        map.put("msg","打印失败");
                        map.put("success",false);
                    }
                }
            }else{
                if(Integer.parseInt(printstatus)!=2){

                    PrizeRecord pr = new PrizeRecord();
                    pr.setStatus(0);
                    pr.setGoodscode(goodscode);

                    if(Integer.parseInt(printstatus) == 3){
                        c.setPrintstatus(0);
                        //重置私人订制表
                        if(printchart!=null && printchart != ""){
                            c.setPrintchart(printchart);
                        }
                        customizeServiceImpl.updateCustomize(c);
                        //重置中奖记录表
                        prizeRecordServiceImpl.updateByGoodsCode(pr);
                        map.put("code",200);
                        map.put("msg","重置成功");
                        map.put("success",true);
                    }else if(c.getPrintstatus() == 0){
                        if("1".equals(printstatus)){
                            map.put("msg","私人订制打印图片成功");
                            c.setPrintstatus(1);
                            customizeServiceImpl.updateCustomize(c);
                            //打印后修改中奖记录表状态
                            pr.setStatus(1);
                            prizeRecordServiceImpl.updateByGoodsCode(pr);
                        }else if(printchart!=null && printchart != "") {

                                c.setPrintchart(printchart);
                                customizeServiceImpl.updateCustomize(c);
                                map.put("msg","重置私人订制打印图片成功");

                        }else{
                            map.put("msg","已存在的私人订制，可以直接打印");
                            map.put("success",false);
                        }
                        map.put("code",200);

                    }else if(c.getPrintstatus() == 1){
                        map.put("code",201);
                        map.put("msg","已打印的私人订制，如需重新打印，请重置提取码");
                        map.put("success",false);
                    }
                }else{
                    map.put("code",202);
                    map.put("msg","无效私人订制");
                    map.put("success",false);
                }
            }
            return map;
        }else{
            System.out.println("customizetime = " + customizetime);
            //创建一个私人订制对象customize
            Customize customize = new Customize();
            if(customizetime!=null){
                customize.setCustomizetime(Long.parseLong(customizetime));
            }else if(customizetime==null || "".equals(customizetime)){
                //创建时间为空则失败
                map.put("code",211);
                map.put("msg","时间戳获取失败");
                map.put("success",false);
                return map;
            }
            //customize保存状态信息(状态码保持未打印状态)
            customize.setPrintstatus(0);
            //customize保存提货码信息
            customize.setGoodscode(goodscodestatic);

            //创建时间不为空

            //通过是否传入图片判断，如果没有传入图片，则视为校验存在
            if(printchart==null){
                Customize iz = customizeServiceImpl.selectByGoodsCode(customize);
                //如果有保存的图片信息，可以通过0判断打印和1通过打印
                if(iz!=null && iz.getPrintchart()!=""  && iz.getPrintchart()!=null){
                    if("1".equals(printstatus)){
                        map.put("code",203);
                        map.put("msg","打印私人定制");
                        map.put("success",true);
                        return map;
                    }else if("0".equals(printstatus)){
                        map.put("code",200);
                        map.put("msg","该私人订制可以打印");
                        map.put("success",true);
                        return map;
                    }
                }else{
                    //如果没有保存的图片信息，不能打印，一律视为校验私人订制码
                        map.put("code",203);
                        map.put("msg","无打印图片的私人定制，无法正常打印");
                        map.put("success",true);
                        return map;
                }
            }else{
                Customize ze = customizeServiceImpl.selectByGoodsCode(customize);
                //通过是否传入图片判断，如果有传入图片，则视为重置信息及打印
                customize.setPrintchart(printchart);
                if("3".equals(printstatus)){
                    //状态信息为3，通过提货码查询私人订制对象
                    if(ze!=null){
                        //状态信息为3，通过提货码查询私人订制对象
                        ze.setPrintstatus(0);
                        ze.setPrintchart(printchart);
                        customizeServiceImpl.updateByGoodsCode(ze);
                        map.put("code",200);
                        map.put("msg","重置操作成功，现在已经重置私人订制信息");
                        map.put("success",true);
                        return map;
                    }else{
                        map.put("code",203);
                        map.put("msg","不存在的私人订制记录，无法进行重置操作");
                        map.put("success",false);
                        return map;
                    }
                }

                if(ze==null){
                    customizeServiceImpl.addCustomize(customize);
                    map.put("code",200);
                    map.put("msg","添加私人订制信息成功");
                    map.put("success",true);
                    return map;
                }else{

                    if(ze.getPrintstatus() == 0){
                        customizeServiceImpl.updateByGoodsCode(customize);
                        if("1".equals(printstatus)){
                            map.put("code",200);
                            map.put("msg","打印订制图片成功");
                            map.put("success",true);
                            return map;
                        }else if("0".equals(printstatus)){
                            map.put("code",200);
                            map.put("msg","更新订制图片成功");
                            map.put("success",true);
                            return map;
                        }else{
                            map.put("code",200);
                            map.put("msg","状态码无效");
                            map.put("success",false);
                            return map;
                        }
                    }else if(ze.getPrintstatus() == 1){
                        map.put("code",200);
                        map.put("msg","goodscode私人订制已成功打印");
                        map.put("success",true);
                        return map;
                    }
                }
            }
        }
        return map;
    }


    /**
     *@Description: 私人定制  手动打印
     *@Param: [request]
     *@return: net.sf.json.JSONObject
     *@Author: gq544
     *@date: 2019/8/15 0:17
     */
    @RequestMapping("/myprzs")
    public Map MyShowGoods(String printstatus,String goodscode) {
        Map map = new HashMap();

        if(printstatus != null  && !"".equals(printstatus)  && goodscode != null  && !"".equals(goodscode)){
            Customize c1 = new Customize();
            //c1.setPrintstatus(Integer.parseInt(printstatus));
            c1.setGoodscode(goodscode);
            c1 = customizeServiceImpl.selectByGoodsCode(c1);

            if((!("1".equals(printstatus)) &&  !("0".equals(printstatus)) && !("2".equals(printstatus)))){
                map.put("code",201);
                map.put("msg","无效的状态码");
                map.put("success",false);
                return map;
            }
            if(c1 == null){
                map.put("code",201);
                map.put("msg","无效的提货码");
                map.put("success",false);
                return map;
            }else{
                if(c1.getPrintstatus()==0){
                    //特权提货码不做限制
                    if(!goodscode.equals(goodscodestatic)){
                    c1.setPrintstatus(Integer.parseInt(printstatus));
                    customizeServiceImpl.updateByGoodsCode(c1);
                    //重置中奖记录表
                        PrizeRecord pr = new PrizeRecord();
                        pr.setStatus(1);
                        pr.setGoodscode(goodscode);
                        prizeRecordServiceImpl.updateByGoodsCode(pr);
                    }

                    map.put("code",200);
                    map.put("msg","打印成功");
                    map.put("success",true);
                }else if(c1.getPrintstatus()==1){
                    map.put("code",201);
                    map.put("msg","已打印的记录，你可以重置领取码重新打印");
                    map.put("success",false);
                }else{map.put("code",202);
                    map.put("msg","私人订制提取码无效或者已过期");
                    map.put("success",false);
                }
                return map;
            }
        }
        map.put("code",201);
        map.put("msg","打印状态无效或者无效的提货码");
        return map;
    }

    /**
     * 0000002
     *
     * @Description: 取货码领取奖品(提货码)
     * @Param: [request]
     * @return: java.lang.String
     * @Author: gq544
     * @date: 2019/8/10 0:08
     */
    @RequestMapping("/gtnum")
    @Transactional  //添加事务管理
    public Map backNumValue(HttpServletRequest request,String token) {
        Map map = new HashMap();
        if(token!=null){
            Tokenuser tokenu = tokenuserService.selectByToken(token);
            System.out.println("tokenu  = " + tokenu );
            if (tokenu==null){
                //如果查询token为空，返回无效的token
                map.put("code",201);
                map.put("msg","token验证失败");
                map.put("success",false);
                return map;
            }else{
                //如果查询token有值，判断token有效期
                if (!(tokenu.getId()==1)){
                    if((new Date().getTime() - tokenu.getTokenDate().getTime()) > 60*60*1000){
                        map.put("code",201);
                        map.put("msg","token已失效");
                        map.put("success",false);
                        return map;
                    }
                    else{
                        //如果token验证成功，刷新token时间
                        tokenu.setTokenDate(new Date());
                        tokenuserService.updateById(tokenu);
                    }
                }
            }
        }else{ //如果查询token为空，返回无效的token
            map.put("code",203);
            map.put("msg","token参数无效或为空，验证失败");
            map.put("success",false);
            return map;
        }

        String goodscode = request.getParameter("goodscode");

        if("null".equals(goodscode)){
            map.put("code",222);
            map.put("msg","提货码参数不能为空");
            map.put("success",false);
            return map;
        }else{
            boolean isok = true;
            for(int i = 0 ; i < goodscode.length();i++){
                if(!((goodscode.charAt(i) >= 65 &&  goodscode.charAt(i) <= 90) || (goodscode.charAt(i) >= 97 &&  goodscode.charAt(i) <= 122)
                     ||  (goodscode.charAt(i) >= 48 &&  goodscode.charAt(i) <= 57))){
                    isok = false;
                    break;
                }
            }if(!isok){
                map.put("code",222);
                map.put("msg","格式无效的提货码");
                map.put("success",false);
                return map;
            }
        }

        System.out.println("goodscode = " + goodscode);
        PrizeRecord cus = new PrizeRecord();
        cus.setGoodscode(goodscode);
        //通过goodscode查询出对应的领奖记录
        PrizeRecord pz = prizeRecordServiceImpl.selectByGoodsCode(cus);
        System.out.println("pz = " + pz);
        //根据中奖纪录的prizeid 查找一对应奖品信息并判断是否过期
        Prize pri = prizeServiceImpl.selectPrizeById(pz.getPrizeid());

        //状态：200成功、201过期、202、已使用
        if (pz != null) {
            if (pz.getStatus() == 1) {
                map.put("code", 201);
                map.put("msg", "已使用的提货码");
                map.put("success",false);
           // } else if (!Timeutil.getDayTime(new Date().getTime() + "").equals(Timeutil.getDayTime(pz.getCreatetime() + ""))) {
            } else if ((new Date().getTime()-Long.parseLong(pri.getEndvalidityperiod()+"000")) > 0) {
                map.put("code", 202);
                map.put("msg", "已过期的奖品");
                map.put("success",false);
            } else if (pz.getStatus() == 0) {
                map.put("code", 200);
                map.put("msg", "goodscode校验成功");
                map.put("success", true);
                System.out.println("map = " + map);
                return map;
            }
        } else {
            map.put("code", 203);
            map.put("msg", "兑换失败，没有找到对应的提货码记录");
            map.put("success", false);
        }
        return map;
    }

    /**
     * 0000002
     *
     * @Description: 根据扫码枪回调参数执行相应处理 需要参数为 id(之前扫描的获奖记录id以及扫码枪处理结果)
     * @Param: [request]
     * @return: java.lang.String
     * @Author: gq544
     * @date: 2019/8/10 0:08
     */
    @RequestMapping("/bycode")
    @Transactional  //添加事务管理
    public Map backCodeValue(String id,String token) {
        Map map = new HashMap();
        if(token!=null){
            Tokenuser tokenu = tokenuserService.selectByToken(token);
            System.out.println("tokenu  = " + tokenu );
            if (tokenu==null){
                //如果查询token为空，返回无效的token
                map.put("code",201);
                map.put("msg","token验证失败");
                map.put("success",false);
                return map;
            }else{
                //如果查询token有值，判断token有效期
                if (!(tokenu.getId()==1)){
                    if((new Date().getTime() - tokenu.getTokenDate().getTime()) > 60*60*1000){
                        map.put("code",201);
                        map.put("msg","token已失效");
                        map.put("success",false);
                        return map;
                    }
                    else{
                        //如果token验证成功，刷新token时间
                        tokenu.setTokenDate(new Date());
                        tokenuserService.updateById(tokenu);
                    }
                }
            }
        }else{ //如果查询token为空，返回无效的token
            map.put("code",203);
            map.put("msg","token参数无效或为空，验证失败");
            map.put("success",false);
            return map;
        }
        if(id==null){
            map.put("code",222);
            map.put("msg","id参数不能为空");
            map.put("success",false);
            return map;
        }else{
            boolean isok = true;
            //判断字符合法
            for(int i = 0 ; i < id.length();i++){
                if(!((id.charAt(i) >= 65 &&  id.charAt(i) <= 90) || (id.charAt(i) >= 97 &&  id.charAt(i) <= 122)
                        ||  (id.charAt(i) >= 48 &&  id.charAt(i) <= 57))){
                    isok = false;
                    break;
                }
            }if(!isok){
                map.put("code",207);
                map.put("msg","无效的id格式");
                map.put("success",false);
                return map;
            }
        }


        PrizeRecord pz = prizeRecordServiceImpl.getPrizeRecordById(Long.parseLong(id));
        //状态：200成功、201过期、202、已使用
        //根据中奖纪录的prizeid 查找一对应奖品信息并判断是否过期
        Prize pri = prizeServiceImpl.selectPrizeById(pz.getPrizeid());
        if (pz != null) {
            //已领取
            if (pz.getStatus() == 1) {
                map.put("code", 202);
            //} else if (!(Timeutil.getDayTime(new Date().getTime() + "").equals(Timeutil.getDayTime(pz.getCreatetime() + "")))) {
            } else if ((new Date().getTime()-Long.parseLong(pri.getEndvalidityperiod()+"000")) > 0) {
                map.put("data", 201);
                map.put("msg", "记录已过期，兑换失败");
                map.put("success", false);
            } else if (pz.getStatus() == 0) {
                //扫码成功过后，修改二维码扫码状态，记录已被扫码领取的状态
                pz.setStatus(1);
                prizeRecordServiceImpl.updateBycode(pz);
                Prize pr = prizeServiceImpl.selectPrizeById(pz.getPrizeid());
                pr.setStock(pr.getStock() - 1);
                prizeServiceImpl.updateStockByPrizeWinner(pr);

                map.put("code", 200);
                map.put("msg", "兑换成功");
                map.put("success", true);
                return map;
            }
            if (pz.getStatus() != 0) {
                map.put("msg", "兑换失败，该条记录已经被成功领取");
                map.put("success", false);
            }
        } else {
            map.put("code", 203);
            map.put("msg", "不存在的中奖记录");
            map.put("success", false);
        }
        return map;
    }


    @RequestMapping("/coupon")
    public Map getcupon(String id){
        Map map = new HashMap();

        if(id!=null && ""!=id){
            PrizeRecord p = prizeRecordServiceImpl.selectById(Long.parseLong(id));
            if(p.getStatus()==2){
                map.put("code",202);
                map.put("msg","过期的优惠券");
                map.put("success",false);
            }else if(p.getStatus()==0){
                p.setStatus(1);
                map.put("code",200);
                map.put("msg","优惠券领取成功");
                map.put("success",true);
            }else if(p.getStatus()==1){
                p.setStatus(1);
                map.put("code",201);
                map.put("msg","已领取的优惠券");
                map.put("success",false);
            }
            return map;
        }
        map.put("code",206);
        map.put("msg","未找到该优惠券");
        map.put("success",false);
        return map;
    }
}