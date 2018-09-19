package com.cody.app.business.easeui.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cody.app.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.util.EMLog;

import java.io.File;

/**
 * show the video
 */
public class EaseShowVideoActivity extends EaseBaseActivity {
    private static final String TAG = "ShowVideoActivity";

    private RelativeLayout loadingLayout;
    private ProgressBar progressBar;
    private String localFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ease_showvideo_activity);
        loadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final EMMessage message = getIntent().getParcelableExtra("msg");

        if (message == null || !(message.getBody() instanceof EMVideoMessageBody)) {
            Toast.makeText(EaseShowVideoActivity.this, "视频无法播放", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        EMVideoMessageBody messageBody = (EMVideoMessageBody) message.getBody();

        localFilePath = messageBody.getLocalUrl();

        if (localFilePath != null && new File(localFilePath).exists()) {
            showLocalVideo(EaseShowVideoActivity.this, localFilePath);
        } else {
            EMLog.d(TAG, "download remote video file");
            downloadVideo(message);
        }
    }

    /**
     * show local video
     *
     * @param localPath -- local path of the video file
     */
    private void showLocalVideo(Context context, String localPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this
                    , context.getPackageName() + ".file_provider", new File(localPath));
            intent.setDataAndType(contentUri, "video/mp4");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(localPath)),
                    "video/mp4");
        }
        startActivity(intent);
        finish();
    }

    /**
     * download video file
     */
    private void downloadVideo(EMMessage message) {
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        loadingLayout.setVisibility(View.GONE);
                        progressBar.setProgress(0);
                        showLocalVideo(EaseShowVideoActivity.this, localFilePath);
                    }
                });
            }

            @Override
            public void onProgress(final int progress, String status) {
                Log.d("ease", "video progress:" + progress);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progressBar.setProgress(progress);
                    }
                });

            }

            @Override
            public void onError(int error, String msg) {
                Log.e("###", "offline file transfer error:" + msg);
                File file = new File(localFilePath);
                if (file.exists()) {
                    file.delete();
                }
            }
        });
        EMClient.getInstance().chatManager().downloadAttachment(message);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
