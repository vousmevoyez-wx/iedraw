package com.shengyuanjun.iedraw.util.msgutil;

/**
 * @program: returnmsg20190804
 * @description: 根据微信openId 获取用户是否订阅
 * @author: gq544
 * @create: 2019-08-04 11:06
 */

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WX_UserUtil {
    private static Logger log = LoggerFactory.getLogger(WX_UserUtil.class);
    /**
     * 根据微信openId 获取用户是否订阅
     * @param openId 微信openId
     * @return 是否订阅该公众号标识
     */
    public static Integer subscribeState(String openId,String accesstoken){
        log.info("查看是否订阅。。。。 openid = "+openId);
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+ accesstoken +"&openid="+openId;
        JSONObject result = WX_HttpsUtil.httpsRequest(tmpurl, "GET",null);
        JSONObject resultJson = new JSONObject(result);
        log.error("获取用户是否订阅 errcode:{} errmsg:{}", resultJson.getInteger("errcode"), resultJson.getString("errmsg"));
        String errmsg = (String) resultJson.get("errmsg");
        System.out.println("结果为 ： "+resultJson);
        if(resultJson.toString().contains("access_token is invalid or not latest hint")){
            //检测到token过期的时候提醒更新token
            return 2;
        }
        if(errmsg==null){
            //用户是否订阅该公众号标识（0代表此用户没有关注该公众号 1表示关注了该公众号）。
            Integer subscribe = (Integer) resultJson.get("subscribe");
            return subscribe;
        }
        return -1;
    }
}