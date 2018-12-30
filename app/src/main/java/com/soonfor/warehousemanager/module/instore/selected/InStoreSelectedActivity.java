package com.soonfor.warehousemanager.module.instore.selected;

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
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.module.instore.beans.InStoreConditionBean;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.DoubleClickU;
import com.soonfor.warehousemanager.tools.ViewTools;
import com.soonfor.warehousemanager.view.TableViewDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/7 0007 11:45
 * 邮箱：suibozhu@139.com
 * 类用途：入库选择的界面
 */
public class InStoreSelectedActivity extends BaseActivity<InStoreSelectedPresenter> implements ISelectedView {

    @BindView(R.id.tvfTitile)
    TextView tvfTitile;
    @BindView(R.id.textV)
    TextView textV;
    @BindView(R.id.tablell)
    LinearLayout tablell;
    @BindView(R.id.etWithClear)
    EditTextWithClear etWithClear;
    @BindView(R.id.cbDel)
    CheckBox cbDel;
    private InStoreSelectedPresenter presenter;
    private TableViewDetail tableView;
    public StoreBean storeBean;//选中的仓库
    private String[] titles;//列表标题
    private List<String[]> data;//数据

    public static String[] selectedDanju, selectedChuwei, selectedYuanyin, selectedDept, selectedRukuDan;//选中的值
    public static ArrayList<String> selectedDingdans;
    private String optionType = "";//选项类型
    public static InStoreConditionBean selectConditon;//已选的条件代号

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_selected;
    }

    @Override
    protected void initPresenter() {
        storeBean = Hawk.get(UserInfo.SELECTSTORE, null);
        presenter = new InStoreSelectedPresenter(this);
        cbDel.setVisibility(View.GONE);
    }

    @Override
    protected void initViews() {
        storeBean = Hawk.get(UserInfo.SELECTSTORE, null);
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
            optionType = bundle.getString("in_source", "");
            selectConditon = (InStoreConditionBean) bundle.getSerializable("IN_SELECTCONDITON");
        }catch (Exception e){
            selectConditon = new InStoreConditionBean();
        }
        if (optionType.equals("inStore@bill")) {
            tvfTitile.setText("选择单据");
            textV.setText("单据号");
            emptyView.show(true);
            presenter.GetReceiptCodeList(InStoreSelectedActivity.this);
        } else if (optionType.equals("inStore@chuwei")) {
            tvfTitile.setText("选择储位");
            textV.setText("储位代号");
            if (storeBean != null) {
                emptyView.show(true);
                presenter.GetPlaceCodeList(InStoreSelectedActivity.this, storeBean.getfStkCode());
            }
        } else if (optionType.equals("inStore@reason")) {
            tvfTitile.setText("选择原因");
            textV.setText("原因代号");
            if (selectConditon != null && !selectConditon.getFbillCode().equals("")) {
                emptyView.show(true);
                presenter.GetReasonCodeList(InStoreSelectedActivity.this, selectConditon.getFbillCode());
            } else {
                Toast.makeText(InStoreSelectedActivity.this, "请先选择单据", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (optionType.equals("inStore@dept")) {
            tvfTitile.setText("选择部门");
            textV.setText("部门代号");
            if (storeBean != null) {
                emptyView.show(true);
                presenter.GetDept(InStoreSelectedActivity.this, storeBean.getfStkCode());
            }
        } else if (optionType.equals("inStore@rukubill")) {
            tvfTitile.setText("选择入库单");
            textV.setText("入库单");
            if (storeBean != null) {
                emptyView.show(true);
                presenter.GetStkInNoListPDA(InStoreSelectedActivity.this, storeBean.getfStkCode());
            }
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
        this.titles = titles;
        if (tableView != null) {
            tablell.removeAllViews();
        }
        String selectCode = "";
        switch (index){
            case 0://单据类型
                selectCode = selectConditon.getFbillCode();
                break;
            case 1://储位
                selectCode = selectConditon.getFbinlocationCode();
                break;
            case 2://原因
                selectCode = selectConditon.getFreasonCode();
                break;
            case 3://部门
                selectCode = selectConditon.getFdeptCode();
                break;
            case 4://入库单
                selectCode = selectConditon.getFrukubillCode();
                break;
        }
        int[] tableWith = CommUtil.SetComWidth(InStoreSelectedActivity.this, this.titles.length, 0, true);
        if (index == 0) {//如果是单据, 则要根据模块来判断是否含有PMS107记录
            List<String[]> newStringArray = new ArrayList<>();
            for (String[] ss : data) {
                if (ss[1].equals("PMS107")) {
                } else {
                    newStringArray.add(ss);
                }
            }
            this.data = newStringArray;
        } else {
            this.data = data;
        }

        tableView = new TableViewDetail(InStoreSelectedActivity.this,
                tablell, this.titles, this.data, tableWith, true, checkedChangeListener);
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (textV.getText().toString().contains("单据")) {
                selectedDanju = ViewTools.setCheckbox(buttonView, isChecked, tableView, true);
            } else if (textV.getText().toString().contains("储位")) {
                selectedChuwei = ViewTools.setCheckbox(buttonView, isChecked, tableView, true);
            } else if (textV.getText().toString().contains("原因")) {
                selectedYuanyin = ViewTools.setCheckbox(buttonView, isChecked, tableView, true);
            } else if (textV.getText().toString().contains("部门")) {
                selectedDept = ViewTools.setCheckbox(buttonView, isChecked, tableView, true);
            } else if (textV.getText().toString().contains("入库单")) {
                selectedRukuDan = ViewTools.setCheckbox(buttonView, isChecked, tableView, true);
            }
        }
    };

    @OnClick({R.id.txt_confirm})
    public void OnViewClick(View view) {
        if (view.getId() == R.id.txt_confirm) {
            Intent intent = new Intent();
            if (textV.getText().toString().contains("单据")) {
                intent.putExtra("selected", selectedDanju);
                setResult(666, intent);
            } else if (textV.getText().toString().contains("储位")) {
                intent.putExtra("selected", selectedChuwei);
                setResult(667, intent);
            } else if (textV.getText().toString().contains("原因")) {
                intent.putExtra("selected", selectedYuanyin);
                setResult(668, intent);
            } else if (textV.getText().toString().contains("部门")) {
                intent.putExtra("selected", selectedDept);
                setResult(673, intent);
            } else if (textV.getText().toString().contains("入库单")) {
                intent.putExtra("selected", selectedRukuDan);
                setResult(674, intent);
            }
            finish();
        }
    }

    private void newDatas(String key) {
        if (textV.getText().toString().contains("单据")) {
            resetView(1, key);
        } else if (textV.getText().toString().contains("储位")) {
            resetView(1, key);
        } else if (textV.getText().toString().contains("原因")) {
            resetView(1, key);
        } else if (textV.getText().toString().contains("部门")) {
            resetView(1, key);
        } else if (textV.getText().toString().contains("入库单")) {
            resetView(1, key);
        }
    }

    private void resetView(int index, String key) {
        tablell.removeAllViews();
        List<String[]> newd = new ArrayList<>();
        if (key.equals("")) {
            newd = this.data;
        } else {
            for (String[] s : this.data) {
                if (s[index].toLowerCase().contains(key.toLowerCase())) {
                    newd.add(s);
                }
            }
        }

        int[] tableWith = CommUtil.SetComWidth(InStoreSelectedActivity.this, this.titles.length, 0, true);
        tableView = new TableViewDetail(InStoreSelectedActivity.this,
                tablell, this.titles, newd, tableWith, true, checkedChangeListener);
    }
}
