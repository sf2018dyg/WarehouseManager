package com.soonfor.warehousemanager.module.instore.flowtype;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 程序流程实体
 * Created by DingYG on 2017-10-17.
 */

public class FlowTypeBean implements Parcelable{
    /**
     * "0", "包装分拣"
     */
    private String _id;//程序id
    private String _name;//程序名称

    public FlowTypeBean(String _id, String _name) {
        this._id = _id;
        this._name = _name;
    }

    protected FlowTypeBean(Parcel in) {
        _id = in.readString();
        _name = in.readString();
    }

    public static final Creator<FlowTypeBean> CREATOR = new Creator<FlowTypeBean>() {
        @Override
        public FlowTypeBean createFromParcel(Parcel in) {
            return new FlowTypeBean(in);
        }

        @Override
        public FlowTypeBean[] newArray(int size) {
            return new FlowTypeBean[size];
        }
    };

    public String get_id() {
        return _id==null || _id.equals("")?"0":_id;
    }
    public String get_name() {
        return _name==null?"":_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(_name);
    }
}
