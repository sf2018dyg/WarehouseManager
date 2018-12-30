// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.querybyscan.unscan;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UnScanBarCodeActivity_ViewBinding extends BaseActivity_ViewBinding {
  private UnScanBarCodeActivity target;

  @UiThread
  public UnScanBarCodeActivity_ViewBinding(UnScanBarCodeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UnScanBarCodeActivity_ViewBinding(UnScanBarCodeActivity target, View source) {
    super(target, source);

    this.target = target;

    target.tablell = Utils.findRequiredViewAsType(source, R.id.tablell, "field 'tablell'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    UnScanBarCodeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tablell = null;

    super.unbind();
  }
}
