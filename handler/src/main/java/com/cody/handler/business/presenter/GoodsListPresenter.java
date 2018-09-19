/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;

import com.cody.repository.business.interaction.GoodsInteraction;
import com.cody.handler.business.mapper.GoodsModelMapper;
import com.cody.handler.business.viewmodel.ItemGoodsViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.ListWithHeaderAndSearchPresenter;
import com.cody.repository.business.bean.GoodsBean;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chy on 2016/9/5.
 * 请求商品列表
 */
public class GoodsListPresenter extends ListWithHeaderAndSearchPresenter<ItemGoodsViewModel> {

    private GoodsInteraction mGoodsInteraction;
    private GoodsModelMapper mGoodsModelMapper = new GoodsModelMapper();

    public GoodsListPresenter() {
        mGoodsInteraction = Repository.getInteraction(GoodsInteraction.class);
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        params.put("bizId", "1001");
        params.put("keyword", getViewModel().getSearchViewModel().getKeyWord().get());
        String value = "shop_id:" + Repository.getLocalValue(LocalKey.SHOP_ID);
        params.put("tags", value);
        getGoodsList(tag, params);
    }

    public void tapSearch(Object tag) {
        this.getInitPage(tag);
    }

    private void getGoodsList(final Object tag, Map<String, String> map) {
        mGoodsInteraction.getGoodsList(tag, map, GoodsBean.class, new DefaultCallback<List<GoodsBean>>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(List<GoodsBean> obj) {
                super.onSuccess(obj);
                if (getView() != null) {
                    if (obj.size() == 0) {
                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setData(new ArrayList<GoodsBean.DataBean>());
                        obj.add(goodsBean);
                    }

                    boolean more = (obj.get(0).getData() != null && getViewModel().getPageSize() == obj.get(0)
                            .getData().size());
                    mGoodsModelMapper.mapperList(getViewModel(), obj.get(0).getData(), getViewModel().getPosition(),
                            more);
                    refreshUI();
                }
            }
        });
    }
}
