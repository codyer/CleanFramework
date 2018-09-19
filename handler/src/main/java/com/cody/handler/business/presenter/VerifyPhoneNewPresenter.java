

package com.cody.handler.business.presenter;

import com.cody.handler.business.viewmodel.VerifyPhoneNewViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.interaction.LoginNewInteraction;
import com.cody.repository.business.interaction.constant.Account;
import com.cody.repository.business.interaction.constant.Constant;
import com.cody.repository.framework.Repository;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.http.IHeaderListener;
import com.cody.xf.utils.http.SimpleBean;

import java.util.Map;


/**
 * Created by chy on 2018/7/18.
 * 登录相关处理
 */
public class VerifyPhoneNewPresenter extends Presenter<VerifyPhoneNewViewModel> {

    private LoginNewInteraction mLoginInteraction;

    public VerifyPhoneNewPresenter() {
        mLoginInteraction = Repository.getInteraction(LoginNewInteraction.class);
    }

    public void verifyLogin(final Object tag, final Map<String, String> params) {
        if (getView() != null) {
            getView().showLoading(null);
            mLoginInteraction.verifySmsLogin(tag, params, SimpleBean.class, new IHeaderListener() {
                @Override
                public void onHeaderResponse(Map<String, String> header) {
                    if (header.containsKey(Account.KEY_TOKEN)) {
                        XFoundation.getApp().onLogin(header);
                    }
                }
            }, new DefaultCallback<SimpleBean>(this) {
                @Override
                public void onSuccess(SimpleBean bean) {
                    super.onSuccess(bean);
                    if (getView() != null && getViewModel() != null) {
                        getView().onUpdate(Constant.RequestType.TYPE_1, bean);
                    }
                }

                @Override
                public void onFailure(SimpleBean simpleBean) {
                    super.onFailure(simpleBean);
                    getView().showFailure(simpleBean);
                    if ("90004".equals(simpleBean.getCode())) {
                    }
                }

                @Override
                public void onError(SimpleBean simpleBean) {
                    super.onError(simpleBean);
                    getView().showError(simpleBean);
                }
            });
        }
    }

    public void onSendMsg(Object tag, Map<String, String> params) {
        if (getView() != null) {
            getView().showLoading(null);
            mLoginInteraction.sendMsg(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
                @Override
                public void onSuccess(SimpleBean bean) {
                    super.onSuccess(bean);
                    if (getView() != null && getViewModel() != null) {
                        getView().onUpdate(Constant.RequestType.TYPE_2, bean);
                    }
                }

                @Override
                public void onFailure(SimpleBean simpleBean) {
                    super.onFailure(simpleBean);
                    getView().showFailure(simpleBean);
                }

                @Override
                public void onError(SimpleBean simpleBean) {
                    super.onError(simpleBean);
                    getView().showError(simpleBean);
                }
            });
        }
    }

}