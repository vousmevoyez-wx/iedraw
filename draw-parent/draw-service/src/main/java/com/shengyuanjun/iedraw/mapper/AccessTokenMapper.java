package com.shengyuanjun.iedraw.mapper;


import com.shengyuanjun.iedraw.domain.AccessToken;

public interface AccessTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccessToken record);

    int insertSelective(AccessToken record);

    AccessToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccessToken record);

    int updateByPrimaryKey(AccessToken record);
}