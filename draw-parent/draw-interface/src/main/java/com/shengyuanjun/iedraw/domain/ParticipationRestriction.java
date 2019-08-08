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
 * @since 2019-08-07
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
     * 优惠券的链接设置
     */
    private String linkurls;
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
    private String range;


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

    public String getLinkurls() {
        return linkurls;
    }

    public void setLinkurls(String linkurls) {
        this.linkurls = linkurls;
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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
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
        ", linkurls=" + linkurls +
        ", noprize=" + noprize +
        ", exceeddaily=" + exceeddaily +
        ", exceedtotal=" + exceedtotal +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", range=" + range +
        "}";
    }
}
