package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;

import com.cody.handler.framework.viewmodel.XItemViewModel;

import java.util.Locale;

/**
 * Created by dong.wang
 * Date: 2018/8/18
 * Time: 15:27
 * Description: 选择优惠券
 */
public class CouponViewModel extends XItemViewModel {
    public final static int ITEM_TYPE_BAG = 111;
    public final static int ITEM_TYPE_NORMAL = 222;
    private ObservableBoolean mIsSelected = new ObservableBoolean(false); // 是否选中
    private boolean isVisibleTag;
    private String imageUrl;
    private int couponId; // 优惠券id
    private String ownerName; // (string, optional): 平台名称、商场名称、店铺名称、工厂名称,
    private String name; // (string, optional): 券名称,
    private String numberOfCoupons; // (integer, optional): 券包的优惠券张数,
    private int usedPercentage; // (number, optional): 领取百分比,
    private String offerContent; //(string, optional): 优惠内容,
    private int remainingCount; // (integer, optional): 剩余数量,
    private String conditions; // (string, optional): 使用条件,
    private String discount; // 折扣
    private boolean isEnough;// 剩余券数是否够发

    public ObservableBoolean getIsSelected() {
        return mIsSelected;
    }

    public void setIsSelected(boolean isSelected) {
        mIsSelected.set(isSelected);
    }

    public boolean isVisibleTag() {
        return isVisibleTag;
    }

    public void setVisibleTag(boolean visibleTag) {
        isVisibleTag = visibleTag;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfCoupons() {
        return numberOfCoupons;
    }

    public void setNumberOfCoupons(int numberOfCoupons) {
        this.numberOfCoupons = String.format(Locale.CHINA, "内含%d张", numberOfCoupons);
    }

    public int getUsedPercentage() {
        return usedPercentage;
    }

    public void setUsedPercentage(int usedPercentage) {
        this.usedPercentage = usedPercentage;
    }

    public String getOfferContent() {
        return offerContent;
    }

    public void setOfferContent(String offerContent) {
        this.offerContent = offerContent;
    }

    public String getRemainingCount() {
        return String.format(Locale.CHINA, "剩余%d", remainingCount);
    }

    public void setRemainingCount(int remainingCount) {
        this.remainingCount = remainingCount;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public boolean isEnough() {
        return isEnough;
    }

    public void setEnough(boolean enough) {
        isEnough = enough;
    }
}
