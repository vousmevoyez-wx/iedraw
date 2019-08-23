package com.shengyuanjun.iedraw.service;

import com.shengyuanjun.iedraw.domain.AccessToken;
import org.springframework.stereotype.Service;

/**
 * @program: isfollow
 * @description: 通过访问链接 https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential？appid=APPID&secret=SECRET  获取access_token
 *
 * ******这里的access_token是小程序获取信息的Token，不是网页授权认证的access_token
 *
 * @author: gq544
 * @create: 2019-08-04 10:10
 */

public interface TokenService {
    public String getAccess_token() ;
    public int updateToken(AccessToken token);
    //通过id取access_token值
    AccessToken getAccessTokenFromDBById(int i);
}