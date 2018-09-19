/************************************************************
 *  * EaseMob CONFIDENTIAL 
 * __________________ 
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved. 
 *
 * NOTICE: All information contained herein is, and remains 
 * the property of EaseMob Technologies.
 * Dissemination of this information or reproduction of this material 
 * is strictly forbidden unless prior written permission is obtained
 * from EaseMob Technologies.
 */
package com.cody.app.business.easeui.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.cody.app.R;
import com.cody.app.business.widget.roundprogressbar.AdvancedCountdownTimer;
import com.cody.app.business.widget.roundprogressbar.RoundProgressBar;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil;
import com.cody.handler.business.EaseCommonUtils;
import com.cody.xf.utils.ImageUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecorderVideoActivity extends EaseBaseActivity implements
        OnClickListener, SurfaceHolder.Callback, OnErrorListener,
        OnInfoListener {
    private static final String TAG = "RecorderVideoActivity";
    private final static String CLASS_LABEL = "RecordActivity";
    private PowerManager.WakeLock mWakeLock;
    private MediaRecorder mediaRecorder;
    private VideoView mVideoView;// to display video
    String localPath = "";// path to save recorded video
    private Camera mCamera;
    private int previewWidth = 640;
    private int previewHeight = 640;
    private int frontCamera = 0; // 0 is back camera，1 is front camera
    private SurfaceHolder mSurfaceHolder;
    int defaultVideoFrameRate = -1;

    private RelativeLayout rlStart;
    private RoundProgressBar roundProgressBar;
    private TextView tvTime;
    private int MAXTIME = 15000;//最长时间
    private int duration = 0;//拍摄时长
    private AdvancedCountdownTimer countDownTimer;

    private ImageView ivVideoBack;
    private ImageView ivVideoOk;
    private ImageView ivClose;
    private ImageView ivSwitch;

    private String imgPath;
    private boolean isMotionEventUp = false;//手指是否拿起
    private boolean isRecordFinish = false;//是否录制完成

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_recorder_activity);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
                CLASS_LABEL);
        mWakeLock.acquire();
        initViews();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {
        ivSwitch = findViewById(R.id.iv_switch);
        ivClose = findViewById(R.id.iv_close);
        ivClose.setOnClickListener(this);
        ivSwitch.setOnClickListener(this);
        mVideoView = findViewById(R.id.mVideoView);
        tvTime = findViewById(R.id.tv_time);
        mSurfaceHolder = mVideoView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        rlStart = findViewById(R.id.rl_recorder_start);
        roundProgressBar = findViewById(R.id.roundProgressBar);
        rlStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isMotionEventUp = false;
                        //轻触拍照
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!isMotionEventUp) {
                                    if (!startRecording()) {
                                        com.cody.xf.utils.ToastUtil.showToast("请重试");
                                        return;
                                    }
                                    roundProgressBar.setStrokeColor(Color.parseColor("#439df7"));
                                    countDownTimer.start();
                                }
                            }
                        }, 500);
                        ivSwitch.setVisibility(View.INVISIBLE);
                        ivClose.setVisibility(View.INVISIBLE);
                        return true;
                    case MotionEvent.ACTION_UP:
                        isMotionEventUp = true;
                        if (isRecordFinish) {
                            return true;
                        }

                        if (isStartRecording()) {//录像
                            countDownTimer.cancel();
                            stopRecording();
                            LogUtil.d(TAG, "录像已启动");
                        } else {//拍照
                            mCamera.takePicture(null, null, new Camera.PictureCallback() {
                                @Override
                                public void onPictureTaken(byte[] data, Camera camera) {
                                    //将照片旋转90度
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    bitmap = ImageUtil.rotateBitmapByDegree(bitmap, 90);
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    byte[] datas = baos.toByteArray();
                                    imgPath = PathUtil.getInstance().getImagePath().getAbsolutePath()
                                            + "/" + System.currentTimeMillis() + ".jpeg";
                                    LogUtil.d(TAG, "拍照图片路径:" + imgPath);
                                    try {
                                        File file = new File(imgPath);
                                        FileOutputStream fos = new FileOutputStream(file);
                                        fos.write(datas);
                                        fos.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            LogUtil.d(TAG, "拍照");
                        }

                        rlStart.setVisibility(View.GONE);
                        ivVideoBack.setVisibility(View.VISIBLE);
                        ivVideoOk.setVisibility(View.VISIBLE);
                        tvTime.setVisibility(View.GONE);
                        return true;
                }
                return false;
            }
        });

        ivVideoBack = (ImageView) findViewById(R.id.iv_video_back);
        ivVideoOk = (ImageView) findViewById(R.id.iv_video_ok);
        ivVideoBack.setOnClickListener(this);
        ivVideoOk.setOnClickListener(this);

        countDownTimer = new AdvancedCountdownTimer(MAXTIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished, int percent) {
                int ms = (int) ((MAXTIME - millisUntilFinished) / 1000);
                duration = ms;
                tvTime.setText(ms + "秒");
                final int pro = (int) Math.floor((ms + 0.01) / (MAXTIME / 1000) * 100);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        roundProgressBar.setProgress(pro > 100 ? 100 : pro);
                    }
                });

            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("15秒");
                        roundProgressBar.setProgress(100);
                    }
                });

            }
        };
    }

    public void back(View view) {
        releaseRecorder();
        releaseCamera();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWakeLock == null) {
            // keep screen on
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
                    CLASS_LABEL);
            mWakeLock.acquire();
        }
    }

    @SuppressLint("NewApi")
    private boolean initCamera() {
        try {
            if (frontCamera == 0) {
                mCamera = Camera.open(CameraInfo.CAMERA_FACING_BACK);
            } else {
                mCamera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
            }
            mCamera.lock();
            mSurfaceHolder = mVideoView.getHolder();
            mSurfaceHolder.addCallback(this);
            mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            mCamera.setDisplayOrientation(90);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void handleSurfaceChanged() {
        if (mCamera == null) {
            finish();
            return;
        }
        boolean hasSupportRate = false;
        List<Integer> supportedPreviewFrameRates = mCamera.getParameters()
                .getSupportedPreviewFrameRates();
        if (supportedPreviewFrameRates != null
                && supportedPreviewFrameRates.size() > 0) {
            Collections.sort(supportedPreviewFrameRates);
            for (int i = 0; i < supportedPreviewFrameRates.size(); i++) {
                int supportRate = supportedPreviewFrameRates.get(i);

                if (supportRate == 15) {
                    hasSupportRate = true;
                }

            }
            if (hasSupportRate) {
                defaultVideoFrameRate = 15;
            } else {
                defaultVideoFrameRate = supportedPreviewFrameRates.get(0);
            }

        }

        // get all resolutions which camera provide
        List<Size> resolutionList = getResolutionList(mCamera);
        if (resolutionList != null && resolutionList.size() > 0) {
            Collections.sort(resolutionList, new ResolutionComparator());
            Size previewSize = null;
            boolean hasSize = false;

            // use 60*480 if camera support
            for (int i = 0; i < resolutionList.size(); i++) {
                Size size = resolutionList.get(i);
                if (size != null && size.width == 640 && size.height == 480) {
                    previewSize = size;
                    previewWidth = previewSize.width;
                    previewHeight = previewSize.height;
                    hasSize = true;
                    break;
                }
            }
            // use medium resolution if camera don't support the above resolution
            if (!hasSize) {
                int mediumResolution = resolutionList.size() / 2;
                if (mediumResolution >= resolutionList.size())
                    mediumResolution = resolutionList.size() - 1;
                previewSize = resolutionList.get(mediumResolution);
                previewWidth = previewSize.width;
                previewHeight = previewSize.height;
            }
        }
    }

    private List<Camera.Size> getResolutionList(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        return parameters.getSupportedPreviewSizes();
    }

    private static class ResolutionComparator implements Comparator<Size> {
        @Override
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if (lhs.height != rhs.height)
                return lhs.height - rhs.height;
            else
                return lhs.width - rhs.width;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }

        releaseRecorder();
        releaseCamera();

        finish();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_switch) {
            switchCamera();
        } else if (i == R.id.iv_close) {
            finish();
        } else if (i == R.id.rl_recorder_start) {// start recording

        } else if (i == R.id.iv_video_back) {
            finish();
        } else if (i == R.id.iv_video_ok) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.Whether_to_send)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                    if (isTakePhoto()) {
                                        sendImage();
                                    } else {
                                        sendVideo(null);
                                    }

                                }
                            })
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    if (localPath != null) {
                                        File file = new File(localPath);
                                        if (file.exists())
                                            file.delete();
                                    }
                                    finish();

                                }
                            }).setCancelable(false).show();

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        mSurfaceHolder = holder;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera == null) {
            if (!initCamera()) {
                showFailDialog();
                return;
            }

        }
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
            final Parameters camParams = mCamera.getParameters();
            List<String> supportedFocusModes = camParams.getSupportedFocusModes();
            if (supportedFocusModes != null
                    && supportedFocusModes.contains(Parameters.FOCUS_MODE_AUTO)) {
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            camParams.setPictureFormat(ImageFormat.JPEG);
//                            camParams.setFlashMode(Parameters.FLASH_MODE_TORCH);
                            camParams.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);//1连续对焦
                            camera.setParameters(camParams);
                            camera.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上
                        }
                    }
                });
            }
            handleSurfaceChanged();
        } catch (Exception e1) {
            EMLog.e("video", "start preview fail " + e1.getMessage());
            showFailDialog();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        EMLog.v("video", "surfaceDestroyed");
    }

    public boolean startRecording() {
        if (mediaRecorder == null) {
            if (!initRecorder())
                return false;
        }
        mediaRecorder.setOnInfoListener(this);
        mediaRecorder.setOnErrorListener(this);
        try {
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @SuppressLint("NewApi")
    private boolean initRecorder() {
        if (!EaseCommonUtils.isSdcardExist()) {
            showNoSDCardDialog();
            return false;
        }

        if (mCamera == null) {
            if (!initCamera()) {
                showFailDialog();
                return false;
            }
        }
        mVideoView.setVisibility(View.VISIBLE);
        mCamera.stopPreview();
        mediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        if (frontCamera == 1) {
            mediaRecorder.setOrientationHint(270);
        } else {
            mediaRecorder.setOrientationHint(90);
        }

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        // set resolution, should be set after the format and encoder was set
        mediaRecorder.setVideoSize(previewWidth, previewHeight);
        mediaRecorder.setVideoEncodingBitRate(900 * 1024);
        // set frame rate, should be set after the format and encoder was set
        if (defaultVideoFrameRate != -1) {
            mediaRecorder.setVideoFrameRate(defaultVideoFrameRate);
        }
        // set the path for video file
        localPath = PathUtil.getInstance().getVideoPath() + "/"
                + System.currentTimeMillis() + ".mp4";
        mediaRecorder.setOutputFile(localPath);
        mediaRecorder.setMaxDuration(MAXTIME);
        mediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.setOnErrorListener(null);
            mediaRecorder.setOnInfoListener(null);
            try {
                mediaRecorder.stop();
            } catch (Exception e) {
                EMLog.e("video", "stopRecording error:" + e.getMessage());
            }
        }
        releaseRecorder();

        if (mCamera != null) {
            mCamera.stopPreview();
            releaseCamera();
        }
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    protected void releaseCamera() {
        try {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        } catch (Exception e) {
        }
    }

    @SuppressLint("NewApi")
    public void switchCamera() {

        if (mCamera == null) {
            return;
        }
        if (Camera.getNumberOfCameras() >= 2) {
            ivSwitch.setEnabled(false);
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }

            switch (frontCamera) {
                case 0:
                    mCamera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
                    frontCamera = 1;
                    break;
                case 1:
                    mCamera = Camera.open(CameraInfo.CAMERA_FACING_BACK);
                    frontCamera = 0;
                    break;
            }
            try {
                mCamera.lock();
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(mVideoView.getHolder());
                mCamera.startPreview();
            } catch (IOException e) {
                mCamera.release();
                mCamera = null;
            }
            ivSwitch.setEnabled(true);

        }

    }

    MediaScannerConnection msc = null;
    ProgressDialog progressDialog = null;

    public void sendVideo(View view) {
        if (TextUtils.isEmpty(localPath)) {
            EMLog.e("Recorder", "recorder fail please try again!");
            return;
        }
        if (msc == null)
            msc = new MediaScannerConnection(this,
                    new MediaScannerConnectionClient() {

                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            EMLog.d(TAG, "scanner completed");
                            String[] projects = new String[]{MediaStore.Video.Media.DATA,
                                    MediaStore.Video.Media.DURATION};
                            Cursor cursor = getContentResolver().query(
                                    uri, projects, null,
                                    null, null);
                            String filePath = null;
                            if (cursor != null) {
                                if (cursor.moveToFirst()) {
                                    // path：MediaStore.Audio.Media.DATA
                                    filePath = cursor.getString(cursor
                                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                                    // MAXTIME：MediaStore.Audio.Media.DURATION
//                                duration = cursor
//                                        .getInt(cursor
//                                                .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
//                                EMLog.d(TAG, "MAXTIME:" + duration);
                                }
                                cursor.close();
                                cursor = null;
                            }

                            msc.disconnect();
                            progressDialog.dismiss();
                            Intent intent = new Intent();
                            intent.putExtra("dur", duration);
                            intent.putExtra("path", filePath);
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void onMediaScannerConnected() {
                            msc.scanFile(localPath, "video/*");
                        }
                    });


        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("processing...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
        msc.connect();

    }

    private void sendImage() {
        Intent intent = new Intent();
        if(!TextUtils.isEmpty(imgPath)){
            intent.putExtra("path", imgPath);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
            ToastUtil.showToast("拍摄时间太短，请重新拍摄");
        }
        finish();
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        EMLog.v("video", "onInfo");
        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
            EMLog.v("video", "max MAXTIME reached");
            isRecordFinish = true;
            stopRecording();
            ivSwitch.setVisibility(View.GONE);
            countDownTimer.cancel();
            rlStart.setVisibility(View.GONE);
            tvTime.setVisibility(View.GONE);

            ivVideoOk.setVisibility(View.VISIBLE);
            ivVideoBack.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        EMLog.e("video", "recording onError:");
        stopRecording();
        Toast.makeText(this,
                "Recording error has occurred. Stopping the recording",
                Toast.LENGTH_SHORT).show();

    }

    public void saveBitmapFile(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(), "a.jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamera();

        if (mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

    }

    @Override
    public void onBackPressed() {
        back(null);
    }

    private void showFailDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.prompt)
                .setMessage(R.string.Open_the_equipment_failure)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();

                            }
                        }).setCancelable(false).show();

    }

    private void showNoSDCardDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.prompt)
                .setMessage("No sd card!")
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();

                            }
                        }).setCancelable(false).show();
    }

    private boolean isStartRecording() {
        return mediaRecorder != null;
    }

    /**
     * 是否是拍照
     *
     * @return
     */
    private boolean isTakePhoto() {
        return duration == 0;
    }
}
