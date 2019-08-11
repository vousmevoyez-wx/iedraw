package com.shengyuanjun.iedraw.domain;

public class JsapiTIcket {
    private Integer id;

    private String accesstoken;

    @Override
    public String toString() {
        return "JsapiTIcket{" +
                "id=" + id +
                ", accesstoken='" + accesstoken + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
