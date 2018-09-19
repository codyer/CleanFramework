package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.ItemCouponFailViewModel;
import com.cody.handler.framework.mapper.ModelMapper;

/**
 * Created by liuliwei on 2018-09-03.
 * 优惠券发送失败列表
 */

public class CouponFailListMapper extends ModelMapper<ItemCouponFailViewModel,String>{

    @Override
    public ItemCouponFailViewModel mapper(String dataModel, int position) {
        ItemCouponFailViewModel viewModel = new ItemCouponFailViewModel();
        if(position==0){
            viewModel.setShowViewLine(false);
        }else{
            viewModel.setShowViewLine(true);
        }
        return mapper(viewModel, dataModel);
    }

    @Override
    public ItemCouponFailViewModel mapper(ItemCouponFailViewModel viewModel, String dataModel) {
        if (dataModel == null || viewModel == null) return viewModel;
        viewModel.setPhoneStr(dataModel.replaceAll("(?<=[\\d]{3})\\d(?=[\\d]{4})", "*"));
        return viewModel;
    }

}
