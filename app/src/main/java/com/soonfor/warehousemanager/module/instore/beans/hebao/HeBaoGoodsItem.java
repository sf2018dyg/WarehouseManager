package com.soonfor.warehousemanager.module.instore.beans.hebao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：DC-ZhuSuiBo on 2018/9/3 0003 15:22
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
@Entity(nameInDb = "HeBaoGoodsItem")
public class HeBaoGoodsItem {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "fOrdNo")
    private String fOrdNo = "";//:"订单号"
    @Property(nameInDb = "fSplitBatchNo")
    private String fSplitBatchNo = "";//:"拆单批次"
    @Property(nameInDb = "fPackNo")
    private String fPackNo = "";//:"包装编号"
    @Property(nameInDb = "fGoodsID")
    private String fGoodsID = "";//:"货品ID"
    @Property(nameInDb = "fGoodsCode")
    private String fGoodsCode = "";//:"品号"
    @Property(nameInDb = "fGoodsName")
    private String fGoodsName = "";//:"品名"
    @Property(nameInDb = "fSizeDesc")
    private String fSizeDesc = "";//:"规格描述"
    @Property(nameInDb = "fCstLotNo")
    private String fCstLotNo = "";//:"类货品定制批号"
    @Property(nameInDb = "fBelongGoodsID")
    private String fBelongGoodsID = "";//:"所属产品ID"
    @Property(nameInDb = "fBelongGoodsCode")
    private String fBelongGoodsCode = "";//:"所属产品代号"
    @Property(nameInDb = "fBelongCstLotNo")
    private String fBelongCstLotNo = "";//:"所属产品定制批号"
    @Property(nameInDb = "fOrdSpID")
    private String fOrdSpID = "";//:"条码ID"

    @Generated(hash = 399838391)
    public HeBaoGoodsItem(Long id, String fOrdNo, String fSplitBatchNo,
                          String fPackNo, String fGoodsID, String fGoodsCode, String fGoodsName,
                          String fSizeDesc, String fCstLotNo, String fBelongGoodsID,
                          String fBelongGoodsCode, String fBelongCstLotNo, String fOrdSpID) {
        this.id = id;
        this.fOrdNo = fOrdNo;
        this.fSplitBatchNo = fSplitBatchNo;
        this.fPackNo = fPackNo;
        this.fGoodsID = fGoodsID;
        this.fGoodsCode = fGoodsCode;
        this.fGoodsName = fGoodsName;
        this.fSizeDesc = fSizeDesc;
        this.fCstLotNo = fCstLotNo;
        this.fBelongGoodsID = fBelongGoodsID;
        this.fBelongGoodsCode = fBelongGoodsCode;
        this.fBelongCstLotNo = fBelongCstLotNo;
        this.fOrdSpID = fOrdSpID;
    }

    @Generated(hash = 349046238)
    public HeBaoGoodsItem() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFOrdNo() {
        return this.fOrdNo;
    }

    public void setFOrdNo(String fOrdNo) {
        this.fOrdNo = fOrdNo;
    }

    public String getFSplitBatchNo() {
        return this.fSplitBatchNo;
    }

    public void setFSplitBatchNo(String fSplitBatchNo) {
        this.fSplitBatchNo = fSplitBatchNo;
    }

    public String getFPackNo() {
        return this.fPackNo;
    }

    public void setFPackNo(String fPackNo) {
        this.fPackNo = fPackNo;
    }

    public String getFGoodsID() {
        return this.fGoodsID;
    }

    public void setFGoodsID(String fGoodsID) {
        this.fGoodsID = fGoodsID;
    }

    public String getFGoodsCode() {
        return this.fGoodsCode;
    }

    public void setFGoodsCode(String fGoodsCode) {
        this.fGoodsCode = fGoodsCode;
    }

    public String getFGoodsName() {
        return this.fGoodsName;
    }

    public void setFGoodsName(String fGoodsName) {
        this.fGoodsName = fGoodsName;
    }

    public String getFSizeDesc() {
        return this.fSizeDesc;
    }

    public void setFSizeDesc(String fSizeDesc) {
        this.fSizeDesc = fSizeDesc;
    }

    public String getFCstLotNo() {
        return this.fCstLotNo;
    }

    public void setFCstLotNo(String fCstLotNo) {
        this.fCstLotNo = fCstLotNo;
    }

    public String getFBelongGoodsID() {
        return this.fBelongGoodsID;
    }

    public void setFBelongGoodsID(String fBelongGoodsID) {
        this.fBelongGoodsID = fBelongGoodsID;
    }

    public String getFBelongGoodsCode() {
        return this.fBelongGoodsCode;
    }

    public void setFBelongGoodsCode(String fBelongGoodsCode) {
        this.fBelongGoodsCode = fBelongGoodsCode;
    }

    public String getFBelongCstLotNo() {
        return this.fBelongCstLotNo;
    }

    public void setFBelongCstLotNo(String fBelongCstLotNo) {
        this.fBelongCstLotNo = fBelongCstLotNo;
    }

    public String getFOrdSpID() {
        return this.fOrdSpID;
    }

    public void setFOrdSpID(String fOrdSpID) {
        this.fOrdSpID = fOrdSpID;
    }


}
