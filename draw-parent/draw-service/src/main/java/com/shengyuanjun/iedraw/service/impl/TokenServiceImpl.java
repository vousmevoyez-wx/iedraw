package com.shengyuanjun.iedraw.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.shengyuanjun.iedraw.controller.PGController;
import com.shengyuanjun.iedraw.domain.AccessToken;
import com.shengyuanjun.iedraw.mapper.AccessTokenMapper;
import com.shengyuanjun.iedraw.service.TokenService;
import com.shengyuanjun.iedraw.test.tokentest.NewTokenTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @program: isfollow
 * @description: 通过访问链接 https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential？appid=APPID&secret=SECRET  获取access_token
 *
 * ******这里的access_token是小程序获取信息的Token，不是网页授权认证的access_token
 *
 * @author: gq544
 * @create: 2019-08-04 10:10
 */

@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger logger = LoggerFactory.getLogger(PGController.class);
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Resource
    private AccessTokenMapper accesstokenMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gzh.appsecret}")
    private String appsecret;

    @Value("${gzh.appid}")
    private String appid;

    @Override
    public String getAccess_token() {
        /*//获取access_token
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + appid + "&secret=" + appsecret;
        if(restTemplate==null){
            restTemplate = new RestTemplate();
        }
        String json = restTemplate.getForObject(url, String.class);
        System.out.println(json);
        JSONObject myJson = JSONObject.parseObject(json);
        return myJson.get("access_token").toString();*/
        return "";
    }




    @Override
    public int updateToken(AccessToken token) {
        logger.info("修改accesstoken中。。。。。");
        return accesstokenMapper.updateByPrimaryKey(token);
    }

    @Override
    public AccessToken getAccessTokenFromDBById(int id) {
        //AccessToken token = accesstokenMapper.selectByPrimaryKey(id);
        AccessToken token = new AccessToken();
        token.setAccesstoken(NewTokenTester.getaccessToken());
        return token;
    }

}