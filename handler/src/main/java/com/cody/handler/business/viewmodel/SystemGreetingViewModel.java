package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;
import com.cody.repository.business.database.UserInfoManager;

/**
 * 系统欢迎词设置页
 */
public class SystemGreetingViewModel extends WithHeaderViewModel {
    private boolean mIsChanged = false;

    public boolean isChanged() {
        return mIsChanged;
    }

    public void setChanged(boolean changed) {
        mIsChanged = changed;
    }

    private String mGreetingId;
    private String mOwnerId;
    private final ObservableField<String> mTextCount = new ObservableField<>();
    private final ObservableBoolean mEnable = new ObservableBoolean(false);
    private final ObservableField<String> mText = new ObservableField<>();
    private final ListViewModel<ItemSystemGreetingGoodsViewModel> mGoods = new ListViewModel<>();

    public ObservableField<String> getTextCount() {
        return mTextCount;
    }

    public void setTextCount(int count) {
        mTextCount.set(count + "/140");
    }

    public String getGreetingId() {
        return mGreetingId;
    }

    public void setGreetingId(String greetingId) {
        mGreetingId = greetingId;
    }

    public String getOwnerId() {
        if (TextUtils.isEmpty(mOwnerId)) {
            return UserInfoManager.getImId();
        }
        return mOwnerId;
    }

    public void setOwnerId(String ownerId) {
        mOwnerId = ownerId;
    }

    public ObservableBoolean getEnable() {
        return mEnable;
    }

    public ObservableField<String> getText() {
        return mText;
    }

    public ListViewModel<ItemSystemGreetingGoodsViewModel> getGoods() {
        return mGoods;
    }
}
