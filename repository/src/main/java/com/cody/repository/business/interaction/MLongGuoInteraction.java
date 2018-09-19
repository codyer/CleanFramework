package com.cody.repository.business.interaction;

import com.cody.repository.Domain;
import com.cody.repository.business.bean.ShopDetailBean;
import com.cody.repository.business.interaction.constant.MLongGuoUrl;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.repository.framework.interaction.QueryCallBack;
import com.cody.repository.framework.interaction.QueryClass;
import com.cody.repository.framework.interaction.QueryString;
import com.cody.repository.framework.interaction.QueryTag;
import com.cody.repository.framework.interaction.RequestMapping;
import com.cody.repository.framework.interaction.RequestMethod;
import com.cody.repository.framework.interaction.ResultType;
import com.cody.repository.framework.interaction.Server;

/**
 * Created by cody.yi on 2018/8/23.
 * 龙果店铺
 */
@Server(Domain.M_API_LONGGUO_URL)
public interface MLongGuoInteraction {
    //店铺信息
    @RequestMapping(
            value = MLongGuoUrl.SHOP_DETAIL,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getShopDetail(@QueryTag Object tag, @QueryString("id") String shopId, @QueryClass Class<ShopDetailBean> clazz,
                 @QueryCallBack ICallback<ShopDetailBean> callback);
}
