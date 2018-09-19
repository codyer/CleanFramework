package com.cody.handler.business.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.cody.handler.framework.viewmodel.XItemViewModel;
import com.cody.xf.utils.DateUtil;

/**
 * Created by cody.yi on 2018/7/20.
 * 首页im item
 */
public class MainImItemViewModel extends XItemViewModel {
    final private ObservableField<String> mImageUrl = new ObservableField<>();
    final private ObservableField<String> mName = new ObservableField<>();
    private final ObservableInt mUnreadMsgCount = new ObservableInt(0);
    private long mTime;
    private String mContent;

    public ObservableInt getUnreadMsgCount() {
        return mUnreadMsgCount;
    }

    public ObservableField<String> getImageUrl() {
        return mImageUrl;
    }

    public ObservableField<String> getName() {
        return mName;
    }

    public String getTimeString() {
        return DateUtil.getNewChatTime(mTime);
    }

    public long getTime() {
        return mTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MainImItemViewModel that = (MainImItemViewModel) o;

        if (mTime != that.mTime) return false;

        // 忽略这三项
//        if (mTag != null ? !mTag.equals(that.mTag) : that.mTag != null) return false;
//        if (mMessage != null ? !mMessage.equals(that.mMessage) : that.mMessage != null)
//            return false;
//        if (mImageUrl != null ? !mImageUrl.equals(that.mImageUrl) : that.mImageUrl != null)
//            return false;
//        if (mName != null ? !mName.equals(that.mName) : that.mName != null) return false;
        return mContent != null ? mContent.equals(that.mContent) : that.mContent == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
//        result = 31 * result + (mTag != null ? mTag.hashCode() : 0);
//        result = 31 * result + (mMessage != null ? mMessage.hashCode() : 0);
//        result = 31 * result + (mImageUrl != null ? mImageUrl.hashCode() : 0);
//        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (int) (mTime ^ (mTime >>> 32));
        result = 31 * result + (mContent != null ? mContent.hashCode() : 0);
        return result;
    }
}
