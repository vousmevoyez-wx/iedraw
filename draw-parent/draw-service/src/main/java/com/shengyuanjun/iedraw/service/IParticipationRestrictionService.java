package com.shengyuanjun.iedraw.service;

import com.shengyuanjun.iedraw.domain.ParticipationRestriction;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 参与次数的限制及电子围栏 服务类
 * </p>
 *
 * @author wx
 * @since 2019-08-07
 */
public interface IParticipationRestrictionService extends IService<ParticipationRestriction> {



    public ParticipationRestriction selectParticipationRestrictionById(Long id);

}
