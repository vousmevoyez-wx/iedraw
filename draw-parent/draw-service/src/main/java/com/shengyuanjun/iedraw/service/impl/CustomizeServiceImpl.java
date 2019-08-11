package com.shengyuanjun.iedraw.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shengyuanjun.iedraw.PageList;
import com.shengyuanjun.iedraw.domain.Customize;
import com.shengyuanjun.iedraw.mapper.CustomizeMapper;
import com.shengyuanjun.iedraw.query.CustomizeQuery;
import com.shengyuanjun.iedraw.service.ICustomizeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 定制纪录表 服务实现类
 * </p>
 *
 * @author wx
 * @since 2019-08-07
 */
@Service
public class CustomizeServiceImpl extends ServiceImpl<CustomizeMapper, Customize> implements ICustomizeService {

    @Autowired
    private CustomizeMapper customizeMapper;

    public PageList<Customize> selectCustomizePage(CustomizeQuery query) {
        //创建分页对象
        Page<Customize> page = new Page<Customize>(query.getPage(), query.getRows());
        //查询是第一个参数是page，mybatis自动分页
        List<Customize> list = customizeMapper.selectByQuery(page,query);
        //封装Pagelist
        PageList<Customize> pageList = new PageList<Customize>(page.getTotal(), list);
        return pageList;
    }

    @Override
    public int addCustomize(Customize cu) {
        return customizeMapper.insertSelective(cu);
    }

    @Override
    public Customize selectCustomizeById(Long quotationsstatus) {
        return customizeMapper.selectByPrimaryKey(quotationsstatus);
    }
}