package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.text.TextUtils;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Create by jiquan.zhong  on 2018/8/14.
 * description:活动页列表 item
 */
public class ItemPromotionViewModel extends XItemViewModel {
    private int mActivityId;//活动id
    private String mActivityStatus;// 状态 1.草稿 2.未开始 3.进行中 4.已结束 21.审核中 22.审核驳回,
    private String mActivityName;//名称
    private String mTotalTimes;//累计展示时间
    private String mTotalVisitCount;//访问次数
    private String mTotalVisitCustomers;//访问人数
    private String mPageTitle;//页面标题
    private String url;//跳转H5
    private final ObservableBoolean mOnline = new ObservableBoolean(false);//上线状态
    private String shareTitle;
    private String shareDesc;
    private String shareImageUrl;

    public String getPageTitle() {
        return mPageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.mPageTitle = pageTitle;
    }

    public ObservableBoolean getChecked() {
        return mOnline;
    }

    public int getActivityId() {
        return mActivityId;
    }

    public void setActivityId(int activityId) {
        mActivityId = activityId;
    }

    public String getActivityStatus() {
        return mActivityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        mActivityStatus = activityStatus;
    }

    public String getActivityName() {
        return mActivityName;
    }

    public void setActivityName(String activityName) {
        mActivityName = activityName;
    }

    public String getTotalTimes() {
        return "累计展示：" + mTotalTimes;
    }

    public void setTotalTimes(String totalTimes) {
        mTotalTimes = totalTimes;
    }

    public String getTotalVisitCount() {
        return "累计访问次数：" + (TextUtils.isEmpty(mTotalVisitCount) ? 0 : mTotalVisitCount);
    }

    public void setTotalVisitCount(String totalVisitCount) {
        mTotalVisitCount = totalVisitCount;
    }

    public String getTotalVisitCustomers() {
        return "访客人数：" + (TextUtils.isEmpty(mTotalVisitCustomers) ? 0 : mTotalVisitCustomers);
    }

    public void setTotalVisitCustomers(String totalVisitCustomers) {
        mTotalVisitCustomers = totalVisitCustomers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getShareImageUrl() {
        return shareImageUrl;
    }

    public void setShareImageUrl(String shareImageUrl) {
        this.shareImageUrl = shareImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemPromotionViewModel that = (ItemPromotionViewModel) o;

        if (mActivityId != that.mActivityId) return false;
        if (mActivityStatus != null ? !mActivityStatus.equals(that.mActivityStatus) : that.mActivityStatus != null)
            return false;
        if (mPageTitle != null ? !mPageTitle.equals(that.mPageTitle) : that.mPageTitle != null)
            return false;
        if (mActivityName != null ? !mActivityName.equals(that.mActivityName) : that.mActivityName != null)
            return false;
        if (mTotalTimes != null ? !mTotalTimes.equals(that.mTotalTimes) : that.mTotalTimes != null)
            return false;
        if (mTotalVisitCount != null ? !mTotalVisitCount.equals(that.mTotalVisitCount) : that.mTotalVisitCount != null)
            return false;
        if (mTotalVisitCustomers != null ? !mTotalVisitCustomers.equals(that.mTotalVisitCustomers) : that.mTotalVisitCustomers != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (mOnline.get() !=that.mOnline.get()) return false;
        if (shareTitle != null ? !shareTitle.equals(that.shareTitle) : that.shareTitle != null)
            return false;
        if (shareDesc != null ? !shareDesc.equals(that.shareDesc) : that.shareDesc != null)
            return false;
        return shareImageUrl != null ? shareImageUrl.equals(that.shareImageUrl) : that.shareImageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mActivityId;
        result = 31 * result + (mActivityStatus != null ? mActivityStatus.hashCode() : 0);
        result = 31 * result + (mPageTitle != null ? mPageTitle.hashCode() : 0);
        result = 31 * result + (mActivityName != null ? mActivityName.hashCode() : 0);
        result = 31 * result + (mTotalTimes != null ? mTotalTimes.hashCode() : 0);
        result = 31 * result + (mTotalVisitCount != null ? mTotalVisitCount.hashCode() : 0);
        result = 31 * result + (mTotalVisitCustomers != null ? mTotalVisitCustomers.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (mOnline.get() ? mOnline.hashCode() : 0);
        result = 31 * result + (shareTitle != null ? shareTitle.hashCode() : 0);
        result = 31 * result + (shareDesc != null ? shareDesc.hashCode() : 0);
        result = 31 * result + (shareImageUrl != null ? shareImageUrl.hashCode() : 0);
        return result;
    }
}
