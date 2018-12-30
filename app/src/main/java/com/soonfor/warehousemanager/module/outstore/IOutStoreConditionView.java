package com.soonfor.warehousemanager.module.outstore;

import com.soonfor.warehousemanager.bases.IBaseView;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/26 0026 16:02
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public interface IOutStoreConditionView extends IBaseView {
    void showUILoading();
    void hideUILoading();
    void setSuccessMsg(String msg);
    void setErrorMsg(int code, String msg);

    void setSuccessResult(String msg);
    void setFailResult(String msg);
    void setZhuiJiaDingdan(String orditem);

}
