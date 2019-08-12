package com.shengyuanjun.iedraw.service;

import com.shengyuanjun.iedraw.domain.Prize;
import com.baomidou.mybatisplus.service.IService;
import com.shengyuanjun.iedraw.query.PrizeQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 奖品表 服务类
 * </p>
 *
 * @author wx
 * @since 2019-08-06
 */
public interface IPrizeService extends IService<Prize> {

    /**
     *  查询H5页面中的奖品
     * @return
     */
    List<Prize> selectPrize();

    Prize selectPrizeById(Long id);

    List<Prize> getCouponsByQuery(PrizeQuery query);

    ArrayList<Prize> selectAllPrize();


    int updateStockByPrizeWinner(Prize p);
}
