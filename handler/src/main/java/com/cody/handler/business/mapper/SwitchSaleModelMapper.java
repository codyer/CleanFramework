/*
 * Copyright (c)  Created by Cody.yi on 2016/9/2.
 */

package com.cody.handler.business.mapper;

import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.SaleBean;
import com.cody.handler.business.viewmodel.ItemSwitchSaleViewModel;

/**
 * Created by chy on 2016/9/2.
 * <p/>
 * 切换导购模型映射
 */
public class SwitchSaleModelMapper extends ModelMapper<ItemSwitchSaleViewModel, SaleBean> {

    @Override
    public ItemSwitchSaleViewModel mapper(SaleBean dataModel, int position) {
        ItemSwitchSaleViewModel viewModel = new ItemSwitchSaleViewModel();
        return mapper(viewModel, dataModel);
    }

    @Override
    public ItemSwitchSaleViewModel mapper(ItemSwitchSaleViewModel viewModel, SaleBean sale) {
        ItemSwitchSaleViewModel item = new ItemSwitchSaleViewModel();
        item.setRole(sale.getUserStatus() + "导购");
        item.setName(sale.getUserName());
        item.setImageUrl(sale.getAvatar());
        item.setImId(sale.getImId());
        return item;
    }
}
