package com.soonfor.warehousemanager.home.main;

/**
 * Created by Dingyg on 2017-09-12.
 */

public class GridItemBean {
    private String ctext;
    private int img_id;
    private boolean isOk;
    private String number;
    private Class m_class;

    public GridItemBean(String ctext, int img_id, boolean isOk, String number, Class m_class) {
        this.ctext = ctext;
        this.img_id = img_id;
        this.isOk = isOk;
        this.number = number;
        this.m_class = m_class;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public String getCtext() {
        return ctext;
    }

    public void setCtext(String ctext) {
        this.ctext = ctext;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public Class getM_class() {
        return m_class;
    }

    public void setM_class(Class m_class) {
        this.m_class = m_class;
    }
}
