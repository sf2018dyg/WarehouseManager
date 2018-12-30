package com.soonfor.warehousemanager.module.outstore;

import com.soonfor.warehousemanager.bases.IBaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/26 0026 16:02
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public interface IOutStoreView extends IBaseView {
    void showUILoading();
    void hideUILoading();
    void setErrorMsg(int code, String msg);
    void setTitles(List<String> lt);
    void setSuccessResult(String msg);
    void setFailResult(String msg);
    void setDatas(int index, String[] titles, List<String[]> data);
    void resetListView();
    void callOnDelAllDatas();
    void SuccessSound();
    void RepatSound();
    void FaileSound();
    void reflashDataList();
    void setCbDelStatu(boolean bl);
    boolean getCheckStu();
    void delHeBaoData(boolean isResetCheckBox, boolean isClearNotQiTao);
    void cleraTiaojian();
    void setSuccessMsg(String msg);

    void setGetBathByBarCode(String[] titles, ArrayList<String[]> datas, String barcode);//通过条码请求物流批次失败
}
