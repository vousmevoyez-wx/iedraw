package com.shengyuanjun.iedraw.controller;


import com.shengyuanjun.iedraw.test.gzhfwq.utils.CheckUtil;
import com.shengyuanjun.iedraw.test.gzhfwq.utils.XMLTOMAP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/jxb")
public class WeChatController {
    private static final Logger log = LoggerFactory.getLogger(WeChatController.class);

    private final static String STATE = "123";
    /*
     * 微信公众号服务器
     */

    private final String token = "computergame";

    @GetMapping(value = "/test")
    public void ASDASD(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进来就好");
    }


    /*@PostMapping(value = "/mytoken")
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("This is my Post...");

    }*/

    @RequestMapping(value = "/mytoken",method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("mytoken执行..");
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet) {
            //获取参数值
            String signature = request.getParameter("signature");
            String timeStamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");

            /* PrintWriter out = null;*/
            if (CheckUtil.checkSignature(token, timeStamp, nonce).equals(signature)) {
                return echostr;
            }else{
                return "fail";
            }
        }else{
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
            try {
                Map<String,String> map = XMLTOMAP.xmlToMap(sb.toString());
                System.out.println("map  =  " + map);
                System.out.println("map.get(\"EventKey\")="+map.get("EventKey"));
                if("123".equals(map.get("EventKey")) || "qrscene_123".equals(map.get("EventKey"))){
                    Map para = new HashMap();
                    para.put("openid",res.get("openid"));

                    new SendMsgController().senemsg("123345");
                    //HttpGetUtil.httpRequestToString("http://www.qvbnnm.xyz/wx/send",para);
                }else{
                    new SendMsgController().senemsg("123345");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }
    }


    @RequestMapping(value = "/mycode")
    @ResponseBody
    public void getcode(HttpServletRequest request,HttpServletResponse response){
        System.out.println("回调...");
        String code = request.getParameter("code");
        System.out.println("获取的 code = "+code);
        try {
            response.sendRedirect("http://192.168.0.54:8080?code="+code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/security", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进来新的方法我试试....");
    }
}