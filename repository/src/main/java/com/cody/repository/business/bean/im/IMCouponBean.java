package com.cody.repository.business.bean.im;


public class IMCouponBean {

    public int type;                    //自定义消息类型
    public int couponID;                //劵ID
    public String couponCode;              //使用码
    public int coupontype;              //券类型 6种优惠劵
    public String startDate;            //开始时间 GMT
    public String endDate;              //结束时间 GMT
    public String couponName;           //券名称
    public String couponSubName;        //子券名称
    public String couponBound;          //满足什么条件可以用
    public String couponScope;          // 优惠券使用范围，店铺名称，红星平台，xx商场
    public int couponScopeType;         // 优惠券渠道类型，店铺，红星平台，商场

    public String getCouponBound() {
        return couponBound;
    }

    public void setCouponBound(String couponBound) {
        this.couponBound = couponBound;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getCouponID() {
        return couponID;
    }

    public void setCouponID(int couponID) {
        this.couponID = couponID;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponSubName() {
        return couponSubName;
    }

    public void setCouponSubName(String couponSubName) {
        this.couponSubName = couponSubName;
    }

    public int getCoupontype() {
        return coupontype;
    }

    public void setCoupontype(int coupontype) {
        this.coupontype = coupontype;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCouponScope() {
        return couponScope;
    }

    public void setCouponScope(String couponScope) {
        this.couponScope = couponScope;
    }

    public int getCouponScopeType() {
        return couponScopeType;
    }

    public void setCouponScopeType(int couponScopeType) {
        this.couponScopeType = couponScopeType;
    }
}
