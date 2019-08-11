package com.shengyuanjun.iedraw.controller;


import com.shengyuanjun.iedraw.domain.Customize;
import com.shengyuanjun.iedraw.domain.Prize;
import com.shengyuanjun.iedraw.domain.PrizeRecord;
import com.shengyuanjun.iedraw.domain.UserInfo;
import com.shengyuanjun.iedraw.service.IPrizeRecordService;
import com.shengyuanjun.iedraw.service.IPrizeService;
import com.shengyuanjun.iedraw.service.TokenService;
import com.shengyuanjun.iedraw.service.UserInfoService;
import com.shengyuanjun.iedraw.util.postutil.URLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

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

    //获取用户信息
    @Resource
    private UserInfoService userinfoServiceImpl;

    @Resource
    private IPrizeService prizeServiceImpl;

    @Value("${url.activecqcode}")
    private String activecodeurl;

    @RequestMapping("/showgzh")
    public void  showGZHCode(HttpServletResponse response){
        try {
            response.sendRedirect(activecodeurl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 获取ticket并生成跳转公众号二维码
     * @Param: []
     * @return: void
     * @Author: gq544
     * @date: 2019/8/5 13:37
     */
    //生成一个有效时间30天的临时二维码
    @RequestMapping(value = "/gzcode1" , method = RequestMethod.GET)
    public void getQRCodeJunpGZH(HttpServletResponse response) {
        String token = tokenServiceImpl.getAccessTokenFromDBById(1).getAccesstoken();
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
        //String jsonString = JSONObject.fromObject("ASDASD").toString().replace("day", "Day");
        //JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", null);
        //String jsonmsg = "{\"expire_seconds\":2592000,\"action_name\": \"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":123}}}";
        String jsonmsg = "{\"expire_seconds\":2592000,\"action_name\": \"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\"mytester\"}}}";

        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonmsg);
        try {
            System.out.println("url = " + url);

            com.alibaba.fastjson.JSONObject json = URLConnection.postResponse(url, jsonObject);

            System.out.println("value = " + json.toString());
            String ticket = json.getString("ticket");
            System.out.println("ticket = " + ticket);
            System.out.println("url = " + json.getString("url"));

            String codeurl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket);
            System.out.println("codeurl = " + codeurl);
            response.sendRedirect(codeurl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 获取ticket并生成跳转公众号二维码
     * @Param: []
     * @return: void
     * @Author: gq544
     * @date: 2019/8/5 13:37
     */
    //生成一个有效时间永久二维码
    @RequestMapping(value = "/gzcode2" , method = RequestMethod.GET)
    public void getQRCodeJunpGZHForEver(HttpServletResponse response) {
        String token = tokenServiceImpl.getAccessTokenFromDBById(1).getAccesstoken();
        System.out.println("TOKEN = "+token);
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
        //String jsonString = JSONObject.fromObject("ASDASD").toString().replace("day", "Day");
        //JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", null);
        //String jsonmsg = "{\"expire_seconds\":2592000,\"action_name\": \"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":123}}}";
        String jsonmsg = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";

        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonmsg);
        try {
            System.out.println("url = " + url);

            com.alibaba.fastjson.JSONObject json = URLConnection.postResponse(url, jsonObject);

            System.out.println("value = " + json.toString());
            String ticket = json.getString("ticket");
            System.out.println("ticket = " + ticket);
            System.out.println("url = " + json.getString("url"));

            String codeurl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket);
            System.out.println("codeurl = " + codeurl);
            response.sendRedirect(codeurl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
    *@Description: 扫码枪访问接口，并传入一个id，通过id查看对应中奖信息用于返回一个阿松秒结果信息
    *@Param: [id]
    *@return: com.example.gzher.entitys.PrizeRecord
    *@Author: gq544
    *@date: 2019/8/8 9:28
    */
    @RequestMapping(value="/bycode",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Transactional  //添加事务管理
    public String selectQRCode(Long id) {

        //Customize cus = customizeServiceImpl.selectCustomizeById(id);
        PrizeRecord pr = prizeRecordServiceImpl.getPrizeRecordById(id);//查询中奖编号

        UserInfo user = userinfoServiceImpl.selectUserById(pr.getUserid());//查询用户信息

        Prize pz = prizeServiceImpl.selectPrizeById(pr.getPrizeid());//查询奖品信息

        String status = pr.getStatus().toString();
        String value = "";
        if("0".equals(status)){
            //status为0说明未领取
            value = "{\"able\":\"" + true + "\",\"username\":\""+ user.getNickname() +"\",\"userHeadimgurl\":\"" + user.getHeadimgurl() + "\",\"prizename\":\""
                    + pz.getPrizename() + "\",\"prizepictureurl\":\"" + pz.getPictureurl() + "\"}";
        }else if("1".equals(status)){
            //status为1说明已领取
            value = "{\"able\":\"" + false + "\"}";
        }
        return value;
    }


    /**
    *@Description: 根据扫码枪回调参数执行相应处理 需要参数为 id(之前扫描的获奖记录id以及扫码枪处理结果)
    *@Param: [request]
    *@return: java.lang.String
    *@Author: gq544
    *@date: 2019/8/10 0:08
    */
    @RequestMapping("/overQcode")
    public String backCodeValue(HttpServletRequest request){
        Long id = Long.parseLong(request.getParameter("id"));
        String statu = request.getParameter("status");
        //如果返回扫码成功信号，则进行相应处理
        if(statu.equals("success")){
            PrizeRecord pzr = new PrizeRecord();
            pzr.setId(id);
            //扫码成功过后，修改二维码扫码状态，记录已被扫码领取的状态
            pzr.setStatus(1);
            prizeRecordServiceImpl.updateBycode(pzr);
        }else{
            return  "{\"value\":\"fail\"}";
        }

        return "{\"value\":\"success\"}";
    }

/**
*@Description:  用提货码提取私人定制的时候，由提供方提供图片等资料信息
*@Param: [printchart] 参数： printchart 图片链接
*@return: java.lang.String
*@Author: gq544
*@date: 2019/8/10 0:46
*/
    @RequestMapping("/addcusmsg")
    public String callbackshow(String printchart){
        Long time = new Date().getTime();
        Customize cum = new Customize();
        cum.setCustomizetime(time);
        cum.setPrintchart(printchart);
        return "{\"value\":\"success\"}";
    }
}