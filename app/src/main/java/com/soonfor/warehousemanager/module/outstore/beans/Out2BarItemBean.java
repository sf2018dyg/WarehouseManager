package com.soonfor.warehousemanager.module.outstore.beans;
import com.soonfor.warehousemanager.tools.CommUtil;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/10 0010 09:35
 * 邮箱：suibozhu@139.com
 * 类用途：4)条码明细页 分包出货
 */
public class Out2BarItemBean {
    private String fBarCode;// "条码代号"
    private String fDlvNo;//出货通知单号
    private String fSNo;//行号
    private String fOrdNo;//"订单号"
    private String fTotalPackQty;//总包数
    private String fSplitBatchNo;//"拆单批次"
    private String fProdNo;//生产单号
    private String fCarCardNo;//车牌号
    private String fSPCode;//"分包号"
    private String fPackMark;//"分包说明"
    private String fSPSCode;//分包分类号
    private String fBarCodeQty;//条码数量
    private String fStkCode;//仓库代号
    private String fPlaceCode;//"储位代号"
    private String fPlaceName;//"储位名称"
    private String fStkTrayCode;//托盘代号
    private String fScanorID;//"扫描人代号"
    private String fScanor;//"扫描人"
    private String fScanDate;//"扫描日期"
    private String fID;// "条码ID"

    private String fCtnL;//"包装长"
    private String fCtnW;//"包装宽"
    private String fCtnH;//" 包装高"
    private String fPrNTimes;
    private String fIfCancel;
    private String fPerPrnTimes;
    private String fPackNo;//包装编号
    private String fPackCuft;//"包装体积"
    private String fPackPcs;//"包装件数"

    public String getfBarCode() {
        return CommUtil.formatStr(CommUtil.formatStr(fBarCode));
    }

    public String getfDlvNo() {
        return CommUtil.formatStr(fDlvNo);
    }

    public String getfSNo() {
        return CommUtil.formatStr(fSNo);
    }

    public String getfOrdNo() {
        return CommUtil.formatStr(fOrdNo);
    }

    public String getfTotalPackQty() {
        return CommUtil.formatStr(fTotalPackQty);
    }

    public String getfSplitBatchNo() {
        return CommUtil.formatStr(fSplitBatchNo);
    }

    public String getfProdNo() {
        return CommUtil.formatStr(fProdNo);
    }

    public String getfCarCardNo() {
        return CommUtil.formatStr(fCarCardNo);
    }

    public String getfSPCode() {
        return CommUtil.formatInteger(fSPCode);
    }

    public String getfPackMark() {
        return CommUtil.formatStr(fPackMark);
    }

    public String getfSPSCode() {
        return CommUtil.formatStr(fSPSCode);
    }

    public String getfBarCodeQty() {
        return CommUtil.formatStr(fBarCodeQty);
    }

    public String getfStkCode() {
        return CommUtil.formatStr(fStkCode);
    }

    public String getfPlaceCode() {
        return CommUtil.formatStr(fPlaceCode);
    }

    public String getfPlaceName() {
        return CommUtil.formatStr(fPlaceName);
    }

    public String getfStkTrayCode() {
        return CommUtil.formatStr(fStkTrayCode);
    }

    public String getfScanorID() {
        return CommUtil.formatStr(fScanorID);
    }

    public String getfScanor() {
        return CommUtil.formatStr(fScanor);
    }

    public String getfScanDate() {
        return CommUtil.formatStr(fScanDate);
    }

    public String getfID() {
        return CommUtil.formatStr(fID);
    }

    public String getfCtnL() {
        return CommUtil.formatInteger(fCtnL);
    }

    public String getfCtnW() {
        return CommUtil.formatInteger(fCtnW);
    }

    public String getfCtnH() {
        return CommUtil.formatInteger(fCtnH);
    }

    public String getfPrNTimes() {
        return CommUtil.formatStr(fPrNTimes);
    }

    public String getfIfCancel() {
        return CommUtil.formatStr(fIfCancel);
    }

    public String getfPerPrnTimes() {
        return CommUtil.formatStr(fPerPrnTimes);
    }

    public String getfPackNo() {
        return CommUtil.formatStr(fPackNo);
    }

    public String getfPackCuft() {
        return CommUtil.formatStr(fPackCuft);
    }

    public String getfPackPcs() {
        return CommUtil.formatStr(fPackPcs);
    }
}
