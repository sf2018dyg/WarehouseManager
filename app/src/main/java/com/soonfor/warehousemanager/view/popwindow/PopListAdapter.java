package com.soonfor.warehousemanager.view.popwindow;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.warehousemanager.R;

import java.util.List;

public class PopListAdapter extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    private List<ActionItem> myList;
    private View.OnClickListener listener;

    public PopListAdapter(Context mContext, List<ActionItem> myList, View.OnClickListener listener) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.myList = myList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        if(myList==null)
            return 0;
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        if(myList==null||myList.size()<=0)
            return null;
        if(position>myList.size())
            return null;
        return myList.get(position);
    }
    public void appendDatas(List<ActionItem> data,boolean isClearOld){
        if(data==null)
            return;
        if(isClearOld)
            myList.clear();
        myList.addAll(data);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ActionItem cItem = myList.get(position);
        final ViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_titlepop, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
       // vh.imgfS.setImageResource(cItem.getmDrawable());
        vh.tvfS.setText(cItem.getmTitle());
//        if(cItem.isEnable()){
//            vh.llfItem.setBackgroundColor(Color.parseColor("#eb433a"));
//            vh.llfItem.setEnabled(true);
//            vh.llfItem.setTag(cItem);
//            vh.llfItem.setOnClickListener(listener);
//        }else {
//            vh.llfItem.setBackgroundColor(Color.parseColor("#cccccc"));
//            vh.llfItem.setEnabled(false);
//        }
        vh.rlfItem.setEnabled(true);
        vh.rlfItem.setTag(cItem);
        vh.rlfItem.setOnClickListener(listener);
        if(position == myList.size()-1){
            vh.rlfItem.setBackgroundColor(Color.parseColor("#00000000"));
        }else {
            vh.rlfItem.setBackgroundColor(Color.parseColor("#eb433a"));
        }
        return convertView;
    }
    class ViewHolder{
        RelativeLayout rlfItem;
        //ImageView imgfS;
        TextView tvfS;
        public ViewHolder(View view){
            rlfItem = (RelativeLayout) view.findViewById(R.id.rlfItem);
           // imgfS = (ImageView) view.findViewById(R.id.imgfS);
            tvfS = (TextView) view.findViewById(R.id.tvfS);
        }
    }

}
