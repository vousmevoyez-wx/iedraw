package com.shengyuanjun.iedraw.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 中奖纪录表
 * </p>
 *
 * @author wx
 * @since 2019-08-03
 */
@TableName("t_prize_record")
public class PrizeRecord extends Model<PrizeRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 中奖纪录的主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 奖品id
     */
    private Long prizeid;
    /**
     * 中奖人的id
     */
    private Long userid;
    /**
     * 0为未领取，1为以领取
     */
    private Integer status;
    /**
     * 奖品的类型
     */
    private Integer type;
    /**
     * 二维码和提货码
     */
    private String goodscode;
    /**
     * 0为删除，1为正常
     */
    private Integer isdel;
    /**
     * 创建纪录的时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createtime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrizeid() {
        return prizeid;
    }

    public void setPrizeid(Long prizeid) {
        this.prizeid = prizeid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGoodscode() {
        return goodscode;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PrizeRecord{" +
        ", id=" + id +
        ", prizeid=" + prizeid +
        ", userid=" + userid +
        ", status=" + status +
        ", type=" + type +
        ", goodscode=" + goodscode +
        ", isdel=" + isdel +
        ", createtime=" + createtime +
        "}";
    }
}
