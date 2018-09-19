package com.cody.repository.business.interaction;


import com.google.gson.JsonObject;
import com.cody.repository.Domain;
import com.cody.repository.business.bean.CaseBean;
import com.cody.repository.business.interaction.constant.JzUrlPath;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.repository.framework.interaction.QueryCallBack;
import com.cody.repository.framework.interaction.QueryClass;
import com.cody.repository.framework.interaction.QueryJson;
import com.cody.repository.framework.interaction.QueryMap;
import com.cody.repository.framework.interaction.QueryTag;
import com.cody.repository.framework.interaction.RequestMapping;
import com.cody.repository.framework.interaction.RequestMethod;
import com.cody.repository.framework.interaction.ResultType;
import com.cody.repository.framework.interaction.Server;
import com.cody.xf.utils.http.DataPart;

import java.util.List;
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

    @RequestMapping(
            value = JzUrlPath.byStyleList,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void uploadPictures(@QueryTag Object tag, List<DataPart> dataPart, @QueryMap Map<String, String> params, @QueryClass Class<?> clazz, @QueryCallBack ICallback<CaseBean> callback);

}
