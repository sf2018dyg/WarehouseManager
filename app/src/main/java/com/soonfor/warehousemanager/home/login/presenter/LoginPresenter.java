package com.soonfor.warehousemanager.home.login.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.home.login.bean.UserInfoBean;
import com.soonfor.warehousemanager.home.login.view.ILoginView;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.tools.MyToast;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class LoginPresenter extends BasePresenter<ILoginView> implements AsyncUtils.AsyncCallback {

    private ILoginView view;
    private String userName;
    private String password;
    private String SerAddress = "";

    public LoginPresenter(ILoginView view) {
        this.view = view;
//        if(Hawk.get(UserInfo.ISREMEMBER, false)){
//            view.isRemember(Hawk.get(UserInfo.ISREMEMBER, false), Hawk.get(UserInfo.PASSWORD, ""));
//        }
    }

    /*public void Test(Context context, String address) {
        view.showLoadingDialog();
        if (address.contains("http://")) {
            SerAddress = address.replace("http://", "");
        } else {
            SerAddress = address;
        }
        if(Hawk.get(SoonforApplication.ServerAdr, "")!=null){
            if(!Hawk.get(SoonforApplication.ServerAdr, "").equals(address)){
                Hawk.put(UserInfo.SELECTSTORE, null);
                Hawk.put(UserInfo.ISREMEMBER, false);
                Hawk.put(UserInfo.ISAUTOLOGIN, false);
                Hawk.put(UserInfo.PASSWORD, "");
            }
        }
        Hawk.put(SoonforApplication.ServerAdr, address);

        Request.Test(context, this);
    }*/

    public void Login(Context context, String loginUsername, String loginPassword) {
        if (Hawk.get(SoonforApplication.ServerAdr, "").equals("")) {
            MyToast.showToast(context, "请设置服务器地址！");
            return;
        }
        if (Hawk.get(UserInfo.SELECTSTORE, "") == null) {
            MyToast.showToast(context, "请选择仓库");
            return;
        }
        userName = loginUsername;
        password = loginPassword;
        if (userName.isEmpty()) {
            MyToast.showCaveatToast(context, "请输入用户名");
            return;
        }
        view.showLoadingDialog();
        Request.sendLogin(context, userName, password, this);
    }


    @Override
    public void success(int requestCode, String data) {
        final Gson gson = new Gson();
        switch (requestCode) {
            case Request.TEST:
                view.saveSerAddress(SerAddress);
                break;
            case Request.LOGIN:
                UserInfoBean user = gson.fromJson(data, UserInfoBean.class);
                Hawk.put(UserInfo.CURRENTUSERINFO, user);
                Hawk.put(UserInfo.PASSWORD, password==null?"":password);
                Hawk.put(UserInfo.ISAUTOLOGIN, true);
                view.successLogin();
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode,String data, String msg) {
        switch (requestCode) {
            case Request.TEST:
                NLogger.w("test", msg);
                view.showNoDataHint("服务器验证失败！");
                break;
            case Request.LOGIN:
                NLogger.w("login", msg);
                if(msg==null || msg.equals(""))
                    view.showNoDataHint("登录失败！");
                else
                    view.showNoDataHint(msg);
                break;
        }
        view.closeLoadingDialog();
    }

}
