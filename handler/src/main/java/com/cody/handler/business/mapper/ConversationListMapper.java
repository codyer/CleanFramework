package com.cody.handler.business.mapper;

import com.hyphenate.chat.EMConversation;
import com.cody.handler.business.presenter.ConversationListPresenter;
import com.cody.handler.business.viewmodel.ItemConversationViewModel;
import com.cody.handler.framework.mapper.ModelMapper;

/**
 * Created by dong.wang
 * on data:  2018/7/13 ;
 */

public class ConversationListMapper extends ModelMapper<ItemConversationViewModel, EMConversation> {

    @Override
    public ItemConversationViewModel mapper(EMConversation dataModel, int position) {
        return mapper(new ItemConversationViewModel(), dataModel);
    }

    @Override
    public ItemConversationViewModel mapper(ItemConversationViewModel viewModel, EMConversation dataModel) {
        if (null == viewModel || null == dataModel) return viewModel;

        viewModel.setEMConversation(dataModel);
        viewModel.setItemType(ConversationListPresenter.ITEM_TYPE_NORMAL);

        return viewModel;
    }
}
