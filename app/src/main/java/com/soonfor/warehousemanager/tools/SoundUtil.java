package com.soonfor.warehousemanager.tools;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.orhanobut.hawk.Hawk;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.home.setting.MediaUtil;
import com.soonfor.warehousemanager.home.setting.Mp3Info;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.service.PlayerService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：DC-DingYG on 2018-05-29 18:16
 * 邮箱：dingyg012655@126.com
 */
public class SoundUtil {
    // 上下文
    static Context mContext;
    static SoundUtil soundUtil;
    public static Map<Integer, Mp3Info> soundMap;
    static Ringtone ringtone;

    public SoundUtil(Context context) {
        mContext = context;
    }

    public static SoundUtil getInstense(Context context) {
        if (soundUtil == null) {
            soundUtil = new SoundUtil(context);
        }
        return soundUtil;
    }

    public void getWavFormRaw() {
        FileUtils.deleteDir("/WarehousManagerWav");
        FileUtils.copyFilesFromRaw(mContext, R.raw.wmscancomplete, "WmScanComplete.mp3");
        FileUtils.copyFilesFromRaw(mContext, R.raw.wmscanrepeat, "WmScanRepeat.mp3");
        FileUtils.copyFilesFromRaw(mContext, R.raw.wmscanfail, "WmScanFail.mp3");
        FileUtils.copyFilesFromRaw(mContext, R.raw.wmscansuccess, "WmScanSuccess.mp3");
        FileUtils.copyFilesFromRaw(mContext, R.raw.wmscansuccessnest, "WmScanSuccessNest.mp3");
    }

    public void initSound() {
        soundMap = null;
        try {
            soundMap = Hawk.get(UserInfo.SP_MUSICMAP, null);
        } catch (Exception e) {
        }
        if (soundMap == null || soundMap.size() < 3) {
            soundMap = new HashMap<>();
            List<Mp3Info> mp3Infos = MediaUtil.getMp3Infos(mContext);
            if (mp3Infos != null && mp3Infos.size() > 0) {
                boolean isHaveHansuo = false;
                for (int i = 0; i < mp3Infos.size(); i++) {
                    if (mp3Infos.get(i).getFileName().equals("hansuo")) {
                        soundMap.put(1, mp3Infos.get(i));
                        isHaveHansuo = true;
                    }
                }
                for (int i = 0; i < mp3Infos.size(); i++) {
                    if (soundMap.size() == 3) {
                        break;
                    }
                    if (!isHaveHansuo) {
                        if (mp3Infos.get(i).getFileName().equals("WmScanComplete")) {
                            soundMap.put(1, mp3Infos.get(i));
                            continue;
                        }
                    }
                    if (mp3Infos.get(i).getFileName().equals("WmScanRepeat")) {
                        soundMap.put(2, mp3Infos.get(i));
                        continue;
                    }
                    if (mp3Infos.get(i).getFileName().equals("WmScanFail")) {
                        soundMap.put(3, mp3Infos.get(i));
                        continue;
                    }
                }
            }
            Hawk.put(UserInfo.SP_MUSICMAP, soundMap);
        }
    }

    public void setHintSound(int type, long time) {
        String mUrl = "";
        try {
            mUrl = soundMap.get(type).getUrl();
        } catch (Exception e) {
            soundMap = Hawk.get(UserInfo.SP_MUSICMAP, null);
            if (soundMap != null && SoundUtil.soundMap.containsKey(type)) {
                mUrl = soundMap.get(type).getUrl();
            }
        }
        if (!mUrl.equals("")) {
            switch (type){
                case 1:
                    setSound(mContext, mUrl, time);
                    break;
                case 2:
                    setSoundByMediaPlayer(mContext, mUrl, 0);
                    break;
                case 3:
                    setSoundByMediaPlayer(mContext, mUrl, 0);
            }

        }
    }

    private void setSound(Context context, String mUrl, long time) {
        try {
            //RingtoneManager 读取铃声 这种方式播放短促音频比MediaPlayer更高效
            ringtone = RingtoneManager.getRingtone(context, Uri.fromFile(new File(mUrl)));
            ringtone.play();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static void setSoundByMediaPlayer(Context context, String mUrl, long time){
        try {
            Intent intent = new Intent(context, PlayerService.class);
            intent.setAction("com.wwj.media.MUSIC_SERVICE");
            intent.putExtra("url", mUrl);
            intent.putExtra("MSG", Tokens.PlayerMsg.PLAY_MSG);
            context.startService(intent);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        if(time > 0) {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent intent = new Intent(context, PlayerService.class);
                        intent.setAction("com.wwj.media.MUSIC_SERVICE");
                        intent.putExtra("url", mUrl);
                        intent.putExtra("MSG", Tokens.PlayerMsg.STOP_MSG);
                        context.startService(intent);
                    } catch (Exception e) {
                        e.fillInStackTrace();
                    }
                }
            }, time);
        }
    }
}
