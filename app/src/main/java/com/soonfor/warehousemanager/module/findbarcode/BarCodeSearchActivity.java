package com.soonfor.warehousemanager.module.findbarcode;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.CommonTabLayout;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.entity.TabEntity;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.listener.CustomTabEntity;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.listener.OnTabSelectListener;
import com.soonfor.warehousemanager.view.TableViewDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 条码查询
 */
public class BarCodeSearchActivity extends BaseActivity<BarCodeSearchPresenter> implements IBarCodeSearchView {

    BarCodeSearchPresenter presenter;
    @BindView(R.id.tl_1)
    CommonTabLayout tl_1;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @BindView(R.id.tablell)
    LinearLayout tablell;
    private TableViewDetail tableView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_bar_code_search;
    }

    @Override
    protected void initPresenter() {
        presenter = new BarCodeSearchPresenter(this);
    }

    @Override
    protected void initViews() {
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("条码查询");

        //请求标题
        presenter.getTitle();
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
        for (int i = 0; i < lt.size(); i++) {
            mTabEntities.add(new TabEntity(lt.get(i), R.mipmap.checked, R.mipmap.checked));
        }
        tl_1.setTabData(mTabEntities);
        tl_1.setCurrentTab(0);
        tl_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        emptyView.show(true);
                        presenter.getDatas();
                        break;
                    case 1:
                        emptyView.show(true);
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void setDatas(String[] titles, List<String[]> data) {
        emptyView.hide();
        if (tableView != null) {
            tablell.removeAllViews();
        }
        int[] tableWith = CommUtil.SetComWidth(BarCodeSearchActivity.this, titles.length, 0, false);
        tableView = new TableViewDetail(BarCodeSearchActivity.this,
                tablell, titles, data, tableWith,false,null);
    }

}
