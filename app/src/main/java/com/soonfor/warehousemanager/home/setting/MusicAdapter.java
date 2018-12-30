package com.soonfor.warehousemanager.home.setting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseAdapter;
import com.soonfor.warehousemanager.tools.DateUtils;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-07-23 14:31
 * 邮箱：dingyg012655@126.com
 * 选择音频的适配器
 */
public class MusicAdapter extends BaseAdapter<MusicAdapter.ViewHolder, Mp3Info>{

    LinearLayoutManager manager;
    RecyclerView mRecyclerView;
    View.OnClickListener listener;
    public MusicAdapter(Context context, LinearLayoutManager manager, RecyclerView mRecyclerView, View.OnClickListener listener) {
        super(context);
        this.manager = manager;
        this.mRecyclerView = mRecyclerView;
        this.listener = listener;
    }

    @Override
    public void notifyDataSetChanged(List<Mp3Info> dataList) {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
        holder.rlfSelect.setTag(position);
        holder.rlfSelect.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return MusicActivity.mp3List==null?0:MusicActivity.mp3List.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout rlfSelect;
        public ImageView checkBox;
        public TextView tvfName;
        public TextView tvfSize;
        public ViewHolder(View itemView) {
            super(itemView);
            rlfSelect = itemView.findViewById(R.id.rlfSeclect);
            checkBox = itemView.findViewById(R.id.check_store);
            tvfName = itemView.findViewById(R.id.tvfName);
            tvfSize = itemView.findViewById(R.id.tvfSize);
        }

        public void setData(int position){
            Mp3Info mp3 = MusicActivity.mp3List.get(position);

            String Mtitle = mp3.getTitle();
            String Msinger = mp3.getArtist();
            if(Mtitle.length()>14){
                Mtitle = Mtitle.substring(0,14)+"...";
            }
            if(Msinger.length()>14){
                Msinger = Msinger.substring(0,14)+"...";
            }
            if(Msinger.contains("unknown")){
                if(Mtitle.contains("-")){
                    Msinger = Mtitle.substring(0,Mtitle.indexOf("-")).trim();
                    Mtitle = Mtitle.substring(Mtitle.indexOf("-")+1, Mtitle.length()).trim();
                }else {
                    Msinger = "未知";
                }
            }
            String name = Mtitle + "\n" + Msinger;
            tvfName.setText(name);
            String time = DateUtils.getFormatTime(mp3.getDuration());
            tvfSize.setText(time);
            if(MusicActivity.mp3Info!=null && mp3.getFileName().equals(MusicActivity.mp3Info.getFileName())){
                checkBox.setImageResource(R.mipmap.checked);
                MoveToPosition(manager, mRecyclerView, position);
            }else {
                checkBox.setImageResource(R.mipmap.unchecked);
            }
        }
    }
}
