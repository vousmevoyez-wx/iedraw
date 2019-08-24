package com.shengyuanjun.iedraw.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wx
 * @since 2019-08-08
 */
@TableName("t_quotations")
public class Quotations extends Model<Quotations> {

    private static final long serialVersionUID = 1L;

    /**
     * 语录的主键
     */
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Long id;
    /**
     * 语录的状态，1开心，2难过，3惊讶，4平静，5欢聚
     */
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Long quotationsstatus;
    /**
     * 语录
     */
    private String quotationsdesc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuotationsstatus() {
        return quotationsstatus;
    }

    public void setQuotationsstatus(Long quotationsstatus) {
        this.quotationsstatus = quotationsstatus;
    }

    public String getQuotationsdesc() {
        return quotationsdesc;
    }

    public void setQuotationsdesc(String quotationsdesc) {
        this.quotationsdesc = quotationsdesc;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Quotations{" +
        ", id=" + id +
        ", quotationsstatus=" + quotationsstatus +
        ", quotationsdesc=" + quotationsdesc +
        "}";
    }
}
