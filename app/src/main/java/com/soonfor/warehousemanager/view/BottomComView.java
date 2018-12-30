package com.soonfor.warehousemanager.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.orhanobut.hawk.Hawk;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.module.instore.IInStoreView;
import com.soonfor.warehousemanager.module.outstore.IOutStoreView;
import com.soonfor.warehousemanager.view.popwindow.ActionItem;
import com.soonfor.warehousemanager.view.popwindow.BottomPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：DC-DingYg on 2018/12/6 0006 11:19
 * 类用途：底部共用View
 */
public class BottomComView extends LinearLayout {

    @BindView(R.id.llfCreateChOrder)
    LinearLayout llfCreateChOrder;
    @BindView(R.id.bnfIsOk)
    Button bnfIsOk;//确认
    @BindView(R.id.imgfBottom)
    ImageView imgfBottom;
    @BindView(R.id.rlfShowPop)
    RelativeLayout rlfShowPop;//
    BottomPopupWindow popupWindow;
    View.OnClickListener chorderListener;
    private boolean isNotScanVisable = false;

    public BottomComView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_bottom_comview, this);
        ButterKnife.bind(this, view);
        this.chorderListener = null;
    }

    public void setChorderListener(OnClickListener chorderListener) {
        this.chorderListener = chorderListener;
    }

    public void initBottomView(Activity mActivity, IInStoreView putInView, IOutStoreView putoutView, View.OnClickListener okOnClick,
                               View.OnClickListener itemOnClick, boolean isVisable, CheckBox checkBox){
        bnfIsOk.setOnClickListener(okOnClick);
        isNotScanVisable = isVisable;
        // 实例化标题栏弹窗
        popupWindow = new BottomPopupWindow(mActivity, rlfShowPop, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOnClickListener(itemOnClick);

        rlfShowPop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popupWindow!=null){
                    if(popupWindow.isShowing()){
                        popupWindow.dismiss();
                    }else {
                        if(checkBox!=null) {
                            setPopView(!checkBox.isChecked());
                        }else {
                            setPopView(isNotScanVisable);
                        }
                        popupWindow.show(imgfBottom);
                    }
                }
            }
        });
        if(checkBox!=null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    Hawk.put("OutCbDel", isChecked);
                    if (isChecked) {
                        //2018-12-19 lilj注释 不能直接锁死
                        //checkBox.setEnabled(false);
                        isNotScanVisable = false;
                        if(putInView!=null) {
                            putInView.delHeBaoData(false);
                        }else if(putoutView!=null){
                            putoutView.delHeBaoData(false, false);
                        }
                    } else {
                        isNotScanVisable = true;
                    }
                }
            });
        }
        if(chorderListener!=null){
            llfCreateChOrder.setVisibility(VISIBLE);
            llfCreateChOrder.setOnClickListener(chorderListener);
        }else {
            llfCreateChOrder.setVisibility(GONE);
        }
    }
    private void setPopView(boolean isVisable){
        // 给弹窗添加子类
        List<ActionItem> aList = new ArrayList<>();
        aList.add(new ActionItem(1, getResources().getString(R.string.scan_delete), true));
        aList.add(new ActionItem(2,   getResources().getString(R.string.clear_rescan),true));
        if(isVisable) aList.add(new ActionItem(3, getResources().getString(R.string.notscan_query),true));
        aList.add(new ActionItem(4, "",true));
        popupWindow.addAction(aList);
    }

//    /**
//     *
//     * @param isChOrderVisable 产生出货单按钮是否显示
//     * @param isNotScanQueryVisable 未扫条码查询是否显示
//     */
//    public void setIfVisible(boolean isChOrderVisable, boolean isNotScanQueryVisable){
//        popupWindow.showPopupWindow(imgfShowPop);
//    }
    public void setIsOkEnable(boolean isOKCan){
        bnfIsOk.setEnabled(isOKCan);
    }

    public BottomPopupWindow getPopupWindow() {
        return popupWindow;
    }
}
