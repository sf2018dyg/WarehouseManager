package com.soonfor.warehousemanager.module.instore.selected;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.SerMap;
import com.soonfor.warehousemanager.tools.ViewTools;
import com.soonfor.warehousemanager.view.TableViewDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jesse.nativelogger.NLogger;

//合包未打印
public class SelectPrientActivity extends BaseActivity implements ISelectedView, AsyncUtils.AsyncCallback {

    @BindView(R.id.tvfTitile)
    TextView tvfTitile;
    /* @BindView(R.id.cbDel)
     CheckBox cbDel;*/
    private TableViewDetail tableView;
    @BindView(R.id.tablell)
    LinearLayout tablell;
    String[] titles;
    Map<String, String[]> okMap;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_select_prient;
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initViews() {
        //cbDel.setVisibility(View.GONE);
        tvfTitile.setText("合包未打印条码");
        okMap = new HashMap<>();
        emptyView.show(true);
        Request.GetPrintBarDataPda(this, "", 1, this);
    }

    @Override
    public void setDatas(int index, String[] titles, List<String[]> data) {
        emptyView.hide();
        this.titles = titles;

        if (tableView != null) {
            tablell.removeAllViews();
        }

        if (data.size() == 0) {
            emptyView.show("没有未打印的条码", "");
            return;
        }

        int[] tableWith = CommUtil.SetComWidth(SelectPrientActivity.this, this.titles.length, 0, true);
        tableView = new TableViewDetail(SelectPrientActivity.this,
                tablell, this.titles, data, tableWith, true, checkedChangeListener);
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String[] selected = ViewTools.setCheckbox(buttonView, isChecked, tableView, false);
            if (Boolean.valueOf(selected[0])) {
                okMap.put(selected[1], selected);
            } else {
                okMap.remove(selected[1]);
            }
        }
    };

    @OnClick({R.id.ivfLeft, R.id.txt_confirm})
    public void OnViewClick(View view) {
        if (view.getId() == R.id.ivfLeft) {
            finish();
        } else if (view.getId() == R.id.txt_confirm) {
            Intent intent = new Intent();
            SerMap map = new SerMap();
            map.setMap(okMap);
            Bundle bundle = new Bundle();
            bundle.putSerializable("OKMAP", map);
            intent.putExtras(bundle);
            setResult(6666, intent);
            finish();
        }
    }

    @Override
    public void showUILoading() {
        showQMTipLoading("请稍后", QMUITipDialog.Builder.ICON_TYPE_LOADING);
    }

    @Override
    public void hideUILoading() {
        hideQMTipLoading();
    }

    @Override
    public void setErrorMsg(int code, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        emptyView.show(msg, "");
    }

    @Override
    public void success(int requestCode, String data) {
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
                                String[] ss = new String[8];
                                ss[0] = "false";
                                ss[1] = oo.getString("fBarCode");
                                ss[2] = oo.getString("fOrdNo");
                                ss[3] = oo.getString("fSPCode");
                                ss[4] = oo.getString("fPackMark");
                                ss[5] = oo.getString("fScanorID");
                                ss[6] = oo.getString("fScanor");
                                ss[7] = oo.getString("fScanDate");
                                d.add(ss);
                            }
                            setDatas(0, new String[]{"选择", "条码代号", "订单号", "分包号", "分包说明", "合包人代号", "合包人", "合包日期"}, d);
                        }
                    } else {
                        setErrorMsg(-1, "获取条码失败: " + head.getString("errormsg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    hideUILoading();
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String data, String msg) {
        NLogger.w(statusCode + " " + data + " " + msg);
    }
}
