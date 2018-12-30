package com.soonfor.warehousemanager.module.querybyscan;

import android.content.Context;

import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;

/**
 * 作者：DC-DingYG on 2018-07-25 9:44
 * 邮箱：dingyg012655@126.com
 */
public class QueryByScanPresenter extends BasePresenter<IQueryByScanView> implements AsyncUtils.AsyncCallback{
    private IQueryByScanView view;

    public QueryByScanPresenter(IQueryByScanView view) {
        this.view = view;
    }
    /**
     * 扫条码查询
     */
    public void getDataByScan(Context mContext, String fcode, String fbarType){
        Request.Fbarcode.getDataByScan(mContext, fcode,this);
    }

    @Override
    public void success(int requestCode, String data) {

    }

    @Override
    public void fail(int requestCode, int statusCode,String data, String msg) {

    }
}
