// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.instore.selected;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dingyg.edittextwithclear.EditTextWithClear;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InStoreSelectedActivity_ViewBinding extends BaseActivity_ViewBinding {
  private InStoreSelectedActivity target;

  private View view2131231110;

  @UiThread
  public InStoreSelectedActivity_ViewBinding(InStoreSelectedActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public InStoreSelectedActivity_ViewBinding(final InStoreSelectedActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.tvfTitile = Utils.findRequiredViewAsType(source, R.id.tvfTitile, "field 'tvfTitile'", TextView.class);
    target.textV = Utils.findRequiredViewAsType(source, R.id.textV, "field 'textV'", TextView.class);
    target.tablell = Utils.findRequiredViewAsType(source, R.id.tablell, "field 'tablell'", LinearLayout.class);
    target.etWithClear = Utils.findRequiredViewAsType(source, R.id.etWithClear, "field 'etWithClear'", EditTextWithClear.class);
    target.cbDel = Utils.findRequiredViewAsType(source, R.id.cbDel, "field 'cbDel'", CheckBox.class);
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
    InStoreSelectedActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvfTitile = null;
    target.textV = null;
    target.tablell = null;
    target.etWithClear = null;
    target.cbDel = null;

    view2131231110.setOnClickListener(null);
    view2131231110 = null;

    super.unbind();
  }
}
