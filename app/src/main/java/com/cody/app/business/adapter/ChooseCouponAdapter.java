/*
 * Copyright (c)  Created by Cody.yi on 2016/8/30.
 */

package com.cody.app.business.adapter;

import com.cody.app.R;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.viewmodel.ItemCouponImViewModel;
import com.cody.handler.framework.viewmodel.ListViewModel;

/**
 * Created by chy on 2016/8/29.
 * 选择优惠券的的adapter
 */
public class ChooseCouponAdapter extends BaseRecycleViewAdapter<ItemCouponImViewModel> {

    private int selectPosition;

    public ChooseCouponAdapter(ListViewModel<ItemCouponImViewModel> listItems) {
        super(listItems);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_im_coupon;
    }

    //设置被选中的item
    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        for (int i = 0; i < getListViewModel().size(); i++) {
            ItemCouponImViewModel itemCouponViewModel = getListViewModel().get(i);
            if (selectPosition != i) {
                itemCouponViewModel.setSelected(false);
            } else {
                itemCouponViewModel.setSelected(true);
            }
            getListViewModel().set(i, itemCouponViewModel);
        }
        notifyDataSetChanged();
    }

    public int getSelectPosition() {
        return selectPosition;
    }
}
