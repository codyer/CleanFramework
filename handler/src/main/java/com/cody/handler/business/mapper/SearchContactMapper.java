package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.ItemSearchContactViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.database.UserInfoManager;

public class SearchContactMapper extends ModelMapper<ItemSearchContactViewModel, UserInfoBean> {
    @Override
    public ItemSearchContactViewModel mapper(UserInfoBean dataModel, int position) {
        ItemSearchContactViewModel model = new ItemSearchContactViewModel();
        return mapper(model, dataModel);
    }

    @Override
    public ItemSearchContactViewModel mapper(ItemSearchContactViewModel viewModel, UserInfoBean dataModel) {
        viewModel.setName(UserInfoManager.handleUserName(dataModel));
        viewModel.setAvatar(dataModel.getAvatar());
        viewModel.setImId(dataModel.getImId());
        return viewModel;
    }
}
