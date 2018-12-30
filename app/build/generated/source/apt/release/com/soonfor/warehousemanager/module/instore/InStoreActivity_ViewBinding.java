// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.instore;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.HorizontalScrollView;
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

public class InStoreActivity_ViewBinding extends BaseActivity_ViewBinding {
  private InStoreActivity target;

  private View view2131230905;

  private View view2131231094;

  @UiThread
  public InStoreActivity_ViewBinding(InStoreActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public InStoreActivity_ViewBinding(final InStoreActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.tab = Utils.findRequiredViewAsType(source, R.id.tl_1, "field 'tab'", CommonTabLayout.class);
    target.tablell = Utils.findRequiredViewAsType(source, R.id.tablell, "field 'tablell'", LinearLayout.class);
    target.etWithClear = Utils.findRequiredViewAsType(source, R.id.etWithClear, "field 'etWithClear'", EditTextWithClear.class);
    target.tvfSubTitile = Utils.findRequiredViewAsType(source, R.id.tvfSubTitile, "field 'tvfSubTitile'", TextView.class);
    target.yingsao = Utils.findRequiredViewAsType(source, R.id.yingsao, "field 'yingsao'", TextView.class);
    target.yisao = Utils.findRequiredViewAsType(source, R.id.yisao, "field 'yisao'", TextView.class);
    target.benci = Utils.findRequiredViewAsType(source, R.id.benci, "field 'benci'", TextView.class);
    target.weisao = Utils.findRequiredViewAsType(source, R.id.weisao, "field 'weisao'", TextView.class);
    target.scrollHead = Utils.findRequiredViewAsType(source, R.id.scrollHead, "field 'scrollHead'", HorizontalScrollView.class);
    target.bcView = Utils.findRequiredViewAsType(source, R.id.bcView, "field 'bcView'", BottomComView.class);
    view = Utils.findRequiredView(source, R.id.ivfLeft, "method 'ThisViewClick'");
    view2131230905 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.ThisViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tvfRightSet, "method 'ThisViewClick'");
    view2131231094 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.ThisViewClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    InStoreActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tab = null;
    target.tablell = null;
    target.etWithClear = null;
    target.tvfSubTitile = null;
    target.yingsao = null;
    target.yisao = null;
    target.benci = null;
    target.weisao = null;
    target.scrollHead = null;
    target.bcView = null;

    view2131230905.setOnClickListener(null);
    view2131230905 = null;
    view2131231094.setOnClickListener(null);
    view2131231094 = null;

    super.unbind();
  }
}
