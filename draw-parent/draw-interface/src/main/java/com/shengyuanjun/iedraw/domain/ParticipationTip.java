package com.shengyuanjun.iedraw.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 超出限制次数的提示
 * </p>
 *
 * @author wx
 * @since 2019-08-03
 */
@TableName("t_participation_tip")
public class ParticipationTip extends Model<ParticipationTip> {

    private static final long serialVersionUID = 1L;

    /**
     * 参与提示表的主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 未中奖的提示
     */
    private String noprize;
    /**
     * 超出每日限制的提示
     */
    private String exceeddaily;
    /**
     * 超出总参与次数的提示
     */
    private String exceedtotal;

    public ParticipationTip() {
    }

    public ParticipationTip(Long id, String noprize, String exceeddaily, String exceedtotal) {
        this.id = id;
        this.noprize = noprize;
        this.exceeddaily = exceeddaily;
        this.exceedtotal = exceedtotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoprize() {
        return noprize;
    }

    public void setNoprize(String noprize) {
        this.noprize = noprize;
    }

    public String getExceeddaily() {
        return exceeddaily;
    }

    public void setExceeddaily(String exceeddaily) {
        this.exceeddaily = exceeddaily;
    }

    public String getExceedtotal() {
        return exceedtotal;
    }

    public void setExceedtotal(String exceedtotal) {
        this.exceedtotal = exceedtotal;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ParticipationTip{" +
        ", id=" + id +
        ", noprize=" + noprize +
        ", exceeddaily=" + exceeddaily +
        ", exceedtotal=" + exceedtotal +
        "}";
    }
}
