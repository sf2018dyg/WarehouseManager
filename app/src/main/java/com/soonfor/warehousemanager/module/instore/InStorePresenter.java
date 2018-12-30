package com.soonfor.warehousemanager.module.instore;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.bases.NetStatusBean;
import com.soonfor.warehousemanager.dao.BarItemBeanDao;
import com.soonfor.warehousemanager.dao.HeBaoBarItemBeanDao;
import com.soonfor.warehousemanager.dao.HeBaoGoodsItemDao;
import com.soonfor.warehousemanager.dao.NetStatusBeanDao;
import com.soonfor.warehousemanager.dao.OrdInStatusBeanDao;
import com.soonfor.warehousemanager.dao.OrdQitaoBeanDao;
import com.soonfor.warehousemanager.dao.PrinterBeanDao;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.module.instore.beans.BarItemBean;
import com.soonfor.warehousemanager.module.instore.beans.DelDataBean;
import com.soonfor.warehousemanager.module.instore.beans.OrdInStatusBean;
import com.soonfor.warehousemanager.module.instore.beans.OrdQitaoBean;
import com.soonfor.warehousemanager.module.instore.beans.ScanLogBean;
import com.soonfor.warehousemanager.module.instore.beans.YeqianBean;
import com.soonfor.warehousemanager.module.instore.beans.hebao.HeBaoBarItemBean;
import com.soonfor.warehousemanager.module.instore.beans.hebao.HeBaoGoodsItem;
import com.soonfor.warehousemanager.module.instore.beans.hebao.SpSidBean;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.DateUtils;
import com.soonfor.warehousemanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jesse.nativelogger.NLogger;

import static com.soonfor.warehousemanager.SoonforApplication.inScanLogList;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/11 0011 8:53
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class InStorePresenter extends BasePresenter<IInStoreView> implements AsyncUtils.AsyncCallback {
    private IInStoreView view;
    private int type;
    String title0 = "";
    String title1 = "";
    String title2 = "";
    String title3 = "";
    String title2_tuku = "";
    String title4 = "";
    String title5 = "";

    public NetStatusBeanDao netStatusBeanDao;
    public OrdInStatusBeanDao inStatusBeanDao;//订单入库状况表
    public OrdQitaoBeanDao qitaoDao;//齐套表
    public BarItemBeanDao baritemDao;//条码明细表
    //public ScanLogBeanDao scanlogDao;//扫描日志表
    //public List<ScanLogBean> scanLogBeanList;//扫描日志集合 （由于需要在表中显示重复条码，表不允许有重复主键才改为集合）
    public HeBaoBarItemBeanDao heBaoBarItemBeanDao;//合包条码明细表
    public HeBaoGoodsItemDao heBaoGoodsItemDao;//合包货品明细表
    //    //打印的
    public PrinterBeanDao printerBeanDao;

    public InStorePresenter(IInStoreView view, int type) {
        this.view = view;
        this.type = type;
        String colum = "";
        String columName = "";
        if (type == 0) {
            colum = "fSortingPackQty";
            columName = "已分拣包数";
        } else if (type == 1 || type == 2 || type == 3) {
            colum = "fStkInPackQty";
            columName = "已入库包数";
        }

        title0 = "{\"fOrdNo\":\"订单号\",\"fProdNo\":\"生产单号\",\"fTotalPackQty\":\"总包数\",\"" + colum + "\":\"" + columName + "\"," +
                "\"fScanedPackQty\":\"已扫描包数\",\"fThisScanPackQty\":\"本次扫描包数\",\"fUnScanPackQty\":\"未扫描包数\"}";

        title1 = "{\"fOrdNo\":\"订单号\",\"fProdNo\":\"生产单号\",\"fTotalPackQty\":\"总包数\",\"" + colum + "\":\"" + columName + "\"," +
                "\"fScanedPackQty\":\"已扫描包数\",\"fThisScanPackQty\":\"本次扫描包数\",\"fUnScanPackQty\":\"未扫描包数\"}";

        title2 = "{\"fBarCode\":\"条码代号\",\"fOrdNo\":\"订单号\",\"fSplitBatchNo\":\"拆单批次\",\"fSPCode\":\"分包号\"," +
                "\"fPackMark\":\"分包说明\",\"fPlaceCode\":\"储位代号\",\"fPlaceName\":\"储位名称\",\"fCtnL\":\"包装长\"," +
                "\"fCtnW\":\"包装宽\",\"fCtnH\":\"包装高\",\"fPackCuft\":\"包装体积\",\"fPackPcs\":\"包装件数\"," +
                "\"fScanorID\":\"扫描人代号\",\"fScanor\":\"扫描人\",\"fScanDate\":\"扫描日期\",\"fID\": \"条码ID\"}";

        title2_tuku = "{\"fBarCode\":\"条码代号\",\"fOrdNo\":\"订单号\",\"fSplitBatchNo\":\"拆单批次\",\"fPackNo\":\"包装编号\",\"fSPCode\":\"分包号\"," +
                "\"fPackMark\":\"分包说明\",\"fPlaceCode\":\"储位代号\",\"fPlaceName\":\"储位名称\",\"fCtnL\":\"包装长\"," +
                "\"fCtnW\":\"包装宽\",\"fCtnH\":\"包装高\",\"fPackCuft\":\"包装体积\",\"fPackPcs\":\"包装件数\"," +
                "\"fPackCtn\":\"包装箱数\",\"fScanorID\":\"扫描人代号\",\"fScanor\":\"扫描人\",\"fScanDate\":\"扫描日期\",\"fID\": \"条码ID\",\"fStkInLogNo\":\"入库单号\"}";

        title3 = "{\"fBarCode\":\"条码代号\",\"fBarErrorMessage\":\"解析状态\",\"fRemark\":\"日志说明\",\"fScanDate\":\"扫描日期\"}";

        title4 = "{\"fBarCode\":\"条码代号\",\"fOrdNo\":\"订单号\",\"fPackNo\":\"包装编号\",\"fSPCode\":\"分包号\"," +
                "\"fSPName\":\"分包说明\",\"fSPSizeDesc\":\"分包描述\"," +
                "\"fStkCode\":\"仓库代号\",\"fStkName\":\"仓库名称\"," +
                "\"fPlaceCode\":\"储位代号\",\"fPlaceName\":\"储位名称\",\"fCtnL\":\"包装长\"," +
                "\"fCtnW\":\"包装宽\",\"fCtnH\":\"包装高\",\"fOutCuft\":\"包装体积\",\"fPackCtn\":\"包装箱数\"," +
                "\"fScanorID\":\"扫描人代号\",\"fScanor\":\"扫描人\",\"fScanDate\":\"扫描日期\",\"fOrdSpID\": \"条码ID\"}";

        title5 = "{\"fOrdNo\":\"订单号\",\"fSplitBatchNo\":\"拆单批次\",\"fPackNo\":\"包装编号\",\"fGoodsID\":\"货品ID\"," +
                "\"fGoodsCode\":\"品号\",\"fGoodsName\":\"品名\",\"fSizeDesc\":\"规格描述\",\"fCstLotNo\":\"类货品定制批号\"," +
                "\"fBelongGoodsID\":\"所属产品ID\",\"fBelongGoodsCode\":\"所属产品代号\",\"fBelongCstLotNo\":\"所属产品定制批号\",\"fOrdSpID\":\"条码ID\"}";

        netStatusBeanDao = SoonforApplication.mDaoSession.getNetStatusBeanDao();
        inStatusBeanDao = SoonforApplication.mDaoSession.getOrdInStatusBeanDao();
        qitaoDao = SoonforApplication.mDaoSession.getOrdQitaoBeanDao();
        baritemDao = SoonforApplication.mDaoSession.getBarItemBeanDao();
        //scanlogDao = SoonforApplication.mDaoSession.getScanLogBeanDao();
        inScanLogList = new ArrayList<ScanLogBean>();
        heBaoBarItemBeanDao = SoonforApplication.mDaoSession.getHeBaoBarItemBeanDao();
        heBaoGoodsItemDao = SoonforApplication.mDaoSession.getHeBaoGoodsItemDao();
        printerBeanDao = SoonforApplication.mDaoSession.getPrinterBeanDao();
    }

    //请求头部
    public void getRukuTitle(Context context, int type) {
        if (type == 0) {//分拣
            Request.PackSortingByPackPage(context, this);
        } else if (type == 1 || type == 3) {//入库扫描和生产入库
            Request.StkInByPackPage(context, InStorePresenter.this);
        } else if (type == 2) {//入库退库
            Request.StkInRtnScanByPackPage(context, this);
        } else if (type == 4) {//合包扫描
            Request.CombineByPackPage(context, this);
        }
    }

    //请求条码
    public String StkInByPackBarItem(Context context, String type, String barcode, String stkcode, String receiptcode, String reasoncode, String placecode, String deptid, String ifundoscanmode, String stkinlogno) {
        if (type.equals("0")) {//分拣 PACKSORTINGBYPACKBARITEM
            return Request.PackSortingByPackBarItem(context, barcode, stkcode, receiptcode, reasoncode, placecode, deptid, ifundoscanmode);
        } else if (type.equals("1") || type.equals("3")) {//入库扫描和生产入库 BARITEM
            return Request.StkInByPackBarItem(context, barcode, stkcode, receiptcode, reasoncode, placecode, deptid, ifundoscanmode);
        } else if (type.equals("2")) {//入库退库 STKINRTNBYPACKBARITEM
            return Request.StkInRtnByPackBarItem(context, barcode, stkcode, stkinlogno);
        }
        return null;
    }

    //确定按钮
    public void StkInByPackSaveBar(Context context, int type, String stkcode, String receiptcode, String reasoncode, String placecode, String deptid, String stkinlogno, String ifundoscanmode, String spsid, JSONArray data) {
        if (type == 0) {//分拣
            Request.PackSortingByPackSaveBar(context, stkcode, receiptcode, reasoncode, placecode, deptid, ifundoscanmode, data, this);
        } else if (type == 1 || type == 3) {//入库扫描和生产入库
            Request.StkInByPackSaveBar(context, stkcode, receiptcode, reasoncode, placecode, deptid, ifundoscanmode, data, this);
        } else if (type == 2) {//入库退库
            Request.StkInRtnScanByPackSaveBar(context, stkcode, stkinlogno, deptid, data, this);
        }
    }

    //获取分包分类
    public void GetSPSIDList(Context context) {
        Request.GetSPSIDList(context, this);
    }

    //合包扫描 确定
    public void CombineByPack(Context context, String spsid, JSONArray data) {
        Request.CombineByPack(context, spsid, data, this);
    }

    //删除
    public void StkInByPackDelBar(Context context, int type, String barcode) {
        if (type == 0) {//分拣
            Request.PackSortingByPackDelBar(context, barcode, this);
        } else if (type == 1 || type == 3) {//入库扫描和生产入库
            Request.StkInByPackDelBar(context, barcode, this);
        } else if (type == 2) {//入库退库
            Request.StkInRtnScanByPackDelBar(context, barcode, this);
        }
    }

    //合包的删除
    public void CombineByPackDelBar(Context context, String barcode) {
        Request.CombineByPackDelBar(context, barcode, this);
    }

    //备货的删除
    //扫描删除
    public void StkPrepByPackDelBar(Context context, String stId, String barcode) {
        Request.StkPrepByPackDelBar(context, stId, barcode, this);
    }

    //清空数据 和 关闭窗体  时 调用接口清空缓存
    public void StkInByPackOnClose(Context context, String type, JSONArray data) {
        Request.StkInByPackOnClose(context, data, type, this);
    }

    //请求打印
    public void StkScanByPackPrintBar(Context context, String printername, String schmname, String barcode) {
        Request.StkScanByPackPrintBar(context, printername, schmname, barcode, new JSONArray(), this);
    }

    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();
        if (requestCode == Request.PACKSORTINGBYPACKPAGE || requestCode == Request.STKINBYPACKPAGE || requestCode == Request.STKINRTNSCANBYPACKPAGE || requestCode == Request.COMBINEBYPACKPAGE) {
            try {
                JSONObject head = new JSONObject(data);
                if (head.getBoolean("success")) {
                    JSONObject jo = new JSONObject(head.getString("data"));
                    //String title = jo.getString("title");
                    String item = jo.getString("item");

                    List<YeqianBean> beans = gson.fromJson(item, new TypeToken<List<YeqianBean>>() {
                    }.getType());
                    List<String> ltTitle = new ArrayList<>();
                    for (int i = 0; i < beans.size(); i++) {
                        ltTitle.add(beans.get(i).getfPageName());
                    }
                    view.setTitles(ltTitle);
                }
            } catch (Exception e) {
                e.printStackTrace();
                view.setErrorMsg(-1, "数据结构返回有误");
            }
        } else if (requestCode == Request.PACKSORTINGBYPACKSAVABAR) {// 分拣扫描确定结果   分拣的话清空已扫描的数据
            try {
                view.goQitaoError(data);
                view.delHeBaoData(true);
            } catch (Exception e) {
                e.printStackTrace();
                view.setErrorMsg(-1, "数据结构返回有误");
            }
        } else if (requestCode == Request.STKINBYPACKSAVEBAR || requestCode == Request.STKINRTNSCANBYPACKSAVEBAR) {// 入库扫描  \  退库  确定结果  不管结果, 直接返回
            try {
                JSONObject head = new JSONObject(data);
                if (head.getBoolean("success")) {
                    view.setSuccessResult("操作成功");
                    view.delHeBaoData(true);
                } else {
                    view.setErrorMsg(-1, "保存失败:\n" + head.getString("errormsg"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                view.setErrorMsg(-1, "数据结构返回有误");
            }
        } else if (requestCode == Request.PACKSORTINGBYPACKDELBAR || requestCode == Request.STKINBYPACKDELBAR || requestCode == Request.STKINRTNSCANBYPACKDELBAR || requestCode == Request.COMBINEBYPACKDELBAR) {
            try {
                DelDataBean bean = gson.fromJson(data, new TypeToken<DelDataBean>() {
                }.getType());
                if (bean.getSuccess()) {
                    view.showDelBack(bean.getData().getItem());
                } else {
                    view.setErrorMsg(-1, bean.getErrormsg() + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
                view.setErrorMsg(-1, "数据结构返回有误");
            }
        } else if (requestCode == Request.STKPREPBYPACKDELBAR) {//分包备货的删除回调
            try {
                DelDataBean bean = gson.fromJson(data, new TypeToken<DelDataBean>() {
                }.getType());
                if (bean.getSuccess()) {
                    view.showDelBack(bean.getData().getItem());
                } else {
                    view.setErrorMsg(-1, bean.getErrormsg() + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
                view.setErrorMsg(-1, "数据结构返回有误");
            }
        } else if (requestCode == Request.STKINBYPACKONCLOSE) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == Request.GETSPSIDLISTPDA) {
            try {
                JSONObject head = new JSONObject(data);
                //if (head.getBoolean("success")) {
                //JSONObject jo = new JSONObject(head.getString("data"));
                String title = head.getString("title");
                String item = head.getString("item");

                List<SpSidBean> beans = gson.fromJson(item, new TypeToken<List<SpSidBean>>() {
                }.getType());
                view.setSpSidDatas(beans);
                // }
            } catch (Exception e) {
                e.printStackTrace();
                view.setErrorMsg(-1, "数据结构返回有误");
            }
        } else if (requestCode == Request.COMBINEBYPACKSAVEBAR) {
            try {
                JSONObject head = new JSONObject(data);
                if (head.getBoolean("success")) {
                    JSONObject jo = new JSONObject(head.getString("data"));
                    view.setCombineBarCode(jo);
                } else {
                    view.setErrorMsg(-1, "提交失败: " + head.getString("errormsg"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == Request.STKSCANBYPACKPRINTBAR) {
            try {
                JSONObject head = new JSONObject(data);
                if (head.getBoolean("success")) {
                    JSONObject jo = new JSONObject(head.getString("data"));
                    view.setSuccessResult("打印成功");
                } else {
                    view.setErrorMsg(-1, "打印失败: " + head.getString("errormsg"));
                    /*
                    * 不需要这些代码
                    //打印失败需要插入一条记录到数据库
                    PrinterBean bean = new PrinterBean();
                    bean.setFBarCode("");
                    bean.setFOrdNo("");
                    bean.setFSPCode("");
                    bean.setFSPName("");
                    bean.setHeBaoTime(TimeUtils.getFullDate());
                    UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
                    if (userinfo == null) return;
                    bean.setHeBaoUser(userinfo.getUsername());
                    printerBeanDao.insert(bean);*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String data, String msg) {
        if (requestCode == Request.PACKSORTINGBYPACKBARITEM || requestCode == Request.BARITEM || requestCode == Request.STKINRTNBYPACKBARITEM) {
            try {
                Gson gson = new Gson();
                JSONObject o = new JSONObject(data);
                JSONObject error = new JSONObject(o.getString("error"));

                //构建sqlite的数据体
                List<ScanLogBean> scanLogBeans = gson.fromJson(error.toString(), new TypeToken<List<ScanLogBean>>() {
                }.getType());

                for (int i = 0; i < scanLogBeans.size(); i++) {
                    ScanLogBean qq = scanLogBeans.get(i);
                    int existIndex = -1;
                    for (int j = 0; j < inScanLogList.size(); j++) {
                        if (inScanLogList.get(j).getFBarCode().equals(qq.getFBarCode())) {
                            existIndex = j;
                            break;
                        }
                    }
                    if (existIndex >= 0) {
                        inScanLogList.remove(existIndex);
                        scanLogBeans.set(existIndex, qq);
                    } else inScanLogList.add(qq);
//                    if (scanlogDao.load(qq.getFBarCode()) == null) {
//                        scanlogDao.insert(qq);
//                    } else {
//                        scanlogDao.update(qq);
//                    }
                }

                makeData3();

                //扫描之后需要锁定撤销扫描功能
                view.setCbDelStatu(true);

            } catch (Exception e) {
                e.printStackTrace();
                view.setErrorMsg(-1, msg);
            }
        } else {
            view.setErrorMsg(statusCode, msg);
        }
    }

    //检查是否齐套
    private boolean checkIsQiTao(OrdInStatusBean oo) {
        if (isQiTao(oo, type)) {
            oo.setFUnScanPackQty(hasNotScanNum(oo) + "");
            qitaoSetData(oo);
        } else {
            oo.setFUnScanPackQty(hasNotScanNum(oo) + "");
            inStatusBeanDao.update(oo);
        }
        return false;
    }

    private void qitaoSetData(OrdInStatusBean oo) {
        OrdQitaoBean bean = new OrdQitaoBean();
        bean.setFOrdNo(oo.getFOrdNo());
        bean.setFProdNo(oo.getFProdNo());
        bean.setFScanedPackQty(oo.getFScanedPackQty());
        bean.setFSortingPackQty(oo.getFSortingPackQty());
        bean.setFStkInPackQty(oo.getFStkInPackQty());
        bean.setFThisScanPackQty(oo.getFThisScanPackQty());
        bean.setFTotalPackQty(oo.getFTotalPackQty());
        bean.setFUnScanPackQty(oo.getFUnScanPackQty());
        inStatusBeanDao.delete(oo);
        if (qitaoDao.load(bean.getFOrdNo()) == null) {
            qitaoDao.insert(bean);
        } else {
            qitaoDao.update(bean);
        }
        List<OrdQitaoBean> beans = new ArrayList<>();
        beans.add(bean);
        makeData1(beans, type);
        view.resetListView();
    }

    private boolean isQiTao(OrdInStatusBean oo, int type) {
        //总包数 =已入库包数 +已扫描包数 +本次扫描包数 +未扫描包数    ( 已入库数和已扫描包数  属性相同,  不能同时加两个 )
        int totle = Integer.parseInt(CommUtil.formatInteger(oo.getFTotalPackQty()));
        //int yisao = Integer.parseInt(CommUtil.formatInteger(oo.getFScanedPackQty()));
        int yiruku = 0;
        if (type == 0) {
            yiruku = Integer.parseInt(CommUtil.formatInteger(oo.getFSortingPackQty()));
        } else if (type == 1 || type == 2 || type == 3) {
            yiruku = Integer.parseInt(CommUtil.formatInteger(oo.getFStkInPackQty()));
        }
        int bencisao = Integer.parseInt(CommUtil.formatInteger(oo.getFThisScanPackQty()));
        //int weisao = Integer.parseInt(CommUtil.formatInteger(oo.getFUnScanPackQty()));

        if (totle == (yiruku + bencisao)) {
            return true;
        }
        return false;
    }

    //未扫描数
    private int hasNotScanNum(OrdInStatusBean oo) {
        boolean isCheXiao = view.getCheckStu();

        //未扫描＝总包数-已经入库-已经扫描-本次扫描
        int totle = Integer.parseInt(CommUtil.formatInteger(oo.getFTotalPackQty()));
        int yiruku = 0;
        if (type == 0) {
            yiruku = Integer.parseInt(CommUtil.formatInteger(oo.getFSortingPackQty()));
        } else if (type == 1 || type == 2 || type == 3) {
            yiruku = Integer.parseInt(CommUtil.formatInteger(oo.getFStkInPackQty()));
        }
        int yisao = Integer.parseInt(CommUtil.formatInteger(oo.getFScanedPackQty()));
        int bencisao = Integer.parseInt(CommUtil.formatInteger(oo.getFThisScanPackQty()));

        if (!isCheXiao) {
            int shengyushu = (totle - yiruku - bencisao);
            return shengyushu < 0 ? 0 : shengyushu;
        } else {
            int shengyushu = (totle - yisao + bencisao);
            return shengyushu < 0 ? 0 : shengyushu;
        }

    }

    public void makeData0(List<OrdInStatusBean> inStatusBeans, int type) {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title0));
            List<String[]> datas = new ArrayList<>();
            for (OrdInStatusBean qq : inStatusBeans) {
                String[] ss = new String[titleMap.get(1).length];
                ss[0] = qq.getFOrdNo();
                ss[1] = qq.getFProdNo();
                ss[2] = CommUtil.formatInteger(qq.getFTotalPackQty());
                if (type == 0) {
                    ss[3] = CommUtil.formatInteger(qq.getFSortingPackQty());
                } else if (type == 1 || type == 2 || type == 3) {
                    ss[3] = CommUtil.formatInteger(qq.getFStkInPackQty());
                }
                ss[4] = CommUtil.formatInteger(qq.getFScanedPackQty());
                ss[5] = CommUtil.formatInteger(qq.getFThisScanPackQty());
                ss[6] = CommUtil.formatInteger(qq.getFUnScanPackQty());
                datas.add(ss);
            }
            view.setDatas(0, titleMap.get(1), datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeData1(List<OrdQitaoBean> qitaoBeans, int type) {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title1));
            List<String[]> datas = new ArrayList<>();
            for (OrdQitaoBean qq : qitaoBeans) {
                String[] ss = new String[titleMap.get(1).length];
                ss[0] = qq.getFOrdNo();
                ss[1] = qq.getFProdNo();
                ss[2] = CommUtil.formatInteger(qq.getFTotalPackQty());
                if (type == 0) {
                    ss[3] = CommUtil.formatInteger(qq.getFSortingPackQty());
                } else if (type == 1 || type == 2 || type == 3) {
                    ss[3] = CommUtil.formatInteger(qq.getFStkInPackQty());
                }
                ss[4] = CommUtil.formatInteger(qq.getFScanedPackQty());
                ss[5] = CommUtil.formatInteger(qq.getFThisScanPackQty());
                ss[6] = CommUtil.formatInteger(qq.getFUnScanPackQty());
                datas.add(ss);
            }
            view.setDatas(1, titleMap.get(1), datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeData2(List<BarItemBean> barItemBeans) {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = null;
            List<String[]> datas = new ArrayList<>();
            if (type == 0 || type == 1) {
                titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title2));
                for (BarItemBean bb : barItemBeans) {
                    String[] ss = new String[titleMap.get(1).length];
                    ss[0] = bb.getFBarCode();
                    ss[1] = bb.getFOrdNo();
                    ss[2] = bb.getFSplitBatchNo();
                    ss[3] = bb.getFSPCode();
                    ss[4] = bb.getFPackMark();
                    ss[5] = bb.getFPlaceCode();
                    ss[6] = bb.getFPlaceName();
                    ss[7] = CommUtil.formatInteger(bb.getFCtnL());
                    ss[8] = CommUtil.formatInteger(bb.getFCtnW());
                    ss[9] = CommUtil.formatInteger(bb.getFCtnH());
                    ss[10] = bb.getFPackCuft();
                    ss[11] = bb.getFPackPcs();
                    ss[12] = bb.getFScanorID();
                    ss[13] = bb.getFScanor();
                    ss[14] = bb.getFScanDate();
                    ss[15] = bb.getFID();
                    datas.add(ss);
                }
            } else if (type == 2 || type == 3) {
                titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title2_tuku));
                for (BarItemBean bb : barItemBeans) {
                    String[] ss = new String[titleMap.get(1).length];
                    ss[0] = bb.getFBarCode();
                    ss[1] = bb.getFOrdNo();
                    ss[2] = bb.getFSplitBatchNo();
                    ss[3] = bb.getFPackNo();
                    ss[4] = bb.getFSPCode();
                    ss[5] = bb.getFPackMark();
                    ss[6] = bb.getFPlaceCode();
                    ss[7] = bb.getFPlaceName();
                    ss[8] = CommUtil.formatInteger(bb.getFCtnL());
                    ss[9] = CommUtil.formatInteger(bb.getFCtnW());
                    ss[10] = CommUtil.formatInteger(bb.getFCtnH());
                    ss[11] = bb.getFPackCuft();
                    ss[12] = bb.getFPackPcs();
                    ss[13] = bb.getFPackCtn();
                    ss[14] = bb.getFScanorID();
                    ss[15] = bb.getFScanor();
                    ss[16] = bb.getFScanDate();
                    ss[17] = bb.getFID();
                    ss[18] = bb.getFStkInLogNo();
                    datas.add(ss);
                }
            }

            view.setDatas(2, titleMap.get(1), datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeData3() {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title3));
            List<String[]> datas = new ArrayList<>();
            for (ScanLogBean qq : inScanLogList) {
                String[] ss = new String[titleMap.get(1).length];
                ss[0] = qq.getFBarCode();
                ss[1] = qq.getFErrorMsg();
                ss[2] = qq.getFRemark();
                ss[3] = qq.getFScanDate();
                datas.add(ss);
            }
            view.setDatas(3, titleMap.get(1), datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeData4(List<HeBaoBarItemBean> heBaoBarItemBeans) {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title4));
            List<String[]> datas = new ArrayList<>();
            for (HeBaoBarItemBean bb : heBaoBarItemBeans) {
                String[] ss = new String[titleMap.get(1).length];
                ss[0] = bb.getFBarCode();
                ss[1] = bb.getFOrdNo();
                ss[2] = bb.getFPackNo();
                ss[3] = bb.getFSPCode();
                ss[4] = bb.getFSPName();
                ss[5] = bb.getFSPSizeDesc();
                ss[6] = bb.getFStkCode();
                ss[7] = bb.getFStkName();
                ss[8] = bb.getFPlaceCode();
                ss[9] = bb.getFPlaceName();
                ss[10] = CommUtil.formatInteger(bb.getFCtnL());
                ss[11] = CommUtil.formatInteger(bb.getFCtnW());
                ss[12] = CommUtil.formatInteger(bb.getFCtnH());
                ss[13] = bb.getFOutCuft();
                ss[14] = bb.getFPackCtn();
                ss[15] = bb.getFScanorID();
                ss[16] = bb.getFScanor();
                ss[17] = bb.getFScanDate();
                ss[18] = bb.getFOrdSpID();
                datas.add(ss);

            }
            view.setDatas(0, titleMap.get(1), datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeData5(List<HeBaoGoodsItem> heBaoGoodsItems) {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title5));
            List<String[]> datas = new ArrayList<>();
            for (HeBaoGoodsItem bb : heBaoGoodsItems) {
                String[] ss = new String[titleMap.get(1).length];
                ss[0] = bb.getFOrdNo();
                ss[1] = bb.getFSplitBatchNo();
                ss[2] = bb.getFPackNo();
                ss[3] = bb.getFGoodsID();
                ss[4] = bb.getFGoodsCode();
                ss[5] = bb.getFGoodsName();
                ss[6] = bb.getFSizeDesc();
                ss[7] = bb.getFCstLotNo();
                ss[8] = bb.getFBelongGoodsID();
                ss[9] = bb.getFBelongGoodsCode();
                ss[10] = bb.getFBelongCstLotNo();
                ss[11] = bb.getFOrdSpID();
                datas.add(ss);

            }
            view.setDatas(1, titleMap.get(1), datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeData6() {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title3));
            List<String[]> datas = new ArrayList<>();
            for (ScanLogBean qq : inScanLogList) {
                String[] ss = new String[titleMap.get(1).length];
                ss[0] = qq.getFBarCode();
                ss[1] = qq.getFErrorMsg();
                ss[2] = qq.getFRemark();
                ss[3] = qq.getFScanDate();
                datas.add(ss);

            }
            view.setDatas(2, titleMap.get(1), datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reflashNet() {
        NetStatusBean netStatusBean = netStatusBeanDao.load("ruku");
        if (netStatusBean != null) {
            //分别从未齐套表和 齐套表中取出对应的数相加
            List<OrdInStatusBean> inbeans = inStatusBeanDao.loadAll();
            List<OrdQitaoBean> qibeans = qitaoDao.loadAll();
            int yingsao = 0;
            int yisao = 0;
            int benci = (int) baritemDao.count();
            int weisao = 0;

            for (OrdInStatusBean b : inbeans) {
                yingsao += CommUtil.formatStrToInteger(b.getFTotalPackQty());
                yisao += CommUtil.formatStrToInteger(b.getFScanedPackQty());
//                benci += CommUtil.formatStrToInteger(b.getFThisScanPackQty());
                weisao += CommUtil.formatStrToInteger(b.getFUnScanPackQty());
            }

            for (OrdQitaoBean b : qibeans) {
                yingsao += CommUtil.formatStrToInteger(b.getFTotalPackQty());
                yisao += CommUtil.formatStrToInteger(b.getFScanedPackQty());
//                benci += CommUtil.formatStrToInteger(b.getFThisScanPackQty());
               weisao += CommUtil.formatStrToInteger(b.getFUnScanPackQty());
            }
            if(InStoreActivity.cbDel.isChecked()){//撤销扫描
                weisao = yingsao - yisao + benci;
            }else {}
            netStatusBean.setYingsao(yingsao);
            netStatusBean.setYisao(yisao);
            netStatusBean.setBenci(benci);
            netStatusBean.setWeisao(weisao<0 ? 0 : weisao);

            netStatusBeanDao.update(netStatusBean);
        }
    }

    /**
     * 非合包扫描相关表 更新数据 dingyg修改
     */
    public void loadDb(int tabindex, String _id) {
        switch (tabindex) {
            case 0://订单入库状况表
                List<OrdInStatusBean> ordInStatusBeans = inStatusBeanDao.loadAll();
                makeData0(ordInStatusBeans, Integer.parseInt(_id));
                break;
            case 1://齐套订单
                List<OrdQitaoBean> ordQitaoBeans = qitaoDao.loadAll();
                makeData1(ordQitaoBeans, Integer.parseInt(_id));
                break;
            case 2://条码明细
                List<BarItemBean> barItemBeans = baritemDao.loadAll();
                makeData2(barItemBeans);
                break;
            case 3://扫描日志表
//                List<ScanLogBean> scanLogBeans = scanlogDao.loadAll();
//                makeData3(scanLogBeans);
                makeData3();
                break;
        }
    }

    /**
     * 合包扫描相关表 更新数据 dingyg修改
     */
    public void loadHeBaoDb(int tabindex) {
        switch (tabindex) {
            case 0://合包条码明细
                List<HeBaoBarItemBean> heBaoBarItemBeans = heBaoBarItemBeanDao.loadAll();
                makeData4(heBaoBarItemBeans);
                break;
            case 1://合包货品明细表
                List<HeBaoGoodsItem> goodsItems = heBaoGoodsItemDao.loadAll();
                makeData5(goodsItems);
                break;
            case 2://扫描日志表
//                List<ScanLogBean> scanLogBeans = scanlogDao.loadAll();
//                makeData6(scanLogBeans);
                makeData6();
                break;
        }
    }

    /**
     * 循环解析非合包条码
     *
     * @param mContext
     * @param int_id           //程序id 比如 0分包分拣
     * @param fstkCode//仓库代号
     * @param //bill//单据
     * @param //reason//原因
     * @param //store//储位
     * @param //deptcode//部门代号
     * @param //rkbill//入库单据
     */
    public void requestBarCodeInfo(Activity mContext, String int_id, int fifUserPlace, String fstkCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                while (InStoreActivity.isNeedAsyn) {
                    if (checkConditions(int_id, fifUserPlace)) {
                        if (inScanLogList != null && inScanLogList.size() > 0) {
                            for (int i = 0; i < inScanLogList.size(); i++) {
                                try {
                                    if (i < inScanLogList.size()) {
                                        ScanLogBean barCodeBean = inScanLogList.get(i);
                                        if (barCodeBean != null && barCodeBean.getFErrorMsg().equals("")) {//只解析未解析的 !barCodeBean.isStartRequest()
                                            //inScanLogList.get(i).setFisStratRequest(true);
                                            String bcInfo = StkInByPackBarItem(mContext, int_id, barCodeBean.getFBarCode(), fstkCode,
                                                    InStoreActivity.conditionBean.getFbillCode(), InStoreActivity.conditionBean.getFreasonCode(),
                                                    InStoreActivity.conditionBean.getFbinlocationCode(), InStoreActivity.conditionBean.getFdeptCode(),
                                                    InStoreActivity.cbDel.isChecked() ? "1" : "0", InStoreActivity.conditionBean.getFrukubillCode());
                                            int finalI = i;
                                            mContext.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (bcInfo != null) {
                                                        asynBarCode(gson, bcInfo, finalI);
                                                    } else {
                                                        inScanLogList.get(finalI).setFErrorMsg("解析失败");
                                                        inScanLogList.get(finalI).setFRemark("请求出错或超时");
                                                        inScanLogList.get(finalI).setFScanDate(DateUtils.getTodayDateTime("yyyy-MM-dd hh:mm:ss"));
                                                        view.FaileSound();
                                                        //刷新一下列表
                                                        view.reflashDataList();
                                                    }
                                                }
                                            });
                                        }
//                            if (barCodeBean.getfValidState().equals("2")) {//再解析失败的
//                                questCode(qurl, barCodeBean, i);
//                            }
                                    }
                                } catch (Exception e) {
                                    NLogger.e("asynBarCode：" + e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 循环解析合包条码
     *
     * @param mContext
     * @param stkcode//仓库代号
     */
    public void requestHbBarCodeInfo(Activity mContext, String stkcode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String ordno = "", chuwei = "";
                while (InStoreActivity.isNeedAsyn) {
                    if (inScanLogList != null && inScanLogList.size() > 0) {
                        for (int i = 0; i < inScanLogList.size(); i++) {
                            try {
                                if (i < inScanLogList.size()) {
                                    ScanLogBean barCodeBean = inScanLogList.get(i);
                                    if (barCodeBean != null && barCodeBean.getFErrorMsg().equals("")) {//只解析未解析的 && !barCodeBean.isStartRequest()
                                        //inScanLogList.get(i).setFisStratRequest(true);
                                        if (ordno.equals("") || chuwei.equals("")) {
                                            List<HeBaoBarItemBean> tmp = heBaoBarItemBeanDao.loadAll();
                                            if (tmp != null && tmp.size() > 0) {
                                                ordno = tmp.get(0).getFOrdNo();
                                                chuwei = tmp.get(0).getFPlaceCode();
                                            }
                                        }
                                        String bcInfo = Request.CombineByPackBarItemNew(mContext, barCodeBean.getFBarCode(), ordno, stkcode, chuwei);
                                        int finalI = i;
                                        mContext.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (bcInfo != null) {
                                                    asynHbBarCode(gson, bcInfo, finalI);
                                                } else {
                                                    inScanLogList.get(finalI).setFErrorMsg("待解析");
                                                    inScanLogList.get(finalI).setFRemark("请求出错或超时");
                                                    inScanLogList.get(finalI).setFScanDate(DateUtils.getTodayDateTime("yyyy-MM-dd hh:mm:ss"));
                                                    view.FaileSound();
                                                    //刷新一下列表
                                                    view.reflashDataList();
                                                }
                                            }
                                        });
                                    }
//                            if (barCodeBean.getfValidState().equals("2")) {//再解析失败的
//                                questCode(qurl, barCodeBean, i);
//                            }
                                }
                            } catch (Exception e) {
                                NLogger.e("asynBarCode：" + e.getMessage());
                            }
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 解析非合包条码
     *
     * @param gson
     * @param data
     */
    private void asynBarCode(Gson gson, String data, int position) {
        int asynStatus = 0;//解析状态 未解析失败 1重复解析 2 解析完成
        try {
            JSONObject head = new JSONObject(data);
            boolean isSuccess = false;
            try {
                isSuccess = head.getBoolean("success");
            } catch (Exception e) {
            }
            if (isSuccess) {
                JSONObject jo = new JSONObject(head.getString("data"));
                String baritem = jo.getString("baritem");
                String instatus = jo.getString("instatus");
                //构建sqlite的数据体
                List<BarItemBean> barItemBeans = gson.fromJson(baritem, new TypeToken<List<BarItemBean>>() {
                }.getType());
                List<OrdInStatusBean> inStatusBeans = gson.fromJson(instatus, new TypeToken<List<OrdInStatusBean>>() {
                }.getType());

                //条码明细
                for (int i = 0; i < barItemBeans.size(); i++) {
                    BarItemBean bb = barItemBeans.get(i);
                    //已经扫码成功了，就更新日志表的记录
                    boolean isExistSuccess = false;
                    for (int j = 0; j < inScanLogList.size(); j++) {
                        if (inScanLogList.get(j).getFBarCode().equals(bb.getFBarCode()) && inScanLogList.get(j).getFErrorMsg().equals("识别成功")) {
                            isExistSuccess = true;
                            break;
                        }
                    }
                    for (int j = 0; j < inScanLogList.size(); j++) {
                        if (inScanLogList.get(j).getFBarCode().equals(bb.getFBarCode()) && inScanLogList.get(j).getFErrorMsg().equals("")) {
                            if (isExistSuccess) {
                                asynStatus = 1;
                                inScanLogList.get(j).setFErrorMsg("重复扫描");
                                inScanLogList.get(j).setFRemark("重复扫描");
                                inScanLogList.get(j).setFScanDate(bb.getFScanDate());
                                view.RepatSound();
                            } else {
                                asynStatus = 2;
                                inScanLogList.get(j).setFErrorMsg("识别成功");
                                inScanLogList.get(j).setFRemark("扫码成功");
                                inScanLogList.get(j).setFScanDate(bb.getFScanDate());
                                //开始进一步的解析并更新UI
                                if (baritemDao.load(bb.getFBarCode()) == null) {
                                    baritemDao.insert(bb);

                                    //如果是分包退库的时候 就自动吧入库单号和储位填上去;
                                    if (type == 2) {
                                        view.setRukuDanju(bb.getFStkInLogNo() == null ? "" : bb.getFStkInLogNo());
                                        view.setChuWei(bb.getFPlaceCode() == null ? "" : bb.getFPlaceCode());
                                    }

                                    //入库状况 和 齐套
                                    for (int k = 0; k < inStatusBeans.size(); k++) {
                                        OrdInStatusBean oo = inStatusBeans.get(k);

                                        //更新一下已扫描数
                                        if (type == 1 || type == 3 || type == 2) {//分包入库\ 分包生产入库 入库退库 特殊处理
                                            oo.setFScanedPackQty(oo.getFStkInPackQty());
                                        } else {
                                            oo.setFScanedPackQty((CommUtil.formatStrToInteger(oo.getFSortingPackQty()) + CommUtil.formatStrToInteger(oo.getFStkInPackQty())) + "");
                                        }

                                        if (inStatusBeanDao.load(oo.getFOrdNo()) == null) {
                                            oo.setFThisScanPackQty("1");
                                            inStatusBeanDao.insert(oo);

                                            //插完后检查是否是齐套
                                            checkIsQiTao(oo);

                                        } else {
                                            OrdInStatusBean tmp = inStatusBeanDao.load(oo.getFOrdNo());
                                            int num = Integer.parseInt(CommUtil.formatInteger(tmp.getFThisScanPackQty()));
                                            num++;
                                            oo.setFThisScanPackQty(num + "");

                                            //更新完后检查是否已经齐套
                                            checkIsQiTao(oo);
                                        }
                                        inStatusBeans.set(k, oo);
                                    }
                                    //构建UI
                                    //先生成条码
                                    makeData2(barItemBeans);
                                    //生成订单入库状况
                                    makeData0(inStatusBeans, type);
                                    if (inStatusBeanDao.loadAll().size() > 0 || qitaoDao.loadAll().size() > 0) {
                                        //扫描之后需要锁定撤销扫描功能  *****只要订单分拣状态页签或者，齐套订单页签没有数据就可以勾选撤销扫描
                                        view.setCbDelStatu(false);
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            } else {
                //{"success":false,"msgcode":"0","errormsg":"","data":{"error":[{"fBarCode":"01807000054","fErrorMsg":"解析失败","fRemark":"当前条码所对应的订单不属于当前物流批次，不能出库扫描!","fScanDate":"2018\/8\/13 15:20:58"}]}}
                JSONArray jr;
                try {
                    JSONObject jo = new JSONObject(head.getString("data"));
                    jr = new JSONArray(jo.getString("error"));
                } catch (Exception e) {
                    jr = null;
                }
                if (inScanLogList.get(position).getFErrorMsg().equals("")) {
                    if (jr != null && jr.length() > 0) {
                        //已经扫码成功了
                        ScanLogBean sb = gson.fromJson(jr.get(0).toString(), new TypeToken<ScanLogBean>() {
                        }.getType());
                        inScanLogList.get(position).setFBarCode(sb.getFBarCode());
                        inScanLogList.get(position).setFErrorMsg(sb.getFErrorMsg());
                        inScanLogList.get(position).setFRemark(sb.getFRemark().equals("") ? "识别失败" : sb.getFRemark());
                        inScanLogList.get(position).setFScanDate(sb.getFScanDate());
                        view.FaileSound();
                        asynStatus = 2;
                    }
                }
            }
            //刷新一下列表
            view.reflashDataList();
        } catch (Exception e) {
            e.printStackTrace();
            //view.setErrorMsg(-1, "数据结构返回有误");
            // NLogger.e("解析条码到显示时间", (System.currentTimeMillis() - SoonforApplication.thisTiem) + "");
        }
        if (asynStatus == 1) {
            if (inScanLogList.get(position).getFErrorMsg().equals("")) {
                inScanLogList.get(position).setFErrorMsg("解析失败");
                inScanLogList.get(position).setFRemark("解析出错");
                inScanLogList.get(position).setFScanDate(DateUtils.getTodayDateTime("yyyy-MM-dd hh:mm:ss"));
                view.FaileSound();
                view.reflashDataList();
            }
        }
    }

    /**
     * 解析合包条码
     */
    private void asynHbBarCode(Gson gson, String data, int position) {
        int asynStatus = 0;//解析状态 未解析失败 1重复解析 2 解析完成
        try {
            JSONObject head = new JSONObject(data);
            if (head.getBoolean("success")) {
                JSONObject jo = new JSONObject(head.getString("data"));

                String baritem = jo.getString("baritem");
                String goodsitem = jo.getString("goodsitem");

                //构建sqlite的数据体
                List<HeBaoBarItemBean> heBaoBarItemBeans = gson.fromJson(baritem, new TypeToken<List<HeBaoBarItemBean>>() {
                }.getType());
                List<HeBaoGoodsItem> goodsItemBeans = gson.fromJson(goodsitem, new TypeToken<List<HeBaoGoodsItem>>() {
                }.getType());

                //条码明细
                for (int i = 0; i < heBaoBarItemBeans.size(); i++) {
                    HeBaoBarItemBean bb = heBaoBarItemBeans.get(i);

                    //已经扫码成功了，就更新日志表的记录
                    boolean isExistSuccess = false;
                    for (int j = 0; j < inScanLogList.size(); j++) {
                        if (inScanLogList.get(j).getFBarCode().equals(bb.getFBarCode()) && inScanLogList.get(j).getFErrorMsg().equals("识别成功")) {
                            isExistSuccess = true;
                            break;
                        }
                    }
                    for (int j = 0; j < inScanLogList.size(); j++) {
                        if (inScanLogList.get(j).getFBarCode().equals(bb.getFBarCode()) && inScanLogList.get(j).getFErrorMsg().equals("")) {
                            if (isExistSuccess) {
                                asynStatus = 1;
                                inScanLogList.get(position).setFErrorMsg("重复扫描");
                                inScanLogList.get(position).setFRemark("重复扫描");
                                inScanLogList.get(position).setFScanDate(bb.getFScanDate());
                                view.RepatSound();
                            } else {
                                asynStatus = 2;
                                inScanLogList.get(position).setFErrorMsg("识别成功");
                                inScanLogList.get(position).setFRemark("扫码成功");
                                inScanLogList.get(position).setFScanDate(bb.getFScanDate());
                                /**
                                 * 合包 它是在分拣之后 入库之前，发现有些包 包的东西类似 就把两个或多个包 包成一个。一个包一个条码，那么就是多个包的条码作废 生成一个新的条码了。
                                 * 两个规则 不同订单的不能合，必需扫了两个条码以上 才能合包确认。
                                 * 这个接口，这个条码 在确认之前填就行了。扫描可以不填
                                 * 跟退库一样，扫第一个码，带出订单号，扫第二个 就判断这个条码是不是属于此订单号
                                 * 合包确认成功后 提是“合包成功，新条码为 xxx （新条码 接口返回）”
                                 * 1706066536
                                 * 1706066537
                                 * **/
                                //开始进一步的解析并更新UI
                                if (heBaoBarItemBeanDao.load(bb.getFBarCode()) == null) {
                                    List<HeBaoBarItemBean> tmp = heBaoBarItemBeanDao.loadAll();
                                    if (tmp != null && tmp.size() > 0) {//第二条记录开始就要进行判断
                                        boolean isSaveOrder = false;
                                        //看看是不是同一个生产单
                                        for (HeBaoBarItemBean hbi : heBaoBarItemBeanDao.loadAll()) {
                                            if (hbi.getFOrdNo().equals(bb.getFOrdNo())) {
                                                isSaveOrder = true;
                                                break;
                                            }
                                        }
                                        if (isSaveOrder) {
                                            heBaoBarItemBeanDao.insert(bb);
                                            //插明细
                                            for (HeBaoGoodsItem hgb : goodsItemBeans) {
                                                heBaoGoodsItemDao.insert(hgb);
                                            }
                                        }
//                            else {
//                                view.setErrorMsg(-1, "条码: " + bb.getFBarCode() + " 不属于订单号: " + bb.getFOrdNo());
//                                view.RepatSound();
//                            }
                                    } else {//第一条记录直接插
                                        heBaoBarItemBeanDao.insert(bb);
                                        //插明细
                                        for (HeBaoGoodsItem hgb : goodsItemBeans) {
                                            heBaoGoodsItemDao.insert(hgb);
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if (asynStatus != 1) {
                    makeData4(heBaoBarItemBeans);
                    makeData5(goodsItemBeans);
                }
            } else {
                //{"success":false,"msgcode":"0","errormsg":"","data":{"error":[{"fBarCode":"01807000054","fErrorMsg":"解析失败","fRemark":"当前条码所对应的订单不属于当前物流批次，不能出库扫描!","fScanDate":"2018\/8\/13 15:20:58"}]}}
                //已经扫码成功了，就更新日志表的记录
                if (inScanLogList.get(position).getFErrorMsg().equals("")) {
                    JSONObject jo = new JSONObject(head.getString("data"));
                    JSONArray jr = new JSONArray(jo.getString("error"));
                    ScanLogBean sb = gson.fromJson(jr.get(0).toString(), new TypeToken<ScanLogBean>() {
                    }.getType());
                    inScanLogList.get(position).setFBarCode(sb.getFBarCode());
                    inScanLogList.get(position).setFErrorMsg(sb.getFErrorMsg());
                    inScanLogList.get(position).setFRemark(sb.getFRemark().equals("") ? "识别失败" : sb.getFRemark());
                    inScanLogList.get(position).setFScanDate(sb.getFScanDate());
                    view.FaileSound();
                    asynStatus = 2;
                }
            }
            //刷新一下列表
            view.reflashDataList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (asynStatus == 0) {
            if (inScanLogList.get(position).getFErrorMsg().equals("")) {
                inScanLogList.get(position).setFErrorMsg("解析失败");
                inScanLogList.get(position).setFRemark("解析出错");
                inScanLogList.get(position).setFScanDate(DateUtils.getTodayDateTime("yyyy-MM-dd hh:mm:ss"));
                view.FaileSound();
                view.reflashDataList();
            }
        }
    }

    /**
     * 确认前的判断 日志中是否还有未解析完的条码
     * cxCode 为程序代号
     */
    public boolean isHaveNoAsyn() {
        // List<ScanLogBean> scanLogBeanList = scanlogDao.loadAll();
        boolean isExistNoAsyn = false;//是否有未解析的存在
        if (inScanLogList != null && inScanLogList.size() > 0) {
            for (int i = 0; i < inScanLogList.size(); i++) {
                if (inScanLogList.get(i).getFErrorMsg().equals("")) {
                    isExistNoAsyn = true;
                    break;
                }
            }
        }
        return isExistNoAsyn;
    }

    /**
     * 检测必选条件是否缺失
     */
    private boolean checkConditions(String sId, int fifuserplace) {
        boolean requiredConditionIsChoose = false;//必选条件是否已经全部选择了
        if (sId.equals("2")) {//退库
            requiredConditionIsChoose = true;
        } else {
            if (InStoreActivity.conditionBean != null) {
                if (!InStoreActivity.conditionBean.getFbillCode().equals("")
                        && !InStoreActivity.conditionBean.getFreasonCode().equals("")
                        && !InStoreActivity.conditionBean.getFdeptCode().equals("")) {
                    if (fifuserplace == 1) {
                        if (!InStoreActivity.conditionBean.getFbinlocationCode().equals("")) {
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
}
