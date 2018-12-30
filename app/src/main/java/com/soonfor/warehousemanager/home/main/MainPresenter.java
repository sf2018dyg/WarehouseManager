package com.soonfor.warehousemanager.home.main;

import android.content.Context;

import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.module.print.PrinterActivity;
import com.soonfor.warehousemanager.module.instore.flowtype.FlowTypeActivity;
import com.soonfor.warehousemanager.module.instore.flowtype.FlowTypeBean;
import com.soonfor.warehousemanager.tools.Tokens;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-07-23 18:20
 * 邮箱：dingyg012655@126.com
 */
public class MainPresenter extends BasePresenter<IMainView> implements AsyncUtils.AsyncCallback {

    private IMainView view;

    public MainPresenter(IMainView view) {
        this.view = view;
    }

    /**
     * 获取GridView数据
     */
    public void getGridItemDatas() {
        List<GridItemBean> gridList = new ArrayList<GridItemBean>();
        gridList.add(new GridItemBean("入库", R.mipmap.index_menu1, true, "1", FlowTypeActivity.class));
        gridList.add(new GridItemBean("出库", R.mipmap.index_menu2, true, "2", FlowTypeActivity.class));
        gridList.add(new GridItemBean("打印", R.mipmap.barcode_printing, true, "3", PrinterActivity.class));
        //gridList.add(new GridItemBean("调拨", R.mipmap.index_menu3, true, "0", TiaoboMainActivity.class));
        //gridList.add(new GridItemBean("条码", R.mipmap.index_menu1, true, "0", QueryByScanActivity.class));
        view.showGrildView(gridList);
    }

    /**
     * 获取入库类型数据
     *
     * @param context
     * @param usercode
     * @param storeId
     */
    public void getPuInTypes(Context context, String usercode, String storeId) {
        view.showLoadingDialog();
//        Request.getStoreTypes(context,usercode, storeId, this);
        ArrayList<FlowTypeBean> flowTypeBeanList = new ArrayList<>();
        flowTypeBeanList.add(new FlowTypeBean("0", "包装分拣"));
        flowTypeBeanList.add(new FlowTypeBean("4", "合包扫描"));
        flowTypeBeanList.add(new FlowTypeBean("1", "分包入库"));
        flowTypeBeanList.add(new FlowTypeBean("2", "分包入库退库"));
        flowTypeBeanList.add(new FlowTypeBean("3", "分包生产入库"));
        view.closeLoadingDialog();
        view.setGetTypes(Tokens.StoreType.PUTIN, flowTypeBeanList);
    }

    /**
     * 获取出类型数据
     *
     * @param context
     * @param usercode
     * @param storeId
     */
    public void getPuOutTypes(Context context, String usercode, String storeId) {
        view.showLoadingDialog();
//        Request.getStoreTypes(context,usercode, storeId, this);
        ArrayList<FlowTypeBean> flowTypeBeanList = new ArrayList<>();
        flowTypeBeanList.add(new FlowTypeBean("0", "分包备货"));
        flowTypeBeanList.add(new FlowTypeBean("1","分包出货"));
        view.closeLoadingDialog();
        view.setGetTypes(Tokens.StoreType.PUTOUT, flowTypeBeanList);
    }

    public void LogOut(Context context) {
        Request.LogOut(context, this);
    }

    @Override
    public void success(int requestCode, String data) {
        view.closeLoadingDialog();
        switch (requestCode) {
            case Request.LOGOUT:
                view.setLogout(true);
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String data, String msg) {
        view.closeLoadingDialog();
        switch (requestCode) {
            case Request.LOGOUT:
                view.setLogout(false);
                break;
        }
    }
}
