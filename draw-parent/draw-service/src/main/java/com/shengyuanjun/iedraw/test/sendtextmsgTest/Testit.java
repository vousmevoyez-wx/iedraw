package com.shengyuanjun.iedraw.test.sendtextmsgTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@RestController
public class Testit {

        //private final static String MEDIATYPE_CHARSET_JSON_UTF8 = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8";

        private static final Logger log = LoggerFactory.getLogger(Testit.class);

        @RequestMapping(value = "/chat"/*, method = {RequestMethod.GET, RequestMethod.POST}, produces = MEDIATYPE_CHARSET_JSON_UTF8*/)
        public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
            //如果为get请求，则为开发者模式验证
            if ("get".equals(request.getMethod().toLowerCase())) {
               // doGet();//在开发者模式验证中已处理，在此省略
            } else {
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                    Map<String, String> map = MessageUtil.xmlToMap(request);
                    String ToUserName = map.get("ToUserName");
                    String FromUserName = map.get("FromUserName");
                    request.getSession().setAttribute("openid",FromUserName);
                    String CreateTime = map.get("CreateTime");
                    String MsgType = map.get("MsgType");
                    String message = null;
                    if (MsgType.equals("Event")) {
                        //从集合中，获取是哪一种事件传入
                        String eventType = map.get("Event");
                        if (eventType.equals("subscribe")) {
                            message = MessageUtil.initNewsMessage(ToUserName, FromUserName);
                        }
                    }
                System.out.println("message  = " + message);
                    out.print(message); //返回转换后的XML字符串
                out.close();
            }
        }
}
