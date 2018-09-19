/*
 * Copyright (c)  Created by Cody.yi on 2016/8/30.
 */

package com.cody.handler.business.presenter;


import android.support.annotation.NonNull;

import com.cody.handler.business.mapper.SwitchSaleModelMapper;
import com.cody.handler.business.viewmodel.ItemSwitchSaleViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.ListWithHeaderPresenter;
import com.cody.repository.business.bean.SaleBean;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by chy on 2016/9/10.
 * IM中切换导购时的获取导购员列表
 */
public class SwitchSalePresenter extends ListWithHeaderPresenter<ItemSwitchSaleViewModel> {

    private LogImInteraction mInteraction;

    public SwitchSalePresenter() {
        mInteraction = Repository.getInteraction(LogImInteraction.class);
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        params.put("shopId", Repository.getLocalValue(LocalKey.SHOP_ID));
        requestSales(tag, params);
    }

    /**
     * 请求导购员列表
     *
     * @param tag    请求tag
     * @param params 请求参数
     */
    private void requestSales(final Object tag, Map<String, String> params) {
        if (getView() != null) {
            mInteraction.getSales(tag, params, SaleBean.class, new DefaultCallback<List<SaleBean>>(this) {
                @Override
                public void onBegin(Object tag) {
                }

                @Override
                public void onSuccess(List<SaleBean> obj) {
                    super.onSuccess(obj);
                    SwitchSaleModelMapper modelMapper = new SwitchSaleModelMapper();
                    if (getView() != null) {
                        modelMapper.mapperList(getViewModel(), obj, getViewModel().getPosition(), false);
                        getView().onUpdate(tag, obj);
                    }
                }
            });
        }
    }
}
