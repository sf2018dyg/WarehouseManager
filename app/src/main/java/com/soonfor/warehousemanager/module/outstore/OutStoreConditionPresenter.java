package com.soonfor.warehousemanager.module.outstore;

import android.content.Context;

import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.module.outstore.beans.OutStoreConditonBean;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/26 0026 15:56
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class OutStoreConditionPresenter extends BasePresenter<IOutStoreConditionView> implements AsyncUtils.AsyncCallback{

    private IOutStoreConditionView view;

    public OutStoreConditionPresenter(IOutStoreConditionView view) {
        this.view = view;
    }
    //追加的订单 (可多选)
    public void StkOutByPackAddOrder(Context context, String logistbatchno, JSONArray data) {
        Request.StkOutByPackAddOrder(context, logistbatchno, data, this);
    }
    @Override
    public void success(int requestCode, String data) {
        switch (requestCode) {
            case Request.STKOUTBYPACKADDORDER:
                try {
                    /*{"Status":200,"Succeed":1,"Data":{"success":true,"msgcode":"0","errormsg":"","data":{"orditem":[]}},"ErrorMessage":null}*/
                    JSONObject head = new JSONObject(data);
                    if (head.getBoolean("success")) {
                        JSONObject o = new JSONObject(head.getString("data"));
                        JSONArray jr = new JSONArray(o.getString("orditemitem"));
                        if (jr.length() > 0) {
                            String ordnos = "";
                            String[] ordno = new String[jr.length()];
                            for (int i = 0; i < jr.length(); i++) {
                                JSONObject object = new JSONObject(jr.get(i).toString());
                                if (i == jr.length() - 1) {
                                    ordnos += object.getString("fOrdNo");
                                } else {
                                    ordnos += object.getString("fOrdNo") + ",";
                                }
                                ordno[i] = object.getString("fOrdNo");
                            }
                            view.setSuccessMsg("追加订单成功:" + ordnos);
                            view.setZhuiJiaDingdan(ordnos);
                        } else {
                            view.setErrorMsg(-1, "追加订单失败:\n请重新选择");
                        }
                    } else {
                        view.setErrorMsg(-1, "追加订单失败:\n" + head.getString("errormsg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setErrorMsg(-1, "数据结构返回有误");
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String data, String msg) {
       view.setErrorMsg(statusCode, msg);
    }

    /**
     * 这样设值 而不直接赋值 是为了防止改变静态变量
     * @param oldBean
     * @return
     */
    public OutStoreConditonBean getNewValue(OutStoreConditonBean oldBean){
        OutStoreConditonBean newBean = new OutStoreConditonBean();
        newBean.setfLogistBatchNo(oldBean.getfLogistBatchNo());
        newBean.setfLogistDirectCode(oldBean.getfLogistDirectName());
        newBean.setfLogistDirectName(oldBean.getfLogistDirectName());
        newBean.setfCarCardNo(oldBean.getfCarCardNo());
        newBean.setfLPCode(oldBean.getfLPCode());
        newBean.setfLPName(oldBean.getfLPName());
        newBean.setfAddOrder(oldBean.getfAddOrder());
        newBean.setfShippingOrder(oldBean.getfShippingOrder());
        newBean.setfCreatorID(oldBean.getfCreatorID());
        newBean.setfCreator(oldBean.getfCreator());
        return newBean;
    }
}
