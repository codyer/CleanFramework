package com.cody.repository.business.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dong.wang
 * Date: 2018/8/23
 * Time: 15:31
 * Description: 发券参数
 */
public class SendCouponBean implements Parcelable {
    /**
     * shopId :
     * marketNumber :
     * clerkOpenId :
     * couponIds : [0]
     * userList : [{"userOpenId":"","couponId":0,"channelId":"","subChannelId":"","channelName":"","clerkOpenId":"","mobile":"","userVipLevel":0,"sourceOrderNo":""}]
     * queryField :
     * type :
     */

    private String queryField;
    private String type;
    private List<Integer> couponIds;
    private List<UserListBean> userList;
    private int selectedUserCount;//选中人数

    public String getQueryField() {
        return queryField;
    }

    public void setQueryField(String queryField) {
        this.queryField = queryField;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List<Integer> couponIds) {
        this.couponIds = couponIds;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public int getSelectedUserCount() {
        return selectedUserCount;
    }

    public void setSelectedUserCount(int selectedUserCount) {
        this.selectedUserCount = selectedUserCount;
    }

    public static class UserListBean implements Parcelable {
        /**
         * userOpenId :
         * couponId : 0
         * channelId :
         * subChannelId :
         * channelName :
         * clerkOpenId :
         * mobile :
         * userVipLevel : 0
         * sourceOrderNo :
         */

        private String userOpenId;

        public String getUserOpenId() {
            return userOpenId;
        }

        public void setUserOpenId(String userOpenId) {
            this.userOpenId = userOpenId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.userOpenId);
        }

        public UserListBean() {
        }

        protected UserListBean(Parcel in) {
            this.userOpenId = in.readString();
        }

        public static final Creator<UserListBean> CREATOR = new Creator<UserListBean>() {
            @Override
            public UserListBean createFromParcel(Parcel source) {
                return new UserListBean(source);
            }

            @Override
            public UserListBean[] newArray(int size) {
                return new UserListBean[size];
            }
        };
    }

    public SendCouponBean() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.queryField);
        dest.writeString(this.type);
        dest.writeList(this.couponIds);
        dest.writeTypedList(this.userList);
        dest.writeInt(this.selectedUserCount);
    }

    protected SendCouponBean(Parcel in) {
        this.queryField = in.readString();
        this.type = in.readString();
        this.couponIds = new ArrayList<Integer>();
        in.readList(this.couponIds, Integer.class.getClassLoader());
        this.userList = in.createTypedArrayList(UserListBean.CREATOR);
        this.selectedUserCount = in.readInt();
    }

    public static final Creator<SendCouponBean> CREATOR = new Creator<SendCouponBean>() {
        @Override
        public SendCouponBean createFromParcel(Parcel source) {
            return new SendCouponBean(source);
        }

        @Override
        public SendCouponBean[] newArray(int size) {
            return new SendCouponBean[size];
        }
    };
}
