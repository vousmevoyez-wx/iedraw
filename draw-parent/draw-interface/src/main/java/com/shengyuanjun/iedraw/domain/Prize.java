package com.shengyuanjun.iedraw.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * @since 2019-08-03
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
    private Date beginvalidityperiod;
    /**
     * 奖品有效期结束时间
     */
    private Date endvalidityperiod;
    /**
     * 奖品图片的url
     */
    private String pictureurl;

    public Prize() {
    }

    public Prize(Long id, String prizename, Long stock, Date beginvalidityperiod, Date endvalidityperiod, String pictureurl) {
        this.id = id;
        this.prizename = prizename;
        this.stock = stock;
        this.beginvalidityperiod = beginvalidityperiod;
        this.endvalidityperiod = endvalidityperiod;
        this.pictureurl = pictureurl;
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

    public Date getBeginvalidityperiod() {
        return beginvalidityperiod;
    }

    public void setBeginvalidityperiod(Date beginvalidityperiod) {
        this.beginvalidityperiod = beginvalidityperiod;
    }

    public Date getEndvalidityperiod() {
        return endvalidityperiod;
    }

    public void setEndvalidityperiod(Date endvalidityperiod) {
        this.endvalidityperiod = endvalidityperiod;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Prize{" +
        ", id=" + id +
        ", prizename=" + prizename +
        ", stock=" + stock +
        ", beginvalidityperiod=" + beginvalidityperiod +
        ", endvalidityperiod=" + endvalidityperiod +
        ", pictureurl=" + pictureurl +
        "}";
    }
}
