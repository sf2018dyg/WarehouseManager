package com.soonfor.warehousemanager.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/17 0017 14:48
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class NoQiTaoUIActivity extends BaseActivity {

    @BindView(R.id.txtmiaoshu)
    TextView txtmiaoshu;
    @BindView(R.id.tablell)
    LinearLayout tablell;
    @BindView(R.id.txt_confirm)
    TextView txt_confirm;
    String type;
    String miaoshu;
    String biaoti;
    String tableData;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_no_qi_tao_ui;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        type = getIntent().getExtras().getString("TYPE");
        miaoshu = getIntent().getExtras().getString("MIAOSHU");
        biaoti = "{\"fOrdNo\":\"订单号\",\"fProdNo\":\"生产单号\",\"fTotalPackQty\":\"总包数\",\"fSortingPackQty\":\"已分拣数\"," + "\"fUnScanPackQty\":\"未扫描包数\",\"fSplitBatchNo\":\"拆单号\",\"fBarCode\":\"条码\"}";
        tableData = getIntent().getExtras().getString("DATATABLES");

        txtmiaoshu.setText(miaoshu + "");

        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(biaoti));
            List<String[]> datas = new ArrayList<>();
            JSONArray jr = new JSONArray(tableData);
            if (jr.length() > 0) {
                for (int i = 0; i < jr.length(); i++) {
                    JSONObject jo = new JSONObject(jr.get(i).toString());
                    String[] ss = new String[titleMap.get(1).length];
                    ss[0] = jo.getString("fOrdNo");
                    ss[1] = jo.getString("fProdNo");
                    ss[2] = CommUtil.formatInteger(jo.getString("fTotalPackQty"));
                    if (type.equals("0")) {
                        ss[3] = CommUtil.formatInteger(jo.getString("fSortingPackQty"));
                    } else if (type.equals("1")) {
                        ss[3] = CommUtil.formatInteger(jo.getString("fStkInPackQty"));
                    }
                    ss[4] = CommUtil.formatInteger(jo.getString("fUnScanPackQty"));
                    ss[5] = CommUtil.formatInteger(jo.getString("fSplitBatchNo"));
                    ss[6] = CommUtil.formatInteger(jo.getString("fBarCode"));
                    datas.add(ss);
                }
                tablell.removeAllViews();

                int[] tableWith = CommUtil.SetComWidth(NoQiTaoUIActivity.this, titleMap.get(1).length, 0, false);
                TableViewDetail tableView = new TableViewDetail(NoQiTaoUIActivity.this,
                        tablell, titleMap.get(1), datas, tableWith, false, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.txt_confirm})
    public void OnViewClick(View view) {
        if (view.getId() == R.id.txt_confirm) {
            finish();
        }
    }
}
