package com.soonfor.warehousemanager.tools;

import android.app.Activity;

/**
 * Created by Administrator on 2017-10-12.
 */

public class ActivityUtils {
    //检测Activity在运行
    public static boolean isRunning(Activity activity){
        if(activity==null||activity.isDestroyed()||activity.isFinishing()){
            return false;
        }else{
            return true;
        }
    }
}
