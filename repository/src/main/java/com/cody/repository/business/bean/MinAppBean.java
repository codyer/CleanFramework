package com.cody.repository.business.bean;

/**
 * Created by cody.yi on 2018/8/23.
 * 微信小程序二维码
 */
public class MinAppBean {

    /**
     * imgStr :
     * wapUrl :
     * scene :
     * page :
     * width : 0
     */

    private String imgStr;
    private String wapUrl;
    private String scene;
    private String page;
    private String gid;
    private String shareParam;
    private String shareEnv;//0:正式 1开发版 2 体验版
    private int width;

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public String getWapUrl() {
        return wapUrl;
    }

    public String getShareParam() {
        return shareParam;
    }

    public void setShareParam(String shareParam) {
        this.shareParam = shareParam;
    }

    public String getShareEnv() {
        return shareEnv;
    }

    public void setShareEnv(String shareEnv) {
        this.shareEnv = shareEnv;
    }

    public void setWapUrl(String wapUrl) {
        this.wapUrl = wapUrl;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
