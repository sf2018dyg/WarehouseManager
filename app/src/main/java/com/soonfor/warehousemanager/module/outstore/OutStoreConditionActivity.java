package com.soonfor.warehousemanager.module.outstore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.module.instore.beans.InStoreConditionBean;
import com.soonfor.warehousemanager.module.instore.flowtype.FlowTypeBean;
import com.soonfor.warehousemanager.module.outstore.beans.OutStoreConditonBean;
import com.soonfor.warehousemanager.module.outstore.selected.OutStoreSelectedActivity;
import com.soonfor.warehousemanager.tools.DoubleClickU;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.Tokens;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/30 0030 9:58
 * 邮箱：suibozhu@139.com
 * 类用途：出库条件
 */
public class OutStoreConditionActivity extends BaseActivity<OutStoreConditionPresenter> implements IOutStoreConditionView {

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    OutStoreConditionPresenter presenter;
    ArrayList<String> dingdan;
    @BindView(R.id.llfBeiHuo)
    LinearLayout llfBeiHuo;
    @BindView(R.id.txt_wuliupici)
    TextView txt_wuliupici;
    @BindView(R.id.txt_chepai)
    TextView txt_chepai;
    @BindView(R.id.txt_selectordno)
    TextView txt_selectordno;
    @BindView(R.id.edt_zaihuotai)
    TextView txt_zaihuotai;
    @BindView(R.id.llfChuHuo)
    LinearLayout llfChuHuo;
    @BindView(R.id.tvfShippingOrder)
    TextView tvfShippingOrder;
    @BindView(R.id.txt_confirm)
            TextView tvfConfirm;
    FlowTypeBean stBean;
    private OutStoreConditonBean conditonBean;//出货条件
    private int changeNum = 0;//改变的条件个数

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_chuku_tiaojian;
    }

    @Override
    protected void initPresenter() {
        stBean = getIntent().getExtras().getParcelable("SELECT_STORETYPE");
        presenter = new OutStoreConditionPresenter(this);
    }

    @Override
    protected void initViews() {
        //读缓存
        try {
            if (OutStoreActivity.conditonBean != null) {
                conditonBean = presenter.getNewValue(OutStoreActivity.conditonBean);
            } else {
                conditonBean = new OutStoreConditonBean();
            }
            if (stBean.get_id().equals("0")) {//分包备货
                llfChuHuo.setVisibility(View.GONE);
                llfBeiHuo.setVisibility(View.VISIBLE);
                txt_wuliupici.setText(conditonBean.getfLogistBatchNo());//物流批次
                txt_chepai.setText(conditonBean.getfCarCardNo());//车牌号
                txt_zaihuotai.setText(conditonBean.getfLPName());
                txt_selectordno.setText(conditonBean.getfAddOrder());
            } else if (stBean.get_id().equals("1")) {//分包出货
                llfBeiHuo.setVisibility(View.GONE);
                llfChuHuo.setVisibility(View.VISIBLE);
                tvfShippingOrder.setText(conditonBean.getfShippingOrder());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (conditonBean == null) conditonBean = new OutStoreConditonBean();
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
    }

    @OnClick({R.id.txt_selectordno, R.id.txt_wuliupici, R.id.txt_chepai, R.id.tvfShippingOrder, R.id.txt_confirm})
    public void OnViewClick(View view) {
        if (view.getId() == R.id.txt_wuliupici) {
            if(!DoubleClickU.isFastDoubleClick(R.id.txt_wuliupici)) {
                Bundle b = new Bundle();
                b.putString("out_source", "outStore@LogisticsBatch");
                b.putSerializable("OUT_SELECTCONDITON", conditonBean);
                startNewAct(OutStoreSelectedActivity.class, b, Tokens.Putout.JUMPCODE_LOGISTICSBATCHS);
            }
        } else if (view.getId() == R.id.txt_chepai) {
            if(!DoubleClickU.isFastDoubleClick(R.id.txt_chepai)) {
                if (conditonBean.getfLogistBatchNo().equals("")) {
                    MyToast.showToast(this, "请先选择物流批次");
                } else {
                    Bundle b = new Bundle();
                    b.putString("out_source", "outStore@PlateNumber");
                    b.putSerializable("OUT_SELECTCONDITON", conditonBean);
                    startNewAct(OutStoreSelectedActivity.class, b, Tokens.Putout.JUMPCODE_PLATENUMBER);
                }
            }
        } else if (view.getId() == R.id.txt_selectordno) {
            if(!DoubleClickU.isFastDoubleClick(R.id.txt_selectordno)) {
                if (conditonBean.getfLogistBatchNo().equals("")) {
                    MyToast.showToast(this, "请先选择物流批次");
                } else {
                    Bundle b = new Bundle();
                    b.putString("out_source", "outStore@AddOrder");
                    b.putSerializable("OUT_SELECTCONDITON", conditonBean);
                    startNewAct(OutStoreSelectedActivity.class, b, Tokens.Putout.JUMPCODE_ADDOORDERS);
                }
            }
        } else if (view.getId() == R.id.tvfShippingOrder) {
            if(!DoubleClickU.isFastDoubleClick(R.id.tvfShippingOrder)) {
                Bundle b = new Bundle();
                b.putString("out_source", "outStore@ShippingOrder");
                b.putSerializable("OUT_SELECTCONDITON", conditonBean);
                startNewAct(OutStoreSelectedActivity.class, b, Tokens.Putout.JUMPCODE_SHIPPINGORDER);
            }
        } else if (view.getId() == R.id.txt_confirm) {
            if(!DoubleClickU.isFastDoubleClick(R.id.txt_confirm)) {
                if (isCansubmit()) {
                    Hawk.put("ChuKuConditions", conditonBean);
                    Intent intent = new Intent();
                    if (!OutStoreActivity.firstBarCode.equals("")) {
                        setResult(Tokens.Putout.JUMPCODE_SETOUTCONDITION_BYFIRSTSCAN, intent);
                    } else {
                        intent.putExtra("ISCHANGE", changeNum > 0 ? true : false);
                        setResult(Tokens.Putout.JUMPCODE_SETOUTCONDITION, intent);
                    }
                    finish();
                } else {
                    MyToast.showToast(this, "必选条件缺失");
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Tokens.Putout.JUMPCODE_LOGISTICSBATCHS:
                try {
                    if (data != null) {
                        String[] newWlpc = data.getStringArrayExtra("selected");
                        if (newWlpc != null && !conditonBean.getfLogistBatchNo().equals(newWlpc[1])) {
                            changeNum++;
                            conditonBean.setfLogistBatchNo(newWlpc[1]);
                            txt_wuliupici.setText(newWlpc[1] + "");
                            //更新了物流批次则清空车牌号 和 追加的订单
                            conditonBean.setfCarCardNo("");
                            conditonBean.setfAddOrder("");
                            txt_chepai.setText("");
                            txt_zaihuotai.setText("");
                            txt_selectordno.setText("");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Tokens.Putout.JUMPCODE_PLATENUMBER:
                try {
                    if (data != null) {
                        String[] newCph = data.getStringArrayExtra("selected");
                        if (newCph != null && !conditonBean.getfCarCardNo().equals(newCph[2])) {
                            changeNum++;
                            conditonBean.setfCarCardNo(newCph[2]);
                            conditonBean.setfLPCode(newCph[4]);
                            conditonBean.setfLPName(newCph[5]);
                            txt_chepai.setText(newCph[2]);
                            txt_zaihuotai.setText(conditonBean.getfLPName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Tokens.Putout.JUMPCODE_ADDOORDERS:
                try {
                    if (data != null) {
                        dingdan = data.getStringArrayListExtra("selected");
                        if (dingdan != null && !conditonBean.getfLogistBatchNo().equals("")) {
                            JSONArray jr = new JSONArray();
                            for (String s : dingdan) {
                                JSONObject o = new JSONObject();
                                o.put("fOrdNo", s);
                                jr.put(o);
                            }
                            //追加订单
                            presenter.StkOutByPackAddOrder(OutStoreConditionActivity.this, conditonBean.getfLogistBatchNo(), jr);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Tokens.Putout.JUMPCODE_SHIPPINGORDER://出库通知单
                try {
                    if (data != null) {
                        String[] newSN = data.getStringArrayExtra("selected");
                        if (newSN != null && !conditonBean.getfShippingOrder().equals(newSN[1])) {
                            changeNum++;
                            conditonBean.setfShippingOrder(newSN[1]);
                            tvfShippingOrder.setText(newSN[1]);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(tvfConfirm!=null){
                                        tvfConfirm.callOnClick();
                                    }
                                }
                            },100);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setSuccessResult(String msg) {
        hideQMTipLoading();
        new QMUIDialog.MessageDialogBuilder(OutStoreConditionActivity.this)
                .setTitle("提示")
                .setMessage(msg)
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    @Override
    public void setFailResult(String msg) {
        hideQMTipLoading();
        new QMUIDialog.MessageDialogBuilder(OutStoreConditionActivity.this)
                .setTitle("提示")
                .setMessage(msg)
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }


    @Override
    public void setZhuiJiaDingdan(String orditem) {
        if (orditem != null || !orditem.equals("")) {
            txt_selectordno.setText(orditem);
            conditonBean.setfAddOrder(orditem);
        }
    }

    @Override
    public void setSuccessMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private boolean isCansubmit() {
        if (stBean.get_id().equals("0")) {
            if (txt_wuliupici.getText().toString().equals("")
                    || txt_chepai.getText().toString().equals("")
                    || txt_zaihuotai.getText().toString().equals("")) {
                return false;
            } else {
                return true;
            }
        } else if (stBean.get_id().equals("1")) {
            if (tvfShippingOrder.getText().toString().equals("")) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //long flag = -1;

    @Override
    public void onBackPressed() {
        finish();
//        if (isCansubmit()) {
//            FinFinish();
//        } else {
//            if ((flag == -1 || System.currentTimeMillis() - flag > 2000)) {
//                Toast.makeText(this, "必选条件缺失,再点击一次将强行退出出库界面", Toast.LENGTH_SHORT).show();
//                flag = System.currentTimeMillis();
//            } else if (System.currentTimeMillis() - flag < 2000) {
//                OutStoreActivity.isNeedAsyn = false;
//                finish();
//                OutStoreActivity.FinishActivity();
//            }
//        }
    }

//    private void FinFinish() {
//        Hawk.put("ChuKuConditions", conditonBean);
//        Intent intent = new Intent();
//        intent.putExtra("ISCHANGE", changeNum > 0 ? true : false);
//        setResult(672, intent);
//        finish();
//    }
}
