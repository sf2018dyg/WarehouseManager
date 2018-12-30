package com.soonfor.warehousemanager.http.api;

import android.content.Context;

import com.orhanobut.hawk.Hawk;
import com.soonfor.warehousemanager.home.login.bean.UserInfoBean;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils.AsyncCallback;
import com.soonfor.warehousemanager.module.outstore.OutStoreActivity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 修改人：DC-ZhuSuiBo on 2018/2/28 15:37
 * 邮箱：suibozhu@139.com
 */
public class Request {
    public static final int TEST = 101;
    public static final int GETSTORES = 102;
    public static final int LOGIN = 103;
    public static final int LOGOUT = 105;
    public static final int GETPLACECODELIST = 106;
    public static final int GETDEPTCODELIST = 107;
    public static final int GETRECEIPTCODELISTPDA = 108;
    public static final int GETREASONCODELISTPDA = 109;
    public static final int GETLOGISTBATCHNOLISTPDA = 110;
    public static final int GETCARCARDNOLISTPDA = 111;
    public static final int GETORDERNOLISTPDA = 112;
    public static final int STKINBYPACKSCANPDA = 113;
    public static final int STKINBYPACKPAGE = 114;
    public static final int BARITEM = 117;
    public static final int STKINBYPACKSAVEBAR = 119;
    public static final int STKINBYPACKDELBAR = 120;

    public static final int STKPREPBYPACKPAGE = 121;
    public static final int STKPREPBYPACKORDUNQITAO = 122;
    public static final int STKOUTBYPACKORDQITAO = 123;
    public static final int STKPREPBYPACKBARITEM = 124;
    public static final int STKPREPBYPACKSAVEBAR = 125;
    public static final int STKPREPBYPACKDELBAR = 126;
    public static final int STKPREPBYPACKGENSTKOUT = 127;

    //分拣
    public static final int PACKSORTINGBYPACKPAGE = 128;
    public static final int PACKSORTINGBYPACKBARITEM = 129;
    public static final int PACKSORTINGBYPACKSAVABAR = 130;
    public static final int PACKSORTINGBYPACKDELBAR = 131;

    //入库退库
    public static final int STKINRTNSCANBYPACKPAGE = 132;
    public static final int STKINRTNBYPACKBARITEM = 133;
    public static final int STKINRTNSCANBYPACKSAVEBAR = 134;
    public static final int STKINRTNSCANBYPACKDELBAR = 135;
    public static final int GETSTKINNOLISTPDA = 136;

    public static final int STKOUTBYPACKADDORDER = 137;
    public static final int STKINBYPACKONCLOSE = 138;
    public static final int COMBINEBYPACKPAGE = 139;
    public static final int COMBINEBYPACKBARITEM = 140;
    public static final int GETSPSIDLISTPDA = 141;
    public static final int COMBINEBYPACKSAVEBAR = 142;
    public static final int COMBINEBYPACKDELBAR = 143;
    public static final int GETPRINTERLISTPDA = 144;
    public static final int GETSCHEMELISTPDA = 145;
    public static final int STKSCANBYPACKPRINTBAR = 146;
    public static final int STKSCANBYPACKNOSCAN = 147;
    public static final int GETPRINTBARDATAPDA = 148;
    public static final int GETMATHINFOBYBARCODE = 149;

    //出库
    public static final int GETDLVNOLIST = 150;

    /**
     * 验证服务器地址
     */
    public static void Test(Context cxt, AsyncCallback callback) {
        AsyncUtils.get(cxt, UserInfo.TEST_SERVICER, TEST, callback);
    }

    /**
     * 获取仓库列表
     */
    public static void getStores(Context cxt, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        AsyncUtils.post(cxt, UserInfo.GET_STORES, jo.toString(), GETSTORES, callback);
    }

    /**
     * @param cxt      登录
     * @param userCode 用户代号
     * @param passWord 密码
     * @param callback
     */
    public static void sendLogin(Context cxt, String userCode, String passWord, AsyncCallback callback) {
//        RequestParams params = new RequestParams();
//        params.put("usercode", userCode);
//        params.put("password", passWord);
        JSONObject jo = new JSONObject();
        try {
            jo.put("usercode", userCode);
            jo.put("password", passWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncUtils.post(cxt, UserInfo.LOGIN, jo.toString(), LOGIN, callback);
    }

    public static void LogOut(Context context, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        AsyncUtils.post(context, UserInfo.LOGOUT, jo.toString(), LOGOUT, callback);
    }

    public static void GetPlaceCodeList(Context context, String fstkcode, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("fstkcode", fstkcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_PLACECODELISTPDA, jo.toString(), GETPLACECODELIST, callback);
    }

    public static void GetDeptCodeList(Context context, String stkcode, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("stkcode", stkcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_DEPTCODELIST, jo.toString(), GETDEPTCODELIST, callback);
    }

    public static void GetReceiptCodeList(Context context, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        AsyncUtils.post(context, UserInfo.GET_RECEIPTCODELISTPDA, jo.toString(), GETRECEIPTCODELISTPDA, callback);
    }

    public static void GetReasonCodeList(Context context, String freceiptcode, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("receiptcode", freceiptcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_REASONCODELISTPDA, jo.toString(), GETREASONCODELISTPDA, callback);
    }

    public static void GetLogistBatchNoList(Context context, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        AsyncUtils.post(context, UserInfo.GET_LOGISTBATCHNOLISTPDA, jo.toString(), GETLOGISTBATCHNOLISTPDA, callback);
    }

    public static void GetCarcardNoList(Context context, String flogistbatchno, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("logistbatchno", flogistbatchno);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_CARCARDNOLISTPDA, jo.toString(), GETCARCARDNOLISTPDA, callback);
    }

    public static void GetOrderNoList(Context context, String logistbatchno, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("logistbatchno", logistbatchno);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GETORDERNOLISTPDA, jo.toString(), GETORDERNOLISTPDA, callback);
    }

    public static void GetDlvNotionList(Context context, String stkcode, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("stkcode", stkcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.STKPREPBYPACKDLVNOLIST, jo.toString(), GETDLVNOLIST, callback);
    }

    public static void StkInByPackScan(Context context, String fbarcode, AsyncCallback callback) {
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        JSONObject jo = new JSONObject();
        try {
            jo.put("barcode", fbarcode);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.STKINBYPACKSCANPDA, jo.toString(), STKINBYPACKSCANPDA, callback);
    }

    public static void StkInByPackPage(Context context, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        AsyncUtils.post(context, UserInfo.STKINBYPACKPAGE, jo.toString(), STKINBYPACKPAGE, callback);
    }

    /**
     * @param barcode     条码
     * @param stkcode     仓库代号
     * @param receiptcode 单据代号
     * @param reasoncode  原因代号
     * @param placecode   储位代号
     * @param deptid      部门代号
     **/
    public static String StkInByPackBarItem(Context context, String barcode, String stkcode, String receiptcode, String reasoncode, String placecode, String deptid, String ifundoscanmode) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        String result = null;
        try {
            jo.put("barcode", barcode);
            jo.put("stkcode", stkcode);
            jo.put("receiptcode", receiptcode);
            jo.put("reasoncode", reasoncode);
            jo.put("placecode", placecode);
            jo.put("deptid", deptid);
            jo.put("ifundoscanmode", ifundoscanmode);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            result = AsyncUtils.resultByOkhttp(context, 1, UserInfo.BARITEM, jo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void StkInByPackSaveBar(Context context, String stkcode, String receiptcode, String reasoncode, String placecode, String deptid, String ifundoscanmode, JSONArray data, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("stkcode", stkcode);
            jo.put("receiptcode", receiptcode);
            jo.put("reasoncode", reasoncode);
            jo.put("placecode", placecode);
            jo.put("deptid", deptid);
            jo.put("ifundoscanmode", ifundoscanmode);
            jo.put("data", data);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.STKINBYPACKSAVEBAR, jo.toString(), STKINBYPACKSAVEBAR, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void StkInByPackDelBar(Context context, String fbarcode, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("barcode", fbarcode);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.STKINBYPACKDELBAR, jo.toString(), STKINBYPACKDELBAR, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void StkPrepByPackPage(Context context, String st_id, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        if (st_id.equals("0")) {//分包备货
            AsyncUtils.post(context, UserInfo.STKPREPBYPACKPAGE, jo.toString(), STKPREPBYPACKPAGE, callback);
        } else if (st_id.equals("1")) {//分包出货
            AsyncUtils.post(context, UserInfo.STKPREPBYPACKPAGE_OUT, jo.toString(), STKPREPBYPACKPAGE, callback);
        }
    }

    /**
     * @param fbarcode        字符	条码	否
     * @param stkcode         字符	仓库	否
     * @param //logistbatchno 字符	物流批次	否
     * @param //carcardno     字符	车牌号	否
     * @param //shippingorder 出货通知单
     **/
    public static String StkPrepByPackBarItem(Context context, String ts_id, String fbarcode, String stkcode, String ifundoscanmode) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        String result = null;
        if (userinfo != null) {
            try {
                jo.put("barcode", fbarcode);
                jo.put("stkcode", stkcode);
                jo.put("ifundoscanmode", ifundoscanmode);
                jo.put("usercode", userinfo.getUsercode());
                jo.put("username", userinfo.getUsername());
                if(ts_id.equals("0")){//分包备货
                    jo.put("logistbatchno", OutStoreActivity.conditonBean.getfLogistBatchNo());
                    jo.put("carcardno", OutStoreActivity.conditonBean.getfCarCardNo());
                    result = AsyncUtils.resultByOkhttp(context, 1, UserInfo.STKPREPBYPACKBARITEM, jo.toString());
                }else if(ts_id.equals("1")){//分包出货
                    jo.put("dlvno", OutStoreActivity.conditonBean.getfShippingOrder());
                    result = AsyncUtils.resultByOkhttp(context, 1, UserInfo.STKPREPBYPACKBARITEM_OUT, jo.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = null;
            }
        }
        return result;
    }

    /**
     * @param stkcode       字符	仓库	否
     * @param logistbatchno 字符	物流批次	否
     * @param carcardno     字符	车牌号	否
     * @param data          字符	已扫描数据	否
     **/
    public static void StkPrepByPackSaveBar(Context context, String st_id, String stkcode, String logistbatchno, String carcardno, String fdlvno, String ifundoscanmode, JSONArray data, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("stkcode", stkcode);
            jo.put("data", data);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            jo.put("ifundoscanmode", ifundoscanmode);
            if (st_id.equals("0")) {
                jo.put("logistbatchno", logistbatchno);
                jo.put("carcardno", carcardno);
                AsyncUtils.post(context, UserInfo.STKPREPBYPACKSAVEBAR, jo.toString(), STKPREPBYPACKSAVEBAR, callback);
            } else if (st_id.equals("1")) {
                jo.put("dlvno", fdlvno);
                AsyncUtils.post(context, UserInfo.STKPREPBYPACKSAVEBAR_OUT, jo.toString(), STKPREPBYPACKSAVEBAR, callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void StkPrepByPackDelBar(Context context, String st_id, String fbarcode, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("barcode", fbarcode);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            if (st_id.equals("0")) {
                AsyncUtils.post(context, UserInfo.STKPREPBYPACKDELBAR, jo.toString(), STKPREPBYPACKDELBAR, callback);
            } else if (st_id.equals("1")) {
                AsyncUtils.post(context, UserInfo.STKPREPBYPACKDELBAR_OUT, jo.toString(), STKPREPBYPACKDELBAR, callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param logistbatchno 字符	物流批次	否
     * @param carcardno     字符	车牌号	否
     **/
    public static void StkPrepByPackGenStkOut(Context context, String logistbatchno, String carcardno, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("logistbatchno", logistbatchno);
            jo.put("carcardno", carcardno);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.STKPREPBYPACKGENSTKOUT, jo.toString(), STKPREPBYPACKGENSTKOUT, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 产生出货单
     *
     * @param shippingorder 字符	出货通知单	否
     **/
    public static void StkOutByPackGenStkOut(Context context, String shippingorder, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("dlvno", shippingorder);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.STKPREPBYPACKGENSTKOUT_OUT, jo.toString(), STKPREPBYPACKGENSTKOUT, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Fbarcode {
        /**
         * 扫描请求数据
         */
        public static void getDataByScan(Context cxt, String fbarcode, AsyncCallback callback) {
//            RequestParams params = new RequestParams();
//            params.put("barcode", fbarcode);
//            params.put("条码类型", "");
            //AsyncUtils.post(cxt, address + UserInfo.TEST_SERVICER, TEST, callback);
        }

    }


    //分拣
    public static void PackSortingByPackPage(Context context, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        AsyncUtils.post(context, UserInfo.PACKSORTINGBYPACKPAGE, jo.toString(), PACKSORTINGBYPACKPAGE, callback);
    }

    public static String PackSortingByPackBarItem(Context context, String barcode, String stkcode, String receiptcode, String reasoncode, String placecode, String deptid, String ifundoscanmode) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        String result = null;
        if (userinfo != null) {
            try {
                jo.put("barcode", barcode);
                jo.put("stkcode", stkcode);
                jo.put("receiptcode", receiptcode);
                jo.put("reasoncode", reasoncode);
                jo.put("placecode", placecode);
                jo.put("deptid", deptid);
                jo.put("ifundoscanmode", ifundoscanmode);
                jo.put("usercode", userinfo.getUsercode());
                jo.put("username", userinfo.getUsername());
                result = AsyncUtils.resultByOkhttp(context, 1, UserInfo.PACKSORTINGBYPACKBARITEM, jo.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void PackSortingByPackSaveBar(Context context, String stkcode, String receiptcode, String reasoncode, String placecode, String deptid, String ifundoscanmode, JSONArray data, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("stkcode", stkcode);
            jo.put("receiptcode", receiptcode);
            jo.put("reasoncode", reasoncode);
            jo.put("placecode", placecode);
            jo.put("deptid", deptid);
            jo.put("ifundoscanmode", ifundoscanmode);
            jo.put("data", data);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.PACKSORTINGBYPACKSAVABAR, jo.toString(), PACKSORTINGBYPACKSAVABAR, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void PackSortingByPackDelBar(Context context, String barcode, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("barcode", barcode);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.PACKSORTINGBYPACKDELBAR, jo.toString(), PACKSORTINGBYPACKDELBAR, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void StkInRtnScanByPackPage(Context context, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        AsyncUtils.post(context, UserInfo.STKINRTNSCANBYPACKPAGE, jo.toString(), STKINRTNSCANBYPACKPAGE, callback);
    }

    /**
     * @param barcode    条码
     * @param stkcode    仓库代号
     * @param stkinlogno 入库单号
     **/
    public static String StkInRtnByPackBarItem(Context context, String barcode, String stkcode, String stkinlogno) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        String result = null;
        if (userinfo != null) {
            try {
                jo.put("barcode", barcode);
                jo.put("stkcode", stkcode);
                jo.put("stkinlogno", stkinlogno);
                jo.put("usercode", userinfo.getUsercode());
                jo.put("username", userinfo.getUsername());
                result = AsyncUtils.resultByOkhttp(context, 1, UserInfo.STKINRTNBYPACKBARITEM, jo.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param stkcode    仓库代号
     * @param stkinlogno 入库单号
     * @param deptid     部门ID
     * @param data       已扫描数据
     **/
    public static void StkInRtnScanByPackSaveBar(Context context, String stkcode, String stkinlogno, String deptid, JSONArray data, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("stkcode", stkcode);
            jo.put("stkinlogno", stkinlogno);
            jo.put("deptid", deptid);
            jo.put("data", data);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.STKINRTNSCANBYPACKSAVEBAR, jo.toString(), STKINRTNSCANBYPACKSAVEBAR, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param barcode
     **/
    public static void StkInRtnScanByPackDelBar(Context context, String barcode, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("barcode", barcode);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.STKINRTNSCANBYPACKDELBAR, jo.toString(), STKINRTNSCANBYPACKDELBAR, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void GetStkInNoListPDA(Context context, String stkcode, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("stkcode", stkcode);
            AsyncUtils.post(context, UserInfo.GETSTKINNOLISTPDA, jo.toString(), GETSTKINNOLISTPDA, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void StkOutByPackAddOrder(Context context, String logistbatchno, JSONArray data, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("logistbatchno", logistbatchno);
            jo.put("data", data);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.STKOUTBYPACKADDORDER, jo.toString(), STKOUTBYPACKADDORDER, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //清空缓存

    /**
     * @param type 1 分包入库/生产入库扫描 11 备货 2 出库 24 出库退库 25 入库退库  29 分拣 30 合包
     **/
    public static void StkInByPackOnClose(Context context, JSONArray data, String type, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("data", data);
            jo.put("oritype", type);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.STKINBYPACKONCLOSE, jo.toString(), STKINBYPACKONCLOSE, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void StkOutByPackOnClose(Context context, JSONArray data, String stId, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("data", data);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            if(stId.equals("0")){
                jo.put("oritype", "11");
                AsyncUtils.post(context, UserInfo.STKINBYPACKONCLOSE, jo.toString(), STKINBYPACKONCLOSE, callback);
            }else if(stId.equals("1")){
                jo.put("oritype", "2");
                AsyncUtils.post(context, UserInfo.STKINBYPACKONCLOSE_OUT, jo.toString(), STKINBYPACKONCLOSE, callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //合包头页签
    public static void CombineByPackPage(Context context, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        AsyncUtils.post(context, UserInfo.COMBINEBYPACKPAGE, jo.toString(), COMBINEBYPACKPAGE, callback);
    }

    //条码明细页 & 货品明细页

    /**
     * @param barcode   条码
     * @param ordno     订单号
     * @param stkcode   仓库代号
     * @param placecode 储位代号
     **/
    public static void CombineByPackBarItem(Context context, String barcode, String ordno, String stkcode, String placecode, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("barcode", barcode);
            jo.put("ordno", ordno);
            jo.put("stkcode", stkcode);
            jo.put("placecode", placecode);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.COMBINEBYPACKBARITEM, jo.toString(), COMBINEBYPACKBARITEM, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String CombineByPackBarItemNew(Context context, String barcode, String ordno, String stkcode, String placecode) {
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        String result = null;
        if (userinfo != null) {
            try {
                JSONObject object = new JSONObject();
                object.put("barcode", barcode);
                object.put("ordno", ordno);
                object.put("stkcode", stkcode);
                object.put("placecode", placecode);
                object.put("usercode", userinfo.getUsercode());
                object.put("username", userinfo.getUsername());
                result = AsyncUtils.resultByOkhttp(context, 1, UserInfo.COMBINEBYPACKBARITEM, object.toString());
            } catch (Exception e) {
            }
        }
        return result;
    }


    //12.PDA获取所有分包分类列表(GetSPSIDList_PDA)
    public static void GetSPSIDList(Context context, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        AsyncUtils.post(context, UserInfo.GETSPSIDLISTPDA, jo.toString(), GETSPSIDLISTPDA, callback);
    }


    /**
     * @param spsid 分包分类ID
     * @param data
     **/
    public static void CombineByPack(Context context, String spsid, JSONArray data, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("spsid", spsid);
            jo.put("data", data);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.COMBINEBYPACKSAVEBAR, jo.toString(), COMBINEBYPACKSAVEBAR, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param barcode
     **/
    public static void CombineByPackDelBar(Context context, String barcode, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("barcode", barcode);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.COMBINEBYPACKDELBAR, jo.toString(), COMBINEBYPACKDELBAR, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //PDA获取所有打印机
    public static void GetPrinterList(Context context, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        AsyncUtils.post(context, UserInfo.GETPRINTERLISTPDA, jo.toString(), GETPRINTERLISTPDA, callback);
    }

    //PDA获取所有打印方案
    public static void GetSchemeList(Context context, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        AsyncUtils.post(context, UserInfo.GETSCHEMELISTPDA, jo.toString(), GETSCHEMELISTPDA, callback);
    }

    //21.PDA打印条码 (StkScanByPack_PrintBar)
    public static void StkScanByPackPrintBar(Context context, String printername, String schmname, String barcode, JSONArray jr, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("printername", printername);
            jo.put("schmname", schmname);
            jo.put("barcode", barcode);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            jo.put("data", jr);
            AsyncUtils.post(context, UserInfo.STKSCANBYPACKPRINTBAR, jo.toString(), STKSCANBYPACKPRINTBAR, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查看未扫描条码
    public static void StkScanByPackNoScan(Context context, String oritype, String logistbatchno, JSONArray data, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("oritype", oritype);
            jo.put("logistbatchno", logistbatchno);
            jo.put("data", data);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.STKSCANBYPACKNOSCAN, jo.toString(), STKSCANBYPACKNOSCAN, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取打印条码数据

    /**
     * @param ifprnfailbar 是否合包失败的条码  获取合包失败的条码 传1 ，其他传0
     **/
    public static void GetPrintBarDataPda(Context context, String barcode, int ifprnfailbar, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("barcode", barcode);
            jo.put("ifprnfailbar", ifprnfailbar);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.GETPRINTBARDATAPDA, jo.toString(), GETPRINTBARDATAPDA, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过条码获取物流批次、车牌号和载物台等消息
     */
    public static void getConditionByBarCode(Context context, String barCode, String logistBatchNo, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        UserInfoBean userinfo = Hawk.get(UserInfo.CURRENTUSERINFO);
        if (userinfo == null) return;
        try {
            jo.put("barcode", barCode);
            jo.put("logistbatchno", logistBatchNo);
            jo.put("usercode", userinfo.getUsercode());
            jo.put("username", userinfo.getUsername());
            AsyncUtils.post(context, UserInfo.GETMATHINFOBYBARCODE, jo.toString(), GETMATHINFOBYBARCODE, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
