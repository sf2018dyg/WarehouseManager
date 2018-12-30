// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.findbarcode;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.CommonTabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BarCodeSearchActivity_ViewBinding extends BaseActivity_ViewBinding {
  private BarCodeSearchActivity target;

  @UiThread
  public BarCodeSearchActivity_ViewBinding(BarCodeSearchActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BarCodeSearchActivity_ViewBinding(BarCodeSearchActivity target, View source) {
    super(target, source);

    this.target = target;

    target.tl_1 = Utils.findRequiredViewAsType(source, R.id.tl_1, "field 'tl_1'", CommonTabLayout.class);
    target.tablell = Utils.findRequiredViewAsType(source, R.id.tablell, "field 'tablell'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    BarCodeSearchActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tl_1 = null;
    target.tablell = null;

    super.unbind();
  }
}
