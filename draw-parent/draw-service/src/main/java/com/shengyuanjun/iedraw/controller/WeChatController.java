package com.shengyuanjun.iedraw.controller;


import com.shengyuanjun.iedraw.service.TokenService;
import com.shengyuanjun.iedraw.test.gzhfwq.utils.CheckUtil;
import com.shengyuanjun.iedraw.test.gzhfwq.utils.HttpGetUtil;
import com.shengyuanjun.iedraw.test.gzhfwq.utils.XMLTOMAP;
import com.shengyuanjun.iedraw.test.sendtextmsgtest2.MessageUtil;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@PropertySource("classpath:constants/constant.properties")
@RequestMapping("/access")
public class WeChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    private final static String STATE = "123";
    /*
     * 微信公众号服务器
     */

    private final String token = "jxbtoken";

    @Resource
    private TokenService tokenServiceImpl;

    @GetMapping(value = "/test")
    public String ASDASD() {
        return "test success!!!!!!";
    }

    @RequestMapping(value = "/mytoken",method = { RequestMethod.GET})
    @ResponseBody
    public String doGet(HttpServletRequest request) {
            //获取参数值
            String signature = request.getParameter("signature");
            String timeStamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");

            /* PrintWriter out = null;*/
            if (CheckUtil.checkSignature(token, timeStamp, nonce).equals(signature)) {
                return echostr;
            }
        return "fail";
    }



    @RequestMapping(value = "/mytoken",method = { RequestMethod.POST})
    @ResponseBody
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
            ////推送链接版本
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
           /* Map<String, String> map = null;
            try {
                map = MessageUtil.xmlToMap(request);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            String ToUserName = map.get("ToUserName");
            String FromUserName = map.get("FromUserName");

            request.getSession().setAttribute("openid",FromUserName);
            String CreateTime = map.get("CreateTime");
            String MsgType = map.get("MsgType");
            String message = "";

            logger.info("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmap = " + map);

            if (MsgType.equalsIgnoreCase("Event")) {
                //从集合中，获取是哪一种事件传入
                String eventType = map.get("Event");
                if (eventType.equalsIgnoreCase("subscribe")) {
                    if("2".equals(map.get("EventKey")) || "qrscene_2".equals(map.get("EventKey"))){
                        String mycontent = com.shengyuanjun.iedraw.test.sendtextmsgtest2.MessageUtil.menuTextS5();
                        message = MessageUtil.initText(ToUserName, FromUserName, mycontent);
                    }else{
                        String mycontent = com.shengyuanjun.iedraw.test.sendtextmsgtest2.MessageUtil.menuTextH5();
                        message = MessageUtil.initText(ToUserName, FromUserName, mycontent);
                    }
                }
            }
            if ( "SCAN".equalsIgnoreCase(map.get("Event"))  || ("subscribe".equalsIgnoreCase(map.get("Event")) && map.get("EventKey").contains("qrscene_"))){
                String mycontent = com.shengyuanjun.iedraw.test.sendtextmsgtest2.MessageUtil.menuTextH5();
                message = MessageUtil.initText(ToUserName, FromUserName, mycontent);
            }
            System.out.println("message  = " + message);
            out.print(message); //返回转换后的XML字符串
            out.close();*/

        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //如果字段的值为空，判断若值为空，则删除这个字段>
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }

        System.out.println(res);


        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }


        InputStream inputStream = null;
        StringBuffer sb = new StringBuffer();


        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = "";

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            while (true) {
                if (!((s = in.readLine()) != null)) break;
                System.out.println(s);
                sb.append(s);

            }
            in.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,String> map = null;
        try {
            map = XMLTOMAP.xmlToMap(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("map = " + map);
        logger.info("map.get(\"EventKey\")="+map.get("EventKey"));
        String message = "";
        if(("999".equals(map.get("EventKey"))) || ("qrscene_999".equals(map.get("EventKey")))){
            //转盘关注扫码成功
            String mycontent = com.shengyuanjun.iedraw.test.sendtextmsgtest2.MessageUtil.menuTextH5();
            message = MessageUtil.initText(map.get("ToUserName"), map.get("FromUserName"), mycontent);
        }else if(("0".equals(map.get("EventKey"))) || ("qrscene_0".equals(map.get("EventKey")))){
            //唱跳rap关注扫码成功
            String mycontent = com.shengyuanjun.iedraw.test.sendtextmsgtest2.MessageUtil.menuTextS5();
            message = MessageUtil.initText(map.get("ToUserName"), map.get("FromUserName"), mycontent);
        }
        System.out.println("message  = " + message);
        out.print(message); //返回转换后的XML字符串
        out.close();

       /* if("123".equals(map.get("EventKey")) || "qrscene_123".equals(map.get("EventKey"))){
            Map para = new HashMap();
            String openid = res.get("openid");
            para.put("openid",openid);

            System.out.println("服务器获取的openid   ： " + openid);

            String str = HttpGetUtil.httpRequestToString("http://192.168.0.12/gzhs/msgsend",para);
            System.out.println("返回参数为  ： " + str);
        }*/

    }


    @RequestMapping(value = "/mycode")
    @ResponseBody
    public void getcode(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("回调...");
        String code = request.getParameter("code");
        System.out.println("获取的 code = " + code);
        try {
            //response.sendRedirect("http://192.168.0.32:8080?code="+code+"&statue"+123);
            response.sendRedirect("http://jxb-zbh.jxbscbd.com?code=" + code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return "{\"code\":\""+ code +"\"}";
    }

}