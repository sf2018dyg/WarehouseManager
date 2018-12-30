package com.soonfor.warehousemanager.http.api;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class UserInfo {
    /**
     * SP存储名
     */
    public static String UUID = "uuid";
    public static String SELECTSTORE = "SelectStore";//选择的仓库
    public static String ISAUTOLOGIN = "isAutoLogin";//是否自动登录
    public static String CURRENTUSERINFO = "currentUserInfo";//当前登录的用户信息
    public static String PASSWORD = "password";//密码

    public static String SP_MUSICMAP = "sp_musicmap";//设置界面扫描声音集合

    /**
     * 接口
     */
    public static String COMMONREQUEST = "/sfapi/Test?sf_action=";
    /**
     * 登陆
     */
    public static String TEST_SERVICER = "/sfapi/Test";
    /**
     * 1.验证服务器地址
     */
    public static String GET_USERINFO = "/sfapi/UserInfo/GetUserMsg";//2.获取用户信息(消息中心)
    public static String LOGIN = "/sfapi/UserInfo/LogIn";
    /***3.登录*/
    public static String LOGOUT = "/sfapi/UserInfo/Logout";//4.用户登出(logout)
    public static String GET_USERRIGHTINFO = "/sfapi/UserInfo/GetUserRightInfo";//5.获取用户对应模块的权限(返回字符串)
    public static String ISEXITSTSSPECIALRIGHT = "/sfapi/UserInfo/IsExistsSpecialRight";//6.判断用户是否有此模块的指定特殊权限(返回True或者False)
    public static String GET_STORES = "/sfapi/StkInfo/GetStkCodeList_PDA";//7.获取仓库列表
    public static String GET_PLACECODELISTPDA = "/sfapi/StkInfo/GetPlaceCodeList_PDA";//8.PDA获取所有储位列表
    public static String GET_DEPTCODELIST = "/sfapi/StkInfo/GetDeptCodeList_PDA";//9.PDA获取所有部门列表
    public static String GET_RECEIPTCODELISTPDA = "/sfapi/BaseDataInfo/GetReceiptCodeList_PDA";//10.PDA获取所有单据类型列表
    public static String GET_REASONCODELISTPDA = "/sfapi/BaseDataInfo/GetReasonCodeList_PDA";//11.PDA获取所有出入库原因列表
    public static String GET_LOGISTBATCHNOLISTPDA = "/sfapi/OrderInfo/GetLogistBatchNoList_PDA";//12.PDA获取所有物流批次列表();;
    public static String GET_CARCARDNOLISTPDA = "/sfapi/OrderInfo/GetCarcardNoList_PDA";//13.PDA获取车牌号&载货台列表()
    public static String GETORDERNOLISTPDA = "/sfapi/OrderInfo/GetOrderNoList_PDA";//PDA获取订单号列表
    public static String GETSTKINNOLISTPDA = "/sfapi/StkInfo/GetStkInNoList_PDA";//PDA获取入库单号
    public static String STKOUTBYPACKADDORDER = "/sfapi/StkPrepScan/StkPrepByPack_AddOrder";//追加订单
    public static String STKINBYPACKONCLOSE = "/sfapi/StkInfo/StkScanByPack_OnClose";//5)清除缓存
    public static String GETPRINTERLISTPDA = "/sfapi/BaseDataInfo/GetPrinterList_PDA";//PDA获取所有打印机
    public static String GETSCHEMELISTPDA = "/sfapi/BaseDataInfo/GetSchemeList_PDA";//PDA获取所有打印方案

    //-----------分包分拣
    public static String PACKSORTINGBYPACKPAGE = "/sfapi/PackSortingScan/PackSortingByPackPage";//1)获取数据页签
    public static String PACKSORTINGBYPACKBARITEM = "/sfapi/PackSortingScan/PackSortingByPack_BarItem";//2)条码明细页 & 订单齐套状况
    public static String PACKSORTINGBYPACKSAVABAR = "/sfapi/PackSortingScan/PackSortingByPack_SaveBar";//3)扫描确定
    public static String PACKSORTINGBYPACKDELBAR = "/sfapi/PackSortingScan/PackSortingByPack_DelBar";//4)扫描删除


    //-----------分包入库
    public static String STKINBYPACKSCANPDA = "/sfapi/StkInScan/StkInByPackScan_PDA";//14.分包入库扫描()
    public static String STKINBYPACKPAGE = "/sfapi/StkInScan/StkInByPackPage";//1)获取数据页签
    public static String BARITEM = "/sfapi/StkInScan/StkInByPack_BarItem";//4)条码明细页
    public static String STKINBYPACKSAVEBAR = "/sfapi/StkInScan/StkInByPack_SaveBar";//6)扫描确定
    public static String STKINBYPACKDELBAR = "/sfapi/StkInScan/StkInByPack_DelBar";//7)扫描删除


    //----------分包备货
    public static String STKPREPBYPACKPAGE = "/sfapi/StkPrepScan/StkPrepByPackPage";//1)获取数据页签
    public static String GETMATHINFOBYBARCODE = "/sfapi/StkPrepScan/StkPrepByPack_GetLogistBatchNo";//根据条码获取物流批次信息
    public static String STKPREPBYPACKBARITEM = "/sfapi/StkPrepScan/StkPrepByPack_BarItem";//2)条码明细 & 订单齐套状况
    public static String STKPREPBYPACKSAVEBAR = "/sfapi/StkPrepScan/StkPrepByPack_SaveBar";//5)扫描确定
    public static String STKPREPBYPACKDELBAR = "/sfapi/StkPrepScan/StkPrepByPack_DelBar";//6)扫描删除
    public static String STKPREPBYPACKGENSTKOUT = "/sfapi/StkPrepScan/StkPrepByPack_GenStkOut";//7)产生出货单


    //----------分包出货
    public static String STKPREPBYPACKPAGE_OUT = "/sfapi/StkOutScan/StkOutByPackPage";//1)获取数据页签
    public static String STKPREPBYPACKBARITEM_OUT = "/sfapi/StkOutScan/StkOutByPack_BarItem";//2)条码明细 & 订单齐套状况
    public static String STKPREPBYPACKSAVEBAR_OUT = "/sfapi/StkOutScan/StkOutByPack_SaveBar";//3)扫描确定
    public static String STKPREPBYPACKDELBAR_OUT = "/sfapi/StkOutScan/StkOutByPack_DelBar";//6)扫描删除
    public static String STKINBYPACKONCLOSE_OUT = "/sfapi/StkOutScan/StkOutByPack_OnClose";//5)清除缓存
    public static String STKPREPBYPACKGENSTKOUT_OUT = "/sfapi/StkOutScan/StkOutByPack_GenStkOut";//7)产生出货单
    public static String STKPREPBYPACKDLVNOLIST = "/sfapi/OrderInfo/GetDlvNoList_PDA";//获取出货通知单

    //分包入库退库扫描
    public static String STKINRTNSCANBYPACKPAGE = "/sfapi/StkInRtnScan/StkInRtnByPackPage";//1)获取数据页签
    public static String STKINRTNBYPACKBARITEM = "/sfapi/StkInRtnScan/StkInRtnByPack_BarItem";//2)条码明细页 & 订单齐套状况
    public static String STKINRTNSCANBYPACKSAVEBAR = "/sfapi/StkInRtnScan/StkInRtnByPack_SaveBar";//3)扫描确定
    public static String STKINRTNSCANBYPACKDELBAR = "/sfapi/StkInRtnScan/StkInRtnByPack_DelBar";//4)扫描删除


    //----------合包扫描
    public static String COMBINEBYPACKPAGE = "/sfapi/CombineScan/CombineByPackPage";//1)获取数据页签
    public static String COMBINEBYPACKBARITEM = "/sfapi/CombineScan/CombineByPack_BarItem";//2)条码明细页 & 货品明细页
    public static String GETSPSIDLISTPDA = "/sfapi/BaseDataInfo/GetSPSIDList_PDA";//12.PDA获取所有分包分类列表(GetSPSIDList_PDA)
    public static String COMBINEBYPACKSAVEBAR = "/sfapi/CombineScan/CombineByPack_SaveBar";//3)扫描确定
    public static String COMBINEBYPACKDELBAR = "/sfapi/CombineScan/CombineByPack_DelBar";//扫码删除


    //-----------查看未扫描条码(StkScanByPack_NoScan)
    public static String STKSCANBYPACKNOSCAN = "/sfapi/StkInfo/StkScanByPack_NoScan";//

    //--获取打印条码数据
    public static String GETPRINTBARDATAPDA = "/sfapi/BaseDataInfo/GetPrintBarData_PDA";
    public static String STKSCANBYPACKPRINTBAR = "/sfapi/BaseDataInfo/StkScanByPack_PrintBar";
}
