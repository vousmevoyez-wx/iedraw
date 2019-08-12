package com.shengyuanjun.iedraw.service;

import com.shengyuanjun.iedraw.domain.PrizeRecord;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 中奖纪录表 服务类
 * </p>
 *
 * @author wx
 * @since 2019-08-06
 */
public interface IPrizeRecordService extends IService<PrizeRecord> {

    /**
     * 查询纪录中重复的数据
     * @return
     */
    List<PrizeRecord> selectRepeat(Long userid);

    public List<PrizeRecord> selectWinTimesDaily() ;

    public int savePrizeRecord(PrizeRecord record) ;


    public PrizeRecord getPrizeRecordById(Long id) ;


    public PrizeRecord selectGetNumber(PrizeRecord record) ;


    public int savePrizeRecordQRCode(PrizeRecord p);

    //用于扫码枪回调函数更改状态
    int updateBycode(PrizeRecord pzr);


    public List<PrizeRecord> selectTwoTableByUserId(Long id);

    public List<PrizeRecord> showpzr(Long id);

    int updateStatus(PrizeRecord pr);
}
