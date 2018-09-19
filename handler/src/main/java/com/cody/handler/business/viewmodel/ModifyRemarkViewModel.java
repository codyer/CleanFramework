package com.cody.handler.business.viewmodel;

import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Create by jiquan.zhong  on 2018/7/30.
 * description:
 */
public class ModifyRemarkViewModel extends WithHeaderViewModel {
    private ObservableField<String> remark = new ObservableField<>("");//备注名称

    public ObservableField<String> getRemark() {
        return remark;
    }

    public void setRemark(ObservableField<String> remark) {
        this.remark = remark;
    }
}
