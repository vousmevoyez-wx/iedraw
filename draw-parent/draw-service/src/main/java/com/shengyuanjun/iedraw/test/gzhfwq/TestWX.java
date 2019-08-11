package com.shengyuanjun.iedraw.test.gzhfwq;

/**
 * @program: returnmsg20190804
 * @description:
 * @author: gq544
 * @create: 2019-08-04 11:07
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class TestWX {

    public static String senMsg(String openId,String accesstoken){
        //用户是否订阅该公众号标识 (1表示关注了该公众号)
        int  state = WX_UserUtil.subscribeState(openId,accesstoken);
        System.out.println("mtr  accesstoke = "+accesstoken);
        System.out.println("openid:"+openId);
        System.out.println("state:"+state);
        // 绑定了微信并且关注了服务号的用户 , 注册成功-推送注册短信
        String value = "";
        if(state==1){
            System.out.println("测得已关注");
            Map<String, TemplateData> param = new HashMap<>();
            param.put("keyword1",new TemplateData("1000001","#696969"));
            param.put("keyword2",new TemplateData("特等奖品一套","#696969"));
            param.put("keyword3",new TemplateData("2019年9月9日","#696969"));
            param.put("keyword4",new TemplateData("祝愉快！","#696969"));
            //注册的微信-模板Id
            // String regTempId =  WX_TemplateMsgUtil.getWXTemplateMsgId("ywBb70467vr18");

            JSON.toJSONString(param);
            System.out.println("openid="+openId);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            //调用发送微信消息给用户的接口
            System.out.println("即将发送");
            value = WX_TemplateMsgUtil.sendWechatMsgToUser(openId,"B7dRDYun-kz0kiVoS1ZkiyX1kGJRmu_d_2wQS162Js8", "http://www.baidu.com",
                    "#000000", jsonObject,accesstoken);
/*
            //获取公众号的自动回复规则
            String urlinfo="https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token="+ Finaler.ACCESSTOKEN;
            JSONObject joinfo = WX_HttpsUtil.httpsRequest(urlinfo, "GET", null);
            Object o=joinfo.get("is_add_friend_reply_open");
            // System.out.println("o:"+joinfo);
            String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
            JSONObject Token = WX_HttpsUtil.httpsRequest(getTokenUrl, "GET", null);
            System.out.println("Token:"+Token);*/
        }

        return value;
    }

}