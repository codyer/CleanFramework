package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.ItemCouponViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.CouponListBean;
import com.cody.xf.utils.DateUtil;

/**
 * Create by jiquan.zhong  on 2018/8/16.
 * description:优惠券列表
 */
public class CouponListMapper extends ModelMapper<ItemCouponViewModel, CouponListBean.RecordsBean> {
    @Override
    public ItemCouponViewModel mapper(CouponListBean.RecordsBean dataModel, int position) {
        ItemCouponViewModel viewModel = new ItemCouponViewModel();
        return mapper(viewModel, dataModel);
    }

    @Override
    public ItemCouponViewModel mapper(ItemCouponViewModel viewModel, CouponListBean.RecordsBean dataModel) {
        if (dataModel == null || viewModel == null) return viewModel;
        viewModel.setCouponId(dataModel.getId());
        viewModel.setCouponTitle(dataModel.getTitle());
        viewModel.setActualConsumed(dataModel.getActualConsumed());
        viewModel.setCouponName(dataModel.getCouponName());
        viewModel.setTotalAmount(dataModel.getIssuedTotalAmount());
        viewModel.setRemainAmount(dataModel.getRemainingCount());
        if (dataModel.getEffectTimeType() == 1) {
            viewModel.setExpiryDate("自领券起" + dataModel.getTimeValue() + (dataModel.getTimeUnit() == 1 ? "小时" : "天") + "内有效");
        } else {
            viewModel.setExpiryDate(DateUtil.getDateDotString(dataModel.getStartTime()) + "-" + DateUtil.getDateDotString(dataModel.getEndTime()));
        }
        viewModel.setCouponTypeId(dataModel.getCouponTypeId());
        viewModel.setCouponStatus(dataModel.getShopAppCouponStatus());
        viewModel.setExpiry(4 == dataModel.getShopAppCouponStatus());
        viewModel.setCondition(dataModel.getCondition());
        viewModel.setShowAdd(dataModel.isAddCouponAmount());
        return viewModel;
    }
}
