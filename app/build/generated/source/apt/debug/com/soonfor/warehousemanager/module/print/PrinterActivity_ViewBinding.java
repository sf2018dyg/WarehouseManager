// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.print;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dingyg.edittextwithclear.EditTextWithClear;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PrinterActivity_ViewBinding extends BaseActivity_ViewBinding {
  private PrinterActivity target;

  private View view2131230905;

  private View view2131231091;

  private View view2131231090;

  @UiThread
  public PrinterActivity_ViewBinding(PrinterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PrinterActivity_ViewBinding(final PrinterActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.cbDel = Utils.findRequiredViewAsType(source, R.id.cbDel, "field 'cbDel'", CheckBox.class);
    target.tablell = Utils.findRequiredViewAsType(source, R.id.tablell, "field 'tablell'", LinearLayout.class);
    target.etWithClear = Utils.findRequiredViewAsType(source, R.id.etWithClear, "field 'etWithClear'", EditTextWithClear.class);
    view = Utils.findRequiredView(source, R.id.ivfLeft, "method 'OnViewClick'");
    view2131230905 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tvfPrinter, "method 'OnViewClick'");
    view2131231091 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tvfNoPrinters, "method 'OnViewClick'");
    view2131231090 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    PrinterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cbDel = null;
    target.tablell = null;
    target.etWithClear = null;

    view2131230905.setOnClickListener(null);
    view2131230905 = null;
    view2131231091.setOnClickListener(null);
    view2131231091 = null;
    view2131231090.setOnClickListener(null);
    view2131231090 = null;

    super.unbind();
  }
}
