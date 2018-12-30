package com.soonfor.warehousemanager.module.outstore.beans;

import com.soonfor.warehousemanager.tools.CommUtil;

/**
 * 物流批次|车牌号|载物台等
 */
public class LogistBatchBean {

    private String st_id;//程序代号 0分包备货 1 分包出货

    private String fLogistBatchNo;//物流批次
    private String fLogistDirectCode;//物流方向
    private String fLogistDirectName;//物流方向
    private String fCarCardNo;//车牌号
    private String fLPCode;//载物台代号
    private String fLPName;//载物台
    private String fCreatorID;//建立人代号
    private String fCreator;//建立人
    private String fCDate;//建立日期

    private String fAddOrder;//追加的订单
    private String fShippingOrder;//出货通知单

    public String getfLogistBatchNo() {
        return CommUtil.formatStr(fLogistBatchNo);
    }

    public String getfLogistDirectCode() {
        return CommUtil.formatStr(fLogistDirectCode);
    }

    public String getfLogistDirectName() {
        return CommUtil.formatStr(fLogistDirectName);
    }

    public String getfCarCardNo() {
        return CommUtil.formatStr(fCarCardNo);
    }

    public String getfLPCode() {
        return CommUtil.formatStr(fLPCode);
    }

    public String getfLPName() {
        return CommUtil.formatStr(fLPName);
    }

    public String getfCreatorID() {
        return CommUtil.formatStr(fCreatorID);
    }

    public String getfCreator() {
        return CommUtil.formatStr(fCreator);
    }

    public String getfCDate() {
        return CommUtil.formatStr(fCDate);
    }

    public String getfAddOrder() {
        return CommUtil.formatStr(fAddOrder);
    }

    public void setfAddOrder(String fAddOrder) {
        this.fAddOrder = fAddOrder;
    }

    public String getfShippingOrder() {
        return CommUtil.formatStr(fShippingOrder);
    }

    public void setfShippingOrder(String fShippingOrder) {
        this.fShippingOrder = fShippingOrder;
    }

    public void setfLogistBatchNo(String fLogistBatchNo) {
        this.fLogistBatchNo = fLogistBatchNo;
    }

    public void setfLogistDirectCode(String fLogistDirectCode) {
        this.fLogistDirectCode = fLogistDirectCode;
    }

    public void setfLogistDirectName(String fLogistDirectName) {
        this.fLogistDirectName = fLogistDirectName;
    }

    public void setfCarCardNo(String fCarCardNo) {
        this.fCarCardNo = fCarCardNo;
    }

    public void setfLPCode(String fLPCode) {
        this.fLPCode = fLPCode;
    }

    public void setfLPName(String fLPName) {
        this.fLPName = fLPName;
    }

    public void setfCreatorID(String fCreatorID) {
        this.fCreatorID = fCreatorID;
    }

    public void setfCreator(String fCreator) {
        this.fCreator = fCreator;
    }

    public void setfCDate(String fCDate) {
        this.fCDate = fCDate;
    }

    public String getSt_id() {
        return CommUtil.formatStr(st_id);
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
    }
}
