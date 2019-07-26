package com.shengyuanjun.iedraw;

public class AjaxResult {
    private Boolean success = true; //结果
    private String message = "操作成功";    //提示信息
    private Object retObj = null;   //后端返回给前台的数据

    public static AjaxResult me(){
        return new AjaxResult();
    }

    public AjaxResult() {
    }

    public AjaxResult(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public AjaxResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getRetObj() {
        return retObj;
    }

    public AjaxResult setRetObj(Object retObj) {
        this.retObj = retObj;
        return this;
    }
}
