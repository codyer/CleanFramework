package com.cody.app.business.easeui.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;

import com.cody.app.R;
import com.cody.app.framework.EventBus.SimpleEventBus;
import com.cody.app.framework.EventBus.SimpleEventBusKey;
import com.cody.app.business.easeui.EaseConstant;
import com.google.gson.JsonObject;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMCallManager;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.NetUtils;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.bean.im.BaseMessageBean;
import com.cody.repository.business.bean.im.CustomMessage;
import com.cody.repository.business.bean.im.IMVideoCallMessageBean;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.xf.utils.JsonUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ResourceUtil;
import com.cody.xf.utils.ToastUtil;

@SuppressLint("Registered")
public class CallActivity extends EaseBaseActivity {
    public final static String TAG = "CallActivity";
    protected final int MSG_CALL_MAKE_VIDEO = 0;
    protected final int MSG_CALL_MAKE_VOICE = 1;
    protected final int MSG_CALL_ANSWER = 2;
    protected final int MSG_CALL_REJECT = 3;
    protected final int MSG_CALL_END = 4;
    protected final int MSG_CALL_RELEASE_HANDLER = 5;
    protected final int MSG_CALL_SWITCH_CAMERA = 6;

    protected boolean isInComingCall;
    protected boolean isRefused = false;
    protected String username;
    protected CallingState callingState = CallingState.CANCELLED;
    protected String callDruationText;
    protected String msgid;
    protected AudioManager audioManager;
    protected SoundPool soundPool;
    protected Ringtone ringtone;
    protected int outgoing;
    protected EMCallStateChangeListener callStateListener;
    protected boolean isAnswered = false;
    protected int streamID = -1;
    protected boolean isAction;//是否是主动操作（主动操作要发送消息）
    protected boolean isTimeOut = false;//是否超时
    EMCallManager.EMCallPushProvider pushProvider;

    /**
     * 0：voice call，1：video call
     */
    protected int callType = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        pushProvider = new EMCallManager.EMCallPushProvider() {

            void updateMessageText(final EMMessage oldMsg, final String to) {
                // update local message text
                EMConversation conv = EMClient.getInstance().chatManager().getConversation(oldMsg.getTo());
                conv.removeMessage(oldMsg.getMsgId());
            }

            @Override
            public void onRemoteOffline(final String to) {

                //this function should exposed & move to Demo
                EMLog.d(TAG, "onRemoteOffline, to:" + to);

                final EMMessage message = EMMessage.createTxtSendMessage("You have an incoming call", to);
                // set the user-defined extension field
                message.setAttribute("em_apns_ext", true);

                message.setAttribute("is_voice_call", callType == 0 ? true : false);

                message.setMessageStatusCallback(new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        EMLog.d(TAG, "onRemoteOffline success");
                        updateMessageText(message, to);
                    }

                    @Override
                    public void onError(int code, String error) {
                        EMLog.d(TAG, "onRemoteOffline Error");
                        updateMessageText(message, to);
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }
                });
                // send messages
                EMClient.getInstance().chatManager().sendMessage(message);
            }
        };

        EMClient.getInstance().callManager().setPushProvider(pushProvider);
    }

    @Override
    protected void onDestroy() {
        if (soundPool != null)
            soundPool.release();
        if (ringtone != null && ringtone.isPlaying())
            ringtone.stop();
        audioManager.setMode(AudioManager.MODE_NORMAL);
        audioManager.setMicrophoneMute(false);

        if (callStateListener != null)
            EMClient.getInstance().callManager().removeCallStateChangeListener(callStateListener);

        if (pushProvider != null) {
            EMClient.getInstance().callManager().setPushProvider(null);
            pushProvider = null;
        }
        releaseHandler();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        EMLog.d(TAG, "onBackPressed");
        handler.sendEmptyMessage(MSG_CALL_END);
        saveCallRecord();
        finish();
        super.onBackPressed();
    }

    Runnable timeoutHangup = new Runnable() {

        @Override
        public void run() {
            isAction = true;
            isTimeOut = true;
            callingState = CallingState.NO_RESPONSE;
            handler.sendEmptyMessage(MSG_CALL_END);
        }
    };

    HandlerThread callHandlerThread = new HandlerThread("callHandlerThread");

    {
        callHandlerThread.start();
    }

    protected Handler handler = new Handler(callHandlerThread.getLooper()) {
        @Override
        public void handleMessage(Message msg) {
            EMLog.d("EMCallManager CallActivity", "handleMessage ---enter block--- msg.what:" + msg.what);
            switch (msg.what) {
                case MSG_CALL_MAKE_VIDEO:
                    UserInfoManager.getUserInfo(TAG, UserInfoManager.getImId(), new DataCallBack<UserInfoBean>() {
                        @Override
                        public void onSuccess(UserInfoBean user) {
                            super.onSuccess(user);
                            try {
                                if (user != null) {
                                    JsonObject jsonObject = new JsonObject();
                                    jsonObject.addProperty("userName", UserInfoManager.handleUserName(user));
                                    jsonObject.addProperty("avatar", user.getAvatar());
                                    jsonObject.addProperty("conversationId", user.getImId());
                                    EMClient.getInstance().callManager().makeVideoCall(username, jsonObject.toString());
                                } else {
                                    EMClient.getInstance().callManager().makeVideoCall(username);
                                }

                            } catch (final EMServiceNotReadyException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        String st2 = e.getMessage();
                                        if (e.getErrorCode() == EMError.CALL_REMOTE_OFFLINE) {
                                            st2 = ResourceUtil.getString(R.string.The_other_is_not_online);
                                        } else if (e.getErrorCode() == EMError.USER_NOT_LOGIN) {
                                            st2 = ResourceUtil.getString(R.string.Is_not_yet_connected_to_the_server);
                                        } else if (e.getErrorCode() == EMError.INVALID_USER_NAME) {
                                            st2 = ResourceUtil.getString(R.string.illegal_user_name);
                                        } else if (e.getErrorCode() == EMError.CALL_BUSY) {
                                            st2 = ResourceUtil.getString(R.string.The_other_is_on_the_phone);
                                        } else if (e.getErrorCode() == EMError.NETWORK_ERROR) {
                                            st2 = ResourceUtil.getString(R.string
                                                    .can_not_connect_chat_server_connection);
                                        }
                                        ToastUtil.showToast(st2);

                                        finish();
                                    }
                                });
                            }
                        }
                    });
                    break;
                case MSG_CALL_ANSWER:
                    EMLog.d(TAG, "MSG_CALL_ANSWER");
                    if (ringtone != null)
                        ringtone.stop();
                    if (isInComingCall) {
                        try {
//                        EMClient.getInstance().callManager().answerCall();
//                        isAnswered = true;
                            // meizu MX5 4G, hasDataConnection(context) return status is incorrect
                            // MX5 con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected() return false in 4G
                            // so we will not judge it, App can decide whether judge the network status

                            if (NetUtils.hasDataConnection(CallActivity.this)) {
                                EMClient.getInstance().callManager().answerCall();
                                isAnswered = true;
                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        final String st2 = getResources().getString(R.string
                                                .Is_not_yet_connected_to_the_server);
                                        ToastUtil.showToast(st2);
                                    }
                                });
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            saveCallRecord();
                            finish();
                            return;
                        }
                    } else {
                        EMLog.d(TAG, "answer call isInComingCall:false");
                    }
                    break;
                case MSG_CALL_REJECT:
                    if (ringtone != null)
                        ringtone.stop();
                    try {
                        EMClient.getInstance().callManager().rejectCall();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        saveCallRecord();
                        finish();
                    }
                    callingState = CallingState.REFUSED;
                    break;
                case MSG_CALL_END:
                    if (soundPool != null)
                        soundPool.stop(streamID);
                    EMLog.d("EMCallManager", "soundPool stop MSG_CALL_END");
                    try {
                        EMClient.getInstance().callManager().endCall();
                    } catch (Exception e) {
                        saveCallRecord();
                        finish();
                    }

                    break;
                case MSG_CALL_RELEASE_HANDLER:
                    try {
                        EMClient.getInstance().callManager().endCall();
                    } catch (Exception e) {
                    }
                    handler.removeCallbacks(timeoutHangup);
                    handler.removeMessages(MSG_CALL_MAKE_VIDEO);
                    handler.removeMessages(MSG_CALL_MAKE_VOICE);
                    handler.removeMessages(MSG_CALL_ANSWER);
                    handler.removeMessages(MSG_CALL_REJECT);
                    handler.removeMessages(MSG_CALL_END);
                    callHandlerThread.quit();
                    break;
                case MSG_CALL_SWITCH_CAMERA:
                    try {
                        EMClient.getInstance().callManager().switchCamera();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            EMLog.d("EMCallManager CallActivity", "handleMessage ---exit block--- msg.what:" + msg.what);
        }
    };

    public EMMessage sendCustomMessage(BaseMessageBean bean) {
        String json = JsonUtil.toJson(bean);
        LogUtil.d("mytag", json + "==");
        EMMessage message = EMMessage.createTxtSendMessage("[视频通话]", username);
        message.setAttribute(EaseConstant.MESSAGE_ATTR_KEY, json);
        sendMessage(message);
        return message;
    }

    public void sendMessage(EMMessage message) {
        if (message == null) {
            return;
        }
        message.setDirection(EMMessage.Direct.SEND);
        EMClient.getInstance().chatManager().saveMessage(message);
//        EMClient.getInstance().chatManager().sendMessage(message);
        /**
         * 发送聊天页面刷新事件 {@link com.cody.app.business.im.ChatActivity#onEventReceiver(String)}
         */
        SimpleEventBus.sentEvent(this, SimpleEventBusKey.REFRESH_CHAT);
    }

    void releaseHandler() {
        handler.sendEmptyMessage(MSG_CALL_RELEASE_HANDLER);
    }

    /**
     * play the incoming call ringtone
     */
    protected int playMakeCallSounds() {
        try {
            audioManager.setMode(AudioManager.MODE_RINGTONE);
            audioManager.setSpeakerphoneOn(true);

            // play
            int id = soundPool.play(outgoing, // sound resource
                    0.3f, // left volume
                    0.3f, // right volume
                    1,    // priority
                    -1,   // loop，0 is no loop，-1 is loop forever
                    1);   // playback rate (1.0 = normal playback, range 0.5 to 2.0)
            return id;
        } catch (Exception e) {
            return -1;
        }
    }

    protected void openSpeakerOn() {
        try {
            if (!audioManager.isSpeakerphoneOn())
                audioManager.setSpeakerphoneOn(true);
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void closeSpeakerOn() {

        try {
            if (audioManager != null) {
                // int curVolume =
                // audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
                if (audioManager.isSpeakerphoneOn())
                    audioManager.setSpeakerphoneOn(false);
                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                // audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                // curVolume, AudioManager.STREAM_VOICE_CALL);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected int callMessageType = 0;
    protected long timeLength = 0;

    /**
     * save call record
     */
    protected void saveCallRecord() {
        String st1 = getResources().getString(R.string.call_duration);
        String st2 = getResources().getString(R.string.Refused);
        String st3 = getResources().getString(R.string.The_other_party_has_refused_to);
        String st4 = getResources().getString(R.string.The_other_is_not_online);
        String st5 = getResources().getString(R.string.The_other_is_on_the_phone);
        String st6 = getResources().getString(R.string.The_other_party_did_not_answer);
        String st7 = getResources().getString(R.string.did_not_answer);
        String st8 = getResources().getString(R.string.Has_been_cancelled);
        switch (callingState) {
            case NORMAL:
                callMessageType = CustomMessage.VideoCallStatus.callerHangup;
                break;
            case REFUSED:
                callMessageType = CustomMessage.VideoCallStatus.decline;
                break;
            case BEREFUSED:
                callMessageType = CustomMessage.VideoCallStatus.decline;
                break;
            case OFFLINE:
                isAction = true;
                callMessageType = CustomMessage.VideoCallStatus.remoteOffline;
                break;
            case BUSY:
                callMessageType = CustomMessage.VideoCallStatus.remoteBusy;
                break;
            case NO_RESPONSE:
                callMessageType = CustomMessage.VideoCallStatus.noResponse;
                break;
            case UNANSWERED:
                callMessageType = CustomMessage.VideoCallStatus.remoteBusy;
                break;
            case VERSION_NOT_SAME:
                callMessageType = CustomMessage.VideoCallStatus.callFailed;
                break;
            default:
                callMessageType = CustomMessage.VideoCallStatus.callerCancel;
                break;
        }

        if (isAction && callMessageType != 0) {
            BaseMessageBean messageBean = new BaseMessageBean();
            messageBean.setType(CustomMessage.PersonVideo);
            IMVideoCallMessageBean imVideoCallMessageBean = new IMVideoCallMessageBean();
            imVideoCallMessageBean.setCallMessageType(callMessageType);
            imVideoCallMessageBean.setTimeLength(getChronometerSeconds(callDruationText));
            messageBean.setJSONContent(JsonUtil.toJson(imVideoCallMessageBean));
            sendCustomMessage(messageBean);
            //ToastUtil.makeToast(mContext, txtBody.getMessage() + "==" + isAction + "==" + callMessageType + "==" +
            // getChronometerSeconds(callDruationText));

        }
    }

    public static int getChronometerSeconds(String timeText) {
        int totalss = 0;
        String string = timeText;
        if (TextUtils.isEmpty(string)) {
            return 0;
        }
        if (string.length() == 7) {
            String[] split = string.split(":");
            String string2 = split[0];
            int hour = Integer.parseInt(string2);
            int Hours = hour * 3600;
            String string3 = split[1];
            int min = Integer.parseInt(string3);
            int Mins = min * 60;
            int SS = Integer.parseInt(split[2]);
            totalss = Hours + Mins + SS;
        } else if (string.length() == 5) {
            String[] split = string.split(":");
            String string3 = split[0];
            int min = Integer.parseInt(string3);
            int Mins = min * 60;
            int SS = Integer.parseInt(split[1]);
            totalss = Mins + SS;
        }
        return totalss;
    }

    enum CallingState {
        CANCELLED, NORMAL, REFUSED, BEREFUSED, UNANSWERED, OFFLINE, NO_RESPONSE, BUSY, VERSION_NOT_SAME
    }
}
