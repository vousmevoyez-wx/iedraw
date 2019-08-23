package com.shengyuanjun.iedraw.test.sendtextmsgTest;


import java.util.List;

public class NewsMessage extends TextMeaasge {
    private int ArticleCount;//图文消息个数，限制为8条以内
    private List<News> Articles;//多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
    public NewsMessage(String toUserName, String fromUserName, Long createTime, String msgType, Integer articleCount, List<News> articles) {
        super(toUserName, fromUserName, createTime, msgType);
        this.ArticleCount = articleCount;
        this.Articles = articles;
    }
 
    public NewsMessage() {
        super();
    }
 
 
    public int getArticleCount() {
        return ArticleCount;
    }
 
    public void setArticleCount(int articleCount) {
        this.ArticleCount = articleCount;
    }
 
    public List<News> getArticles() {
        return Articles;
    }
 
    public void setArticles(List<News> articles) {
        this.Articles = articles;
    }
}