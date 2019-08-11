package com.shengyuanjun.iedraw.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.shengyuanjun.iedraw.PageList;
import com.shengyuanjun.iedraw.domain.WechatUserInfo;
import com.shengyuanjun.iedraw.mapper.WechatUserInfoMapper;
import com.shengyuanjun.iedraw.query.WechatUserInfoQuery;
import com.shengyuanjun.iedraw.service.IWechatUserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 微信用户的基本信息 服务实现类
 * </p>
 *
 * @author wx
 * @since 2019-08-08
 */
@Service
public class WechatUserInfoServiceImpl extends ServiceImpl<WechatUserInfoMapper, WechatUserInfo> implements IWechatUserInfoService {

    @Autowired
    private WechatUserInfoMapper wechatUserInfoMapper;

    @Override
    public PageList<WechatUserInfo> selectByQuery(WechatUserInfoQuery query) {

        Page<WechatUserInfo> page = new Page<>(query.getPage(),query.getRows());
        List<WechatUserInfo> list = wechatUserInfoMapper.selectByQuery(page,query);
        PageList<WechatUserInfo> pageList = new PageList<>(page.getTotal(), list);
        return pageList;
    }

}
