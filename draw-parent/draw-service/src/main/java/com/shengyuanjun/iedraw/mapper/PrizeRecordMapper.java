package com.shengyuanjun.iedraw.mapper;

import com.shengyuanjun.iedraw.domain.PrizeRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 中奖纪录表 Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2019-08-06
 */
public interface PrizeRecordMapper extends BaseMapper<PrizeRecord> {

    /**
     * 查询纪录中重复的数据
     * @return
     */
    List<PrizeRecord> selectRepeat(Long userid);

    int deleteByPrimaryKey(Long id);

    Integer insert(PrizeRecord record);

    int insertSelective(PrizeRecord record);

    PrizeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PrizeRecord record);

    int updateByPrimaryKey(PrizeRecord record);

    PrizeRecord selectGetNumber(PrizeRecord record);

    List<PrizeRecord> selectall();

    //中奖表和prize表连表查询，通过userid查询某个人中奖记录
    List<PrizeRecord> showmine(Long id);

    //中奖表和prize表连表查询
    List<PrizeRecord> showpzr(Long id);

    List<PrizeRecord> showAll();
}