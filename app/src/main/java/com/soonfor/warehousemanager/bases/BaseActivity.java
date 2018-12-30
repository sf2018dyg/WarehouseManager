package com.soonfor.warehousemanager.bases;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.tu.loadingdialog.LoadingDailog;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.tools.CommonApp;
import com.soonfor.warehousemanager.tools.SwipeRefreshHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/5 0005.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    /**
     * 刷新控件，注意，资源的ID一定要一样
     */
    @Nullable
    @BindView(R.id.swipe_refresh)
    protected SmartRefreshLayout mSwipeRefresh;

    @Nullable
    @BindView(R.id.emptyView)
    public QMUIEmptyView emptyView;

    QMUITipDialog tipDialog = null;

    /**
     * 控制器
     */
    public T presenter;


    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @LayoutRes
    protected abstract int attachLayoutRes();


    /**
     * 初始化presenter
     */
    protected abstract void initPresenter();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    public LoadingDailog mLoadingDialog;
    protected String actionName = "加载中...";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        CommonApp.getInstance().addActivity(this);
        setContentView(attachLayoutRes());
        if (mLoadingDialog == null) {
            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                    .setMessage(actionName)
                    .setCancelable(true)
                    .setCancelOutside(true);
            mLoadingDialog = loadBuilder.create();
        }
        ButterKnife.bind(this);
        //StatusBarUtil.setColor(this, getResources().getColor(R.color.black));
        initPresenter();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
            try {
                toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }catch (Exception e){}

        }
        initViews();
        initSwipeRefresh();
    }

//    public void localImageLoading(String imagePath, ImageView view, int defaultResId) {
//        if (imagePath == null || imagePath.equals("")) {
//            view.setImageResource(defaultResId);
//        } else {
//            File file = new File(imagePath);
//            Picasso.with(this).load(file)
//                    .fit()
//                    .placeholder(defaultResId)
//                    .error(defaultResId)
//                    .centerCrop()
//                    .into(view);
//        }
//    }

    /**
     * startNewActivity Description: 意图，界面跳转
     *
     * @param targetActClass 转入的活动类
     */
    public void startNewAct(Class<?> targetActClass) {
        startNewAct(targetActClass, null);
    }

    public void startNewAct(Class<?> targetActClass, boolean isfinished) {
        startNewAct(targetActClass, null);
        if (isfinished) {
            finish();
        }
    }

    public void startNewAct(Class<?> targetActClass, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(this, targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }

    public void startNewAct(Class<?> targetActClass, Bundle data, boolean isfinsished) {
        Intent intent = new Intent();
        intent.setClass(this, targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
        if (isfinsished) {
            finish();
        }
    }

    public void startNewAct(Class<?> targetActClass, Bundle data, int resultCode) {
        Intent intent = new Intent();
        intent.setClass(this, targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, resultCode);
    }

    /**
     * 修改人：DC-ZhuSuiBo on 2018/3/20 0020 18:03
     * 邮箱：suibozhu@139.com
     */
    public void startNewAct(Activity activity, Class<?> targetActClass, Bundle data) {
        Intent intent = new Intent(activity, targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }

    /**
     * 修改人：DC-ZhuSuiBo on 2018/3/20 0020 18:03
     * 邮箱：suibozhu@139.com
     */
    public void startNewAct(Activity activity, Class<?> targetActClass, Bundle data, int resultCode) {
        Intent intent = new Intent(activity, targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, resultCode);
    }

    public void startNewAct(Class<?> targetActClass, int resultCode) {
        Intent intent = new Intent();
        intent.setClass(this, targetActClass);
        startActivityForResult(intent, resultCode);
    }

    /**
     * -----------------------------    点击空白处隐藏软键盘   --------------------------------------------------------------------------
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void RefreshData(boolean isRefresh) {

    }

    public void showLoading() {
        SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
    }

    public void hideLoading() {
        SwipeRefreshHelper.enableRefresh(mSwipeRefresh, true);
        SwipeRefreshHelper.controlRefresh(mSwipeRefresh, false);

    }

    public void showNetError() {
        SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
    }


    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (mSwipeRefresh != null) {
            //设置 Header 为 Material风格
            // mSwipeRefresh.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(false).setColorSchemeColors(getResources().getColor(R.color.bartextcolor)));
            SwipeRefreshHelper.init(mSwipeRefresh, new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    RefreshData(true);
                }
            });
            mSwipeRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                    loadmoredata();
                }
            });
        }
    }

    protected void loadmoredata() {

    }

    @Override
    public void finishRefresh() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.finishRefresh();
            mSwipeRefresh.finishLoadmore();
        }
    }

    /**
     * 请求有数据时刷新界面
     */
    @Override
    public void showDataToView(String returnJson) {
        finishRefresh();
    }

    /**
     * 请求无数据时显示错误提示
     */
    @Override
    public void showNoDataHint(String msg) {
        finishRefresh();
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void closeLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    public void showQMTipLoading(String hint, int iconType, boolean isCanCelable) {
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(iconType)
                    .setTipWord(hint)
                    .create();
            tipDialog.setCancelable(isCanCelable);
            tipDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (tipDialog != null) {
                        //Toast.show(getApplication(),"请求超时",Toast.LENGTH_SHORT);
                        tipDialog.dismiss();
                        tipDialog = null;
                    }
                }
            }, 60000);
        }
    }
    public void showQMTipLoading(String hint, int iconType) {
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(iconType)
                    .setTipWord(hint)
                    .create();
            tipDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (tipDialog != null) {
                        //Toast.show(getApplication(),"请求超时",Toast.LENGTH_SHORT);
                        tipDialog.dismiss();
                        tipDialog = null;
                    }
                }
            }, 60000);
        }
    }

    public void hideQMTipLoading() {
        if (tipDialog != null) {
            tipDialog.dismiss();
            tipDialog = null;
        }
    }


    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) { // 监听home键
                String reason = intent.getStringExtra(SYSTEM_REASON);

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // unregisterReceiver(mHomeKeyEventReceiver);
    }
}

