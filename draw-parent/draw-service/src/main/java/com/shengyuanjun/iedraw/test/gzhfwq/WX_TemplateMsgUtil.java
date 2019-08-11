package com.shengyuanjun.iedraw.test.gzhfwq;

/**
 * @program: returnmsg20190804
 * @description: 封装模板详细信息
 * @author: gq544
 * @create: 2019-08-04 11:00
 */

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WX_TemplateMsgUtil {

    private static Logger log = LoggerFactory.getLogger(WX_TemplateMsgUtil.class);


    /**
     * 发送微信消息(模板消息)
     * @param touser 用户 OpenID
     * @param templatId 模板消息ID
     * @param clickurl URL置空，则在发送后，点击模板消息会进入一个空白页面（ios），或无法点击（android）。
     * @param topcolor 标题颜色
     * @param data 详细内容
     * @return
     */
    public static String sendWechatMsgToUser(String touser, String templatId, String clickurl, String topcolor, JSONObject data,String accesstoken) {
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+ accesstoken;
        JSONObject json = new JSONObject();
        json.put("touser", touser);
        json.put("template_id", templatId);
        json.put("url", clickurl);
        json.put("topcolor", topcolor);
        json.put("data", data);
        try{
            System.out.println("openid = "+ touser);
            JSONObject result = WX_HttpsUtil.httpsRequest(tmpurl, "POST", json.toString());
            JSONObject resultJson = new JSONObject(result);
            log.info("发送微信消息返回信息：" + resultJson.get("errcode"));
            String errmsg = (String) resultJson.get("errmsg");
            if(!"ok".equals(errmsg)){  //如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
                return "error";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "error";
        }finally {
            if(templatId!=null) {
                //删除新增的 微信模板
                //deleteWXTemplateMsgById(templatId);
            }
        }
        return "success";
    }

}