package com.cody.repository.business.bean.entity;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by  dong.wang
 * Date: 2017/3/6
 * Time: 10:58
 * Description: C端用户信息
 */
public class UserInfoBean extends RealmObject {

    /**
     * openId : 1a4eb00c-9970-49ec-aacd-65f468972b07
     * imId : 2_1a4eb00c-9970-49ec-aacd-65f468972b07
     * name : 钱磊
     * userName : hm_3jG12xFw
     * nickName : 钱磊
     * avatar : http://img1.uat1.rs.com/g1/M00/00/15/wKh8y1i1Du-AYqWBAAAXdz60F0Q02.JPEG
     * mobile : 18918701158
     * gender : 未设定
     * remark : 备注
     */
    @Ignore
    private boolean isGroup;//是否为分组
    @Ignore
    private boolean expend;//是否展开
    private String groupId;//分组id
    private String groupName;//分组名
    @Ignore
    private int groupType;//是否是自定义分组
    private String openId;
    @PrimaryKey
    private String imId;
    private String name;
    private String userName;
    private String nickName;
    private String avatar;
    private String mobile;
    private String gender;
    private String remark;
    private String provinceCode;
    private String province;
    private String cityCode;
    private String city;
    private String distributeCode;
    private String distribute;
    private String street;
    private String address;
    private String zipCode;
    private String houseCode;
    private String house;
    private String communityName;
    private String communityTele;

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistributeCode() {
        return distributeCode;
    }

    public void setDistributeCode(String distributeCode) {
        this.distributeCode = distributeCode;
    }

    public String getDistribute() {
        return distribute;
    }

    public void setDistribute(String distribute) {
        this.distribute = distribute;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityTele() {
        return communityTele;
    }

    public void setCommunityTele(String communityTele) {
        this.communityTele = communityTele;
    }

    public boolean isExpend() {
        return expend;
    }

    public void setExpend(boolean expend) {
        this.expend = expend;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        this.isGroup = group;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }
}
