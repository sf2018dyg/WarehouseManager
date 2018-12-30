package com.soonfor.warehousemanager.module.querybyscan.unscan;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.dao.BarItemBeanDao;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.module.instore.beans.BarItemBean;
import com.soonfor.warehousemanager.module.instore.flowtype.FlowTypeBean;
import com.soonfor.warehousemanager.module.outstore.beans.Out2BarItemBean;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.JsonUtils;
import com.soonfor.warehousemanager.view.TableViewDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.jesse.nativelogger.NLogger;

import static com.soonfor.warehousemanager.SoonforApplication.bar2Map;

public class UnScanBarCodeActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    @BindView(R.id.tablell)
    LinearLayout tablell;
    FlowTypeBean stBean;
    String model;
    String logistbatchno;
    String title = "{\"fShowSeqSNo\":\"编号\",\"fBarCode\":\"条码\",\"fOrdNo\":\"订单号\",\"fSplitBatchNo\":\"拆分批次\"," +
            "\"fProdNo\":\"生产单号\",\"fSpCode\":\"分包号\"}";

    BarItemBeanDao baritemDao;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_un_scan_bar_code;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText(getResources().getString(R.string.notscan_query));
        setDatas(new ArrayList<String[]>());
        stBean = getIntent().getExtras().getParcelable("SELECT_STORETYPE");
        model = getIntent().getStringExtra("MODEL");
        logistbatchno = getIntent().getStringExtra("logistbatchno");
        try {
            if (model.equals("instore")) {//分包分拣、分包入库、生产分包入库
                baritemDao = SoonforApplication.mDaoSession.getBarItemBeanDao();
                JSONArray jr = new JSONArray();
                List<BarItemBean> beans = baritemDao.loadAll();
                for (BarItemBean b : beans) {
                    JSONObject o = new JSONObject();
                    o.put("fOrdNo", b.getFOrdNo());
                    o.put("fBarCode", b.getFBarCode());
                    jr.put(o);
                }
                if (stBean.get_id().equals("0")) {
                    emptyView.show(true);
                    Request.StkScanByPackNoScan(this, "29", "",jr, this);
                } else if (stBean.get_id().equals("1")) {
                    emptyView.show(true);
                    Request.StkScanByPackNoScan(this, "1", "", jr, this);
                } else if (stBean.get_id().equals("3")) {
                    emptyView.show(true);
                    Request.StkScanByPackNoScan(this, "1", "", jr, this);
                }
            } else if (model.equals("outstore")) {//分包备货
                JSONArray jr = new JSONArray();
                for (Map.Entry<String, Out2BarItemBean> entry : bar2Map.entrySet()){
                    Out2BarItemBean b = entry.getValue();
                    JSONObject o = new JSONObject();
                    o.put("fOrdNo", b.getfOrdNo());
                    o.put("fBarCode", b.getfBarCode());
                    jr.put(o);
                }
                emptyView.show(true);
                if (stBean.get_id().equals("0")) {
                    Request.StkScanByPackNoScan(this, "11", logistbatchno, jr, this);
                }else if(stBean.get_id().equals("1")){
                    Request.StkScanByPackNoScan(this, "2", logistbatchno, jr, this);
                }
            }
        } catch (Exception e) {
            NLogger.e(e.getMessage());
        }
    }

    private void setDatas(List<String[]> datas) {
        try {
            tablell.removeAllViews();
            if (datas.size() > 0) {
                emptyView.hide();
            }else{
                emptyView.show("没有数据","");
            }
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title));
            int[] tableWith = CommUtil.SetComWidth(UnScanBarCodeActivity.this, titleMap.get(1).length, 0, false);
            TableViewDetail tableView = new TableViewDetail(UnScanBarCodeActivity.this,
                    tablell, titleMap.get(1), datas, tableWith, false, null);
        } catch (Exception e) {
            NLogger.w(e.getMessage());
        }
    }


    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();
        if (requestCode == Request.STKSCANBYPACKNOSCAN) {
            try {
                JSONObject head = new JSONObject(data);
                if (head.getBoolean("success")) {
                    JSONObject jo = new JSONObject(head.getString("data"));
                    String item = jo.getString("item");

                    List<unScanBean> beans = gson.fromJson(item, new TypeToken<List<unScanBean>>() {
                    }.getType());
                    if (beans != null && beans.size() > 0) {
                        makeData(beans);
                    } else {
                        emptyView.show("请求成功", "查无未扫条码信息");
                    }
                } else {
                    emptyView.show("请求失败", head.getString("errormsg"));
                }
            } catch (Exception e) {
                NLogger.w(e.getMessage());
            }
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String data, String msg) {
        hideQMTipLoading();
        Toast.makeText(UnScanBarCodeActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    public void makeData(List<unScanBean> beans) {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title));
            List<String[]> datas = new ArrayList<>();
            for (unScanBean qq : beans) {
                String[] ss = new String[titleMap.get(1).length];
                ss[0] = qq.getfShowSeqSNo() + "";
                ss[1] = qq.getfBarCode();
                ss[2] = qq.getfOrdNo();
                ss[3] = qq.getfSplitBatchNo();
                ss[4] = qq.getfProdNo();
                ss[5] = qq.getfSpCode();
                datas.add(ss);
            }
            setDatas(datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //{"fsel":true,"fShowSeqSNo":1,"fBarCode":"01804033056","fOrdNo":"BSX0161-00276-0","fSplitBatchNo":"BSX0161-00276-0",
    // "fProdNo":"H454283","fSpCode":3,"fBelongGoodsID":41204,"fBelongGoodsCode":"321030017",
    // "fBelongGoodsName":"单配TM02L(带L)帽头","fBelongCstlotNo":"20180406061294",
    // "fID":1866939,"fIfHide":"1"}
    class unScanBean {
        private boolean fsel;
        private long fShowSeqSNo;
        private String fBarCode;
        private String fOrdNo;
        private String fSplitBatchNo;
        private String fProdNo;
        private String fSpCode;
        private long fBelongGoodsID;
        private String fBelongGoodsCode;
        private String fBelongGoodsName;
        private String fBelongCstlotNo;
        private long fID;
        private String fIfHide;

        public boolean isFsel() {
            return fsel;
        }

        public void setFsel(boolean fsel) {
            this.fsel = fsel;
        }

        public long getfShowSeqSNo() {
            return fShowSeqSNo;
        }

        public void setfShowSeqSNo(long fShowSeqSNo) {
            this.fShowSeqSNo = fShowSeqSNo;
        }

        public String getfBarCode() {
            return fBarCode;
        }

        public void setfBarCode(String fBarCode) {
            this.fBarCode = fBarCode;
        }

        public String getfOrdNo() {
            return fOrdNo;
        }

        public void setfOrdNo(String fOrdNo) {
            this.fOrdNo = fOrdNo;
        }

        public String getfSplitBatchNo() {
            return fSplitBatchNo;
        }

        public void setfSplitBatchNo(String fSplitBatchNo) {
            this.fSplitBatchNo = fSplitBatchNo;
        }

        public String getfProdNo() {
            return fProdNo;
        }

        public void setfProdNo(String fProdNo) {
            this.fProdNo = fProdNo;
        }

        public String getfSpCode() {
            return fSpCode;
        }

        public void setfSpCode(String fSpCode) {
            this.fSpCode = fSpCode;
        }

        public long getfBelongGoodsID() {
            return fBelongGoodsID;
        }

        public void setfBelongGoodsID(long fBelongGoodsID) {
            this.fBelongGoodsID = fBelongGoodsID;
        }

        public String getfBelongGoodsCode() {
            return fBelongGoodsCode;
        }

        public void setfBelongGoodsCode(String fBelongGoodsCode) {
            this.fBelongGoodsCode = fBelongGoodsCode;
        }

        public String getfBelongGoodsName() {
            return fBelongGoodsName;
        }

        public void setfBelongGoodsName(String fBelongGoodsName) {
            this.fBelongGoodsName = fBelongGoodsName;
        }

        public String getfBelongCstlotNo() {
            return fBelongCstlotNo;
        }

        public void setfBelongCstlotNo(String fBelongCstlotNo) {
            this.fBelongCstlotNo = fBelongCstlotNo;
        }

        public long getfID() {
            return fID;
        }

        public void setfID(long fID) {
            this.fID = fID;
        }

        public String getfIfHide() {
            return fIfHide;
        }

        public void setfIfHide(String fIfHide) {
            this.fIfHide = fIfHide;
        }
    }
}
