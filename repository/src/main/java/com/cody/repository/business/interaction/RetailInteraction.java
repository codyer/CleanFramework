package com.cody.repository.business.interaction;

import com.google.gson.JsonObject;
import com.cody.repository.Domain;
import com.cody.repository.business.bean.MinAppBean;
import com.cody.repository.business.interaction.constant.RetailUrl;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.repository.framework.interaction.QueryCallBack;
import com.cody.repository.framework.interaction.QueryClass;
import com.cody.repository.framework.interaction.QueryJson;
import com.cody.repository.framework.interaction.QueryTag;
import com.cody.repository.framework.interaction.RequestMapping;
import com.cody.repository.framework.interaction.RequestMethod;
import com.cody.repository.framework.interaction.ResultType;
import com.cody.repository.framework.interaction.Server;

/**
 * Created by cody.yi on 2018/8/22.
 * 零售
 */
@Server(Domain.RETAIL_URL)
public interface RetailInteraction {
    //获取店铺小程序二维码
    @RequestMapping(
            value = RetailUrl.WX_CODE_URL,
            method = RequestMethod.POST,
            type = ResultType.BEAN)
    void getWxQrCode(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<MinAppBean> clazz,
                 @QueryCallBack ICallback<MinAppBean> callback);

}
