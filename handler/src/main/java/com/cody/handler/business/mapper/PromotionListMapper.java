package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.ItemPromotionViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.ActivitiesListBean;
import com.cody.xf.utils.DateUtil;

/**
 * Create by jiquan.zhong  on 2018/8/15.
 * description:
 */
public class PromotionListMapper extends ModelMapper<ItemPromotionViewModel, ActivitiesListBean.RecordsBean> {
    @Override
    public ItemPromotionViewModel mapper(ActivitiesListBean.RecordsBean dataModel, int position) {
        ItemPromotionViewModel viewModel = new ItemPromotionViewModel();
        return mapper(viewModel, dataModel);
    }

    @Override
    public ItemPromotionViewModel mapper(ItemPromotionViewModel viewModel, ActivitiesListBean.RecordsBean dataModel) {
        if (null == viewModel || dataModel == null) return viewModel;
        viewModel.setActivityId(dataModel.getId());
        viewModel.setPageTitle(dataModel.getPageTitle());
        viewModel.setActivityName(dataModel.getActivityName());
        viewModel.setTotalVisitCount(dataModel.getTotalShowCount());
        viewModel.setTotalVisitCustomers(dataModel.getVisitorCount());
        viewModel.setActivityStatus(dataModel.getActivityStatus());
        viewModel.setTotalTimes(DateUtil.formatDuring2(dataModel.getOnLineTotalTime()));
        viewModel.setShareTitle(dataModel.getShareTitle());
        viewModel.setShareDesc(dataModel.getShareContent());
        viewModel.setShareImageUrl(dataModel.getShareImage());
        viewModel.setUrl(dataModel.getUrlLink());
        viewModel.getChecked().set(dataModel.getOnlineStatus() > 0);
        return viewModel;
    }
}
