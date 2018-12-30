package com.soonfor.warehousemanager.module.instore;

import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.soonfor.warehousemanager.dao.BarItemBeanDao;
import com.soonfor.warehousemanager.dao.HeBaoBarItemBeanDao;
import com.soonfor.warehousemanager.dao.HeBaoGoodsItemDao;
import com.soonfor.warehousemanager.dao.OrdInStatusBeanDao;
import com.soonfor.warehousemanager.dao.OrdQitaoBeanDao;
import com.soonfor.warehousemanager.dao.OutOrdInStatusBeanDao;
import com.soonfor.warehousemanager.dao.OutOrdQitaoBeanDao;
import com.soonfor.warehousemanager.module.instore.beans.BarItemBean;
import com.soonfor.warehousemanager.module.instore.beans.DelDataBean;
import com.soonfor.warehousemanager.module.instore.beans.OrdInStatusBean;
import com.soonfor.warehousemanager.module.instore.beans.OrdQitaoBean;
import com.soonfor.warehousemanager.module.instore.beans.hebao.HeBaoGoodsItem;
import com.soonfor.warehousemanager.module.instore.beans.hebao.SpSidBean;
import com.soonfor.warehousemanager.module.instore.flowtype.FlowTypeBean;
import com.soonfor.warehousemanager.module.outstore.beans.Out2BarItemBean;
import com.soonfor.warehousemanager.module.outstore.beans.OutOrdInStatusBean;
import com.soonfor.warehousemanager.module.outstore.beans.OutOrdQitaoBean;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.SoundUtil;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.soonfor.warehousemanager.SoonforApplication.inScanLogList;
import static com.soonfor.warehousemanager.SoonforApplication.bar2Map;
import static com.soonfor.warehousemanager.SoonforApplication.outScanLogList;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/15 0015 16:36
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class deleteBarCodeActivity extends BaseActivity<InStorePresenter> implements IInStoreView {

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    @BindView(R.id.etWithClear)
    EditTextWithClear etWithClear;
    @BindView(R.id.txtpinhao)
    TextView txtpinhao;
    @BindView(R.id.txtpinming)
    TextView txtpinming;
    @BindView(R.id.txtguige)
    TextView txtguige;
    @BindView(R.id.txtdingdanhao)
    TextView txtdingdanhao;
    @BindView(R.id.txtfenbaohao)
    TextView txtfenbaohao;
    FlowTypeBean stBean;
    String[] source;
    @BindView(R.id.cbDel)
    CheckBox cbDek;

    private String curTagNo = "";

    //入库模块的DAO
    public OrdInStatusBeanDao inStatusBeanDao;
    public OrdQitaoBeanDao qitaoDao;
    public BarItemBeanDao baritemDao;
    //public ScanLogBeanDao scanlogDao;
    //入库模块中的合包的DAO
    public HeBaoBarItemBeanDao heBaoBarItemBeanDao;
    public HeBaoGoodsItemDao heBaoGoodsItemDao;

    //出库模块的DAO
    public OutOrdInStatusBeanDao outinStatusBeanDao;
    public OutOrdQitaoBeanDao outqitaoDao;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_delete_bar_code;
    }

    @Override
    protected void initPresenter() {
        stBean = getIntent().getExtras().getParcelable("SELECT_STORETYPE");
        presenter = new InStorePresenter(this, Integer.parseInt(stBean.get_id()));
    }

    @Override
    protected void initViews() {
        cbDek.setVisibility(View.GONE);
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("扫码删除");

        //从上个界面接收来源
        String tmp = getIntent().getExtras().getString("source");
        if (tmp != null) {
            source = tmp.split("@");
        }
        //根据来源初始化对应的DAO
        try {
            if (source[0].equals("inStore")) {
                if (source[1].equals("4")) {
                    heBaoBarItemBeanDao = SoonforApplication.mDaoSession.getHeBaoBarItemBeanDao();
                    heBaoGoodsItemDao = SoonforApplication.mDaoSession.getHeBaoGoodsItemDao();
                    //scanlogDao = SoonforApplication.mDaoSession.getScanLogBeanDao();
                } else {
                    inStatusBeanDao = SoonforApplication.mDaoSession.getOrdInStatusBeanDao();
                    qitaoDao = SoonforApplication.mDaoSession.getOrdQitaoBeanDao();
                    baritemDao = SoonforApplication.mDaoSession.getBarItemBeanDao();
                    //scanlogDao = SoonforApplication.mDaoSession.getScanLogBeanDao();
                }
            } else if (source[0].equals("outStore")) {
                outinStatusBeanDao = SoonforApplication.mDaoSession.getOutOrdInStatusBeanDao();
                outqitaoDao = SoonforApplication.mDaoSession.getOutOrdQitaoBeanDao();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        etWithClear.addTextChangedListener(CommUtil.watcher);
        etWithClear.setOnEditorActionListener(0, new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                EditText edt = (EditText) v;
                String s = edt.getText().toString();
                if (curTagNo.equals(s)) {
                    return true;
                }
                curTagNo = s;

                //先检查是否已经被扫
                if (source[0].equals("inStore")) {
                    if (source[1].equals("4")) {
                        if (heBaoBarItemBeanDao.load(s) == null) {
                            Toast.makeText(deleteBarCodeActivity.this, "此条码不在本次扫描, 无法删除", Toast.LENGTH_LONG).show();
                        } else {
                            //再提交删除
                            presenter.CombineByPackDelBar(deleteBarCodeActivity.this, s);
                        }
                    } else {
                        if (baritemDao.load(s) == null) {
                            Toast.makeText(deleteBarCodeActivity.this, "此条码不在本次扫描, 无法删除", Toast.LENGTH_LONG).show();
                        } else {
                            //再提交删除
                            presenter.StkInByPackDelBar(deleteBarCodeActivity.this, Integer.parseInt(stBean.get_id()), s);
                        }
                    }
                } else if (source[0].equals("outStore")) {
                    if (bar2Map == null || !bar2Map.containsKey(s)) {
                        Toast.makeText(deleteBarCodeActivity.this, "此条码不在本次扫描, 无法删除", Toast.LENGTH_LONG).show();
                    } else {
                        //再提交删除
                        presenter.StkPrepByPackDelBar(deleteBarCodeActivity.this, source[1], s);
                    }
                }

                return true;
            }
        });
    }

    @Override
    public void setDatas(int index, String[] titles, List<String[]> data) {

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

    @Override
    public void setTitles(List<String> lt) {

    }

    @Override
    public void setSuccessResult(String msg) {
        hideQMTipLoading();
        new QMUIDialog.MessageDialogBuilder(deleteBarCodeActivity.this)
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
        new QMUIDialog.MessageDialogBuilder(deleteBarCodeActivity.this)
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

    @OnClick({R.id.txtClose})
    public void OnViewClick(View view) {
        if (view.getId() == R.id.txtClose) {
            setResult(888);
            finish();
        }
    }

    @Override
    public void resetListView() {

    }

    @Override
    public void showDelBack(List<DelDataBean.Items.Infos> items) {
        try {
            if (items != null || items.size() > 0) {
                Toast.makeText(deleteBarCodeActivity.this, "条码删除成功", Toast.LENGTH_SHORT).show();

                txtpinhao.setText(items.get(0).getfGoodsCode());
                txtpinming.setText(items.get(0).getfGoodsName());
                txtguige.setText(items.get(0).getfSizeDesc());
                txtdingdanhao.setText(items.get(0).getfOrdNo());
                txtfenbaohao.setText(items.get(0).getfSPCode());

                //开始删除记录
                if (source[0].equals("inStore")) {
                    if (source[1].equals("4")) {
                        //删除主表
                        String fOrdSpID = heBaoBarItemBeanDao.load(curTagNo) == null ? "" : heBaoBarItemBeanDao.load(curTagNo).getFOrdSpID();
                        heBaoBarItemBeanDao.deleteByKey(curTagNo);
                        //删除明细表
                        List<HeBaoGoodsItem> tmp = heBaoGoodsItemDao.loadAll();
                        for (HeBaoGoodsItem hb : tmp) {
                            if (hb.getFOrdSpID().equals(fOrdSpID)) {
                                heBaoGoodsItemDao.delete(hb);
                            }
                        }
                    } else {
                        //未齐套
                        OrdInStatusBean inStatusBean = inStatusBeanDao.load(items.get(0).getfOrdNo());
                        if (inStatusBean != null) {
                            if (source[1].equals("0") || source[1].equals("1") || source[1].equals("2") || source[1].equals("3")) {
                                inStatusBeanDao.update(updateInStatu(inStatusBean));
                            }
                        }
                        //齐套
                        OrdQitaoBean qitaoBean = qitaoDao.load(items.get(0).getfOrdNo());
                        if (qitaoBean != null) {
                            if (source[1].equals("0") || source[1].equals("1") || source[1].equals("2") || source[1].equals("3")) {
                                qitaoDao.update(updateQitao(qitaoBean));
                            }
                        }
                        //检查齐套的情况, 如果记录变成了不齐套,则把记录移回去不齐套的表
                        IsNeedBackIN(items.get(0).getfOrdNo());
                        //条码
                        BarItemBean barItemBean = baritemDao.load(curTagNo);
                        if (barItemBean != null) {
                            baritemDao.delete(barItemBean);
                        }
                    }
                    //删除日志
                    if (inScanLogList != null) {
                        for (int i = 0; i < inScanLogList.size(); i++) {
                            if (i < inScanLogList.size() && inScanLogList.get(i).getFBarCode().equals(curTagNo)) {
                                inScanLogList.remove(i);
                            }
                        }
                    }
                } else if (source[0].equals("outStore")) {
                    //未齐套
                    OutOrdInStatusBean inStatusBean = outinStatusBeanDao.load(items.get(0).getfOrdNo());
                    if (inStatusBean != null) {
                        outinStatusBeanDao.update(updateOutInStatu(inStatusBean));
                    }
                    //齐套
                    OutOrdQitaoBean qitaoBean = outqitaoDao.load(items.get(0).getfOrdNo());
                    if (qitaoBean != null) {
                        outqitaoDao.update(updateOutQitao(qitaoBean));
                    }
                    //检查齐套的情况, 如果记录变成了不齐套,则把记录移回去不齐套的表
                    IsNeedBackOUT(items.get(0).getfOrdNo());
                    if (bar2Map != null) {
                        for (Map.Entry<String, Out2BarItemBean> entry : bar2Map.entrySet()){
                            Out2BarItemBean bean = entry.getValue();
                            if (bean.getfBarCode().equals(curTagNo)) {
                                bar2Map.remove(bean.getfBarCode());
                                break;
                            }
                        }
                    }
                }
                //日志
                // OutScanLogBean scanLogBean = outscanlogDao.load(curTagNo);
                if (outScanLogList != null) {
                    for (int i = 0; i < outScanLogList.size(); i++) {
                        if (i < outScanLogList.size() && outScanLogList.get(i).getFBarCode().equals(curTagNo)) {
                            outScanLogList.remove(i);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 102 || keyCode == 103 || keyCode == 110) {
            etWithClear.setText("");
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {
            setResult(888);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void callOnDelAllDatas() {

    }

    private OrdInStatusBean updateInStatu(OrdInStatusBean inStatusBean) {
        //本次扫描数
        int i = Integer.parseInt(inStatusBean.getFThisScanPackQty());
        if (i > 0) {
            i--;
        }
        inStatusBean.setFThisScanPackQty(i + "");

        //未扫描数
        int ii = Integer.parseInt(inStatusBean.getFUnScanPackQty());
        ii++;
        inStatusBean.setFUnScanPackQty(ii + "");

        return inStatusBean;
    }

    private OrdQitaoBean updateQitao(OrdQitaoBean qitaoBean) {
        //本次扫描数
        int i = Integer.parseInt(qitaoBean.getFThisScanPackQty());
        if (i > 0) {
            i--;
        }
        qitaoBean.setFThisScanPackQty(i + "");

        //未扫描数
        int ii = Integer.parseInt(qitaoBean.getFUnScanPackQty());
        ii++;
        qitaoBean.setFUnScanPackQty(ii + "");

        return qitaoBean;
    }

    private OutOrdInStatusBean updateOutInStatu(OutOrdInStatusBean inStatusBean) {
        //本次扫描数
        int i = Integer.parseInt(inStatusBean.getFThisScanPackQty());
        if (i > 0) {
            i--;
        }
        inStatusBean.setFThisScanPackQty(i + "");

        //未扫描数
        int ii = Integer.parseInt(inStatusBean.getFUnScanPackQty());

        boolean cbChecked = Hawk.get("OutCbDel", false);
        if (cbChecked) {
            ii--;
        } else {
            ii++;
        }
        inStatusBean.setFUnScanPackQty(ii + "");

        return inStatusBean;
    }

    private OutOrdQitaoBean updateOutQitao(OutOrdQitaoBean qitaoBean) {
        //本次扫描数
        int i = Integer.parseInt(qitaoBean.getFThisScanPackQty());
        if (i > 0) {
            i--;
        }
        qitaoBean.setFThisScanPackQty(i + "");

        //未扫描数
        int ii = Integer.parseInt(qitaoBean.getFUnScanPackQty());
        ii++;
        qitaoBean.setFUnScanPackQty(ii + "");

        return qitaoBean;
    }

    @Override
    public void goQitaoError(String data) {

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
    public void reflashDataList() {

    }


    private void IsNeedBackIN(String ordno) {
        OrdQitaoBean qitaoBeanNew = qitaoDao.load(ordno);
        if (qitaoBeanNew != null) {
            int totle = CommUtil.formatStrToInteger(qitaoBeanNew.getFTotalPackQty());
            int yiru = 0;
            if (source[1].equals("0")) {
                yiru = CommUtil.formatStrToInteger(qitaoBeanNew.getFSortingPackQty());
            } else if (source[1].equals("1") || source[1].equals("2") || source[1].equals("3")) {
                yiru = CommUtil.formatStrToInteger(qitaoBeanNew.getFStkInPackQty());
            }
            //int yisao = CommUtil.formatStrToInteger(qitaoBeanNew.getFScanedPackQty());
            int benci = CommUtil.formatStrToInteger(qitaoBeanNew.getFThisScanPackQty());

            if (totle == (yiru + benci)) { // 已齐套

            } else { //已经不齐套了, 弄回去吧
                OrdInStatusBean inStatusBeanNew = inStatusBeanDao.load(ordno);
                if (inStatusBeanNew == null) {
                    if (source[1].equals("0")) {
                        inStatusBeanNew = new OrdInStatusBean(ordno,
                                qitaoBeanNew.getFProdNo(), qitaoBeanNew.getFTotalPackQty(),
                                qitaoBeanNew.getFSortingPackQty(), "0",
                                qitaoBeanNew.getFScanedPackQty(), qitaoBeanNew.getFThisScanPackQty(),
                                qitaoBeanNew.getFUnScanPackQty());
                    } else if (source[1].equals("1") || source[1].equals("2") || source[1].equals("3")) {
                        inStatusBeanNew = new OrdInStatusBean(ordno,
                                qitaoBeanNew.getFProdNo(), qitaoBeanNew.getFTotalPackQty(),
                                "0", qitaoBeanNew.getFStkInPackQty(),
                                qitaoBeanNew.getFScanedPackQty(), qitaoBeanNew.getFThisScanPackQty(),
                                qitaoBeanNew.getFUnScanPackQty());
                    }
                    inStatusBeanDao.insert(inStatusBeanNew);
                    qitaoDao.delete(qitaoBeanNew);
                } else {
                    inStatusBeanNew.setFTotalPackQty(qitaoBeanNew.getFTotalPackQty());
                    if (source[1].equals("0")) {
                        inStatusBeanNew.setFSortingPackQty(qitaoBeanNew.getFSortingPackQty());
                        inStatusBeanNew.setFStkInPackQty("0");
                    } else if (source[1].equals("1") || source[1].equals("2") || source[1].equals("3")) {
                        inStatusBeanNew.setFSortingPackQty("0");
                        inStatusBeanNew.setFStkInPackQty(qitaoBeanNew.getFStkInPackQty());
                    }
                    inStatusBeanNew.setFScanedPackQty(qitaoBeanNew.getFScanedPackQty());
                    inStatusBeanNew.setFThisScanPackQty(qitaoBeanNew.getFThisScanPackQty());
                    inStatusBeanNew.setFUnScanPackQty(qitaoBeanNew.getFUnScanPackQty());
                    inStatusBeanDao.update(inStatusBeanNew);
                    qitaoDao.delete(qitaoBeanNew);
                }
            }

        }
    }


    private void IsNeedBackOUT(String ordno) {
        OutOrdQitaoBean qitaoBeanNew = outqitaoDao.load(ordno);
        if (qitaoBeanNew != null) {
            int totle = CommUtil.formatStrToInteger(qitaoBeanNew.getFTotalPackQty());
            int yiru = 0;
            yiru = CommUtil.formatStrToInteger(qitaoBeanNew.getFStkOutPackQty());
            //int yisao = CommUtil.formatStrToInteger(qitaoBeanNew.getFScanedPackQty());
            int benci = CommUtil.formatStrToInteger(qitaoBeanNew.getFThisScanPackQty());

            if (totle == (yiru + benci)) { // 已齐套

            } else { //已经不齐套了, 弄回去吧
                OutOrdInStatusBean inStatusBeanNew = outinStatusBeanDao.load(ordno);
                if (inStatusBeanNew == null) {
                    inStatusBeanNew = new OutOrdInStatusBean(ordno,
                            qitaoBeanNew.getFProdNo(), qitaoBeanNew.getFTotalPackQty(),
                            qitaoBeanNew.getFStkOutPackQty(),
                            qitaoBeanNew.getFScanedPackQty(), qitaoBeanNew.getFThisScanPackQty(),
                            qitaoBeanNew.getFUnScanPackQty());

                    outinStatusBeanDao.insert(inStatusBeanNew);
                    outqitaoDao.delete(qitaoBeanNew);
                } else {
                    inStatusBeanNew.setFTotalPackQty(qitaoBeanNew.getFTotalPackQty());
                    inStatusBeanNew.setFStkOutPackQty(qitaoBeanNew.getFStkOutPackQty());
                    inStatusBeanNew.setFScanedPackQty(qitaoBeanNew.getFScanedPackQty());
                    inStatusBeanNew.setFThisScanPackQty(qitaoBeanNew.getFThisScanPackQty());
                    inStatusBeanNew.setFUnScanPackQty(qitaoBeanNew.getFUnScanPackQty());
                    outinStatusBeanDao.update(inStatusBeanNew);
                    outqitaoDao.delete(qitaoBeanNew);
                }
            }

        }
    }

    @Override
    public void setRukuDanju(String rukuDanju) {

    }

    @Override
    public void setCbDelStatu(boolean bl) {

    }

    @Override
    public void setSpSidDatas(List<SpSidBean> beans) {

    }

    @Override
    public void setCombineBarCode(JSONObject o) {

    }

    @Override
    public boolean getCheckStu() {
        return false;
    }

    @Override
    public void setChuWei(String chuwei) {

    }

    @Override
    public void delHeBaoData(boolean isResetCheckBox) {

    }
}
