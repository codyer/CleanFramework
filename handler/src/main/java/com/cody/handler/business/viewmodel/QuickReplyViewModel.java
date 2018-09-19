package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Created by chen.huarong on 2018/7/30.
 */
public class QuickReplyViewModel extends WithHeaderViewModel {
    private boolean mChanged = false;

    public boolean isChanged() {
        return mChanged;
    }

    public void setChanged(boolean changed) {
        mChanged = changed;
    }

    private ObservableBoolean enable = new ObservableBoolean(false);
    private ObservableField<String> mContent = new ObservableField<>();
    private String ownerId;
    private String qrId;
    private long createdAt;
    private ObservableField<String> title = new ObservableField<>("快捷回复语");

    public ObservableBoolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable.set(enable);
    }

    public ObservableField<String> getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent.set(content);
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getQrId() {
        return qrId;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
    }
}
