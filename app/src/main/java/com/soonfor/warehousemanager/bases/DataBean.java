package com.soonfor.warehousemanager.bases;

/**
 * Created by Administrator on 2018-02-20.
 */

public class DataBean {

    private Boolean success;
    private int msgcode;
    private String data;
    private String errormsg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(int msgcode) {
        this.msgcode = msgcode;
    }

    public String getData() {
        return data==null?"":data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
