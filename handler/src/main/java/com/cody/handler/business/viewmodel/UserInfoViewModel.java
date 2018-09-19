package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Create by jiquan.zhong  on 2018/7/30.
 * description:
 */
public class UserInfoViewModel extends WithHeaderViewModel {
    private String mImId;
    private String mGroupId;
    private final ObservableField<String> mGroupName = new ObservableField<>();
    private final ObservableField<String> mTextCount = new ObservableField<>();
    private final ObservableField<String> remarkName = new ObservableField<>("");
    private final ObservableField<String> remark = new ObservableField<>("");
    private String mNickName;
    private String avatar;
    private final ObservableBoolean mShowDelete = new ObservableBoolean(false);//是否显示删除

    public ObservableField<String> getTextCount() {
        return mTextCount;
    }

    public void setTextCount(int count) {
        mTextCount.set(count + "/30");
    }

    public String getImId() {
        return mImId;
    }

    public void setImId(String imId) {
        mImId = imId;
    }

    public String getGroupId() {
        return mGroupId;
    }

    public void setGroupId(String groupId) {
        setShowDelete(!TextUtils.isEmpty(groupId));
        mGroupId = groupId;
    }

    public ObservableField<String> getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        mGroupName.set(groupName);
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public ObservableField<String> getRemarkName() {
        return remarkName;
    }

    public ObservableField<String> getRemark() {
        return remark;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ObservableBoolean getShowDelete() {
        return mShowDelete;
    }

    public void setShowDelete(boolean b) {
        mShowDelete.set(b);
    }
}
