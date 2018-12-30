package com.soonfor.warehousemanager.view.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.soonfor.warehousemanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 类用途：底部popwindow
 */
public class BottomPopupWindow extends PopupWindow {

    Activity ma;
    View view;

    protected final int LIST_PADDING = 10;
    private Rect mRect = new Rect();
    private final int[] mLocation = new int[2];
    private int mScreenWidth;
    private boolean mIsDirty;
    private int popupGravity = Gravity.NO_GRAVITY;
    private View.OnClickListener mOnClickListener;
    private ListView mListView;
    private List<ActionItem> mActionItems = new ArrayList<ActionItem>();
    PopListAdapter popAdapter;
    private int viewHight = 0;
    public BottomPopupWindow(Activity context, View view) {
        this(context, view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @SuppressWarnings("deprecation")
    public BottomPopupWindow(Activity context, View view, int width, int height) {
        ma = context;
        this.view = view;
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        mScreenWidth = ma.getResources().getDisplayMetrics().widthPixels;
        setWidth(width);
        setHeight(height);

        setBackgroundDrawable(new BitmapDrawable());
        setContentView(LayoutInflater.from(ma).inflate(
                R.layout.view_bottom_popwindow, null));
    }

    private void initUI() {
        mListView = (ListView) getContentView().findViewById(R.id.title_list);
        popAdapter = new PopListAdapter(ma, mActionItems, mOnClickListener);
        mListView.setAdapter(popAdapter);
    }

    public void show(View view) {
        view.getLocationOnScreen(mLocation);

        mRect.set(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(),
                mLocation[1] + view.getHeight());

        if (mIsDirty) {
            populateActions();
        }

        showAtLocation(view, popupGravity, mScreenWidth
                - (getWidth() / 2), mRect.bottom - dip2px(ma,44) * mActionItems.size());
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = ma.getWindow().getAttributes();
        layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
        ma.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        ma.getWindow().setAttributes(layoutParams);

    }

    private void populateActions() {
        mIsDirty = false;
        popAdapter.appendDatas(mActionItems, true);
    }

    public void addAction(List<ActionItem> itmes) {
        if (itmes != null) {
            mActionItems = itmes;
            mIsDirty = true;
        }
    }

    public void cleanAction() {
        if (mActionItems.isEmpty()) {
            mActionItems.clear();
            mIsDirty = true;
        }
    }

    public ActionItem getAction(int position) {
        if (position < 0 || position > mActionItems.size())
            return null;
        return mActionItems.get(position);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        initUI();
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
