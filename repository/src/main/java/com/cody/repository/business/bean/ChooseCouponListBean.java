package com.cody.repository.business.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by chy on 2016/10/10.
 * 选择优惠券列表bean类
 */
public class ChooseCouponListBean {

    /**
     * total : 1
     * list : [{"id":597,"startT":"2016-10-29 18:50","endT":"2016-10-31 18:50","title":"10元","conditions":"订单笔每满1000元可用","couponType":53,"couponTypeName":"每满减券","status":1,"remainingCount":99,"ownerType":3,"ownerName":"美加华(新区店)"}]
     */

    private int total;
    /**
     * id : 597
     * startT : 2016-10-29 18:50
     * endT : 2016-10-31 18:50
     * title : 10元
     * conditions : 订单笔每满1000元可用
     * couponType : 53
     * couponTypeName : 每满减券
     * status : 1
     * remainingCount : 99
     * ownerType : 3
     * ownerName : 美加华(新区店)
     */

    private List<ListBean> list;

    public static ChooseCouponListBean objectFromData(String str) {

        return new Gson().fromJson(str, ChooseCouponListBean.class);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int id;
        private String startT;
        private String endT;
        private String title;
        private String conditions;
        private int couponType;
        private String couponTypeName;
        private int status;
        private int remainingCount;
        private int ownerType;
        private String ownerName;

        public static ListBean objectFromData(String str) {

            return new Gson().fromJson(str, ListBean.class);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getCouponType() {
            return couponType;
        }

        public void setCouponType(int couponType) {
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
    }
}
