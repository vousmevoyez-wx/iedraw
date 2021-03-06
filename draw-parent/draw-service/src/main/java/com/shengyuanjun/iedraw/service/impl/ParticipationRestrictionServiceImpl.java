package com.shengyuanjun.iedraw.service.impl;

import com.shengyuanjun.iedraw.domain.ParticipationRestriction;
import com.shengyuanjun.iedraw.domain.PrizeRecord;
import com.shengyuanjun.iedraw.mapper.ParticipationRestrictionMapper;
import com.shengyuanjun.iedraw.service.IParticipationRestrictionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参与次数的限制及电子围栏 服务实现类
 * </p>
 *
 * @author wx
 * @since 2019-08-07
 */
@Service
public class ParticipationRestrictionServiceImpl extends ServiceImpl<ParticipationRestrictionMapper, ParticipationRestriction> implements IParticipationRestrictionService {

    @Autowired
    private ParticipationRestrictionMapper participationRestrictionMapper;

    public String selectlinkurls() {
        String selectlinkurls = participationRestrictionMapper.selectlinkurls();
        return selectlinkurls;
    }


    public ParticipationRestriction selectParticipationRestrictionById(Long id) {
        return participationRestrictionMapper.selectByPrimaryKey(id);
    }


}
