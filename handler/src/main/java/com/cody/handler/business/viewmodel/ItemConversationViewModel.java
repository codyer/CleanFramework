package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.cody.handler.business.EaseCommonUtils;
import com.cody.handler.framework.viewmodel.XItemViewModel;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.DateUtil;

public class ItemConversationViewModel extends XItemViewModel {
    private final Object mTag = this.getClass().getSimpleName();
    private final ObservableField<String> mImageUrl = new ObservableField<>("");
    private final ObservableField<String> mName = new ObservableField<>("");
    private final ObservableInt mUnreadMsgCount = new ObservableInt(0);
    private final ObservableBoolean isTop = new ObservableBoolean(false); //会话是否置顶
    private String mGroupId; //分组id
    private String mGroupName;//分组名
    private String mConversationId; //会话id
    private EMConversation mEMConversation;
    private long mTime;
    private String mContent;

    // 系统消息
    private ObservableField<String> mSysTitle = new ObservableField<>("系统消息");
    private ObservableField<String> mSysContent = new ObservableField<>("暂无数据");
    private ObservableField<String> mSysTime = new ObservableField<>();

    public ObservableField<String> getSysTitle() {
        return mSysTitle;
    }

    public void setSysTitle(String sysTitle) {
        mSysTitle.set(sysTitle);
    }

    public ObservableField<String> getSysContent() {
        return mSysContent;
    }

    public void setSysContent(String sysContent) {
        mSysContent.set(sysContent);
    }

    public ObservableField<String> getSysTime() {
        return mSysTime;
    }

    public void setSysTime(String sysTime) {
        this.mSysTime.set(sysTime);
    }

    public EMConversation getEMConversation() {
        return mEMConversation;
    }

    public void setEMConversation(EMConversation emConversation) {
        mEMConversation = emConversation;

        setConversationId(emConversation.conversationId());
        getUnreadMsgCount().set(emConversation.getUnreadMsgCount());
        if ("true".equals(emConversation.getExtField())) {
            getIsTop().set(true);
        } else {
            getIsTop().set(false);
        }

        EMMessage message = emConversation.getLastMessage();
        UserInfoManager.getUserInfo(mTag, emConversation.conversationId(),
                new DataCallBack<UserInfoBean>() {
                    @Override
                    public void onSuccess(UserInfoBean userInfoBean) {
                        super.onSuccess(userInfoBean);
                        if (!mImageUrl.get().equals(userInfoBean.getAvatar())) {
                            mImageUrl.set(userInfoBean.getAvatar());
                        }
                        String name = UserInfoManager.handleUserName(userInfoBean);
                        if (!mName.get().equals(name)) {
                            mName.set(name);
                        }
                        setGroupName(userInfoBean.getGroupName());
                        setGroupId(userInfoBean.getGroupId());
                    }
                });
        if (message == null) return;
        setTime(message.getMsgTime());
        setContent(EaseCommonUtils.getMessageDigest(message, XFoundation.getContext()));
    }

    public ObservableField<String> getImageUrl() {
        return mImageUrl;
    }

    public ObservableField<String> getName() {
        return mName;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }

    public String getTime() {
        return DateUtil.getNewChatTime(mTime);
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public ObservableInt getUnreadMsgCount() {
        return mUnreadMsgCount;
    }

    public ObservableBoolean getIsTop() {
        return isTop;
    }

    public String getGroupId() {
        return mGroupId;
    }

    public void setGroupId(String groupId) {
        mGroupId = groupId;
    }

    public String getConversationId() {
        return mConversationId;
    }

    public void setConversationId(String conversationId) {
        mConversationId = conversationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemConversationViewModel that = (ItemConversationViewModel) o;

        if (isTop.get() != that.isTop.get()) return false;
        if (mUnreadMsgCount.get() != that.mUnreadMsgCount.get()) return false;
        if (mTime != that.mTime) return false;
        if (mImageUrl.get() != null ? !mImageUrl.get().equals(that.mImageUrl.get()) : that.mImageUrl.get() != null)
            return false;
        if (mName.get() != null ? !mName.get().equals(that.mName.get()) : that.mName.get() != null)
            return false;
        if (mSysContent.get() != null ? !mSysContent.get().equals(that.mSysContent.get()) : that.mSysContent.get() != null)
            return false;
        if (mGroupId != null ? !mGroupId.equals(that.mGroupId) : that.mGroupId != null)
            return false;
        if (mSysTime.get() != null ? !mSysTime.get().equals(that.mSysTime.get()) : that.mSysTime.get() != null)
            return false;
        if (mSysTitle.get() != null ? !mSysTitle.get().equals(that.mSysTitle.get()) : that.mSysTitle.get() != null)
            return false;
        if (mConversationId != null ? !mConversationId.equals(that.mConversationId) : that.mConversationId != null)
            return false;
        return mContent != null ? mContent.equals(that.mContent) : that.mContent == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mImageUrl.get() != null ? mImageUrl.get().hashCode() : 0);
        result = 31 * result + (mName.get() != null ? mName.get().hashCode() : 0);
        result = 31 * result + (mSysContent.get() != null ? mSysContent.get().hashCode() : 0);
        result = 31 * result + (isTop.get() ? 1 : 0);
        result = 31 * result + mUnreadMsgCount.get();
        result = 31 * result + (mGroupId != null ? mGroupId.hashCode() : 0);
        result = 31 * result + (mSysTime.get() != null ? mSysTime.get().hashCode() : 0);
        result = 31 * result + (mSysTitle.get() != null ? mSysTitle.get().hashCode() : 0);
        result = 31 * result + (mConversationId != null ? mConversationId.hashCode() : 0);
        result = 31 * result + (int) (mTime ^ (mTime >>> 32));
        result = 31 * result + (mContent != null ? mContent.hashCode() : 0);
        return result;
    }
}