package com.shengyuanjun.iedraw.domain;

public class AccessToken {
    private Integer id;

    private String accesstoken;

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
        this.accesstoken = accesstoken == null ? null : accesstoken.trim();
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "id=" + id +
                ", accesstoken='" + accesstoken + '\'' +
                '}';
    }
}