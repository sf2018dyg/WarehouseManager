// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.allot;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.CommonTabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AllotMainActivity_ViewBinding extends BaseActivity_ViewBinding {
  private AllotMainActivity target;

  private View view2131231094;

  private View view2131231111;

  @UiThread
  public AllotMainActivity_ViewBinding(AllotMainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AllotMainActivity_ViewBinding(final AllotMainActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tvfRightSet, "field 'tvfRightSet' and method 'OnViewClick'");
    target.tvfRightSet = Utils.castView(view, R.id.tvfRightSet, "field 'tvfRightSet'", TextView.class);
    view2131231094 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    target.tl_1 = Utils.findRequiredViewAsType(source, R.id.tl_1, "field 'tl_1'", CommonTabLayout.class);
    target.tablell = Utils.findRequiredViewAsType(source, R.id.tablell, "field 'tablell'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.txt_scanDel, "method 'OnViewClick'");
    view2131231111 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    AllotMainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvfRightSet = null;
    target.tl_1 = null;
    target.tablell = null;

    view2131231094.setOnClickListener(null);
    view2131231094 = null;
    view2131231111.setOnClickListener(null);
    view2131231111 = null;

    super.unbind();
  }
}
