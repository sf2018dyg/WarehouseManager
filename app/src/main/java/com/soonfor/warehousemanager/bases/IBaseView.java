package com.soonfor.warehousemanager.bases;


/**
 * Created by long on 2016/8/23.
 * 基础 BaseView 接口
 */
public interface IBaseView {

    void showLoadingDialog();

    void closeLoadingDialog();

    /**
     * 下拉刷新
     */
    void RefreshData(boolean isRefresh);

    /**
     * 完成刷新, 新增控制刷新
     */
    void finishRefresh();

    /**
     * 有数据时刷新界面
     */
    void showDataToView(String returnJson);

    /**
     * 无数据时显示界面提示
     */
    void showNoDataHint(String msg);

}
