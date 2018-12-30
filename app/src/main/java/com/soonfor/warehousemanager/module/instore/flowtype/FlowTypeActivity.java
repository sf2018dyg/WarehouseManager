package com.soonfor.warehousemanager.module.instore.flowtype;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by DingYG on 2018-07-24.
 */

/**
 * 修改人：DC-ZhuSuiBo on 2018/8/8 0008 14:30
 * 邮箱：suibozhu@139.com
 * 修改目的：
 */
public class FlowTypeActivity extends BaseActivity {

    FlowTypeActivity mActivity;
    @BindView(R.id.rlfBottom)
    RelativeLayout rlfBottom;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    FlowTypeAdapter stAdapter;
    String buttonType;
    ArrayList<FlowTypeBean> stList = null;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_dialog_storetypes;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        mActivity = FlowTypeActivity.this;
        try {
            buttonType = getIntent().getExtras().getString("BUTTON_TYPE", null);
        } catch (Exception e) {
        }
        try {
            stList = getIntent().getParcelableArrayListExtra("STORETYPES");
        } catch (Exception e) {
        }
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        stAdapter = new FlowTypeAdapter(mActivity, stList, buttonType);
        // 设置adapter
        mRecyclerView.setAdapter(stAdapter);

        rlfBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
