package com.shengyuanjun.iedraw.controller;


import com.shengyuanjun.iedraw.domain.*;
import com.shengyuanjun.iedraw.service.*;
import com.shengyuanjun.iedraw.test.GenerateCode;
import com.shengyuanjun.iedraw.util.DrawUtil;
import com.shengyuanjun.iedraw.util.MessageFrom;
import com.shengyuanjun.iedraw.util.NameUtil;
import com.shengyuanjun.iedraw.util.timeUtil.Timeutil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


/**
 * @program: gongzhongget
 * @description:
 * @author: gq544
 * @create: 2019-08-03 16:28
 */
@RestController
@PropertySource("classpath:constants/constant.properties")
@RequestMapping("/wx")
public class PGController {

    private static final Logger logger = LoggerFactory.getLogger(PGController.class);

    //获取用户信息
    @Resource
    private UserInfoService userinfoServiceImpl;

    @Resource
    private IPrizeRecordService prizeRecordServiceImpl;


    @Resource
    private IPrizeService prizeServiceImpl;

    @Resource
    private TokenService tokenServiceImpl;

    @Resource
    private ICustomizeService customizeServiceImpl;

    @Resource
    private IQuotationsService quotationsServiceImpl;

    @Resource
    private IParticipationRestrictionService participationRestrictionServiceImpl;

    @Value("${url.project}")
    private String pjurl;//项目的IP地址&域名

    @Value("${url.active}")
    private String h5url;

    @Value("${gzh.coderedirecturl}")
    private String redirecturl;


    @Value("${gzh.appid}")
    private String appid;

    @Value("${url.prizeurl}")
    private String gopsaveurl;

    @Value("${url.winprizecodeurl}")
    public String winprizeurl;

    /**
     * @Description: 判断请求必须来自微信才能打开进入H5活动页面, 不能通过外部浏览器直接输入H5地址进入活动页面
     * @Param: [response, request]
     * @return: void
     * @Author: gq544
     * @date: 2019/8/7 22:28
     */
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public void backActive(HttpServletResponse response, HttpServletRequest request) {
        System.out.println("判断结果为 " + MessageFrom.msgFrom(request));
        try {
            if (MessageFrom.msgFrom(request)) {//判断请求是否来自于微信端
                response.sendRedirect(h5url);
            } else {
                logger.info("本次请求不是来自微信端，需要重新到微信端进行访问。。。");
                response.sendRedirect(pjurl + "ot/backwx");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 01
     *
     * @Description: 来自微信的请求，首先让前端需要通过授权获取code，来换取用户信息
     * @Param: [response]
     * @Author: gq544
     * @date: 2019/8/5 21:48
     */
    @RequestMapping(value = "/pgcode", method = RequestMethod.GET)
    public String toGetCode(HttpServletResponse response, HttpServletRequest request) {
        String junpurl = "";
        try {
            //判断请求是否来自于微信端
            boolean bo = MessageFrom.msgFrom(request);
            //如果bo为true则来自微信，反之不是来自微信。。。;
            if (bo) {
                //通过微信端访问进来的请求回调请求获取code，redirect_url进行URLEncoder处理
                String callbackurl = URLEncoder.encode(redirecturl, "UTF-8");
                junpurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + callbackurl + "&response_type=code&scope=snsapi_base&state=123&connect_redirect=#wechat_redirect";

                //在前端重定向到回调函数获取授权code
                response.sendRedirect(junpurl);
            } else {
                //如果不是微信登录，跳转到项目下提示页面
                logger.info("本次请求不是来自微信端，需要重新到微信端进行访问。。。");
                junpurl = "非微信端无法访问此页面，请到微信端进行访问。";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "fail";
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
        return junpurl;
    }


    /**
     * 02
     *
     * @Description: 前端页面网页授权后，获取code并发送给后台，在此方法换取用户openid，如果符合要求，则保存用户信息
     * 获取code之后。通过  https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     * @Param: [request, response]
     * @return: java.lang.String 返回状态码   1表示用户已关注且信息保存成功，1表示用户未关注公众号，2表示用户信息获取失败
     * @Author: gq544
     * @date: 2019/8/4 13:54
     */
    @RequestMapping(value = "/pgopen", method = RequestMethod.GET)
    @ResponseBody
    public String GetGZHOpenid(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String value = "1";
        //预防重复请求
        //if (session.getAttribute("code") == null) {
            String code = request.getParameter("code");//获取code
        System.out.println("前端过来 code = " + code);
            if (!code.equals(session.getAttribute("code"))){
                logger.info("获取code并申请获得openid...code = " + code);
                session.setAttribute("code", code);
                System.out.println("code =" + code);
                String openid = userinfoServiceImpl.getOpenid(code);
                session.setAttribute("openid", openid);
                //获取openid之后通过官方接口查询用户基本信息
                //从数据库中获取此时的access_token
                AccessToken accesstoken = tokenServiceImpl.getAccessTokenFromDBById(1);
                String token = accesstoken.getAccesstoken();
                //通过openid和accesstoken获取用户的基本信息
                logger.info(" /pgopen 使用openid 获取用户基本信息userinfo。。。。。。。。。。。。");
                JSONObject json = userinfoServiceImpl.getSNSUserInfo(token, openid);
                try {
                    if (1 == (json.getInt("subscribe"))) {
                        //如果用户已经关注公众号，则执行用户信息比对录入
                        System.out.println(json);
                        UserInfo user = new UserInfo();

                        user.setHeadimgurl(json.getString("headimgurl"));
                        user.setOpenid(json.getString("openid"));
                        user.setNickname(json.getString("nickname"));
                        user.setSex(new Byte(json.getString("sex")));
                        user.setSubscribe(new Byte(json.getString("subscribe")));

                        String nowtime = Timeutil.getLongTime();

                        user.setCreatetime(Long.parseLong(nowtime));

                        UserInfo backuser = new UserInfo();
                        //通过用户openid查找用户信息
                        backuser = userinfoServiceImpl.selectThisUser(openid);
                        System.out.println("user = "+backuser);
                        //校验是否已经录入资料结果
                        //如果数据库没有该用户信息，则执行保存
                        if (backuser != null) {
                            userinfoServiceImpl.saveUserInfomsg(user);
                        }
                        logger.info("该用户已关注公众号，信息校验成功。。。");
                    } else {
                        logger.info("该用户并未关注。。。。。。。。。");
                        value = "0";
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    logger.info("用户数据处理失败。。。。。。...");
                    return "2";
                }
            }
       // }
        return value;
    }

    /**
     * @Description: 判断是否超出了范围，在范围内才能进入抽奖页面
     * @Param: [request]
     * @return: java.lang.Boolean
     * @Author: gq544
     * @date: 2019/8/9 18:00
     */
    @RequestMapping(value = "/inhe", method = RequestMethod.GET)
    @ResponseBody
    public boolean ishere(HttpServletRequest request) {
        System.out.println("定位确认");
        //判断范围
        ParticipationRestriction par = participationRestrictionServiceImpl.selectParticipationRestrictionById(Long.parseLong("1"));
        String range = par.getRange();//系统范围
        String longitude0 = request.getParameter("longitude");
        String latitude0 = request.getParameter("latitude");
        Double la = Double.parseDouble(par.getLatitude()) - Double.parseDouble(latitude0);
        Double gi = Double.parseDouble(par.getLongitude()) - Double.parseDouble(longitude0);

        //判断是否在活动的地图范围内
        if ((la * la + gi * gi) < Double.parseDouble(par.getRange()) * Double.parseDouble(par.getRange())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 03
     *
     * @Description: 获奖结果保存, (单个奖品传递)/通过type判断奖品类型，私人定制生成提货码，普通奖品生成二维码，有赞优惠券直接保存有赞,返回一个Prize 的Json对象
     * @Param: [id  奖品id   ，
     * @return: java.lang.String
     * @Author: gq544
     * @date: 2019/8/6 11:03
     */
    @RequestMapping(value = "/prizval", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Transactional  //添加事务管理
    public Map savePrizeValue(HttpServletRequest request) {
        
        Map map = new HashMap();

        System.out.println("定位确认");

        String longitude0 =  request.getParameter("longitude");
        String latitude0 =  request.getParameter("latitude");

        //判断范围
        ParticipationRestriction par = participationRestrictionServiceImpl.selectParticipationRestrictionById(Long.parseLong("3"));
        String range = par.getRange();//系统范围
        System.out.println("td = " + longitude0);
        System.out.println("ti = " + latitude0);
        Double la = Double.parseDouble(par.getLatitude()) - Double.parseDouble(latitude0);
        Double gi = Double.parseDouble(par.getLongitude()) - Double.parseDouble(longitude0);
        //判断是否在活动的地图范围内
       if ((la * la + gi * gi) < Double.parseDouble(par.getRange()) * Double.parseDouble(par.getRange())) {
            logger.info("判断的值在范围以内");
            HttpSession session = request.getSession();
            //zaisession中获取openid来查询用户信息
           // String openid = (String) session.getAttribute("openid");
            String openid = "asd4564";
            System.out.println("收到openid = " + openid);
            UserInfo user = userinfoServiceImpl.selectThisUser("asd4564");
            System.out.println("par = " + par);
            //查询日抽奖上限
            String dailytimes = par.getDayparticipation();

            //查询总参与次数
            String Alltimes = par.getTotalparticipants();

            System.out.println("user = " + user);

            //查询本人抽奖次数
            List<PrizeRecord> pzr = prizeRecordServiceImpl.selectWinTimesDaily();
            List<PrizeRecord> list = new Vector<PrizeRecord>();
            System.out.println("times = " + Integer.parseInt(Alltimes));
            //如果总次数没有超，则判断当天总次数
            if (pzr.size() < Integer.parseInt(Alltimes)) {
                //获取现在时间
                String nowtm = Timeutil.getLongTime();
                Iterator<PrizeRecord> it = pzr.iterator();
                System.out.println("pzr = " + pzr);
                while (it.hasNext()) {
                    PrizeRecord p = it.next();
                    //用于记录当天记录次数
                    //工具类转换时间戳为日期,确定当天有效
                    System.out.println("p = " + p);
                    if (Timeutil.getDayTime(new Date().getTime() + "").equals(Timeutil.getDayTime(p.getCreatetime() + "")) && p.getUserid() == user.getId()) {
                        list.add(it.next());
                    }
                }
                if (list.size() > Integer.parseInt(dailytimes)) {
                    map.put("dayup", "true");
                    return map;
                }
        /*
           状态判定:0为有赞，
                    1为奖品
                    2为私人定制
         */
                ArrayList<Prize> prizes = prizeServiceImpl.selectAllPrize();
                int i = 0;
                int[] result = new int[4];
                Iterator<Prize> iter = prizes.iterator();

                ArrayList<Prize> AP = new ArrayList<>();
                while (iter.hasNext()) {
                    Prize p = iter.next();
                    p.setOdds((float) p.getOdds());
                    if (p.getStock() > 0) {
                        AP.add(p);
                    }
                }

                System.out.println("抽奖开始");
                //根据参数进入抽奖工具类进行该路计算
                int selected = DrawUtil.getPrizeIndex(AP);
                Prize ppp = prizes.get(selected);
                System.out.println("抽中的奖品为：" + ppp);

                System.out.println("--------------------------------");

                System.out.println("抽奖结束");

                PrizeRecord pz = new PrizeRecord();
                pz.setUserid(user.getId());
                pz.setPrizeid(prizes.get(selected).getId());

                String nowtime = Timeutil.getLongTime();
                Long time = Long.parseLong(nowtime);

                pz.setCreatetime(time);
                pz.setStatus(0);
                pz.setIsdel(1);

                System.out.println("继续走,pp.name = "+ppp.getPrizename());
                /*
                    类型： 0表示优惠券
                    类型： 1表示私人定制
                    类型： 2表示奖品
                 */
                if (ppp.getType()==0) {
                    System.out.println("这优惠券");
                    pz.setGoodscode("有赞");
                    pz.setType(0);
                    prizeRecordServiceImpl.savePrizeRecord(pz);
                } else if (ppp.getType()==1) {
                    System.out.println("这私人定制");
                    String number = NameUtil.genNumberStr(5);
                    PrizeRecord p1 = new PrizeRecord();
                    p1.setGoodscode(number);
                    p1.setType(1);
                    //判断这个商品提货码是否存在，如果不存在，则保存，。如果存在，重新生成提货吗
                    boolean bo = true;
                    //如果该码用过，则重新生成
                    while (bo) {
                        System.out.println("判断bo的值,其中  p1 = " + p1);
                        bo = (prizeRecordServiceImpl.selectGetNumber(p1) != null);
                        System.out.println("bo = " + bo);
                        if (!bo) {
                            pz.setGoodscode(number);
                            Customize cu = new Customize();
                            cu.setGoodscode(number);

                            String thistime = Timeutil.getLongTime();

                            cu.setCreatetime(Long.parseLong(thistime));
                            pz.setType(1);
                            prizeRecordServiceImpl.savePrizeRecord(pz);
                            customizeServiceImpl.addCustomize(cu);
                            break;
                        }
                        number = NameUtil.genNumberStr(5);
                        System.out.println("again。。。");
                        p1.setGoodscode(number);
                    }
                } else if (ppp.getType()==2) {
                    System.out.println("这是个奖品");
                    pz.setType(2);
                    prizeRecordServiceImpl.savePrizeRecord(pz);

                    Long pzid = pz.getId();

                    //随机字符串拼接时间戳生成唯一二维码图片文件名称
                    String codeName = NameUtil.generateMixStr(6) + new Date().getTime() + "";

                    String jsonmsg = "{\"id\":\"" + pzid + "\",\"status\": \"0\"}";

                    GenerateCode.generateCode(gopsaveurl, jsonmsg, codeName);

                    PrizeRecord p = new PrizeRecord();
                    p.setGoodscode(winprizeurl + gopsaveurl + "/" + codeName + ".png");
                    p.setId(pzid);
                    prizeRecordServiceImpl.savePrizeRecordQRCode(p);
                }

                System.out.println("prize = " + ppp);

                logger.info("用户查询结果为 " + user);
                map.put("winner", ppp);
                return map;
            }else{
                //超出总次数
                map.put("allup", "true");
                return map;
            }
       } else {
            System.out.println("超出活动区域");
            //超出活动区域
            map.put("area", "out");
            return map;
        }
    }

    /**
     * -1
     * 通过openid获取userInfo基本信息
     *
     * @Description: 通过接口 https://api.weixin.qq.com/sns/userinfo?access_token=TOKEN&appid=APPID 获取userinfo 用户基本信息
     * @Param: [openid]
     * @return: java.lang.String
     * @Author: gq544
     * @date: 2019/8/4 14:53
     */
    @RequestMapping(value = "/getuserinfo", method = RequestMethod.GET)
    @ResponseBody
    public void GetGZHUserInfo(String openid) throws IOException {

        //从数据库中获取此时的access_token
        AccessToken accesstoken = tokenServiceImpl.getAccessTokenFromDBById(1);
        System.out.println("accesstoken = " + accesstoken);
        String token = accesstoken.getAccesstoken();
        //通过openid和accesstoken获取用户的基本信息
        JSONObject json = userinfoServiceImpl.getSNSUserInfo(token, openid);
        logger.info("已使用openid 获取用户基本信息userinfo。。。。。。。。。。。。");
        try {
            if (1 == (json.getInt("subscribe"))) {
                logger.info("该用户已关注，正在校核用户信息。。。。。。。。。");
                System.out.println(json);
                UserInfo user = new UserInfo();

                String nowtime = Timeutil.getLongTime();
                user.setCreatetime(Long.parseLong(nowtime));
                System.out.println("user = " + user);

                //如果数据库没有该用户信息，则执行保存
                if (userinfoServiceImpl.selectThisUser(openid) == null) {

                    userinfoServiceImpl.saveUserInfomsg(user);

                }

            } else {
                logger.info("该用户并未关注。。。。。。。。。");
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("用户数据处理失败。。。。");
        }
    }

    @RequestMapping(value="/shwal",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map showWinnerPrizes(){
        Map map = new HashMap();
        String openid = "asd4564";
        UserInfo user = userinfoServiceImpl.selectThisUser(openid);

        List<PrizeRecord> pzr = prizeRecordServiceImpl.selectWinTimesDaily();
        List<PrizeRecord> list = new Vector<PrizeRecord>();

        Iterator<PrizeRecord> it = pzr.iterator();
        System.out.println("pzr = " + pzr);
        while (it.hasNext()) {
            PrizeRecord p = it.next();
            System.out.println("p = " + p);
            if (p.getUserid() == user.getId()) {
                list.add(p);
            }
        }
        map.put("抽奖用户",user);

        Iterator<PrizeRecord> iterator = list.iterator();
        while(iterator.hasNext()){
            PrizeRecord p1 = iterator.next();
            //0代表有赞
            if(p1.getType()==0){
                map.put("有赞","有赞优惠券");
            }else if(p1.getType()==1){
                map.put("私人订制",customizeServiceImpl.selectCustomizeById(p1.getPrizeid()));
                System.out.println("p1.getPrizeid() = "+p1.getPrizeid());
            }else if(p1.getType()==2){
                map.put("奖品",prizeServiceImpl.selectPrizeById(p1.getPrizeid()));
                System.out.println("prize 信息 "+prizeServiceImpl.selectPrizeById(p1.getPrizeid()));
            }
        }
        return map;
    }
    @RequestMapping("/shmin")
    public List<PrizeRecord> getit(HttpServletRequest request){
        logger.info("selectmyit...................");
        HttpSession session = request.getSession();
        String openid = (String)session.getAttribute("openid");
        openid = "asd4564";
        UserInfo user = userinfoServiceImpl.selectThisUser(openid);

        List<PrizeRecord> prc = prizeRecordServiceImpl.selectTwoTableByUserId(user.getId());
        Iterator<PrizeRecord> it = prc.iterator();
        while(it.hasNext()){
            System.out.println("value = "+it.next());
        }
        return prc;
    }
}