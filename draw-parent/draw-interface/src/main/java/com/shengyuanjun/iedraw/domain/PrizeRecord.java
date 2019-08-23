package com.shengyuanjun.iedraw.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * <p>
 * 中奖纪录表
 * </p>
 *
 * @author wx
 * @since 2019-08-06
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
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
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
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Integer isdel;
    /**
     * 创建纪录的时间
     */
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Long createtime;


    private Prize prize;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public Prize getPrize() {
        return prize;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

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

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PrizeRecord{" +
                "id=" + id +
                ", prizeid=" + prizeid +
                ", userid=" + userid +
                ", status=" + status +
                ", type=" + type +
                ", goodscode='" + goodscode + '\'' +
                ", isdel=" + isdel +
                ", createtime=" + createtime +
                ", prize=" + prize +
                '}';
    }
}
