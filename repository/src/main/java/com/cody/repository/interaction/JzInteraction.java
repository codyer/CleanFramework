package com.cody.repository.interaction;


import com.cody.repository.bean.CaseBean;
import com.cody.repository.interaction.constant.Domain;
import com.cody.repository.interaction.constant.JzUrlPath;
import com.cody.xf.binding.ICallback;
import com.cody.xf.interaction.framework.QueryCallBack;
import com.cody.xf.interaction.framework.QueryClass;
import com.cody.xf.interaction.framework.QueryJson;
import com.cody.xf.interaction.framework.QueryMap;
import com.cody.xf.interaction.framework.QueryTag;
import com.cody.xf.interaction.framework.RequestMapping;
import com.cody.xf.interaction.framework.RequestMethod;
import com.cody.xf.interaction.framework.ResultType;
import com.cody.xf.interaction.framework.Server;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by cody.yi on 2017/3/28.
 * 家装测试接口
 */
@Server(Domain.JZ_MAIN_URL)
public interface JzInteraction {

    @RequestMapping(
            value = JzUrlPath.byStyleList,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getCase(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<?> clazz, @QueryCallBack ICallback<CaseBean> callback);

    @RequestMapping("c/case/byStyleList?pageNo=1&pageSize=10")
    void getCaseWithJson(@QueryTag Object tag, @QueryJson JsonObject JsonParams, @QueryCallBack ICallback<CaseBean> callback);
}
