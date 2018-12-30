package com.soonfor.warehousemanager.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.tools.DoubleClickU;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/26 0026 16:50
 * 邮箱：suibozhu@139.com
 * 类用途：PDA风格专用
 */
public class TableViewDetail extends LinearLayout {

    private static LayoutParams FILL_FILL_LAYOUTPARAMS;
    private static LayoutParams WAP_WAP_LAYOUTPARAMS;

    private static Paint BLACK_PAINT = new Paint();
    private static Paint WHITE_PAINT = new Paint();

    static {
        WHITE_PAINT.setColor(Color.WHITE);
        BLACK_PAINT.setColor(Color.BLACK);
    }

    private CAdapter cAdapter;

    /**
     * 标题空间.
     */
    private LinearLayout titleLayout;
    private String[] title;

    private ListView listView;
    /**
     * 数据.
     */
    private List<String[]> data;

    /**
     * 列宽数据.
     */
    private int[] itemWidth;

    /**
     * textview 的行高
     **/
    private int lineHeight;

    /**
     * 当前选中行.
     */
    private int selectedPosition = -1;
    /**
     * 自动列宽列.
     */
    private int autoWidthIndex = -1;

    private AdapterView.OnItemClickListener onItemClickListener;

    /**
     * 行背景颜色.
     */
    private int[] rowsBackgroundColor = new int[]{Color.parseColor("#f0f0f0"), Color.parseColor("#ffffff")};
    /**
     * 选中行背景颜色.
     */
    private int selectedBackgroundColor = Color.parseColor("#efddc9");
    /**
     * 标题背景颜色.
     */
    private int titleBackgroundColor = Color.parseColor("#d2d2d2");
    /**
     * 标题字体颜色.
     */
    private int titleTextColor = Color.parseColor("#565656");
    /**
     * 内容字体颜色.
     */
    private int contentTextColor = Color.parseColor("#565656");
    /**
     * 标题字体大小.
     */
    private float titleTextSize = 13;
    /**
     * 内容字体大小.
     */
    private float contentTextSize = 13;

    /**
     * 按钮的高度
     **/
    private int btHeight;

    /**
     * 按钮的宽
     **/
    private int btWidth;

    /**
     * 按钮的字体大小
     **/
    private float btTextSize = 10;

    /**
     * 控制按钮可不可用
     **/
    private boolean btIsEnable = true;

    /**
     * 是否启用数量列 的可编辑
     **/
    private boolean isNumberEnable = false;

    /**
     * 是否启用checkbox
     **/
    private boolean isCheckBox = false;

    /**
     * checkbox对应的选中事件
     **/
    private CompoundButton.OnCheckedChangeListener checkedChangeListener;

    /**
     * 明确哪一列是数量列
     **/
    private int NumberColumn = -1;

    private Context mContext;

    /**
     * 初始化带标题ListView
     *
     * @param context               父级上下文
     * @param tablell               生成的位置
     * @param title                 标题数组
     * @param data                  内容列表
     * @param itemwidth             每一列的宽度
     * @param isCheckBox            是否启用多选框
     * @param checkedChangeListener 多选框的选中事件
     */
    public TableViewDetail(Context context, LinearLayout tablell, String[] title,
                           List<String[]> data, int[] itemwidth, boolean isCheckBox,
                           CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.data = data;
        this.isCheckBox = isCheckBox;
        this.checkedChangeListener = checkedChangeListener;
        this.lineHeight = CommUtil.dip2px(context, 40);
        this.btWidth = CommUtil.dip2px(context, 50);
        this.btHeight = CommUtil.dip2px(context, 26);

        FILL_FILL_LAYOUTPARAMS = new LayoutParams(LayoutParams.FILL_PARENT,
                lineHeight, 1);
        WAP_WAP_LAYOUTPARAMS = new LayoutParams(WRAP_CONTENT,
                lineHeight);

        // 设定纵向布局
        setOrientation(VERTICAL);
        // 设定背景为白色
        setBackgroundColor(Color.WHITE);

        // 预先设定好每列的宽
        this.itemWidth = itemwidth;// new int[title.length];
        autoWidthIndex = this.itemWidth.length - 1;
        // 计算列宽
        // calcColumnWidth();

        // 添加title位置
        titleLayout = new LinearLayout(getContext());
        titleLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        titleLayout.setVerticalGravity(Gravity.CENTER_VERTICAL);
        tablell.addView(titleLayout);
        // 绘制标题面板
        drawTitleLayout();
        // 添加listview
        listView = new ListView(getContext());
        cAdapter = new CAdapter();
        listView.setAdapter(cAdapter);
        listView.setCacheColorHint(0);
        listView.setVerticalScrollBarEnabled(false);
        listView.setLayoutParams(FILL_FILL_LAYOUTPARAMS);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(parent, view, position, id);
                    setSelectedPosition(position);
                    selectedPosition = position;
                    cAdapter.notifyDataSetChanged();
                } catch (NullPointerException e) {
                }
            }
        });
        tablell.addView(listView);
    }

    public LinearLayout getTitleLayout() {
        return titleLayout;
    }

    public void setTitleLayout(LinearLayout titleLayout) {
        this.titleLayout = titleLayout;
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    /**
     * 设置选中时的监听器
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(
            AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置行背景颜色, 多个颜色可以作为间隔色
     *
     * @param color 行背景颜色，可以为多个
     */
    public void setItemBackgroundColor(int... color) {
        rowsBackgroundColor = color;
    }

    /**
     * 数据总数
     */
    public int getCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    /**
     * 当前选中数据
     *
     * @param position
     * @return
     */
    public String[] getItem(int position) {
        if (data == null)
            return null;
        return data.get(position);
    }

    public List<String[]> getAlldata() {
        return data;
    }

    public CAdapter getcAdapter() {
        return cAdapter;
    }

    public void setcAdapter(CAdapter cAdapter) {
        this.cAdapter = cAdapter;
    }

    /**
     * 设置当前选中位置
     *
     * @return
     */
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    /**
     * 当前选中位置
     *
     * @return
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * 设置被选中时的背景色
     *
     * @param color
     */
    public void setSelectedBackgroundColor(int color) {
        selectedBackgroundColor = color;
    }

    /**
     * 设置标题背景色.
     *
     * @param color
     */
    public void setTitleBackgroundColor(int color) {
        titleBackgroundColor = color;
        titleLayout.setBackgroundColor(titleBackgroundColor);
    }

    /**
     * 设置标题文字颜色
     *
     * @param color
     */
    public void setTitleTextColor(int color) {
        titleTextColor = color;
        for (int i = 0; i < titleLayout.getChildCount(); i++) {
            ((TextView) titleLayout.getChildAt(i)).setTextColor(titleTextColor);
        }
    }

    /**
     * 设置内容文字颜色
     *
     * @param color
     */
    public void setContentTextColor(int color) {
        contentTextColor = color;
    }

    /**
     * 设置标题字体大小
     *
     * @param szie
     */
    public void setTitleTextSize(float szie) {
        titleTextSize = szie;
    }

    /**
     * 设置内容字体大小
     *
     * @param szie
     */
    public void setContentTextSize(float szie) {
        contentTextSize = szie;
    }

    /**
     * 绘制标题
     */
    private void drawTitleLayout() {
        titleLayout.removeAllViews();
        buildSubTitle(0);
    }

    private void buildSubTitle(int start) {
        while (start < title.length) {
            TextView tv = new CTextView(titleLayout.getContext());
            tv.setTextColor(Color.parseColor("#333333"));
            tv.setGravity(Gravity.CENTER);
            tv.setText(title[start]);
            if (titleTextSize > 0) {
                tv.setTextSize(titleTextSize);
            }
//            if (start == title.length - 1) {
//                tv.setPadding(0, 0, 0, 0);
//            } else {
                tv.setPadding(10, 0, 10, 0);
//            }

            tv.setWidth(itemWidth[start]);
            tv.setHeight(lineHeight);
            titleLayout.addView(tv);
            start++;

            if (start < title.length) {
                View view = new View(titleLayout.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(CommUtil.dip2px(titleLayout.getContext(), 2), ViewGroup.LayoutParams.MATCH_PARENT);//宽度、高度
                params.topMargin = CommUtil.dip2px(titleLayout.getContext(), 8);
                params.bottomMargin = CommUtil.dip2px(titleLayout.getContext(), 8);
                view.setLayoutParams(params);
                view.setBackgroundColor(Color.parseColor("#66232323"));
                titleLayout.addView(view);
            }
        }
    }

    /**
     * 计算字符串所占像素
     *
     * @param textSize 字体大小
     * @param text     字符串
     * @return 字符串所占像素
     */
    private static int GetPixelByText(float textSize, String text) {
        Paint mTextPaint = new Paint();
        mTextPaint.setTextSize(textSize); // 指定字体大小
        mTextPaint.setFakeBoldText(true); // 粗体
        mTextPaint.setAntiAlias(true); // 非锯齿效果

        return (int) (mTextPaint.measureText(text) + textSize);
    }

    /**
     * 主要用的Adapter
     */
    public class CAdapter extends BaseAdapter {

        /*
         * (non-Javadoc)
         *
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            if (data == null)
                return 0;
            return data.size();
        }

        /*
         * (non-Javadoc)
         *
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Object getItem(int position) {
            if (data == null)
                return null;
            return data.get(position);
        }

        /*
         * (non-Javadoc)
         *
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int position) {
            return 0;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.widget.Adapter#getView(int, android.view.View,
         * android.view.ViewGroup)
         */
        @SuppressLint("ResourceType")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 初始化主layout
            LinearLayout contextLayout = new LinearLayout(
                    TableViewDetail.this.getContext());

            String[] dataItem = data.get(position);

            contextLayout.setGravity(Gravity.CENTER);

            if (getSelectedPosition() == position) { // 为当前选中行
                contextLayout.setBackgroundColor(selectedBackgroundColor);
            } else if (rowsBackgroundColor != null
                    && rowsBackgroundColor.length > 0) {
                contextLayout.setBackgroundColor(rowsBackgroundColor[position
                        % rowsBackgroundColor.length]);
            }

            if (isCheckBox) {
                //底布的参数
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(itemWidth[0], lineHeight);
                LinearLayout layout = new LinearLayout(contextLayout.getContext());
                layout.setOrientation(HORIZONTAL);
                layout.setGravity(Gravity.CENTER);
                layout.setLayoutParams(layoutParams);
                //layout.setBackgroundColor(Color.parseColor("#ebebeb"));
                CheckBox cb = new CCheckBox(contextLayout.getContext());
                cb.setId(999999);
                cb.setTag(position);
                cb.setOnCheckedChangeListener(checkedChangeListener);
                cb.setChecked(false);
                if (dataItem[0].equals("true")) {
                    cb.setButtonDrawable(R.mipmap.checked);
                } else {
                    cb.setButtonDrawable(R.mipmap.unchecked);
                }
                layout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cb.isChecked()) {
                            cb.setChecked(false);
                        } else {
                            cb.setChecked(true);
                        }
                    }
                });
                layout.addView(cb);
                contextLayout.addView(layout);

                buildSubItem(1, contextLayout, dataItem);
            } else {
                buildSubItem(0, contextLayout, dataItem);
            }


            return contextLayout;
        }
    }

    private void buildSubItem(int start, LinearLayout contextLayout, String[] dataItem) {
        int lastPost = title.length - 1;
        while (start <= lastPost) {
            contextLayout.addView(CreateCTextView(contextLayout, dataItem, start));
            start++;
        }
    }

    /**
     * 创建一个ctextview
     **/
    private TextView CreateCTextView(LinearLayout contextLayout, String[] dataItem, int posi) {
        TextView tv = new CTextView(contextLayout.getContext());
        tv.setGravity(Gravity.CENTER);
        if (posi < dataItem.length) {
            if (dataItem[posi] == null || dataItem[posi].equals("")) {
                tv.setText("<空>");
            } else {
                tv.setText(dataItem[posi]);
            }
        }
        tv.setTextColor(Color.parseColor("#666666"));
        //tv.setBackgroundColor(Color.parseColor("#eb433a"));
        tv.setWidth(itemWidth[posi]);
        tv.setHeight(lineHeight);
        tv.setPadding(0, 0, 0, 0);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        if (contentTextSize > 0) {
            tv.setTextSize(contentTextSize);
        }
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DoubleClickU.isFastDoubleClick()) {
                    TextView vv = (TextView) v;
                    if (!vv.getText().toString().equals("")) {
                        QMUIPopup mNormalPopup = new QMUIPopup(getContext(), QMUIPopup.DIRECTION_NONE);
                        TextView textView = new TextView(getContext());
                        textView.setLayoutParams(mNormalPopup.generateLayoutParam(
                                QMUIDisplayHelper.dp2px(getContext(), 250),
                                WRAP_CONTENT
                        ));
                        textView.setLineSpacing(QMUIDisplayHelper.dp2px(getContext(), 4), 1.0f);
                        int padding = QMUIDisplayHelper.dp2px(getContext(), 20);
                        textView.setPadding(padding, padding, padding, padding);
                        textView.setText(vv.getText().toString() + "");
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.text));
                        mNormalPopup.setContentView(textView);
                        mNormalPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {

                            }
                        });
                        mNormalPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
                        mNormalPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
                        mNormalPopup.show(v);
                    }
                }
            }
        });
        //returnColor(tv);
        return tv;
    }

    /**
     * 重写的TextView
     */
    @SuppressLint("AppCompatCustomView")
    class CTextView extends TextView {

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

        }

        public CTextView(Context context) {
            super(context);
        }
    }

    /**
     * 重写的checkbox
     */
    @SuppressLint("AppCompatCustomView")
    class CCheckBox extends CheckBox {

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //画圆角
        }

        public CCheckBox(Context context) {
            super(context);
        }
    }
}
