package com.cody.handler.business.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Create by jiquan.zhong  on 2018/8/15.
 * description:优惠券列表item
 */
public class ItemCouponViewModel extends XItemViewModel {
    private int couponId;
    private String couponName;//优惠券名称
    private String couponTitle;//优惠内容
    private String condition;//使用条件
    private int totalAmount;//总发行数量
    private int remainAmount;//剩余数量
    private final ObservableField<String> mTotalCount = new ObservableField<>();
    private final ObservableField<String> mRemainCount = new ObservableField<>();
    private int actualConsumed;//实际消费数量
    private String expiryDate;//有效期
    private int couponStatus;//状态 1.待报名 2.待审核 3.进行中 4.已过期（龙果APP使用）,
    private boolean showAdd;//显示增发
    private int couponTypeId;// 优惠券类型ID 51.无门槛现金抵用券 52.满减券 53.叠加满减券 54.阶梯满减券 55.折扣券 56.赠品券,
    private boolean isExpiry;//是否过期

    /*
        51.无门槛现金抵用券   -> 4
        52.满减券 53.叠加满减券 54.阶梯满减券  -> 3
        55.折扣券   -》 1
        56.赠品券  -》2
    */

    //显示满减条件
    public boolean showCondition() {
        return !TextUtils.isEmpty(condition);
    }

    //显示¥
    public boolean showUnit() {
        return couponTypeId == 51 || couponTypeId == 52 || couponTypeId == 53 || couponTypeId == 54;
    }

    //被使用过
    public boolean isUsed() {
        return actualConsumed > 0;
    }

    //显示折
    public boolean showDiscount() {
        return couponTypeId == 55;
    }

    //显示最高
    public boolean showMost() {
        return couponTypeId == 52 || couponTypeId == 53 || couponTypeId == 54;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getRemainAmount() {
        return remainAmount;
    }

    public ObservableField<String> getTotalCount() {
        return mTotalCount;
    }

    public ObservableField<String> getRemainCount() {
        return mRemainCount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
        this.mTotalCount.set("总发行数量：" + totalAmount);
    }

    public void setRemainAmount(int remainAmount) {
        this.remainAmount = remainAmount;
        this.mRemainCount.set("剩余数量：" + remainAmount);
    }

    public String getActualConsumed() {
        return "本店使用" + actualConsumed + "张";
    }

    public void setActualConsumed(int actualConsumed) {
        this.actualConsumed = actualConsumed;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(int couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getCouponStatusDec() {
        //1.待报名 2.待审核 3.进行中 4.已过期（龙果APP使用） ,
        switch (couponStatus) {
            case 1:
                return "待报名";
            case 2:
                return "待审核";
            case 3:
                return "进行中";
            case 4:
                return "已过期";
        }
        return "";
    }

    public boolean isShowAdd() {
        return showAdd;
    }

    public void setShowAdd(boolean showAdd) {
        this.showAdd = showAdd;
    }

    public int getCouponTypeId() {
        return couponTypeId;
    }

    public void setCouponTypeId(int couponTypeId) {
        this.couponTypeId = couponTypeId;
    }

    //待审核
    public boolean isChecked() {
        return couponStatus == 2;
    }

    public boolean isExpiry() {
        return isExpiry;
    }

    public void setExpiry(boolean expiry) {
        isExpiry = expiry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemCouponViewModel that = (ItemCouponViewModel) o;

        if (couponId != that.couponId) return false;
        if (totalAmount != that.totalAmount) return false;
        if (remainAmount != that.remainAmount) return false;
        if (actualConsumed != that.actualConsumed) return false;
        if (couponStatus != that.couponStatus) return false;
        if (showAdd != that.showAdd) return false;
        if (couponTypeId != that.couponTypeId) return false;
        if (isExpiry != that.isExpiry) return false;
        if (couponName != null ? !couponName.equals(that.couponName) : that.couponName != null)
            return false;
        if (couponTitle != null ? !couponTitle.equals(that.couponTitle) : that.couponTitle != null)
            return false;
        if (condition != null ? !condition.equals(that.condition) : that.condition != null)
            return false;
        return expiryDate != null ? expiryDate.equals(that.expiryDate) : that.expiryDate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + couponId;
        result = 31 * result + (couponName != null ? couponName.hashCode() : 0);
        result = 31 * result + (couponTitle != null ? couponTitle.hashCode() : 0);
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        result = 31 * result + totalAmount;
        result = 31 * result + remainAmount;
        result = 31 * result + actualConsumed;
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        result = 31 * result + couponStatus;
        result = 31 * result + (showAdd ? 1 : 0);
        result = 31 * result + couponTypeId;
        result = 31 * result + (isExpiry ? 1 : 0);
        return result;
    }
}
