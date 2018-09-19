package com.cody.repository.business.interaction;


import com.cody.repository.Domain;
import com.cody.repository.business.bean.im.SystemMsgBean;
import com.cody.repository.business.interaction.constant.LongGuoUrl;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.repository.framework.interaction.QueryCallBack;
import com.cody.repository.framework.interaction.QueryClass;
import com.cody.repository.framework.interaction.QueryMap;
import com.cody.repository.framework.interaction.QueryTag;
import com.cody.repository.framework.interaction.RequestMapping;
import com.cody.repository.framework.interaction.RequestMethod;
import com.cody.repository.framework.interaction.ResultType;
import com.cody.repository.framework.interaction.Server;
import com.cody.xf.utils.http.SimpleBean;

import java.util.List;
import java.util.Map;

/**
 * Created by dong.wang on 2018-07-16.
 * 系统消息
 */
@Server(Domain.SYSTEM_URL)
public interface SystemMsgInteraction {
    // 系统消息
    @RequestMapping(
            value = LongGuoUrl.URL_GET_SYS_MSG,
            method = RequestMethod.GET,
            type = ResultType.LIST_BEAN)
    void getSystemMsg(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<?> clazz,
                      @QueryCallBack ICallback<List<SystemMsgBean>> callback);

    @RequestMapping(value = LongGuoUrl.J_PUSH_REGISTER,
            method = RequestMethod.GET,
            type = ResultType.SIMPLE)
    void registerPush(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<?> clazz, @QueryCallBack ICallback<SimpleBean> callback);
}
