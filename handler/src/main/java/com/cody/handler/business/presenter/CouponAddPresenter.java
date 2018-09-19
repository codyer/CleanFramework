package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.cody.handler.business.mapper.CouponChannelMapper;
import com.cody.handler.business.viewmodel.CouponAddViewModel;
import com.cody.handler.business.viewmodel.ItemCouponChannelViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.ListWithHeaderBasePresenter;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.repository.business.bean.CouponChannelBean;
import com.cody.repository.business.interaction.CouponInteraction;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.http.SimpleBean;

import java.util.List;
import java.util.Map;

/**
 * Create by jiquan.zhong  on 2018/8/16.
 * description:增发优惠券
 */
public class CouponAddPresenter extends ListWithHeaderBasePresenter<CouponAddViewModel, ItemCouponChannelViewModel> {
    private CouponInteraction mInteraction;
    private CouponChannelMapper mMapper;

    public CouponAddPresenter() {
        mInteraction = Repository.getInteraction(CouponInteraction.class);
        mMapper = new CouponChannelMapper();
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        mInteraction.getCouponAddChannel(tag, getViewModel().getCouponId(), CouponChannelBean.class, new DefaultCallback<List<CouponChannelBean>>(this) {
            @Override
            public void onBegin(Object tag) {

            }

            @Override
            public void onSuccess(List<CouponChannelBean> bean) {
                super.onSuccess(bean);
                if (null != bean && bean.size() > 0) {
                    mMapper.mapperList(getViewModel(), bean, getViewModel().getPosition(), false);
                }
                refreshUI();
            }
        });
    }

    //增发优惠券
    public void addCoupon(Object tag, final OnActionListener listener) {
        //{"couponId":优惠券ID,"couponDispatchType":2（写死2）,"additionalChannels":[{"channelId":1(渠道 id),"aditionalAmount":12（增发数量）}]}
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("couponId", getViewModel().getCouponId());
        jsonObject.addProperty("couponDispatchType", 2);
        JsonArray channels = new JsonArray();
        for (int i = 0; i < getViewModel().size(); i++) {
            if (TextUtils.isEmpty(getViewModel().get(i).getCount().get())) continue;
            JsonObject item = new JsonObject();
            item.addProperty("channelId", getViewModel().get(i).getChannelId());
            item.addProperty("aditionalAmount", getViewModel().get(i).getCount().get());
            channels.add(item);
        }
        jsonObject.add("additionalChannels", channels);
        mInteraction.couponAdd(tag, jsonObject, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                hideLoading();
                if (listener != null){
                    listener.onSuccess();
                }
            }
        });
    }
}
