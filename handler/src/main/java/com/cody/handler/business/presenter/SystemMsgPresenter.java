package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cody.handler.business.mapper.SystemMsgListMapper;
import com.cody.handler.business.viewmodel.ItemSysMsgViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.ListWithHeaderPresenter;
import com.cody.repository.business.bean.im.SystemMsgBean;
import com.cody.repository.business.interaction.SystemMsgInteraction;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.http.SimpleBean;

import java.util.List;
import java.util.Map;

/**
 * Create by jiquan.zhong  on 2018/7/26.
 * description:
 */
public class SystemMsgPresenter extends ListWithHeaderPresenter<ItemSysMsgViewModel> {
    SystemMsgInteraction mMsgInteraction;
    private String curUsername;
    SystemMsgListMapper mMapper;

    public SystemMsgPresenter(String curUsername) {
        mMsgInteraction = Repository.getInteraction(SystemMsgInteraction.class);
        mMapper = new SystemMsgListMapper();
        this.curUsername = curUsername;
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        if (TextUtils.isEmpty(curUsername)) {
            getView().hideLoading();
            return;
        }
        params.clear();
        params.put("appCode", "LG");
        params.put("accountId", curUsername.substring(2));
        mMsgInteraction.getSystemMsg(tag, params, SystemMsgBean.class, new DefaultCallback<List<SystemMsgBean>>(this) {
            @Override
            public void onBegin(Object tag) {
//                super.onBegin(tag);
            }

            @Override
            public void onSuccess(List<SystemMsgBean> bean) {
                super.onSuccess(bean);
                if (bean != null && bean.size() > 0) {
                    mMapper.mapperList(getViewModel(), bean, getViewModel().getPosition(), false);
                }
                hideLoading();

            }

            @Override
            public void onFailure(SimpleBean simpleBean) {
                super.onFailure(simpleBean);
            }
        });
    }
}
