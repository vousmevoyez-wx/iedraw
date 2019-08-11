package com.shengyuanjun.iedraw.util;

import com.shengyuanjun.iedraw.controller.PGController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: gzher
 * @description:  通过MessageFrom的msgFrom方法判断该请求是否来自元微信端
 * @author: gq544
 * @create: 2019-08-05 10:12
 */

public class MessageFrom {

    private static final Logger logger = LoggerFactory.getLogger(PGController.class);

    public static boolean msgFrom(HttpServletRequest request){
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if(userAgent.indexOf("micromessenger")>-1){//微信客户端
            logger.info("来自微信端的请求。。。");
            return true;
        }else{
            logger.info("来自非微信端的请求。。。");
            return  false;
        }
    }
}