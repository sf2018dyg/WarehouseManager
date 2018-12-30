// Generated code from Butter Knife. Do not modify!
package com.soonfor.warehousemanager.home.setting;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MusicActivity_ViewBinding extends BaseActivity_ViewBinding {
  private MusicActivity target;

  private View view2131231093;

  private View view2131231105;

  @UiThread
  public MusicActivity_ViewBinding(MusicActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MusicActivity_ViewBinding(final MusicActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.mRecyclerView, "field 'mRecyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tvfRightClick, "method 'ThisOnClick'");
    view2131231093 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.ThisOnClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tvfSure, "method 'ThisOnClick'");
    view2131231105 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.ThisOnClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    MusicActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;

    view2131231093.setOnClickListener(null);
    view2131231093 = null;
    view2131231105.setOnClickListener(null);
    view2131231105 = null;

    super.unbind();
  }
}
