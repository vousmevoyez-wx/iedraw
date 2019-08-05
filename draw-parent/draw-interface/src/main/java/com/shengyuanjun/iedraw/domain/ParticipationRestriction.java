package com.shengyuanjun.iedraw.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 参与次数的限制及电子围栏
 * </p>
 *
 * @author wx
 * @since 2019-08-03
 */
@TableName("t_participation_restriction")
public class ParticipationRestriction extends Model<ParticipationRestriction> {

    private static final long serialVersionUID = 1L;

    /**
     * 参与限制表的主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 总参与次数
     */
    private String totalparticipants;
    /**
     * 日参与次数
     */
    private String dayparticipation;
    /**
     * 总中奖次数
     */
    private String totalwinningprize;
    /**
     * 日中奖次数
     */
    private String daywinningprize;
    /**
     * 电子围栏的经度
     */
    private String longitude;
    /**
     * 电子围栏的纬度
     */
    private String latitude;
    /**
     * 电子围栏的范围
     */
    private Integer range;

    public ParticipationRestriction() {
    }

    public ParticipationRestriction(Long id, String totalparticipants, String dayparticipation,
                                    String totalwinningprize, String daywinningprize,
                                    String longitude, String latitude, Integer range) {
        this.id = id;
        this.totalparticipants = totalparticipants;
        this.dayparticipation = dayparticipation;
        this.totalwinningprize = totalwinningprize;
        this.daywinningprize = daywinningprize;
        this.longitude = longitude;
        this.latitude = latitude;
        this.range = range;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTotalparticipants() {
        return totalparticipants;
    }

    public void setTotalparticipants(String totalparticipants) {
        this.totalparticipants = totalparticipants;
    }

    public String getDayparticipation() {
        return dayparticipation;
    }

    public void setDayparticipation(String dayparticipation) {
        this.dayparticipation = dayparticipation;
    }

    public String getTotalwinningprize() {
        return totalwinningprize;
    }

    public void setTotalwinningprize(String totalwinningprize) {
        this.totalwinningprize = totalwinningprize;
    }

    public String getDaywinningprize() {
        return daywinningprize;
    }

    public void setDaywinningprize(String daywinningprize) {
        this.daywinningprize = daywinningprize;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ParticipationRestriction{" +
        ", id=" + id +
        ", totalparticipants=" + totalparticipants +
        ", dayparticipation=" + dayparticipation +
        ", totalwinningprize=" + totalwinningprize +
        ", daywinningprize=" + daywinningprize +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", range=" + range +
        "}";
    }
}
