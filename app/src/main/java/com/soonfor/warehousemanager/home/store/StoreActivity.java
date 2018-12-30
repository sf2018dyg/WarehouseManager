package com.soonfor.warehousemanager.home.store;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.home.login.activity.LoginActivity;
import com.soonfor.warehousemanager.home.login.activity.ServerSettingsActivity;
import com.soonfor.warehousemanager.home.login.bean.UserInfoBean;
import com.soonfor.warehousemanager.home.main.MainActivity;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.module.instore.beans.InStoreConditionBean;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.Tokens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-DingYG on 2018-07-23 13:38
 * 邮箱：dingyg012655@126.com
 */
public class StoreActivity extends BaseActivity<StorePresenter> implements IStoreView {

    StoreActivity mActivity;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    SelectStoreAdapter ssAdapter;
    public static List<StoreBean> sbList;
    public static StoreBean storeBean;//选中的仓库
    private boolean isFromMain = false;
    private UserInfoBean user;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_selectstore;
    }

    @Override
    protected void initPresenter() {
        presenter = new StorePresenter(this);
        mActivity = StoreActivity.this;
        user = Hawk.get(UserInfo.CURRENTUSERINFO, null);
        try {
            isFromMain = getIntent().getExtras().getBoolean("ISFROMMAIN", false);
        }catch (Exception e){}
        mSwipeRefresh.setEnableLoadmore(false);
    }

    @OnClick({R.id.tvfReset, R.id.tvfNextStep})
    void ThisOnClick(View view) {
        switch (view.getId()) {
            case R.id.tvfReset:
                startNewAct(ServerSettingsActivity.class);
                finish();
                break;
            case R.id.tvfNextStep:
                if(storeBean!=null) {
                    Hawk.put(UserInfo.SELECTSTORE, storeBean);
                    //清掉入（出）库条件中的储位
                    Map<String, InStoreConditionBean> inSotreCondMap = Hawk.get(Tokens.Putint.PUTIN_CONDITONS, null);
                    if(inSotreCondMap!=null && inSotreCondMap.size()>0){
                        Map<String, InStoreConditionBean> newMap = new HashMap<>();
                        for(Map.Entry<String, InStoreConditionBean> map : inSotreCondMap.entrySet()){
                            InStoreConditionBean bean = map.getValue();
                            bean.setFbinlocationCode("");
                            bean.setFbinlocationName("");
                            newMap.put(map.getKey(), bean);
                        }
                        Hawk.put(Tokens.Putint.PUTIN_CONDITONS, newMap);
                    }
                    if (isFromMain) {
                        startNewAct(MainActivity.class);
                    }else {
                        startNewAct(LoginActivity.class);
                    }
                    finish();
                }else{
                    MyToast.showToast(mActivity, "请选择仓库！");
                }
                break;
        }
    }

    @Override
    protected void initViews() {
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        storeBean = Hawk.get(UserInfo.SELECTSTORE, null);
        ssAdapter = new SelectStoreAdapter(mActivity, mLayoutManager, mRecyclerView, listener);
        // 设置adapter
        mRecyclerView.setAdapter(ssAdapter);
        presenter.getData(mActivity, true);
    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        presenter.getData(mActivity, false);
    }
    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);
        closeLoadingDialog();
        emptyView.hide();
        mRecyclerView.setVisibility(View.VISIBLE);
        ssAdapter.notifyDataSetChanged(sbList);
    }

    @Override
    public void showNoDataHint(String msg) {
        super.showNoDataHint(msg);
        closeLoadingDialog();
        mRecyclerView.setVisibility(View.GONE);
        emptyView.show(msg, "");
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            storeBean = sbList.get(position);
            ssAdapter.notifyDataSetChanged();
        }
    };
    // 返回home
    long flag= -1;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {
            if (flag == -1||System.currentTimeMillis() - flag >2000) {
                Toast.makeText(this, "再点击一次退出App", Toast.LENGTH_SHORT).show();
                flag = System.currentTimeMillis();

            } else if (System.currentTimeMillis() - flag < 2000) {
                /*Intent exit = new Intent(Intent.ACTION_MAIN);
                exit.addCategory(Intent.CATEGORY_HOME);
                exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(exit);
                System.exit(0);*/
                finish();
            }
        }
        return true;
    }
}
