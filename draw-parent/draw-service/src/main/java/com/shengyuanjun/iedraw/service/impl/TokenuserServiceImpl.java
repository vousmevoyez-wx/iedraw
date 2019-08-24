package com.shengyuanjun.iedraw.service.impl;

import com.shengyuanjun.iedraw.domain.Tokenuser;
import com.shengyuanjun.iedraw.mapper.TokenuserMapper;
import com.shengyuanjun.iedraw.service.ITokenuserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wx
 * @since 2019-08-16
 */
@Service
public class TokenuserServiceImpl extends ServiceImpl<TokenuserMapper, Tokenuser> implements ITokenuserService {

    @Autowired
    private TokenuserMapper tokenuserMapper;

    @Override
    public List<Tokenuser> selectByAppId(Tokenuser tokenuser) {
        return tokenuserMapper.selectByAppId(tokenuser);
    }

    @Override
    public Tokenuser selectByToken(String token) {
        return tokenuserMapper.selectByToken(token);
    }
}
