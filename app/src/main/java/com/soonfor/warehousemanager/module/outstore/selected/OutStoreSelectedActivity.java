package com.soonfor.warehousemanager.module.outstore.selected;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dingyg.edittextwithclear.EditTextWithClear;
import com.orhanobut.hawk.Hawk;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.home.store.StoreBean;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.module.instore.beans.InStoreConditionBean;
import com.soonfor.warehousemanager.module.instore.selected.ISelectedView;
import com.soonfor.warehousemanager.module.outstore.beans.OutStoreConditonBean;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.DoubleClickU;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.Tokens;
import com.soonfor.warehousemanager.tools.ViewTools;
import com.soonfor.warehousemanager.view.BaseQueryView;
import com.soonfor.warehousemanager.view.TableViewDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-DingYg on 2018/12/17
 * 类用途：
 * 类用途：出库条件选择界面
 */
public class OutStoreSelectedActivity extends BaseActivity<OutStoreSelectedPresenter> implements IOutStoreSelectedView {

    @BindView(R.id.tvfTitile)
    TextView tvfTitile;
    @BindView(R.id.textV)
    TextView textV;
    @BindView(R.id.queryView)
    BaseQueryView queryView;
    @BindView(R.id.tablell)
    LinearLayout tablell;
    @BindView(R.id.etWithClear)
    EditTextWithClear etWithClear;
    @BindView(R.id.cbDel)
    CheckBox cbDel;
    private TableViewDetail tableView;
    String[] titles;
    List<String[]> totalData;
    private String[] selectedPici = null, selectedChepai = null, shippingOrder = null;//物流批次， 车牌号， 出货通知单
    private ArrayList<String> selectedDingdans;
    private String optionType = "";//选项类型
    public static OutStoreConditonBean selectConditon;//已选的条件代号

    public static List<String[]> fbarBatchs;//由条码扫出的物流批次信息集合

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_selected;
    }

    @Override
    protected void initPresenter() {
        presenter = new OutStoreSelectedPresenter(this);
        cbDel.setVisibility(View.GONE);
    }

    @Override
    protected void initViews() {
        selectedDingdans = new ArrayList<>();
        etWithClear.addTextChangedListener(CommUtil.watcher);
        etWithClear.setOnEditorActionListener(0, new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!DoubleClickU.isFastDoubleClick()) {
                    EditText edt = (EditText) v;
                    String s = edt.getText().toString();
                    newDatas(s);
                }
                return true;
            }
        });
        Bundle bundle = getIntent().getExtras();
        try {
            optionType = bundle.getString("out_source", "");
            selectConditon = (OutStoreConditonBean) bundle.getSerializable("OUT_SELECTCONDITON");
        } catch (Exception e) {
            selectConditon = new OutStoreConditonBean();
        }
        if (optionType.equals("outStore@AllConditions")) {
            tvfTitile.setText("物流批次|车牌号|载物台");
            queryView.setVisibility(View.GONE);
            emptyView.show(true);
            if (fbarBatchs != null)
                setDatas(-1, null, fbarBatchs);
        } else if (optionType.equals("outStore@LogisticsBatch")) {
            tvfTitile.setText("选择物流批次");
            textV.setText("物流批次");
            emptyView.show(true);
            presenter.GetLogistBatchNoList(OutStoreSelectedActivity.this);
        } else if (optionType.equals("outStore@PlateNumber")) {
            tvfTitile.setText("选择车牌号");
            textV.setText("车牌号");
            emptyView.show(true);
            if (selectConditon != null && !selectConditon.getfLogistBatchNo().equals("")) {
                presenter.GetCarcardNoList(OutStoreSelectedActivity.this, selectConditon.getfLogistBatchNo());
            } else {
                Toast.makeText(OutStoreSelectedActivity.this, "请先选择物流批次", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (optionType.equals("outStore@AddOrder")) {
            tvfTitile.setText("选择订单号");
            textV.setText("订单号");
            emptyView.show(true);
            if (selectConditon != null && !selectConditon.getfLogistBatchNo().equals("")) {
                presenter.GetOrderNoList(OutStoreSelectedActivity.this, selectConditon.getfLogistBatchNo());
            } else {
                Toast.makeText(OutStoreSelectedActivity.this, "请先选择物流批次", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (optionType.equals("outStore@ShippingOrder")) {
            tvfTitile.setText("选择出货通知单");
            textV.setText("通知单号");
            emptyView.show(true);
            StoreBean storeBean = Hawk.get(UserInfo.SELECTSTORE, null);
            presenter.GetDlvNotionList(OutStoreSelectedActivity.this, storeBean==null?"":storeBean.getfStkCode());
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
        emptyView.show(msg, "");
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDatas(int index, String[] titles, List<String[]> data) {
        emptyView.hide();
        if (index == -1) {
            this.titles = new String[]{"选择", "物流批次", "车牌号", "载物台代号", "载物台", "建立人代号", "建立人"};
        } else {
            this.titles = titles;
        }
        if (tableView != null) {
            tablell.removeAllViews();
        }
        int[] tableWith = CommUtil.SetComWidth(OutStoreSelectedActivity.this, this.titles.length, 0, true);
        this.totalData = data;
        tableView = new TableViewDetail(OutStoreSelectedActivity.this,
                tablell, this.titles, this.totalData, tableWith, true, checkedChangeListener);
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (optionType.equals("outStore@AllConditions")) {
                selectedPici = ViewTools.setCheckbox(buttonView, isChecked, tableView, true);
            } else if (optionType.equals("outStore@LogisticsBatch")) {
                selectedPici = ViewTools.setCheckbox(buttonView, isChecked, tableView, true);
            } else if (optionType.equals("outStore@PlateNumber")) {
                selectedChepai = ViewTools.setCheckbox(buttonView, isChecked, tableView, true);
            } else if (optionType.equals("outStore@AddOrder")) {
                String[] selectedDingdan = ViewTools.setCheckbox(buttonView, isChecked, tableView, false);
                if (selectedDingdans == null) selectedDingdans = new ArrayList<>();
                selectedDingdans.add(selectedDingdan[1]);
            } else if (optionType.equals("outStore@ShippingOrder")) {
                shippingOrder = ViewTools.setCheckbox(buttonView, isChecked, tableView, true);
            }
        }
    };

    @OnClick({R.id.txt_confirm})
    public void OnViewClick(View view) {
        if (view.getId() == R.id.txt_confirm) {
            Intent intent = new Intent();
            if (optionType.equals("outStore@AllConditions")) {
//                if(selectedPici==null){
//                    MyToast.showToast(OutStoreSelectedActivity.this, "请至少选择一条数据");
//                    return;
//                }else {
                    intent.putExtra("selected", selectedPici);
                    setResult(Tokens.Putout.JUMPCODE_SCANFORLOGISTICS, intent);
//                }
            } else if (optionType.equals("outStore@LogisticsBatch")) {
                intent.putExtra("selected", selectedPici);
                setResult(Tokens.Putout.JUMPCODE_LOGISTICSBATCHS, intent);
            } else if (optionType.equals("outStore@PlateNumber")) {
                intent.putExtra("selected", selectedChepai);
                setResult(Tokens.Putout.JUMPCODE_PLATENUMBER, intent);
            } else if (optionType.equals("outStore@AddOrder")) {
                intent.putStringArrayListExtra("selected", selectedDingdans);
                setResult(Tokens.Putout.JUMPCODE_LOGISTICSBATCHS, intent);
            } else if (optionType.equals("outStore@ShippingOrder")) {
                intent.putExtra("selected", shippingOrder);
                setResult(Tokens.Putout.JUMPCODE_SHIPPINGORDER, intent);
            }
            finish();
        }
    }

    private void newDatas(String key) {
        if (optionType.equals("outStore@LogisticsBatch")) {
            resetView(1, key);
        } else if (optionType.equals("outStore@PlateNumber")) {
            resetView(2, key);
        } else if (optionType.equals("outStore@AddOrder")) {
            resetView(1, key);
        } else if (optionType.equals("outStore@ShippingOrder")) {
            resetView(1, key);
        }
    }

    private void resetView(int index, String key) {
        tablell.removeAllViews();
        List<String[]> newd = new ArrayList<>();
        if (key.equals("")) {
            newd = this.totalData;
        } else {
            for (String[] s : this.totalData) {
                if (s[index].toLowerCase().contains(key.toLowerCase())) {
                    newd.add(s);
                }
            }
        }
        int[] tableWith = CommUtil.SetComWidth(OutStoreSelectedActivity.this, this.titles.length, 0, true);
        tableView = new TableViewDetail(OutStoreSelectedActivity.this,
                tablell, this.titles, newd, tableWith, true, checkedChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fbarBatchs = null;
    }
}
