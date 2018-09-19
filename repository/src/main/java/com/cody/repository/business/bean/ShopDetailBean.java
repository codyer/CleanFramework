package com.cody.repository.business.bean;

import java.util.List;

/**
 * Created by cody.yi on 2018/8/23.
 * 店铺信息
 */
public class ShopDetailBean {

    /**
     * brandId : 100935
     * shopName : 欧美兰(上海真北商场)
     * shopPic : http://img1.uat1.rs.com/g1/M00/03/87/wKh8y1rYccaAA9-rAADVNUVI-TM520.jpg
     * address : 上海市普陀区真北路1208号/1108 号红星美凯龙北馆F3楼A110
     * shopTel : 021-4412141
     * tags : ["定制家具","哈哈没有标签","风格标签"]
     * marketCode : 10058
     * buildingCode : 10058A
     * floorCode : 10058AF03
     * flagShipShopId : 671106560
     * shopPics : ["http://img1.uat1.rs.com/g1/M00/03/87/wKh8y1rYccaAA9-rAADVNUVI-TM520.jpg","http://img1.uat1.rs.com/g1/M00/03/87/wKh8y1rYccyAHykOAADOeyQ4PgQ172.jpg","http://img1.uat1.rs.com/g1/M00/03/15/wKh8ylrYcdyAWOgaAACwFaZToCA264.jpg"]
     * shopLevel : null
     * brandLogo : http://img1.uat1.rs.com/g1/M00/03/13/wKh8ylrW6qaAOu3xAAA9onZjPPg479.jpg
     * brandTagNames : ["green_recommend"]
     * marketBoothNumber : A110
     * brandTagList : [{"brandId":100935,"tagName":"green_recommend","codeValueCode":"green_recommend","codeValueName":"绿色推荐"}]
     */

    private int brandId;
    private String shopName;
    private String shopPic;
    private String address;
    private String shopTel;
    private String marketCode;
    private String buildingCode;
    private String floorCode;
    private int flagShipShopId;
    private Object shopLevel;
    private String brandLogo;
    private String marketBoothNumber;
    private List<String> tags;
    private List<String> shopPics;
    private List<String> brandTagNames;
    private List<BrandTagListBean> brandTagList;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPic() {
        return shopPic;
    }

    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopTel() {
        return shopTel;
    }

    public void setShopTel(String shopTel) {
        this.shopTel = shopTel;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(String floorCode) {
        this.floorCode = floorCode;
    }

    public int getFlagShipShopId() {
        return flagShipShopId;
    }

    public void setFlagShipShopId(int flagShipShopId) {
        this.flagShipShopId = flagShipShopId;
    }

    public Object getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(Object shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getMarketBoothNumber() {
        return marketBoothNumber;
    }

    public void setMarketBoothNumber(String marketBoothNumber) {
        this.marketBoothNumber = marketBoothNumber;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getShopPics() {
        return shopPics;
    }

    public void setShopPics(List<String> shopPics) {
        this.shopPics = shopPics;
    }

    public List<String> getBrandTagNames() {
        return brandTagNames;
    }

    public void setBrandTagNames(List<String> brandTagNames) {
        this.brandTagNames = brandTagNames;
    }

    public List<BrandTagListBean> getBrandTagList() {
        return brandTagList;
    }

    public void setBrandTagList(List<BrandTagListBean> brandTagList) {
        this.brandTagList = brandTagList;
    }

    public static class BrandTagListBean {
        /**
         * brandId : 100935
         * tagName : green_recommend
         * codeValueCode : green_recommend
         * codeValueName : 绿色推荐
         */

        private int brandId;
        private String tagName;
        private String codeValueCode;
        private String codeValueName;

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public String getCodeValueCode() {
            return codeValueCode;
        }

        public void setCodeValueCode(String codeValueCode) {
            this.codeValueCode = codeValueCode;
        }

        public String getCodeValueName() {
            return codeValueName;
        }

        public void setCodeValueName(String codeValueName) {
            this.codeValueName = codeValueName;
        }
    }
}
