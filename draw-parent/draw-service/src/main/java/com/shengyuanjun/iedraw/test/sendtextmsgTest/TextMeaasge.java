package com.shengyuanjun.iedraw.test.sendtextmsgTest;

public class TextMeaasge {

	private String FromUserName;
	private String ToUserName;
	private String MsgType;
	private long CreateTime;
	private String Content;
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}

	public TextMeaasge(){}

    public TextMeaasge(String toUserName, String fromUserName, Long createTime, String msgType){
	    this.FromUserName = fromUserName;
	    this.ToUserName = toUserName;
	    this.CreateTime = createTime;
	    this.MsgType = msgType;
    }

}