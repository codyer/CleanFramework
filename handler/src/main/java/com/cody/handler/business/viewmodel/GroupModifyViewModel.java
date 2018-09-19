package com.cody.handler.business.viewmodel;

import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * Created by Cody.yi ;
 * on data:  2018/7/11 ;
 * 修改分组
 */
public class GroupModifyViewModel extends WithHeaderViewModel {
    public final static int ADD_TO_GROUP = 1;
    public final static int EDIT_TO_GROUP = 2;

    private int mType;//1:add,2:edit
    private String mImId;//用户 im id
    private String mOldGroupId;// 原来分组id
    private String mOldGroupName;//原来分组name
    private String mNewGroupId;// 新分组 id
    private ObservableField<String> mNewGroupName = new ObservableField<>();//新分组name

    public String getOldGroupName() {
        return mOldGroupName;
    }

    public void setOldGroupName(String oldGroupName) {
        mOldGroupName = oldGroupName;
    }

    public ObservableField<String> getNewGroupName() {
        return mNewGroupName;
    }

    public void setNewGroupName(String newGroupName) {
        mNewGroupName.set(newGroupName);
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getImId() {
        return mImId;
    }

    public void setImId(String imId) {
        mImId = imId;
    }

    public String getOldGroupId() {
        return mOldGroupId;
    }

    public void setOldGroupId(String oldGroupId) {
        mOldGroupId = oldGroupId;
    }

    public String getNewGroupId() {
        return mNewGroupId;
    }

    public void setNewGroupId(String newGroupId) {
        mNewGroupId = newGroupId;
    }
}
