/*
 * Copyright (c)  Created by Cody.yi on 2016/9/2.
 */

package com.cody.repository.business.bean;


import java.util.List;

/**
 * Created by lenovo on 2016/9/2.
 * B 端用户信息
 */
public class UserBean {

    /**
     * groupList : [{"groupCode":"R00001","settle":false}]
     * username : 18856151955
     * name :
     * gender : 0
     * openid : 218dc5fa-69fb-430d-be86-9116fcebbi3e
     * avatar :
     * mobile : 18856151955
     */
    private String username;
    private String name;
    private int gender;
    private String openid;
    private String avatar;
    private String mobile;
    private String identity;

    /**
     * groupCode : R00001
     * settle : false
     */
    private List<GroupListBean> groupList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public List<GroupListBean> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupListBean> groupList) {
        this.groupList = groupList;
    }

    public static class GroupListBean {
        private String groupCode;
        private boolean settle;

        public String getGroupCode() {
            return groupCode;
        }

        public void setGroupCode(String groupCode) {
            this.groupCode = groupCode;
        }

        public boolean isSettle() {
            return settle;
        }

        public void setSettle(boolean settle) {
            this.settle = settle;
        }

        @Override
        public String toString() {
            return "GroupListBean{" +
                    "groupCode='" + groupCode + '\'' +
                    ", settle=" + settle +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", openid='" + openid + '\'' +
                ", avatar='" + avatar + '\'' +
                ", mobile='" + mobile + '\'' +
                ", groupList=" + groupList +
                '}';
    }
}