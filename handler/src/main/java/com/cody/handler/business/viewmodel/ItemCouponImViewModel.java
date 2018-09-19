/*
 * Copyright (c)  Created by Cody.yi on 2016/8/30.
 */

package com.cody.handler.business.viewmodel;


import android.text.TextUtils;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by chy on 2016/8/29.
 * IM中优惠券列表的Item
 */
public class ItemCouponImViewModel extends XItemViewModel {
    private String name;
    private int mCouponId;
    private boolean selected;
    private String mCouponNum;
    private String startT;//开始时间
    private String endT;//结束时间
    private String promotionType;
    private int cupontypeId;//51 无门槛现金抵用券 , 52 满减券 , 53 每满减券 ,54 阶梯满减券 , 55 折扣券, 56 赠品券
    private int ownerType;//优惠券渠道类型，1 平台，2商场，3店铺
    private String ownerName;//渠道名称
    private String couponSubName;        //子券名称
    private String couponBound;          //满足什么条件可以用

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public int getCouponId() {
        return mCouponId;
    }

    public void setCouponId(int couponId) {
        mCouponId = couponId;
    }

    public String getCouponNum() {
        return mCouponNum;
    }

    public void setCouponNum(String couponNum) {
        mCouponNum = couponNum;
    }

    public String getStartT() {
        return startT;
    }

    public void setStartT(String startT) {
        this.startT = startT;
    }

    public String getEndT() {
        return endT;
    }

    public void setEndT(String endT) {
        this.endT = endT;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public int getCupontypeId() {
        return cupontypeId;
    }

    public void setCupontypeId(int cupontypeId) {
        this.cupontypeId = cupontypeId;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCouponSubName() {
        if (TextUtils.isEmpty(couponSubName))
            return name;
        return couponSubName;
    }

    public void setCouponSubName(String couponSubName) {
        this.couponSubName = couponSubName;
    }

    public String getCouponBound() {
        return couponBound;
    }

    public void setCouponBound(String couponBound) {
        this.couponBound = couponBound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemCouponImViewModel that = (ItemCouponImViewModel) o;

        if (mCouponId != that.mCouponId) return false;
        if (selected != that.selected) return false;
        if (cupontypeId != that.cupontypeId) return false;
        if (ownerType != that.ownerType) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (mCouponNum != null ? !mCouponNum.equals(that.mCouponNum) : that.mCouponNum != null)
            return false;
        if (startT != null ? !startT.equals(that.startT) : that.startT != null) return false;
        if (endT != null ? !endT.equals(that.endT) : that.endT != null) return false;
        if (promotionType != null ? !promotionType.equals(that.promotionType) : that.promotionType != null)
            return false;
        if (ownerName != null ? !ownerName.equals(that.ownerName) : that.ownerName != null)
            return false;
        if (couponSubName != null ? !couponSubName.equals(that.couponSubName) : that.couponSubName != null)
            return false;
        return couponBound != null ? couponBound.equals(that.couponBound) : that.couponBound == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + mCouponId;
        result = 31 * result + (selected ? 1 : 0);
        result = 31 * result + (mCouponNum != null ? mCouponNum.hashCode() : 0);
        result = 31 * result + (startT != null ? startT.hashCode() : 0);
        result = 31 * result + (endT != null ? endT.hashCode() : 0);
        result = 31 * result + (promotionType != null ? promotionType.hashCode() : 0);
        result = 31 * result + cupontypeId;
        result = 31 * result + ownerType;
        result = 31 * result + (ownerName != null ? ownerName.hashCode() : 0);
        result = 31 * result + (couponSubName != null ? couponSubName.hashCode() : 0);
        result = 31 * result + (couponBound != null ? couponBound.hashCode() : 0);
        return result;
    }
}
