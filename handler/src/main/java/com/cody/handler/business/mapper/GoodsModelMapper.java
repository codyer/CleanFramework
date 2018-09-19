/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.business.mapper;

import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.GoodsBean;
import com.cody.handler.business.viewmodel.ItemGoodsViewModel;


/**
 * Created by cody.yi on 2016/8/24.
 * 模型装饰，映射,
 * 当获取的数据和ViewModel有差距时需要使用mapper
 */
public class GoodsModelMapper extends ModelMapper<ItemGoodsViewModel, GoodsBean.DataBean> {

    @Override
    public ItemGoodsViewModel mapper(GoodsBean.DataBean dataModel, int position) {
        ItemGoodsViewModel viewModel = new ItemGoodsViewModel();
        return mapper(viewModel, dataModel);
    }

    @Override
    public ItemGoodsViewModel mapper(ItemGoodsViewModel viewModel, GoodsBean.DataBean dataBean) {
        ItemGoodsViewModel item = new ItemGoodsViewModel();
        item.setTitle(dataBean.getTitle());
        item.setMaterial(dataBean.getMaterial());
        item.setId(dataBean.getPdt_sku());
        item.setSale_price("¥" + dataBean.getSale_price());
        item.setColor("颜色分类：" + dataBean.getColor());
        item.setPic_url(dataBean.getPic_url());
        item.setBase_category_name(dataBean.getShow_category_name());
        item.setCity_name(dataBean.getCity_name());
        item.setBrand_name(dataBean.getBrand_name());
        item.setStandard("长度：" + dataBean.getStandard());
        return item;
    }

}
