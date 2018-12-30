package com.soonfor.warehousemanager.home.store;

/**
 * 作者：DC-DingYG on 2018-07-23 13:37
 * 邮箱：dingyg012655@126.com
 */
public class StoreBean {
    /*{"fStkCode":"103","fStkName":"梦天成品仓库"}*/

    private String fStkCode;
    private String fStkName;
    private int fIfUsePlace;

    public StoreBean(String fStkCode,String fStkName,int fIfUsePlace){
        this.fStkCode = fStkCode;
        this.fStkName = fStkName;
        this.fIfUsePlace = fIfUsePlace;
    }

    public String getfStkCode() {
        return fStkCode;
    }

    public void setfStkCode(String fStkCode) {
        this.fStkCode = fStkCode;
    }

    public String getfStkName() {
        return fStkName;
    }

    public void setfStkName(String fStkName) {
        this.fStkName = fStkName;
    }

    public int getfIfUsePlace() {
        return fIfUsePlace;
    }

    public void setfIfUsePlace(int fIfUsePlace) {
        this.fIfUsePlace = fIfUsePlace;
    }
}
