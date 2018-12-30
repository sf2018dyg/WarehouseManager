package com.soonfor.warehousemanager.module.instore.flowtype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseAdapter;
import com.soonfor.warehousemanager.module.instore.InStoreActivity;
import com.soonfor.warehousemanager.module.outstore.OutStoreActivity;
import com.soonfor.warehousemanager.tools.Tokens;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-07-24 14:56
 * 邮箱：dingyg012655@126.com
 */
public class FlowTypeAdapter extends BaseAdapter<FlowTypeAdapter.ViewHolder, FlowTypeBean> implements View.OnClickListener{

    List<FlowTypeBean> stList;
    FlowTypeActivity mActivity;
    String buttonType;
    public FlowTypeAdapter(FlowTypeActivity context, List<FlowTypeBean> stList, String buttonType) {
        super(context);
        this.stList = stList;
        mActivity = context;
        this.buttonType = buttonType;
    }

    @Override
    public void notifyDataSetChanged(List<FlowTypeBean> dataList) {
        stList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_storetypes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FlowTypeBean stBean = stList.get(position);
        if (position == 0) {
            holder.imgfFenge.setVisibility(View.GONE);
        } else {
            holder.imgfFenge.setVisibility(View.VISIBLE);
        }
        holder.tvfBank.setText(stBean.get_name());
        holder.llfItem.setTag(R.id.tag_position, position);
        holder.llfItem.setTag(R.id.tag_viewholder, holder);
        holder.llfItem.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return stList==null?0:stList.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.tag_position);
        ViewHolder vh = (ViewHolder) v.getTag(R.id.tag_viewholder);
        Bundle bundle = new Bundle();
        bundle.putParcelable("SELECT_STORETYPE", stList.get(position));
        if(buttonType!=null){
            Intent intent = new Intent();
            if(buttonType.equals(Tokens.StoreType.PUTIN)){
                intent.setClass(mActivity, InStoreActivity.class);
            }else if(buttonType.equals(Tokens.StoreType.PUTOUT)){
                intent.setClass(mActivity, OutStoreActivity.class);
            }
            intent.putExtras(bundle);
            mActivity.startActivity(intent);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout llfItem;
        public ImageView imgfFenge;
        public TextView tvfBank;

        public ViewHolder(View itemView) {
            super(itemView);
            this.llfItem = itemView.findViewById(R.id.llfItem);
            this.imgfFenge = (ImageView) itemView.findViewById(R.id.imgfFenge);
            this.tvfBank = (TextView) itemView.findViewById(R.id.tvfBank);
        }
    }
}
