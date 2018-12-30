package com.soonfor.warehousemanager.home.main;

import com.soonfor.warehousemanager.bases.IBaseView;
import com.soonfor.warehousemanager.module.instore.flowtype.FlowTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-07-23 18:19
 * 邮箱：dingyg012655@126.com
 */
public interface IMainView extends IBaseView{
    void showGrildView(List<GridItemBean> gibList);
    void setGetTypes(String button_type, ArrayList<FlowTypeBean> stList);
    void setLogout(boolean isSuccess);
}
