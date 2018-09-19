/*
 * Copyright (c)  Created by Cody.yi on 2016/8/30.
 */

package com.cody.app.business.adapter;

import com.cody.app.R;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.viewmodel.ItemSwitchSaleViewModel;
import com.cody.handler.framework.viewmodel.ListViewModel;

/**
 * Created by chy on 2016/8/29.
 * 切换导购角色的adapter
 */
public class SwitchSaleAdapter extends BaseRecycleViewAdapter<ItemSwitchSaleViewModel> {

    public SwitchSaleAdapter(ListViewModel<ItemSwitchSaleViewModel> listItems) {
        super(listItems);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_switch_sale;
    }
}
