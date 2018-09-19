package com.cody.repository.business.bean;

import com.cody.xf.common.NotProguard;

/**
 * Created by chy on 2016/10/10.
 * 领取成功后返回券码
 */
@NotProguard
public class CouponNumBean {
    private String cuponCode;//领取成功后返回券码

    public String getCuponCode() {
        return cuponCode;
    }

    public void setCuponCode(String cuponCode) {
        this.cuponCode = cuponCode;
    }
}
