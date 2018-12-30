// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.home.login.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import butterknife.internal.Utils;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CustomScanAct_ViewBinding extends BaseActivity_ViewBinding {
  private CustomScanAct target;

  @UiThread
  public CustomScanAct_ViewBinding(CustomScanAct target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CustomScanAct_ViewBinding(CustomScanAct target, View source) {
    super(target, source);

    this.target = target;

    target.ib_back = Utils.findRequiredViewAsType(source, R.id.ibt_back, "field 'ib_back'", ImageButton.class);
    target.dbvCustom = Utils.findRequiredViewAsType(source, R.id.dbv_custom, "field 'dbvCustom'", DecoratedBarcodeView.class);
  }

  @Override
  public void unbind() {
    CustomScanAct target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ib_back = null;
    target.dbvCustom = null;

    super.unbind();
  }
}
