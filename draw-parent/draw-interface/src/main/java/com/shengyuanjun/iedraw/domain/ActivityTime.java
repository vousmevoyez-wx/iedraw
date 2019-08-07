package com.shengyuanjun.iedraw.domain;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2019-08-06
 */
@TableName("t_activity_time")
public class ActivityTime extends Model<ActivityTime> {

    private static final long serialVersionUID = 1L;

    /**
     * 活动时间的主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 开始活动的日期
     */
    private Long beginactivitydate;
    /**
     * 结束活动的日期
     */
    private Long endactivitydate;
    /**
     * 每日活动开始时间
     */
    private Long begindailytime;
    /**
     * 每日活动结束时间
     */
    private Long enddailytime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBeginactivitydate() {
        return beginactivitydate;
    }

    public void setBeginactivitydate(Long beginactivitydate) {
        this.beginactivitydate = beginactivitydate;
    }

    public Long getEndactivitydate() {
        return endactivitydate;
    }

    public void setEndactivitydate(Long endactivitydate) {
        this.endactivitydate = endactivitydate;
    }

    public Long getBegindailytime() {
        return begindailytime;
    }

    public void setBegindailytime(Long begindailytime) {
        this.begindailytime = begindailytime;
    }

    public Long getEnddailytime() {
        return enddailytime;
    }

    public void setEnddailytime(Long enddailytime) {
        this.enddailytime = enddailytime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ActivityTime{" +
        ", id=" + id +
        ", beginactivitydate=" + beginactivitydate +
        ", endactivitydate=" + endactivitydate +
        ", begindailytime=" + begindailytime +
        ", enddailytime=" + enddailytime +
        "}";
    }
}
