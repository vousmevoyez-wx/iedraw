package com.shengyuanjun.iedraw.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.shengyuanjun.iedraw.PageList;
import com.shengyuanjun.iedraw.domain.Customize;
import com.baomidou.mybatisplus.service.IService;
import com.shengyuanjun.iedraw.query.CustomizeQuery;

/**
 * <p>
 * 定制纪录表 服务类
 * </p>
 *
 * @author wx
 * @since 2019-08-07
 */
public interface ICustomizeService extends IService<Customize> {

    PageList<Customize> selectCustomizePage(CustomizeQuery query);

}