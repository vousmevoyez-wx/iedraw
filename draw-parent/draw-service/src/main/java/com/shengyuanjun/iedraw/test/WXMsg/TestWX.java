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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TestWX {
    public static void main(String[] args) {
        //senMsgActive("oJ0mVw_iFy01cJBS76VQigr9ljWw","24_Z0UhtkTX3M4KmJu2vIsJSxTydLzjZwSJH5KEeO2LUGu76VzhVjJMbjBpofCLwICYA71WbcJ-h4XVs2IDCvvzjV-qba9_W3BGjFP0T_b_gjRXz6iG5OVtkHCvjMi1qSNqs4oDQPpQleVvpHYIBPKaAFAPDY");
       /* DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        System.out.println("time = " + time);*/
    }

    @Async
    public   String senMsgActive(String openId,String accesstoken,String prizeName){
        try {
            Thread.sleep(5500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String time = format.format(new Date());

        // 绑定了微信并且关注了服务号的用户 , 注册成功-推送注册短信
        String value = "";
            Map<String, TemplateData> param = new HashMap<>();
            param.put("first",new TemplateData("恭喜您中奖了！","#696969"));
            param.put("keyword1",new TemplateData("智博会“江小白”抽奖活动","#696969"));
            param.put("keyword2",new TemplateData(prizeName,"#696969"));
            param.put("keyword3",new TemplateData(time,"#696969"));
            param.put("remark",new TemplateData("点击详情查看兑换信息，如有疑问请咨询现场工作人员","#696969"));
            //注册的微信-模板Id
            // String regTempId =  WX_TemplateMsgUtil.getWXTemplateMsgId("ywBb70467vr18");
            JSON.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            //调用发送微信消息给用户的接口
            value = WX_TemplateMsgUtil.sendWechatMsgToUser(openId,"h91_CW8IPpli8DQaKcF2cquGoNMvFiLzG_1zT_4FPb4","http://jxb-zbh.jxbscbd.com",
                    "#000000", jsonObject,accesstoken);

        return "state";
    }

}