package com.shengyuanjun.iedraw.service;

import com.baomidou.mybatisplus.service.IService;
import com.shengyuanjun.iedraw.PageList;
import com.shengyuanjun.iedraw.domain.WechatUserInfo;
import com.shengyuanjun.iedraw.query.WechatUserInfoQuery;

/**
 * <p>
 * 微信用户的基本信息 服务类
 * </p>
 *
 * @author wx
 * @since 2019-08-08
 */
public interface IWechatUserInfoService extends IService<WechatUserInfo> {

    PageList<WechatUserInfo> selectByQuery(WechatUserInfoQuery query);
}
