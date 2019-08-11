package com.shengyuanjun.iedraw.service;

import com.shengyuanjun.iedraw.domain.UserInfo;
import net.sf.json.JSONObject;
/**
 * @program: isfollow
 * @description: 测试类
 * @author: gq544
 * @create: 2019-08-04 10:03
 */
public interface UserInfoService {
    //通过code获取openid
    public String getOpenid(String code);

    public boolean judgeIsFollow(String token, String openid);

    public JSONObject getSNSUserInfo(String token, String openid);


    int saveUserInfomsg(UserInfo user);

    UserInfo selectThisUser(String openid);

    UserInfo  selectUserById(Long id);
}