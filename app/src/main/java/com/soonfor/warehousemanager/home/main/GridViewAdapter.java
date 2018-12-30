package com.soonfor.warehousemanager.home.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.warehousemanager.R;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-07-23 18:43
 * 邮箱：dingyg012655@126.com
 */
public class GridViewAdapter extends android.widget.BaseAdapter {

    List<GridItemBean> gibList;
    protected Context context;

    public GridViewAdapter(Context context, List<GridItemBean> gibList) {
        this.context = context;
        this.gibList = gibList;
    }

    @Override
    public int getCount() {
        return gibList==null?0:gibList.size();
    }

    @Override
    public GridItemBean getItem(int position) {
        return gibList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /*
       刷新数据
        */
    public void appendData(List<GridItemBean> data,boolean isClearOld){
        if(data==null){
            return;
        }else if(isClearOld){
            gibList.clear();
        }
        gibList.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridItemBean itemBean = gibList.get(position);
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        try {
            vh.imageView.setImageResource(itemBean.getImg_id());
            vh.textView.setText(itemBean.getCtext());
        } catch (Exception e) {
        }
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView) {
            imageView = itemView.findViewById(R.id.imgfItemPic);
            textView = itemView.findViewById(R.id.tvfItemName);
        }
    }
}
