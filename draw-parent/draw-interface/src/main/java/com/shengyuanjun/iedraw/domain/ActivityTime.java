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
 *      活动时间
 * </p>
 *
 * @author wx
 * @since 2019-08-03
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
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date beginactivitydate;

    /**
     * 结束活动的日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date endactivitydate;
    /**
     * 每日活动开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date begindailytime;
    /**
     * 每日活动结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date enddailytime;

    public ActivityTime() {
    }

    public ActivityTime(Long id, Date beginactivitydate, Date endactivitydate, Date begindailytime, Date enddailytime) {
        this.id = id;
        this.beginactivitydate = beginactivitydate;
        this.endactivitydate = endactivitydate;
        this.begindailytime = begindailytime;
        this.enddailytime = enddailytime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getBeginactivitydate() {
        return beginactivitydate;
    }

    public void setBeginactivitydate(Date beginactivitydate) {
        this.beginactivitydate = beginactivitydate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getEndactivitydate() {
        return endactivitydate;
    }

    public void setEndactivitydate(Date endactivitydate) {
        this.endactivitydate = endactivitydate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getBegindailytime() {
        return begindailytime;
    }

    public void setBegindailytime(Date begindailytime) {
        this.begindailytime = begindailytime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getEnddailytime() {
        return enddailytime;
    }

    public void setEnddailytime(Date enddailytime) {
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
