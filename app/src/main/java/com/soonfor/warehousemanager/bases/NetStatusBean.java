package com.soonfor.warehousemanager.bases;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/19 0019 08:32
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
@Entity(nameInDb = "NetStatusBean")
public class NetStatusBean {
    @Property(nameInDb = "name")
    @Id
    private String name;//页面名称  入库 ruku 出货 chuku
    @Property(nameInDb = "yingsao")
    private int yingsao;//应扫
    @Property(nameInDb = "yisao")
    private int yisao;//已扫
    @Property(nameInDb = "benci")
    private int benci;//本次
    @Property(nameInDb = "weisao")
    private int weisao;//未扫

    @Generated(hash = 549672571)
    public NetStatusBean(String name, int yingsao, int yisao, int benci,
                         int weisao) {
        this.name = name;
        this.yingsao = yingsao;
        this.yisao = yisao;
        this.benci = benci;
        this.weisao = weisao;
    }

    @Generated(hash = 1928631904)
    public NetStatusBean() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYingsao() {
        return this.yingsao;
    }

    public void setYingsao(int yingsao) {
        this.yingsao = yingsao;
    }

    public int getYisao() {
        return this.yisao;
    }

    public void setYisao(int yisao) {
        this.yisao = yisao;
    }

    public int getBenci() {
        return this.benci;
    }

    public void setBenci(int benci) {
        this.benci = benci;
    }

    public int getWeisao() {
        return this.weisao;
    }

    public void setWeisao(int weisao) {
        this.weisao = weisao;
    }


}
