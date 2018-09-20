package com.cody.app.business.scan;

import android.Manifest;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import com.cody.app.R;
import com.cody.app.databinding.FwActivityScanBinding;
import com.cody.app.framework.activity.BaseBindingActivity;
import com.cody.app.framework.widget.image.ImageViewDelegate;
import com.cody.app.framework.widget.image.OnImageViewListener;
import com.cody.app.framework.zxing.camera.CameraManager;
import com.cody.app.framework.zxing.decoding.CaptureActivityHandler;
import com.cody.app.framework.zxing.decoding.InactivityTimer;
import com.cody.app.framework.zxing.view.ViewfinderView;
import com.cody.handler.framework.presenter.DefaultWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.SystemBarUtil;
import com.cody.xf.utils.ToastUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Create by jiquan.zhong  on 2018/8/3.
 * description:
 */
public class ScanActivity extends BaseBindingActivity<DefaultWithHeaderPresenter, WithHeaderViewModel, FwActivityScanBinding> implements SurfaceHolder.Callback {
    public static final String SCAN_RESULT = "scan_result";
    public static final int SCAN_REQUEST_CODE = 0x001;
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
    private ImageViewDelegate mImageViewDelegate;

    public static void startScanActivity() {
        ActivityUtil.navigateToForResult(ScanActivity.class, SCAN_REQUEST_CODE);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_activity_scan;
    }

    @Override
    protected DefaultWithHeaderPresenter buildPresenter() {
        return new DefaultWithHeaderPresenter();
    }

    @Override
    protected WithHeaderViewModel buildViewModel(Bundle savedInstanceState) {
        return new WithHeaderViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CameraManager.init(getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        mImageViewDelegate = new ImageViewDelegate(new OnImageViewListener() {
            @Override
            public void onPreview(int id, List<ImageItem> images) {

            }

            @Override
            public void onPickImage(int id, List<ImageItem> images) {
                if (images != null && images.size() > 0) {
                    Result result = ScanUtils.scanningImage(images.get(0).path);
                    if (result == null) {
                        ToastUtil.showToast(getString(R.string.error_qrcode));
                    } else {
                        handleDecode(result, null);
                    }
                }
            }
        }, this);
    }

    @Override
    protected void onImmersiveReady() {
        SystemBarUtil.setStatusBarDarkMode(this, false);
        SystemBarUtil.tintStatusBar(this, Color.TRANSPARENT);
        SystemBarUtil.immersiveStatusBar(this, 0.0f);
        SystemBarUtil.setPadding(this, getBinding().header);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = getBinding().previewView.getHolder();
        if (hasSurface) {
            requestPermissions(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            if (Build.VERSION.SDK_INT < 16) {
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            }
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService == null || audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
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
        return (!TextUtils.isEmpty(result));
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
            if (vibrator != null) {
                vibrator.vibrate(VIBRATE_DURATION);
            }
        }
    }

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
        mImageViewDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                cancelAndFinish();
                break;
            case R.id.tv_photo:
                mImageViewDelegate.selectImage(1);
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

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private void cameraFlashControl() {
        if (CameraManager.get().flashControlHandler()) {
            getBinding().ivLight.setImageResource(R.drawable.fw_flashlight_open);
            getBinding().tvLight.setText(getString(R.string.light_off));
        } else {
            getBinding().ivLight.setImageResource(R.drawable.fw_flashlight_close);
            getBinding().tvLight.setText(getString(R.string.light_on));
        }
    }
}
