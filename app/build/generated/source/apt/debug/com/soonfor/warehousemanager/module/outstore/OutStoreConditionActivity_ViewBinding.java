// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.outstore;

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

public class OutStoreConditionActivity_ViewBinding extends BaseActivity_ViewBinding {
  private OutStoreConditionActivity target;

  private View view2131231114;

  private View view2131231109;

  private View view2131231112;

  private View view2131231101;

  private View view2131231110;

  @UiThread
  public OutStoreConditionActivity_ViewBinding(OutStoreConditionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OutStoreConditionActivity_ViewBinding(final OutStoreConditionActivity target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.llfBeiHuo = Utils.findRequiredViewAsType(source, R.id.llfBeiHuo, "field 'llfBeiHuo'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.txt_wuliupici, "field 'txt_wuliupici' and method 'OnViewClick'");
    target.txt_wuliupici = Utils.castView(view, R.id.txt_wuliupici, "field 'txt_wuliupici'", TextView.class);
    view2131231114 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_chepai, "field 'txt_chepai' and method 'OnViewClick'");
    target.txt_chepai = Utils.castView(view, R.id.txt_chepai, "field 'txt_chepai'", TextView.class);
    view2131231109 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_selectordno, "field 'txt_selectordno' and method 'OnViewClick'");
    target.txt_selectordno = Utils.castView(view, R.id.txt_selectordno, "field 'txt_selectordno'", TextView.class);
    view2131231112 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    target.txt_zaihuotai = Utils.findRequiredViewAsType(source, R.id.edt_zaihuotai, "field 'txt_zaihuotai'", TextView.class);
    target.llfChuHuo = Utils.findRequiredViewAsType(source, R.id.llfChuHuo, "field 'llfChuHuo'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tvfShippingOrder, "field 'tvfShippingOrder' and method 'OnViewClick'");
    target.tvfShippingOrder = Utils.castView(view, R.id.tvfShippingOrder, "field 'tvfShippingOrder'", TextView.class);
    view2131231101 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_confirm, "field 'tvfConfirm' and method 'OnViewClick'");
    target.tvfConfirm = Utils.castView(view, R.id.txt_confirm, "field 'tvfConfirm'", TextView.class);
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
    OutStoreConditionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.llfBeiHuo = null;
    target.txt_wuliupici = null;
    target.txt_chepai = null;
    target.txt_selectordno = null;
    target.txt_zaihuotai = null;
    target.llfChuHuo = null;
    target.tvfShippingOrder = null;
    target.tvfConfirm = null;

    view2131231114.setOnClickListener(null);
    view2131231114 = null;
    view2131231109.setOnClickListener(null);
    view2131231109 = null;
    view2131231112.setOnClickListener(null);
    view2131231112 = null;
    view2131231101.setOnClickListener(null);
    view2131231101 = null;
    view2131231110.setOnClickListener(null);
    view2131231110 = null;

    super.unbind();
  }
}
