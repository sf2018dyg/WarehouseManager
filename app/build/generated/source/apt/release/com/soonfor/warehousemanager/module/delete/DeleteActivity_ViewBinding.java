// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.delete;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeleteActivity_ViewBinding extends BaseActivity_ViewBinding {
  private DeleteActivity target;

  @UiThread
  public DeleteActivity_ViewBinding(DeleteActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DeleteActivity_ViewBinding(DeleteActivity target, View source) {
    super(target, source);

    this.target = target;

    target.tablell = Utils.findRequiredViewAsType(source, R.id.tablell, "field 'tablell'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    DeleteActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tablell = null;

    super.unbind();
  }
}
