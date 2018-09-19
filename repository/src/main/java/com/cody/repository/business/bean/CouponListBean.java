package com.cody.repository.business.bean;

import java.util.List;

/**
 * Create by jiquan.zhong  on 2018/8/15.
 * description:优惠券列表
 */
public class CouponListBean {

    /**
     * pageNo : 1
     * pageSize : 190
     * totalPages : 2
     * totalRecords : 283
     * records : [{"id":7683,"couponName":"订单促销返的券","title":"100.00元","condition":"订单满0.00元可用","issuedTotalAmount":100,"remainingCount":87,"actualConsumed":4,"shopAppCouponStatus":3,"addCouponAmount":false,"effectTimeType":0,"timeUnit":0,"timeValue":0,"startTime":1534731000000,"endTime":1535299199000,"ownerType":2,"ownerId":"1009","ownerName":"上海真北商场","couponDispatchType":2,"couponPackageId":0,"receivedNumber":13,"couponTypeId":51,"status":3,"displayName":"上海真北商场券","conditionsOfUse":"无门槛使用","mallAbbreviation":null}]
     */

    private int pageNo;
    private int pageSize;
    private int totalPages;
    private int totalRecords;
    private List<RecordsBean> records;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * id : 7683
         * couponName : 订单促销返的券
         * title : 100.00元
         * condition : 订单满0.00元可用
         * issuedTotalAmount : 100
         * remainingCount : 87
         * actualConsumed : 4
         * shopAppCouponStatus : 3
         * addCouponAmount : false
         * effectTimeType : 0
         * timeUnit : 0
         * timeValue : 0
         * startTime : 1534731000000
         * endTime : 1535299199000
         * ownerType : 2
         * ownerId : 1009
         * ownerName : 上海真北商场
         * couponDispatchType : 2
         * couponPackageId : 0
         * receivedNumber : 13
         * couponTypeId : 51
         * status : 3
         * displayName : 上海真北商场券
         * conditionsOfUse : 无门槛使用
         * mallAbbreviation : null
         */

        private int id;
        private String couponName;
        private String title;
        private String condition;
        private int effectTimeType;//生效时间类型 0.绝对时间 1.相对时间（龙果APP使用）,
        private int timeUnit;//时间单位 1.小时 2.天
        private String timeValue;//时间值
        private int issuedTotalAmount;
        private int remainingCount;
        private int actualConsumed;
        private int shopAppCouponStatus;
        private boolean addCouponAmount;
        private long startTime;
        private long endTime;
        private int ownerType;
        private String ownerId;
        private String ownerName;
        private int couponDispatchType;
        private int couponPackageId;
        private int receivedNumber;
        private int couponTypeId;
        private int status;
        private String displayName;
        private String conditionsOfUse;
        private Object mallAbbreviation;

        public int getEffectTimeType() {
            return effectTimeType;
        }

        public void setEffectTimeType(int effectTimeType) {
            this.effectTimeType = effectTimeType;
        }

        public int getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(int timeUnit) {
            this.timeUnit = timeUnit;
        }

        public String getTimeValue() {
            return timeValue;
        }

        public void setTimeValue(String timeValue) {
            this.timeValue = timeValue;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public int getIssuedTotalAmount() {
            return issuedTotalAmount;
        }

        public void setIssuedTotalAmount(int issuedTotalAmount) {
            this.issuedTotalAmount = issuedTotalAmount;
        }

        public int getRemainingCount() {
            return remainingCount;
        }

        public void setRemainingCount(int remainingCount) {
            this.remainingCount = remainingCount;
        }

        public int getActualConsumed() {
            return actualConsumed;
        }

        public void setActualConsumed(int actualConsumed) {
            this.actualConsumed = actualConsumed;
        }

        public int getShopAppCouponStatus() {
            return shopAppCouponStatus;
        }

        public void setShopAppCouponStatus(int shopAppCouponStatus) {
            this.shopAppCouponStatus = shopAppCouponStatus;
        }

        public boolean isAddCouponAmount() {
            return addCouponAmount;
        }

        public void setAddCouponAmount(boolean addCouponAmount) {
            this.addCouponAmount = addCouponAmount;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(int ownerType) {
            this.ownerType = ownerType;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public int getCouponDispatchType() {
            return couponDispatchType;
        }

        public void setCouponDispatchType(int couponDispatchType) {
            this.couponDispatchType = couponDispatchType;
        }

        public int getCouponPackageId() {
            return couponPackageId;
        }

        public void setCouponPackageId(int couponPackageId) {
            this.couponPackageId = couponPackageId;
        }

        public int getReceivedNumber() {
            return receivedNumber;
        }

        public void setReceivedNumber(int receivedNumber) {
            this.receivedNumber = receivedNumber;
        }

        public int getCouponTypeId() {
            return couponTypeId;
        }

        public void setCouponTypeId(int couponTypeId) {
            this.couponTypeId = couponTypeId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getConditionsOfUse() {
            return conditionsOfUse;
        }

        public void setConditionsOfUse(String conditionsOfUse) {
            this.conditionsOfUse = conditionsOfUse;
        }

        public Object getMallAbbreviation() {
            return mallAbbreviation;
        }

        public void setMallAbbreviation(Object mallAbbreviation) {
            this.mallAbbreviation = mallAbbreviation;
        }
    }
}
