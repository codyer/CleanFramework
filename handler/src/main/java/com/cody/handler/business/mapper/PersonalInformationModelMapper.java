/*
 * Copyright (c)  Created by Cody.yi on 2016/9/11.
 */

package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.PersonalInformationViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.PersonalInformationBean;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;

/**
 * Created by Cody.yi on 2016/9/19.
 * 导购员个人信息
 */
public class PersonalInformationModelMapper extends ModelMapper<PersonalInformationViewModel, PersonalInformationBean> {

    @Override
    public PersonalInformationViewModel mapper(PersonalInformationBean dataModel, int position) {
        PersonalInformationViewModel personalInformationViewModel = new
                PersonalInformationViewModel();
        return mapper(personalInformationViewModel, dataModel);
    }

    @Override
    public PersonalInformationViewModel mapper(PersonalInformationViewModel viewModel, PersonalInformationBean
            dataModel) {
        return viewModel;
    }

    /**
     * 个人信息模型映射
     *
     * @param viewModel PersonalInformationViewModel
     */
    public void mapper(PersonalInformationViewModel viewModel) {
        viewModel.getImageUrl().set(Repository.getLocalValue(LocalKey.PICTURE_URL));
        viewModel.getTelPhone().set(Repository.getLocalValue(LocalKey.MOBILE_PHONE));
        viewModel.getSelfIntroduction().set(Repository.getLocalValue(LocalKey.INTRODUCTION));
        viewModel.getGender().set(Repository.getLocalValue(LocalKey.USER_GENDER));
        viewModel.getRealName().set(Repository.getLocalValue(LocalKey.REAL_NAME));
        viewModel.getNickName().set(Repository.getLocalValue(LocalKey.NICK_NAME));
        int userStatusType = Repository.getLocalInt(LocalKey.USER_STATUS);
        if (userStatusType == 1) {
            viewModel.getImStatus().set(true);
        } else {
            viewModel.getImStatus().set(false);
        }
    }
}
