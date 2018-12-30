// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.module.instore;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dingyg.edittextwithclear.EditTextWithClear;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class deleteBarCodeActivity_ViewBinding extends BaseActivity_ViewBinding {
  private deleteBarCodeActivity target;

  private View view2131231108;

  @UiThread
  public deleteBarCodeActivity_ViewBinding(deleteBarCodeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public deleteBarCodeActivity_ViewBinding(final deleteBarCodeActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.etWithClear = Utils.findRequiredViewAsType(source, R.id.etWithClear, "field 'etWithClear'", EditTextWithClear.class);
    target.txtpinhao = Utils.findRequiredViewAsType(source, R.id.txtpinhao, "field 'txtpinhao'", TextView.class);
    target.txtpinming = Utils.findRequiredViewAsType(source, R.id.txtpinming, "field 'txtpinming'", TextView.class);
    target.txtguige = Utils.findRequiredViewAsType(source, R.id.txtguige, "field 'txtguige'", TextView.class);
    target.txtdingdanhao = Utils.findRequiredViewAsType(source, R.id.txtdingdanhao, "field 'txtdingdanhao'", TextView.class);
    target.txtfenbaohao = Utils.findRequiredViewAsType(source, R.id.txtfenbaohao, "field 'txtfenbaohao'", TextView.class);
    target.cbDek = Utils.findRequiredViewAsType(source, R.id.cbDel, "field 'cbDek'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.txtClose, "method 'OnViewClick'");
    view2131231108 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnViewClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    deleteBarCodeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etWithClear = null;
    target.txtpinhao = null;
    target.txtpinming = null;
    target.txtguige = null;
    target.txtdingdanhao = null;
    target.txtfenbaohao = null;
    target.cbDek = null;

    view2131231108.setOnClickListener(null);
    view2131231108 = null;

    super.unbind();
  }
}
