package com.soonfor.warehousemanager.module.outstore.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/10 0010 09:30
 * 邮箱：suibozhu@139.com
 * 类用途：2)订单入库状况页
 */
@Entity(nameInDb = "OutOrdInStatusBean")
public class OutOrdInStatusBean {
    @Property(nameInDb = "fOrdNo")
    @Id
    private String fOrdNo = "";//"订单号"
    @Property(nameInDb = "fProdNo")
    private String fProdNo = "";//"生成单号"
    @Property(nameInDb = "fTotalPackQty")
    private String fTotalPackQty = "0";//"总包数"
    @Property(nameInDb = "fStkOutPackQty")
    private String fStkOutPackQty = "0";//" 已出货包数"
    @Property(nameInDb = "fScanedPackQty")
    private String fScanedPackQty = "0";//"已扫描包数"
    @Property(nameInDb = "fThisScanPackQty")
    private String fThisScanPackQty = "0";//"本次扫描包数"
    @Property(nameInDb = "fUnScanPackQty")
    private String fUnScanPackQty = "0";//"未扫描包数"

    @Generated(hash = 91776306)
    public OutOrdInStatusBean(String fOrdNo, String fProdNo, String fTotalPackQty,
                              String fStkOutPackQty, String fScanedPackQty, String fThisScanPackQty,
                              String fUnScanPackQty) {
        this.fOrdNo = fOrdNo;
        this.fProdNo = fProdNo;
        this.fTotalPackQty = fTotalPackQty;
        this.fStkOutPackQty = fStkOutPackQty;
        this.fScanedPackQty = fScanedPackQty;
        this.fThisScanPackQty = fThisScanPackQty;
        this.fUnScanPackQty = fUnScanPackQty;
    }

    @Generated(hash = 14377677)
    public OutOrdInStatusBean() {
    }

    public String getFOrdNo() {
        return this.fOrdNo;
    }

    public void setFOrdNo(String fOrdNo) {
        this.fOrdNo = fOrdNo;
    }

    public String getFProdNo() {
        return this.fProdNo;
    }

    public void setFProdNo(String fProdNo) {
        this.fProdNo = fProdNo;
    }

    public String getFTotalPackQty() {
        return this.fTotalPackQty;
    }

    public void setFTotalPackQty(String fTotalPackQty) {
        this.fTotalPackQty = fTotalPackQty;
    }

    public String getFStkOutPackQty() {
        return this.fStkOutPackQty;
    }

    public void setFStkOutPackQty(String fStkOutPackQty) {
        this.fStkOutPackQty = fStkOutPackQty;
    }

    public String getFScanedPackQty() {
        return this.fScanedPackQty;
    }

    public void setFScanedPackQty(String fScanedPackQty) {
        this.fScanedPackQty = fScanedPackQty;
    }

    public String getFThisScanPackQty() {
        return this.fThisScanPackQty;
    }

    public void setFThisScanPackQty(String fThisScanPackQty) {
        this.fThisScanPackQty = fThisScanPackQty;
    }

    public String getFUnScanPackQty() {
        return this.fUnScanPackQty;
    }

    public void setFUnScanPackQty(String fUnScanPackQty) {
        this.fUnScanPackQty = fUnScanPackQty;
    }


}
