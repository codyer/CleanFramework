/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cody.app.business.easeui.ui;

import android.Manifest;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cody.app.R;
import com.cody.app.business.easeui.ImManager;
import com.cody.app.business.widget.MyChronometer;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMVideoCallHelper;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.media.EMCallSurfaceView;
import com.hyphenate.util.EMLog;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.DataChangeListener;
import com.cody.repository.business.database.UserInfoManager;
import com.superrtc.sdk.VideoView;

import org.json.JSONObject;

import java.util.UUID;

import pub.devrel.easypermissions.EasyPermissions;


public class VideoCallActivity extends CallActivity implements OnClickListener {

    private static final int PERMISSION_CODE_VIDEO = 3;
    private static final String BTN_TXT_CANCEL = "取消";
    private static final String BTN_TXT_REFUSE = "拒绝";
    private static final String BTN_TXT_ANSWER = "接听";
    private static final String BTN_TXT_CAMERA = "切换摄像头";

    private boolean isAnswered;
    private boolean endCallTriggerByMe = false;

    // 视频通话画面显示控件，这里在新版中使用同一类型的控件，方便本地和远端视图切换
    protected EMCallSurfaceView localSurface;
    protected EMCallSurfaceView oppositeSurface;
    private int surfaceState = -1;

    private Handler uiHandler;

    private boolean isInCalling;
    boolean isRecording = false;
    private EMVideoCallHelper callHelper;

    private ImageView mPortrait;
    private TextView mNickName;
    private TextView mStatus;
    private MyChronometer mCallTime;
    private LinearLayout mLeftBtn;
    private TextView mLeftBtnTxt;
    private LinearLayout mCenterBtn;
    private TextView mCenterBtnTxt;
    private LinearLayout mRightBtn;
    private TextView mRightBtnTxt;
    private ImageView mRightBtnImg;
    private DataChangeListener mUserInfoChangeListener;

    private void findViews() {
        mPortrait = findViewById(R.id.portrait);
        mNickName = findViewById(R.id.nickName);
        mStatus = findViewById(R.id.status);
        mCallTime = findViewById(R.id.callTime);
        mLeftBtn = findViewById(R.id.leftBtn);
        mLeftBtnTxt = findViewById(R.id.leftBtnTxt);
        mLeftBtn.setOnClickListener(this);
        mCenterBtn = findViewById(R.id.centerBtn);
        mCenterBtnTxt = findViewById(R.id.centerBtnTxt);
        mCenterBtn.setOnClickListener(this);
        mRightBtn = findViewById(R.id.rightBtn);
        mRightBtnTxt = findViewById(R.id.rightBtnTxt);
        mRightBtnImg = findViewById(R.id.rightBtnImg);
        mRightBtn.setOnClickListener(this);

        localSurface = findViewById(R.id.local_surface);
        localSurface.setOnClickListener(this);
        localSurface.setZOrderMediaOverlay(true);
        localSurface.setZOrderOnTop(true);

        // remote surfaceview
        oppositeSurface = findViewById(R.id.opposite_surface);
        oppositeSurface.setScaleMode(VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            finish();
            return;
        }
        setContentView(R.layout.em_activity_video_call);

        findViews();

        ImManager.getInstance().isVideoCalling = true;
        callType = 1;

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        uiHandler = new Handler();

        msgid = UUID.randomUUID().toString();
        isInComingCall = getIntent().getBooleanExtra("isComingCall", false);
        username = getIntent().getStringExtra("username");

        if (!EasyPermissions.hasPermissions(this
                , Manifest.permission.CAMERA
                , Manifest.permission.RECORD_AUDIO
                , Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this
                    , getString(R.string.permission_video), PERMISSION_CODE_VIDEO
                    , Manifest.permission.CAMERA
                    , Manifest.permission.RECORD_AUDIO
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        // set call state listener
        addCallStateListener();
        mUserInfoChangeListener = UserInfoManager.getUserInfo(TAG, false, true, username, new
                DataCallBack<UserInfoBean>() {
                    @Override
                    public void onSuccess(UserInfoBean userInfoBean) {
                        super.onSuccess(userInfoBean);
                        if (userInfoBean == null) {//会话中还不存在这个用户(没聊过天，直接打电话过来的情况)
                            try {
                                JSONObject jsonObject = new JSONObject(EMClient.getInstance()
                                        .callManager().getCurrentCallSession().getExt());
                                mNickName.setText(jsonObject.getString("userName"));
                                Glide.with(VideoCallActivity.this)
                                        .load(jsonObject.getString("avatar")).into(mPortrait);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            mNickName.setText(UserInfoManager.handleUserName(userInfoBean));
                            Glide.with(VideoCallActivity.this)
                                    .load(userInfoBean.getAvatar())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.ic_touxiang)
                                    .into(mPortrait);
                        }

                    }
                });
//        EaseUserUtils.setUserNick(username, mNickName);
//        EaseUserUtils.setUserAvatar(this, username, mPortrait);
        if (!isInComingCall) {// outgoing call
            soundPool = new SoundPool(1, AudioManager.STREAM_RING, 0);
            outgoing = soundPool.load(this, R.raw.im_video_call, 1);

            mLeftBtn.setVisibility(View.INVISIBLE);
            mCenterBtn.setVisibility(View.VISIBLE);
            mRightBtn.setVisibility(View.INVISIBLE);
            mCenterBtnTxt.setText(BTN_TXT_CANCEL);
            mCallTime.setVisibility(View.INVISIBLE);
            String st = getResources().getString(R.string.Are_connected_to_each_other);
            mStatus.setText(st);
            EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
            handler.sendEmptyMessage(MSG_CALL_MAKE_VIDEO);
            handler.postDelayed(new Runnable() {
                public void run() {
                    streamID = playMakeCallSounds();
                }
            }, 300);

            final int MAKE_CALL_TIMEOUT = 50 * 1000;
            handler.removeCallbacks(timeoutHangup);
            handler.postDelayed(timeoutHangup, MAKE_CALL_TIMEOUT);
        } else { // incoming call

            mStatus.setText("邀请您视频聊天");
            if (EMClient.getInstance().callManager().getCallState() == EMCallStateChangeListener.CallState.IDLE
                    || EMClient.getInstance().callManager().getCallState() == EMCallStateChangeListener.CallState
                    .DISCONNECTED) {
                // the call has ended
                finish();
                return;
            }
            mLeftBtn.setVisibility(View.VISIBLE);
            mCenterBtn.setVisibility(View.INVISIBLE);
            mRightBtn.setVisibility(View.VISIBLE);
            mLeftBtnTxt.setText(BTN_TXT_REFUSE);
            mRightBtnTxt.setText(BTN_TXT_ANSWER);
            mCallTime.setVisibility(View.INVISIBLE);
            Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            audioManager.setMode(AudioManager.MODE_RINGTONE);
            audioManager.setSpeakerphoneOn(true);
            ringtone = RingtoneManager.getRingtone(this, ringUri);
            ringtone.play();
            EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
        }

        // get instance of call helper, should be called after setSurfaceView was called
        callHelper = EMClient.getInstance().callManager().getVideoCallHelper();

    }


    /**
     * 切换通话界面，这里就是交换本地和远端画面控件设置，以达到通话大小画面的切换
     */
    private void changeCallView() {
        if (surfaceState == 0) {
            surfaceState = 1;
            EMClient.getInstance().callManager().setSurfaceView(oppositeSurface, localSurface);
        } else {
            surfaceState = 0;
            EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
        }
    }

    /**
     * set call state listener
     */
    void addCallStateListener() {
        callStateListener = new EMCallStateChangeListener() {

            @Override
            public void onCallStateChanged(final CallState callState, final CallError error) {
                switch (callState) {

                    case CONNECTING: // is connecting
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mStatus.setText(R.string.Are_connected_to_each_other);
                            }

                        });
                        break;
                    case CONNECTED: // connected
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
//                                mStatus.setText(R.string.have_connected_with);
                            }

                        });
                        break;

                    case ACCEPTED: // call is accepted
                        surfaceState = 0;
                        handler.removeCallbacks(timeoutHangup);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    if (soundPool != null)
                                        soundPool.stop(streamID);
                                    EMLog.d("EMCallManager", "soundPool stop ACCEPTED");
                                } catch (Exception ignored) {
                                }
                                openSpeakerOn();
                                mLeftBtn.setVisibility(View.VISIBLE);
                                mCenterBtn.setVisibility(View.INVISIBLE);
                                mRightBtn.setVisibility(View.VISIBLE);
                                mRightBtn.setEnabled(true);
                                mLeftBtnTxt.setText(BTN_TXT_CANCEL);
                                mRightBtnTxt.setText(BTN_TXT_CAMERA);
                                mRightBtnImg.setImageResource(R.drawable.ic_qiehuanshipin);
                                isInCalling = true;
                                mCallTime.setVisibility(View.VISIBLE);
                                mCallTime.setBase(SystemClock.elapsedRealtime());
                                // call durations start
                                mCallTime.start();
                                mNickName.setVisibility(View.INVISIBLE);
                                mStatus.setVisibility(View.INVISIBLE);
                                mPortrait.setVisibility(View.INVISIBLE);
                                callingState = CallingState.NORMAL;
//                                localSurface.setVisibility(View.VISIBLE);
                            }

                        });
                        break;
                    case DISCONNECTED: // call is disconnected
                        handler.removeCallbacks(timeoutHangup);
                        @SuppressWarnings("UnnecessaryLocalVariable") final CallError fError = error;
                        runOnUiThread(new Runnable() {
                            private void postDelayedCloseMsg() {
                                uiHandler.postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        removeCallStateListener();
                                        saveCallRecord();
                                        finish();
                                    }

                                }, 200);
                            }

                            @Override
                            public void run() {
                                mCallTime.stop();
                                callDruationText = mCallTime.getText().toString();
                                String s1 = getResources().getString(R.string.The_other_party_refused_to_accept);
                                String s2 = getResources().getString(R.string.Connection_failure);
                                String s3 = getResources().getString(R.string.The_other_party_is_not_online);
                                String s4 = getResources().getString(R.string.The_other_is_on_the_phone_please);
                                String s5 = getResources().getString(R.string.The_other_party_did_not_answer);

                                String s6 = getResources().getString(R.string.hang_up);
                                String s7 = getResources().getString(R.string.The_other_is_hang_up);
                                String s8 = getResources().getString(R.string.did_not_answer);
                                String s9 = getResources().getString(R.string.Has_been_cancelled);
                                String s10 = getResources().getString(R.string.Refused);

                                if (fError == CallError.REJECTED) {
                                    callingState = CallingState.BEREFUSED;
                                    mStatus.setText(s1);
                                } else if (fError == CallError.ERROR_TRANSPORT) {
                                    mStatus.setText(s2);
                                } else if (fError == CallError.ERROR_UNAVAILABLE) {
                                    callingState = CallingState.OFFLINE;
                                    mStatus.setText(s3);
                                } else if (fError == CallError.ERROR_BUSY) {
                                    callingState = CallingState.BUSY;
                                    mStatus.setText(s4);
                                } else if (fError == CallError.ERROR_NORESPONSE) {
                                    callingState = CallingState.NO_RESPONSE;
                                    mStatus.setText(s5);
                                } else if (fError == CallError.ERROR_LOCAL_SDK_VERSION_OUTDATED || fError ==
                                        CallError.ERROR_REMOTE_SDK_VERSION_OUTDATED) {
                                    callingState = CallingState.VERSION_NOT_SAME;
                                    mStatus.setText(R.string.call_version_inconsistent);
                                } else {
                                    if (isRefused) {
                                        callingState = CallingState.REFUSED;
                                        mStatus.setText(s10);
                                    } else if (isAnswered) {
                                        callingState = CallingState.NORMAL;
                                        if (endCallTriggerByMe) {
//                                        mStatus.setText(s6);
                                        } else {
                                            mStatus.setText(s7);
                                        }
                                    } else {
                                        if (isInComingCall) {
                                            callingState = CallingState.UNANSWERED;
                                            mStatus.setText(s8);
                                        } else if (isTimeOut) {
                                            callingState = CallingState.NO_RESPONSE;
                                            mStatus.setText(s5);
                                        } else {
                                            if (callingState != CallingState.NORMAL) {
                                                callingState = CallingState.CANCELLED;
                                                mStatus.setText(s9);
                                            } else {
                                                mStatus.setText(s6);
                                            }
                                        }
                                    }
                                }
                                Toast.makeText(VideoCallActivity.this, mStatus.getText(), Toast.LENGTH_SHORT).show();
                                postDelayedCloseMsg();
                            }

                        });

                        break;

                    default:
                        break;
                }

            }
        };
        EMClient.getInstance().callManager().addCallStateChangeListener(callStateListener);
    }

    void removeCallStateListener() {
        EMClient.getInstance().callManager().removeCallStateChangeListener(callStateListener);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.local_surface) {
            changeCallView();

        } else if (i == R.id.leftBtn) {
            if (BTN_TXT_CANCEL.equals(mLeftBtnTxt.getText().toString())) {//取消
                isAction = true;
                mCallTime.stop();
                endCallTriggerByMe = true;
                mStatus.setText(getResources().getString(R.string.hanging_up));
                if (isRecording) {
                    callHelper.stopVideoRecord();
                }
                handler.sendEmptyMessage(MSG_CALL_END);
            } else if (BTN_TXT_REFUSE.equals(mLeftBtnTxt.getText().toString())) {//拒绝
                isAction = true;
                isRefused = true;
                mLeftBtn.setEnabled(false);
                handler.sendEmptyMessage(MSG_CALL_REJECT);
            }

        } else if (i == R.id.centerBtn) {
            isAction = true;
            mCallTime.stop();
            endCallTriggerByMe = true;
            mStatus.setText(getResources().getString(R.string.hanging_up));
            if (isRecording) {
                callHelper.stopVideoRecord();
            }
            handler.sendEmptyMessage(MSG_CALL_END);

        } else if (i == R.id.rightBtn) {
            if (BTN_TXT_ANSWER.equals(mRightBtnTxt.getText().toString())) {//接听
                mRightBtn.setEnabled(false);
                openSpeakerOn();
                if (ringtone != null)
                    ringtone.stop();

                mStatus.setText("连接中...");
                handler.sendEmptyMessage(MSG_CALL_ANSWER);
                isAnswered = true;
            } else if (BTN_TXT_CAMERA.equals(mRightBtnTxt.getText().toString())) {//切换摄像头
                handler.sendEmptyMessage(MSG_CALL_SWITCH_CAMERA);
            }

        }
    }

    @Override
    protected void onDestroy() {
        ImManager.getInstance().isVideoCalling = false;
        if (isRecording) {
            callHelper.stopVideoRecord();
            isRecording = false;
        }
        localSurface.getRenderer().dispose();
        localSurface = null;
        oppositeSurface.getRenderer().dispose();
        oppositeSurface = null;
        if (mUserInfoChangeListener != null) {
            mUserInfoChangeListener.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        callDruationText = mCallTime.getText().toString();
        super.onBackPressed();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (isInCalling) {
            try {
                EMClient.getInstance().callManager().pauseVideoTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInCalling) {
            try {
                EMClient.getInstance().callManager().resumeVideoTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

}
