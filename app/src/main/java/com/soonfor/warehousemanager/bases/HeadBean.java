package com.soonfor.warehousemanager.bases;

import com.google.gson.Gson;
import com.soonfor.warehousemanager.tools.JsonUtils;

/**
 * Created by Administrator on 2018-02-20.
 */

public class HeadBean {

    private int Status;
    private String Data;
    private String ErrorMessage;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public DataBean getDataBean() {
        DataBean dataBean = null;
        if(Data!=null && Data.equals("")){
            dataBean = JsonUtils.getDataBean(Data);
        }
        return dataBean;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
