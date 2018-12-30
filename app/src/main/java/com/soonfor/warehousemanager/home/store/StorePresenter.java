package com.soonfor.warehousemanager.home.store;

import android.app.Activity;
import android.content.Context;

import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.bases.DataBean;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 作者：DC-DingYG on 2018-07-23 13:41
 * 邮箱：dingyg012655@126.com
 */
public class StorePresenter extends BasePresenter<IStoreView> implements AsyncUtils.AsyncCallback {

    private IStoreView view;

    public StorePresenter(IStoreView view) {
        this.view = view;
    }

    public void getData(Activity context, boolean isRefresh) {
        if (isRefresh) {
            view.showLoadingDialog();
        }
        Request.getStores(context, this);
    }

    public void getMoreData() {

    }

    @Override
    public void success(int requestCode, String data) {
        view.closeLoadingDialog();
        switch (requestCode) {
            case Request.GETSTORES:
                try {
                    JSONObject jo = new JSONObject(data);
                    String title = jo.getString("title");
                    String item = jo.getString("item");
                    JSONArray jrItem = new JSONArray(item);
                    StoreActivity.sbList = new ArrayList<>();

                    for (int i = 0; i < jrItem.length(); i++) {
                        JSONObject o = new JSONObject(jrItem.get(i).toString());
                        StoreActivity.sbList.add(new StoreBean(o.getString("fStkCode"), o.getString("fStkName"), o.getInt("fIfUsePlace")));
                    }

                    if (StoreActivity.sbList.size() > 0) {
                        view.showDataToView(null);
                    } else {
                        view.showNoDataHint("暂无仓库信息");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode,String data, String msg) {
        view.showNoDataHint(msg);
    }
}
