package com.soonfor.warehousemanager.tools;

/**
 * Created by Administrator on 2018-02-02.
 */

public class Tokens {

    public static class PlayerMsg{
        public static final int PLAY_MSG = 110;
        public static final int PAUSE_MSG = 119;
        public static final int STOP_MSG = 120;
        public static final int PRIVIOUS_MSG = 109;
        public static final int CONTINUE_MSG = 111;
        public static final int NEXT_MSG = 121;
        public static final int MUSIC_CURRENT = 122;
    }

    public static class StoreType{
        public static final String PUTIN = "putin";
        public static final String PUTOUT = "putout";
    }

    public static class Putint{
        public static final String PUTIN_CONDITONS = "putin_conditions";//入库的条件集合

        public static final int JUMPCODE_SETOUTCONDITION_BYFIRSTSCAN = 201;// 界面跳转 通过第一次扫码而调起的条件设置
        public static final int JUMPCODE_SETOUTCONDITION = 302;// 界面跳转 去往设置入库条件

    }

    public static class Putout{
        public static final String PUTOUT_CONDITONS = "putout_conditions";//出库的条件集合

        public static final int JUMPCODE_SCANFORLOGISTICS = 300;// 界面跳转 通过扫描获取物流
        public static final int JUMPCODE_SETOUTCONDITION_BYFIRSTSCAN = 301;// 界面跳转 通过第一次扫码而调起的条件设置
        public static final int JUMPCODE_SETOUTCONDITION = 302;// 界面跳转 去往设置出库条件
        public static final int JUMPCODE_LOGISTICSBATCHS = 303;// 界面跳转 选择物流批次
        public static final int JUMPCODE_PLATENUMBER = 304;// 界面跳转 选择车牌
        public static final int JUMPCODE_ADDOORDERS = 305;// 界面跳转 追加订单
        public static final int JUMPCODE_SHIPPINGORDER = 306;//界面跳转 选择出库通知单
    }
}
