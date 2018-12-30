package com.soonfor.warehousemanager.home.main;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.orhanobut.hawk.Hawk;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.home.login.activity.LoginActivity;
import com.soonfor.warehousemanager.home.setting.SettingActivity;
import com.soonfor.warehousemanager.home.store.StoreActivity;
import com.soonfor.warehousemanager.home.store.StoreBean;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.module.print.PrinterActivity;
import com.soonfor.warehousemanager.module.instore.flowtype.FlowTypeBean;
import com.soonfor.warehousemanager.module.instore.flowtype.FlowTypeActivity;
import com.soonfor.warehousemanager.tools.ActivityUtils;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.CommonApp;
import com.soonfor.warehousemanager.tools.CustomGlridView;
import com.soonfor.warehousemanager.tools.FileUtils;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.SoundUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-DingYG on 2018-07-23 18:18
 * 邮箱：dingyg012655@126.com
 */

/**
 * 修改人：DC-ZhuSuiBo on 2018/8/8 0008 14:30
 * 邮箱：suibozhu@139.com
 * 修改目的：
 */
public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {

    private MainActivity mActivity;
    @BindView(R.id.tvfStore)
    TextView tvfStore;
    @BindView(R.id.rlfSetting)
    RelativeLayout rlfSetting;
    @BindView(R.id.gvbody)
    CustomGlridView glridView;
    @BindView(R.id.tvfVersion)
    TextView tvfVersion;
    GridViewAdapter gridAdapter;
    StoreBean selectStore;//仓库
    //内网更新
    private com.dingyg.updateutil.IntranetUpdateManager intUpdateManager;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        presenter = new MainPresenter(this);
        mActivity = MainActivity.this;
        selectStore = Hawk.get(UserInfo.SELECTSTORE, null);
        if (selectStore != null) {
            tvfStore.setText(selectStore.getfStkName());
        }
    }

    @Override
    protected void initViews() {
        try {
            presenter.getGridItemDatas();
            String url[] = new String[]{Hawk.get(SoonforApplication.ServerAdr, "") + "/sfapi/UPDate/GetVersions?path=UPdate&fileName=version.txt",
                    Hawk.get(SoonforApplication.ServerAdr, "") + "/sfapi/UPDate/DownLoadFile?path=UPdate&fileName="};
            //内网 WarehousetManager.apk
            intUpdateManager = new com.dingyg.updateutil.IntranetUpdateManager(this,
                    url,
                    "WarehouseManager","com.soonfor.warehousemanager.myfileprovider");
            new Thread(checkSelfUpdateInt).start();
            tvfVersion.setText(CommUtil.getVersionName(mActivity));
        }catch (Exception e){}
    }

    @Override
    public void showGrildView(List<GridItemBean> gibList) {
        gridAdapter = new GridViewAdapter(mActivity, gibList);
        glridView.setAdapter(gridAdapter);
        glridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectStore == null) {
                    MyToast.showToast(mActivity, "请选择仓库！");
                    return;
                }
                GridItemBean item = gibList.get(position);
                Class tarClass = item.getM_class();
                switch (position) {
                    case 0://入库
                        presenter.getPuInTypes(mActivity, "", selectStore.getfStkCode());
                        break;
                    case 1://出库
                        presenter.getPuOutTypes(mActivity, "", selectStore.getfStkCode());
                        break;
                    case 2://打印
                        startNewAct(PrinterActivity.class);
                        break;
                    /*case 2://调拨
                        startNewAct(TiaoboMainActivity.class);
                        break;
                    case 3://扫码查询
                        startNewAct(BarCodeSearchActivity.class);
                        break;*/
                }
            }
        });
    }

    @Override
    public void setGetTypes(String button_type, ArrayList<FlowTypeBean> stList) {
        Bundle bundle = new Bundle();
        bundle.putString("BUTTON_TYPE", button_type);
        bundle.putParcelableArrayList("STORETYPES", stList);
        startNewAct(FlowTypeActivity.class, bundle);
    }

    @Override
    public void setLogout(boolean isSuccess) {
        if (isSuccess) {
            finish();
            Hawk.delete(UserInfo.ISAUTOLOGIN);
            Hawk.delete(UserInfo.PASSWORD);
            startNewAct(LoginActivity.class);
        } else {
            MyToast.showToast(mActivity, "账号注销不成功");
        }
    }

    @Override
    public void showNoDataHint(String msg) {
        super.showNoDataHint(msg);
        MyToast.showToast(mActivity, msg);
    }

    Dialog cancleDialog;

    @OnClick({R.id.rlfLogout, R.id.rlfSetting, R.id.tvfStore})
    void MainOnClick(View v) {
        switch (v.getId()) {
            case R.id.rlfLogout:
                if (ActivityUtils.isRunning(mActivity)) {
                    if (System.currentTimeMillis() - com.dingyg.updateutil.App.lastUpdateTime > 0) {
                        com.dingyg.updateutil.App.lastUpdateTime = System.currentTimeMillis();
                        cancleDialog = com.dingyg.updateutil.CustomDialog.createCancleDialog(mActivity, "确要注销当前账号吗？",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cancleDialog.dismiss();
                                        presenter.LogOut(mActivity);
                                    }
                                });
                        cancleDialog.show();
                    }
                }
                break;
            case R.id.rlfSetting:
               initSoundUtil(true);
                break;
            case R.id.tvfStore:
                Bundle bundle = new Bundle();
                bundle.putBoolean("ISFROMMAIN", true);
                startNewAct(StoreActivity.class, bundle);
                finish();
                break;
        }
    }

//    // 返回home
    long flag = -1;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {
            if (flag == -1 || System.currentTimeMillis() - flag > 2000) {
                Toast.makeText(this, "再点击一次退出App", Toast.LENGTH_SHORT).show();
                flag = System.currentTimeMillis();

            } else if (System.currentTimeMillis() - flag < 2000) {
                FileUtils.deleteDir("/WarehousManagerWav");
                CommonApp.finishAllActivity();
                System.exit(0);
            }
        }
        return true;
    }

    Dialog dialog;
    //内网更新
    Runnable checkSelfUpdateInt = new Runnable() {
        @Override
        public void run() {
            // TODO 这里写上传逻辑
            try {
                int localVerCode = intUpdateManager.getLocalVerCode();
                int serverVerCode = intUpdateManager.getServerVerCode();
                // 如果服务器版本高于本地版本则提示更新
                if (serverVerCode > localVerCode) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (ActivityUtils.isRunning(mActivity)) {
                                if (System.currentTimeMillis() - com.dingyg.updateutil.App.lastUpdateTime > 0) {
                                    com.dingyg.updateutil.App.lastUpdateTime = System.currentTimeMillis();
                                    dialog = com.dingyg.updateutil.CustomDialog.createUpdateDialog(mActivity,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                    dialog.setCancelable(true);
                                                    intUpdateManager.showDownloadDialog();
                                                }
                                            }, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.dismiss();
                                                    initSoundUtil(false);
                                                }
                                            });
                                    dialog.show();
                                }
                            }
                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            initSoundUtil(false);
                        }
                    });
                }
            } catch (Exception e) {
            }
        }
    };

    /**
     * 初始化提示语音
     * @param isNeedToSetting 是否跳转至设置界面
     */
    private void initSoundUtil(boolean isNeedToSetting){
        PermissionsUtil.requestPermission(MainActivity.this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                if(isNeedToSetting) {
                    startNewAct(SettingActivity.class);
                }
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                Toast.makeText(MainActivity.this, "用户拒绝了读写本地权限,APP无法获取默认提示铃声", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


}
