package com.soonfor.warehousemanager.module.instore.selected;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.dao.PrinterBeanDao;
import com.soonfor.warehousemanager.dao.PrinterReadyBeanDao;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.module.outstore.selected.OutStoreSelectedActivity;
import com.soonfor.warehousemanager.module.print.bean.PrinterBean;
import com.soonfor.warehousemanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/26 0026 15:56
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class InStoreSelectedPresenter extends BasePresenter<ISelectedView> implements AsyncUtils.AsyncCallback {

    private ISelectedView view;
    public PrinterReadyBeanDao printerReadyBeanDao;
    public PrinterBeanDao printerBeanDao;

    public InStoreSelectedPresenter(ISelectedView view) {
        this.view = view;
        printerReadyBeanDao = SoonforApplication.mDaoSession.getPrinterReadyBeanDao();
        printerBeanDao = SoonforApplication.mDaoSession.getPrinterBeanDao();

    }


    //所有单据类型列表
    public void GetReceiptCodeList(Context context) {
        Request.GetReceiptCodeList(context, this);
    }

    //所有储位列表
    public void GetPlaceCodeList(Context context, String fstkcode) {
        Request.GetPlaceCodeList(context, fstkcode, this);
    }

    //所有出入库原因列表
    public void GetReasonCodeList(Context context, String freceiptcode) {
        Request.GetReasonCodeList(context, freceiptcode, this);
    }

    //部门列表
    public void GetDept(Context context, String stkcode) {
        Request.GetDeptCodeList(context, stkcode, this);
    }

    //入库单
    public void GetStkInNoListPDA(Context context, String stkcode) {
        Request.GetStkInNoListPDA(context, stkcode, this);
    }

    //获取所有未打印的条码
    public void LoadUnPrintlnBarCodes() {
        try {
            String[] titles = new String[]{"条码", "订单号", "分包名称", "合包时间", "合包人"};
            List<String[]> list = new ArrayList<>();
            List<PrinterBean> ll = printerBeanDao.loadAll();
            if (ll != null && ll.size() > 0) {
                for (PrinterBean pb : ll) {
                    String[] tmp = new String[]{pb.getFBarCode(), pb.getFOrdNo(), pb.getFSPName(), pb.getHeBaoTime(), pb.getHeBaoUser()};
                    list.add(tmp);
                }
                view.setDatas(5, titles, list);
            } else {
                list = new ArrayList<>();
                view.setDatas(5, titles, list);
            }
        } catch (Exception e) {
            NLogger.w("获取本地未打印条码的列表失败, " + e.getMessage());
        }
    }

    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.GETRECEIPTCODELISTPDA://单据类型
                setView(0, data);
                break;
            case Request.GETPLACECODELIST://储位
                setView(1, data);
                break;
            case Request.GETREASONCODELISTPDA://原因
                setView(2, data);
                break;
            case Request.GETDEPTCODELIST://部门
                setView(3, data);
                break;
            case Request.GETSTKINNOLISTPDA://入库单
                setView(4, data);
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String data, String msg) {
        view.setErrorMsg(statusCode, msg);
    }

    /**
     *
     * @param index
     * @param data
     */
    private void setView(int index, String data) {
        try {
            JSONObject jo = new JSONObject(data);
            String title = jo.getString("title");
            String item = jo.getString("item");
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(true, new JSONObject((new JSONArray(title)).get(0).toString()));
            List<String[]> datas = null;
            switch (index){
                case 0:
                    datas = JsonUtils.getColumByTitleMap(true, titleMap.get(0), item, 1, InStoreSelectedActivity.selectConditon.getFbillCode());
                    break;
                case 1:
                    datas = JsonUtils.getColumByTitleMap(true, titleMap.get(0), item, 1, InStoreSelectedActivity.selectConditon.getFbinlocationCode());
                    break;
                case 2:
                    datas = JsonUtils.getColumByTitleMap(true, titleMap.get(0), item, 1, InStoreSelectedActivity.selectConditon.getFreasonCode());
                    break;
                case 3:
                    datas = JsonUtils.getColumByTitleMap(true, titleMap.get(0), item, 2, InStoreSelectedActivity.selectConditon.getFdeptCode());
                    break;
                case 4:
                    datas = JsonUtils.getColumByTitleMap(true, titleMap.get(0), item, 1, InStoreSelectedActivity.selectConditon.getFrukubillCode());
                    break;
            }
            view.setDatas(index, titleMap.get(1), datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
