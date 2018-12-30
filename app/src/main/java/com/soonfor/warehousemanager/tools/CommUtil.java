package com.soonfor.warehousemanager.tools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * Created by ljc on 2018/1/16.
 */

public class CommUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否内网
     * 10.0.0.0~10.255.255.255
     * 172.16.0.0~172.31.255.255
     * 192.168.0.0~192.168.255.255
     **/
    public static boolean isInner(String ip) {
        String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        return matcher.find();
    }

    public static String formatSecond(int second) {
        int result = second / 60;
        if (result > 0) {
            return String.format("%d'%d\'", result, second % 60);
        } else {
            return second + "\"";
        }
    }


    public static String formatStr(String string) {
        if (string == null || string.equals("null")) {
            return "";
        } else {
            return string;
        }
    }
    /**
     * 获取VersionName
     * @param context
     * @return pi.versionName
     */
    public static int getVersionNCode(Context context){
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(pi==null){
            return 1000;
        }
        return pi.versionCode;
    }
    /**
     * 获取VersionName
     * @param context
     * @return pi.versionName
     */
    public static String getVersionName(Context context){
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(pi==null){
            return "";
        }
        return pi.versionName;
    }
    public static void setRightDrawable(Context context, TextView textView, String text, int drawableId) {
        textView.setText(text);
        Drawable drawable = context.getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, 23, 13);
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * @param mContext      上下文
     * @param TLength       标题的长度
     * @param b_count       最后一列多少个按钮
     * @param isFirstButton 第一列是否为按钮
     * @return
     */
    public static int[] SetComWidth(Context mContext, int TLength, int b_count, boolean isFirstButton) {
        int[] tableWith = new int[TLength];
        int operationWidth = 0;
        operationWidth = CommUtil.dip2px(mContext, 100);//一个按钮所占的父窗体宽度

        int ScrrentWith = getScreenWidth(mContext);
        for (int i = 0; i < tableWith.length; i++) {
            if (isFirstButton) {
                if (i == 0) {
                    tableWith[i] = CommUtil.dip2px(mContext, 50);
                } else {
                    int www = (ScrrentWith - tableWith[0] - operationWidth * b_count) / (TLength - 1);
                    tableWith[i] = www < 120 ? 120 : www;
                }
            } else {
                int www = (ScrrentWith - operationWidth * b_count) / (TLength - 1);
                tableWith[i] = www < 120 ? 120 : www;
            }

        }
        /*int ScrrentWith = getScreenWidth(mContext);
        int row = ScrrentWith / 12;//固定一屏最多显示12列
        if (TLength > 11) {
            for (int i = 0; i < TLength; i++) {
                if (i == TLength - 1) {
                    tableWith[i] = row * b_count;
                } else {
                    tableWith[i] = row;
                }
            }
        } else {
            for (int i = 0; i < tableWith.length; i++) {
                if (isFirstButton) {
                    if (i == 0) {
                        tableWith[i] = operationWidth;
                    } else if (i == tableWith.length - 1) {
                        tableWith[i] = operationWidth * b_count;
                    } else {
                        tableWith[i] = (ScrrentWith - operationWidth * (b_count + 1)) / (TLength - 2);
                    }
                } else {
                    if (i == tableWith.length - 1) {
                        tableWith[i] = operationWidth * b_count;
                    } else {
                        tableWith[i] = (ScrrentWith - operationWidth * b_count) / (TLength - 1);
                    }
                }

            }
        }*/
        return tableWith;
    }

    public static TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable edt) {
            try {
                String temp = edt.toString();
                String tem = temp.substring(temp.length() - 1, temp.length());
                char[] temC = tem.toCharArray();
                int mid = temC[0];
                if (mid >= 48 && mid <= 57) {//数字
                    return;
                }
                if (mid >= 65 && mid <= 90) {//大写字母
                    return;
                }
                if (mid > 97 && mid <= 122) {//小写字母
                    return;
                }
                edt.delete(temp.length() - 1, temp.length());
            } catch (Exception e) {
            }
        }
    };

    public static String formatInteger(String value){
        if(value == null || value.equals("")){
            return "0";
        }else{
            return value;
        }
    }

    public static int formatStrToInteger(String value){
        if(value == null){
            return 0;
        }else{
            return Integer.parseInt(value);
        }
    }
    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    // 判断一个字符串是否含有中文
    public static boolean isChinese(String str) {
        if (str == null)
            return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c))
                return true;// 有一个中文字符就返回
        }
        return false;
    }


}
