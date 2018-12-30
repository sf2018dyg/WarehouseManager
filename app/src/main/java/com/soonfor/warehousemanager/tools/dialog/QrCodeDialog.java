package com.soonfor.warehousemanager.tools.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.soonfor.warehousemanager.R;
import com.soonfor.warehousemanager.home.login.activity.CustomScanAct;
import com.soonfor.warehousemanager.home.login.activity.ServerSettingsActivity;
import com.soonfor.warehousemanager.tools.BitmapDrawableUtil;
import com.soonfor.warehousemanager.tools.BitmapUtils;
import com.soonfor.warehousemanager.tools.MyToast;
import com.soonfor.warehousemanager.tools.ViewTools;

/**
 * Created by Administrator on 2018/1/11 0011.
 */

public class QrCodeDialog implements View.OnClickListener {
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private Activity context;
    private View dialogView;
    public static final int CHOOSE_PIC = 0;

    public QrCodeDialog(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.view_dialog_qr_code, null);
        ViewTools.setButtonListener(dialogView, R.id.camera, this);
        ViewTools.setButtonListener(dialogView, R.id.choose_from_album, this);
        ViewTools.setButtonListener(dialogView, R.id.cancel, this);
        dialog = new AlertDialog.Builder(context, R.style.my_style_dialog).create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 显示对话框
     */
    public void show() {
        dialog.show();
        dialog.getWindow().setContentView(dialogView);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    public void setGravityBottom() {
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    /**
     * 隐藏对话框
     */
    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 判断对话框是否显示
     */
    public boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        }
        return false;
    }

    /**
     * 设置对话框点击对话框以外的范围是否可以关闭对话框
     */
    public void setCancelable(boolean bool) {
        dialog.setCancelable(bool);
    }

    /**
     * 相册
     */
    public void pictureSelect() {
        //启动相册
        PermissionsUtil.requestPermission(context, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {
                        // 跳转到图片选择界面去选择一张二维码图片
                        Intent intent1 = new Intent();
                        intent1.setAction(Intent.ACTION_PICK);
                        intent1.setType("image/*");
                        context.startActivityForResult(intent1, CHOOSE_PIC);
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                    }
                },
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 二维码扫描
     */
    private void scan() {
        PermissionsUtil.requestPermission(context, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {
                        IntentIntegrator intentIntegrator = new IntentIntegrator(context);
                        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                                .setPrompt("对齐QR码/条码框内进行扫描")//写那句提示的话
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

    @Override
    public void onClick(View v) {
        dialog.dismiss();
        switch (v.getId()) {
            case R.id.camera:
                scan();//进入二维码扫描界面
                break;
            case R.id.choose_from_album:
                pictureSelect();//打开相册
                break;
            case R.id.cancel:
                break;
        }
    }

    /**
     * 解析本地图片
     */
    public static String getStringFromImage(AppCompatActivity mContext, Intent data, int requestCode, int resultCode) {
        String resultStr = "";
        if (requestCode == CHOOSE_PIC) {
            Bitmap bitmap;
            Result result = null;
            if (data != null) {
                String[] proj = new String[]{MediaStore.MediaColumns.DATA};
                Cursor cursor = mContext.getContentResolver().query(
                        data.getData(), proj, null, null, null);

                if (cursor == null) {
                    cursor = mContext.managedQuery(BitmapDrawableUtil.geturi(mContext, data), proj, null, null, null);
                }
                String imgPath = null;
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
                    // 获取到用户选择的二维码图片的绝对路径
                    imgPath = cursor.getString(columnIndex);
                }
                //4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
                if (Integer.parseInt(Build.VERSION.SDK) < 14) {
                    cursor.close();
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                bitmap = BitmapFactory.decodeFile(imgPath, options);
                // 在这里我用到的 getSmallerBitmap 非常重要，下面就要说到
                bitmap = BitmapUtils.getSmallerBitmap(bitmap);
                // 获取bitmap的宽高，像素矩阵
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int[] pixels = new int[width * height];
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                // 最新的库中，RGBLuminanceSource 的构造器参数不只是bitmap了
                RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
                Reader reader = new MultiFormatReader();
                // 尝试解析此bitmap，！！注意！！ 这个部分一定写到外层的try之中，因为只有在bitmap获取到之后才能解析写外部可能会有异步的问题（开始解析时bitmap为空）
                try {
                    result = reader.decode(binaryBitmap);
                    resultStr = result.toString();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                    MyToast.showFailToast(mContext, "未能识别到二维码信息");
                } catch (ChecksumException e) {
                    e.printStackTrace();
                    MyToast.showFailToast(mContext, "不是合法的二维码图片");
                } catch (FormatException e) {
                    e.printStackTrace();
                    MyToast.showFailToast(mContext, "不是合法的二维码图片");
                }
            }
        } else {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                if (intentResult.getContents() == null) {
                } else {
                    // scanResult 为获取到的字符串
                    String scanResult = intentResult.getContents();
                    if(scanResult!=null){
                        resultStr = scanResult;
                    }
                }
            }
        }
       return resultStr;
    }
}