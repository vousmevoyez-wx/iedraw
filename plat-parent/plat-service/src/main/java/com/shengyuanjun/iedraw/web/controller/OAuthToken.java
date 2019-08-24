//package com.shengyuanjun.iedraw.web.controller;
//
//import com.shengyuanjun.iedraw.util.MD5Util;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//
///**
// * OAuthTokenController
// *
// */
//@Scope("prototype")
//@Controller
//@RequestMapping("/oAuthToken")
//public class OAuthToken {
//
//    /**
//     * 獲取Token
//     * @param grant_type
//     * @param appid
//     * @param secret
//     * @return
//     */
//    @RequestMapping(params = "token",method = RequestMethod.GET)
//    @ResponseBody
//    public Object token (@RequestParam(value = "grant_type") String grant_type, @RequestParam(value = "appid") String appid,
//                         @RequestParam(value = "secret") String secret, HttpServletResponse response) {
//        Map<string, object=""> map = new HashMap<>();
//        switch (grant_type) {
//            case "authorization_code" : //授权码模式(即先登录获取code,再获取token)
//                break;
//            case "password" : //密码模式(将用户名,密码传过去,直接获取token)
//                break;
//            case "client_credentials" : //客户端模式(无用户,用户向客户端注册,然后客户端以自己的名义向’服务端’获取资源)
//                OAuthTokenManager oAuthTokenManager = OAuthTokenManager.getInstance();
//                String token = oAuthTokenManager.token(appid, secret,null);//loginInterface是业务逻辑回掉函数
//                //返回Token
//                map.put("access_token",token);
//                map.put("expires_in",OAuthTokenManager.MINUTE_TTL/1000);
//                break;
//            case "implicit" : //简化模式(在redirect_uri 的Hash传递token; Auth客户端运行在浏览器中,如JS,Flash)
//                break;
//            case "refresh_token" : //刷新access_token
//                break;
//        }
//
//        return map;
//    }
//
//    @RequestMapping(params = "loginAuth2",method = RequestMethod.GET)
//    @ResponseBody
//    public Object loginAuth2 (HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "accessToken") String accessToken ){
//        Map<string, object=""> map = new HashMap<>();
//        //COOKIE不存在：解析验证正确性
//        try {
//                OAuthTokenManager oAuthTokenManager = OAuthTokenManager.getInstance();
//                Claims claims = oAuthTokenManager.validateToken(accessToken);
//                if (claims != null ) {
//                    map.put("state","success");
//                    map.put("loginAuth","采用Token登录");
//                    int validMillis = (int)(claims.getExpiration().getTime()-System.currentTimeMillis());
//                    if(validMillis > 0) {
//                        //交給容器管理，可以存放redis，這裡模擬是cookie
//                        Cookie cookie = new Cookie(MD5Util.MD5Encode("MD5SING", "UTF-8").toUpperCase(), accessToken);
//                        cookie.setMaxAge(validMillis/1000);
//                        response.addCookie(cookie);
//                    }
//
//                }else{
//                    map.put("state","fail");
//                }
//            }catch (MalformedJwtException | SignatureException e){
//                     map.put("state","signature");//改造簽名，或者無效的Token
//                     map.put("loginAuth","該Token無效");//改造簽名，或者無效的Token
//            }catch (ExpiredJwtException e){
//                     map.put("state","expired");//改造簽名，或者無效的Token
//                     map.put("loginAuth","Token已經過時");
//            }catch (Exception e) {
//            e.printStackTrace();
//            map.put("state","fail");
//            }
//            return map;
//    }
//
//    @RequestMapping(params = "index",method = RequestMethod.GET)
//    @ResponseBody
//    public Object index (HttpServletRequest request, HttpServletResponse response){
//        Map<string, object=""> map = new HashMap<>();
//        //从COOKIE中查找，模拟访问，可以集成容器管理
//        Cookie[] cookies = request.getCookies();
//        if (cookies!=null) {
//            for (int i = cookies.length-1; i >= 0; i--) {
//                Cookie cookie = cookies[i];
//                if (cookie.getName().equals(MD5Util.MD5Encode("MD5SING", "UTF-8").toUpperCase())) {
//                    //跳过登陆
//                    map.put("index","采用Redis登录");
//                    return map;
//                }
//            }
//        }
//        map.put("index","你的Token已经销毁");
//        return map;
//    }
//
//}