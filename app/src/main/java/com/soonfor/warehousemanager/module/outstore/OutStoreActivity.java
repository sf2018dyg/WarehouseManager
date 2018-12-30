package com.soonfor.warehousemanager.module.outstore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.soonfor.warehousemanager.bases.NetStatusBean;
import com.soonfor.warehousemanager.home.store.StoreBean;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.module.instore.beans.ScanLogBean;
import com.soonfor.warehousemanager.module.instore.deleteBarCodeActivity;
import com.soonfor.warehousemanager.module.instore.flowtype.FlowTypeBean;
import com.soonfor.warehousemanager.module.outstore.beans.Out2BarItemBean;
import com.soonfor.warehousemanager.module.outstore.beans.OutScanLogBean;
import com.soonfor.warehousemanager.module.outstore.beans.OutStoreConditonBean;
import com.soonfor.warehousemanager.module.outstore.selected.OutStoreSelectedActivity;
import com.soonfor.warehousemanager.tools.ActivityUtils;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.DateUtils;
import com.soonfor.warehousemanager.tools.DoubleClickU;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.SoundUtil;
import com.soonfor.warehousemanager.tools.Tokens;
import com.soonfor.warehousemanager.module.querybyscan.unscan.UnScanBarCodeActivity;
import com.soonfor.warehousemanager.view.BottomComView;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.CommonTabLayout;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.entity.TabEntity;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.listener.CustomTabEntity;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.listener.OnTabSelectListener;
import com.soonfor.warehousemanager.view.TableViewDetail;
import com.soonfor.warehousemanager.view.popwindow.ActionItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.soonfor.warehousemanager.SoonforApplication.bar2Map;
import static com.soonfor.warehousemanager.SoonforApplication.outScanLogList;

/**
 * 修改人：DC-ZhuSuiBo on 2018/7/30 0030 13:34
 * 邮箱：suibozhu@139.com
 * 修改目的：补充功能
 */
public class OutStoreActivity extends BaseActivity<OutStorePresenter> implements IOutStoreView {

    static OutStoreActivity sActivity;
    @BindView(R.id.tl_1)
    CommonTabLayout tl_1;
    @BindView(R.id.tablell)
    LinearLayout tablell;
    @BindView(R.id.etWithClear)
    EditTextWithClear etWithClear;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    private OutStorePresenter presenter;
    private FlowTypeBean stBean;
    private TextView tvfRightSet;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private Map<String, OutStoreConditonBean> outCondMap;//所有的出库的条件
    public static OutStoreConditonBean conditonBean;//出货条件
    Map<Integer, TableViewDetail> tableViewMap;
    public StoreBean storeBean;//选中的仓库
    @BindView(R.id.yingsao)
    TextView yingsao;
    @BindView(R.id.yisao)
    TextView yisao;
    @BindView(R.id.benci)
    TextView benci;
    @BindView(R.id.weisao)
    TextView weisao;

    @BindView(R.id.bcView)
    BottomComView bcView;

    public static CheckBox cbDel;
    public static boolean isNeedAsyn;
    public static String firstBarCode = "";//没有设置条件情况下扫描的第一个条码
    private long lastscanTime = 0;//处理图森pda扫描时出现重复回车现象

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_putout_main;
    }

    @Override
    protected void initPresenter() {
        sActivity = OutStoreActivity.this;
        firstBarCode = "";
        stBean = getIntent().getExtras().getParcelable("SELECT_STORETYPE");
        presenter = new OutStorePresenter(this, stBean.get_id());
    }

    @Override
    protected void initViews() {
        cbDel = findViewById(R.id.cbDel);
        storeBean = Hawk.get(UserInfo.SELECTSTORE, null);
        tvfRightSet = toolbar.findViewById(R.id.tvfRightSet);
        etWithClear.addTextChangedListener(CommUtil.watcher);
        etWithClear.setOnEditorActionListener(0, new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                long thisTime = System.currentTimeMillis();
                if(thisTime - lastscanTime > 50) {
                    lastscanTime = thisTime;
                    EditText edt = (EditText) v;
                    String s = edt.getText().toString().trim();
                    disposeBarCode(s);
                }
                return true;
            }
        });
        Hawk.put("OutCbDel", false);
        isNeedAsyn = true;
        initData();
    }

    private void disposeBarCode(String s){
        try {
            if (!s.equals("")) {
                tl_1.setCurrentTab(0);
                if (stBean.get_id().equals("0")) {
                    if (conditonBean == null || conditonBean.getfLogistBatchNo().equals("")) {
                        SuccessSound();
                        emptyView.show(true);
                        presenter.GetMBathByBarCode(OutStoreActivity.this, s, "");
                    } else if (conditonBean.getfCarCardNo().equals("")) {
                        SuccessSound();
                        emptyView.show(true);
                        presenter.GetMBathByBarCode(OutStoreActivity.this, s, conditonBean.getfLogistBatchNo());
                    } else {
                        SendBarCode(s, true);
                    }
                } else if (stBean.get_id().equals("1")) {
                    if (conditonBean == null || conditonBean.getfShippingOrder().equals("")) {
                        SuccessSound();
                        firstBarCode = s;
                        tvfRightSet.callOnClick();
                    } else {
                        SendBarCode(s, true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        try {
            //清空表记录
            presenter.netStatusBeanDao.deleteAll();
            presenter.inStatusBeanDao.deleteAll();
            presenter.qitaoDao.deleteAll();

            if (stBean != null) {
                //请求标题
                presenter.getTitle(OutStoreActivity.this, stBean.get_id());
                if (!stBean.get_name().equals("")) {
                    ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText(stBean.get_name());
                    tvfRightSet.setText(getRightSetName(stBean.get_name()));
                }
                tvfRightSet.setVisibility(View.VISIBLE);
                bcView.setChorderListener(chScanClick);
                bcView.initBottomView(OutStoreActivity.this, null, this, okClick, itemOnClick, true, cbDel);
                conditonBean = new OutStoreConditonBean();
                outCondMap = Hawk.get(Tokens.Putout.PUTOUT_CONDITONS, null);
                if (outCondMap == null) {
                    outCondMap = new HashMap<>();
                    outCondMap.put(stBean.get_id(), conditonBean);
                } else if (outCondMap.containsKey(stBean.get_id()) && outCondMap.get(stBean.get_id()) != null) {
                    conditonBean = outCondMap.get(stBean.get_id());
                }
            } else {
                tvfRightSet.setVisibility(View.GONE);
            }
            presenter.requestBarCodeInfo(OutStoreActivity.this, storeBean.getfStkCode(), stBean.get_id());
            presenter.netStatusBeanDao.insert(new NetStatusBean("chuku", 0, 0, 0, 0));
            final Handler reflashNet = new Handler();
            reflashNet.postDelayed(new Runnable() {
                @Override
                public void run() {
                    presenter.reflashNet();
                    NetStatusBean bean = presenter.netStatusBeanDao.load("chuku");
                    if (bean != null) {
                        yingsao.setText(bean.getYingsao() + "");
                        yisao.setText(bean.getYisao() + "");
                        benci.setText(bean.getBenci() + "");

                        benci.setText(bar2Map.size() + "");
                        weisao.setText(bean.getWeisao() + "");
                    }
                    reflashNet.postDelayed(this, 1500);
                }
            }, 1500);
        } catch (Exception e) {
        }
    }

    /**
     * 检测必选条件是否缺失
     */
    private boolean checkConditions() {
        boolean requiredConditionIsChoose = false;//必选条件是否已经全部选择了
        if (conditonBean != null) {
            if (stBean.get_id().equals("0")) {//分包备货
                if (!conditonBean.getfLogistBatchNo().equals("") && !conditonBean.getfCarCardNo().equals("") && !conditonBean.getfLPCode().equals("")) {
                    requiredConditionIsChoose = true;
                }
            } else if (stBean.get_id().equals("1")) {//分包出货
                if (!conditonBean.getfShippingOrder().equals("")) {
                    requiredConditionIsChoose = true;
                }
            }
        }
        return requiredConditionIsChoose;
    }
    /**
     * @param s 条码
     * @param isNeedSuccessRing 是否需要扫描成功的声音
     **/
    private void SendBarCode(String s, boolean isNeedSuccessRing) {
        boolean isExistAndSuccessed = false;//是否已经存在并且解析成功了
        //boolean isSuccessed = false;//入股已经存在，则是否已经解析成功
        for (int i = 0; i < outScanLogList.size(); i++) {
            if (outScanLogList.get(i).getFBarCode().equals(s) && outScanLogList.get(i).getFErrorMsg().equals("识别成功")) {
                isExistAndSuccessed = true;
                break;
            }
        }
        if (isExistAndSuccessed) {
            RepatSound();
            OutScanLogBean beanRe = new OutScanLogBean(true);
            beanRe.setFBarCode(s);
            beanRe.setFRemark("重复扫描");
            beanRe.setFErrorMsg("重复扫描");
            beanRe.setFScanDate(DateUtils.getTodayDateTime("yyyy-MM-dd hh:mm:ss"));
            outScanLogList.add(beanRe);
            hideUILoading();
        } else {
            if(isNeedSuccessRing){SuccessSound();}
            OutScanLogBean bean = new OutScanLogBean(false);
            bean.setFBarCode(s);
            bean.setFRemark("");
            bean.setFErrorMsg("");
            bean.setFScanDate("");
            outScanLogList.add(bean);
        }
        //刷新一下列表
        reflashDataList();
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
        emptyView.show(false);
        hideQMTipLoading();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setTitles(List<String> lt) {
        tableViewMap = new HashMap<>();
        for (int i = 0; i < lt.size(); i++) {
            mTabEntities.add(new TabEntity(lt.get(i), R.mipmap.checked, R.mipmap.checked));
            tableViewMap.put(i, null);
        }
        tl_1.setTabData(mTabEntities);
        tl_1.setCurrentTab(0);
        presenter.loadDb(0, stBean.get_id());
        ;
        tl_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                reflashDataList();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    //根据条码获取物流批次信息
    @Override
    public void setGetBathByBarCode(String[] titles, ArrayList<String[]> datas, String barcode) {
        hideUILoading();
        if (datas.size() <= 0) {
            firstBarCode = barcode;
            MyToast.showToast(OutStoreActivity.this, "未关联到条码[" + barcode + "]对应的物流批次信息，请重新选择");
            tvfRightSet.callOnClick();
        } else if (datas.size() == 1) {//直接设置条件 解析异步中会自动匹配解析
            firstBarCode = "";
            SendBarCode(barcode, false);
            presenter.SaveBhtjAndStartAnalysisBarcode(datas.get(0));
        } else {//匹配出多个条件，则跳转至选择界面
            firstBarCode = barcode;
            OutStoreSelectedActivity.fbarBatchs = datas;
            Bundle b = new Bundle();
            b.putString("out_source", "outStore@AllConditions");
            b.putSerializable("OUT_SELECTCONDITON", conditonBean);
            startNewAct(OutStoreSelectedActivity.class, b, Tokens.Putout.JUMPCODE_SCANFORLOGISTICS);
        }
    }

    @Override
    public void setDatas(int index, String[] titles, List<String[]> data) {
        hideUILoading();
        emptyView.hide();
        tablell.removeAllViews();

        int[] tableWith = CommUtil.SetComWidth(OutStoreActivity.this, titles.length, 0, false);
        TableViewDetail tableView = new TableViewDetail(OutStoreActivity.this,
                tablell, titles, data, tableWith, false, null);

        tableViewMap.put(index, tableView);

        if (isCanClickConfirm()) {
            bcView.setIsOkEnable(true);
        } else {
            bcView.setIsOkEnable(false);
        }
        etWithClear.getEditText().setText("");
    }

    @Override
    public void reflashDataList() {
        presenter.loadDb(tl_1.getCurrentTab(), stBean.get_id());
    }

    private String getRightSetName(String title) {
        return "出货条件";
    }

    @OnClick({R.id.ivfLeft, R.id.tvfRightSet})
    public void OnViewOnClick(View view) {
        if (view.getId() == R.id.ivfLeft) {
            inFinish();
        } else if (view.getId() == R.id.tvfRightSet) {
            if (!DoubleClickU.isFastDoubleClick(R.id.tvfRightSet)) {
                if (tvfRightSet.getText().toString().equals("出货条件")) {
                    Bundle bb = new Bundle();
                    bb.putParcelable("SELECT_STORETYPE", stBean);
                    if (!firstBarCode.equals("")) {//通过第一次扫码而调起的条件设置
                        startNewAct(OutStoreConditionActivity.class, bb, Tokens.Putout.JUMPCODE_SETOUTCONDITION_BYFIRSTSCAN);
                    } else {//通过点击“出货条件”按钮或进界面自动调起的条件设置
                        startNewAct(OutStoreConditionActivity.class, bb, Tokens.Putout.JUMPCODE_SETOUTCONDITION);
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Tokens.Putout.JUMPCODE_SCANFORLOGISTICS:
                try {
                    if (data != null) {
                        String[] newWlpc = data.getStringArrayExtra("selected");
                        if(!firstBarCode.equals("")){
                            SendBarCode(firstBarCode, false);
                        }
                        presenter.SaveBhtjAndStartAnalysisBarcode(newWlpc);
                    }
                } catch (Exception e) {
                }
                firstBarCode = "";
                break;
            case Tokens.Putout.JUMPCODE_SETOUTCONDITION_BYFIRSTSCAN:
                try {
                    if (data != null) {
                        OutStoreConditonBean condBean = Hawk.get("ChuKuConditions", null);
                        if (condBean != null) {
                            conditonBean = condBean;
                            if(!firstBarCode.equals("")) {
                                SendBarCode(firstBarCode, false);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                firstBarCode = "";
                break;
            case Tokens.Putout.JUMPCODE_SETOUTCONDITION:
                try {
                    if (data != null) {
                        boolean isChange = data.getBooleanExtra("ISCHANGE", false);
                        if (isChange) {
                            isNeedAsyn = false;//必选条件有改变则清空
                            delHeBaoData(true, false);
                            OutStoreConditonBean condBean = Hawk.get("ChuKuConditions", null);
                            if (condBean != null) {
                                conditonBean = condBean;
                            }
                            MyToast.showToast(OutStoreActivity.this, "出货条件已改变，请重新扫码");
                            if(!firstBarCode.equals("")){
                                SendBarCode(firstBarCode, false);
                            }
                            isNeedAsyn = true;
                            presenter.requestBarCodeInfo(OutStoreActivity.this, storeBean.getfStkCode(), stBean.get_id());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                firstBarCode = "";
                hideUILoading();
                break;
            case 888:
                reflashDataList();
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private boolean isCanClickConfirm() {
        TableViewDetail viewDetail = tableViewMap.get(2);
        if (viewDetail != null) {
            if (viewDetail.getAlldata().size() > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isExits(List<String[]> data, String compare) {
        for (String[] s : data) {
            if (s[0].equals(compare)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setSuccessResult(String msg) {
        hideQMTipLoading();
        new QMUIDialog.MessageDialogBuilder(OutStoreActivity.this)
                .setTitle("提示")
                .setMessage(msg)
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    @Override
    public void setFailResult(String msg) {
        hideQMTipLoading();
        new QMUIDialog.MessageDialogBuilder(OutStoreActivity.this)
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
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void resetListView() {
        if (tl_1 != null) {
            tl_1.setCurrentTab(1);
            presenter.loadDb(1, stBean.get_id());
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 102 || keyCode == 103 || keyCode == 110) {
            etWithClear.getEditText().setText("");
        } else if (keyCode == 4) {
            //finish();
            inFinish();
            return false;
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
    public void callOnDelAllDatas() {
        clearToReScan();
    }


    @Override
    public void cleraTiaojian() {
        conditonBean = null;
    }

    @Override
    public void setSuccessMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void setCbDelStatu(boolean bl) {
        if (cbDel != null) {
            cbDel.setEnabled(bl);
        }
    }

    private void ClearOnClose() {
        try {
            //调接口清空缓存
            if (bar2Map != null && bar2Map.size() > 0) {
                JSONArray jr = new JSONArray();
                for (Map.Entry<String, Out2BarItemBean> map : bar2Map.entrySet()) {
                    JSONObject jo = new JSONObject();
                    jo.put("fBarCode", map.getValue().getfBarCode());
                    jr.put(jo);
                }
                presenter.StkOutByPackOnClose(OutStoreActivity.this, stBean.get_id(),jr);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public boolean getCheckStu() {
        if (cbDel != null) {
            return cbDel.isChecked();
        } else {
            return false;
        }
    }

    private void inFinish() {
        new QMUIDialog.MessageDialogBuilder(OutStoreActivity.this)
                .setTitle("提示")
                .setMessage("是否关闭 ? ")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        conditonBean = null;
                        finish();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 是否重设checkbox
     *
     * @param isResetCheckBox
     * @param isOnlyClearQiTao//是否仅情况其它条码
     */
    @Override
    public void delHeBaoData(boolean isResetCheckBox, boolean isOnlyClearQiTao) {
        presenter.inStatusBeanDao.deleteAll();
        //if(!isOnlyClearQiTao) {
            presenter.qitaoDao.deleteAll();
       // }
        if (bar2Map != null) bar2Map.clear();
        //presenter.scanlogDao.deleteAll();
        if (outScanLogList != null && outScanLogList.size() > 0) {
            outScanLogList.clear();
        }
        tableViewMap.put(0, null);
        tableViewMap.put(1, null);
        tableViewMap.put(2, null);
        tableViewMap.put(3, null);

        tablell.removeAllViews();

        emptyView.show("没有数据", "");

        tl_1.setCurrentTab(0);
        presenter.loadDb(0, stBean.get_id());
        yingsao.setText("0");
        yisao.setText("0");
        benci.setText("0");
        weisao.setText("0");

        NetStatusBean bean = presenter.netStatusBeanDao.load("chuku");
        if (bean != null) {
            bean.setYingsao(0);
            bean.setYisao(0);
            bean.setBenci(0);
            bean.setWeisao(0);
        }
        presenter.netStatusBeanDao.update(bean);

        if (isResetCheckBox) {
            cbDel.setEnabled(true);
            cbDel.setChecked(false);
        }

//        zhuijiadingdan = null;
//        Hawk.delete("zhuijiadingdan");

        ClearOnClose();
    }

    //确认按钮点击事件
    private View.OnClickListener okClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!DoubleClickU.isFastDoubleClick(R.id.bnfIsOk)) {
                if (!checkConditions()) {
                    Toast.makeText(OutStoreActivity.this, "必须先选择出货条件", Toast.LENGTH_SHORT).show();
                } else {
                    if (!presenter.isHaveNoAsyn()) submit();
                    else MyToast.showToast(OutStoreActivity.this, "日志中尚有未解析条码，暂不能提交");
                }
            }
        }
    };
    //产生出货单按钮点击事件
    private View.OnClickListener chScanClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!DoubleClickU.isFastDoubleClick(R.id.llfCreateChOrder)) {
                if (!checkConditions()) {
                    Toast.makeText(OutStoreActivity.this, "必须先选择出货条件", Toast.LENGTH_SHORT).show();
                } else {
                    if (!presenter.isHaveNoAsyn()) {
                        if (bar2Map != null && bar2Map.size() > 0) {
                            Toast.makeText(OutStoreActivity.this, "当前扫描条码列表有未提交数据, 请先点[确定]提交", Toast.LENGTH_LONG).show();
                        } else {
                            showQMTipLoading("正在产生出货单..", QMUITipDialog.Builder.ICON_TYPE_LOADING, false);
                            presenter.StkPrepByPackGenStkOut(OutStoreActivity.this, stBean.get_id(),
                                    conditonBean.getfLogistBatchNo(), conditonBean.getfCarCardNo(), conditonBean.getfShippingOrder());

                        }
                    } else MyToast.showToast(OutStoreActivity.this, "日志中尚有未解析条码，暂不能提交");
                }
            }
        }
    };

    //弹出窗口列表点击事件
    private View.OnClickListener itemOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ActionItem aItem = (ActionItem) view.getTag();
            bcView.getPopupWindow().dismiss();
            if (aItem != null) {
                switch (aItem.getId()) {
                    case 1:
                        if (!DoubleClickU.isFastDoubleClick()) {
                            //扫码删除
                            Bundle b = new Bundle();
                            b.putParcelable("SELECT_STORETYPE", stBean);
                            b.putString("source", "outStore@" + stBean.get_id());
                            startNewAct(deleteBarCodeActivity.class, b, 888);
                        }
                        break;
                    case 2://清空重扫
                        if (!DoubleClickU.isFastDoubleClick()) {
                            clearToReScan();
                        }
                        break;
                    case 3://未扫查询
                        if (!DoubleClickU.isFastDoubleClick()) {
                            //分包分拣、分包入库、生产分包入库
                            if (stBean.get_id().equals("0")) {
                                if (!conditonBean.getfLogistBatchNo().equals("")) {
                                    Bundle bb = new Bundle();
                                    bb.putParcelable("SELECT_STORETYPE", stBean);
                                    bb.putString("MODEL", "outstore");
                                    bb.putString("logistbatchno", conditonBean.getfLogistBatchNo());//物流批次/出货通知单
                                    startNewAct(UnScanBarCodeActivity.class, bb);
                                } else {
                                    Toast.makeText(OutStoreActivity.this, "请先选择物流批次", Toast.LENGTH_SHORT).show();
                                }
                            } else if (stBean.get_id().equals("1")) {
                                if (!conditonBean.getfShippingOrder().equals("")) {
                                    Bundle bb = new Bundle();
                                    bb.putParcelable("SELECT_STORETYPE", stBean);
                                    bb.putString("MODEL", "outstore");
                                    bb.putString("logistbatchno", conditonBean.getfShippingOrder());//物流批次/出货通知单
                                    startNewAct(UnScanBarCodeActivity.class, bb);
                                } else {
                                    Toast.makeText(OutStoreActivity.this, "请先选择出货通知单", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        break;
                    case 4://关闭
                        break;
                }
            }
        }
    };

    //清空重扫
    private void clearToReScan() {
        new QMUIDialog.MessageDialogBuilder(OutStoreActivity.this)
                .setTitle("提示")
                .setMessage("是否确定清除数据 ? ")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        delHeBaoData(true, false);
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    private void submit() {
        try {
            JSONArray jr = new JSONArray();
            if (stBean.get_id().equals("0")) {
                for (Map.Entry<String, Out2BarItemBean> entry : bar2Map.entrySet()) {
                    Out2BarItemBean bean = entry.getValue();
                    JSONObject o = new JSONObject();
                    o.put("fBarCode", bean.getfBarCode());//条码
                    o.put("fOrdNo", bean.getfOrdNo());//订单号
                    o.put("fSplitBatchNo", bean.getfSplitBatchNo());//拆单批次
                    o.put("fSPCode", bean.getfSPCode());//分包号
                    o.put("fPackMark", bean.getfPackMark());//分包说明
                    o.put("fPlaceCode", bean.getfPlaceCode());//储位代号
                    o.put("fScanorID", bean.getfScanorID());//扫描人代号
                    o.put("fScanor", bean.getfScanor());//扫描人
                    o.put("fScanDate", bean.getfScanDate());//扫描日期
                    o.put("fProdNo", bean.getfProdNo());//生产单号
                    o.put("fCarCardNo", bean.getfCarCardNo());//车牌号
                    o.put("fPackNo", bean.getfPackNo());//包装编号
                    o.put("fPlaceName", bean.getfPlaceName());//储位名称
                    o.put("fCtnL", bean.getfCtnL());//包装长
                    o.put("fCtnW", bean.getfCtnW());//包装宽
                    o.put("fCtnH", bean.getfCtnH());//包装高
                    o.put("fPackCuft", bean.getfPackCuft());//包装体积
                    o.put("fPackPcs", bean.getfPackPcs());//包装件数
                    o.put("fID", bean.getfID());//条码ID
                    jr.put(o);
                }
            } else if (stBean.get_id().equals("1")) {
                for (Map.Entry<String, Out2BarItemBean> entry : bar2Map.entrySet()) {
                    Out2BarItemBean bean = entry.getValue();
                    JSONObject o = new JSONObject();
                    o.put("fBarCode", bean.getfBarCode());//条码
                    o.put("fDlvNo", bean.getfDlvNo());//出货通知单号
                    o.put("fSNo", bean.getfSNo());//行号
                    o.put("fOrdNo", bean.getfOrdNo());//订单号
                    o.put("fSplitBatchNo", bean.getfSplitBatchNo());//拆单批次
                    o.put("fSPCode", bean.getfSPCode());//分包号
                    o.put("fPackMark", bean.getfPackMark());//分包说明
                    o.put("fPlaceCode", bean.getfPlaceCode());//储位代号
                    o.put("fScanorID", bean.getfScanorID());//扫描人代号
                    o.put("fScanor", bean.getfScanor());//扫描人
                    o.put("fScanDate", bean.getfScanDate());//扫描日期
                    o.put("fBarCodeQty", bean.getfBarCodeQty());
                    o.put("fStkTrayCode", bean.getfStkTrayCode());//托盘代号
                    o.put("fID", bean.getfID());//条码ID
                    jr.put(o);
                }
            }
            if (jr.length() > 0) {
                showQMTipLoading("正在提交..", QMUITipDialog.Builder.ICON_TYPE_LOADING, false);
                presenter.StkPrepByPackSaveBar(OutStoreActivity.this, stBean.get_id(),
                        storeBean.getfStkCode(), conditonBean.getfLogistBatchNo(), conditonBean.getfCarCardNo(), conditonBean.getfShippingOrder(), cbDel.isChecked() ? "1" : "0", jr);
            } else {
                new QMUIDialog.MessageDialogBuilder(OutStoreActivity.this)
                        .setTitle("提示")
                        .setMessage("没有齐套数据可提交")
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void SuccessSound() {
        SoundUtil.getInstense(this).setHintSound(1, 1000);//1成功2重复3失败
    }

    @Override
    public void RepatSound() {
        SoundUtil.getInstense(this).setHintSound(2, 1500);//1成功2重复3失败
    }

    @Override
    public void FaileSound() {
       SoundUtil.getInstense(OutStoreActivity.this).setHintSound(3, 1500);//1成功2重复3失败
    }

    @Override
    protected void onDestroy() {
        isNeedAsyn = false;
//        if(outCondMap==null){outCondMap = new HashMap<>();}
//        outCondMap.put(stBean.get_id(), conditonBean);
//        Hawk.put(Tokens.Putout.PUTOUT_CONDITONS, outCondMap);
        cleraTiaojian();
        ClearOnClose();
        Hawk.delete(Tokens.Putout.PUTOUT_CONDITONS);
        if (bar2Map != null) {
            bar2Map.clear();
        }
        if (outScanLogList != null) {
            outScanLogList.clear();
        }
        super.onDestroy();
    }

    public static void FinishActivity() {
        if (sActivity != null && ActivityUtils.isRunning(sActivity)) {
            sActivity.finish();
        }
    }
}
