package com.soonfor.warehousemanager.http.httptools;

import android.content.Context;
import android.os.Handler;

import com.orhanobut.hawk.Hawk;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.HeadBean;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.JsonUtils;
import com.soonfor.warehousemanager.tools.NetUtils;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.jesse.nativelogger.NLogger;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AsyncUtils {

    private static AsyncCallback callback = null;

    public void setAsyncListerner(AsyncCallback callback) {
        this.callback = callback;
    }

    public interface AsyncCallback {
        void success(int requestCode, String data);

        void fail(int requestCode, int statusCode, String data, String msg);
    }

    /**
     * 异步请求 post
     *
     * @param context
     * @param url
     * @param jsonParams
     * @param requestCode
     * @param callback
     */
    public static void post(final Context context, String url, String jsonParams, final int requestCode,
                            final AsyncCallback callback) {
        if (!NetUtils.isConnectInternet(context)) {
            NetUtils.IfNetOff_OpenSetUI(context);
            String emsg = "您的WLAN和移动网络均未连接！";
            callback.fail(requestCode, -1, "", emsg);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //SoonforApplication.apiTime = System.currentTimeMillis();
                    if (SoonforApplication.client == null) {
                        SoonforApplication.client = new OkHttpClient.Builder()
                                .writeTimeout(20, TimeUnit.SECONDS)
                                .readTimeout(20, TimeUnit.SECONDS)
                                .connectTimeout(20, TimeUnit.SECONDS)
                                .build();
                    }
                    String fullUrl = "http://" + Hawk.get(SoonforApplication.ServerAdr) + url;
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, jsonParams);
                    Request request = new Request.Builder()
                            .url(fullUrl)
                            .post(body)
                            .build();
                    final Call call = SoonforApplication.client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            new Handler(context.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    String result = e.getMessage();
                                    if (result == null || result.equals("")) {
                                        callback.fail(requestCode, -1, result, "网络错误");
                                    } else {
                                        callback.fail(requestCode, -1, result, getFailure(fullUrl, result));
                                    }
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if(response.code()==200){
                                String result = response.body().string();
                                HeadBean bean = JsonUtils.getHeadBean(result);
                                new Handler(context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 在这里执行你要想的操作 比如直接在这里更新ui或者调用回调在 在回调中更新ui
                                        if (bean != null) {
                                            if (bean.getStatus() == 200) {
                                                callback.success(requestCode, bean.getData());
                                            } else {
                                                if (bean.getErrorMessage() == null || bean.getErrorMessage().equals("")) {
                                                    callback.fail(requestCode, bean.getStatus(), bean.getData(), "网络错误或接口异常");
                                                } else {
                                                    callback.fail(requestCode, bean.getStatus(), bean.getData(), bean.getErrorMessage());
                                                    LogError(fullUrl, result);
                                                }
                                            }
                                        } else {
                                            callback.fail(requestCode, -1, "", "请求出错");
                                            LogError(fullUrl, result);
                                        }
                                    }
                                });
                            }else if(response.code()==500){
                                callback.fail(requestCode, -1, "", "服务器内部错误（500）");
                            }
                        }
                    });
                }
            }).start();
        }
    }

    /**
     * 异步请求 get
     *
     * @param context
     * @param url
     * @param requestCode
     * @param callback
     */
    public static void get(final Context context, String url, final int requestCode,
                           final AsyncCallback callback) {
        if (!NetUtils.isConnectInternet(context)) {
            NetUtils.IfNetOff_OpenSetUI(context);
            String emsg = "您的WLAN和移动网络均未连接！";
            callback.fail(requestCode, -1, "", emsg);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (SoonforApplication.client == null) {
                        SoonforApplication.client = new OkHttpClient.Builder()
                                .writeTimeout(20, TimeUnit.SECONDS)
                                .readTimeout(20, TimeUnit.SECONDS)
                                .connectTimeout(20, TimeUnit.SECONDS)
                                .build();
                    }
                    String fullUrl = "http://" + Hawk.get(SoonforApplication.ServerAdr) + url;
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(fullUrl)
                            .get()
                            .build();
                    final Call call = SoonforApplication.client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            new Handler(context.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    String result = e.getMessage();
                                    if (result == null || request.equals("")) {
                                        callback.fail(requestCode, -1, result, "网络错误");
                                    } else {
                                        callback.fail(requestCode, -1, result, getFailure(fullUrl, result));
                                    }
                                }
                            });

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if(response.code()==200) {
                                String result = response.body().string();
                                HeadBean bean = JsonUtils.getHeadBean(result);
                                new Handler(context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 在这里执行你要想的操作 比如直接在这里更新ui或者调用回调在 在回调中更新ui
                                        if (bean != null) {
                                            if (bean.getStatus() == 200) {
                                                callback.success(requestCode, bean.getData());
                                            } else {
                                                if (bean.getErrorMessage() == null || bean.getErrorMessage().equals("")) {
                                                    callback.fail(requestCode, bean.getStatus(), bean.getData(), "网络错误或接口异常");
                                                } else {
                                                    callback.fail(requestCode, bean.getStatus(), bean.getData(), bean.getErrorMessage());
                                                    LogError(fullUrl, result);
                                                }
                                            }
                                        } else {
                                            callback.fail(requestCode, -1, "", "请求出错");
                                            LogError(fullUrl, result);
                                        }
                                    }
                                });
                            }else if(response.code()==500){
                                callback.fail(requestCode, -1, "", "服务器内部错误（500）");
                            }
                        }
                    });
                }
            }).start();
        }
    }

    /**
     * 同步请求数据
     *
     * @param context
     * @param requestMethod 请求方式 0 Get 1 Post
     * @param url
     * @param jsonParams    post请求参数
     */
    public static String resultByOkhttp(final Context context, int requestMethod, String url, final String jsonParams) {
        String result = null;
        String fullUrl = "http://" + Hawk.get(SoonforApplication.ServerAdr) + url;
        okhttp3.Request request = null;
        if (requestMethod == 0) {
            request = new okhttp3.Request.Builder()
                    .url(fullUrl)
                    .get()
                    .build();
        } else {
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonParams);
            request = new okhttp3.Request.Builder()
                    .url(fullUrl)
                    .post(body)
                    .build();
        }
        final Call call = SoonforApplication.client.newCall(request);
        try {
            HeadBean bean = JsonUtils.getHeadBean(call.execute().body().string());
            if (bean != null) {
                if (bean.getStatus() == 200) {
                    result = bean.getData();
                } else {
                    if (bean.getErrorMessage() != null && !bean.getErrorMessage().equals("")) {
                        result = bean.getErrorMessage();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getFailure(String url, String errmsg) {
        String e_msg = "";
        if (errmsg.contains("TimeoutException"))
            e_msg = "连接服务器超时(30s)";
        else if (errmsg.contains("ConnectException") || errmsg.contains("404")) {
            if (errmsg.contains(":")) {
                e_msg = "无法连接服务器\n"
                        + errmsg.substring(errmsg.indexOf(":") + 1, errmsg.length());
            } else
                e_msg = "无法连接服务器";
        } else if (errmsg.contains("Proxy Error"))
            e_msg = "网络错误";
        else if (!CommUtil.isChinese(errmsg))
            e_msg = "网络异常, 请检查网络";
        else
            e_msg = errmsg;
        LogError(url, errmsg);
        return e_msg;
    }

    private static void LogError(String url, String errmsg) {
        //异步中更新UI
//        new Handler(context.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                // 在这里执行你要想的操作 比如直接在这里更新ui或者调用回调在 在回调中更新ui
        NLogger.e(url, errmsg);
//            }});
    }

}
