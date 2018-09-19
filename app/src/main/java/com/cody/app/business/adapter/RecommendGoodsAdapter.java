/*
 * Copyright (c)  Created by chy on 2016/9/5.
 */

package com.cody.app.business.adapter;

import com.cody.app.R;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.viewmodel.ItemGoodsViewModel;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.xf.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chy on 2016/9/5.
 * 搜索商品adapter
 */
public class RecommendGoodsAdapter extends BaseRecycleViewAdapter<ItemGoodsViewModel> {

    private int limit;

    public RecommendGoodsAdapter(ListViewModel<ItemGoodsViewModel> listItems, int limit) {
        super(listItems);
        this.limit = limit;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_recommend_goods;
    }

    //设置被选中的item
    public void setSelectPosition(int selectPosition) {
        ItemGoodsViewModel itemCouponViewModel = getListViewModel().get(selectPosition);
        if (getSelectPositions().size() >= limit
                && !itemCouponViewModel.getChecked()) {
            ToastUtil.showToast(String.format("最多选择%d件商品", limit));
            return;
        }
        itemCouponViewModel.setChecked(!itemCouponViewModel.getChecked());
        getListViewModel().set(selectPosition, itemCouponViewModel);
    }

    public List<ItemGoodsViewModel> getSelectPositions() {
        List<ItemGoodsViewModel> selectPositions = new ArrayList<>();
        for (ItemGoodsViewModel itemGoodsViewModel : getListViewModel()) {
            if (itemGoodsViewModel.getChecked()) {
                selectPositions.add(itemGoodsViewModel);
            }
        }
        return selectPositions;
    }
}
