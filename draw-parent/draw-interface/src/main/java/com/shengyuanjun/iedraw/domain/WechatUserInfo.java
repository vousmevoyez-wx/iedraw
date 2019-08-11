package com.shengyuanjun.iedraw.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 微信用户的基本信息
 * </p>
 *
 * @author wx
 * @since 2019-08-08
 */
@TableName("t_wechat_user_info")
public class WechatUserInfo extends Model<WechatUserInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户的唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 是否订阅公众号，0为未关注
     */
    private Integer subscribe;
    /**
     * 用户的标识
     */
    private String openid;
    /**
     * 用户的昵称
     */
    private String nickname;
    /**
     * 性别，1为男，2为女，0为未知
     */
    private Integer sex;
    /**
     * 用户头像的地址
     */
    private String headimgurl;
    /**
     * 创建纪录的时间
     */
    private Long createtime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
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
        return "WechatUserInfo{" +
        ", id=" + id +
        ", subscribe=" + subscribe +
        ", openid=" + openid +
        ", nickname=" + nickname +
        ", sex=" + sex +
        ", headimgurl=" + headimgurl +
        ", createtime=" + createtime +
        "}";
    }
}
