package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.ListWithSearchViewModel;

/**
 * Created by liuliwei on 2018-07-17.
 * 商户订单
 */
public class SearchBusinessOrderViewModel extends ListWithSearchViewModel<ItemBusinessOrderListViewModel> {
    public SearchBusinessOrderViewModel() {
        getSearchViewModel().getHint().set("搜索顾客手机号、单号");
    }
}
