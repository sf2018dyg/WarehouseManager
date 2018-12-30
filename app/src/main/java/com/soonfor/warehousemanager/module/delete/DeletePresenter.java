package com.soonfor.warehousemanager.module.delete;

import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/26 0026 15:56
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class DeletePresenter extends BasePresenter<IDeleteView> implements AsyncUtils.AsyncCallback {

    private IDeleteView view;

    public DeletePresenter(IDeleteView view) {
        this.view = view;
    }

    public void getDatas() {
        String[] titles = new String[]{"订单号", "生产单号", "在库包数", "总包数", "本次扫描包"};
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"CP03052653", "CP03052653", "1", "1", "1"});
        data.add(new String[]{"CP03052653", "CP03052653", "2", "1", "1"});
        data.add(new String[]{"CP03052653", "CP03052653", "3", "1", "1"});
        data.add(new String[]{"CP03052653", "CP03052653", "4", "1", "1"});
        view.setDatas(titles, data);
    }

    @Override
    public void success(int requestCode, String data) {
        switch (requestCode) {

        }
    }

    @Override
    public void fail(int requestCode, int statusCode,String data, String msg) {
        view.setErrorMsg(statusCode, msg);
    }
}
