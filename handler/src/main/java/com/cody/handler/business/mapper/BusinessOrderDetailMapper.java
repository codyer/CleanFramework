package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.BusinessOrderDetailViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.order.BusinessOrderDetailBean;

/**
 * Created by liuliwei on 2018-07-16.
 * 商户订单详情
 */
public class BusinessOrderDetailMapper extends ModelMapper<BusinessOrderDetailViewModel,BusinessOrderDetailBean> {
    @Override
    public BusinessOrderDetailViewModel mapper(BusinessOrderDetailBean dataModel, int position) {
        BusinessOrderDetailViewModel model=new BusinessOrderDetailViewModel();
        return mapper(dataModel,position);
    }

    @Override
    public BusinessOrderDetailViewModel mapper(BusinessOrderDetailViewModel viewModel, BusinessOrderDetailBean dataModel) {
        viewModel.setSerialNumber(dataModel.getSerialNumber());

        return viewModel;
    }
}
