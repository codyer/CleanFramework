package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.ItemQuickReplyViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.im.ReplyInfoBean;

/**
 * Created by chen.huarong on 2018/8/4.
 */
public class QuickReplySettingMapper extends ModelMapper<ItemQuickReplyViewModel
        , ReplyInfoBean.QuickReplyVoListBean> {

    @Override
    public ItemQuickReplyViewModel mapper(ReplyInfoBean.QuickReplyVoListBean dataModel, int position) {
        ItemQuickReplyViewModel viewModel = new ItemQuickReplyViewModel();
        viewModel.setItemType(position == 0 ? 0 : 1);
        return mapper(viewModel, dataModel);
    }

    @Override
    public ItemQuickReplyViewModel mapper(ItemQuickReplyViewModel viewModel, ReplyInfoBean.QuickReplyVoListBean
            dataModel) {
        if (dataModel == null) {
            return viewModel;
        }
        viewModel.setText(dataModel.getTitle());
        return viewModel;
    }
}
