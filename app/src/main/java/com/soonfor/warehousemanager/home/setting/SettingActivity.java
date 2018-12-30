package com.soonfor.warehousemanager.home.setting;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.orhanobut.hawk.Hawk;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.bases.BaseActivity;
import com.soonfor.warehousemanager.home.login.activity.StartActivity;
import com.soonfor.warehousemanager.http.api.Request;
import com.soonfor.warehousemanager.http.api.UserInfo;
import com.soonfor.warehousemanager.http.httptools.AsyncUtils;
import com.soonfor.warehousemanager.tools.SoundUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-DingYG on 2018-07-23 19:22
 * 邮箱：dingyg012655@126.com
 */
public class SettingActivity extends BaseActivity implements ISettingView, AsyncUtils.AsyncCallback {

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    SettingActivity mActivity;
    @BindView(R.id.tvfSMToSucess)
    TextView tvfSMToSucess;
    @BindView(R.id.tvfSMToRepetition)
    TextView tvfSMToRepetition;
    @BindView(R.id.tvfSMToFail)
    TextView tvfSMToFail;
    @BindView(R.id.tvfSMToPrint)
    TextView tvfSMToPrint;
    @BindView(R.id.tvfSMToPrintPlan)
    TextView tvfSMToPrintPlan;
    private Map<Integer, Mp3Info> defaultMap;//保存的音频文件
    private static final int GET_SUCCESS_MUSIC = 7;
    private static final int GET_REPTITION_MUSIC = 8;
    private static final int GET_FAIL_MUSIC = 9;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initPresenter() {
        presenter = new SettingPresenter(this);
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("设置");
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mActivity = SettingActivity.this;
    }

    @Override
    protected void initViews() {
        try {
            defaultMap = Hawk.get(UserInfo.SP_MUSICMAP, null);
        } catch (Exception e) {
        }
        if (defaultMap == null) {
            defaultMap = new HashMap<>();
        }
        initDefaultShow();

        //打印机的
        tvfSMToPrint.setText(Hawk.get("PrinterMAC",""));
        tvfSMToPrintPlan.setText(Hawk.get("PrintFrom",""));
    }

    /**
     * 设置默认值 将资源文件中的wav文件复制到sdk
     */
    private void initDefaultShow() {
        if(defaultMap.size()<3){
            try {
                SoundUtil soundUtil = SoundUtil.getInstense(SettingActivity.this);
                soundUtil.getWavFormRaw();
                soundUtil.initSound();
            }catch (Exception e){}
            defaultMap = Hawk.get(UserInfo.SP_MUSICMAP, new HashMap<>());
        }
        if (defaultMap.containsKey(1) && defaultMap.get(1) != null) {
            tvfSMToSucess.setText(defaultMap.get(1).getFileName());
        }
        if (defaultMap.containsKey(2) && defaultMap.get(2) != null) {
            tvfSMToRepetition.setText(defaultMap.get(2).getFileName());
        }
        if (defaultMap.containsKey(3) && defaultMap.get(3) != null) {
            tvfSMToFail.setText(defaultMap.get(3).getFileName());
        }
    }

    @Override
    public void setErrorMsg(int code, String msg) {
        hideQMTipLoading();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.llfSMToSucess, R.id.llfSMToRepetition, R.id.llfSMToFail, R.id.llfSMToPrint, R.id.llfSMToPrintPlan})
    void SettingOnClick(View view) {
        PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {
                        Intent intent = new Intent(mActivity, MusicActivity.class);
                        Bundle bundle = new Bundle();
                        switch (view.getId()) {
                            case R.id.llfSMToSucess:
                                if (defaultMap != null && defaultMap.containsKey(1) && defaultMap.get(1) != null) {
                                    bundle.putSerializable("DEFAULT_MP3", defaultMap.get(1));
                                } else {
                                    bundle.putSerializable("DEFAULT_MP3", null);
                                }
                                intent.putExtras(bundle);
                                mActivity.startActivityForResult(intent, GET_SUCCESS_MUSIC);
                                break;
                            case R.id.llfSMToRepetition:
                                if (defaultMap != null && defaultMap.containsKey(2) && defaultMap.get(2) != null) {
                                    bundle.putSerializable("DEFAULT_MP3", defaultMap.get(2));
                                } else {
                                    bundle.putSerializable("DEFAULT_MP3", null);
                                }
                                intent.putExtras(bundle);
                                mActivity.startActivityForResult(intent, GET_REPTITION_MUSIC);
                                break;
                            case R.id.llfSMToFail:
                                if (defaultMap != null && defaultMap.containsKey(3) && defaultMap.get(3) != null) {
                                    bundle.putSerializable("DEFAULT_MP3", defaultMap.get(3));
                                } else {
                                    bundle.putSerializable("DEFAULT_MP3", null);
                                }
                                intent.putExtras(bundle);
                                mActivity.startActivityForResult(intent, GET_FAIL_MUSIC);
                                break;
                            case R.id.llfSMToPrint://打印机
                                Request.GetPrinterList(SettingActivity.this, SettingActivity.this);
                                break;
                            case R.id.llfSMToPrintPlan://打印方案
                                Request.GetSchemeList(SettingActivity.this, SettingActivity.this);
                                break;
                        }
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                    }
                },
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case GET_SUCCESS_MUSIC:
                    Mp3Info mp3Info = (Mp3Info) data.getSerializableExtra("SELECTED_MP3");
                    if (mp3Info != null) {
                        tvfSMToSucess.setText(mp3Info.getFileName());
                    } else {
                        tvfSMToSucess.setText("");
                    }
                    defaultMap.put(1, mp3Info);
                    break;
                case GET_REPTITION_MUSIC:
                    mp3Info = (Mp3Info) data.getSerializableExtra("SELECTED_MP3");
                    if (mp3Info != null) {
                        tvfSMToRepetition.setText(mp3Info.getFileName());
                    } else {
                        tvfSMToRepetition.setText("");
                    }
                    defaultMap.put(2, mp3Info);
                    break;
                case GET_FAIL_MUSIC:
                    mp3Info = (Mp3Info) data.getSerializableExtra("SELECTED_MP3");
                    if (mp3Info != null) {
                        tvfSMToFail.setText(mp3Info.getFileName());
                    } else {
                        tvfSMToFail.setText("");
                    }
                    defaultMap.put(3, mp3Info);
                    break;
            }
            SoundUtil.soundMap = defaultMap;
            Hawk.put(UserInfo.SP_MUSICMAP, defaultMap);
        }
    }

    @Override
    public void success(int requestCode, String data) {
        if (requestCode == Request.GETPRINTERLISTPDA) {
            try {
                JSONObject head = new JSONObject(data);
                String title = head.getString("title");
                String item = head.getString("item");
                JSONArray jr = new JSONArray(item);
                String[] list = new String[jr.length()];
                for (int i = 0; i < jr.length(); i++) {
                    JSONObject o = new JSONObject(jr.get(i).toString());
                    list[i] = o.getString("fPrinterName");
                }
                new QMUIDialog.CheckableDialogBuilder(SettingActivity.this)
                        .setTitle("请选择一台打印机")
                        .addItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Hawk.put("PrinterMAC", list[which]);
                                tvfSMToPrint.setText(Hawk.get("PrinterMAC",""));
                                dialog.dismiss();
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == Request.GETSCHEMELISTPDA) {
            try {
                JSONObject head = new JSONObject(data);
                String title = head.getString("title");
                String item = head.getString("item");
                JSONArray jr = new JSONArray(item);
                String[] list = new String[jr.length()];
                for (int i = 0; i < jr.length(); i++) {
                    JSONObject o = new JSONObject(jr.get(i).toString());
                    list[i] = o.getString("fSchmName");
                }
                new QMUIDialog.CheckableDialogBuilder(SettingActivity.this)
                        .setTitle("请选择一个打印方案")
                        .addItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Hawk.put("PrintFrom", list[which]);
                                tvfSMToPrintPlan.setText(Hawk.get("PrintFrom",""));
                                dialog.dismiss();
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String data, String msg) {
        setErrorMsg(statusCode, msg);
    }
}
