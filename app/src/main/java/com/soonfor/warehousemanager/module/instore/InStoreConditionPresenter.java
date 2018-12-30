package com.soonfor.warehousemanager.module.instore;

import com.soonfor.warehousemanager.bases.BasePresenter;
import com.soonfor.warehousemanager.module.instore.beans.InStoreConditionBean;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/11 0011 8:53
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class InStoreConditionPresenter extends BasePresenter<IInStoreConditionView>{

    private IInStoreConditionView view;
    public InStoreConditionPresenter(IInStoreConditionView view) {
        this.view = view;
    }

    public InStoreConditionBean getNewValue(InStoreConditionBean oldBean){
        InStoreConditionBean newBean = new InStoreConditionBean();
        newBean.setFbillCode(oldBean.getFbillCode());
        newBean.setFbillName(oldBean.getFbillName());
        newBean.setFrukubillCode(oldBean.getFrukubillCode());
        newBean.setFrukubillName(oldBean.getFrukubillName());
        newBean.setFdeptCode(oldBean.getFdeptCode());
        newBean.setFdeptName(oldBean.getFdeptName());
        newBean.setFreasonCode(oldBean.getFreasonCode());
        newBean.setFreasonDesc(oldBean.getFreasonDesc());
        newBean.setFbinlocationCode(oldBean.getFbinlocationCode());
        newBean.setFbinlocationName(oldBean.getFbinlocationName());
        return newBean;
    }
}
