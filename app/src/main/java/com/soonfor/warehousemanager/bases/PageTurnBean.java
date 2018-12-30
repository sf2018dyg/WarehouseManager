package com.soonfor.warehousemanager.bases;

/**
 * Created by Administrator on 2018-03-03.
 */

public class PageTurnBean {
    /**
     * "rowCount":20,
     "pageNo":1,
     "pageSize":10
     */
    private int rowCount;
    private int pageNo;
    private int pageSize;

    public PageTurnBean() {
    }

    public PageTurnBean(int rowCount, int pageNo, int pageSize) {
        this.rowCount = rowCount;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取总页数
     */
    public int getPageCount(){
        //算多少页
        int totalPage = 1;
        if(getRowCount()>1 && getPageSize()>0){
            totalPage = getRowCount() / getPageSize();
            if(getRowCount() % getPageSize() != 0){
                totalPage+=1;
            }
        }
        return totalPage;
    }
}
