package com.shengyuanjun.iedraw.service;

import com.shengyuanjun.iedraw.domain.ActivityTime;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wx
 * @since 2019-08-06
 */
public interface IActivityTimeService extends IService<ActivityTime> {

    void updateActivityTime(ActivityTime activityTime);
}
