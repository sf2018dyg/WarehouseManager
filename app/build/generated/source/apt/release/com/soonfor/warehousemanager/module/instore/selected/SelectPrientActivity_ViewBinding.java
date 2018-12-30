// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.instore.selected;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SelectPrientActivity_ViewBinding extends BaseActivity_ViewBinding {
  private SelectPrientActivity target;

  private View view2131230905;

  private View view2131231110;

  @UiThread
  public SelectPrientActivity_ViewBinding(SelectPrientActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SelectPrientActivity_ViewBinding(final SelectPrientActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.tvfTitile = Utils.findRequiredViewAsType(source, R.id.tvfTitile, "field 'tvfTitile'", TextView.class);
    target.tablell = Utils.findRequiredViewAsType(source, R.id.tablell, "field 'tablell'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.ivfLeft, "method 'OnViewClick'");
    view2131230905 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_confirm, "method 'OnViewClick'");
    view2131231110 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    SelectPrientActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvfTitile = null;
    target.tablell = null;

    view2131230905.setOnClickListener(null);
    view2131230905 = null;
    view2131231110.setOnClickListener(null);
    view2131231110 = null;

    super.unbind();
  }
}
