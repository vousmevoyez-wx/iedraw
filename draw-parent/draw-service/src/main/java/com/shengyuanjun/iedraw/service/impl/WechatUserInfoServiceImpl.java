package com.shengyuanjun.iedraw.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shengyuanjun.iedraw.domain.WechatUserInfo;
import com.shengyuanjun.iedraw.mapper.WechatUserInfoMapper;
import com.shengyuanjun.iedraw.service.IWechatUserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信用户的基本信息 服务实现类
 * </p>
 *
 * @author wx
 * @since 2019-08-02
 */
@Service
public class WechatUserInfoServiceImpl extends ServiceImpl<WechatUserInfoMapper, WechatUserInfo> implements IWechatUserInfoService {

}