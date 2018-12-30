package com.soonfor.warehousemanager.module.outstore.selected;

import java.util.List;

public interface IOutStoreSelectedView {
    void showUILoading();

    void hideUILoading();

    void setErrorMsg(int code, String msg);

    void setDatas(int index, String[] titles, List<String[]> data);
}
