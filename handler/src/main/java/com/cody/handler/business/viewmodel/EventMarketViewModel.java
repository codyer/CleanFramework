package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Create by jiquan.zhong  on 2018/8/14.
 * description:
 */
public class EventMarketViewModel extends WithHeaderViewModel {
    private final ObservableBoolean mHasNew = new ObservableBoolean(false);//是否有新增优惠券

    public ObservableBoolean getHasNew() {
        return mHasNew;
    }
}
