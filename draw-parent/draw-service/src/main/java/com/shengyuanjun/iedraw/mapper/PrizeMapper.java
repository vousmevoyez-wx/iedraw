package com.shengyuanjun.iedraw.mapper;

import com.shengyuanjun.iedraw.domain.Prize;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shengyuanjun.iedraw.query.PrizeQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 奖品表 Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2019-08-06
 */
public interface PrizeMapper extends BaseMapper<Prize> {

    List<Prize> selectPrize();

    int deleteByPrimaryKey(Long id);

    Integer insert(Prize record);

    int insertSelective(Prize record);

    Prize selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Prize record);

    int updateByPrimaryKey(Prize record);

    List<Prize> selectCouponsByQuery(PrizeQuery query);

    ArrayList<Prize> selectAll();
}
