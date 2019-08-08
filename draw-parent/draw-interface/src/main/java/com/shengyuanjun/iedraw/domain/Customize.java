package com.shengyuanjun.iedraw.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 定制纪录表
 * </p>
 *
 * @author wx
 * @since 2019-08-07
 */
@TableName("t_customize")
public class Customize extends Model<Customize> {

    private static final long serialVersionUID = 1L;

    /**
     * 私人订制的主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 定制时间
     */
    private Long customizetime;
    /**
     * 提货码
     */
    private String goodscode;
    /**
     * 打印图
     */
    private String printchart;
    /**
     * 打印状态，0为未打印，1为已打印，2为已失效，3为打印失败
     */
    private Integer printstatus;
    /**
     * 创建表纪录的时间
     */
    private Long createtime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomizetime() {
        return customizetime;
    }

    public void setCustomizetime(Long customizetime) {
        this.customizetime = customizetime;
    }

    public String getGoodscode() {
        return goodscode;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode;
    }

    public String getPrintchart() {
        return printchart;
    }

    public void setPrintchart(String printchart) {
        this.printchart = printchart;
    }

    public Integer getPrintstatus() {
        return printstatus;
    }

    public void setPrintstatus(Integer printstatus) {
        this.printstatus = printstatus;
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
        return "Customize{" +
        ", id=" + id +
        ", customizetime=" + customizetime +
        ", goodscode=" + goodscode +
        ", printchart=" + printchart +
        ", printstatus=" + printstatus +
        ", createtime=" + createtime +
        "}";
    }
}
