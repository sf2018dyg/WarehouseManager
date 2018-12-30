package com.soonfor.warehousemanager.module.allot;

import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/27 0027 13:47
 * 邮箱：suibozhu@139.com
 * 类用途：调拨条件
 */
@Route(path = "/com/soonfor/warehousemanager/home/Allot/AllotConditionActivity")
public class AllotConditionActivity extends BaseActivity<AllotPresenter> implements IAllotView {
    
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_allot_tiaojian;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    public void showUILoading() {
        showQMTipLoading("请稍后", QMUITipDialog.Builder.ICON_TYPE_LOADING);
    }

    @Override
    public void hideUILoading() {
        hideQMTipLoading();
    }

    @Override
    public void setErrorMsg(int code, String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTitles(List<String> lt) {

    }

    @Override
    public void setDatas(String[] titles, List<String[]> data) {

    }
}
