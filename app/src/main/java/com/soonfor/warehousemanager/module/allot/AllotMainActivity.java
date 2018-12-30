package com.soonfor.warehousemanager.module.allot;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.module.delete.DeleteActivity;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.CommonTabLayout;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.entity.TabEntity;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.listener.CustomTabEntity;
import com.soonfor.warehousemanager.view.FlycoTabLayout_Lib.listener.OnTabSelectListener;
import com.soonfor.warehousemanager.view.TableViewDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/26 0026 15:56
 * 邮箱：suibozhu@139.com
 * 类用途：分包转仓调拨
 */
public class AllotMainActivity extends BaseActivity<AllotPresenter> implements IAllotView {

    AllotPresenter presenter;
    @BindView(R.id.tvfRightSet)
    TextView tvfRightSet;
    @BindView(R.id.tl_1)
    CommonTabLayout tl_1;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @BindView(R.id.tablell)
    LinearLayout tablell;
    private TableViewDetail tableView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_allot_main;
    }

    @Override
    protected void initPresenter() {
        presenter = new AllotPresenter(this);
    }

    @Override
    protected void initViews() {
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("分包转仓调拨");
        tvfRightSet.setVisibility(View.VISIBLE);
        tvfRightSet.setText("调拨条件");

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
        int[] tableWith = CommUtil.SetComWidth(AllotMainActivity.this, titles.length, 0, false);
        tableView = new TableViewDetail(AllotMainActivity.this,
                tablell, titles, data, tableWith,false,null);
    }

    @OnClick({R.id.tvfRightSet, R.id.txt_scanDel})
    public void OnViewClick(View view) {
        if (view.getId() == R.id.tvfRightSet) {
            startActivity(new Intent(AllotMainActivity.this, AllotConditionActivity.class));
        } else if (view.getId() == R.id.txt_scanDel) {
            startNewAct(DeleteActivity.class);
        }
    }
}
