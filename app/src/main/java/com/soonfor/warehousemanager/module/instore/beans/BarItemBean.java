package com.soonfor.warehousemanager.module.instore.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/10 0010 09:35
 * 邮箱：suibozhu@139.com
 * 类用途：4)条码明细页
 */
@Entity(nameInDb = "BarItemBean")
public class BarItemBean {
    @Property(nameInDb = "fBarCode")
    @Id
    private String fBarCode = "";// "条码代号"
    @Property(nameInDb = "fOrdNo")
    private String fOrdNo = "";//"订单号"
    @Property(nameInDb = "fSplitBatchNo")
    private String fSplitBatchNo = "";//"拆单批次"
    @Property(nameInDb = "fSPCode")
    private String fSPCode = "";//"分包号"
    @Property(nameInDb = "fSPSortName")
    private String fSPSortName = "";
    @Property(nameInDb = "fSPSortEnName")
    private String fSPSortEnName = "";
    @Property(nameInDb = "fPackMark")
    private String fPackMark = "";//"分包说明"
    @Property(nameInDb = "fPlaceCode")
    private String fPlaceCode = "";//"储位代号"
    @Property(nameInDb = "fPlaceName")
    private String fPlaceName = "";//"储位名称"
    @Property(nameInDb = "fCtnL")
    private String fCtnL = "0";//"包装长"
    @Property(nameInDb = "fCtnW")
    private String fCtnW = "0";//"包装宽"
    @Property(nameInDb = "fCtnH")
    private String fCtnH = "0";//" 包装高"
    @Property(nameInDb = "fPrNTimes")
    private String fPrNTimes = "";
    @Property(nameInDb = "fIfCancel")
    private String fIfCancel = "";
    @Property(nameInDb = "fPerPrnTimes")
    private String fPerPrnTimes = "";
    @Property(nameInDb = "fPackNo")
    private String fPackNo = "";//包装编号
    @Property(nameInDb = "fProdNo")
    private String fProdNo = "";
    @Property(nameInDb = "fPackCuft")
    private String fPackCuft = "0";//"包装体积"
    @Property(nameInDb = "fPackPcs")
    private String fPackPcs = "0";//"包装件数"
    @Property(nameInDb = "fPackCtn")
    private String fPackCtn = "0";//包装箱数
    @Property(nameInDb = "fScanorID")
    private String fScanorID = "";//"扫描人代号"
    @Property(nameInDb = "fScanor")
    private String fScanor = "";//"扫描人"
    @Property(nameInDb = "fScanDate")
    private String fScanDate = "";//"扫描日期"
    @Property(nameInDb = "fID")
    private String fID = "";// "条码ID"
    @Property(nameInDb = "fStkInLogNo")
    private String fStkInLogNo = "";//入库单号

    @Generated(hash = 648116049)
    public BarItemBean(String fBarCode, String fOrdNo, String fSplitBatchNo,
                       String fSPCode, String fSPSortName, String fSPSortEnName,
                       String fPackMark, String fPlaceCode, String fPlaceName, String fCtnL,
                       String fCtnW, String fCtnH, String fPrNTimes, String fIfCancel,
                       String fPerPrnTimes, String fPackNo, String fProdNo, String fPackCuft,
                       String fPackPcs, String fPackCtn, String fScanorID, String fScanor,
                       String fScanDate, String fID, String fStkInLogNo) {
        this.fBarCode = fBarCode;
        this.fOrdNo = fOrdNo;
        this.fSplitBatchNo = fSplitBatchNo;
        this.fSPCode = fSPCode;
        this.fSPSortName = fSPSortName;
        this.fSPSortEnName = fSPSortEnName;
        this.fPackMark = fPackMark;
        this.fPlaceCode = fPlaceCode;
        this.fPlaceName = fPlaceName;
        this.fCtnL = fCtnL;
        this.fCtnW = fCtnW;
        this.fCtnH = fCtnH;
        this.fPrNTimes = fPrNTimes;
        this.fIfCancel = fIfCancel;
        this.fPerPrnTimes = fPerPrnTimes;
        this.fPackNo = fPackNo;
        this.fProdNo = fProdNo;
        this.fPackCuft = fPackCuft;
        this.fPackPcs = fPackPcs;
        this.fPackCtn = fPackCtn;
        this.fScanorID = fScanorID;
        this.fScanor = fScanor;
        this.fScanDate = fScanDate;
        this.fID = fID;
        this.fStkInLogNo = fStkInLogNo;
    }

    @Generated(hash = 1910352572)
    public BarItemBean() {
    }

    public String getFBarCode() {
        return this.fBarCode;
    }

    public void setFBarCode(String fBarCode) {
        this.fBarCode = fBarCode;
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

    public String getFSPCode() {
        return this.fSPCode;
    }

    public void setFSPCode(String fSPCode) {
        this.fSPCode = fSPCode;
    }

    public String getFSPSortName() {
        return this.fSPSortName;
    }

    public void setFSPSortName(String fSPSortName) {
        this.fSPSortName = fSPSortName;
    }

    public String getFSPSortEnName() {
        return this.fSPSortEnName;
    }

    public void setFSPSortEnName(String fSPSortEnName) {
        this.fSPSortEnName = fSPSortEnName;
    }

    public String getFPackMark() {
        return this.fPackMark;
    }

    public void setFPackMark(String fPackMark) {
        this.fPackMark = fPackMark;
    }

    public String getFPlaceCode() {
        return this.fPlaceCode;
    }

    public void setFPlaceCode(String fPlaceCode) {
        this.fPlaceCode = fPlaceCode;
    }

    public String getFPlaceName() {
        return this.fPlaceName;
    }

    public void setFPlaceName(String fPlaceName) {
        this.fPlaceName = fPlaceName;
    }

    public String getFCtnL() {
        return this.fCtnL;
    }

    public void setFCtnL(String fCtnL) {
        this.fCtnL = fCtnL;
    }

    public String getFCtnW() {
        return this.fCtnW;
    }

    public void setFCtnW(String fCtnW) {
        this.fCtnW = fCtnW;
    }

    public String getFCtnH() {
        return this.fCtnH;
    }

    public void setFCtnH(String fCtnH) {
        this.fCtnH = fCtnH;
    }

    public String getFPrNTimes() {
        return this.fPrNTimes;
    }

    public void setFPrNTimes(String fPrNTimes) {
        this.fPrNTimes = fPrNTimes;
    }

    public String getFIfCancel() {
        return this.fIfCancel;
    }

    public void setFIfCancel(String fIfCancel) {
        this.fIfCancel = fIfCancel;
    }

    public String getFPerPrnTimes() {
        return this.fPerPrnTimes;
    }

    public void setFPerPrnTimes(String fPerPrnTimes) {
        this.fPerPrnTimes = fPerPrnTimes;
    }

    public String getFPackNo() {
        return this.fPackNo;
    }

    public void setFPackNo(String fPackNo) {
        this.fPackNo = fPackNo;
    }

    public String getFProdNo() {
        return this.fProdNo;
    }

    public void setFProdNo(String fProdNo) {
        this.fProdNo = fProdNo;
    }

    public String getFPackCuft() {
        return this.fPackCuft;
    }

    public void setFPackCuft(String fPackCuft) {
        this.fPackCuft = fPackCuft;
    }

    public String getFPackPcs() {
        return this.fPackPcs;
    }

    public void setFPackPcs(String fPackPcs) {
        this.fPackPcs = fPackPcs;
    }

    public String getFPackCtn() {
        return this.fPackCtn;
    }

    public void setFPackCtn(String fPackCtn) {
        this.fPackCtn = fPackCtn;
    }

    public String getFScanorID() {
        return this.fScanorID;
    }

    public void setFScanorID(String fScanorID) {
        this.fScanorID = fScanorID;
    }

    public String getFScanor() {
        return this.fScanor;
    }

    public void setFScanor(String fScanor) {
        this.fScanor = fScanor;
    }

    public String getFScanDate() {
        return this.fScanDate;
    }

    public void setFScanDate(String fScanDate) {
        this.fScanDate = fScanDate;
    }

    public String getFID() {
        return this.fID;
    }

    public void setFID(String fID) {
        this.fID = fID;
    }

    public String getFStkInLogNo() {
        return this.fStkInLogNo;
    }

    public void setFStkInLogNo(String fStkInLogNo) {
        this.fStkInLogNo = fStkInLogNo;
    }


}
