package com.cody.repository.business.bean.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2018/7/22.
 * 用户分组
 */
public class UserGroupBean extends RealmObject {
    @PrimaryKey
    private String groupId;//组ID
    private String groupName;//组名称
    private int count;
    private RealmList<UserInfoBean> customerList;
    private int groupType;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public RealmList<UserInfoBean> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(RealmList<UserInfoBean> customerList) {
        this.customerList = customerList;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }
}
