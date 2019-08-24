package com.shengyuanjun.iedraw.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wx
 * @since 2019-08-16
 */
@TableName("t_tokenuser")
public class Tokenuser extends Model<Tokenuser> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户APPID
     */
    private String AppId;
    /**
     * 用户密钥
     */
    private String AppScr;
    private String Token;
    private Date TokenDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String AppId) {
        this.AppId = AppId;
    }

    public String getAppScr() {
        return AppScr;
    }

    public void setAppScr(String AppScr) {
        this.AppScr = AppScr;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public Date getTokenDate() {
        return TokenDate;
    }

    public void setTokenDate(Date TokenDate) {
        this.TokenDate = TokenDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Tokenuser{" +
        ", id=" + id +
        ", AppId=" + AppId +
        ", AppScr=" + AppScr +
        ", Token=" + Token +
        ", TokenDate=" + TokenDate +
        "}";
    }
}
