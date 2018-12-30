// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.instore;

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

public class InStoreConditionActivity_ViewBinding extends BaseActivity_ViewBinding {
  private InStoreConditionActivity target;

  private View view2131231077;

  private View view2131231076;

  private View view2131231080;

  private View view2131231079;

  private View view2131231110;

  @UiThread
  public InStoreConditionActivity_ViewBinding(InStoreConditionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public InStoreConditionActivity_ViewBinding(final InStoreConditionActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.tv_selectDanju = Utils.findRequiredViewAsType(source, R.id.tv_selectDanju, "field 'tv_selectDanju'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_selectChuwei, "field 'tv_selectChuwei' and method 'OnViewClick'");
    target.tv_selectChuwei = Utils.castView(view, R.id.tv_selectChuwei, "field 'tv_selectChuwei'", TextView.class);
    view2131231077 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_selectBumen, "field 'tv_selectBumen' and method 'OnViewClick'");
    target.tv_selectBumen = Utils.castView(view, R.id.tv_selectBumen, "field 'tv_selectBumen'", TextView.class);
    view2131231076 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_selectYuanyin, "field 'tv_selectYuanyin' and method 'OnViewClick'");
    target.tv_selectYuanyin = Utils.castView(view, R.id.tv_selectYuanyin, "field 'tv_selectYuanyin'", TextView.class);
    view2131231080 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    target.viewllfchuwei = Utils.findRequiredView(source, R.id.viewllfchuwei, "field 'viewllfchuwei'");
    target.llfchuwei = Utils.findRequiredViewAsType(source, R.id.llfchuwei, "field 'llfchuwei'", LinearLayout.class);
    target.llfrukudan = Utils.findRequiredViewAsType(source, R.id.llfrukudan, "field 'llfrukudan'", LinearLayout.class);
    target.viewllfrukudanju = Utils.findRequiredView(source, R.id.viewllfrukudanju, "field 'viewllfrukudanju'");
    view = Utils.findRequiredView(source, R.id.tv_selectRukuDanju, "field 'tv_selectRukuDanju' and method 'OnViewClick'");
    target.tv_selectRukuDanju = Utils.castView(view, R.id.tv_selectRukuDanju, "field 'tv_selectRukuDanju'", TextView.class);
    view2131231079 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    target.llfdanju = Utils.findRequiredViewAsType(source, R.id.llfdanju, "field 'llfdanju'", LinearLayout.class);
    target.llfyuanyin = Utils.findRequiredViewAsType(source, R.id.llfyuanyin, "field 'llfyuanyin'", LinearLayout.class);
    target.llfremark = Utils.findRequiredViewAsType(source, R.id.llfremark, "field 'llfremark'", LinearLayout.class);
    target.viewchuwei = Utils.findRequiredView(source, R.id.viewchuwei, "field 'viewchuwei'");
    target.viewbm = Utils.findRequiredView(source, R.id.viewbm, "field 'viewbm'");
    target.viewreson = Utils.findRequiredView(source, R.id.viewreson, "field 'viewreson'");
    target.txthead = Utils.findRequiredViewAsType(source, R.id.txthead, "field 'txthead'", TextView.class);
    target.txtdeptstar = Utils.findRequiredViewAsType(source, R.id.txtdeptstar, "field 'txtdeptstar'", TextView.class);
    target.chuweistar = Utils.findRequiredViewAsType(source, R.id.chuweistar, "field 'chuweistar'", TextView.class);
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
    InStoreConditionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_selectDanju = null;
    target.tv_selectChuwei = null;
    target.tv_selectBumen = null;
    target.tv_selectYuanyin = null;
    target.viewllfchuwei = null;
    target.llfchuwei = null;
    target.llfrukudan = null;
    target.viewllfrukudanju = null;
    target.tv_selectRukuDanju = null;
    target.llfdanju = null;
    target.llfyuanyin = null;
    target.llfremark = null;
    target.viewchuwei = null;
    target.viewbm = null;
    target.viewreson = null;
    target.txthead = null;
    target.txtdeptstar = null;
    target.chuweistar = null;

    view2131231077.setOnClickListener(null);
    view2131231077 = null;
    view2131231076.setOnClickListener(null);
    view2131231076 = null;
    view2131231080.setOnClickListener(null);
    view2131231080 = null;
    view2131231079.setOnClickListener(null);
    view2131231079 = null;
    view2131231110.setOnClickListener(null);
    view2131231110 = null;

    super.unbind();
  }
}
