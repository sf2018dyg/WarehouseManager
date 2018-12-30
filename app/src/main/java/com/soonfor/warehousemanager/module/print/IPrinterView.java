package com.soonfor.warehousemanager.module.print;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/9/22 0022 10:11
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public interface IPrinterView {
    void setDatas(int index, String[] titles, List<String[]> data);
    void showUILoading();
    void hideUILoading();
    void setErrorMsg(int code, String msg);
    void setSuccessResult(String msg);
    void setFailResult(String msg);
    void SuccessSound();
    void RepatSound();
    void FaileSound();
    void clearAddDatas();
}
