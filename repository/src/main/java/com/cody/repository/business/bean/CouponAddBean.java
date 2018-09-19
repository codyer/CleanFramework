package com.cody.repository.business.bean;

import java.util.List;

/**
 * Create by jiquan.zhong  on 2018/8/16.
 * description:
 */
public class CouponAddBean {

    private int couponId;//优惠券id
    private String couponDispatchType;//2.分渠道发券,
    private List<AdditionalChannelsBean> additionalChannels;//增发渠道和数量

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponDispatchType() {
        return couponDispatchType;
    }

    public void setCouponDispatchType(String couponDispatchType) {
        this.couponDispatchType = couponDispatchType;
    }

    public List<AdditionalChannelsBean> getAdditionalChannels() {
        return additionalChannels;
    }

    public void setAdditionalChannels(List<AdditionalChannelsBean> additionalChannels) {
        this.additionalChannels = additionalChannels;
    }

    public static class AdditionalChannelsBean {
        private String channelId;//渠道id
        private int aditionalAmount;//优惠券数量

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public int getAditionalAmount() {
            return aditionalAmount;
        }

        public void setAditionalAmount(int aditionalAmount) {
            this.aditionalAmount = aditionalAmount;
        }
    }
}
