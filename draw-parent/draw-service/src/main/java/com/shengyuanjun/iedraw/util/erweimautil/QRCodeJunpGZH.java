package com.shengyuanjun.iedraw.util.erweimautil;


import com.shengyuanjun.iedraw.util.postutil.URLConnection;

import java.io.IOException;

/**
 * @program: participate
 * @description: 生成跳转公众号的二维码的类
 * @author: gq544
 * @create: 2019-08-05 20:50
 */

public class QRCodeJunpGZH {

    public static void main(String[] args) {
        getQRCodeJunpGZH();
    }

    public static void getQRCodeJunpGZH() {
        String token = "24_hyDxqMK4XoCcnawQqoXToMj37eGjKuf1aEerquqJRPa2Kt6VPyVUevJx9mESMMzyj3QaaFXXyqd3x3lCBtmNQbLqyQDZqp0w8-bjKIB0XzuJGgFwaGkDaeC2f48pSriELjL68kKnUr1yKG5RTBGhAEAMUZ";
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
        //String jsonString = JSONObject.fromObject("ASDASD").toString().replace("day", "Day");
        //JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", null);
        //String jsonmsg = "{\"expire_seconds\":2592000,\"action_name\": \"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":123}}}";
        String jsonmsg = "{\"expire_seconds\":2592000,\"action_name\": \"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\"mytester\"}}}";

        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonmsg);
        try {
            System.out.println("url = " + url);

            com.alibaba.fastjson.JSONObject json = URLConnection.postResponse(url, jsonObject);
            String ticket = json.getString("ticket");
            System.out.println("value = " + json.toString());
            System.out.println("ticket = " + ticket);
            System.out.println("url = " + json.getString("url"));
            System.out.println("二维码url = https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ ticket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}