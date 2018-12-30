package com.soonfor.warehousemanager.module.print;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/9/22 0022 10:11
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class PrinterPresenter extends BasePresenter<IPrinterView> implements AsyncUtils.AsyncCallback {

    IPrinterView view;
    String title0;

    public PrinterPresenter(IPrinterView view) {
        this.view = view;

        title0 = "{\"fBarCode\":\"条码代号\",\"fOrdNo\":\"订单号\",\"fSPCode\":\"分包号\",\"fPackMark\":\"分包说明\"}";
    }

    public void getPrintBarData(Context context, String barcode) {
        Request.GetPrintBarDataPda(context, barcode, 0,this);
    }

    //请求打印
    public void StkScanByPackPrintBar(Context context, String printername, String schmname, JSONArray jr) {
        Request.StkScanByPackPrintBar(context, printername, schmname, "", jr, this);
    }

    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.GETPRINTBARDATAPDA:
                try {
                    JSONObject head = new JSONObject(data);
                    if (head.getBoolean("success")) {
                        JSONObject jo = new JSONObject(head.getString("data"));
                        JSONArray jr = new JSONArray(jo.getString("item"));
                        if (jr.length() > 0) {
                            List<String[]> d = new ArrayList<>();
                            for (int i = 0; i < jr.length(); i++) {
                                JSONObject oo = new JSONObject(jr.get(i).toString());
                                String[] ss = new String[4];
                                ss[0] = oo.getString("fBarCode");
                                ss[1] = oo.getString("fOrdNo");
                                ss[2] = oo.getString("fSPCode");
                                ss[3] = oo.getString("fPackMark");
                                d.add(ss);
                            }
                            view.setDatas(0, new String[]{"条码代号", "订单号", "分包号", "分包说明"}, d);
                            view.SuccessSound();
                        }
                    } else {
                        view.setErrorMsg(-1, "获取条码失败: " + head.getString("errormsg"));
                        view.FaileSound();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    view.hideUILoading();
                    view.FaileSound();
                }
                break;
            case Request.STKSCANBYPACKPRINTBAR:
                try {
                    JSONObject head = new JSONObject(data);
                    if (head.getBoolean("success")) {
                        JSONObject jo = new JSONObject(head.getString("data"));
                        view.setSuccessResult("打印成功");
                        view.clearAddDatas();
                    } else {
                        view.setErrorMsg(-1, "打印失败: " + head.getString("errormsg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String data, String msg) {
        NLogger.w(statusCode + " " + data + " " + msg);
        view.setFailResult(statusCode + " " + data + " " + msg);
        view.hideUILoading();
    }

}
