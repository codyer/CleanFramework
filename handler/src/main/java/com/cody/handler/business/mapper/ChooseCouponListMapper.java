package com.cody.handler.business.mapper;

import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.ChooseCouponListBean;
import com.cody.handler.business.viewmodel.ItemCouponImViewModel;

/**
 * Created by cody.yi on 2016/8/24.
 * 模型装饰，映射,
 * 当获取的数据和ViewModel有差距时需要使用mapper
 */
public class ChooseCouponListMapper extends ModelMapper<ItemCouponImViewModel, ChooseCouponListBean.ListBean> {

    @Override
    public ItemCouponImViewModel mapper(ChooseCouponListBean.ListBean dataModel, int position) {
        ItemCouponImViewModel viewModel = new ItemCouponImViewModel();
        return mapper(viewModel,dataModel);
    }

    @Override
    public ItemCouponImViewModel mapper(ItemCouponImViewModel viewModel, ChooseCouponListBean.ListBean listBean) {
        ItemCouponImViewModel item = new ItemCouponImViewModel();
        item.setName(listBean.getTitle());
        item.setCouponNum(listBean.getRemainingCount() + "张");
        item.setCouponId(listBean.getId());
        item.setCupontypeId(listBean.getId());
        item.setEndT(listBean.getEndT().split("T")[0].replaceAll("-", "."));
        item.setStartT(listBean.getStartT().split("T")[0].replaceAll("-", "."));
        item.setOwnerName(listBean.getOwnerName());
        item.setOwnerType(listBean.getOwnerType());
        item.setPromotionType(listBean.getCouponTypeName());
        item.setCouponSubName(listBean.getTitle());
        item.setCouponBound(listBean.getConditions());
        return item;
    }
}
