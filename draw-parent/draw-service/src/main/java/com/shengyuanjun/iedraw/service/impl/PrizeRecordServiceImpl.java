package com.shengyuanjun.iedraw.service.impl;

import com.shengyuanjun.iedraw.domain.PrizeRecord;
import com.shengyuanjun.iedraw.mapper.ParticipationRestrictionMapper;
import com.shengyuanjun.iedraw.mapper.PrizeMapper;
import com.shengyuanjun.iedraw.mapper.PrizeRecordMapper;
import com.shengyuanjun.iedraw.service.IPrizeRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 中奖纪录表 服务实现类
 * </p>
 *
 * @author wx
 * @since 2019-08-06
 */
@Service
public class PrizeRecordServiceImpl extends ServiceImpl<PrizeRecordMapper, PrizeRecord> implements IPrizeRecordService {

    @Autowired
    private PrizeRecordMapper prizeRecordMapper;

    public List<PrizeRecord> selectRepeat(Long userid) {
        return prizeRecordMapper.selectRepeat(userid);
    }

    @Override
    public List<PrizeRecord> selectWinTimesDaily() {

        return prizeRecordMapper.selectall();
    }

    @Override
    public int savePrizeRecord(PrizeRecord record) {
        return prizeRecordMapper.insert(record);
    }

    @Override
    public PrizeRecord getPrizeRecordById(Long id) {
        return prizeRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public PrizeRecord selectGetNumber(PrizeRecord record) {
        return prizeRecordMapper.selectGetNumber(record);
    }
    @Override
    public int savePrizeRecordQRCode(PrizeRecord p) {
        return prizeRecordMapper.updateByPrimaryKeySelective(p);
    }


    @Override
    public int updateBycode(PrizeRecord pzr) {
        return prizeRecordMapper.updateByPrimaryKeySelective(pzr);
    }


    @Override
    public List<PrizeRecord> selectTwoTableByUserId(Long id) {
        return prizeRecordMapper.showmine(id);
    }

    @Override
    public List<PrizeRecord> showpzr(Long id) {
        return prizeRecordMapper.showpzr(id);
    }

    @Override
    public int updateStatus(PrizeRecord pr) {
        return prizeRecordMapper.updateByPrimaryKeySelective(pr);
    }


    //查看仅仅记录

    //查询抽奖记录情况
    @Override
    public List<PrizeRecord> selectAll() {
        return prizeRecordMapper.showAll();
    }

}