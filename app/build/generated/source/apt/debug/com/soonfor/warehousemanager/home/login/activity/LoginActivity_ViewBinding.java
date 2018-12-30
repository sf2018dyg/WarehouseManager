// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.home.login.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding extends BaseActivity_ViewBinding {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    super(target, source);

    this.target = target;

    target.tvfStore = Utils.findRequiredViewAsType(source, R.id.tvfStore, "field 'tvfStore'", TextView.class);
    target.imgfStore = Utils.findRequiredViewAsType(source, R.id.imgfStore, "field 'imgfStore'", ImageView.class);
    target.tvLoginUsername = Utils.findRequiredViewAsType(source, R.id.tv_login_username, "field 'tvLoginUsername'", EditText.class);
    target.tvLoginPassword = Utils.findRequiredViewAsType(source, R.id.tv_login_password, "field 'tvLoginPassword'", EditText.class);
    target.btLogin = Utils.findRequiredViewAsType(source, R.id.bt_login, "field 'btLogin'", Button.class);
    target.tvReset = Utils.findRequiredViewAsType(source, R.id.tv_reset, "field 'tvReset'", TextView.class);
  }

  @Override
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvfStore = null;
    target.imgfStore = null;
    target.tvLoginUsername = null;
    target.tvLoginPassword = null;
    target.btLogin = null;
    target.tvReset = null;

    super.unbind();
  }
}
