package com.shengyuanjun.iedraw.service.impl;

import com.shengyuanjun.iedraw.domain.ActivityTime;
import com.shengyuanjun.iedraw.mapper.ActivityTimeMapper;
import com.shengyuanjun.iedraw.service.IActivityTimeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wx
 * @since 2019-08-06
 */
@Service
public class ActivityTimeServiceImpl extends ServiceImpl<ActivityTimeMapper, ActivityTime> implements IActivityTimeService {

    @Autowired
    private ActivityTimeMapper activityTimeMapper;

    @Override
    public void updateActivityTime(ActivityTime activityTime) {
        activityTimeMapper.updateActivityTime(activityTime);
    }
}
