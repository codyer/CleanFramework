package com.cody.app.business.scan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import com.cody.app.R;
import com.cody.app.framework.zxing.camera.CameraManager;
import com.cody.app.framework.zxing.decoding.CaptureActivityHandler;
import com.cody.app.framework.zxing.decoding.InactivityTimer;
import com.cody.app.framework.zxing.view.ViewfinderView;
import com.cody.app.databinding.ActivityScanBinding;
import com.cody.app.framework.activity.BaseBindingActivity;
import com.cody.app.framework.activity.HtmlActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.cody.handler.framework.presenter.DefaultWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;
import com.cody.repository.Domain;
import com.cody.repository.business.interaction.constant.H5Url;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.SystemBarUtil;
import com.cody.xf.utils.ToastUtil;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Create by jiquan.zhong  on 2018/8/3.
 * description:
 */
public class ScanActivity extends BaseBindingActivity<DefaultWithHeaderPresenter, WithHeaderViewModel, ActivityScanBinding> implements SurfaceHolder.Callback {
    public static final String SCAN_RESULT = "scan_result";
    public static final String SCAN_FROM_NATIVE = "scan_from_native";
    public static final String SCAN_ALL = "scan_all";
    public static final int SCAN_REQUEST_CODE = 0x001;
    private static final int IMAGE_CODE = 1001;
    private final int PERMISSION_REQUEST_CODE = 88;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private String permissions[] = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SurfaceHolder surfaceHolder;
    private String path;
    private boolean mScanAll;
    private boolean mFromNative;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_scan;
    }

    @Override
    protected DefaultWithHeaderPresenter buildPresenter() {
        return new DefaultWithHeaderPresenter();
    }

    @Override
    protected WithHeaderViewModel buildViewModel(Bundle savedInstanceState) {
        return new WithHeaderViewModel();
    }

    public static void startScanActivity() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(SCAN_FROM_NATIVE, true);
        ActivityUtil.navigateToForResult(ScanActivity.class, SCAN_REQUEST_CODE, bundle);
    }

    public static void startScanAllActivity() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(SCAN_FROM_NATIVE, true);
        bundle.putBoolean(SCAN_ALL, true);
        ActivityUtil.navigateToForResult(ScanActivity.class, SCAN_REQUEST_CODE, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFromNative = getIntent().getBooleanExtra(SCAN_FROM_NATIVE, false);
        mScanAll = getIntent().getBooleanExtra(SCAN_ALL, false);
        if (mScanAll) {
            getBinding().manualOrder.setVisibility(View.GONE);
        }else {
            getBinding().manualOrder.setVisibility(View.VISIBLE);
        }
        CameraManager.init(getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onImmersiveReady() {
        SystemBarUtil.setStatusBarDarkMode(this, false);
        SystemBarUtil.tintStatusBar(this, Color.TRANSPARENT);
        SystemBarUtil.immersiveStatusBar(this, 0.0f);
        SystemBarUtil.setPadding(this, getBinding().header);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        surfaceHolder = getBinding().previewView.getHolder();
        if (hasSurface) {
            requestPermissions(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (checkBarCode(resultString)) {
            Intent i = new Intent();
            i.putExtra(SCAN_RESULT, resultString);
            setResult(RESULT_OK, i);
            finish();
        } else {
            if (handler != null) {
                handler.restartPreviewAndDecode();
            }
            Toast.makeText(ScanActivity.this, getString(R.string.wrong_qr_code), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkBarCode(String result) {
        return mScanAll || (!TextUtils.isEmpty(result) && result.contains(Domain.MMALL_URL));
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            requestPermissions(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return getBinding().viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        getBinding().viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private void requestPermissions(SurfaceHolder surfaceHolder) {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            initCamera(surfaceHolder);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_remind), PERMISSION_REQUEST_CODE, permissions);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        if (requestCode == PERMISSION_REQUEST_CODE && null != perms && perms.size() > 0) {
            ToastUtil.showToast(getString(R.string.permission_deny));
            cancelAndFinish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE) {

            //此处的用于判断接收的Activity是不是你想要的那个
            //这说明是用户点击了图片返回到的app界面
            Result result = null;
            try {
                String[] proj = {MediaStore.Images.Media.DATA};

                // 获取选中图片的路径
                //                外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                Cursor cursor = getContentResolver().query(data.getData(),
                        proj, null, null, null);

                if (cursor.moveToFirst()) {

                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    path = cursor.getString(column_index);
                    if (path == null) {
                        path = ScanUtils.getPath(getApplicationContext(),
                                data.getData());
                    }

                }

                cursor.close();

                result = ScanUtils.scanningImage(path);

                if (result == null) {
                    ToastUtil.showToast(getString(R.string.error_qrcode));
                } else {
                    handleDecode(result, null);
                }

            } catch (Exception e) {
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                cancelAndFinish();
                break;
            case R.id.tv_photo:
                PackageManager pm = getPackageManager();
                boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()));
                //判断有没有android.permission.WRITE_EXTERNAL_STORAGE权限
                if (permission) {
                    //如果有权限则激活系统相册
                    // 激活系统图库，选择一张图片
                    try {
                        Intent innerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        Intent wrapperIntent = Intent.createChooser(innerIntent, getString(R.string.select_qrcode));
                        startActivityForResult(wrapperIntent, IMAGE_CODE);
                    } catch (Exception e) {
                        ToastUtil.showToast(getString(R.string.without_ablum));
                    }
                    return;
                }
                break;
            case R.id.manualOrder:
                if (mFromNative) {
                    HtmlActivity.startHtml(null, H5Url.MANUAL_ORDER);
                }else {
                    cancelAndFinish();
                }
                break;
            case R.id.ll_light:
                cameraFlashControl();
                break;
        }

    }

    /**
     * 取消扫码
     */
    private void cancelAndFinish() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void cameraFlashControl() {
        //cameraManager.flashControlHandler();
        if (CameraManager.get().flashControlHandler()) {
            getBinding().ivLight.setImageResource(R.drawable.flashlight_open);
            getBinding().tvLight.setText(getString(R.string.light_off));
        } else {
            getBinding().ivLight.setImageResource(R.drawable.flashlight_close);
            getBinding().tvLight.setText(getString(R.string.light_on));
        }
    }
}
