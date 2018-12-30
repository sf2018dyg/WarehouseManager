package com.soonfor.warehousemanager.view.popwindow;

public class ActionItem {
    private int id;
    private String mTitle;
    private boolean isEnable;

    public ActionItem(int id, String mTitle, boolean isEnable) {
        this.id = id;
        this.mTitle = mTitle;
        this.isEnable = isEnable;
    }

    public int getId() {
        return id;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
