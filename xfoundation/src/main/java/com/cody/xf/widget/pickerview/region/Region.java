package com.cody.xf.widget.pickerview.region;

import com.cody.xf.widget.pickerview.model.IPickerViewData;

/**
 * Created by cody.yi on 2018/12/3.
 * 区域base
 */
public class Region implements IPickerViewData {
    private String code;
    private String name;

    public Region(ProvinceBean bean) {
        code = bean.getCode();
        name = bean.getName();
    }

    public Region(ProvinceBean.CityBean bean) {
        code = bean.getCode();
        name = bean.getName();
    }

    public Region(ProvinceBean.CityBean.DistrictBean bean) {
        code = bean.getCode();
        name = bean.getName();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
