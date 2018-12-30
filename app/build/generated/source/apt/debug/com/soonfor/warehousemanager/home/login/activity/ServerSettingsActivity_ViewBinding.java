// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.home.login.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ServerSettingsActivity_ViewBinding extends BaseActivity_ViewBinding {
  private ServerSettingsActivity target;

  private View view2131230875;

  private View view2131230783;

  @UiThread
  public ServerSettingsActivity_ViewBinding(ServerSettingsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ServerSettingsActivity_ViewBinding(final ServerSettingsActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.tvServerAddress_sj = Utils.findRequiredViewAsType(source, R.id.tv_sj_server_address, "field 'tvServerAddress_sj'", EditText.class);
    view = Utils.findRequiredView(source, R.id.ibt_sj_scan, "field 'ibfScan' and method 'onViewClicked'");
    target.ibfScan = Utils.castView(view, R.id.ibt_sj_scan, "field 'ibfScan'", ImageButton.class);
    view2131230875 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_save, "field 'btSave' and method 'onViewClicked'");
    target.btSave = Utils.castView(view, R.id.bt_save, "field 'btSave'", Button.class);
    view2131230783 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  public void unbind() {
    ServerSettingsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvServerAddress_sj = null;
    target.ibfScan = null;
    target.btSave = null;

    view2131230875.setOnClickListener(null);
    view2131230875 = null;
    view2131230783.setOnClickListener(null);
    view2131230783 = null;

    super.unbind();
  }
}
