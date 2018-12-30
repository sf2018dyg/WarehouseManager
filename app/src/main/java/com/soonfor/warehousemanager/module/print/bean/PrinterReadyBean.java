package com.soonfor.warehousemanager.module.print.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：DC-ZhuSuiBo on 2018/9/22 0022 10:41
 * 邮箱：suibozhu@139.com
 * 类用途：未打印而准备要打印的条码bean
 */
@Entity(nameInDb = "PrinterReadyBean")
public class PrinterReadyBean {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "fBarCode")
    private String fBarCode = "";// "条码代号"
    @Property(nameInDb = "fOrdNo")
    private String fOrdNo = "";//:"订单号"
    @Property(nameInDb = "fSPCode")
    private String fSPCode = "";//:"分包号"
    @Property(nameInDb = "fSPName")
    private String fSPName = "";//:"分包说明"
    @Property(nameInDb = "HeBaoTime")
    private String HeBaoTime = "";//合包时间
    @Property(nameInDb = "HeBaoUser")
    private String HeBaoUser = "";//合包人

    @Generated(hash = 1480713177)
    public PrinterReadyBean(Long id, String fBarCode, String fOrdNo, String fSPCode,
                            String fSPName, String HeBaoTime, String HeBaoUser) {
        this.id = id;
        this.fBarCode = fBarCode;
        this.fOrdNo = fOrdNo;
        this.fSPCode = fSPCode;
        this.fSPName = fSPName;
        this.HeBaoTime = HeBaoTime;
        this.HeBaoUser = HeBaoUser;
    }

    @Generated(hash = 959620125)
    public PrinterReadyBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getHeBaoTime() {
        return this.HeBaoTime;
    }

    public void setHeBaoTime(String HeBaoTime) {
        this.HeBaoTime = HeBaoTime;
    }

    public String getHeBaoUser() {
        return this.HeBaoUser;
    }

    public void setHeBaoUser(String HeBaoUser) {
        this.HeBaoUser = HeBaoUser;
    }

}
