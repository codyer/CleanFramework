/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.repository.business.interaction;

import com.cody.repository.Domain;
import com.cody.repository.business.bean.GoodsBean;
import com.cody.repository.business.interaction.constant.GoodSearchUrl;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.repository.framework.interaction.QueryCallBack;
import com.cody.repository.framework.interaction.QueryClass;
import com.cody.repository.framework.interaction.QueryMap;
import com.cody.repository.framework.interaction.QueryTag;
import com.cody.repository.framework.interaction.RequestMapping;
import com.cody.repository.framework.interaction.RequestMethod;
import com.cody.repository.framework.interaction.ResultType;
import com.cody.repository.framework.interaction.Server;

import java.util.List;
import java.util.Map;

/**
 * Created by chy on 2016/9/5.
 * 获取商品列表请求
 */
@Server(Domain.GOODS_SEARCH_URL)
public interface GoodsInteraction {
    //优惠券列表
    @RequestMapping(
            value = GoodSearchUrl.SEARCH_GOODS,
            method = RequestMethod.GET,
            type = ResultType.LIST_BEAN)
    void getGoodsList(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<GoodsBean> clazz,
                       @QueryCallBack ICallback<List<GoodsBean>> callback);
}
