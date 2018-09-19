package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.cody.handler.framework.viewmodel.ListWithHeaderViewModel;

/**
 * Created by dong.wang
 * Date: 2018/8/18
 * Time: 15:27
 * Description: 选择优惠券
 */
public class CouponsListViewModel extends ListWithHeaderViewModel<CouponViewModel> {
    private final ObservableBoolean mIsSelectedAll = new ObservableBoolean(false); // 是否全选
    private final ObservableInt mSentSum = new ObservableInt(); // 发送数量
    private final ObservableField<String> mSentSumStr = new ObservableField<>("发送"); // 发送总数

    public ObservableBoolean getIsSelectedAll() {
        return mIsSelectedAll;
    }

    public ObservableField<String> getSentSumStr() {
        return mSentSumStr;
    }

    public ObservableInt getSentSum() {
        return mSentSum;
    }

    public void setSentSum(int sum) {
        mSentSumStr.set(String.format("发送(%d)", sum));
        mSentSum.set(sum);
    }
}
