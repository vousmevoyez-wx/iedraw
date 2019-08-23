package com.shengyuanjun.iedraw.service.impl;

import com.shengyuanjun.iedraw.domain.ParticipationRestriction;
import com.shengyuanjun.iedraw.domain.Prize;
import com.shengyuanjun.iedraw.mapper.ParticipationRestrictionMapper;
import com.shengyuanjun.iedraw.mapper.PrizeMapper;
import com.shengyuanjun.iedraw.query.PrizeQuery;
import com.shengyuanjun.iedraw.service.IPrizeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 奖品表 服务实现类
 * </p>
 *
 * @author wx
 * @since 2019-08-06
 */
@Service
public class PrizeServiceImpl extends ServiceImpl<PrizeMapper, Prize> implements IPrizeService {

    @Autowired
    private PrizeMapper prizeMapper;

    @Autowired
    private ParticipationRestrictionMapper participationRestrictionMapper;

    /**
     *  h5的奖品列表 优惠券
     * @return
     */
    public List<Prize> selectPrize() {
        List<Prize> prizes = prizeMapper.selectPrize();
        Prize prize = new Prize();
        String prizename = prize.getPrizename();
        prize.setPrizename(prizename);
        String linkurl = participationRestrictionMapper.selectlinkurls();
        ParticipationRestriction participationRestriction = new ParticipationRestriction();
        participationRestriction.setLinkurls(linkurl);
        for (Prize prize1 : prizes) {
           prizename = prize1.getPrizename();
        }
        prizes.add(prize);
        return prizes;
    }

    public Prize selectPrizeById(Long id) {
        return prizeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Prize> getCouponsByQuery(PrizeQuery query) {
        return prizeMapper.selectCouponsByQuery(query);
    }


    @Override
    public ArrayList<Prize> selectAllPrize() {
        return prizeMapper.selectAll();
    }

    @Override
    public int updateStockByPrizeWinner(Prize p) {

        return prizeMapper.updateByPrimaryKeySelective(p);
    }

}