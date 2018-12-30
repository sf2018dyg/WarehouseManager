package com.soonfor.warehousemanager.module.instore.beans;

import java.util.List;

/**
 * Created by Administrator on 2018-02-20.
 */

public class DelDataBean {

    private Boolean success;
    private int msgcode;
    private Items data;
    private String errormsg;

    public class Items {

        private List<Infos> item;

        public class Infos {
            private String fGoodsCode;
            private String fGoodsName;
            private String fSizeDesc;
            private String fOrdNo;
            private String fSPCode;
            private String fSPName;

            public String getfGoodsCode() {
                return fGoodsCode;
            }

            public void setfGoodsCode(String fGoodsCode) {
                this.fGoodsCode = fGoodsCode;
            }

            public String getfGoodsName() {
                return fGoodsName;
            }

            public void setfGoodsName(String fGoodsName) {
                this.fGoodsName = fGoodsName;
            }

            public String getfSizeDesc() {
                return fSizeDesc;
            }

            public void setfSizeDesc(String fSizeDesc) {
                this.fSizeDesc = fSizeDesc;
            }

            public String getfOrdNo() {
                return fOrdNo;
            }

            public void setfOrdNo(String fOrdNo) {
                this.fOrdNo = fOrdNo;
            }

            public String getfSPCode() {
                return fSPCode;
            }

            public void setfSPCode(String fSPCode) {
                this.fSPCode = fSPCode;
            }

            public String getfSPName() {
                return fSPName;
            }

            public void setfSPName(String fSPName) {
                this.fSPName = fSPName;
            }
        }

        public List<Infos> getItem() {
            return item;
        }

        public void setItem(List<Infos> item) {
            this.item = item;
        }
    }

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

    public Items getData() {
        return data;
    }

    public void setData(Items data) {
        this.data = data;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
