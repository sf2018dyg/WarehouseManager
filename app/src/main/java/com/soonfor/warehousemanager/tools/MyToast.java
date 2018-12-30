package com.soonfor.warehousemanager.tools;

import android.content.Context;
import android.widget.Toast;

import com.soonfor.warehousemanager.R;


/**
 * Created by Administrator on 2017/8/17 0017.
 */
/**
 * 修改人：DC-ZhuSuiBo on 2018/2/2 10:22
 * 邮箱：suibozhu@139.com
 */
public class MyToast {
    public static void showToast(Context context, CharSequence text) {
        Toast.makeText(context,  text, Toast.LENGTH_SHORT).show();
    }

    public static void showCaveatToast(Context context, CharSequence text) {
        Toast.makeText(context,  text, Toast.LENGTH_SHORT).show();
    }

    public static void showFailToast(Context context, CharSequence text) {
        Toast.makeText(context,  text, Toast.LENGTH_SHORT).show();
    }

}
