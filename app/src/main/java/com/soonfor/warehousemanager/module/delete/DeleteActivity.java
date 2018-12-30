package com.soonfor.warehousemanager.module.delete;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.tools.CommUtil;
import com.soonfor.warehousemanager.view.TableViewDetail;

import java.util.List;

import butterknife.BindView;

/**
 * 删除
 */
public class DeleteActivity extends BaseActivity<DeletePresenter> implements IDeleteView {

    DeletePresenter presenter;
    @BindView(R.id.tablell)
    LinearLayout tablell;
    private TableViewDetail tableView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_delete;
    }

    @Override
    protected void initPresenter() {
        presenter = new DeletePresenter(this);
    }

    @Override
    protected void initViews() {
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("删除");

        emptyView.show(true);
        presenter.getDatas();
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
    public void setDatas(String[] titles, List<String[]> data) {
        emptyView.hide();
        if (tableView != null) {
            tablell.removeAllViews();
        }
        int[] tableWith = CommUtil.SetComWidth(DeleteActivity.this, titles.length, 0, false);
        tableView = new TableViewDetail(DeleteActivity.this,
                tablell, titles, data, tableWith,false,null);
    }
}
