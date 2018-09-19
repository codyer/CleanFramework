package com.cody.repository.business.bean;

/**
 * Created by cody.yi on 2018/8/17.
 * 用户备注
 */
public class RemarkBean {

    /**
     * friendId : 1_9964ea53-670a-461c-84af-e1ced7bf343c
     * remarkInfo : 顾客成交意向大
     */

    private String friendId;
    private String remarkInfo;

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }
}
