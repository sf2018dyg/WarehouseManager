package com.soonfor.warehousemanager.module.outstore;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.bases.NetStatusBean;
import com.soonfor.warehousemanager.dao.NetStatusBeanDao;
import com.soonfor.warehousemanager.dao.OutOrdInStatusBeanDao;
import com.soonfor.warehousemanager.dao.OutOrdQitaoBeanDao;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.module.instore.InStoreActivity;
import com.soonfor.warehousemanager.module.instore.beans.YeqianBean;
import com.soonfor.warehousemanager.module.outstore.beans.LogistBatchBean;
import com.soonfor.warehousemanager.module.outstore.beans.Out2BarItemBean;
import com.soonfor.warehousemanager.module.outstore.beans.OutOrdInStatusBean;
import com.soonfor.warehousemanager.module.outstore.beans.OutOrdQitaoBean;
import com.soonfor.warehousemanager.module.outstore.beans.OutScanLogBean;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.DateUtils;
import com.soonfor.warehousemanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jesse.nativelogger.NLogger;

import static com.soonfor.warehousemanager.SoonforApplication.bar2Map;
import static com.soonfor.warehousemanager.SoonforApplication.outScanLogList;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/26 0026 15:56
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class OutStorePresenter extends BasePresenter<IOutStoreView> implements AsyncUtils.AsyncCallback {

    private IOutStoreView view;
    private String flowType;
    String title0;
    String title1;
    String title2;
    String title3;

    public NetStatusBeanDao netStatusBeanDao;
    public OutOrdInStatusBeanDao inStatusBeanDao;
    public OutOrdQitaoBeanDao qitaoDao;

    public OutStorePresenter(IOutStoreView view, String type) {
        this.view = view;
        this.flowType = type;

        title0 = "{\"fOrdNo\":\"订单号\",\"fProdNo\":\"生产单号\",\"fTotalPackQty\":\"总包数\",\"fStkOutPackQty\":\"已出货包数\"," +
                "\"fScanedPackQty\":\"已扫描包数\",\"fThisScanPackQty\":\"本次扫描包数\",\"fUnScanPackQty\":\"未扫描包数\"}";

        title1 = "{\"fOrdNo\":\"订单号\",\"fProdNo\":\"生产单号\",\"fTotalPackQty\":\"总包数\",\"fStkOutPackQty\":\"已出货包数\"," +
                "\"fScanedPackQty\":\"已扫描包数\",\"fThisScanPackQty\":\"本次扫描包数\",\"fUnScanPackQty\":\"未扫描包数\"}";
        if (flowType.equals("0")) {
            title2 = "{\"fBarCode\":\"条码代号\",\"fOrdNo\":\"订单号\",\"fCarCardNo\":\"车牌号\",\"fSplitBatchNo\":\"拆单批次\",\"fSPCode\":\"分包号\"," +
                    "\"fPackMark\":\"分包说明\",\"fPlaceCode\":\"储位代号\",\"fPlaceName\":\"储位名称\",\"fCtnL\":\"包装长\"," +
                    "\"fCtnW\":\"包装宽\",\"fCtnH\":\"包装高\",\"fPackCuft\":\"包装体积\",\"fPackPcs\":\"包装件数\"," +
                    "\"fScanorID\":\"扫描人代号\",\"fScanor\":\"扫描人\",\"fScanDate\":\"扫描日期\",\"fID\": \"条码ID\"}";
            //baritemDao = SoonforApplication.mDaoSession.getOut2BarItemBeanDao();
        } else if (flowType.equals("1")) {
            title2 = "{\"fBarCode\":\"条码代号\",\"fDlvNo\":\"出库通知单\",\"fOrdNo\":\"订单号\",\"fSplitBatchNo\":\"拆单批次\",\"fSPCode\":\"分包号\"," +
                    "\"fPackMark\":\"分包说明\",\"fStkCode\":\"仓库代号\",\"fPlaceCode\":\"储位代号\",\"fPlaceName\":\"储位名称\",\"fStkTrayCode\":\"托盘代号\"," +
                    "\"fScanorID\":\"扫描人代号\",\"fScanor\":\"扫描人\",\"fScanDate\":\"扫描日期\",\"fID\":\"条码ID\",\"fBarCodeQty\": \"条码数量\"}";
        }
        title3 = "{\"fBarCode\":\"条码代号\",\"fBarErrorMessage\":\"解析状态\",\"fRemark\":\"日志说明\",\"fScanDate\":\"扫描日期\"}";


        netStatusBeanDao = SoonforApplication.mDaoSession.getNetStatusBeanDao();
        inStatusBeanDao = SoonforApplication.mDaoSession.getOutOrdInStatusBeanDao();
        qitaoDao = SoonforApplication.mDaoSession.getOutOrdQitaoBeanDao();
        bar2Map = new HashMap<>();
        outScanLogList = new ArrayList<>();

    }

    //1)获取数据页签
    public void getTitle(Context context, String st_id) {
        Request.StkPrepByPackPage(context, st_id, this);
    }

    //2)条码明细 & 订单齐套状况
//    public String StkPrepByPackBarItem(Context context, String fbarcode, String stkcode, String logistbatchno, String carcardno, String ifundoscanmode) {
//        return Request.StkPrepByPackBarItem(context, fbarcode, stkcode, logistbatchno, carcardno, ifundoscanmode);
//    }

    //扫描确定
    public void StkPrepByPackSaveBar(Context context, String st_id, String stkcode, String logistbatchno, String carcardno, String fdlvno, String ifundoscanmode, JSONArray data) {
        Request.StkPrepByPackSaveBar(context, st_id, stkcode, logistbatchno, carcardno, fdlvno, ifundoscanmode, data, this);
    }

    //产生出货单 分包备货/分包出货
    public void StkPrepByPackGenStkOut(Context context, String st_id, String logistbatchno, String carcardno, String sorder) {
        if (st_id.equals("0")) {
            Request.StkPrepByPackGenStkOut(context, logistbatchno, carcardno, this);
        } else if (st_id.equals("1")) {
            Request.StkOutByPackGenStkOut(context, sorder, this);
        }
    }

    //清空数据 和 关闭窗体  时 调用接口清空缓存
    public void StkOutByPackOnClose(Context context, String st_id, JSONArray data) {
        Request.StkOutByPackOnClose(context, data, st_id, this);
    }

    private String fbarcode;

    //根据条码获取物流批次信息
    public void GetMBathByBarCode(Context context, String fbarcode, String fbatchno) {
        this.fbarcode = fbarcode;
        Request.getConditionByBarCode(context, fbarcode, fbatchno, this);
    }

    String[] titles;

    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.STKPREPBYPACKPAGE:
                try {
                    JSONObject head = new JSONObject(data);
                    if (head.getBoolean("success")) {
                        JSONObject jo = new JSONObject(head.getString("data"));
                        String title = jo.getString("title");
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
                break;
            case Request.GETMATHINFOBYBARCODE://根据条码获取物流批次|车牌号
                ArrayList<String[]> datas = new ArrayList<>();
                titles = null;
                try {
                    JSONObject jo = new JSONObject(data);
                    if (jo.getBoolean("success")) {
                        JSONObject joc = new JSONObject(jo.getString("data"));
                        JSONArray ja = joc.getJSONArray("item");
                        if (ja != null && ja.length() > 0) {
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject joItme = ja.getJSONObject(i);
                                LogistBatchBean bean = gson.fromJson(ja.getJSONObject(i).toString(), LogistBatchBean.class);
//                                if (i == 0) {
//                                    titles = new String[]{"物流批次", "车牌号", "载物台代号", "载物台", "建立人代号", "建立人"};
//                                }
                                String[] itemStr = new String[]{"false", bean.getfLogistBatchNo(), bean.getfCarCardNo(), bean.getfLPCode(), bean.getfLPName(), bean.getfCreatorID(), bean.getfCreator()};
                                datas.add(itemStr);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                view.setGetBathByBarCode(titles, datas, fbarcode);
                break;
            case Request.STKPREPBYPACKSAVEBAR:
                try {
                    JSONObject databean = new JSONObject(data);
                    if (databean.getBoolean("success")) {
                        view.setSuccessResult("操作成功");
                        if (flowType.equals("0")) {
                            view.delHeBaoData(true, true);
                        } else {
                            view.delHeBaoData(true, false);
                        }
                    } else {
                        view.setErrorMsg(-1, "提交失败:\n" + databean.getString("errormsg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setErrorMsg(-1, "数据结构返回有误");
                }
                break;
            case Request.STKPREPBYPACKGENSTKOUT:
                try {
                    JSONObject head = new JSONObject(data);
                    if (head.getBoolean("success")) {
                        JSONObject o = new JSONObject(head.getString("data"));
                        JSONArray jr = new JSONArray(o.getString("item"));
                        String billno = "";
                        for (int i = 0; i < jr.length(); i++) {
                            JSONObject object = new JSONObject(jr.get(i).toString());
                            billno += object.getString("fRtnBillNo") + "\n";
                        }
                        view.setSuccessResult("产生出货单成功:" + billno);
                    } else {
                        view.setErrorMsg(-1, "产生出货单失败:\n" + head.getString("errormsg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setErrorMsg(-1, "数据结构返回有误");
                }
                break;
            case Request.STKINBYPACKONCLOSE:

                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String data, String msg) {
        view.setErrorMsg(statusCode, msg);
        switch (requestCode) {
            case Request.STKINBYPACKONCLOSE:
                break;
        }
    }

    //检查是否齐套
    private boolean checkIsQiTao(OutOrdInStatusBean oo) {
        if (isQiTao(oo)) {
            qitaoSetData(oo);
        } else {
            oo.setFUnScanPackQty(hasNotScanNum(oo) + "");
            inStatusBeanDao.update(oo);
        }
        return false;
    }

    private void qitaoSetData(OutOrdInStatusBean oo) {
        OutOrdQitaoBean bean = new OutOrdQitaoBean();
        bean.setFOrdNo(oo.getFOrdNo());
        bean.setFProdNo(oo.getFProdNo());
        bean.setFScanedPackQty(oo.getFScanedPackQty());
        bean.setFStkOutPackQty(oo.getFStkOutPackQty());
        bean.setFThisScanPackQty(oo.getFThisScanPackQty());
        bean.setFTotalPackQty(oo.getFTotalPackQty());
        bean.setFUnScanPackQty("0");
        inStatusBeanDao.delete(oo);
        if (qitaoDao.load(bean.getFOrdNo()) == null) {
            qitaoDao.insert(bean);
        } else {
            qitaoDao.update(bean);
        }
        List<OutOrdQitaoBean> beans = new ArrayList<>();
        beans.add(bean);
        makeData1(beans);
        view.resetListView();
    }

    private boolean isQiTao(OutOrdInStatusBean oo) {
        //总包数 =已出货包数 +已扫描包数 +本次扫描包数
        int totle = Integer.parseInt(CommUtil.formatInteger(oo.getFTotalPackQty()));
        int yisao = Integer.parseInt(CommUtil.formatInteger(oo.getFScanedPackQty()));
        int yichuku = Integer.parseInt(CommUtil.formatInteger(oo.getFStkOutPackQty()));
        int bencisao = Integer.parseInt(CommUtil.formatInteger(oo.getFThisScanPackQty()));
        //int weisao = Integer.parseInt(CommUtil.formatInteger(oo.getFUnScanPackQty()));

        if (view.getCheckStu()) {
            if (totle == (yichuku + bencisao)) {
                return true;
            }
        } else {
            if (totle == (yichuku + yisao + bencisao)) {
                return true;
            }
        }

        return false;
    }

    //未齐套的 未扫描数
    private int hasNotScanNum(OutOrdInStatusBean oo) {
        boolean isCheXiao = view.getCheckStu();

        //未扫描＝总包数-已经出库-已经扫描-本次扫描
        int totle = Integer.parseInt(CommUtil.formatInteger(oo.getFTotalPackQty()));
        int yichuku = 0;
        yichuku = Integer.parseInt(CommUtil.formatInteger(oo.getFStkOutPackQty()));
        int yisao = Integer.parseInt(CommUtil.formatInteger(oo.getFScanedPackQty()));
        int bencisao = Integer.parseInt(CommUtil.formatInteger(oo.getFThisScanPackQty()));

        if (!isCheXiao) {
            int shengyushu = (totle - yisao - yichuku - bencisao);
            return shengyushu < 0 ? 0 : shengyushu;
        } else {
            int shengyushu = (totle - yisao - yichuku + bencisao);
            return shengyushu < 0 ? 0 : shengyushu;
        }
    }

    //齐套的 未扫描数
    private int hasNotScanNum(OutOrdQitaoBean oo) {
        boolean isCheXiao = view.getCheckStu();

        //未扫描＝总包数-已经出库-已经扫描-本次扫描
        int totle = Integer.parseInt(CommUtil.formatInteger(oo.getFTotalPackQty()));
        int yichuku = 0;
        yichuku = Integer.parseInt(CommUtil.formatInteger(oo.getFStkOutPackQty()));
        int yisao = Integer.parseInt(CommUtil.formatInteger(oo.getFScanedPackQty()));
        int bencisao = Integer.parseInt(CommUtil.formatInteger(oo.getFThisScanPackQty()));

        if (!isCheXiao) {
            int shengyushu = (totle - yisao - yichuku - bencisao);
            return shengyushu < 0 ? 0 : shengyushu;
        } else {
            int shengyushu = (totle - yisao - yichuku + bencisao);
            return shengyushu < 0 ? 0 : shengyushu;
        }
    }


    public void makeData0(List<OutOrdInStatusBean> inStatusBeans) {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title0));
            List<String[]> datas = new ArrayList<>();
            for (OutOrdInStatusBean qq : inStatusBeans) {
                String[] ss = new String[titleMap.get(1).length];
                ss[0] = qq.getFOrdNo();
                ss[1] = qq.getFProdNo();
                ss[2] = CommUtil.formatInteger(qq.getFTotalPackQty());
                ss[3] = CommUtil.formatInteger(qq.getFStkOutPackQty());
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

    public void makeData1(List<OutOrdQitaoBean> qitaoBeans) {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title1));
            List<String[]> datas = new ArrayList<>();
            for (OutOrdQitaoBean qq : qitaoBeans) {
                String[] ss = new String[titleMap.get(1).length];
                ss[0] = qq.getFOrdNo();
                ss[1] = qq.getFProdNo();
                ss[2] = CommUtil.formatInteger(qq.getFTotalPackQty());
                ss[3] = CommUtil.formatInteger(qq.getFStkOutPackQty());
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

    public void makeData2() {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title2));
            List<String[]> datas = new ArrayList<>();
            for (Map.Entry<String, Out2BarItemBean> map : bar2Map.entrySet()) {
                Out2BarItemBean bb = map.getValue();
                String[] ss = new String[titleMap.get(1).length];
                ss[0] = bb.getfBarCode();
                ss[1] = bb.getfOrdNo();
                ss[2] = bb.getfCarCardNo();
                ss[3] = bb.getfSplitBatchNo();
                ss[4] = bb.getfSPCode();
                ss[5] = bb.getfPackMark();
                ss[6] = bb.getfPlaceCode();
                ss[7] = bb.getfPlaceName();
                ss[8] = CommUtil.formatInteger(bb.getfCtnL());
                ss[9] = CommUtil.formatInteger(bb.getfCtnW());
                ss[10] = CommUtil.formatInteger(bb.getfCtnH());
                ss[11] = bb.getfPackCuft();
                ss[12] = bb.getfPackPcs();
                ss[13] = bb.getfScanorID();
                ss[14] = bb.getfScanor();
                ss[15] = bb.getfScanDate();
                ss[16] = bb.getfID();
                datas.add(ss);
            }
            view.setDatas(2, titleMap.get(1), datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeData2_2() {
        try {
            //构建UI的数据体
            Map<Integer, String[]> titleMap = JsonUtils.getKeyAndTitle(false, new JSONObject(title2));
            List<String[]> datas = new ArrayList<>();
            for (Map.Entry<String, Out2BarItemBean> map : bar2Map.entrySet()) {
                Out2BarItemBean bb = map.getValue();
                String[] ss = new String[titleMap.get(1).length];
                ss[0] = bb.getfBarCode();
                ss[1] = bb.getfDlvNo();
                ss[2] = bb.getfOrdNo();
                ss[3] = bb.getfSplitBatchNo();
                ss[4] = bb.getfSPCode();
                ss[5] = bb.getfPackMark();
                ss[6] = bb.getfStkCode();
                ss[7] = bb.getfPlaceCode();
                ss[8] = bb.getfPlaceName();
                ss[9] = bb.getfStkTrayCode();
                ss[10] = bb.getfScanorID();
                ss[11] = bb.getfScanor();
                ss[12] = bb.getfScanDate();
                ss[13] = bb.getfID();
                ss[14] = bb.getfBarCodeQty();
                datas.add(ss);
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
            for (OutScanLogBean qq : outScanLogList) {
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

    public void reflashNet() {
        NetStatusBean netStatusBean = netStatusBeanDao.load("chuku");
        if (netStatusBean != null) {
            //分别从未齐套表和 齐套表中取出对应的数相加
            List<OutOrdInStatusBean> inbeans = inStatusBeanDao.loadAll();
            List<OutOrdQitaoBean> qibeans = qitaoDao.loadAll();

            int yingsao = 0;
            int yisao = 0;
            int benci = bar2Map.size();
            int weisao = 0;
            for (OutOrdInStatusBean b : inbeans) {
                yingsao += CommUtil.formatStrToInteger(b.getFTotalPackQty());
                yisao += CommUtil.formatStrToInteger(b.getFScanedPackQty());
//                benci += CommUtil.formatStrToInteger(b.getFThisScanPackQty());
               weisao += CommUtil.formatStrToInteger(b.getFUnScanPackQty());
            }

            for (OutOrdQitaoBean b : qibeans) {
                yingsao += CommUtil.formatStrToInteger(b.getFTotalPackQty());
                yisao += CommUtil.formatStrToInteger(b.getFScanedPackQty());
//                benci += CommUtil.formatStrToInteger(b.getFThisScanPackQty());
                weisao += CommUtil.formatStrToInteger(b.getFUnScanPackQty());
            }

            netStatusBean.setYingsao(yingsao);
            netStatusBean.setYisao(yisao);
            netStatusBean.setBenci(benci);
            if(OutStoreActivity.cbDel.isChecked()){//撤销扫描
                weisao = yingsao - yisao + benci;
            }else {}
            netStatusBean.setWeisao(weisao<0 ? 0: weisao);
            netStatusBeanDao.update(netStatusBean);
        }
    }

    /**
     * 相关表 更新数据 dingyg修改
     */
    public void loadDb(int tabindex, String st_id) {
        switch (tabindex) {
            case 0://未齐套订单表
                List<OutOrdInStatusBean> notQtbeans = inStatusBeanDao.loadAll();
                makeData0(notQtbeans);
                break;
            case 1://齐套订单表
                List<OutOrdQitaoBean> qtbeans = qitaoDao.loadAll();
                makeData1(qtbeans);
                break;
            case 2://条码明细表
                if (st_id.equals("0")) {
                    makeData2();
                } else if (st_id.equals("1")) {
                    makeData2_2();
                }
                break;
            case 3://扫描日志表
//                List<OutScanLogBean> slbeans = scanlogDao.loadAll();
//                makeData3(slbeans);
                makeData3();
                break;
        }
    }

    /**
     * 保存备货条件，解析异步中会自动匹配解析解析条码
     *
     * @param picis
     */
    public void SaveBhtjAndStartAnalysisBarcode(String[] picis) {
        OutStoreActivity.conditonBean.setfLogistBatchNo(picis[1]);
        OutStoreActivity.conditonBean.setfCarCardNo(picis[2]);
        OutStoreActivity.conditonBean.setfLPCode(picis[3]);
        OutStoreActivity.conditonBean.setfLPName(picis[4]);
    }


    /**
     * 循环解析分包条码
     *
     * @param mContext
     * @param fstkCode//仓库代号
     */
    public void requestBarCodeInfo(Context mContext, String fstkCode, String ts_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                while (OutStoreActivity.isNeedAsyn) {
                    if (checkConditions(ts_id)) {
                        //List<OutScanLogBean> outScanLogList = scanlogDao.loadAll();
                        if (outScanLogList != null && outScanLogList.size() > 0) {
                            for (int i = 0; i < outScanLogList.size(); i++) {
                                try {
                                    if (i < outScanLogList.size()) {
                                        OutScanLogBean barCodeBean = outScanLogList.get(i);
                                        if (barCodeBean != null && barCodeBean.getFErrorMsg().equals("")) {//只解析未解析的 !barCodeBean.isStartRequest()避免重复请求
                                            //outScanLogList.get(i).setfIsStartRequest(true);
                                            String bcInfo = Request.StkPrepByPackBarItem(mContext, ts_id, barCodeBean.getFBarCode(),
                                                    fstkCode, OutStoreActivity.cbDel.isChecked() ? "1" : "0");
                                            int finalI = i;
                                            new Handler(mContext.getMainLooper()).post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (bcInfo != null) {
                                                        asynBarCode(gson, bcInfo, ts_id, finalI);
                                                    } else {
                                                        outScanLogList.get(finalI).setFErrorMsg("解析失败");
                                                        outScanLogList.get(finalI).setFRemark("请求出错或超时");
                                                        outScanLogList.get(finalI).setFScanDate(DateUtils.getTodayDateTime("yyyy-MM-dd hh:mm:ss"));
                                                        view.FaileSound();
//                                                        //刷新一下列表
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
     * 解析条码
     *
     * @param gson
     * @param data
     */
    private void asynBarCode(Gson gson, String data, String stId, int position) {
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
                String instatus = jo.getString("instatus");
                //齐套和不齐套总列表
                List<OutOrdInStatusBean> inStatusBeans = gson.fromJson(instatus, new TypeToken<List<OutOrdInStatusBean>>() {
                }.getType());
                String baritem = jo.getString("baritem");
                //条码明细
                List<Out2BarItemBean> barItemBeans = gson.fromJson(baritem, new TypeToken<List<Out2BarItemBean>>() {
                }.getType());
                //条码明细
                for (int i = 0; i < barItemBeans.size(); i++) {
                    Out2BarItemBean bb = barItemBeans.get(i);
                    //已经扫码成功了，就更新日志表的记录
                    boolean isExistSuccess = false;
                    for (int j = 0; j < outScanLogList.size(); j++) {
                        if (outScanLogList.get(j).getFBarCode().equals(bb.getfBarCode()) && outScanLogList.get(j).getFErrorMsg().equals("识别成功")) {
                            isExistSuccess = true;
                            break;
                        }
                    }
                    for (int j = 0; j < outScanLogList.size(); j++) {
                        if (outScanLogList.get(j).getFBarCode().equals(bb.getfBarCode()) && outScanLogList.get(j).getFErrorMsg().equals("")) {
                            if (isExistSuccess) {
                                asynStatus = 1;
                                outScanLogList.get(position).setFErrorMsg("重复扫描");
                                outScanLogList.get(position).setFRemark("重复扫描");
                                outScanLogList.get(position).setFScanDate(bb.getfScanDate());
                                view.RepatSound();
                            } else {
                                asynStatus = 2;
                                outScanLogList.get(position).setFErrorMsg("识别成功");
                                outScanLogList.get(position).setFRemark("扫码成功");
                                outScanLogList.get(position).setFScanDate(bb.getfScanDate());
                                //更新条码明细表
                                if (!bar2Map.containsKey(bb.getfBarCode()) || bar2Map.get(bb.getfBarCode()) == null) {
                                    bar2Map.put(bb.getfBarCode(), bb);
                                    //入库状况 和 齐套
                                    for (int k = 0; k < inStatusBeans.size(); k++) {
                                        OutOrdInStatusBean oo = inStatusBeans.get(k);
                                        ////更新一下已扫描数
                                        //oo.setFScanedPackQty(CommUtil.formatStrToInteger(oo.getFStkOutPackQty()) + "");
                                        if (qitaoDao.load(oo.getFOrdNo()) == null) {
                                            if (inStatusBeanDao.load(oo.getFOrdNo()) == null) {
                                                //判断条码是不是属于这个订单  不属于的不管
                                                if (bb.getfOrdNo().equals(oo.getFOrdNo())) {
                                                    oo.setFThisScanPackQty("1");
                                                } else {
                                                    oo.setFThisScanPackQty("0");
                                                }
                                                inStatusBeanDao.insert(oo);
                                                if (!view.getCheckStu()) {
                                                    //插完表后检查是否是齐套
                                                    checkIsQiTao(oo);
                                                } else {
                                                    oo.setFUnScanPackQty(hasNotScanNum(oo) + "");
                                                    inStatusBeanDao.update(oo);
                                                }
                                            } else {
                                                OutOrdInStatusBean tmp = inStatusBeanDao.load(bb.getfOrdNo());
                                                //跟条码是同一个生产单号的才更新数量
                                                if (stId.equals("0")) {
                                                    if (oo.getFProdNo().equals(bb.getfProdNo())) {
                                                        int num = Integer.parseInt(CommUtil.formatInteger(tmp.getFThisScanPackQty()));
                                                        num++;
                                                        oo.setFThisScanPackQty(num + "");
                                                        //更新完后检查是否已经齐套
                                                        if (!view.getCheckStu()) {
                                                            checkIsQiTao(oo);
                                                        } else {
                                                            oo.setFUnScanPackQty(hasNotScanNum(oo) + "");
                                                            inStatusBeanDao.update(oo);
                                                        }
                                                    }
                                                } else {
                                                    int num = Integer.parseInt(CommUtil.formatInteger(tmp.getFThisScanPackQty()));
                                                    num++;
                                                    oo.setFThisScanPackQty(num + "");

                                                    //更新完后检查是否已经齐套
                                                    if (!view.getCheckStu()) {
                                                        checkIsQiTao(oo);
                                                    } else {
                                                        oo.setFUnScanPackQty(hasNotScanNum(oo) + "");
                                                        inStatusBeanDao.update(oo);
                                                    }
                                                }
                                            }
                                            inStatusBeans.set(k, oo);
                                        } else {
                                            //如果是撤销操作,  则要撤销齐套的
                                            if (view.getCheckStu()) {
                                                OutOrdQitaoBean tmp = qitaoDao.load(bb.getfOrdNo());
                                                if (tmp != null) {
                                                    if (stId.equals("0")) {
                                                        //跟条码是同一个生产单号的才更新数量
                                                        if (tmp.getFProdNo().equals(bb.getfProdNo())) {
                                                            int num = Integer.parseInt(CommUtil.formatInteger(tmp.getFThisScanPackQty()));
                                                            num++;
                                                            tmp.setFThisScanPackQty(num + "");
                                                            tmp.setFUnScanPackQty(hasNotScanNum(tmp) + "");
                                                        }
                                                    } else {
                                                        int num = Integer.parseInt(CommUtil.formatInteger(tmp.getFThisScanPackQty()));
                                                        num++;
                                                        tmp.setFThisScanPackQty(num + "");
                                                        tmp.setFUnScanPackQty(hasNotScanNum(tmp) + "");
                                                    }
                                                    qitaoDao.update(tmp);
                                                }
                                            }
                                        }
                                    }
                                    //构建UI
                                    //先生成条码
                                    if (stId.equals("0")) {
                                        makeData2();
                                    } else if (stId.equals("1")) {
                                        makeData2_2();
                                    }
                                    //生成订单入库状况
                                    makeData0(inStatusBeans);
                                    //成功解析锁定
                                    view.setCbDelStatu(false);
                                }
                            }

                            break;
                        }
                    }
                }
            } else {
                JSONArray jr;
                try {
                    JSONObject jo = new JSONObject(head.getString("data"));
                    jr = new JSONArray(jo.getString("error"));
                } catch (Exception e) {
                    jr = null;
                }
                if (outScanLogList.get(position).getFErrorMsg().equals("")) {
                    if (jr != null && jr.length() > 0) {
                        //已经扫码成功了，就更新日志表的记录
                        OutScanLogBean sb = gson.fromJson(jr.get(0).toString(), new TypeToken<OutScanLogBean>() {
                        }.getType());
                        outScanLogList.get(position).setFBarCode(sb.getFBarCode());
                        outScanLogList.get(position).setFErrorMsg(sb.getFErrorMsg());
                        outScanLogList.get(position).setFRemark(sb.getFRemark().equals("") ? "识别失败" : sb.getFRemark());
                        outScanLogList.get(position).setFScanDate(sb.getFScanDate());
                        view.FaileSound();
                        asynStatus = 2;
                    }
                }
            }
            //刷新一下列表
            view.reflashDataList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (asynStatus == 1) {
            if (outScanLogList.get(position).getFErrorMsg().equals("")) {
                outScanLogList.get(position).setFErrorMsg("解析失败");
                outScanLogList.get(position).setFRemark("解析出错");
                outScanLogList.get(position).setFScanDate(DateUtils.getTodayDateTime("yyyy-MM-dd hh:mm:ss"));
                view.FaileSound();
                //刷新一下列表
                view.reflashDataList();
            }
        }
    }

    /**
     * 确认前的判断 日志中是否还有未解析完的条码
     * cxCode 为程序代号
     */
    public boolean isHaveNoAsyn() {
        //List<OutScanLogBean> outScanLogList = scanlogDao.loadAll();
        boolean isExistNoAsyn = false;//是否有未解析的存在
        if (outScanLogList != null && outScanLogList.size() > 0) {
            for (int i = 0; i < outScanLogList.size(); i++) {
                if (outScanLogList.get(i).getFErrorMsg().equals("")) {
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
    private boolean checkConditions(String sId) {
        boolean requiredConditionIsChoose = false;//必选条件是否已经全部选择了
        try {
            if (OutStoreActivity.conditonBean != null) {
                if (sId.equals("0")) {//分包备货
                    if (!OutStoreActivity.conditonBean.getfLogistBatchNo().equals("")
                            && !OutStoreActivity.conditonBean.getfCarCardNo().equals("")
                            && !OutStoreActivity.conditonBean.getfLPCode().equals("")) {
                        requiredConditionIsChoose = true;
                    }
                } else if (sId.equals("1")) {//分包出货
                    if (!OutStoreActivity.conditonBean.getfShippingOrder().equals("")) {
                        requiredConditionIsChoose = true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return requiredConditionIsChoose;
    }
}
