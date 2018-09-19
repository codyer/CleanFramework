package com.cody.handler.business.presenter;

import com.google.gson.JsonObject;
import com.cody.handler.business.viewmodel.ModifyRemarkViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.http.SimpleBean;

/**
 * Create by jiquan.zhong  on 2018/7/30.
 * description:
 */
public class ModifyRemarkPresenter extends Presenter<ModifyRemarkViewModel> {
    private LogImInteraction mImInteraction;

    public ModifyRemarkPresenter() {
        mImInteraction = Repository.getInteraction(LogImInteraction.class);
    }

    public void modifyImRemark(String tag, final String userId, final OnActionListener actionListener) {
        final String remark = getViewModel().getRemark().get();
        JsonObject params = new JsonObject();
        params.addProperty("ownerId", UserInfoManager.getImId());
        params.addProperty("friendId", userId);
        params.addProperty("remark", remark);
        mImInteraction.updateIMRemark(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                UserInfoManager.addOrUpdateUserRemark(userId, remark, new DataCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        super.onSuccess(s);
                        actionListener.onSuccess();
                    }

                    @Override
                    public void onError(String error) {
                        super.onError(error);
                        onFailure(new SimpleBean("400", error));
                    }
                });
            }
        });
    }

}
