/*
 * Copyright (c)  Created by Cody.yi on 2016/9/8.
 */

package com.cody.handler.business.mapper;

import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.QuotationBean;
import com.cody.handler.business.viewmodel.QuotationViewModel;

/**
 * Created by chy on 2016/9/8.
 * 报价单列表界面
 */
public class QuotationModelMapper extends ModelMapper<QuotationViewModel, QuotationBean> {

    @Override
    public QuotationViewModel mapper(QuotationBean dataModel, int position) {
        QuotationViewModel quotationViewModel = new QuotationViewModel();
        return mapper(quotationViewModel, dataModel);
    }

    @Override
    public QuotationViewModel mapper(QuotationViewModel viewModel, QuotationBean dataModel) {
        viewModel.setProductPicUrl(dataModel.getProductPicUrl());
        viewModel.setMemo(dataModel.getMemo());
        viewModel.setAddress(dataModel.getMarketAddress());
        viewModel.setProductName(dataModel.getProductName());
        viewModel.setModel(dataModel.getCategoryName());
        viewModel.setProductPicUrl(dataModel.getProductPicUrl());
        viewModel.setMarket(dataModel.getMarketName());
        viewModel.setOfferPrice(dataModel.getOfferPrice());
        viewModel.setProductName(dataModel.getProductName());
        viewModel.setQuantity(dataModel.getQuantity() + "");
        viewModel.setSalePrice("¥" + dataModel.getSalePrice());
        viewModel.setStandard(dataModel.getStandard());
        return viewModel;
    }
}
