package com.shengyuanjun.iedraw.test.tokentest;

import com.shengyuanjun.iedraw.util.postutil.URLConnection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewTokenTester {
    public static final String GET_URL = "https://api.jxbscbd.com/gateway/weixinUser/weixin/user/getAccesstoken?appId=wxaf662218893cdffa";

//Token接口
    public static String getaccessToken() {
       /* String token = tokenServiceImpl.getAccessTokenFromDBById(1).getAccesstoken();
        System.out.println("TOKEN = " + token);*/
        //正式
        //String url = "https://api.jxbscbd.com/auth/login";
        //测试
        String url = "https://api.jxbscbd.com/auth/login";
        String jsonmsg = "{\n" +
                "\"username\":\"token_keeper\",\n" +
                "\"password\":\"75469rgjk\",\n" +
                "\"appid\":\"tokenmanager\",\n" +
                "\"accountType\":\"wechatManager\"\n" +
                "}";
        System.out.println("jsonmsg = "+jsonmsg);
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonmsg);

        try {
            System.out.println("url = " + url);

            com.alibaba.fastjson.JSONObject json = URLConnection.postResponse(url, jsonObject);

            System.out.println("json = "+json);
            String Authorization = json.getString("data");
            System.out.println("Aut = " + Authorization);
            //获取 token 对象
            jsonObject = com.alibaba.fastjson.JSONObject.parseObject(Authorization);
            System.out.println("jsonobj = "+ jsonObject);
            //获取 Authorization 参数
            String token = jsonObject.getString("Authorization");
            System.out.println("token = " + token);




            /*String urlgetaccess = GETACCESS_TOKEN+"?appId="+appid;

            String value = HttpGetUtil.httpURLConectionGET(urlgetaccess,token);

            value = jsonObject.getString("data");
            System.out.println("VALUE  ACCESSTOKEN  = " + value);


            url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+URLEncoder.encode(value, "UTF-8");

            jsonmsg = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 999}}}";

            jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonmsg);


            System.out.println("url = " + url);

            json = URLConnection.postResponse(url, jsonObject);

            System.out.println("value = " + json.toString());
            String ticket = json.getString("ticket");
            System.out.println("ticket = " + ticket);
            System.out.println("url = " + json.getString("url"));

            System.out.println("TICKET = " + ticket);

            String codeurl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket);
            System.out.println("codeurl = " + codeurl);
            System.out.println("end 。。。。。。。。。。。。。。。。");
            response.sendRedirect(codeurl);
*/
            try {
                URL urls = new URL(GET_URL);    //把字符串转换为URL请求地址
                HttpURLConnection connection = (HttpURLConnection) urls.openConnection();// 打开连接
                //addRequestProperty添加相同的key不会覆盖，如果相同，内容会以{name1,name2}
                connection.addRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4MDAwMDEzNCIsImlzcyI6Imlqb3ZvIiwiZXhwIjoxNTk3MjE0MjM0LCJpYXQiOjE1NjYxMTAyMzQsInJvbCI6InRva2VuLCJ9.GfShzSqYWcWYi2ag2hQ2FyQsP0N3jZO-30FGyqQoNAjeK_HBw8WGwKUe_IBny6wyGB-Q_2jRlpTx_-bkTLf8cA");  //来源哪个系统
                //setRequestProperty添加相同的key会覆盖value信息
                //setRequestProperty方法，如果key存在，则覆盖；不存在，直接添加。
                //addRequestProperty方法，不管key存在不存在，直接添加。
            /*connection.setRequestProperty("user", "user");  //访问申请用户
            InetAddress address = InetAddress.getLocalHost();
            String ip = address.getHostAddress();//获得本机IP
            connection.setRequestProperty("ip", ip);  //请求来源IP
            connection.setRequestProperty("encry", "00000");*/
                connection.connect();// 连接会话
                // 获取输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {// 循环读取流
                    sb.append(line);
                }
                br.close();// 关闭流
                connection.disconnect();// 断开连接
                String value = sb.toString();
                // com.alibaba.fastjson.JSONObject jsonObject = null;
                jsonObject = com.alibaba.fastjson.JSONObject.parseObject(value);

                String data = jsonObject.getString("data");
                System.out.println("data =  "+data);
                jsonObject = com.alibaba.fastjson.JSONObject.parseObject(data);
                String accessToken = jsonObject.getString("accessToken");
                System.out.println("accesstoken = " + accessToken);
                return accessToken;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("失败!");
                return "fail";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
