package com.cody.handler.business.mapper;

import android.text.TextUtils;

import com.cody.handler.business.viewmodel.CouponViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.CouponStoreListBean;

/**
 * Created by dong.wang
 * Date: 2018/8/20
 * Time: 11:35
 * Description: 选择优惠券
 */
public class SelectCouponListMapper extends ModelMapper<CouponViewModel, CouponStoreListBean.RecordsBean> {
    private int userSum;

    @Override
    public CouponViewModel mapper(CouponStoreListBean.RecordsBean dataModel, int position) {
        CouponViewModel viewModel = new CouponViewModel();
        return mapper(viewModel, dataModel);
    }

    @Override
    public CouponViewModel mapper(CouponViewModel viewModel, CouponStoreListBean.RecordsBean dataModel) {
        if (dataModel == null || viewModel == null) return viewModel;

        viewModel.setItemType(TextUtils.equals(dataModel.getTypeFlag(), "1") ? CouponViewModel.ITEM_TYPE_BAG : CouponViewModel.ITEM_TYPE_NORMAL);
        // 1平台 2商场 3店铺 4工厂
        viewModel.setVisibleTag(dataModel.getOwnerType() == 3);
        viewModel.setImageUrl(dataModel.getImageUrl());
        viewModel.setCouponId(dataModel.getId());
        viewModel.setOwnerName(dataModel.getOwnerName());
        viewModel.setName(dataModel.getName());
        viewModel.setOfferContent(dataModel.getOfferContent());
        if (userSum <= dataModel.getRemainingCount()) {
            viewModel.setEnough(true);
        } else {
            viewModel.setEnough(false);
        }
        viewModel.setRemainingCount(dataModel.getRemainingCount());
        viewModel.setNumberOfCoupons(dataModel.getNumberOfCoupons());
        viewModel.setConditions(dataModel.getConditions());
        viewModel.setUsedPercentage((int) (dataModel.getUsedPercentage().floatValue() * 100));
        viewModel.setDiscount(dataModel.getTitle());

        return viewModel;
    }

    public void setUserSum(int userSum) {
        this.userSum = userSum;
    }
}
