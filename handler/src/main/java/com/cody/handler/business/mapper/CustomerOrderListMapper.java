package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.ItemCustomerOrderListViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.order.CustomerOrderListBean;

/**
 * TA 的订单列表
 */
public class CustomerOrderListMapper extends ModelMapper<ItemCustomerOrderListViewModel, CustomerOrderListBean.OrderBean> {

    @Override
    public ItemCustomerOrderListViewModel mapper(CustomerOrderListBean.OrderBean dataModel, int position) {
        ItemCustomerOrderListViewModel viewModel = new ItemCustomerOrderListViewModel();
        return mapper(viewModel, dataModel);
    }

    @Override
    public ItemCustomerOrderListViewModel mapper(ItemCustomerOrderListViewModel viewModel, CustomerOrderListBean.OrderBean dataModel) {
        if (dataModel == null) return viewModel;
        viewModel.setPaidAmount("¥ " + dataModel.getPaidAmount());
        viewModel.setOrderAmount("¥ " + dataModel.getPayableAmount());
        viewModel.setOrderNumber(dataModel.getSerialNumber());
        viewModel.setOrderType(dataModel.getOrderStatusDesc());
        if (dataModel.getOrderItems() != null && dataModel.getOrderItems().size() > 0) {
            viewModel.setGoodsName(dataModel.getOrderItems().get(0).getProductName());
        } else {
            viewModel.setGoodsName("无产品名");
        }

        if (dataModel.getOrderItems() != null && dataModel.getOrderItems().size() > 0) {
            viewModel.setCount(dataModel.getOrderItems().get(0).getQuantity() + "");
        } else {
            viewModel.setCount("1");
        }
        viewModel.setHasServerMoney(dataModel.getOrderItems() != null && dataModel.getOrderItems().size() > 1);
        return viewModel;
    }
}
