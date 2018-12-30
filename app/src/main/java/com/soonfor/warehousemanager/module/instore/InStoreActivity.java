package com.soonfor.warehousemanager.module.instore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dingyg.edittextwithclear.EditTextWithClear;
import com.orhanobut.hawk.Hawk;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.bases.NetStatusBean;
import com.soonfor.warehousemanager.dao.BarItemBeanDao;
import com.soonfor.warehousemanager.home.store.StoreBean;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.module.instore.beans.BarItemBean;
import com.soonfor.warehousemanager.module.instore.beans.DelDataBean;
import com.soonfor.warehousemanager.module.instore.beans.InStoreConditionBean;
import com.soonfor.warehousemanager.module.instore.beans.ScanLogBean;
import com.soonfor.warehousemanager.module.instore.beans.hebao.HeBaoBarItemBean;
import com.soonfor.warehousemanager.module.instore.beans.hebao.SpSidBean;
import com.soonfor.warehousemanager.module.instore.flowtype.FlowTypeBean;
import com.soonfor.warehousemanager.tools.ActivityUtils;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.DateUtils;
import com.soonfor.warehousemanager.tools.DoubleClickU;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.SoundUtil;
import com.soonfor.warehousemanager.module.querybyscan.unscan.UnScanBarCodeActivity;
import com.soonfor.warehousemanager.tools.Tokens;
import com.soonfor.warehousemanager.view.BottomComView;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.CommonTabLayout;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.entity.TabEntity;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.listener.CustomTabEntity;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.listener.OnTabSelectListener;
import com.soonfor.warehousemanager.view.NoQiTaoUIActivity;
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
import cn.jesse.nativelogger.NLogger;

import static com.soonfor.warehousemanager.SoonforApplication.inScanLogList;

/**
 * 修改人：DC-ZhuSuiBo on 2018/7/30 0030 10:06
 * 邮箱：suibozhu@139.com
 * 修改目的：补充功能
 */
public class InStoreActivity extends BaseActivity<InStorePresenter> implements IInStoreView {

    static InStoreActivity sActivity;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    Map<Integer, TableViewDetail> tableViewMap;
    TextView tvfRightSet;
    @BindView(R.id.tl_1)
    CommonTabLayout tab;
    @BindView(R.id.tablell)
    LinearLayout tablell;
    @BindView(R.id.etWithClear)
    EditTextWithClear etWithClear;
    @BindView(R.id.tvfSubTitile)
    TextView tvfSubTitile;
    @BindView(R.id.yingsao)
    TextView yingsao;
    @BindView(R.id.yisao)
    TextView yisao;
    @BindView(R.id.benci)
    TextView benci;
    @BindView(R.id.weisao)
    TextView weisao;
    @BindView(R.id.scrollHead)
    HorizontalScrollView scrollHead;
    @BindView(R.id.bcView)
    BottomComView bcView;

    private FlowTypeBean stBean;//程序
    private StoreBean storeBean;//选中的仓库
    private Map<String, InStoreConditionBean> inSotreCondMap;//入库的所有条件
    List<SpSidBean> spSidBeans;
    public static InStoreConditionBean conditionBean;
    public static CheckBox cbDel;
    public static boolean isNeedAsyn;//是否需要解析条码
    public static String firstBarCode;//没有设置条件情况下扫描的第一个条码,将自动弹出设置条件界面
    private long lastscanTime = 0;//处理图森pda扫描时出现重复回车现象

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_putin_main;
    }

    @Override
    protected void initPresenter() {
        sActivity = InStoreActivity.this;
        firstBarCode = "";
        stBean = getIntent().getExtras().getParcelable("SELECT_STORETYPE");
        presenter = new InStorePresenter(this, Integer.parseInt(stBean.get_id()));
    }

    @Override
    protected void initViews() {
        cbDel = findViewById(R.id.cbDel);
        storeBean = Hawk.get(UserInfo.SELECTSTORE, null);
        conditionBean = new InStoreConditionBean();
        tvfRightSet = toolbar.findViewById(R.id.tvfRightSet);
        etWithClear.addTextChangedListener(CommUtil.watcher);
        etWithClear.setOnEditorActionListener(0, new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                try {
                    long thisTime = System.currentTimeMillis();
                    if(thisTime - lastscanTime > 50){
                        lastscanTime = thisTime;
                        EditText edt = (EditText) v;
                        String s = edt.getText().toString();
                        if (!s.equals("")) {
                            tab.setCurrentTab(0);
                            if (!stBean.get_id().equals("4") && !checkConditions()) {//非合包条码需要判断条件
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
                return true;
            }
        });
        isNeedAsyn = true;
        initData();
    }

    private void initData() {
        try {
            //清空表记录
            presenter.netStatusBeanDao.deleteAll();
            presenter.inStatusBeanDao.deleteAll();
            presenter.qitaoDao.deleteAll();
            presenter.baritemDao.deleteAll();
            //presenter.scanlogDao.deleteAll();
            if (inScanLogList != null && inScanLogList.size() > 0) {
                inScanLogList.clear();
            }
            presenter.heBaoBarItemBeanDao.deleteAll();
            presenter.heBaoGoodsItemDao.deleteAll();
        } catch (Exception e) {
        }
        try {
            if (stBean != null) {
                //请求标题
                presenter.getRukuTitle(this, Integer.parseInt(stBean.get_id()));
                if (!stBean.get_name().equals("")) {
                    ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText(stBean.get_name());
                    tvfRightSet.setText(getRightSetName(stBean.get_name()));
                }
                //获取入库条件
                inSotreCondMap = Hawk.get(Tokens.Putint.PUTIN_CONDITONS, null);
                if (inSotreCondMap == null) {
                    inSotreCondMap = new HashMap<>();
                    inSotreCondMap.put(stBean.get_id(), conditionBean);
                }
                if (inSotreCondMap.containsKey(stBean.get_id()) && inSotreCondMap.get(stBean.get_id()) != null) {
                    conditionBean = inSotreCondMap.get(stBean.get_id());
                }
                tvfRightSet.setVisibility(View.VISIBLE);
                if (stBean.get_id().equals("4")) {//合包扫描 不要条件
                    tvfRightSet.setVisibility(View.GONE);
                    scrollHead.setVisibility(View.GONE);
                    cbDel.setVisibility(View.GONE);
                    bcView.initBottomView(InStoreActivity.this, this, null, okClick, itemOnClick, false, null);
                    presenter.requestHbBarCodeInfo(InStoreActivity.this, storeBean.getfStkCode());
                } else {
                    tvfRightSet.setVisibility(View.VISIBLE);
                    if (stBean.get_id().equals("0")) {//分包分拣
                        scrollHead.setVisibility(View.VISIBLE);
                        cbDel.setVisibility(View.VISIBLE);
                        bcView.initBottomView(InStoreActivity.this, this, null, okClick, itemOnClick, true, cbDel);
                    } else if (stBean.get_id().equals("1")) {//分包入库
                        scrollHead.setVisibility(View.VISIBLE);
                        cbDel.setVisibility(View.GONE);
                        bcView.initBottomView(InStoreActivity.this, this, null, okClick, itemOnClick, true, null);
                    } else if (stBean.get_id().equals("2")) {////分包入库退库
                        scrollHead.setVisibility(View.GONE);
                        cbDel.setVisibility(View.GONE);
                        bcView.initBottomView(InStoreActivity.this, this, null, okClick, itemOnClick, false, null);
                    } else if (stBean.get_id().equals("3")) {//分包生产入库
                        scrollHead.setVisibility(View.VISIBLE);
                        cbDel.setVisibility(View.GONE);
                        bcView.initBottomView(InStoreActivity.this, this, null, okClick, itemOnClick, true, null);
                    }
                    presenter.requestBarCodeInfo(InStoreActivity.this, stBean.get_id(), storeBean.getfIfUsePlace(), storeBean.getfStkCode());
                    if (!checkConditions()) {
                        Bundle bb = new Bundle();
                        bb.putParcelable("SELECT_STORETYPE", stBean);
                        startNewAct(InStoreConditionActivity.class, bb, Tokens.Putint.JUMPCODE_SETOUTCONDITION);
                    }
                }
            } else {
                tvfRightSet.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            NLogger.e("初始化入库数据出错", e.getMessage());
        }
        presenter.netStatusBeanDao.insert(new NetStatusBean("ruku", 0, 0, 0, 0));
        final Handler reflashNet = new Handler();
        reflashNet.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.reflashNet();
                NetStatusBean bean = presenter.netStatusBeanDao.load("ruku");
                if (bean != null) {
                    yingsao.setText(bean.getYingsao() + "");
                    yisao.setText(bean.getYisao() + "");
                    benci.setText(bean.getBenci() + "");
                    weisao.setText(bean.getWeisao() + "");
                }
                reflashNet.postDelayed(this, 1500);
            }
        }, 1500);
    }

    /**
     * 检测必选条件是否缺失
     */
    private boolean checkConditions() {
        boolean requiredConditionIsChoose = false;//必选条件是否已经全部选择了
        if (stBean.get_id().equals("2")) {//退库
            requiredConditionIsChoose = true;
        } else {
            if (conditionBean != null) {
                if (!conditionBean.getFbillCode().equals("") && !conditionBean.getFreasonCode().equals("") && !conditionBean.getFdeptCode().equals("")) {
                    if (storeBean.getfIfUsePlace() == 1) {
                        if (!conditionBean.getFbinlocationCode().equals("")) {
                            requiredConditionIsChoose = true;
                        }
                    } else {
                        requiredConditionIsChoose = true;
                    }
                }
            }
        }
        return requiredConditionIsChoose;
    }

    /**
     * @param s 条码
     * @param isNeedSuccessRing 是否需要成功的声音
     **/
    private void SendBarCode(String s, boolean isNeedSuccessRing) {
        boolean isExistAndSuccessed = false;//是否已经存在并且解析成功了
        //boolean isSuccessed = false;//入股已经存在，则是否已经解析成功
        for (int i = 0; i < inScanLogList.size(); i++) {
            if (inScanLogList.get(i).getFBarCode().equals(s) && inScanLogList.get(i).getFErrorMsg().equals("识别成功")) {
                isExistAndSuccessed = true;
                break;
            }
        }
        if (isExistAndSuccessed) {
            RepatSound();
            ScanLogBean beanRe = new ScanLogBean(true);
            beanRe.setFBarCode(s);
            beanRe.setFRemark("重复扫描");
            beanRe.setFErrorMsg("重复扫描");
            beanRe.setFScanDate(DateUtils.getTodayDateTime("yyyy-MM-dd hh:mm:ss"));
            inScanLogList.add(beanRe);
            hideUILoading();
        } else {
            if(isNeedSuccessRing){SuccessSound();}
            ScanLogBean bean = new ScanLogBean(false);
            bean.setFBarCode(s);
            bean.setFRemark("");
            bean.setFErrorMsg("");
            bean.setFScanDate("");
            inScanLogList.add(bean);
        }
        //刷新一下列表
        reflashDataList();
    }

    @OnClick({R.id.ivfLeft, R.id.tvfRightSet})
    void ThisViewClick(View v) {
        switch (v.getId()) {
            case R.id.ivfLeft:
                inFinish();
                break;
            case R.id.tvfRightSet:
                if (!DoubleClickU.isFastDoubleClick(R.id.tvfRightSet)) {
                    if (stBean.get_id().equals("0") || stBean.get_id().equals("1") || stBean.get_id().equals("2") || stBean.get_id().equals("3")) {
                        Bundle bb = new Bundle();
                        bb.putParcelable("SELECT_STORETYPE", stBean);
                        if (!firstBarCode.equals("")) {//通过第一次扫码而调起的条件设置
                            startNewAct(InStoreConditionActivity.class, bb, Tokens.Putint.JUMPCODE_SETOUTCONDITION_BYFIRSTSCAN);
                        } else {//通过点击“出货条件”按钮或进界面自动调起的条件设置
                            startNewAct(InStoreConditionActivity.class, bb, Tokens.Putint.JUMPCODE_SETOUTCONDITION);
                        }
                    }
                }
                break;
        }
    }

    private void submit() {
        try {
            JSONArray jr = new JSONArray();
            List<BarItemBean> beans = presenter.baritemDao.loadAll();
            String stkinlogno = "";
            for (int k = 0; k < beans.size(); k++) {
                BarItemBean bean = beans.get(k);
                JSONObject o = new JSONObject();
                o.put("fBarCode", bean.getFBarCode());//条码
                o.put("fOrdNo", bean.getFOrdNo());//订单号
                o.put("fProdNo", bean.getFProdNo());//生产单号
                o.put("fSplitBatchNo", bean.getFSplitBatchNo());//拆单批次
                o.put("fSPCode", bean.getFSPCode());//分包号
                o.put("fPackMark", bean.getFPackMark());//分包说明
                o.put("fPlaceCode", conditionBean.getFbinlocationCode());//储位代号
                o.put("fPlaceName", conditionBean.getFbinlocationName());//储位名称
                o.put("fCtnL", bean.getFCtnL());//包装长
                o.put("fCtnW", bean.getFCtnW());//包装宽
                o.put("fCtnH", bean.getFCtnH());//包装高
                o.put("fPackCuft", bean.getFPackCuft());//包装体积
                o.put("fPackPcs", bean.getFPackPcs());//包装件数
                o.put("fScanorID", bean.getFScanorID());//扫描人代号
                o.put("fScanor", bean.getFScanor());//扫描人
                o.put("fScanDate", bean.getFScanDate());//扫描日期
                o.put("fID", bean.getFID());//条码ID
                if (stBean.get_id().equals("2")) {//退库
                    o.put("fStkInLogNo", bean.getFStkInLogNo());
                }
                jr.put(o);
                stkinlogno = bean.getFStkInLogNo();
            }
            if (jr.length() > 0) {
                bcView.setIsOkEnable(false);
                showQMTipLoading("正在提交..", QMUITipDialog.Builder.ICON_TYPE_LOADING, false);
                presenter.StkInByPackSaveBar(InStoreActivity.this, Integer.parseInt(stBean.get_id()), storeBean.getfStkCode(), conditionBean.getFbillCode(), conditionBean.getFreasonCode(), conditionBean.getFbinlocationCode(), conditionBean.getFdeptCode(), stBean.get_id().equals("2") ? stkinlogno : conditionBean.getFrukubillCode(), cbDel.isChecked() ? "1" : "0", "", jr);
            }
//            } else {
//                new QMUIDialog.MessageDialogBuilder(InStoreActivity.this)
//                        .setTitle("提示")
//                        .setMessage("没有齐套数据可提交")
//                        .addAction("确定", new QMUIDialogAction.ActionListener() {
//                            @Override
//                            public void onClick(QMUIDialog dialog, int index) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .create(mCurrentDialogStyle).show();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submit2(String spsid) {
        try {
            JSONArray jr = new JSONArray();
            List<HeBaoBarItemBean> beans = presenter.heBaoBarItemBeanDao.loadAll();
            for (HeBaoBarItemBean hb : beans) {
                JSONObject o = new JSONObject();
                o.put("fBarCode", hb.getFBarCode());
                o.put("fOrdNo", hb.getFOrdNo());
                o.put("fSPCode", hb.getFSPCode());
                o.put("fPackNo", hb.getFPackNo());
                o.put("fStkCode", hb.getFStkCode());
                o.put("fPlaceCode", hb.getFPlaceCode());
                o.put("fScanorID", hb.getFScanorID());
                o.put("fScanor", hb.getFScanor());
                o.put("fScanDate", hb.getFScanDate());
                o.put("fOrdSpID", hb.getFOrdSpID());
                jr.put(o);
            }
            if (jr.length() > 0) {
                showQMTipLoading("正在提交..", QMUITipDialog.Builder.ICON_TYPE_LOADING);
                presenter.CombineByPack(InStoreActivity.this, spsid, jr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRightSetName(String title) {
        if (title.contains("查询")) {
            return "查询条件";
        } else if (title.contains("分包入库退库")) {
            return "入库退库条件";
        } else if (title.contains("分包入库") || title.contains("分包生产入库")) {
            return "入库条件";
        } else if (title.contains("出货")) {
            return "出货条件";
        } else if (title.contains("包装分拣")) {
            return "分拣条件";
        } else if (title.contains("调拨")) {
            return "调拨条件";
        }
        return "";
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
    public void setSuccessResult(String msg) {
        hideQMTipLoading();
        new QMUIDialog.MessageDialogBuilder(InStoreActivity.this)
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
        new QMUIDialog.MessageDialogBuilder(InStoreActivity.this)
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
    public void setErrorMsg(int code, String msg) {
        Message message = new Message();
        message.what = 0;
        message.obj = msg;
        msgHandler.sendMessage(message);
    }

    Handler msgHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    emptyView.show(false);
                    bcView.setIsOkEnable(true);
                    hideQMTipLoading();
                    Toast.makeText(InStoreActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    emptyView.hide();
                    etWithClear.getEditText().setText("");
                    tablell.removeAllViews();

                    int[] tableWith = CommUtil.SetComWidth(InStoreActivity.this, tmpTitles.length, 0, false);
                    TableViewDetail tableView = new TableViewDetail(InStoreActivity.this,
                            tablell, tmpTitles, tmpData, tableWith, false, null);

                    tableViewMap.put(tmpIndex, tableView);

                    if (isCanClickConfirm()) {
                        bcView.setIsOkEnable(true);
                    } else {
                        bcView.setIsOkEnable(false);
                    }
                    break;
            }
        }
    };

    @Override
    public void setTitles(List<String> lt) {
        tableViewMap = new HashMap<>();
        for (int i = 0; i < lt.size(); i++) {
            mTabEntities.add(new TabEntity(lt.get(i), R.mipmap.checked, R.mipmap.checked));
            tableViewMap.put(i, null);
        }
        tab.setTabData(mTabEntities);
        tab.setCurrentTab(0);
        tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                reflashDataList();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        if (stBean.get_id().equals("4")) presenter.loadHeBaoDb(0);
        else presenter.loadDb(0, stBean.get_id());
    }

    private int tmpIndex;
    private String[] tmpTitles;
    private List<String[]> tmpData;

    @Override
    public void setDatas(int index, String[] titles, List<String[]> data) {
        hideUILoading();
        this.tmpIndex = index;
        this.tmpTitles = titles;
        this.tmpData = data;
        msgHandler.sendEmptyMessage(1);
    }

    @Override
    public void reflashDataList() {
        if (stBean.get_id().equals("4")) {//合包扫描
            presenter.loadHeBaoDb(tab.getCurrentTab());
        } else {//不是合包入库
            presenter.loadDb(tab.getCurrentTab(), stBean.get_id());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Tokens.Putint.JUMPCODE_SETOUTCONDITION_BYFIRSTSCAN:
                try {
                    if (data != null) {
                        InStoreConditionBean inSCondBean = (InStoreConditionBean) data.getSerializableExtra("INSTORE_CONDITION");
                        if (inSCondBean != null) {
                            conditionBean = inSCondBean;
                            if (!firstBarCode.equals("")) {
                                SendBarCode(firstBarCode, false);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                firstBarCode = "";
                break;
            case Tokens.Putint.JUMPCODE_SETOUTCONDITION:
                try {
                    if (data != null) {
                        boolean isChange = data.getBooleanExtra("ISCHANGE", false);
                        if (isChange) {
                            isNeedAsyn = false;//必选条件有改变则清空
                            delHeBaoData(true);
                            InStoreConditionBean inSCondBean = (InStoreConditionBean) data.getSerializableExtra("INSTORE_CONDITION");
                            if (inSCondBean != null) {
                                conditionBean = inSCondBean;
                                if (!firstBarCode.equals("")) {
                                    SendBarCode(firstBarCode, false);
                                }
                            }
                            isNeedAsyn = true;
                            presenter.requestBarCodeInfo(InStoreActivity.this, stBean.get_id(), storeBean.getfIfUsePlace(), storeBean.getfStkCode());
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
        if (stBean.get_id().equals("4")) {//合包扫描特殊处理
            List<HeBaoBarItemBean> beans = presenter.heBaoBarItemBeanDao.loadAll();
            if (beans != null && beans.size() > 0) {
                return true;
            }
        } else {////合包扫描特殊处理
            List<BarItemBean> beans = presenter.baritemDao.loadAll();
            if (beans != null && beans.size() > 0) {
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
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void resetListView() {
        if (tab != null) {
            tab.setCurrentTab(1);
            presenter.loadDb(1, stBean.get_id());
        }
    }

    @Override
    public void showDelBack(List<DelDataBean.Items.Infos> items) {

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
    public void goQitaoError(String data) {
        try {
            hideUILoading();
            JSONObject head = new JSONObject(data);
            if (head.getBoolean("success")) {
                JSONObject jo = new JSONObject(head.getString("data"));
                try {
                    JSONArray jr = new JSONArray(jo.getString("item"));
                    if (jr.length() > 0) {
                        try {
                            JSONObject jj = new JSONObject(jo.getString("item"));
                            boolean bl = jj.getBoolean("fok");
                            if (bl) {

                            }
                        } catch (Exception e) {
                            Bundle b = new Bundle();
                            b.putString("MIAOSHU", "下列订单分包未分拣,本次分拣扫描分包已自动暂存");
                            b.putString("TYPE", stBean.get_id());
                            b.putString("DATATABLES", jo.getString("item"));
                            startNewAct(NoQiTaoUIActivity.class, b);
                        }
                    }
                } catch (Exception e) {
                    setSuccessResult("操作成功");
                }
            } else {
                setFailResult(head.getString("errormsg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRukuDanju(String rukuDanju) {
        conditionBean.setFrukubillCode(rukuDanju);
        if (inSotreCondMap == null) inSotreCondMap = new HashMap<>();
        inSotreCondMap.put(stBean.get_id(), conditionBean);
    }

    @Override
    public void setChuWei(String chuwei) {
        conditionBean.setFbinlocationCode(chuwei);
        if (inSotreCondMap == null) inSotreCondMap = new HashMap<>();
        inSotreCondMap.put(stBean.get_id(), conditionBean);
    }

    @Override
    public void setCbDelStatu(boolean bl) {
        if (cbDel != null) {
            cbDel.setEnabled(bl);
        }
    }

    /**
     * 1 分包入库/生产入库扫描 11 备货 2 出库 24 出库退库 25 入库退库  29 分拣 30 合包
     **/
    private void ClearOnClose() {
        try {
            //调接口清空缓存
            if (stBean.get_id().equals("0") || stBean.get_id().equals("1") || stBean.get_id().equals("2") || stBean.get_id().equals("3")) {
                List<BarItemBean> beans = presenter.baritemDao.loadAll();
                if (beans != null && beans.size() > 0) {
                    JSONArray jr = new JSONArray();
                    for (BarItemBean bt : beans) {
                        JSONObject jo = new JSONObject();
                        jo.put("fBarCode", bt.getFBarCode());
                        jr.put(jo);
                    }

                    presenter.StkInByPackOnClose(InStoreActivity.this, GetOritype(stBean.get_id()), jr);
                }
            } else if (stBean.get_id().equals("4")) {
                List<HeBaoBarItemBean> beans = presenter.heBaoBarItemBeanDao.loadAll();
                if (beans != null && beans.size() > 0) {
                    JSONArray jr = new JSONArray();
                    for (HeBaoBarItemBean bt : beans) {
                        JSONObject jo = new JSONObject();
                        jo.put("fBarCode", bt.getFBarCode());
                        jr.put(jo);
                    }

                    presenter.StkInByPackOnClose(InStoreActivity.this, GetOritype(stBean.get_id()), jr);
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void setSpSidDatas(List<SpSidBean> beans) {
        hideUILoading();
        try {
            spSidBeans = beans;
            if (spSidBeans != null && spSidBeans.size() > 0) {
                final String[] items = new String[spSidBeans.size()];
                int i = 0;
                for (SpSidBean s : spSidBeans) {
                    items[i] = s.getfSPSortName();
                    i++;
                }
                new QMUIDialog.CheckableDialogBuilder(InStoreActivity.this)
                        .setTitle("请选择包装分类")
                        .addItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SpSidBean b = spSidBeans.get(which);
                                submit2(b.getfID());
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
    public void setCombineBarCode(JSONObject o) {
        hideUILoading();
        try {
            JSONArray jr = new JSONArray(o.getString("baritem"));
            JSONObject oo = new JSONObject(jr.get(0).toString());
            final String fBarcode = oo.getString("fBarcode");
            new QMUIDialog.MessageDialogBuilder(InStoreActivity.this)
                    .setTitle("提示")
                    .setMessage("合包成功, 新条码为: " + fBarcode)
                    .addAction("好的", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();

                            //清除合包数据
                            delHeBaoData(true);

                            String printer = Hawk.get("PrinterMAC", "");
                            String schme = Hawk.get("PrintFrom", "");
                            if (!printer.equals("") && !schme.equals("")) {
                                showQMTipLoading("请求打印..", QMUITipDialog.Builder.ICON_TYPE_LOADING);
                                presenter.StkScanByPackPrintBar(InStoreActivity.this, printer, schme, fBarcode);
                            } else {
                                Toast.makeText(InStoreActivity.this, "打印机或打印方案没设置,请先设置后打印", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .create(mCurrentDialogStyle).show();
        } catch (Exception e) {
            e.printStackTrace();
            setErrorMsg(-1, "提交成功, 但返回结构有误");
        }
    }

    /**
     * 是否重设checkbox
     *
     * @param isResetCheckBox
     */
    @Override
    public void delHeBaoData(boolean isResetCheckBox) {
        bcView.setIsOkEnable(false);
        if (stBean.get_id().equals("0") || stBean.get_id().equals("1") || stBean.get_id().equals("2") || stBean.get_id().equals("3")) {
            ClearOnClose();
            presenter.inStatusBeanDao.deleteAll();
            presenter.qitaoDao.deleteAll();
            presenter.baritemDao.deleteAll();
            //presenter.scanlogDao.deleteAll();
            if (inScanLogList != null) {
                inScanLogList.clear();
            }
            tableViewMap.put(0, null);
            tableViewMap.put(1, null);
            tableViewMap.put(2, null);
            tableViewMap.put(3, null);
            tablell.removeAllViews();
            emptyView.show("没有数据", "");
            tab.setCurrentTab(0);
            presenter.loadDb(0, stBean.get_id());

            yingsao.setText("0");
            yisao.setText("0");
            benci.setText("0");
            weisao.setText("0");
            NetStatusBean bean = presenter.netStatusBeanDao.load("ruku");
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

        } else if (stBean.get_id().equals("4")) {
            ClearOnClose();
            presenter.heBaoBarItemBeanDao.deleteAll();
            presenter.heBaoGoodsItemDao.deleteAll();
            //presenter.scanlogDao.deleteAll();
            if (inScanLogList != null) {
                inScanLogList.clear();
            }
            tableViewMap.put(0, null);
            tableViewMap.put(1, null);
            tableViewMap.put(2, null);

            tablell.removeAllViews();
            emptyView.show("没有数据", "");
            tab.setCurrentTab(0);
            presenter.loadHeBaoDb(0);
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

    private String GetOritype(String stId) {
        if (stId.equals("0")) {//分拣
            return "29";
        } else if (stId.equals("1")) {//入库
            return "1";
        } else if (stId.equals("2")) {//退库
            return "25";
        } else if (stId.equals("3")) {//生产入库
            return "1";
        } else if (stId.equals("4")) {//合包
            return "30";
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        isNeedAsyn = false;
        if (inSotreCondMap == null) {
            inSotreCondMap = new HashMap<>();
        }
        inSotreCondMap.put(stBean.get_id(), conditionBean);
        Hawk.put(Tokens.Putint.PUTIN_CONDITONS, inSotreCondMap);
        conditionBean = null;
        if (SoonforApplication.inScanLogList != null) {
            SoonforApplication.inScanLogList.clear();
        }
        ClearOnClose();

        super.onDestroy();
    }

    private View.OnClickListener okClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!DoubleClickU.isFastDoubleClick(R.id.bnfIsOk))
                if (stBean.get_id().equals("4")) {//合包扫描
                    // String printer = Hawk.get("PrinterMAC", "");
                    // String schme = Hawk.get("PrintFrom", "");
                    //if(printer.equals("") || schme.equals("")){
                    //    Toast.makeText(InStoreActivity.this, "没有设置打印机", Toast.LENGTH_SHORT).show();
                    // }else{
                    //先调一个 获取 分包分类列表
                    if (!presenter.isHaveNoAsyn()) {
                        showQMTipLoading("正在分包分类..", QMUITipDialog.Builder.ICON_TYPE_LOADING, false);
                        presenter.GetSPSIDList(InStoreActivity.this);
                    } else MyToast.showToast(InStoreActivity.this, "日志中尚有未解析条码，暂不能提交");

                    //}
                } else {
                    if (!presenter.isHaveNoAsyn()) submit();
                    else MyToast.showToast(InStoreActivity.this, "日志中尚有未解析条码，暂不能提交");
                }
//                if (storeBean.getfIfUsePlace() == 0) {
//                    if (danju == null || yuanyin == null || dept == null || storeBean == null) {
//                        Toast.makeText(InStoreActivity.this, "必须先选择条件", Toast.LENGTH_SHORT).show();
//                    } else {
//                        submit();
//                    }
//                } else if (storeBean.getfIfUsePlace() == 1) {
//                    if (danju == null || chuwei == null || yuanyin == null || dept == null || storeBean == null) {
//                        Toast.makeText(InStoreActivity.this, "必须先选择条件", Toast.LENGTH_SHORT).show();
//                    } else {
//                        submit();
//                    }
//                }
//            }
        }
    };

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
                            b.putString("source", "inStore@" + stBean.get_id());
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
                            if (stBean.get_id().equals("0") || stBean.get_id().equals("1") || stBean.get_id().equals("3")) {
                                Bundle bb = new Bundle();
                                bb.putParcelable("SELECT_STORETYPE", stBean);
                                bb.putString("MODEL", "instore");
                                bb.putString("logistbatchno", "");//物流批次
                                startNewAct(UnScanBarCodeActivity.class, bb);
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
        new QMUIDialog.MessageDialogBuilder(InStoreActivity.this)
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
                        delHeBaoData(true);
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    private void inFinish() {
//        if(bcView!=null && bcView.getPopupWindow()!=null && bcView.getPopupWindow().isShowing()) {
//            bcView.getPopupWindow().dismiss();
//        }
        new QMUIDialog.MessageDialogBuilder(InStoreActivity.this)
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
                        finish();
                    }
                })
                .create(mCurrentDialogStyle).show();
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
        SoundUtil.getInstense(this).setHintSound(3, 1500);//1成功2重复3失败
    }

    public static void FinishActivity() {
        if (sActivity != null && ActivityUtils.isRunning(sActivity)) {
            sActivity.finish();
        }
    }
}
