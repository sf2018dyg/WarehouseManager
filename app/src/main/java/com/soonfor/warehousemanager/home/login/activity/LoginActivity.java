package com.soonfor.warehousemanager.home.login.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.SoonforApplication;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.home.login.bean.UserInfoBean;
import com.soonfor.warehousemanager.home.login.presenter.LoginPresenter;
import com.soonfor.warehousemanager.home.login.view.ILoginView;
import com.soonfor.warehousemanager.home.main.MainActivity;
import com.soonfor.warehousemanager.home.store.StoreActivity;
import com.soonfor.warehousemanager.home.store.StoreBean;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.tools.CommonApp;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.SoundUtil;
import com.soonfor.warehousemanager.tools.Tokens;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/1/11 0011.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements CompoundButton.OnCheckedChangeListener, ILoginView {

    static LoginActivity loginActivity;
    @BindView(R.id.tvfStore)
    TextView tvfStore;
    @BindView(R.id.imgfStore)
    ImageView imgfStore;
    @BindView(R.id.tv_login_username)
    EditText tvLoginUsername;
    @BindView(R.id.tv_login_password)
    EditText tvLoginPassword;
    //    @BindView(R.id.check_is_remember)
//    CheckBox checkIsRemember;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    //public static boolean is_Remember = false;
    private boolean is_AutomLogin = false;
    private UserInfoBean userInfo;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        actionName = "登录中...";
        return R.layout.activity_login;
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter(this);
        loginActivity = LoginActivity.this;

        if (!isTaskRoot()) {
            finish();
            return;
        }
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        String service = Hawk.get(SoonforApplication.ServerAdr, "");
        if (service == null || service.equals("")) {
            startNewAct(ServerSettingsActivity.class);
            finish();
        } else {
            StoreBean selectStore = Hawk.get(UserInfo.SELECTSTORE, null);
            if (selectStore == null) {
                startNewAct(StoreActivity.class);
                finish();
            } else {
                tvfStore.setText(selectStore.getfStkName());
                tvfStore.setOnClickListener(listener);
                imgfStore.setOnClickListener(listener);
                btLogin.setOnClickListener(listener);
                tvReset.setOnClickListener(listener);
                //checkIsRemember.setOnCheckedChangeListener(this);
                //checkIsRemember.setChecked(Hawk.get(UserInfo.ISREMEMBER, false));
                userInfo = Hawk.get(UserInfo.CURRENTUSERINFO, null);
                if (userInfo != null) {
                    tvLoginUsername.setText(userInfo.getUsercode());
                    tvLoginUsername.setSelection(userInfo.getUsercode().length());//将光标移至文字末尾
                }
            }
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int i = view.getId();
            if (i == R.id.bt_login) {
                presenter.Login(LoginActivity.this,
                        tvLoginUsername.getText().toString().trim(),
                        tvLoginPassword.getText().toString().trim());
            } else if (i == R.id.tv_reset) {
                startNewAct(ServerSettingsActivity.class);
                finish();
            } else if (i == R.id.tvfStore || i == R.id.imgfStore) {
                startNewAct(StoreActivity.class);
                finish();
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        int i = buttonView.getId();
//        if (i == R.id.check_is_remember) {
//            is_Remember = isChecked;
//            Hawk.put(UserInfo.ISREMEMBER, is_Remember);
//        }
    }

    @Override
    public void saveSerAddress(String address) {
    }

    @Override
    public void successLogin() {
        Hawk.delete(Tokens.Putint.PUTIN_CONDITONS);
        Hawk.delete(Tokens.Putout.PUTOUT_CONDITONS);
        closeLoadingDialog();
        startNewAct(MainActivity.class);
        finish();
    }

//    @Override
//    public void isRemember(boolean checked, String password) {
//        checkIsRemember.setChecked(checked);
//        tvLoginPassword.setText(password);
//    }

    @Override
    public void showNoDataHint(String msg) {
        super.showNoDataHint(msg);
        MyToast.showFailToast(LoginActivity.this, msg);
    }

    public static void FinishLoginActivity(boolean mainIsExist) {
        if (loginActivity != null) {
            loginActivity.closeLoadingDialog();
            if (mainIsExist) {
                loginActivity.finish();
            }
        }
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
                CommonApp.finishAllActivity();
                System.exit(0);
            }
        }
        return true;
    }
}
