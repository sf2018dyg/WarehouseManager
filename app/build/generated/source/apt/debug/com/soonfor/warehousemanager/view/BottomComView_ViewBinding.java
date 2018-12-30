// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BottomComView_ViewBinding implements Unbinder {
  private BottomComView target;

  @UiThread
  public BottomComView_ViewBinding(BottomComView target) {
    this(target, target);
  }

  @UiThread
  public BottomComView_ViewBinding(BottomComView target, View source) {
    this.target = target;

    target.llfCreateChOrder = Utils.findRequiredViewAsType(source, R.id.llfCreateChOrder, "field 'llfCreateChOrder'", LinearLayout.class);
    target.bnfIsOk = Utils.findRequiredViewAsType(source, R.id.bnfIsOk, "field 'bnfIsOk'", Button.class);
    target.imgfBottom = Utils.findRequiredViewAsType(source, R.id.imgfBottom, "field 'imgfBottom'", ImageView.class);
    target.rlfShowPop = Utils.findRequiredViewAsType(source, R.id.rlfShowPop, "field 'rlfShowPop'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BottomComView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.llfCreateChOrder = null;
    target.bnfIsOk = null;
    target.imgfBottom = null;
    target.rlfShowPop = null;
  }
}
