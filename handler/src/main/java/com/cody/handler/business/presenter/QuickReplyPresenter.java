package com.cody.handler.business.presenter;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.cody.handler.R;
import com.cody.handler.business.mapper.QuickReplyMapper;
import com.cody.handler.business.viewmodel.QuickReplyViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.DefaultSubmitCallback;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.bean.im.ReplyInfoBean;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.JsonUtil;
import com.cody.xf.utils.ResourceUtil;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.utils.http.SimpleBean;

/**
 * Created by chen.huarong on 2018/7/30.
 */
public class QuickReplyPresenter extends Presenter<QuickReplyViewModel> {

    private LogImInteraction mInteraction;
    private QuickReplyMapper mMapper;

    public QuickReplyPresenter() {
        mInteraction = Repository.getInteraction(LogImInteraction.class);
        mMapper = new QuickReplyMapper();
    }

    public void getReplyInfo(final Object tag, final DefaultCallback<ReplyInfoBean> callback) {
        mInteraction.getReplyInfo(tag
                , UserInfoManager.getImId()
                , ReplyInfoBean.class
                , new DefaultCallback<ReplyInfoBean>(this) {
                    @Override
                    public void onSuccess(ReplyInfoBean bean) {
                        super.onSuccess(bean);
                        if (bean != null) {
                            Repository.setLocalValue(LocalKey.REPLY_INFO, JsonUtil.toJson(bean));
                        }
                        if (callback != null) {
                            callback.onSuccess(bean);
                        }
                    }
                });
    }

    public void saveOrUpdateQuickReply(final Object tag) {
        JsonObject params = new JsonObject();
        params.addProperty("ownerId", UserInfoManager.getImId());
        if (TextUtils.isEmpty(getViewModel().getQrId())) {
            ToastUtil.showToast(R.string.xf_http_code_parameter_err);
            return;
        }

        params.addProperty("id", getViewModel().getQrId());

        if (!TextUtils.isEmpty(getViewModel().getTitle().get())) {
            params.addProperty("title", getViewModel().getTitle().get().trim());
        } else {
            ToastUtil.showToast("标题不能为空");
            return;
        }

        if (!TextUtils.isEmpty(getViewModel().getContent().get())) {
            params.addProperty("replyContent", getViewModel().getContent().get().trim());
        } else {
            ToastUtil.showToast("内容不能为空");
            return;
        }

        params.addProperty("enable", getViewModel().getEnable().get() ? 1 : 0);
        mInteraction.saveOrUpdateQuickReply(tag, params, ReplyInfoBean.QuickReplyVoListBean.class
                , new DefaultCallback<ReplyInfoBean.QuickReplyVoListBean>(this) {
                    @Override
                    public void onSuccess(ReplyInfoBean.QuickReplyVoListBean bean) {
                        super.onSuccess(bean);
                        try {
                            ReplyInfoBean replyInfoBean = JsonUtil.fromJson(Repository.getLocalValue(LocalKey
                                            .REPLY_INFO)
                                    , ReplyInfoBean.class);
                            if (replyInfoBean != null
                                    && replyInfoBean.getQuickReplyVoList() != null) {
                                for (int i = 0; i < replyInfoBean.getQuickReplyVoList().size(); i++) {
                                    if (replyInfoBean.getQuickReplyVoList().get(i).getId().equals(bean.getId())) {
                                        replyInfoBean.getQuickReplyVoList().set(i, bean);
                                        Repository.setLocalValue(LocalKey.REPLY_INFO, JsonUtil.toJson(replyInfoBean));
                                        refreshUI(tag);
                                        return;
                                    }
                                }
                            }
                            getViewModel().setChanged(true);
                            refreshUI(tag);
                        } catch (Exception ignore) {
                            hideLoading();
                            ToastUtil.showToast(ResourceUtil.getString(R.string.xf_http_code_request_err));
                        }
                    }
                });
    }

    public void mapper(ReplyInfoBean.QuickReplyVoListBean bean) {
        mMapper.mapper(getViewModel(), bean);
        refreshUI();
    }

    public void switchOpen(String tag, final boolean open) {
        if (open == getViewModel().getEnable().get()) return;
        setOpen(open);
        JsonObject params = new JsonObject();
        params.addProperty("ownerId", getViewModel().getOwnerId());
        params.addProperty("id", getViewModel().getQrId());
        params.addProperty("enable", getViewModel().getEnable().get() ? 1 : 0);
        mInteraction.updateQuickReplyStatus(tag, params, SimpleBean.class,
                new DefaultSubmitCallback<SimpleBean>(this) {
                    @Override
                    public void onSuccess(SimpleBean bean) {
                        super.onSuccess(bean);
                        ReplyInfoBean replyInfoBean = JsonUtil.fromJson(Repository.getLocalValue(LocalKey
                                        .REPLY_INFO)
                                , ReplyInfoBean.class);
                        if (replyInfoBean != null && replyInfoBean.getQuickReplyVoList() != null) {
                            for (int i = 0; i < replyInfoBean.getQuickReplyVoList().size(); i++) {
                                if (replyInfoBean.getQuickReplyVoList().get(i) != null &&
                                        replyInfoBean.getQuickReplyVoList().get(i).getId()
                                                .equals(getViewModel().getQrId())) {
                                    int enable = getViewModel().getEnable().get() ? 1 : 0;
                                    replyInfoBean.getQuickReplyVoList().get(i).setEnable(enable);
                                    Repository.setLocalValue(LocalKey.REPLY_INFO, JsonUtil.toJson(replyInfoBean));
                                    getViewModel().setChanged(true);
                                    break;
                                }
                            }
                        }
                        refreshUI();
                    }

                    @Override
                    public void onFailure(SimpleBean simpleBean) {
                        super.onFailure(simpleBean);
                        setOpen(!open);
                    }

                    @Override
                    public void onError(SimpleBean simpleBean) {
                        super.onError(simpleBean);
                        setOpen(!open);
                    }
                });

    }

    private void setOpen(boolean open) {
        if (open == getViewModel().getEnable().get()) return;
        getViewModel().getEnable().set(open);
    }
}
