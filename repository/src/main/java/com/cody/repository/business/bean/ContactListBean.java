package com.cody.repository.business.bean;

import com.cody.repository.business.bean.entity.UserInfoBean;

import java.util.List;

public class ContactListBean {
    private String groupId;//组ID
    private String groupName;//组名称
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private List<UserInfoBean> customerList;

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

    public List<UserInfoBean> getCustomerList() {
        if (customerList == null) return null;
        for (UserInfoBean bean : customerList) {
            bean.setGroupId(groupId);
            bean.setGroupName(groupName);
        }
        return customerList;
    }

    public void setCustomerList(List<UserInfoBean> customerList) {
        this.customerList = customerList;
    }
}
