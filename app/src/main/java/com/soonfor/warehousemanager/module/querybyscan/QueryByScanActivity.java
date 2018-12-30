package com.soonfor.warehousemanager.module.querybyscan;

import android.content.Intent;

import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.dialog.QrCodeDialog;
import com.soonfor.warehousemanager.view.BaseQueryView;

import butterknife.BindView;

/**
 * 作者：DC-DingYG on 2018-07-25 9:43
 * 邮箱：dingyg012655@126.com
 */
public class QueryByScanActivity extends BaseActivity<QueryByScanPresenter> implements IQueryByScanView,BaseQueryView.Post {

    QueryByScanActivity mActivity;
    @BindView(R.id.queryView)
    BaseQueryView queryView;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_querybyscan;
    }

    @Override
    protected void initPresenter() {
        presenter = new QueryByScanPresenter(this);
        mActivity = QueryByScanActivity.this;
    }

    @Override
    protected void initViews() {
        queryView.initQueryView(mActivity, "", this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String qrcode = QrCodeDialog.getStringFromImage(mActivity, data, requestCode, resultCode);
        if(qrcode==null || qrcode.equals("")){
            MyToast.showToast(mActivity, "未扫描到条码信息！");
        }else {

        }
    }

    @Override
    public void postData(String inputText) {

    }
}
