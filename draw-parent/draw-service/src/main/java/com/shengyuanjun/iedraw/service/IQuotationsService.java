package com.shengyuanjun.iedraw.service;

import com.shengyuanjun.iedraw.domain.Quotations;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wx
 * @since 2019-08-08
 */
public interface IQuotationsService extends IService<Quotations> {
    List<Quotations> selectQuotationsByStatus(Long statu);

}
