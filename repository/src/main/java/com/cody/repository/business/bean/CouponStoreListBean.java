package com.cody.repository.business.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Create by dong.wang  on 2018/8/15.
 * description: 优惠券发券查询列表
 */
public class CouponStoreListBean {

    /**
     * pageNo : 0
     * pageSize : 0
     * totalPages : 0
     * totalRecords : 0
     * records : [{"id":0,"name":"","effectTimeType":"","timeUnit":"","timeValue":0,"startT":"","startTime":"","endT":"","endTime":"","title":"","conditions":"","couponType":"","couponTypeName":"","status":0,"remainingCount":0,"ownerType":0,"ownerName":"","ownerId":"","couponNo":"","couponUseType":"","price":0,"couponPackageId":0,"perPersonLimit":0,"perOrderLimit":0,"extRelationId":"","extRelationName":"","typeFlag":"","baseType":"","scopeType":"","useScope":"","mallId":"","offerContent":"","displayName":"","conditionsOfUse":"","mallAbbreviation":"","typeName":"","usedPercentage":0,"perPersonRemainingCount":0,"numberOfCoupons":0,"issueStartTime":"","issueEndTime":"","receiveType":""}]
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
         * id : 0
         * name :
         * effectTimeType :
         * timeUnit :
         * timeValue : 0
         * startT :
         * startTime :
         * endT :
         * endTime :
         * title :
         * conditions :
         * couponType :
         * couponTypeName :
         * status : 0
         * remainingCount : 0
         * ownerType : 0
         * ownerName :
         * ownerId :
         * couponNo :
         * couponUseType :
         * price : 0
         * couponPackageId : 0
         * perPersonLimit : 0
         * perOrderLimit : 0
         * extRelationId :
         * extRelationName :
         * typeFlag :
         * baseType :
         * scopeType :
         * useScope :
         * mallId :
         * offerContent :
         * displayName :
         * conditionsOfUse :
         * mallAbbreviation :
         * typeName :
         * usedPercentage : 0
         * perPersonRemainingCount : 0
         * numberOfCoupons : 0
         * issueStartTime :
         * issueEndTime :
         * receiveType :
         */

        private int id;
        private String imageUrl;
        private String name;
        private String effectTimeType;
        private String timeUnit;
        private int timeValue;
        private String startT;
        private String startTime;
        private String endT;
        private String endTime;
        private String title;
        private String conditions;
        private String couponType;
        private String couponTypeName;
        private int status;
        private int remainingCount;
        private int ownerType;
        private String ownerName;
        private String ownerId;
        private String couponNo;
        private String couponUseType;
        private int price;
        private int couponPackageId;
        private int perPersonLimit;
        private int perOrderLimit;
        private String extRelationId;
        private String extRelationName;
        private String typeFlag;
        private String baseType;
        private String scopeType;
        private String useScope;
        private String mallId;
        private String offerContent;
        private String displayName;
        private String conditionsOfUse;
        private String mallAbbreviation;
        private String typeName;
        private BigDecimal usedPercentage;
        private int perPersonRemainingCount;
        private int numberOfCoupons;
        private String issueStartTime;
        private String issueEndTime;
        private String receiveType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEffectTimeType() {
            return effectTimeType;
        }

        public void setEffectTimeType(String effectTimeType) {
            this.effectTimeType = effectTimeType;
        }

        public String getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(String timeUnit) {
            this.timeUnit = timeUnit;
        }

        public int getTimeValue() {
            return timeValue;
        }

        public void setTimeValue(int timeValue) {
            this.timeValue = timeValue;
        }

        public String getStartT() {
            return startT;
        }

        public void setStartT(String startT) {
            this.startT = startT;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndT() {
            return endT;
        }

        public void setEndT(String endT) {
            this.endT = endT;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }

        public String getCouponType() {
            return couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public String getCouponTypeName() {
            return couponTypeName;
        }

        public void setCouponTypeName(String couponTypeName) {
            this.couponTypeName = couponTypeName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getRemainingCount() {
            return remainingCount;
        }

        public void setRemainingCount(int remainingCount) {
            this.remainingCount = remainingCount;
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

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getCouponNo() {
            return couponNo;
        }

        public void setCouponNo(String couponNo) {
            this.couponNo = couponNo;
        }

        public String getCouponUseType() {
            return couponUseType;
        }

        public void setCouponUseType(String couponUseType) {
            this.couponUseType = couponUseType;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCouponPackageId() {
            return couponPackageId;
        }

        public void setCouponPackageId(int couponPackageId) {
            this.couponPackageId = couponPackageId;
        }

        public int getPerPersonLimit() {
            return perPersonLimit;
        }

        public void setPerPersonLimit(int perPersonLimit) {
            this.perPersonLimit = perPersonLimit;
        }

        public int getPerOrderLimit() {
            return perOrderLimit;
        }

        public void setPerOrderLimit(int perOrderLimit) {
            this.perOrderLimit = perOrderLimit;
        }

        public String getExtRelationId() {
            return extRelationId;
        }

        public void setExtRelationId(String extRelationId) {
            this.extRelationId = extRelationId;
        }

        public String getExtRelationName() {
            return extRelationName;
        }

        public void setExtRelationName(String extRelationName) {
            this.extRelationName = extRelationName;
        }

        public String getTypeFlag() {
            return typeFlag;
        }

        public void setTypeFlag(String typeFlag) {
            this.typeFlag = typeFlag;
        }

        public String getBaseType() {
            return baseType;
        }

        public void setBaseType(String baseType) {
            this.baseType = baseType;
        }

        public String getScopeType() {
            return scopeType;
        }

        public void setScopeType(String scopeType) {
            this.scopeType = scopeType;
        }

        public String getUseScope() {
            return useScope;
        }

        public void setUseScope(String useScope) {
            this.useScope = useScope;
        }

        public String getMallId() {
            return mallId;
        }

        public void setMallId(String mallId) {
            this.mallId = mallId;
        }

        public String getOfferContent() {
            return offerContent;
        }

        public void setOfferContent(String offerContent) {
            this.offerContent = offerContent;
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

        public String getMallAbbreviation() {
            return mallAbbreviation;
        }

        public void setMallAbbreviation(String mallAbbreviation) {
            this.mallAbbreviation = mallAbbreviation;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public BigDecimal getUsedPercentage() {
            return usedPercentage;
        }

        public void setUsedPercentage(BigDecimal usedPercentage) {
            this.usedPercentage = usedPercentage;
        }

        public int getPerPersonRemainingCount() {
            return perPersonRemainingCount;
        }

        public void setPerPersonRemainingCount(int perPersonRemainingCount) {
            this.perPersonRemainingCount = perPersonRemainingCount;
        }

        public int getNumberOfCoupons() {
            return numberOfCoupons;
        }

        public void setNumberOfCoupons(int numberOfCoupons) {
            this.numberOfCoupons = numberOfCoupons;
        }

        public String getIssueStartTime() {
            return issueStartTime;
        }

        public void setIssueStartTime(String issueStartTime) {
            this.issueStartTime = issueStartTime;
        }

        public String getIssueEndTime() {
            return issueEndTime;
        }

        public void setIssueEndTime(String issueEndTime) {
            this.issueEndTime = issueEndTime;
        }

        public String getReceiveType() {
            return receiveType;
        }

        public void setReceiveType(String receiveType) {
            this.receiveType = receiveType;
        }
    }
}
