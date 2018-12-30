package com.soonfor.warehousemanager.tools;

import java.io.Serializable;
import java.util.Map;

/**
 * 作者：DC-ZhuSuiBo on 2018/10/26 0026 14:08
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class SerMap implements Serializable {
    public Map<String,String[]> map;

    public Map<String, String[]> getMap() {
        return map;
    }

    public void setMap(Map<String, String[]> map) {
        this.map = map;
    }
}
