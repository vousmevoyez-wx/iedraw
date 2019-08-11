package com.shengyuanjun.iedraw.mapper;

import com.shengyuanjun.iedraw.domain.ParticipationRestriction;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 参与次数的限制及电子围栏 Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2019-08-07
 */
public interface ParticipationRestrictionMapper extends BaseMapper<ParticipationRestriction> {

    String selectlinkurls();

    ParticipationRestriction selectByPrimaryKey(Long id);


    int deleteByPrimaryKey(Long id);

    Integer insert(ParticipationRestriction record);

    int insertSelective(ParticipationRestriction record);


    int updateByPrimaryKeySelective(ParticipationRestriction record);

    int updateByPrimaryKey(ParticipationRestriction record);
}
