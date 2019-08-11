package com.shengyuanjun.iedraw.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 奖品表
 * </p>
 *
 * @author wx
 * @since 2019-08-06
 */
@TableName("t_prize")
public class Prize extends Model<Prize> {

    private static final long serialVersionUID = 1L;

    /**
     * 奖品表的主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 奖品名称
     */
    private String prizename;
    /**
     * 库存
     */
    private Long stock;
    /**
     * 奖品有效期开始时间
     */
    private Long beginvalidityperiod;
    /**
     * 奖品有效期结束时间
     */
    private Long endvalidityperiod;
    /**
     * 奖品图片的url
     */
    private String pictureurl;
    /**
     * 中奖概率
     */
    private Float odds;

    private int type;

    @Override
    public String toString() {
        return "Prize{" +
                "id=" + id +
                ", prizename='" + prizename + '\'' +
                ", stock=" + stock +
                ", beginvalidityperiod=" + beginvalidityperiod +
                ", endvalidityperiod=" + endvalidityperiod +
                ", pictureurl='" + pictureurl + '\'' +
                ", odds=" + odds +
                ", type=" + type +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrizename() {
        return prizename;
    }

    public void setPrizename(String prizename) {
        this.prizename = prizename;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getBeginvalidityperiod() {
        return beginvalidityperiod;
    }

    public void setBeginvalidityperiod(Long beginvalidityperiod) {
        this.beginvalidityperiod = beginvalidityperiod;
    }

    public Long getEndvalidityperiod() {
        return endvalidityperiod;
    }

    public void setEndvalidityperiod(Long endvalidityperiod) {
        this.endvalidityperiod = endvalidityperiod;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public Float getOdds() {
        return odds;
    }

    public void setOdds(Float odds) {
        this.odds = odds;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
