package com.soonfor.warehousemanager.home.login.activity;

import android.os.Handler;

import com.orhanobut.hawk.Hawk;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.home.login.bean.UserInfoBean;
import com.soonfor.warehousemanager.home.login.presenter.StartPresenter;
import com.soonfor.warehousemanager.home.login.view.IStartView;
import com.soonfor.warehousemanager.home.main.MainActivity;
import com.soonfor.warehousemanager.home.store.StoreActivity;
import com.soonfor.warehousemanager.home.store.StoreBean;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.tools.SoundUtil;

public class StartActivity extends BaseActivity<StartPresenter> implements IStartView {

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_start;
    }

    @Override
    protected void initPresenter() {
        presenter = new StartPresenter(StartActivity.this, this);
    }

    @Override
    protected void initViews() {
        showLoadingDialog();
        try {
            if(Hawk.get(UserInfo.SP_MUSICMAP, null) !=null) {
                SoundUtil soundUtil = SoundUtil.getInstense(StartActivity.this);
                soundUtil.getWavFormRaw();
                soundUtil.initSound();
            }
            if (Hawk.get(SoonforApplication.ServerAdr, "").equals("")) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeLoadingDialog();
                        startNewAct(LoginActivity.class);
                        finish();
                    }
                }, 500);
            } else {
                UserInfoBean userInfo = Hawk.get(UserInfo.CURRENTUSERINFO, null);
                String pdw = Hawk.get(UserInfo.PASSWORD, "");
                if (Hawk.get(UserInfo.ISAUTOLOGIN, false) && userInfo != null && !userInfo.getUsercode().equals("")) {
                    presenter.Login(userInfo.getUsercode(), pdw);
                } else {
                    closeLoadingDialog();
                    startNewAct(LoginActivity.class);
                    finish();
                }
            }
        }catch (Exception e){}
    }

    @Override
    public void successLogin() {
        Hawk.put(UserInfo.ISAUTOLOGIN, true);
        StoreBean selectStore = Hawk.get(UserInfo.SELECTSTORE, null);
        closeLoadingDialog();
        if (selectStore == null) {
            startNewAct(StoreActivity.class);
        } else {
            startNewAct(MainActivity.class);
        }
        finish();
    }

    @Override
    public void FailureLogin() {
        closeLoadingDialog();
        startNewAct(LoginActivity.class);
        finish();
    }
}