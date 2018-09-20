/*
 * @Title ConstDataSource.java
 * @Copyright Copyright 2016 Cody All Rights Reserved.
 * @author Cody
 * @date 2016-4-11 下午3:07:54
 * @version 1.0
 */
package com.cody.repository.framework.statistics;

import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;

/**
 * @author Cody
 * @Description TODO
 * @date 2016-4-11 下午3:07:54
 */
public class ConstDataSource extends StatConstDataSource {
    @Override
    public String deviceId() {
        return UserDataManager.getDeviceUtdid();
    }

    @Override
    public String userId() {
        return Repository.getLocalValue(LocalKey.OPEN_ID);
    }

    @Override
    public String country() {
        return UserDataManager.getCountry();
    }

    @Override
    public String province() {
        return UserDataManager.getProvince();
    }


    @Override
    public String city() {
        return UserDataManager.getCity();
    }

    @Override
    public String userCity() {
        return "";
    }

    @Override
    public String district() {
        return UserDataManager.getDistrict();
    }

    @Override
    public String domain() {
        return "cody.com";
    }

    @Override
    public String channel() {
        return UserDataManager.getChannel();
    }

    @Override
    public String gps() {
        return UserDataManager.getGps();
    }
}
