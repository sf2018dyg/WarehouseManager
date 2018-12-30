package com.soonfor.warehousemanager.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * Created by Dingyg on 2017-09-30.
 * 自定义GridView
 * 使用处理在ScrollView里套用GrildView时显示不全的BUG，设置让ScrillView滑动，CustomGlridView不可滑动
 */

public class CustomGlridView extends GridView {
    public CustomGlridView(Context context) {
        super(context);
    }

    public CustomGlridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGlridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
