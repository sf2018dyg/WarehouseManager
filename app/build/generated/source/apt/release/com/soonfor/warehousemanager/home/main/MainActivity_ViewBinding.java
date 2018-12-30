// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.home.main;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import com.soonfor.warehousemanager.tools.CustomGlridView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding extends BaseActivity_ViewBinding {
  private MainActivity target;

  private View view2131231103;

  private View view2131230980;

  private View view2131230978;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tvfStore, "field 'tvfStore' and method 'MainOnClick'");
    target.tvfStore = Utils.castView(view, R.id.tvfStore, "field 'tvfStore'", TextView.class);
    view2131231103 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MainOnClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rlfSetting, "field 'rlfSetting' and method 'MainOnClick'");
    target.rlfSetting = Utils.castView(view, R.id.rlfSetting, "field 'rlfSetting'", RelativeLayout.class);
    view2131230980 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MainOnClick(p0);
      }
    });
    target.glridView = Utils.findRequiredViewAsType(source, R.id.gvbody, "field 'glridView'", CustomGlridView.class);
    target.tvfVersion = Utils.findRequiredViewAsType(source, R.id.tvfVersion, "field 'tvfVersion'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rlfLogout, "method 'MainOnClick'");
    view2131230978 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MainOnClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvfStore = null;
    target.rlfSetting = null;
    target.glridView = null;
    target.tvfVersion = null;

    view2131231103.setOnClickListener(null);
    view2131231103 = null;
    view2131230980.setOnClickListener(null);
    view2131230980 = null;
    view2131230978.setOnClickListener(null);
    view2131230978 = null;

    super.unbind();
  }
}
