package com.shengyuanjun.iedraw.mapper;

import com.shengyuanjun.iedraw.domain.Quotations;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2019-08-08
 */
public interface QuotationsMapper extends BaseMapper<Quotations> {

    int deleteByPrimaryKey(Long id);

    Integer insert(Quotations record);

    int insertSelective(Quotations record);

    Quotations selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Quotations record);

    int updateByPrimaryKey(Quotations record);

    List<Quotations> selectByStatus(Long quotationsstatus);

}
