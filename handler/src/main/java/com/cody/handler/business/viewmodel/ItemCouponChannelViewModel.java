package com.cody.handler.business.viewmodel;

import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Create by jiquan.zhong  on 2018/8/16.
 * description:增发优惠券
 */
public class ItemCouponChannelViewModel extends XItemViewModel {
    private String mChannelId;//渠道ID,
    private String mChannelName;//渠道名称,
    private final ObservableField<String> mCount = new ObservableField<>();

    public String getChannelId() {
        return mChannelId;
    }

    public void setChannelId(String channelId) {
        mChannelId = channelId;
    }

    public String getChannelName() {
        return mChannelName;
    }

    public void setChannelName(String channelName) {
        mChannelName = channelName;
    }

    public ObservableField<String> getCount() {
        return mCount;
    }
}
