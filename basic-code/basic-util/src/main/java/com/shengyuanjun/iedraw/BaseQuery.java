package com.shengyuanjun.iedraw;

public class BaseQuery {

    private String keyword;//关键字

    private Integer page;//第几页
    private Integer rows;//当前页的条目数

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
