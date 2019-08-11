package com.shengyuanjun.iedraw.util;

import com.shengyuanjun.iedraw.controller.PGController;
import com.shengyuanjun.iedraw.domain.AccessToken;
import com.shengyuanjun.iedraw.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: participate
 * @description:    定时器定时执行保证token有效性,在服务器启动的时候，8秒内执行获取accessToken并且保存入数据库
 * @author: gq544
 * @create: 2019-08-06 09:43
 */


@Component
public class AccessTokenScheduler {


    private static final Logger logger = LoggerFactory.getLogger(PGController.class);


    @Resource
    private TokenService tokenServiceImpl;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //每隔100分钟执行一次
    @Scheduled(initialDelay=3000, fixedRate = 100*60*1000)//每100分钟重新获取一次，accesstoken有效时间7200s,2小时
    public void testTasks() {
        logger.info("定时任务执行时间：" + dateFormat.format(new Date()));
        try {
            logger.info("此处获取access_token并存入数据库。。。。。。。");

           AccessToken token = new AccessToken();
            token.setAccesstoken(tokenServiceImpl.getAccess_token());
            token.setId(1);
            if(tokenServiceImpl.getAccessTokenFromDBById(1) != null){
                tokenServiceImpl.updateToken(token);
            }else{
                  tokenServiceImpl.saveToken(token);
            }

            logger.info("存储结束。。。。。。。。");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}