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
package com.cody.handler.business;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.EMLog;
import com.cody.handler.R;
import com.cody.handler.business.viewmodel.ChatViewModel;
import com.cody.repository.business.bean.im.BaseMessageBean;
import com.cody.repository.business.bean.im.CustomMessage;
import com.cody.xf.utils.JsonUtil;

import java.util.List;

public class EaseCommonUtils {
    private static final String TAG = "CommonUtils";

    /**
     * check if network avalable
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }

        return false;
    }

    /**
     * check if sdcard exist
     *
     * @return
     */
    public static boolean isSdcardExist() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    // TODO: 2018/7/25 需要清理的方法
    public static EMMessage createExpressionMessage(String toChatUsername, String expressioName, String identityCode) {
        EMMessage message = EMMessage.createTxtSendMessage("[" + expressioName + "]", toChatUsername);
//        if (identityCode != null) {
//            message.setAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, identityCode);
//        }
//        message.setAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, true);
        return message;
    }

    /**
     * Get digest according message type and content
     *
     * @param message
     * @param context
     * @return
     */
    public static String getMessageDigest(EMMessage message, Context context) {
        String digest = "";
        switch (message.getType()) {
            case LOCATION:
                if (message.direct() == EMMessage.Direct.RECEIVE) {
                    digest = getString(context, R.string.h_location_recv);
                    digest = String.format(digest, message.getFrom());
                    return digest;
                } else {
                    digest = getString(context, R.string.h_location_prefix);
                }
                break;
            case IMAGE:
                digest = getString(context, R.string.h_picture);
                break;
            case VOICE:
                digest = getString(context, R.string.h_voice_prefix);
                break;
            case VIDEO:
                digest = getString(context, R.string.h_video);
                break;
            case TXT:
                EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
                if (message.getBooleanAttribute(ChatViewModel.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    digest = getString(context, R.string.h_voice_call) + txtBody.getMessage();
                } else if (message.getBooleanAttribute(ChatViewModel.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    digest = getString(context, R.string.h_video_call) + txtBody.getMessage();
                } else if (message.getBooleanAttribute(ChatViewModel.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)) {
                    if (!TextUtils.isEmpty(txtBody.getMessage())) {
                        digest = txtBody.getMessage();
                    } else {
                        digest = getString(context, R.string.h_dynamic_expression);
                    }
                } else {
                    String msg = message.getStringAttribute(BaseMessageBean.MESSAGE_ATTR_KEY, null);
                    if (!TextUtils.isEmpty(msg)) {
                        BaseMessageBean baseMessageBean = JsonUtil.fromJson(msg, BaseMessageBean.class);
                        if (baseMessageBean != null) {
                            switch (baseMessageBean.getType()) {
                                case CustomMessage.SystemMerchandise:
                                    digest = getString(context, R.string.h_systemmerchandise);
                                    break;
                                case CustomMessage.PersonLocation:
                                    digest = getString(context, R.string.h_personlocation);
                                    break;
                                case CustomMessage.PersonGuideSwitch:
                                    digest = getString(context, R.string.h_personguideswitch);
                                    break;
                                case CustomMessage.PersonCoupon:
                                    digest = getString(context, R.string.h_personcoupon);
                                    break;
                                case CustomMessage.PersonVideo:
                                    digest = getString(context, R.string.h_personvideo);
                                    break;
                                case CustomMessage.UserOrder:
                                    digest = getString(context, R.string.h_personorder);
                                    break;
                                case CustomMessage.PersonRecommendGoods:
                                    digest = getString(context, R.string.h_personrecommendgoods);
                                    break;
                                case CustomMessage.SysGreetingWords:
                                    digest = getString(context, R.string.h_sysgreetingwords);
                                    break;
                                case CustomMessage.UserLike:
                                    digest = getString(context, R.string.h_like);
                                    break;
                                default:
                                    digest = txtBody.getMessage();
                                    break;
                            }
                        }
                    } else {
                        digest = txtBody.getMessage();
                    }
                }
                break;
            case FILE:
                digest = getString(context, R.string.h_file);
                break;
            default:
                EMLog.e(TAG, "error, unknow type");
                return "";
        }

        return digest;
    }

    static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * get top activity
     *
     * @param context
     * @return
     */
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null)
            return runningTaskInfos.get(0).topActivity.getClassName();
        else
            return "";
    }

    /**
     * change the chat type to EMConversationType
     *
     * @param chatType
     * @return
     */
    @Deprecated
    public static EMConversationType getConversationType(int chatType) {
//        if (chatType == ChatViewModel.CHATTYPE_SINGLE) {
//            return EMConversationType.Chat;
//        } else if (chatType == EaseConstant.CHATTYPE_GROUP) {
//            return EMConversationType.GroupChat;
//        } else {
//            return EMConversationType.ChatRoom;
//        }
        // TODO: 2018/7/25 无用可以清理
        return null;
    }

    /**
     * \~chinese
     * 判断是否是免打扰的消息,如果是app中应该不要给用户提示新消息
     *
     * @param message return
     *                <p>
     *                \~english
     *                check if the message is kind of slient message, if that's it, app should not play tone or vibrate
     * @param message
     * @return
     */
    public static boolean isSilentMessage(EMMessage message) {
        return message.getBooleanAttribute("em_ignore_notification", false);
    }

}
