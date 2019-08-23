package com.shengyuanjun.iedraw.mapper;

import com.shengyuanjun.iedraw.domain.Tokenuser;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2019-08-16
 */
public interface TokenuserMapper extends BaseMapper<Tokenuser> {
    List<Tokenuser> selectByAppId(Tokenuser tokenuser);

    Tokenuser selectByToken(String token);
}
