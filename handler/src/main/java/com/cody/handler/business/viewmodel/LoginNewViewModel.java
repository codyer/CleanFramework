package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.ViewModel;
import com.cody.repository.business.bean.UserBean;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;

import java.util.List;

/**
 * Created by Emcy-fu ;
 * on data:  2018/7/11 ;
 */

public class LoginNewViewModel extends ViewModel {
    private final ObservableBoolean showBlue = new ObservableBoolean();
    //带空格
    private final ObservableField<String> textPhone = new ObservableField<>(Repository.getLocalValue(LocalKey.CACHE_NAME));
    //处理空格后
    private final ObservableField<String> userPhone = new ObservableField<>("");
    private final ObservableField<String> userPwd = new ObservableField<>("");

    public ObservableField<String> getUserPhone() {
        return userPhone;
    }

    public ObservableBoolean getShowBlue() {
        return showBlue;
    }

    public ObservableField<String> getUserPwd() {
        return userPwd;
    }

    public ObservableField<String> getTextPhone() {
        return textPhone;
    }

    private List<UserBean.GroupListBean> groupListBeen;

    public void setGroupListBeen(List<UserBean.GroupListBean> groupListBeen) {
        this.groupListBeen = groupListBeen;
    }

    public List<UserBean.GroupListBean> getGroupListBeen() {
        return groupListBeen;
    }
}
