package com.shengyuanjun.iedraw.test.sendtextmsgTest;

public class News {
    private String Title;//图文标题
    private String Description;//图文描述
    private String PicUrl;//图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    private String Url;//点击图文消息跳转链接
 
    public News(String title, String description, String picUrl, String url) {
        this.Title = title;
        this.Description = description;
        this.PicUrl = picUrl;
        this.Url = url;
    }
    public News(){super();}
 
    public String getTitle() {
        return Title;
    }
 
    public void setTitle(String title) {
        this.Title = title;
    }
 
    public String getDescription() {
        return Description;
    }
 
    public void setDescription(String description) {
        this.Description = description;
    }
 
    public String getPicUrl() {
        return PicUrl;
    }
 
    public void setPicUrl(String picUrl) {
        this.PicUrl = picUrl;
    }
 
    public String getUrl() {
        return Url;
    }
 
    public void setUrl(String url) {
        this.Url = url;
    }
}