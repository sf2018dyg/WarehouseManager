package com.soonfor.warehousemanager.module.instore;

import com.soonfor.warehousemanager.bases.IBaseView;

/**
 * 作者：DC-DingYG on 2018-07-24 15:46
 * 邮箱：dingyg012655@126.com
 */
public interface IInStoreConditionView extends IBaseView {
    void showUILoading();
    void hideUILoading();
    void setErrorMsg(int code, String msg);
    void setSuccessResult(String msg);
    void setFailResult(String msg);
}
