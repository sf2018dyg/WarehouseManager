// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.bases;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonfor.warehousemanager.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseActivity_ViewBinding implements Unbinder {
  private BaseActivity target;

  @UiThread
  public BaseActivity_ViewBinding(BaseActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BaseActivity_ViewBinding(BaseActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findOptionalViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.mSwipeRefresh = Utils.findOptionalViewAsType(source, R.id.swipe_refresh, "field 'mSwipeRefresh'", SmartRefreshLayout.class);
    target.emptyView = Utils.findOptionalViewAsType(source, R.id.emptyView, "field 'emptyView'", QMUIEmptyView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.mSwipeRefresh = null;
    target.emptyView = null;
  }
}
