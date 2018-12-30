// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.home.setting;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingActivity_ViewBinding extends BaseActivity_ViewBinding {
  private SettingActivity target;

  private View view2131230927;

  private View view2131230926;

  private View view2131230923;

  private View view2131230924;

  private View view2131230925;

  @UiThread
  public SettingActivity_ViewBinding(SettingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SettingActivity_ViewBinding(final SettingActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.tvfSMToSucess = Utils.findRequiredViewAsType(source, R.id.tvfSMToSucess, "field 'tvfSMToSucess'", TextView.class);
    target.tvfSMToRepetition = Utils.findRequiredViewAsType(source, R.id.tvfSMToRepetition, "field 'tvfSMToRepetition'", TextView.class);
    target.tvfSMToFail = Utils.findRequiredViewAsType(source, R.id.tvfSMToFail, "field 'tvfSMToFail'", TextView.class);
    target.tvfSMToPrint = Utils.findRequiredViewAsType(source, R.id.tvfSMToPrint, "field 'tvfSMToPrint'", TextView.class);
    target.tvfSMToPrintPlan = Utils.findRequiredViewAsType(source, R.id.tvfSMToPrintPlan, "field 'tvfSMToPrintPlan'", TextView.class);
    view = Utils.findRequiredView(source, R.id.llfSMToSucess, "method 'SettingOnClick'");
    view2131230927 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.SettingOnClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.llfSMToRepetition, "method 'SettingOnClick'");
    view2131230926 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.SettingOnClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.llfSMToFail, "method 'SettingOnClick'");
    view2131230923 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.SettingOnClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.llfSMToPrint, "method 'SettingOnClick'");
    view2131230924 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.SettingOnClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.llfSMToPrintPlan, "method 'SettingOnClick'");
    view2131230925 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.SettingOnClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    SettingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvfSMToSucess = null;
    target.tvfSMToRepetition = null;
    target.tvfSMToFail = null;
    target.tvfSMToPrint = null;
    target.tvfSMToPrintPlan = null;

    view2131230927.setOnClickListener(null);
    view2131230927 = null;
    view2131230926.setOnClickListener(null);
    view2131230926 = null;
    view2131230923.setOnClickListener(null);
    view2131230923 = null;
    view2131230924.setOnClickListener(null);
    view2131230924 = null;
    view2131230925.setOnClickListener(null);
    view2131230925 = null;

    super.unbind();
  }
}
