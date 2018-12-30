// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.instore.flowtype;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FlowTypeActivity_ViewBinding extends BaseActivity_ViewBinding {
  private FlowTypeActivity target;

  @UiThread
  public FlowTypeActivity_ViewBinding(FlowTypeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FlowTypeActivity_ViewBinding(FlowTypeActivity target, View source) {
    super(target, source);

    this.target = target;

    target.rlfBottom = Utils.findRequiredViewAsType(source, R.id.rlfBottom, "field 'rlfBottom'", RelativeLayout.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.mRecyclerView, "field 'mRecyclerView'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FlowTypeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rlfBottom = null;
    target.mRecyclerView = null;

    super.unbind();
  }
}
