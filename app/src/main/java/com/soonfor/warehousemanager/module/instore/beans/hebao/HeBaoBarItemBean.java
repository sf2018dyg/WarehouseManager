package com.soonfor.warehousemanager.module.instore.beans.hebao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：DC-ZhuSuiBo on 2018/9/3 0003 15:12
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
@Entity(nameInDb = "HeBaoBarItemBean")
public class HeBaoBarItemBean {
    @Property(nameInDb = "fBarCode")
    @Id
    private String fBarCode = "";// "条码代号"
    @Property(nameInDb = "fOrdNo")
    private String fOrdNo = "";//订单号
    @Property(nameInDb = "fPackNo")
    private String fPackNo = "";//: "包装编号"
    @Property(nameInDb = "fSPCode")
    private String fSPCode = "";//:"分包号"
    @Property(nameInDb = "fSPName")
    private String fSPName = "";//:"分包说明"
    @Property(nameInDb = "fSPSizeDesc")
    private String fSPSizeDesc = "";//:"分包描述"
    @Property(nameInDb = "fStkCode")
    private String fStkCode = "";//:"仓库代号"
    @Property(nameInDb = "fStkName")
    private String fStkName = "";//:"仓库名称"
    @Property(nameInDb = "fPlaceCode")
    private String fPlaceCode = "";//:"储位代号"
    @Property(nameInDb = "fPlaceName")
    private String fPlaceName = "";//:"储位名称"
    @Property(nameInDb = "fCtnL")
    private String fCtnL = "0";//:"包装长"
    @Property(nameInDb = "fCtnW")
    private String fCtnW = "0";//:"包装宽"
    @Property(nameInDb = "fCtnH")
    private String fCtnH = "0";//:"包装高"
    @Property(nameInDb = "fOutCuft")
    private String fOutCuft = "0";//:"包装体积"
    @Property(nameInDb = "fPackCtn")
    private String fPackCtn = "0";//: "包装箱数"
    @Property(nameInDb = "fScanorID")
    private String fScanorID = "";//:"扫描人代号"
    @Property(nameInDb = "fScanor")
    private String fScanor = "";//:"扫描人"
    @Property(nameInDb = "fScanDate")
    private String fScanDate = "";//:"扫描日期"
    @Property(nameInDb = "fOrdSpID")
    private String fOrdSpID = "";//: "条码ID"

    @Generated(hash = 1924468370)
    public HeBaoBarItemBean(String fBarCode, String fOrdNo, String fPackNo,
                            String fSPCode, String fSPName, String fSPSizeDesc, String fStkCode,
                            String fStkName, String fPlaceCode, String fPlaceName, String fCtnL,
                            String fCtnW, String fCtnH, String fOutCuft, String fPackCtn,
                            String fScanorID, String fScanor, String fScanDate, String fOrdSpID) {
        this.fBarCode = fBarCode;
        this.fOrdNo = fOrdNo;
        this.fPackNo = fPackNo;
        this.fSPCode = fSPCode;
        this.fSPName = fSPName;
        this.fSPSizeDesc = fSPSizeDesc;
        this.fStkCode = fStkCode;
        this.fStkName = fStkName;
        this.fPlaceCode = fPlaceCode;
        this.fPlaceName = fPlaceName;
        this.fCtnL = fCtnL;
        this.fCtnW = fCtnW;
        this.fCtnH = fCtnH;
        this.fOutCuft = fOutCuft;
        this.fPackCtn = fPackCtn;
        this.fScanorID = fScanorID;
        this.fScanor = fScanor;
        this.fScanDate = fScanDate;
        this.fOrdSpID = fOrdSpID;
    }

    @Generated(hash = 485242284)
    public HeBaoBarItemBean() {
    }

    public String getFBarCode() {
        return this.fBarCode;
    }

    public void setFBarCode(String fBarCode) {
        this.fBarCode = fBarCode;
    }

    public String getFOrdNo() {
        return this.fOrdNo==null?"":this.fOrdNo;
    }

    public void setFOrdNo(String fOrdNo) {
        this.fOrdNo = fOrdNo;
    }

    public String getFPackNo() {
        return this.fPackNo;
    }

    public void setFPackNo(String fPackNo) {
        this.fPackNo = fPackNo;
    }

    public String getFSPCode() {
        return this.fSPCode;
    }

    public void setFSPCode(String fSPCode) {
        this.fSPCode = fSPCode;
    }

    public String getFSPName() {
        return this.fSPName;
    }

    public void setFSPName(String fSPName) {
        this.fSPName = fSPName;
    }

    public String getFSPSizeDesc() {
        return this.fSPSizeDesc;
    }

    public void setFSPSizeDesc(String fSPSizeDesc) {
        this.fSPSizeDesc = fSPSizeDesc;
    }

    public String getFStkCode() {
        return this.fStkCode;
    }

    public void setFStkCode(String fStkCode) {
        this.fStkCode = fStkCode;
    }

    public String getFStkName() {
        return this.fStkName;
    }

    public void setFStkName(String fStkName) {
        this.fStkName = fStkName;
    }

    public String getFPlaceCode() {
        return this.fPlaceCode==null?"":this.fPlaceCode;
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

    public String getFOutCuft() {
        return this.fOutCuft;
    }

    public void setFOutCuft(String fOutCuft) {
        this.fOutCuft = fOutCuft;
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

    public String getFOrdSpID() {
        return this.fOrdSpID;
    }

    public void setFOrdSpID(String fOrdSpID) {
        this.fOrdSpID = fOrdSpID;
    }
}
