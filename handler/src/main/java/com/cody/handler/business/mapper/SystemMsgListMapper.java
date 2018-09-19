package com.cody.handler.business.mapper;

import android.text.TextUtils;

import com.cody.handler.business.viewmodel.ItemSysMsgViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.im.SystemMsgBean;

/**
 * Create by jiquan.zhong  on 2018/7/26.
 * description:
 */
public class SystemMsgListMapper extends ModelMapper<ItemSysMsgViewModel, SystemMsgBean> {
    @Override
    public ItemSysMsgViewModel mapper(SystemMsgBean dataModel, int position) {
        ItemSysMsgViewModel viewModel = new ItemSysMsgViewModel();
        return mapper(viewModel, dataModel);
    }

    @Override
    public ItemSysMsgViewModel mapper(ItemSysMsgViewModel viewModel, SystemMsgBean dataModel) {
        if (dataModel == null || viewModel == null) return viewModel;
        viewModel.setTitle(dataModel.getTitle());
        viewModel.setContent(dataModel.getContent());
        viewModel.setTime(TextUtils.isEmpty(dataModel.getCreateDate()) ? "" : dataModel.getCreateDate());
        viewModel.setCategory(dataModel.getCategory());
        viewModel.setExtras(dataModel.getExtras());
        return viewModel;
    }
}
