package com.cody.repository.business.interaction;

import com.google.gson.JsonObject;
import com.cody.repository.Domain;
import com.cody.repository.business.bean.order.UpdateWrapBean;
import com.cody.repository.business.interaction.constant.AppConfigUrl;
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
 * Created by cody.yi on 2017/5/5.
 * App 配置
 */
@Server(Domain.APP_CONFIG_URL)
public interface AppConfigInteraction {

    @RequestMapping(
            value = AppConfigUrl.APP_UPDATE_INFO,
            method = RequestMethod.POST,
            type = ResultType.BEAN)
    void checkVersion(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<UpdateWrapBean> clazz, @QueryCallBack ICallback<UpdateWrapBean> callback);
}
