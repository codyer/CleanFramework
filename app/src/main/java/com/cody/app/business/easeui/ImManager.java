package com.cody.app.business.easeui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cody.app.BuildConfig;
import com.cody.app.R;
import com.cody.app.framework.EventBus.SimpleEventBus;
import com.cody.app.framework.EventBus.SimpleEventBusKey;
import com.cody.app.business.im.ChatActivity;
import com.cody.app.business.easeui.model.EaseNotifier;
import com.cody.app.business.easeui.receiver.CallReceiver;
import com.cody.app.business.easeui.ui.VideoCallActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMMessage.Status;
import com.hyphenate.chat.EMMessage.Type;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.EMLog;
import com.cody.handler.business.EaseCommonUtils;
import com.cody.repository.KeyConstant;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.widget.popup.CommonPopupWindow;

import java.util.List;

public class ImManager {

    protected final String TAG = this.getClass().getSimpleName();

    private EaseUI easeUI;

    private static ImManager instance = null;

    private EMConnectionListener connectionListener;

    public boolean isVideoCalling;

    private Context appContext;

    private CallReceiver callReceiver;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ImManager() {
    }

    public synchronized static ImManager getInstance() {
        if (instance == null) {
            instance = new ImManager();
        }
        return instance;
    }

    /**
     * init helper
     *
     * @param context application context
     */
    public void init(Context context) {
        EMOptions options = initChatOptions();
        //use default options if options is null
        if (EaseUI.getInstance().init(context, options)) {
            appContext = context;
            EMClient.getInstance().setDebugMode(BuildConfig.DEBUG);
            easeUI = EaseUI.getInstance();
            setNotifierProviders();
            setGlobalListeners();
        }
    }

    private EMOptions initChatOptions() {
        EMOptions options = new EMOptions();
        options.setAppKey(KeyConstant.IM_KEY);
        return options;
    }

    /**
     * 设置系统通知
     */
    protected void setNotifierProviders() {
        //set notification options, will use default if you don't set it
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //you can update title here
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //you can update icon here
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // be used on notification bar, different text according the message type.
                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                if (message.getType() == Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                UserInfoBean user = UserInfoManager.getUserInfo(message.conversationId());
                if (user != null) {
                    String name = UserInfoManager.handleUserName(user);
                    return name + ": " + ticker;
                } else {
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                // here you can customize the text.
                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
                return null;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                // you can set what activity you want display when user click the notification
                Intent intent = new Intent(appContext, ChatActivity.class);

                // open calling activity if there is call
                if (isVideoCalling) {
                    intent = new Intent(appContext, VideoCallActivity.class);
                } else {
                    ChatType chatType = message.getChatType();
                    if (chatType == ChatType.Chat) { // single chat message
                        intent.putExtra(EaseConstant.EXTRA_USER_ID, message.getFrom());
//                        intent.putExtra("chatType", EaseConstant.CHATTYPE_SINGLE);
                        intent.putExtra("chat_message", true);
                    } else { // group chat message
                        // message.getTo() is the group id
                        intent.putExtra(EaseConstant.EXTRA_USER_ID, message.getTo());
                        if (chatType == ChatType.GroupChat) {
                            intent.putExtra("chatType", EaseConstant.CHATTYPE_GROUP);
                        } else {
                            intent.putExtra("chatType", EaseConstant.CHATTYPE_CHATROOM);
                        }
                    }
                }
                return intent;
            }
        });
    }

    /**
     * set global listener
     */
    protected void setGlobalListeners() {
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        //register incoming call receiver
        appContext.registerReceiver(callReceiver, callFilter);
        registerMessageListener();
        registerConnectionListener();
    }

    /**
     * user met some exception: conflict, removed or forbidden
     */
    protected void onUserException(String exception) {
        EMLog.e(TAG, "onUserException: " + exception);
        if (EaseConstant.ACCOUNT_CONFLICT.equals(exception)) {
            SimpleEventBus.sentEvent(appContext, SimpleEventBusKey.USER_LOGIN_ANOTHER_DEVICE);
            showPopupWindow();
        }
    }

    /**
     * 其他设备登录是否需要重连弹窗
     */
    private void showPopupWindow() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (ActivityUtil.isActivityDestroyed()) return;
                new CommonPopupWindow.Builder(ActivityUtil.getCurrentActivity())
                        .setView(R.layout.xf_view_alertdialog)
                        .setOutsideTouchable(false)
                        .setBackGroundLevel(0.5f)
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(final CommonPopupWindow popupWindow, View view, int layoutResId) {
                                ((TextView) view.findViewById(R.id.txt_msg)).setText("您的IM账号已在其他设备登录，是否重新连接？");
                                Button btn_neg = view.findViewById(R.id.btn_neg);
                                Button btn_pos = view.findViewById(R.id.btn_pos);
                                btn_neg.setText("取消");
                                btn_pos.setText("确定");
                                btn_neg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popupWindow.dismiss();
                                    }
                                });
                                btn_pos.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        isReconnectIM();
                                        popupWindow.dismiss();
                                    }
                                });
                            }
                        })
                        .create()
                        .showAtLocation(ActivityUtil.getCurrentActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
            }
        });
    }

    /**
     * Global listener
     * If this event already handled by an activity, you don't need handle it again
     * activityList.size() <= 0 means all activities already in background or not in Activity Stack
     */
    private void registerMessageListener() {
        EMMessageListener messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    // in background, do not refresh UI, notify it in notification bar
                    getNotifier().onNewMsg(message);
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "receive command message");
                    //get message body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                    EMLog.d(TAG, String.format("Command：action:%s,message:%s", action, message.toString()));
                }
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                for (EMMessage msg : messages) {
                    EMMessage msgNotification = EMMessage.createReceiveMessage(Type.TXT);
                    EMTextMessageBody txtBody = new EMTextMessageBody(String.format(appContext.getString(R.string
                            .msg_recall_by_user), msg.getFrom()));
                    msgNotification.addBody(txtBody);
                    msgNotification.setFrom(msg.getFrom());
                    msgNotification.setTo(msg.getTo());
                    msgNotification.setUnread(false);
                    msgNotification.setMsgTime(msg.getMsgTime());
                    msgNotification.setLocalTime(msg.getMsgTime());
                    msgNotification.setChatType(msg.getChatType());
                    msgNotification.setAttribute(EaseConstant.MESSAGE_TYPE_RECALL, true);
                    msgNotification.setStatus(Status.SUCCESS);
                    EMClient.getInstance().chatManager().saveMessage(msgNotification);
                }
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                EMLog.d(TAG, "change:");
                EMLog.d(TAG, "change:" + change);
            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    /**
     * create the global connection listener
     */
    private void registerConnectionListener() {
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                EMLog.d("global listener", "onDisconnect" + error);
                SimpleEventBus.sentEvent(appContext, SimpleEventBusKey.ON_DISCONNECTED);
                if (error == EMError.USER_REMOVED) {
                    onUserException(EaseConstant.ACCOUNT_REMOVED);
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    onUserException(EaseConstant.ACCOUNT_CONFLICT);
                } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {
                    onUserException(EaseConstant.ACCOUNT_FORBIDDEN);
                } else if (error == EMError.USER_KICKED_BY_CHANGE_PASSWORD) {
                    onUserException(EaseConstant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD);
                } else if (error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                    onUserException(EaseConstant.ACCOUNT_KICKED_BY_OTHER_DEVICE);
                }
            }

            @Override
            public void onConnected() {
                EMLog.d(TAG, "group and contact already synced with servre");
                SimpleEventBus.sentEvent(appContext, SimpleEventBusKey.ON_CONNECTED);
            }
        };
        //register connection listener
        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    /**
     * if ever logged in
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * logout
     *
     * @param unbindDeviceToken whether you need unbind your device token
     * @param callback          callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "logout: onSuccess");
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                Log.d(TAG, "logout: onSuccess");
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    /**
     * get instance of EaseNotifier
     */
    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * IM掉线重连功能
     */
    public void isReconnectIM() {
        if (!EMClient.getInstance().isConnected()) {

            if (TextUtils.isEmpty(UserInfoManager.getImId())) return;//登录环信IM

            EMClient.getInstance().login(UserInfoManager.getImId(), UserInfoManager.getImId(), new EMCallBack() {
                @Override
                public void onSuccess() {
                    Log.d("main", "登录聊天服务器成功！");
                    EMClient.getInstance().chatManager().loadAllConversations();
                }

                @Override
                public void onError(int code, String error) {
                    Log.d("main", "登录聊天服务器失败！");
                }

                @Override
                public void onProgress(int progress, String status) {

                }
            });
        }
    }
}