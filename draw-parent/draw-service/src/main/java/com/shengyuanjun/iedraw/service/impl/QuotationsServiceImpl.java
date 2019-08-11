package com.shengyuanjun.iedraw.service.impl;

import com.shengyuanjun.iedraw.domain.Quotations;
import com.shengyuanjun.iedraw.mapper.QuotationsMapper;
import com.shengyuanjun.iedraw.service.IQuotationsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wx
 * @since 2019-08-08
 */
@Service
public class QuotationsServiceImpl extends ServiceImpl<QuotationsMapper, Quotations> implements IQuotationsService {
    @Resource
    private QuotationsMapper quotationsMapper;

    @Override
    public List<Quotations> selectQuotationsByStatus(Long statu) {
        return quotationsMapper.selectByStatus(statu);
    }
}
