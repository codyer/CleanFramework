package com.cody.repository.business.bean;

import java.util.List;

/**
 * Create by jiquan.zhong  on 2018/8/14.
 * description: 活动管理列表bean
 */
public class ActivitiesListBean {

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
        private int id;//主键id,
        private String ownerType;// 1.平台 2.商场 3.店铺,
        private String ownerId;//活动拥有者id,
        private String ownerName;//活动拥有者名称,
        private String mallId;//owner_type=1为0,owner_type=2或者3为商场id,
        private String activityName;//活动名称,
        private String startTime;//开始时间,
        private String endTime;//结束时间,
        private String pageTitle;
        private String pageDesc;
        private String totalShowCount;   //累计访问次数
        private String visitorCount;     //访客人数
        private String pageKey;
        private String shareTitle;//分享标题,
        private String shareContent;//分享内容,
        private String shareImage;//分享图片,
        private String urlLink;//地址
        private String activityStatus;// 状态 1.草稿 2.未开始 3.进行中 4.已结束 21.审核中 22.审核驳回,
        private int onlineStatus;//在线状态  1.已上线 0.已下线
        private String applyAuditName;//申请审核人,
        private String applyAuditTime;//申请审核时间,
        private String createTime;//创建时间,
        private String createUser;
        private String createUserName;
        private String updateTime;
        private String updateUser;
        private String updateUserName;
        private boolean isDeleted;//是否删除：0.未删除，1.已删除,
        private long onLineTotalTime;//上线累计时间（秒）

        public String getTotalShowCount() {
            return totalShowCount;
        }

        public void setTotalShowCount(String totalShowCount) {
            this.totalShowCount = totalShowCount;
        }

        public String getVisitorCount() {
            return visitorCount;
        }

        public void setVisitorCount(String visitorCount) {
            this.visitorCount = visitorCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(String ownerType) {
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

        public String getMallId() {
            return mallId;
        }

        public void setMallId(String mallId) {
            this.mallId = mallId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getPageTitle() {
            return pageTitle;
        }

        public void setPageTitle(String pageTitle) {
            this.pageTitle = pageTitle;
        }

        public String getPageDesc() {
            return pageDesc;
        }

        public void setPageDesc(String pageDesc) {
            this.pageDesc = pageDesc;
        }

        public String getPageKey() {
            return pageKey;
        }

        public void setPageKey(String pageKey) {
            this.pageKey = pageKey;
        }

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public String getShareContent() {
            return shareContent;
        }

        public void setShareContent(String shareContent) {
            this.shareContent = shareContent;
        }

        public String getShareImage() {
            return shareImage;
        }

        public void setShareImage(String shareImage) {
            this.shareImage = shareImage;
        }

        public String getUrlLink() {
            return urlLink;
        }

        public void setUrlLink(String urlLink) {
            this.urlLink = urlLink;
        }

        public String getActivityStatus() {
            return activityStatus;
        }

        public void setActivityStatus(String activityStatus) {
            this.activityStatus = activityStatus;
        }

        public int getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(int onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public String getApplyAuditName() {
            return applyAuditName;
        }

        public void setApplyAuditName(String applyAuditName) {
            this.applyAuditName = applyAuditName;
        }

        public String getApplyAuditTime() {
            return applyAuditTime;
        }

        public void setApplyAuditTime(String applyAuditTime) {
            this.applyAuditTime = applyAuditTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUpdateUserName() {
            return updateUserName;
        }

        public void setUpdateUserName(String updateUserName) {
            this.updateUserName = updateUserName;
        }

        public boolean isIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public long getOnLineTotalTime() {
            return onLineTotalTime;
        }

        public void setOnLineTotalTime(long onLineTotalTime) {
            this.onLineTotalTime = onLineTotalTime;
        }
    }
}
