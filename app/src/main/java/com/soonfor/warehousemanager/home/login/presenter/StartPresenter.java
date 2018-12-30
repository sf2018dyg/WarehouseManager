package com.soonfor.warehousemanager.home.login.presenter;

import android.app.Activity;
import android.os.Handler;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.bases.HeadBean;
import com.soonfor.warehousemanager.home.login.activity.StartActivity;
import com.soonfor.warehousemanager.home.login.bean.UserInfoBean;
import com.soonfor.warehousemanager.home.login.view.IStartView;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.tools.Base64Utils;
import com.soonfor.warehousemanager.tools.JsonUtils;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.RSAUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

import cn.jesse.nativelogger.NLogger;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class StartPresenter extends BasePresenter<IStartView> implements AsyncUtils.AsyncCallback {

    private Activity mActivity;
    private IStartView view;

    public StartPresenter(Activity mActivity, IStartView view) {
        this.mActivity = mActivity;
        this.view = view;
    }

    public void Login(String userName, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        String fullUrl = "http://" + Hawk.get(SoonforApplication.ServerAdr) + UserInfo.LOGIN;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jo = new JSONObject();
        try {
            jo.put("usercode", userName);
            jo.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jo.toString());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(fullUrl)
                .post(body)
                .build();
        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                NLogger.e("自动登录失败：" + e.getMessage());
                view.FailureLogin();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    HeadBean bean = JsonUtils.getHeadBean(result);
                    if (bean != null && bean.getStatus() == 200) {
                        view.successLogin();
                    }else {
                        view.FailureLogin();
                    }
                } else {
                    if (response.code() == 500) {
                        NLogger.e("自动登录失败：", "服务器内部错误（500）");
                    }
                    view.FailureLogin();
                }
            }
        });
    }

    @Override
    public void success(int requestCode, String data) {
        Hawk.put(UserInfo.ISAUTOLOGIN, true);
        view.successLogin();
    }

    @Override
    public void fail(int requestCode, int statusCode, String data, String msg) {
        view.FailureLogin();
    }
}
