package com.soonfor.warehousemanager.module.print;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dingyg.edittextwithclear.EditTextWithClear;
import com.orhanobut.hawk.Hawk;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.module.print.bean.PrinterBean;
import com.soonfor.warehousemanager.module.instore.selected.SelectPrientActivity;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.DoubleClickU;
import com.soonfor.warehousemanager.tools.SerMap;
import com.soonfor.warehousemanager.tools.SoundUtil;
import com.soonfor.warehousemanager.view.TableViewDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PrinterActivity extends BaseActivity<PrinterPresenter> implements IPrinterView {

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    PrinterPresenter presenter;
    @BindView(R.id.cbDel)
    CheckBox cbDel;
    List<PrinterBean> beans;
    @BindView(R.id.tablell)
    LinearLayout tablell;
    @BindView(R.id.etWithClear)
    EditTextWithClear etWithClear;
    TableViewDetail tableView;

    private long lastscanTime = 0;//处理图森pda扫描时出现重复回车现象
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_printer;
    }

    @Override
    protected void initPresenter() {
        presenter = new PrinterPresenter(this);
    }

    @Override
    protected void initViews() {
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("打印条码");
        cbDel.setVisibility(View.GONE);
        etWithClear.addTextChangedListener(CommUtil.watcher);
        etWithClear.setOnEditorActionListener(0, new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                try {
                    EditText edt = (EditText) v;
                    String s = edt.getText().toString();
                    if (s.equals("")) {
                        return true;
                    }
                    long thisTime = System.currentTimeMillis();
                    if (thisTime - lastscanTime > 50) {
                        lastscanTime = thisTime;
                        boolean isHas = false;
                        if (tableView != null) {
                            for (String[] dd : tableView.getAlldata()) {
                                if (s.equals(dd[0])) {
                                    isHas = true;
                                }
                            }
                        }
                        if (!isHas) {
                            emptyView.show(true);
                            presenter.getPrintBarData(PrinterActivity.this, s);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    @Override
    public void setDatas(int index, String[] titles, List<String[]> data) {
        hideUILoading();
        int[] tableWith = CommUtil.SetComWidth(PrinterActivity.this, titles.length, 0, false);

        if (tableView != null) {
            List<String[]> tmp = tableView.getAlldata();
            for (int i = 0; i < data.size(); i++) {
                String[] dd = data.get(i);
                for (int j = 0; j < tmp.size(); j++) {
                    if (!tmp.get(j)[0].equals(dd[0])) {
                        tmp.add(dd);
                    }
                }
            }
            tableView.getcAdapter().notifyDataSetChanged();
        } else {
            tableView = new TableViewDetail(PrinterActivity.this,
                    tablell, titles, data, tableWith, false, null);
        }
        etWithClear.getEditText().setText("");
    }

    @OnClick({R.id.ivfLeft, R.id.tvfPrinter, R.id.tvfNoPrinters})
    public void OnViewClick(View view) {
        switch (view.getId()) {
            case R.id.ivfLeft:
                finish();
                break;
            case R.id.tvfPrinter:
                if(tableView == null || tableView.getAlldata().size() == 0){
                    return;
                }
                new QMUIDialog.MessageDialogBuilder(PrinterActivity.this)
                        .setTitle("提示")
                        .setMessage("是否确定开始打印?")
                        .addAction("再等会", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("好的", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                try {
                                    if (tableView != null) {
                                        List<String[]> ltdata = tableView.getAlldata();
                                        JSONArray jr = new JSONArray();
                                        if (ltdata.size() > 0) {
                                            for (int i = 0; i < ltdata.size(); i++) {
                                                JSONObject oo = new JSONObject();
                                                oo.put("fBarCode", ltdata.get(i)[0]);
                                                jr.put(oo);
                                            }

                                            String printer = Hawk.get("PrinterMAC", "");
                                            String schme = Hawk.get("PrintFrom", "");
                                            if (!printer.equals("") && !schme.equals("")) {
                                                showQMTipLoading("请求打印..", QMUITipDialog.Builder.ICON_TYPE_LOADING);
                                                presenter.StkScanByPackPrintBar(PrinterActivity.this, printer, schme, jr);
                                            } else {
                                                Toast.makeText(PrinterActivity.this, "打印机或打印方案没设置,请先设置后打印", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(PrinterActivity.this, "没有可打印条码", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .create(mCurrentDialogStyle).show();
                break;
            case R.id.tvfNoPrinters:
                startNewAct(SelectPrientActivity.class, 6666);
                break;
        }
    }


    @Override
    public void showUILoading() {
        showQMTipLoading("正在请求..", QMUITipDialog.Builder.ICON_TYPE_LOADING);
    }

    @Override
    public void hideUILoading() {
        emptyView.hide();
        hideQMTipLoading();
    }

    @Override
    public void setErrorMsg(int code, String msg) {
        hideQMTipLoading();
        Toast.makeText(PrinterActivity.this, msg + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setSuccessResult(String msg) {
        hideQMTipLoading();
        new QMUIDialog.MessageDialogBuilder(PrinterActivity.this)
                .setTitle("提示")
                .setMessage(msg)
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();

                        if (tableView != null) {

                        }
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    @Override
    public void setFailResult(String msg) {
        hideQMTipLoading();
        new QMUIDialog.MessageDialogBuilder(PrinterActivity.this)
                .setTitle("提示")
                .setMessage(msg)
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        emptyView.show("没有数据", "");
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    @Override
    public void SuccessSound() {
        SoundUtil.getInstense(this).setHintSound(1, 1500);//1成功2重复3失败
    }

    @Override
    public void RepatSound() {
        SoundUtil.getInstense(this).setHintSound(2, 1500);//1成功2重复3失败
    }

    @Override
    public void FaileSound() {
        SoundUtil.getInstense(this).setHintSound(3, 1000);//1成功2重复3失败
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 6666) {
            Bundle bundle = data.getExtras();
            SerMap serMap = (SerMap) bundle.getSerializable("OKMAP");
            //加到列表中
            Map<String, String[]> okmap = serMap.getMap();
            if (tableView != null) {
                List<String[]> tmp = tableView.getAlldata();
                for (String key : okmap.keySet()) {
                    String[] dd = okmap.get(key);
                    for (int j = 0; j < tmp.size(); j++) {
                        if (!tmp.get(j)[0].equals(dd[1])) {
                            tmp.add(dd);
                        }
                    }
                }
                tableView.getcAdapter().notifyDataSetChanged();
            } else {
                if (okmap.size() > 0) {
                    List<String[]> tmp = new ArrayList<>();
                    for (String key : okmap.keySet()) {
                        String[] dd = okmap.get(key);
                        String[] tt = new String[]{dd[1], dd[2], dd[3], dd[4]};
                        tmp.add(tt);
                    }
                    String[] titles = new String[]{"条码代号", "订单号", "分包号", "分包说明"};
                    int[] tableWith = CommUtil.SetComWidth(PrinterActivity.this, titles.length, 0, false);
                    tableView = new TableViewDetail(PrinterActivity.this,
                            tablell, titles, tmp, tableWith, false, null);
                    emptyView.hide();
                }
            }
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 102 || keyCode == 103 || keyCode == 110) {
            etWithClear.getEditText().setText("");
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 102 || keyCode == 103 || keyCode == 110) {
            etWithClear.getEditText().setFocusable(true);
            etWithClear.getEditText().setFocusableInTouchMode(true);
            etWithClear.getEditText().requestFocus();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void clearAddDatas(){
        if(tableView!=null){
            tableView.getAlldata().clear();
            tableView.getcAdapter().notifyDataSetChanged();
        }
    }
}
