package com.cody.repository.business.interaction;

import com.google.gson.JsonObject;
import com.cody.repository.Domain;
import com.cody.repository.business.bean.ActivitiesListBean;
import com.cody.repository.business.interaction.constant.ActivityUrl;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.repository.framework.interaction.QueryCallBack;
import com.cody.repository.framework.interaction.QueryClass;
import com.cody.repository.framework.interaction.QueryJson;
import com.cody.repository.framework.interaction.QueryTag;
import com.cody.repository.framework.interaction.RequestMapping;
import com.cody.repository.framework.interaction.RequestMethod;
import com.cody.repository.framework.interaction.ResultType;
import com.cody.repository.framework.interaction.Server;
import com.cody.xf.utils.http.SimpleBean;

/**
 * Create by jiquan.zhong  on 2018/8/14.
 * description:活动管理
 */
@Server(Domain.ACTIVITY_URL)
public interface ActivityInteraction {

    //活动列表
    @RequestMapping(
            value = ActivityUrl.ACTIVITY_QUERY,
            method = RequestMethod.POST,
            type = ResultType.BEAN)
    void getActivityList(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<ActivitiesListBean> clazz,
                         @QueryCallBack ICallback<ActivitiesListBean> callback);

    //活动上下线
    @RequestMapping(
            value = ActivityUrl.ACTIVITY_ON_OFFLINE,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void activityOn_OffLine(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                            @QueryCallBack ICallback<SimpleBean> callback);


}
