package com.shengyuanjun.iedraw.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.shengyuanjun.iedraw.domain.Customize;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shengyuanjun.iedraw.query.CustomizeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 定制纪录表 Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2019-08-07
 */
public interface CustomizeMapper extends BaseMapper<Customize> {

    //将page作为第一个参数，分页关键字查询
    List<Customize> selectByQuery(Page<Customize> page, @Param("cq")CustomizeQuery query);

    int deleteByPrimaryKey(Long id);

    Integer insert(Customize record);

    int insertSelective(Customize record);

    Customize selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Customize record);

    int updateByPrimaryKey(Customize record);

}