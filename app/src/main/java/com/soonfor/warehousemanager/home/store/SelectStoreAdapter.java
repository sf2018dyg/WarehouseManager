package com.soonfor.warehousemanager.home.store;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseAdapter;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-07-23 14:31
 * 邮箱：dingyg012655@126.com
 */
public class SelectStoreAdapter extends BaseAdapter<SelectStoreAdapter.ViewHolder, StoreBean>{

    LinearLayoutManager manager;
    RecyclerView mRecyclerView;
    View.OnClickListener listener;
    public SelectStoreAdapter(Context context,LinearLayoutManager manager, RecyclerView mRecyclerView, View.OnClickListener listener) {
        super(context);
        this.manager = manager;
        this.mRecyclerView = mRecyclerView;
        this.listener = listener;
    }

    @Override
    public void notifyDataSetChanged(List<StoreBean> dataList) {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_selectstore, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
        holder.llfSelect.setTag(position);
        holder.llfSelect.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return StoreActivity.sbList==null?0:StoreActivity.sbList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout llfSelect;
        public ImageView checkBox;
        public TextView tvfCode;
        public TextView tvfName;
        public ViewHolder(View itemView) {
            super(itemView);
            llfSelect = itemView.findViewById(R.id.llfSeclect);
            checkBox = itemView.findViewById(R.id.check_store);
            tvfCode = itemView.findViewById(R.id.tvfCode);
            tvfName = itemView.findViewById(R.id.tvfName);
        }

        public void setData(int position){
            StoreBean sb = StoreActivity.sbList.get(position);
            tvfCode.setText(sb.getfStkCode());
            tvfName.setText(sb.getfStkName());
            if(StoreActivity.storeBean!=null && sb.getfStkCode().equals(StoreActivity.storeBean.getfStkCode())){
                checkBox.setImageResource(R.mipmap.checked);
                MoveToPosition(manager, mRecyclerView, position);
            }else {
                checkBox.setImageResource(R.mipmap.unchecked);
            }
        }
    }
}
