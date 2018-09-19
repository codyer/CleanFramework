/*
 * Copyright (c)  Created by Cody.yi on 2016/9/10.
 */

package com.cody.repository.business.bean.im;

/**
 * Created by wujun on 16/9/5.
 * 切换导购bean
 */
public class IMSwitchGuideBean {
    public String targetIMID;         //被唤起的导购员的IMID
    public String targetName;         //被唤起的导购员的姓名
    public String SGRemark;           //导购员的备注，C端不可见
    //    public IMGoodsBean IMGoodsBean;       //商品信息 非必须
//    public T JSONContent;       //商品信息 非必须
    public String merchandise;  //商品信息 非必须

    public int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSGRemark() {
        return SGRemark;
    }

    public void setSGRemark(String SGRemark) {
        this.SGRemark = SGRemark;
    }

    public String getTargetIMID() {
        return targetIMID;
    }

    public void setTargetIMID(String targetIMID) {
        this.targetIMID = targetIMID;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

//    public IMGoodsBean getGoodsBean() {
//        return IMGoodsBean;
//    }
//
//    public void setGoodsBean(IMGoodsBean IMGoodsBean) {
//        this.IMGoodsBean = IMGoodsBean;
//    }

//    public T getJSONContent() {
//        return JSONContent;
//    }
//
//    public void setJSONContent(T JSONContent) {
//        this.JSONContent = JSONContent;
//    }

    public String getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(String merchandise) {
        this.merchandise = merchandise;
    }
}


