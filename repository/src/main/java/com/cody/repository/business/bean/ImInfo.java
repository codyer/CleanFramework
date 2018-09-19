package com.cody.repository.business.bean;

/**
 * Created by dong.wang
 * Date: 2018/5/10
 * Time: 19:05
 * Description:
 */
public class ImInfo {

    /**
     * openId : db28e96c-5ea5-11e6-93dc-448500db06d6
     * imId : 1_db28e96c-5ea5-11e6-93dc-448500db06d6
     * userStatus : 1
     * name : 陈丽娟
     * userName : hm_f28049b0
     * nickName : 小丽
     * avatar : url
     */

    private String openId;
    private String imId;
    private int userStatus;
    private String name;
    private String userName;
    private String nickName;
    private int shopId;//店铺id
    private String avatar;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
