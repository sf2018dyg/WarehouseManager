// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.outstore;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dingyg.edittextwithclear.EditTextWithClear;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import com.soonfor.warehousemanager.view.BottomComView;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.CommonTabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OutStoreActivity_ViewBinding extends BaseActivity_ViewBinding {
  private OutStoreActivity target;

  private View view2131230905;

  private View view2131231094;

  @UiThread
  public OutStoreActivity_ViewBinding(OutStoreActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OutStoreActivity_ViewBinding(final OutStoreActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.tl_1 = Utils.findRequiredViewAsType(source, R.id.tl_1, "field 'tl_1'", CommonTabLayout.class);
    target.tablell = Utils.findRequiredViewAsType(source, R.id.tablell, "field 'tablell'", LinearLayout.class);
    target.etWithClear = Utils.findRequiredViewAsType(source, R.id.etWithClear, "field 'etWithClear'", EditTextWithClear.class);
    target.yingsao = Utils.findRequiredViewAsType(source, R.id.yingsao, "field 'yingsao'", TextView.class);
    target.yisao = Utils.findRequiredViewAsType(source, R.id.yisao, "field 'yisao'", TextView.class);
    target.benci = Utils.findRequiredViewAsType(source, R.id.benci, "field 'benci'", TextView.class);
    target.weisao = Utils.findRequiredViewAsType(source, R.id.weisao, "field 'weisao'", TextView.class);
    target.bcView = Utils.findRequiredViewAsType(source, R.id.bcView, "field 'bcView'", BottomComView.class);
    view = Utils.findRequiredView(source, R.id.ivfLeft, "method 'OnViewOnClick'");
    view2131230905 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewOnClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tvfRightSet, "method 'OnViewOnClick'");
    view2131231094 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewOnClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    OutStoreActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tl_1 = null;
    target.tablell = null;
    target.etWithClear = null;
    target.yingsao = null;
    target.yisao = null;
    target.benci = null;
    target.weisao = null;
    target.bcView = null;

    view2131230905.setOnClickListener(null);
    view2131230905 = null;
    view2131231094.setOnClickListener(null);
    view2131231094 = null;

    super.unbind();
  }
}
