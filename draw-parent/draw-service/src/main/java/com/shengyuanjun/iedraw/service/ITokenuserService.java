package com.shengyuanjun.iedraw.service;

import com.shengyuanjun.iedraw.PageList;
import com.shengyuanjun.iedraw.domain.Quotations;
import com.shengyuanjun.iedraw.domain.Tokenuser;
import com.baomidou.mybatisplus.service.IService;
import com.shengyuanjun.iedraw.domain.WechatUserInfo;
import com.shengyuanjun.iedraw.query.WechatUserInfoQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wx
 * @since 2019-08-16
 */
public interface ITokenuserService extends IService<Tokenuser> {
    List<Tokenuser> selectByAppId(Tokenuser tokenuser);

    Tokenuser selectByToken(String token);
}
