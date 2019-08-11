package com.shengyuanjun.iedraw.controller;



import com.shengyuanjun.iedraw.test.gzhfwq.TestWX;
import com.shengyuanjun.iedraw.test.gzhfwq.utils.HttpGetUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: 公众号发送推送消息
 * @description:
 * @author: gq544
 * @create: 2019-08-09 00:00
 */

@RestController
@RequestMapping("/smsg")
public class SendMsgController {
    @RequestMapping("/te")
    public void senemsg(String openid){
        String accesstoken = "24_kKUEIJ3Ni3GtfAujvIsJSxTydLzjZwSJH5KEeD2evIqSsf0OXF5dK67gB8LcZWwBewNX5Yx7GUE2ynbxCgLxIr8kGpw4cz1lM4DXxQ5vVtr2JQrCoY2nM_zbzjcZCtsPLDVwtnXUPgJeZlpzNCGgAAAUJN";
        System.out.println("openid = "+openid);
        System.out.println("ctr  accesstoke = "+accesstoken);
        String value = TestWX.senMsg(openid, accesstoken);
        Map map = new HashMap();
        HttpGetUtil.httpRequestToString("https://www.qvbnnm.xyz/a/test",map);
    }
}