package com.soonfor.warehousemanager.module.instore.beans;

import com.soonfor.warehousemanager.tools.CommUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/10 0010 09:42
 * 邮箱：suibozhu@139.com
 * 类用途：5)扫描日志页
 */
public class ScanLogBean {
    private String fBarCode;//"条码代号"
    private String fErrorMsg;//"解析状态"
    private String fRemark;//"日志说明"
    private String fScanDate;//"扫描日期"
    private boolean fisStratRequest;//是否已经开始请求了

    public ScanLogBean(boolean fisStratRequest) {
        this.fisStratRequest = fisStratRequest;
    }

    public String getFBarCode() {
        return CommUtil.formatStr(fBarCode);
    }

    public void setFBarCode(String fBarCode) {
        this.fBarCode = fBarCode;
    }

    public String getFErrorMsg() {
        return  CommUtil.formatStr(fErrorMsg);
    }

    public void setFErrorMsg(String fErrorMsg) {
        this.fErrorMsg = fErrorMsg;
    }

    public String getFRemark() {
        return CommUtil.formatStr(fRemark);
    }

    public void setFRemark(String fRemark) {
        this.fRemark = fRemark;
    }

    public String getFScanDate() {
        return CommUtil.formatStr(fScanDate);
    }

    public void setFScanDate(String fScanDate) {
        this.fScanDate = fScanDate;
    }

    public boolean isFisStratRequest() {
        return fisStratRequest;
    }

    public void setFisStratRequest(boolean fisStratRequest) {
        this.fisStratRequest = fisStratRequest;
    }
}
