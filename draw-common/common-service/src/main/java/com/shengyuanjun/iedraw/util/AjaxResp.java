package com.shengyuanjun.iedraw.util;

import java.util.HashMap;
import java.util.Map;

public class AjaxResp {

	public static final int OK = 200;
	public static final int ERROR = 500;
	private int code;

	private String remark;

	private Map<String, Object> args = new HashMap<String, Object>();

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Map<String, Object> getArgs() {
		return args;
	}

}
