package com.shengyuanjun.iedraw.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.shengyuanjun.iedraw.domain.WechatUserInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shengyuanjun.iedraw.query.WechatUserInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 微信用户的基本信息 Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2019-08-08
 */
public interface WechatUserInfoMapper extends BaseMapper<WechatUserInfo> {

    List<WechatUserInfo> selectByQuery(Page<WechatUserInfo> page, @Param("wq") WechatUserInfoQuery query);

}
