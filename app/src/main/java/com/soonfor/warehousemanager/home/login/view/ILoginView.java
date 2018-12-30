package com.soonfor.warehousemanager.home.login.view;

import com.soonfor.warehousemanager.bases.IBaseView;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public interface ILoginView extends IBaseView {

    void saveSerAddress(String address);
    void successLogin();
    //void isRemember(boolean checked, String password);
}
