package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Created by cody.yi on 2018/8/21.
 * 微信小程序二维码
 */
public class MiniAppViewModel extends WithHeaderViewModel{
    private String mShopPic;//店铺图片
    private String mShopName;//店铺名
    private String mShopAddress;//店铺地址
    private String mMiniAppUrl;//二维码图片
    private String mWapUrl;//网页地址
    private String mGid;//微信id
    private String mMiniPage;//小程序地址
    private String mShareEvn;//小程序环境

    public String getShareEvn() {
        return mShareEvn;
    }

    public void setShareEvn(String shareEvn) {
        mShareEvn = shareEvn;
    }

    public String getShopPic() {
        return mShopPic;
    }

    public void setShopPic(String shopPic) {
        mShopPic = shopPic;
    }

    public String getShopName() {
        return mShopName;
    }

    public String getShopAddress() {
        return mShopAddress;
    }

    public void setShopAddress(String shopAddress) {
        mShopAddress = shopAddress;
    }

    public void setShopName(String shopName) {
        mShopName = shopName;
    }

    public String getMiniAppUrl() {
        return mMiniAppUrl;
    }

    public void setMiniAppUrl(String miniAppUrl) {
        mMiniAppUrl = miniAppUrl;
    }

    public String getWapUrl() {
        return mWapUrl;
    }

    public void setWapUrl(String wapUrl) {
        mWapUrl = wapUrl;
    }

    public String getGid() {
        return mGid;
    }

    public void setGid(String gid) {
        mGid = gid;
    }

    public String getMiniPage() {
        return mMiniPage;
    }

    public void setMiniPage(String miniPage) {
        mMiniPage = miniPage;
    }
}
