package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.LoginNewViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.UserBean;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;

/**
 * Created by Emcy-fu ;
 * on data:  2018/7/13 ;
 */

public class LoginNewMapper extends ModelMapper<LoginNewViewModel, UserBean> {
    @Override
    public LoginNewViewModel mapper(UserBean dataModel, int position) {
        return mapper(new LoginNewViewModel(), dataModel);
    }

    @Override
    public LoginNewViewModel mapper(LoginNewViewModel viewModel, UserBean dataModel) {
        if (null == viewModel || null == dataModel) return viewModel;

        viewModel.setGroupListBeen(dataModel.getGroupList());
        Repository.setLocalValue(LocalKey.OPEN_ID, dataModel.getOpenid());
        Repository.setLocalValue(LocalKey.CACHE_NAME, viewModel.getUserPhone().get());
        Repository.setLocalValue(LocalKey.MOBILE_PHONE, dataModel.getMobile());
        String gender;
        switch (dataModel.getGender()) {
            // -1：未设定 0：女 1：男
            case 0:
                gender = "女";
                break;
            case 1:
                gender = "男";
                break;
            default:
                gender = "未设定";
                break;
        }
        Repository.setLocalValue(LocalKey.USER_GENDER, gender);
        Repository.setLocalValue(LocalKey.IDENTITY, dataModel.getIdentity());//获取是店长还是店员角色
        return viewModel;
    }
}
