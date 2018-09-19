package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.ItemCouponChannelViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.CouponChannelBean;

/**
 * Create by jiquan.zhong  on 2018/8/16.
 * description:
 */
public class CouponChannelMapper extends ModelMapper<ItemCouponChannelViewModel, CouponChannelBean> {
    @Override
    public ItemCouponChannelViewModel mapper(CouponChannelBean dataModel, int position) {
        ItemCouponChannelViewModel viewModel = new ItemCouponChannelViewModel();
        return mapper(viewModel, dataModel);
    }

    @Override
    public ItemCouponChannelViewModel mapper(ItemCouponChannelViewModel viewModel, CouponChannelBean dataModel) {
        if (null == viewModel || dataModel == null) return viewModel;
        viewModel.setChannelId(dataModel.getChannelId());
        viewModel.setChannelName(dataModel.getChannelName());
        return viewModel;
    }
}
