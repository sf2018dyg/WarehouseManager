package com.soonfor.warehousemanager.module.instore;

import com.soonfor.warehousemanager.bases.IBaseView;
import com.soonfor.warehousemanager.module.instore.beans.DelDataBean;
import com.soonfor.warehousemanager.module.instore.beans.hebao.SpSidBean;

import org.json.JSONObject;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-07-24 15:46
 * 邮箱：dingyg012655@126.com
 */
public interface IInStoreView extends IBaseView {
    void showUILoading();
    void hideUILoading();
    void setErrorMsg(int code, String msg);
    void setTitles(List<String> lt);
    void setSuccessResult(String msg);
    void setFailResult(String msg);
    void setDatas(int index, String[] titles, List<String[]> data);
    void resetListView();
    void showDelBack(List<DelDataBean.Items.Infos> items);
    void callOnDelAllDatas();
    void goQitaoError(String data);
    void SuccessSound();
    void RepatSound();
    void FaileSound();
    void reflashDataList();
    void setRukuDanju(String rukuDanju);
    void setChuWei(String chuwei);
    void setCbDelStatu(boolean bl);
    void setSpSidDatas(List<SpSidBean> beans);
    void setCombineBarCode(JSONObject o);
    boolean getCheckStu();
    void delHeBaoData(boolean isResetCheckBox);

}
