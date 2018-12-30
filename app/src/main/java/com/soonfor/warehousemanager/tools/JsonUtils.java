package com.soonfor.warehousemanager.tools;

import com.soonfor.warehousemanager.bases.DataBean;
import com.soonfor.warehousemanager.bases.HeadBean;
import com.soonfor.warehousemanager.bases.PageTurnBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-02-20.
 */

public class JsonUtils {

    /**
     * 解析json数据的头部
     * {"Data":null,"Msg":null,"MsgCode":"1","Success":true}
     *
     * @return
     */
    public static HeadBean getHeadBean(String jsonStr) {
        HeadBean bean = new HeadBean();
        if (jsonStr != null && !jsonStr.equals("")) {
            try {
                JSONObject json = new JSONObject(jsonStr);
                bean.setStatus(json.getInt("Status"));
                //bean.setSucceed(json.getInt("Succeed"));
                bean.setData(json.getString("Data"));
                bean.setErrorMessage(json.getString("ErrorMessage"));
            } catch (Exception e) {
            }
        }
        return bean;
    }
    /**
     * 修改人：DC-ZhuSuiBo on 2018/8/13 0013 8:59
     * 邮箱：suibozhu@139.com
     * 修改目的：
     */
    public static DataBean getDataBean(String jsonStr){
        DataBean dataBean = new DataBean();
        if (jsonStr != null && !jsonStr.equals("")) {
            try {
                JSONObject json = new JSONObject(jsonStr);
                dataBean.setSuccess(json.getBoolean("success"));
                dataBean.setMsgcode(json.getInt("msgcode"));
                dataBean.setData(json.getString("data"));
                dataBean.setErrormsg(json.getString("errormsg"));
            } catch (Exception e) {
            }
        }
        return dataBean;
    }
    /**
     * 处理解析出的json数据的页码相关部分
     *
     * @return
     */
    public static PageTurnBean getPageBean(String jsonStr) {
        PageTurnBean bean = new PageTurnBean();
        if (jsonStr != null && !jsonStr.equals("")) {
            try {
                JSONObject json = new JSONObject(jsonStr);
                bean.setRowCount(json.getInt("rowCount"));
                bean.setPageNo(json.getInt("pageNo"));
                bean.setPageSize(json.getInt("pageSize"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    public interface OperateData {
        void doingInSuccess(String data);
    }

    /**
     * 返回表格标题和key
     **/
    public static Map<Integer, String[]> getKeyAndTitle(boolean hasCheckBox,JSONObject jo) {
        try {
            Map<Integer, String[]> sarrList = new HashMap<>();
            Iterator iterator = jo.keys();
            String[] tkey = new String[hasCheckBox?jo.names().length()+1:jo.names().length()];
            String[] title = new String[hasCheckBox?jo.names().length()+1:jo.names().length()];
            int index = 0;
            if(hasCheckBox){
                index = 1;
                tkey[0] = "选择";
                title[0] = "选择";
            }
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = jo.getString(key);
                tkey[index] = key;
                title[index] = value;
                index++;
            }
            sarrList.put(0, tkey);
            sarrList.put(1, title);
            return sarrList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据标题的key返回列
     **/
    public static List<String[]> getColumByTitleMap(boolean hasCheckBox,String[] titleKeys, String item, int arrIndex, String lastKey) {
        try {
            List<String[]> lt = new ArrayList<>();
            JSONArray jr = new JSONArray(item);
            for (int i = 0; i < jr.length(); i++) {
                JSONObject o = new JSONObject(jr.get(i).toString());
                String[] srtArray = new String[titleKeys.length];
                int kk = 0;
                if(hasCheckBox){
                    kk = 1;
                    srtArray[0] = "false";
                }

                for (String key : titleKeys) {
                    if(key.equals("选择")){
                        continue;
                    }
                    srtArray[kk] = o.getString(key).equals("null")?"":o.getString(key);
                    kk++;
                }
                if(arrIndex>0 && srtArray[arrIndex].equals(lastKey)){
                    srtArray[0] = "true";
                }
                lt.add(srtArray);
            }
            return lt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回表格标题和key
     **/
    public static String getValue(Map<Integer, String[]> sMap, int position, int index) {
        if (sMap.containsKey(position)) {
            if (index < sMap.get(position).length) {
                return sMap.get(position)[index];
            } else {
                return "";
            }
        }
        return "";
    }
}
