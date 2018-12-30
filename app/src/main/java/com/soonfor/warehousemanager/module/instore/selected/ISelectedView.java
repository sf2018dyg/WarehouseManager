package com.soonfor.warehousemanager.module.instore.selected;

import com.soonfor.warehousemanager.bases.IBaseView;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/26 0026 16:02
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public interface ISelectedView extends IBaseView {
    void showUILoading();

    void hideUILoading();

    void setErrorMsg(int code, String msg);

    void setDatas(int index, String[] titles, List<String[]> data);
}
