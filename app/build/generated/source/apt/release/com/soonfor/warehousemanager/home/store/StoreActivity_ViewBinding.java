// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.home.store;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StoreActivity_ViewBinding extends BaseActivity_ViewBinding {
  private StoreActivity target;

  private View view2131231092;

  private View view2131231089;

  @UiThread
  public StoreActivity_ViewBinding(StoreActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StoreActivity_ViewBinding(final StoreActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.mRecyclerView, "field 'mRecyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tvfReset, "method 'ThisOnClick'");
    view2131231092 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.ThisOnClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tvfNextStep, "method 'ThisOnClick'");
    view2131231089 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.ThisOnClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    StoreActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;

    view2131231092.setOnClickListener(null);
    view2131231092 = null;
    view2131231089.setOnClickListener(null);
    view2131231089 = null;

    super.unbind();
  }
}
