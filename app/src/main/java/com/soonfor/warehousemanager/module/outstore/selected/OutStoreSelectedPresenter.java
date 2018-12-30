package com.soonfor.warehousemanager.module.outstore.selected;

import android.content.Context;

import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.dao.PrinterBeanDao;
import com.soonfor.warehousemanager.dao.PrinterReadyBeanDao;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.module.instore.selected.ISelectedView;
import com.soonfor.warehousemanager.module.outstore.OutStoreConditionActivity;
import com.soonfor.warehousemanager.module.outstore.beans.OutStoreConditonBean;
import com.soonfor.warehousemanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 作者：DC-DingYg on 2018/12/17
 * 类用途：
 */
public class OutStoreSelectedPresenter extends BasePresenter<IOutStoreSelectedView> implements AsyncUtils.AsyncCallback {

    private IOutStoreSelectedView view;
    private PrinterReadyBeanDao printerReadyBeanDao;
    private PrinterBeanDao printerBeanDao;
    private String fbarcode;


    public OutStoreSelectedPresenter(IOutStoreSelectedView view) {
        this.view = view;
        printerReadyBeanDao = SoonforApplication.mDaoSession.getPrinterReadyBeanDao();
        printerBeanDao = SoonforApplication.mDaoSession.getPrinterBeanDao();

    }

    //物流批次  GETLOGISTBATCHNOLISTPDA
    public void GetLogistBatchNoList(Context context) {
        Request.GetLogistBatchNoList(context, this);
    }

    //车牌信息 GETCARCARDNOLISTPDA
    public void GetCarcardNoList(Context context, String flogistbatchno) {
        Request.GetCarcardNoList(context, flogistbatchno, this);
    }

    //订单追加 GETORDERNOLISTPDA
    public void GetOrderNoList(Context context, String logistbatchno) {
        Request.GetOrderNoList(context, logistbatchno, this);
    }

    //出货通知单 GETDLVNOLIST
    public void GetDlvNotionList(Context context, String stkcode) {
        Request.GetDlvNotionList(context, stkcode, this);
    }

    @Override
    public void success(int requestCode, String data) {
        switch (requestCode) {
            case Request.GETLOGISTBATCHNOLISTPDA:
                setView(1, data);
                break;
            case Request.GETCARCARDNOLISTPDA:
                setView(2, data);
                break;
            case Request.GETORDERNOLISTPDA:
                setView(3, data);
                break;
            case Request.GETDLVNOLIST:
                setView(4, data);
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String data, String msg) {
        view.setErrorMsg(statusCode, msg);
    }

    private void setView(int index, String data) {
        try {
            JSONObject jo = new JSONObject(data);
            String title = jo.getString("title");
            String item = jo.getString("item");
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(true, new JSONObject((new JSONArray(title)).get(0).toString()));
            List<String[]> datas = null;
            switch (index){
                case 1:
                    datas = JsonUtils.getColumByTitleMap(true, titleMap.get(0), item, 1, OutStoreSelectedActivity.selectConditon.getfLogistBatchNo());
                    break;
                case 2:
                    datas = JsonUtils.getColumByTitleMap(true, titleMap.get(0), item, 2, OutStoreSelectedActivity.selectConditon.getfCarCardNo());
                    break;
                case 3:
                    datas = JsonUtils.getColumByTitleMap(true, titleMap.get(0), item, 0, "");
                    break;
                case 4:
                    datas = JsonUtils.getColumByTitleMap(true, titleMap.get(0), item, 1, OutStoreSelectedActivity.selectConditon.getfShippingOrder());
                    break;
            }
            view.setDatas(index, titleMap.get(1), datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
