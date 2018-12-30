// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.view;

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

public class NoQiTaoUIActivity_ViewBinding extends BaseActivity_ViewBinding {
  private NoQiTaoUIActivity target;

  private View view2131231110;

  @UiThread
  public NoQiTaoUIActivity_ViewBinding(NoQiTaoUIActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NoQiTaoUIActivity_ViewBinding(final NoQiTaoUIActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.txtmiaoshu = Utils.findRequiredViewAsType(source, R.id.txtmiaoshu, "field 'txtmiaoshu'", TextView.class);
    target.tablell = Utils.findRequiredViewAsType(source, R.id.tablell, "field 'tablell'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.txt_confirm, "field 'txt_confirm' and method 'OnViewClick'");
    target.txt_confirm = Utils.castView(view, R.id.txt_confirm, "field 'txt_confirm'", TextView.class);
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
    NoQiTaoUIActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtmiaoshu = null;
    target.tablell = null;
    target.txt_confirm = null;

    view2131231110.setOnClickListener(null);
    view2131231110 = null;

    super.unbind();
  }
}
