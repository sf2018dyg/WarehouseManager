// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.querybyscan;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import com.soonfor.warehousemanager.view.BaseQueryView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QueryByScanActivity_ViewBinding extends BaseActivity_ViewBinding {
  private QueryByScanActivity target;

  @UiThread
  public QueryByScanActivity_ViewBinding(QueryByScanActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public QueryByScanActivity_ViewBinding(QueryByScanActivity target, View source) {
    super(target, source);

    this.target = target;

    target.queryView = Utils.findRequiredViewAsType(source, R.id.queryView, "field 'queryView'", BaseQueryView.class);
  }

  @Override
  public void unbind() {
    QueryByScanActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.queryView = null;

    super.unbind();
  }
}
