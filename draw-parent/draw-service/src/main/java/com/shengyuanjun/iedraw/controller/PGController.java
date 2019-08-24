package com.shengyuanjun.iedraw.controller;

import com.shengyuanjun.iedraw.domain.*;
import com.shengyuanjun.iedraw.service.*;
import com.shengyuanjun.iedraw.test.GenerateCode;
import com.shengyuanjun.iedraw.test.WXMsg.TestWX;
import com.shengyuanjun.iedraw.test.tokentest.NewTokenTester;
import com.shengyuanjun.iedraw.util.*;
import com.shengyuanjun.iedraw.util.timeUtil.Timeutil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Resource
private TestWX testwx;
    //获取用户信息
    @Resource
    private UserInfoService userinfoServiceImpl;

    @Resource
    private IPrizeRecordService prizeRecordServiceImpl;

    @Autowired
    private ITokenuserService tokenuserService;

    @Resource
    private IPrizeService prizeServiceImpl;

    @Resource
    private TokenService tokenServiceImpl;


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
     * 01
     *
     * @Description: 来自微信的请求，首先让前端需要通过授权获取code，来换取用户信息
     * @Param: [response]
     * @Author: gq544
     * @date: 2019/8/5 21:48
     */
    @RequestMapping(value = "/pgunp", method = RequestMethod.GET)
    public Map toGetCode(HttpServletResponse response, HttpServletRequest request) {
        String junpurl = "";
        Map map = new HashMap();
        try {
            //判断请求是否来自于微信端
            boolean bo = MessageFrom.msgFrom(request);
            //如果bo为true则来自微信，反之不是来自微信。。。;
            if (bo) {
                //通过微信端访问进来的请求回调请求获取code，redirect_url进行URLEncoder处理
                String callbackurl = URLEncoder.encode(redirecturl, "UTF-8");
                junpurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + callbackurl + "&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";

                map.put("appid",appid);
                map.put("redirecturl",junpurl);

                return map;
            } else {
                //如果不是微信登录，跳转到项目下提示页面
                junpurl = "非微信端无法访问此页面，请到微信端进行访问。";
                map.put("message","未关注");
                return map;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            map.put("message","error");
            return map;
        } catch (IOException e) {
            map.put("message","error");
            return map;
        }
    }


    /**
     * 02
     *
     * @Description: 前端页面网页授权后，获取code并发送给后台，在此方法换取用户openid，如果符合要求，则保存用户信息
     * 获取code之后。通过  https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     * @Param: [request, response]
     * @return: java.lang.String 返回状态码   返回userid=0表示用户未关注，userid=-1表示用户信息获取发生异常
     * @Author: gq544
     * @date: 2019/8/4 13:54
     */
    @RequestMapping(value = "/pgopen", method = RequestMethod.GET)
    @ResponseBody
    public String GetGZHOpenid(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String tokenaccess = "";
        String value = "1";
        //预防重复请求
        if (session.getAttribute("code") == null) {
            String code = request.getParameter("code");//获取code
            if (!code.equals(session.getAttribute("code"))) {
                Map codemap = userinfoServiceImpl.getOpenid(code);
                String openid = (String)codemap.get("openid");
                String opemidaccesstoken = (String)codemap.get("accesstoken");

                //获取openid之后通过官方接口查询用户基本信息
                //从数据库中获取此时的access_token
                String accesstoken = NewTokenTester.getaccessToken();
                if(accesstoken == null){

                    tokenaccess = tokenaccess+"accesstoken查询为空";

                }else{

                    tokenaccess = "这是token..  ： "+accesstoken;

                }
                //通过openid和accesstoken获取用户的基本信息
                JSONObject json = userinfoServiceImpl.getSNSUserInfo(accesstoken, openid);
                try {
                    if(json.toString().contains("subscribe")){
                        //已关注的状态
                        if ("1".equals(json.getString("subscribe"))) {
                            //如果用户已经关注公众号，则执行用户信息比对录入
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
                            System.out.println("user = " + backuser);
                            //校验是否已经录入资料结果
                            //如果数据库没有该用户信息，则执行保存
                            if (backuser == null) {
                                //已关注无记录
                                userinfoServiceImpl.saveUserInfomsg(user);
                                System.out.println("userid = " + user.getId());
                                return "{\"userid\":\"" + user.getId() + "\"}";
                            }else{
                                //已关注有记录
                                return "{\"userid\":\"" + backuser.getId() + "\"}";
                            }
                        } else {
                            //未关注的状态
                            UserInfo user = new UserInfo();
                            user.setOpenid(json.getString("openid"));

                            UserInfo backuser = new UserInfo();
                            //通过用户openid查找用户信息
                            backuser = userinfoServiceImpl.selectThisUser(openid);
                            if(backuser != null){
                                backuser.setSubscribe(new Byte("0"));
                                userinfoServiceImpl.updateById(backuser);
                            }
                            return "{\"userid\":\"0\"}";
                        }
                    }else{
                        return "{\"userid\":\"" + "  tokenaccessshow = "+ tokenaccess +"     后台拿到的code = " + code + "      code请求获取openid的value1 = "+json+"          openid换取用户信息的value2 = " + codemap.get("value") + "\"}";
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return "{\"userid\":\"-1\"}";
                }
            }
            return value;
        }
        return value;
    }

    /**
     * 03
     *
     * @Description: 获奖结果保存, (单个奖品传递)/通过type判断奖品类型，私人定制生成提货码，普通奖品生成二维码，有赞优惠券直接保存有赞,返回一个Prize 的Json对象
     * @Param: [id  奖品id   ，
     * @return: java.lang.String
     * @Author: gq544
     * @date: 2019/8/6 11:03
     * return  map(status,value)  如果status为-1，用户id获取错误，0，用户未关注公众号，   1，用户信息正常，已成功获取用户id
     *                                  2：超出当日次数    3：超出总抽奖次数    4： 不在活动范围内
     */
    @RequestMapping(value = "/prizval", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Transactional  //添加事务管理
    public Map savePrizeValue(HttpServletRequest request,String userid) {
        String testmsg = "";

        Map map = new HashMap();
        if(Integer.parseInt(userid) < 0){
            map.put("status","-1");
            return map;
        }else if(Integer.parseInt(userid) == 0){
            map.put("status","0");
            return map;
        }

      String longitude0 =  request.getParameter("longitude");
        String latitude0 =  request.getParameter("latitude");

        if(longitude0==null || latitude0==null){
            map.put("status","5");
            map.put("msg","未获取到地理位置信息");
            return map;
        }
        ParticipationRestriction par = participationRestrictionServiceImpl.selectParticipationRestrictionById(Long.parseLong("3"));
       String range = par.getRange();//系统范围
        //判断是否在活动的地图范围内
        double distence = CalulateTwoLanLon.getDistance(Double.parseDouble(longitude0),Double.parseDouble(latitude0),Double.parseDouble(par.getLongitude()),Double.parseDouble(par.getLatitude()));
        distence = distence * 1000;


        if (distence <= Double.parseDouble(par.getRange())) {

            System.out.println("userid = "+ userid);

            UserInfo user = userinfoServiceImpl.selectUserById(Long.parseLong(userid));
            //查询日抽奖上限
            String dailytimes = par.getDayparticipation();

            //查询总参与次数
            String uploaduTimes = par.getTotalparticipants();

            System.out.println("user = " + user);

            //查询本人抽奖次数111
            List<PrizeRecord> pzr = prizeRecordServiceImpl.selectWinTimesDaily();
            List<PrizeRecord> list = new Vector<PrizeRecord>();
            //如果总次数没有超，则判断当天总次数
            if (pzr.size() < Integer.parseInt(uploaduTimes)) {
                //获取现在  时间
                String nowtm = Timeutil.getLongTime();
                Iterator<PrizeRecord> it = pzr.iterator();
                while (it.hasNext()) {
                    PrizeRecord p = it.next();
                    //用于记录当天记录次数
                    //工具类转换时间戳为日期,确定当天有效
                    if (Timeutil.getDayTime(new Date().getTime() + "").equals(Timeutil.getDayTime(p.getCreatetime() + "")) && p.getUserid() == user.getId()) {
                        list.add(p);
                    }
                }
                if (list.size() > Integer.parseInt(dailytimes)) {
                    map.put("status","2");
                    return map;
                }
        /*
           状态判定:0为有赞，
                    1为奖品
                    2为私人定制
         */

                map.put("status","1");

                ArrayList<Prize> prizes = prizeServiceImpl.selectAllPrize();
                int i = 0;
                int[] result = new int[4];
                //遍历所有奖品
                Iterator<Prize> iter = prizes.iterator();
                //存放有货的奖品
                ArrayList<Prize> AP = new ArrayList<>();
                float obbchange = 0;

                //抽奖前库存判断
                while(iter.hasNext()){
                    Prize p= iter.next();
                    Prize tem = new Prize();
                    tem.setId(p.getId());
                    tem.setType(p.getType());
                    tem.setPrizename(p.getPrizename());
                    tem.setPictureurl(p.getPictureurl());
                    tem.setOdds(p.getOdds());
                    tem.setStock(p.getStock());
                    tem.setBeginvalidityperiod(p.getBeginvalidityperiod());
                    tem.setEndvalidityperiod(p.getEndvalidityperiod());

                    if(tem.getStock()<=0){
                        obbchange = obbchange + tem.getOdds();
                        tem.setOdds((float) 0);
                    }
                    AP.add(tem);
                }

                List<Prize> prizeList = new  ArrayList<Prize>();
                Iterator<Prize> ite = AP.iterator();
                int count = 0 ;
                while(ite.hasNext()){
                    Prize p= ite.next();
                    if(p.getType()==0 && count==0 && obbchange > 0){
                        p.setOdds(p.getOdds()+obbchange);
                        count++;
                    }
                    prizeList.add(p);
                }

                //根据参数进入抽奖工具类进行该路计算
                int selected = 0;
                Prize pz1 = null;
                //计算抽奖下标
                selected = DrawUtil.getPrizeIndex(prizeList);
                //结果奖品
                pz1 = prizes.get(selected);

                PrizeRecord pz = new PrizeRecord();

                pz.setUserid(user.getId());
                pz.setPrizeid(prizes.get(selected).getId());

                Long time = new Date().getTime();

                pz.setCreatetime(time);
                pz.setStatus(0);
                pz.setIsdel(1);

                /*
                    类型： 0表示优惠券
                    类型： 1表示私人定制
                    类型： 2表示奖品
                 */
                if (pz1.getType()==0) {

                    testmsg = testmsg + "优惠券奖品";

                    pz.setGoodscode("有赞");
                    pz.setType(0);

                    //保存到中奖记录
                    prizeRecordServiceImpl.savePrizeRecord(pz);
                } else if (pz1.getType()==1) {
                    testmsg = testmsg + "私人订制提货码奖品";

                    String number = NameUtil.genNumberStr(5);
                    PrizeRecord p1 = new PrizeRecord();
                    p1.setGoodscode(number);
                    p1.setType(1);
                    //判断这个商品提货码是否存在，如果不存在，则保存，。如果存在，重新生成提货吗
                    boolean bo = true;
                    //如果该码用过，则重新生成
                    while (bo) {

                        bo = (prizeRecordServiceImpl.selectGetNumber(p1) != null);

                        if (!bo) {
                            pz.setGoodscode(number);
                            Customize cu = new Customize();
                            //cu.setGoodscode(number);

                            String thistime = Timeutil.getLongTime();

                            //cu.setCreatetime(Long.parseLong(thistime));
                            pz.setType(1);

                            //保存到中奖记录
                            prizeRecordServiceImpl.savePrizeRecord(pz);
                           // customizeServiceImpl.addCustomize(cu);
                            break;
                        }
                        number = NameUtil.genNumberStr(5);
                        System.out.println("again。。。");
                        p1.setGoodscode(number);
                    }
                } else if (pz1.getType()==2) {
                    testmsg = testmsg + "获得奖品二维码";

                    pz.setType(2);

                    prizeRecordServiceImpl.savePrizeRecord(pz);

                    Long pzid = pz.getId();

                    //随机字符串拼接时间戳生成唯一二维码图片文件名称
                    String codeName = NameUtil.generateMixStr(6) + new Date().getTime() + "";

                    String jsonmsg = "{\"id\":\"" + pzid + "\"}";

                    GenerateCode.generateCode(gopsaveurl, jsonmsg, codeName);

                    PrizeRecord p = new PrizeRecord();
                    //设置对象id和url
                    p.setGoodscode(winprizeurl + gopsaveurl + "/" + codeName + ".png");
                    p.setId(pzid);
                    //对应保存url信息
                    prizeRecordServiceImpl.savePrizeRecordQRCode(p);
                }
                //抽奖后奖品数量减1
                System.out.println("pz1 = "+pz1);

                if(pz1.getStock()>0){
                    pz1.setStock(pz1.getStock()-1);
                    prizeServiceImpl.updateStockByPrizeWinner(pz1);
                }

                map.put("winner", pz1);
                map.put("msg",testmsg);

                String accesstoken = tokenServiceImpl.getAccessTokenFromDBById(1).getAccesstoken();
                String prizeName  = pz1.getPrizename();
                String openid = user.getOpenid();



               testwx.senMsgActive(openid, accesstoken,prizeName);

                return map;
            }else{
                //超出总次数
                map.put("status","3");

                return map;
            }
        } else {
            //超出范围
            map.put("status","4");
            map.put("msg","不在活动范围内");
            return map;
        }
    }


    /*
    用户查询自己的中奖记录的方法
    -1表示用户信息获取失败
    0表示用户信息不存在
    status=1表示用户信息正常获取并且附带一个PrizeRecord对象
 */
    @RequestMapping("/shmin")
    public Map getit(HttpServletRequest request){
        Map map = new HashMap();
        String userid = request.getParameter("userid");
        if(Integer.parseInt(userid) < 0){
            map.put("status","-1");
            return map;
        }else if(Integer.parseInt(userid) == 0){
            map.put("status","0");
            return map;
        }

        //前端传递回来userid。后端查看用户身份

        System.out.println("URL = " + request.getRequestURL());
        System.out.println("userid = " + userid);
        UserInfo user = userinfoServiceImpl.selectUserById(Long.parseLong(userid));
        if(user == null){
            map.put("status","-100");
            map.put("msg","不存在的用户id");
            return map;
        }

        List<PrizeRecord> prc = prizeRecordServiceImpl.selectTwoTableByUserId(user.getId());
        System.out.println("prc = " + prc);
        //用于调用接口的时候判断过期
        List<PrizeRecord> plist  =  new ArrayList<PrizeRecord>();

        Iterator<PrizeRecord> it = prc.iterator();
        while(it.hasNext()){
            PrizeRecord prcs =  it.next();
            Prize pe = prizeServiceImpl.selectPrizeById(prcs.getPrizeid());
            if(Long.parseLong(pe.getEndvalidityperiod()+"000") < new Date().getTime()){
                if("0".equals(prcs.getStatus()+"")) {
                    prcs.setStatus(2);
                    plist.add(prcs);
                }
            }
        }

        boolean bo = (plist.size()> 0);
        if(bo){
            Iterator<PrizeRecord> itps = plist.iterator();
            while(itps.hasNext()){
                PrizeRecord prcse =  itps.next();
                prcse.setStatus(2);
                System.out.println(prizeRecordServiceImpl.updateStatus(prcse));
            }
        }
        prc = prizeRecordServiceImpl.selectTwoTableByUserId(user.getId());

        map.put("status","1");
        map.put("PrizeRecord",prc);

        return map;
    }


    /**
     * 00001
     *@Description: 通过前段传递过来的状态返回对应语句 6条
     *@Param: [status]
     *@return: java.util.List<com.example.gzher.entitys.Quotations>
     *@Author: gq544
     *@date: 2019/8/9 1:43
     */
    @RequestMapping("/words")
    public Map  backWords(String status,String token){
        Map map = new HashMap();
        if(token!=null){
            Tokenuser tokenu = tokenuserService.selectByToken(token);
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

        Long statu = Long.parseLong(status);
        List<Quotations> qlist = quotationsServiceImpl.selectQuotationsByStatus(statu);
        System.out.println("value = " + qlist);

        List<Integer> indexList = new Vector<>();
        //通过status来选取对应语句
        for(int i = 0 ; i < qlist.size() ; i++){
            indexList.add(i);
        }

        List<Quotations> list = new Vector<>();
        Random r = new Random();
        if(indexList.size() <= 0){
            map.put("code",201);
            map.put("msg","获取失败");
            map.put("success",false);
            return map;
        }
        for(int j = 0 ; j < 6 ; j++){
            int index = r.nextInt(indexList.size());
            list.add(qlist.get(indexList.get(index)));
            indexList.remove(index);
        }
        //200成功   201 失败
        if(list.size()>0){
            map.put("code",200);
            map.put("data",list);
            map.put("msg","获取成功");
            map.put("success",true);
        }else{
            map.put("code",201);
            map.put("msg","获取失败");
            map.put("success",false);
        }
        return map;
    }
}