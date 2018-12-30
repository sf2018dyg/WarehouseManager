package com.soonfor.warehousemanager.home.setting;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.service.PlayerService;
import com.soonfor.warehousemanager.tools.SoundUtil;
import com.soonfor.warehousemanager.tools.Tokens;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-DingYG on 2018-07-24 9:37
 * 邮箱：dingyg012655@126.com
 */
public class MusicActivity extends BaseActivity<SettingPresenter> implements ISettingView {

    MusicActivity mActivity;
    //    @BindView(R.id.tvfRightClick)
//    TextView tvfRightClick;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MusicAdapter mAdapter;
    public static List<Mp3Info> mp3List;
    public static Mp3Info mp3Info;//选中的项
    private Mp3Info defaultSound;//第一次进入的默认
    MediaPlayer mediaPlayer;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_music;
    }

    @Override
    protected void initPresenter() {
        presenter = new SettingPresenter(this);
        mActivity = MusicActivity.this;
        Bundle bundle = getIntent().getExtras();
        try {
            mp3Info = (Mp3Info) bundle.getSerializable("DEFAULT_MP3");
            defaultSound = (Mp3Info) bundle.getSerializable("DEFAULT_MP3");
        } catch (Exception e) {
            mp3Info = null;
        }
        mediaPlayer = new MediaPlayer();
        mSwipeRefresh.setEnableLoadmore(false);
    }

    @Override
    protected void initViews() {

        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MusicAdapter(mActivity, mLayoutManager, mRecyclerView, listener);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        presenter.getMp3List(mActivity, true);
    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        presenter.getMp3List(mActivity, false);
    }

    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);
        closeLoadingDialog();
        mAdapter.notifyDataSetChanged(mp3List);
//        if (mp3Info != null) {
//            tvfRightClick.setVisibility(View.VISIBLE);
//            tvfRightClick.setText("点击试听");
//        } else {
//            tvfRightClick.setVisibility(View.GONE);
//        }
    }

    @OnClick({R.id.tvfRightClick, R.id.tvfSure})
    void ThisOnClick(View v) {
//        if (v.getId() == R.id.tvfRightClick) {
//            if (mp3Info != null) {
//                if (tvfRightClick.getText().equals("点击试听")) {
//                    tvfRightClick.setText("正在播放");
//                    setSound(mp3Info.getUrl(),mp3Info.getDuration());
//                } else if (tvfRightClick.getText().equals("正在播放")) {
//                    stopMusicPlay();
//                }
//            }
//        } else
        if (v.getId() == R.id.tvfSure) {
            Intent intent1 = new Intent(mActivity, SettingActivity.class);
            if (mp3Info == null) {
                intent1.putExtra("SELECTED_MP3", defaultSound);
            } else {
                intent1.putExtra("SELECTED_MP3", mp3Info);
            }
            mActivity.setResult(RESULT_OK, intent1);// Result_OK
            finish();
        }

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (mp3Info != null) {
                if (mp3Info.getUrl().equals(mp3List.get(position).getUrl())) {
                    mp3Info = null;
                    stopMusicPlay();
                } else {
                    mp3Info = mp3List.get(position);
                    SoundUtil.setSoundByMediaPlayer(mActivity, mp3Info.getUrl(), mp3Info.getDuration());
                }
            } else {
                mp3Info = mp3List.get(position);
                SoundUtil.setSoundByMediaPlayer(mActivity, mp3Info.getUrl(), mp3Info.getDuration());
            }
            mAdapter.notifyDataSetChanged();
//            if (mp3Info != null) {
//                tvfRightClick.setVisibility(View.VISIBLE);
//                tvfRightClick.setText("点击试听");
//            } else {
//                tvfRightClick.setVisibility(View.GONE);
//            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusicPlay();
    }

    private void stopMusicPlay() {
        //tvfRightClick.setText("点击试听");
        try {
            Intent intent = new Intent(mActivity, PlayerService.class);
            intent.setAction("com.wwj.media.MUSIC_SERVICE");
//                    intent.putExtra("listPosition", 0);
//                    intent.putExtra("url", mp3Info.getUrl());
            intent.putExtra("MSG", Tokens.PlayerMsg.STOP_MSG);
            mActivity.startService(intent);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public void setErrorMsg(int code, String msg) {

    }
}
