package com.soonfor.warehousemanager.module.instore;

import android.content.Intent;
import android.os.Bundle;
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
import com.soonfor.warehousemanager.home.store.StoreBean;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.module.instore.beans.InStoreConditionBean;
import com.soonfor.warehousemanager.module.instore.selected.InStoreSelectedActivity;
import com.soonfor.warehousemanager.module.instore.flowtype.FlowTypeBean;
import com.soonfor.warehousemanager.tools.DoubleClickU;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.Tokens;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/8 0008 9:43
 * 邮箱：suibozhu@139.com
 * 类用途：入库条件
 */
public class InStoreConditionActivity extends BaseActivity<InStoreConditionPresenter> implements IInStoreConditionView {

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    @BindView(R.id.tv_selectDanju)
    TextView tv_selectDanju;
    @BindView(R.id.tv_selectChuwei)
    TextView tv_selectChuwei;
    @BindView(R.id.tv_selectBumen)
    TextView tv_selectBumen;
    @BindView(R.id.tv_selectYuanyin)
    TextView tv_selectYuanyin;
    public static StoreBean storeBean;//选中的仓库
    @BindView(R.id.viewllfchuwei)
    View viewllfchuwei;
    @BindView(R.id.llfchuwei)
    LinearLayout llfchuwei;
    FlowTypeBean stBean;
    @BindView(R.id.llfrukudan)
    LinearLayout llfrukudan;
    @BindView(R.id.viewllfrukudanju)
    View viewllfrukudanju;
    @BindView(R.id.tv_selectRukuDanju)
    TextView tv_selectRukuDanju;
    @BindView(R.id.llfdanju)
    LinearLayout llfdanju;
    @BindView(R.id.llfyuanyin)
    LinearLayout llfyuanyin;
    @BindView(R.id.llfremark)
    LinearLayout llfremark;
    @BindView(R.id.viewchuwei)
    View viewchuwei;
    @BindView(R.id.viewbm)
    View viewbm;
    @BindView(R.id.viewreson)
    View viewreson;
    @BindView(R.id.txthead)
    TextView txthead;
    @BindView(R.id.txtdeptstar)
    TextView txtdeptstar;
    @BindView(R.id.chuweistar)
    TextView chuweistar;

    private InStoreConditionBean conditionBean;

    private int changeNum = 0;//改变的条件个数

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_ruku_tiaojian;
    }

    @Override
    protected void initPresenter() {
        stBean = getIntent().getExtras().getParcelable("SELECT_STORETYPE");
        presenter = new InStoreConditionPresenter(this);
    }

    @Override
    protected void initViews() {
        changeNum = 0;
        txthead.setText(stBean.get_name() + "");
        storeBean = Hawk.get(UserInfo.SELECTSTORE, null);
        if (storeBean != null) {
            if (storeBean.getfIfUsePlace() == 0) {//不启用储位
                llfchuwei.setVisibility(View.GONE);
                viewchuwei.setVisibility(View.GONE);
            } else if (storeBean.getfIfUsePlace() == 1) {//启用
                llfchuwei.setVisibility(View.VISIBLE);
                viewchuwei.setVisibility(View.VISIBLE);
            }
        }
        //读缓存
        try {
            if (InStoreActivity.conditionBean != null) {
                conditionBean = presenter.getNewValue(InStoreActivity.conditionBean);
            } else {
                conditionBean = new InStoreConditionBean();
            }
            if (stBean.get_id().equals("2")) {//入库退库
                llfrukudan.setVisibility(View.VISIBLE);
                viewllfrukudanju.setVisibility(View.VISIBLE);
                chuweistar.setVisibility(View.INVISIBLE);
                llfdanju.setVisibility(View.GONE);
                llfyuanyin.setVisibility(View.GONE);
                llfremark.setVisibility(View.GONE);
                viewbm.setVisibility(View.GONE);
                viewreson.setVisibility(View.GONE);
                txtdeptstar.setVisibility(View.INVISIBLE);
                tv_selectRukuDanju.setText(conditionBean.getFrukubillName());
            } else {
                llfrukudan.setVisibility(View.GONE);
                viewllfrukudanju.setVisibility(View.GONE);
                chuweistar.setVisibility(View.VISIBLE);
                llfdanju.setVisibility(View.VISIBLE);
                llfyuanyin.setVisibility(View.VISIBLE);
                llfremark.setVisibility(View.VISIBLE);
                viewbm.setVisibility(View.VISIBLE);
                viewreson.setVisibility(View.VISIBLE);
                txtdeptstar.setVisibility(View.VISIBLE);
                if (stBean.get_id().equals("3")) {
                    conditionBean.setFbillCode("PMS107");
                    conditionBean.setFbillName("生产入库单");
                    conditionBean.setFreasonCode("901");
                    conditionBean.setFreasonDesc("生产入库");
                } else {
                    tv_selectDanju.setOnClickListener(danjuClick);
                }
                tv_selectDanju.setText(conditionBean.getFbillName());
                tv_selectChuwei.setText(conditionBean.getFbinlocationName());
                tv_selectYuanyin.setText(conditionBean.getFreasonDesc());
                tv_selectBumen.setText(conditionBean.getFdeptName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showUILoading() {
        showQMTipLoading("正在请求..", QMUITipDialog.Builder.ICON_TYPE_LOADING);
    }

    @Override
    public void hideUILoading() {
        hideQMTipLoading();
    }

    @Override
    public void setErrorMsg(int code, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener danjuClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tv_selectDanju) {
                if(!DoubleClickU.isFastDoubleClick(R.id.tv_selectDanju)){
                    Bundle b = new Bundle();
                    b.putString("in_source", "inStore@bill");
                    b.putSerializable("IN_SELECTCONDITON", conditionBean);
                    startNewAct(InStoreSelectedActivity.class, b, 666);
                }
            }
        }
    };

    @OnClick({R.id.tv_selectRukuDanju, R.id.tv_selectChuwei, R.id.tv_selectYuanyin, R.id.tv_selectBumen, R.id.txt_confirm})
    public void OnViewClick(View view) {
        Bundle b = new Bundle();
        switch (view.getId()) {
            case R.id.tv_selectChuwei:
                if(!DoubleClickU.isFastDoubleClick(R.id.tv_selectChuwei)) {
                    b.putString("in_source", "inStore@chuwei");
                    b.putSerializable("IN_SELECTCONDITON", conditionBean);
                    startNewAct(InStoreSelectedActivity.class, b, 667);
                }
                break;
            case R.id.tv_selectYuanyin:
                if(!DoubleClickU.isFastDoubleClick(R.id.tv_selectYuanyin)){
                    b = new Bundle();
                    b.putString("in_source", "inStore@reason");
                    b.putSerializable("IN_SELECTCONDITON", conditionBean);
                    startNewAct(InStoreSelectedActivity.class, b, 668);
                }
                break;
            case R.id.tv_selectBumen:
                if(!DoubleClickU.isFastDoubleClick(R.id.tv_selectBumen)){
                    b = new Bundle();
                    b.putString("in_source", "inStore@dept");
                    b.putSerializable("IN_SELECTCONDITON", conditionBean);
                    startNewAct(InStoreSelectedActivity.class, b, 673);
                }
                break;
            case R.id.tv_selectRukuDanju:
                if(!DoubleClickU.isFastDoubleClick(R.id.tv_selectRukuDanju)){
                    b = new Bundle();
                    b.putString("in_source", "inStore@rukubill");
                    b.putSerializable("IN_SELECTCONDITON", conditionBean);
                    startNewAct(InStoreSelectedActivity.class, b, 674);
                }
                break;
            case R.id.txt_confirm:
                if(!DoubleClickU.isFastDoubleClick(R.id.txt_confirm)) {
                    if (storeBean != null) {
                        if (storeBean.getfIfUsePlace() == 0) {//不启用
                            if (stBean.get_id().equals("2")) {
                                submit();
                            } else {
                                if (conditionBean.getFbillCode().equals("") || conditionBean.getFreasonCode().equals("") || conditionBean.getFdeptCode().equals("")) {
                                    Toast.makeText(InStoreConditionActivity.this, "必选条件缺失", Toast.LENGTH_SHORT).show();
                                } else {
                                    submit();
                                }
                            }
                        } else if (storeBean.getfIfUsePlace() == 1) {//启用
                            if (stBean.get_id().equals("2")) {
                                if (conditionBean.getFbinlocationCode().equals("")) {
                                    Toast.makeText(InStoreConditionActivity.this, "必选条件缺失", Toast.LENGTH_SHORT).show();
                                } else {
                                    submit();
                                }
                            } else {
                                if (conditionBean.getFbinlocationCode().equals("") || conditionBean.getFbillCode().equals("") || conditionBean.getFreasonCode().equals("") || conditionBean.getFdeptCode().equals("")) {
                                    Toast.makeText(InStoreConditionActivity.this, "必选条件缺失", Toast.LENGTH_SHORT).show();
                                } else {
                                    submit();
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    private void submit() {
        if (isCansubmit()) {
            Intent intent = new Intent();
            intent.putExtra("ISCHANGE", changeNum > 0 ? true : false);
            intent.putExtra("INSTORE_CONDITION", conditionBean);
            if(!InStoreActivity.firstBarCode.equals("")){
                setResult(Tokens.Putint.JUMPCODE_SETOUTCONDITION_BYFIRSTSCAN, intent);
            }else {
                setResult(Tokens.Putint.JUMPCODE_SETOUTCONDITION, intent);
            }
            finish();
        } else {
            MyToast.showToast(this, "必选条件缺失");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 666:
                try {
                    if(data!=null) {
                        String[] newDanju = data.getStringArrayExtra("selected");
                        if (newDanju != null && !conditionBean.getFbillCode().equals(newDanju[1])) {
                            changeNum++;
                            conditionBean.setFbillCode(newDanju[1]);
                            conditionBean.setFbillName(newDanju[2]);
                            tv_selectDanju.setText(conditionBean.getFbillName());
                            //清空原因
                            conditionBean.setFreasonCode("");
                            conditionBean.setFreasonDesc("");
                            tv_selectYuanyin.setText("");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 667:
                try {
                    if(data!=null) {
                        String[] newChuWei = data.getStringArrayExtra("selected");
                        if (newChuWei != null && !conditionBean.getFbillCode().equals(newChuWei[1])) {
                            changeNum++;
                            conditionBean.setFbinlocationCode(newChuWei[1]);
                            conditionBean.setFbinlocationName(newChuWei[2]);
                            tv_selectChuwei.setText(conditionBean.getFbinlocationName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 668:
                try {
                    if(data!=null) {
                        String[] newYuanyin = data.getStringArrayExtra("selected");
                        if (newYuanyin != null && !conditionBean.getFreasonCode().equals(newYuanyin[1])) {
                            changeNum++;
                            conditionBean.setFreasonCode(newYuanyin[1]);
                            conditionBean.setFreasonDesc(newYuanyin[2]);
                            tv_selectYuanyin.setText(conditionBean.getFreasonDesc());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 673:
                try {
                    if(data!=null) {
                        String[] newDept = data.getStringArrayExtra("selected");
                        if (newDept != null && !conditionBean.getFdeptCode().equals(newDept[2])) {
                            changeNum++;
                            conditionBean.setFdeptCode(newDept[2]);
                            conditionBean.setFdeptName(newDept[3]);
                            tv_selectBumen.setText(conditionBean.getFdeptName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 674:
                try {
                    if(data!=null) {
                        String[] newRudanju = data.getStringArrayExtra("selected");
                        if (newRudanju != null && !conditionBean.getFrukubillCode().equals(newRudanju[1])) {
                            changeNum++;
                            conditionBean.setFrukubillCode(newRudanju[1]);
                            conditionBean.setFrukubillName(newRudanju[2]);
                            tv_selectRukuDanju.setText(conditionBean.getFrukubillName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setSuccessResult(String msg) {
        hideQMTipLoading();
        new QMUIDialog.MessageDialogBuilder(InStoreConditionActivity.this)
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
        new QMUIDialog.MessageDialogBuilder(InStoreConditionActivity.this)
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

    private boolean isCansubmit() {
        if ((llfdanju.getVisibility() != View.VISIBLE || !tv_selectDanju.getText().toString().equals(""))
                && (llfchuwei.getVisibility() != View.VISIBLE || !tv_selectChuwei.getText().toString().equals(""))
                && (txtdeptstar.getVisibility() != View.VISIBLE || !tv_selectBumen.getText().toString().equals(""))
                && (llfyuanyin.getVisibility() != View.VISIBLE || !tv_selectYuanyin.getText().toString().equals(""))) {
            return true;
        } else {
            return false;
        }
    }

    long flag = -1;

    @Override
    public void onBackPressed() {
        finish();
//        if (isCansubmit()) {
//            Intent intent = new Intent();
//            intent.putExtra("ISCHANGE", changeNum > 0 ? true : false);
//            intent.putExtra("INSTORE_CONDITION", conditionBean);
//            setResult(665, intent);
//            finish();
//        } else {
//            if ((flag == -1 || System.currentTimeMillis() - flag > 2000)) {
//                Toast.makeText(this, "必选条件缺失,再点击一次退出备货界面", Toast.LENGTH_SHORT).show();
//                flag = System.currentTimeMillis();
//            } else if (System.currentTimeMillis() - flag < 2000) {
//                InStoreActivity.isNeedAsyn = false;
//                finish();
//                InStoreActivity.FinishActivity();
//            }
//        }
    }
}
