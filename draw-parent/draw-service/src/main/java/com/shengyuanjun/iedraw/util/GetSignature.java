package com.shengyuanjun.iedraw.util;

import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Admin
 * @version V1.0
 * @className GetSignature
 * @description TODO
 * @date 2019/8/9
 **/


public class GetSignature {


    /**
     * 先获取access_token
     * @param appId
     * @param appSecret
     * @return
     *//*
    public static String getAccessToken(String appId , String appSecret){
        // 网页授权接口
        String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;

        HttpClient client = null;
        String access_token = null;
        int expires_in = 0;
        try {
            client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(GetPageAccessTokenUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(httpget, responseHandler);
            System.out.println("==============================="+response);
            JSONObject OpenidJSONO = JSONObject.fromObject(response);
            System.out.println("==============================="+OpenidJSONO);
            access_token = String.valueOf(OpenidJSONO.get("access_token"));//获取access_token
            System.out.println("==============================="+access_token);
            expires_in = Integer.parseInt(String.valueOf(OpenidJSONO.get("expires_in")));//获取时间
        } catch (Exception e) {
            throw new RuntimeException("获取AccessToken出错！");//CommonRuntimeException
        } finally {
            client.getConnectionManager().shutdown();
        }
        return access_token;
    }*/

    /**
     * 获取jsapi_ticket
     * @param accessToken
     * @return
     */
    public static String getTicket(String accessToken) {
        // 网页授权接口
        String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
        HttpClient client = null;
        String ticket = "";
        int expires_in = 0;
        System.out.println("accesstoken = " + accessToken);
        try {
            client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(GetPageAccessTokenUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(httpget, responseHandler);

            System.out.println("+++++++++++++++++++"+response);

            JSONObject OpenidJSONO = JSONObject.fromObject(response);

            System.out.println("------------------"+ OpenidJSONO);
            ticket = String.valueOf(OpenidJSONO.get("ticket"));//获取ticket
            expires_in = Integer.parseInt(String.valueOf(OpenidJSONO.get("expires_in")));//获取时间
        } catch (Exception e) {
            throw new RuntimeException("获取Ticket出错！");//CommonRuntimeException
        } finally {
            client.getConnectionManager().shutdown();
        }

        return ticket;
    }

    /**
     * SHA1加密，参数是由url、jsapi_ticket、noncestr、timestamp组合而成
     * @param str
     * @return
     */
    public static String SHA1(String str) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 Signature
     * @param url
     * @return
     */
    public static Map<String,String> getSignature(String appid,String url,String accesstoken) {

        Map<String,String> map = new HashMap<>();
        String signature = "";

        //String appid = "wxaf662218893cdffa";//微信公众号的appid
        //String appsecret = "9f0ab14aa8d9491a9e30453813a1ee6d";//微信公众号的appsecret

        //获取noncestr
        String noncestr = UUID.randomUUID().toString();
        //获取timestamp
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        //获取access_token
        //String access_token = getAccessToken(appid , appsecret);
        System.out.println("access_token"+accesstoken);
        //获取jspai_ticket
        String jsapi_ticket = getTicket(accesstoken);
        //将四个数据进行组合，传给SHA1进行加密
        //这里有个坑，页面是nonceStr，但是签名的字段是noncestr，注意大小写
        String str = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println("str = "+str);
        //sha1加密
        signature = SHA1(str);
        System.out.println("signature1 = "+ signature );
        map.put("jsapi_ticket",jsapi_ticket);
        map.put("appid",appid);
        map.put("nonceStr",noncestr);
        map.put("timestamp",timestamp);
        map.put("signature",signature);
        map.put("url",url);

        return map;
    }

    public static Map<String,String> getSignatureFromMap(String url,String accesstoken) {
        Map<String,String> map = new HashMap<>();
        String signature = "";


       // String appid = "wxaf662218893cdffa";//微信公众号的appid
        //String appsecret = "9f0ab14aa8d9491a9e30453813a1ee6d";//微信公众号的appsecret

        //获取noncestr
        String noncestr = UUID.randomUUID().toString();
        //获取timestamp
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        //获取access_token
        //String access_token = getAccessToken(appid , appsecret);

        //获取jspai_ticket
        String jsapi_ticket = getTicket(accesstoken);
        //将四个数据进行组合，传给SHA1进行加密
        //这里有个坑，页面是nonceStr，但是签名的字段是noncestr，注意大小写
        //sha1加密
        map.put("jsapi_ticket",jsapi_ticket);
        map.put("nonceStr",noncestr);
        map.put("timestamp",timestamp);
        map.put("url",url);
        return map;
    }
}
