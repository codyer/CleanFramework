package com.cody.repository.business.bean;

/**
 * Create by jiquan.zhong  on 2018/8/16.
 * description:优惠券增发渠道bean
 */
public class CouponChannelBean {

    private int couponId;
    private String channelId;//渠道ID,
    private String channelName;//渠道名称,
    private int issuedTotalAmount;//发券总数量
    private long issueStartTime;
    private long issueEndTime;

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getIssuedTotalAmount() {
        return issuedTotalAmount;
    }

    public void setIssuedTotalAmount(int issuedTotalAmount) {
        this.issuedTotalAmount = issuedTotalAmount;
    }

    public long getIssueStartTime() {
        return issueStartTime;
    }

    public void setIssueStartTime(long issueStartTime) {
        this.issueStartTime = issueStartTime;
    }

    public long getIssueEndTime() {
        return issueEndTime;
    }

    public void setIssueEndTime(long issueEndTime) {
        this.issueEndTime = issueEndTime;
    }
}
