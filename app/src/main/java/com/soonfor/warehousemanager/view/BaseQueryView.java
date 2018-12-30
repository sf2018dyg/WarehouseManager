package com.soonfor.warehousemanager.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dingyg.edittextwithclear.EditTextWithClear;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.home.login.activity.CustomScanAct;
import com.soonfor.warehousemanager.tools.MyToast;

/**
 * 作者：DC-DingYG on 2018-07-25 9:58
 * 邮箱：dingyg012655@126.com
 */
public class BaseQueryView extends LinearLayout implements EditTextWithClear.EditListener{

    private Context mContext;
    private EditTextWithClear llfEditText;
    private ImageButton ibfScan;
    private EditText etfInput;
    private Post post;
    private String hintText;
    public BaseQueryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_com_querybyscan, this);
        llfEditText = this.findViewById(R.id.etWithClear);
        ibfScan = this.findViewById(R.id.ibfscan);
    }
    //初始化
    public void initQueryView(final Context mContext, String hintText, Post post) {
        this.mContext = mContext;
        llfEditText.setSearchViewListener(this);
        llfEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        llfEditText.setImeOpton(EditorInfo.IME_ACTION_DONE);
        etfInput = llfEditText.getEditText();
        this.hintText = hintText;
        this.post = post;
        ibfScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsUtil.requestPermission(mContext, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permissions) {
                                IntentIntegrator intentIntegrator = new IntentIntegrator((Activity) mContext);
                                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                                        .setPrompt("对齐条码框内进行扫描")//写那句提示的话
                                        .setOrientationLocked(false)//扫描方向固定
                                        .setCaptureActivity(CustomScanAct.class) // 设置自定义的activity是CustomActivity
                                        .initiateScan(); // 初始化扫描
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permissions) {
                            }
                        },
                        Manifest.permission.CAMERA);
            }
        });
    }

    public EditText getEtfInput() {
        return etfInput;
    }

    @Override
    public void onSetHint(EditText editText) {
        this.etfInput = editText;
        etfInput.setHint(hintText==null?"":hintText);
    }

    @Override
    public void atetTextChanged(String s) {
        if (!s.equals("")) {
            etfInput.setSelection(s.length());
        } else {
            //隐藏软键盘
            ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(etfInput.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onEditAction(int actionId) {
        /*判断是否是“DONE”键*/
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            /*隐藏软键盘*/
            InputMethodManager imm = (InputMethodManager) etfInput
                    .getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(
                        etfInput.getApplicationWindowToken(), 0);
            }
            if (etfInput.getText() == null || etfInput.getText().toString().trim().equals("")) {
                MyToast.showToast(mContext, "条码不能为空！");
            } else {
                post.postData(etfInput.getText().toString().trim());
            }
        }
    }

    public interface Post{
        void postData(String inputText);
    }
}
