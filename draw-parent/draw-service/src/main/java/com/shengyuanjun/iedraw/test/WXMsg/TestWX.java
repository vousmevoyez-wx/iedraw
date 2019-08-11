package com.shengyuanjun.iedraw.test.WXMsg;

/**
 * @program: returnmsg20190804
 * @description:
 * @author: gq544
 * @create: 2019-08-04 11:07
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.shengyuanjun.iedraw.util.msgutil.TemplateData;
import com.shengyuanjun.iedraw.util.msgutil.WX_TemplateMsgUtil;
import com.shengyuanjun.iedraw.util.msgutil.WX_UserUtil;

import java.util.HashMap;
import java.util.Map;


public class TestWX {
    public static void main(String[] args) {
        senMsgActive("oJ0mVw_iFy01cJBS76VQigr9ljWw","24_Z0UhtkTX3M4KmJu2vIsJSxTydLzjZwSJH5KEeO2LUGu76VzhVjJMbjBpofCLwICYA71WbcJ-h4XVs2IDCvvzjV-qba9_W3BGjFP0T_b_gjRXz6iG5OVtkHCvjMi1qSNqs4oDQPpQleVvpHYIBPKaAFAPDY");
    }

    public static String senMsgActive(String openId,String accesstoken){
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
            param.put("first",new TemplateData("恭喜用户！","#696969"));
            param.put("keyword1",new TemplateData("12345345456","#696969"));
            param.put("keyword2",new TemplateData("2017年","#696969"));
            param.put("remark",new TemplateData("祝愉快！","#696969"));
            //注册的微信-模板Id
            // String regTempId =  WX_TemplateMsgUtil.getWXTemplateMsgId("ywBb70467vr18");

            JSON.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            //调用发送微信消息给用户的接口
            System.out.println("即将发送");
            value = WX_TemplateMsgUtil.sendWechatMsgToUser(openId,"B7dRDYun-kz0kiVoS1ZkiyX1kGJRmu_d_2wQS162Js8","http://192.168.0.54:8080/",
                    "#000000", jsonObject,accesstoken);

        }

        return value;
    }

}