/*
 * Copyright (c)  Created by Cody.yi on 2016/9/2.
 */

package com.cody.repository.business.bean;

/**
 * Created by lenovo on 2016/9/1.
 */
public class SaleBean {

    /**
     * openId : 0f74ba6a-7dd1-423c-b115-6067c8ebcbf8
     * userName : 张
     * avatar : url1
     * imId : 2_0f74ba6a-7dd1-423c-b115-6067c8ebcbf8
     * userStatus : 1
     */

    private String openId;
    private String userName;
    private String avatar;
    private String imId;
    private int userStatus;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

}
