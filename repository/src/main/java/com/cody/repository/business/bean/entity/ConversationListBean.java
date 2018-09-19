package com.cody.repository.business.bean.entity;

import com.hyphenate.chat.EMConversation;

/**
 * Created by  dong.wang
 * Date: 2017/3/6
 * Time: 10:58
 * Description: 最新联系人列表
 */
public class ConversationListBean {
    private UserInfoBean mUserInfo;
    private long mTime;
    private String mMessage;
    private boolean isTop;

    public EMConversation getEMConversation() {
        return mEMConversation;
    }

    public void setEMConversation(EMConversation EMConversation) {
        mEMConversation = EMConversation;
    }

    private EMConversation mEMConversation;

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public int getUnreadMsgCount() {
        return mUnreadMsgCount;
    }

    public void setUnreadMsgCount(int unreadMsgCount) {
        mUnreadMsgCount = unreadMsgCount;
    }

    private int mUnreadMsgCount;

    public UserInfoBean getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        mUserInfo = userInfo;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
