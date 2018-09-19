package com.cody.app.business.easeui.domain;

import android.text.TextUtils;

import com.cody.app.business.easeui.EaseConstant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.cody.repository.business.bean.im.BaseMessageBean;
import com.cody.repository.business.bean.im.CustomMessage;
import com.cody.repository.business.bean.im.IMVideoCallMessageBean;
import com.cody.xf.utils.JsonUtil;


/**
 * Created by chen.huarong on 2018/4/9.
 * 消息管理类
 */
public class EMMessageManager {
    /**
     * 创建自定义消息
     *
     * @param jsonObject 消息json对象（BaseMessageBean）
     * @param username   接收者
     */
    public static EMMessage createCustomMessage(String jsonObject, String username) {
        try {
            if (TextUtils.isEmpty(jsonObject)
                    || TextUtils.isEmpty(username)) {
                return null;
            }
            EMMessage message = EMMessage.createTxtSendMessage(" ", username);
            message.setAttribute(EaseConstant.MESSAGE_ATTR_KEY, jsonObject);

            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发送自定义消息
     *
     * @param jsonObject 消息json对象（BaseMessageBean）
     * @param username   接收者
     */
    public static void sendCustomMessage(String jsonObject, String username) {
        EMMessage message = createCustomMessage(jsonObject, username);
        if (message != null) {
            EMClient.getInstance().chatManager().sendMessage(message);
        }
    }

    /**
     * 发送通话状态
     *
     * @param username
     * @param callStatus
     * @param timeLength
     */
    public static void sendVideoCallMessage(String username, int callStatus, int timeLength) {
        IMVideoCallMessageBean videoCallMessageBean = new IMVideoCallMessageBean();
        videoCallMessageBean.setCallMessageType(callStatus);
        if (timeLength > 0) {
            videoCallMessageBean.setTimeLength(timeLength);
        }
        sendCustomMessage(JsonUtil.toJson(videoCallMessageBean), username);
    }

    /**
     * 发送通话状态
     *
     * @param username
     * @param callStatus
     */
    public static void sendVideoCallMessage(String username, int callStatus) {
        sendVideoCallMessage(username, callStatus, -1);
    }


    /**
     * 是否自定义消息bean类
     */
    public static boolean isCustomMessage(EMMessage message) {
        return BaseMessageBean.getExtraMsgBean(message).type != CustomMessage.NO_DIY;
    }
}
