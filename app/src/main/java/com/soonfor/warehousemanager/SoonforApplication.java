package com.soonfor.warehousemanager;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.soonfor.warehousemanager.dao.DaoMaster;
import com.soonfor.warehousemanager.dao.DaoSession;
import com.soonfor.warehousemanager.module.instore.beans.ScanLogBean;
import com.soonfor.warehousemanager.module.outstore.beans.Out2BarItemBean;
import com.soonfor.warehousemanager.module.outstore.beans.OutScanLogBean;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.SimpleFormatter;

import cn.jesse.nativelogger.NLogger;
import cn.jesse.nativelogger.logger.LoggerLevel;
import cn.jesse.nativelogger.util.CrashWatcher;
import okhttp3.OkHttpClient;

/**
 * Created by ljc on 2018/1/11.
 */

public class SoonforApplication extends android.support.multidex.MultiDexApplication {

    public static DaoMaster.DevOpenHelper mHelper;
    public static SQLiteDatabase db;
    public static DaoMaster mDaoMaster;
    public static DaoSession mDaoSession;

    //入库
    public static List<ScanLogBean> inScanLogList;//入库扫描日志
    //出库
    public static Map<String, Out2BarItemBean> bar2Map;//解析成功的条码
    public static List<OutScanLogBean> outScanLogList;//出库扫描日志

    public static Context AppContext;
    public static String ServerAdr = "ServerAdr";
    public static OkHttpClient client = null;
    //static 代码段可以防止内存泄露
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                //layout.setPrimaryColorsId(R.color.bg_color, android.R.color.black);//全局设置主题颜色
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        Configuration config = new Configuration();
//        config.setToDefaults();
//        res.updateConfiguration(config, res.getDisplayMetrics());
//        return res;
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        if (newConfig.fontScale != 1)//非默认值
//            getResources();
//        super.onConfigurationChanged(newConfig);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();

        //路由注册
        ARouter.init(this);
        //本地保存
        Hawk.init(getApplicationContext()).build();
        client = new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        //日志
        String loggerpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WarehouseManager/logs";
        File dir = new File(loggerpath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        NLogger.getInstance()
                .builder()
                .tag("WarehouseManager")
                .loggerLevel(LoggerLevel.DEBUG)
                .fileLogger(true)
                .fileDirectory(loggerpath)
                .fileFormatter(new SimpleFormatter())
                .expiredPeriod(3)
                .catchException(true, new CrashWatcher.UncaughtExceptionListener() {
                    @Override
                    public void uncaughtException(Thread thread, Throwable ex) {
                        NLogger.e("uncaughtException", ex);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .build();
        //初始化本地数据库
        setDatabase();
    }

    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "sport-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
}
