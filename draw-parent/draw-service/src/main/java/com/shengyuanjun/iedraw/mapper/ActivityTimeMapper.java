package com.shengyuanjun.iedraw.mapper;

import com.shengyuanjun.iedraw.domain.ActivityTime;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2019-08-06
 */
public interface ActivityTimeMapper extends BaseMapper<ActivityTime> {

    void updateActivityTime(ActivityTime activityTime);
}
