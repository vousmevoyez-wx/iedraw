package com.shengyuanjun.iedraw.test.Code.util;

import net.sf.json.JSONObject;

/**
 * @program: gzher
 * @description:
 * @author: gq544
 * @create: 2019-08-05 11:59
 */
public class Test {
    public void asdsd(){
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
        String jsonString = JSONObject.fromObject("ASDASD").toString().replace("day", "Day");
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
        System.out.println(jsonObject.toString());
    }
}