package com.soonfor.warehousemanager.home.login.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.orhanobut.hawk.Hawk;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.home.login.presenter.LoginPresenter;
import com.soonfor.warehousemanager.home.login.view.ILoginView;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.dialog.QrCodeDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改人：DC-ZhuSuiBo on 2018/8/6 0006 13:31
 * 邮箱：suibozhu@139.com
 * 修改目的：
 */

public class ServerSettingsActivity extends BaseActivity<LoginPresenter> implements ILoginView {
    ServerSettingsActivity mContext = this;
    @BindView(R.id.tv_sj_server_address)
    EditText tvServerAddress_sj;//数据服务器
    @BindView(R.id.ibt_sj_scan)
    ImageButton ibfScan;
    @BindView(R.id.bt_save)
    Button btSave;

    private QrCodeDialog mQrCodeDialog;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_server_settings;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        PermissionsUtil.requestPermission(mContext, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {

                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {

                    }
                },
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA);
        mQrCodeDialog = new QrCodeDialog(this);
        mQrCodeDialog.setGravityBottom();
        String address = Hawk.get(SoonforApplication.ServerAdr, "");
        if (address != null && !address.equals("")) {
            tvServerAddress_sj.setText(address);
            tvServerAddress_sj.setSelection(address.length());
        }
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter(this);
    }

    @OnClick({R.id.ibt_sj_scan, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibt_sj_scan:
                mQrCodeDialog.show();
                break;
            case R.id.bt_save:
                if (tvServerAddress_sj.getText() == null && tvServerAddress_sj.getText().toString().trim().length() == 0) {
                    MyToast.showToast(mContext, "请设置服务器地址！");
                    return;
                }
                String serverUrl = tvServerAddress_sj.getText().toString().trim().toLowerCase();
                if (serverUrl.startsWith("http://")) {
                    serverUrl = serverUrl.replace("http://", "");
                }
                //判断url是否符合标准
                if (Patterns.WEB_URL.matcher(serverUrl).matches()) {
                    //符合标准
                    for (int i = 0; i < 5; i++) {
                        if (serverUrl.endsWith("/"))
                            serverUrl = serverUrl.substring(0, serverUrl.length() - 1);
                        else break;
                    }
                    saveSerAddress(serverUrl);
                } else {
                    //不符合标准
                    MyToast.showToast(mContext, "请输入正确的服务器地址！");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String scanResult = QrCodeDialog.getStringFromImage(ServerSettingsActivity.this, data, requestCode, resultCode);
        if (!scanResult.equals("")) {
            tvServerAddress_sj.setText(scanResult);
            tvServerAddress_sj.setSelection(scanResult.length());
        }
    }

    @Override
    public void saveSerAddress(String address) {
        if (Hawk.get(SoonforApplication.ServerAdr, "") != null) {
            if (!Hawk.get(SoonforApplication.ServerAdr, "").equals(address)) {
                Hawk.put(UserInfo.SELECTSTORE, null);
                Hawk.put(UserInfo.ISAUTOLOGIN, false);
                Hawk.put(UserInfo.PASSWORD, "");
            }
        }
        Hawk.put(SoonforApplication.ServerAdr, address);
        closeLoadingDialog();
        startNewAct(LoginActivity.class);
        finish();
    }

    @Override
    public void successLogin() {
    }

//    @Override
//    public void isRemember(boolean checked, String password) {
//
//    }

    @Override
    public void showNoDataHint(String msg) {
        super.showNoDataHint(msg);
        MyToast.showFailToast(mContext, msg);
    }

    // 返回home
    long flag = -1;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {
            if (flag == -1 || System.currentTimeMillis() - flag > 2000) {
                Toast.makeText(this, "再点击一次退出App", Toast.LENGTH_SHORT).show();
                flag = System.currentTimeMillis();

            } else if (System.currentTimeMillis() - flag < 2000) {
                /*Intent exit = new Intent(Intent.ACTION_MAIN);
                exit.addCategory(Intent.CATEGORY_HOME);
                exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(exit);*/
                System.exit(0);
            }
        }
        return true;
    }
}
