package com.soonfor.warehousemanager.home.setting;

import android.content.Context;

import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;

/**
 * 作者：DC-DingYG on 2018-07-24 8:50
 * 邮箱：dingyg012655@126.com
 */
public class SettingPresenter extends BasePresenter<ISettingView> implements AsyncUtils.AsyncCallback {
    private ISettingView view;

    public SettingPresenter(ISettingView view) {
        this.view = view;
    }

    /**
     *  //获取本机歌曲列表
     */
    public void getMp3List(Context context, boolean isRefresh){
        if(isRefresh) {
            view.showLoadingDialog();
        }
        MusicActivity.mp3List = MediaUtil.getMp3Infos(context);
        view.showDataToView(null);
    }
    @Override
    public void success(int requestCode, String data) {

    }

    @Override
    public void fail(int requestCode, int statusCode,String data, String msg) {

    }
}
