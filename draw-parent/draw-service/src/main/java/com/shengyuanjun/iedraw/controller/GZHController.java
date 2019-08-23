package com.shengyuanjun.iedraw.controller;


import com.shengyuanjun.iedraw.service.IQuotationsService;
import com.shengyuanjun.iedraw.service.TokenService;
import com.shengyuanjun.iedraw.service.UserInfoService;
import com.shengyuanjun.iedraw.test.WXMsg.TestWX;
import com.shengyuanjun.iedraw.util.NameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * @program: participate
 * @description: 公众号Controller类
 * @author: gq544
 * @create: 2019-08-08 13:06
 */
@RestController
@PropertySource("classpath:constants/constant.properties")
@RequestMapping("/gzhs")
public class GZHController {

    private static final Logger logger = LoggerFactory.getLogger(GZHController.class);



    @Resource
    private IQuotationsService quotationsServiceImpl;
    //获取用户信息
    @Resource
    private UserInfoService userinfoServiceImpl;

    @Resource
    private TokenService tokenServiceImpl;



   /* @RequestMapping("/msgsend")
    public String senemsg(String openid){
        String accesstoken = tokenServiceImpl.getAccessTokenFromDBById(1).getAccesstoken();
        System.out.println("openid = "+openid);
        System.out.println("ctr  accesstoke = "+accesstoken);
        TestWX.senMsgActive(openid, accesstoken,"");
        //如果返回值为2需要更新token
        return "";
    }*/

    public static void main(String[] args) {
        String number = NameUtil.genNumberStr(6);
        System.out.println( "number = " + number);
    }
}