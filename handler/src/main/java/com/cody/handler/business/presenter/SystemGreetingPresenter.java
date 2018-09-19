package com.cody.handler.business.presenter;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.cody.handler.business.viewmodel.ItemSystemGreetingGoodsViewModel;
import com.cody.handler.business.viewmodel.SystemGreetingViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.DefaultSubmitCallback;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.bean.im.ReplyInfoBean;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.JsonUtil;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.utils.http.SimpleBean;

/**
 * Created by cody.yi on 2018/8/4.
 * 系统欢迎词设置页
 */
public class SystemGreetingPresenter extends Presenter<SystemGreetingViewModel> {
    private LogImInteraction mLogImInteraction;

    public SystemGreetingPresenter() {
        mLogImInteraction = Repository.getInteraction(LogImInteraction.class);
    }

    public void save(String tag, final OnActionListener listener) {

        JsonObject params = new JsonObject();
        params.addProperty("ownerId", getViewModel().getOwnerId());
        if (!TextUtils.isEmpty(getViewModel().getGreetingId())) {
            params.addProperty("id", getViewModel().getGreetingId());
        }

        if (!TextUtils.isEmpty(getViewModel().getText().get())
                && getViewModel().getText().get().length() >= 15) {
            params.addProperty("replyContent", getViewModel().getText().get());
        } else {
            ToastUtil.showToast("欢迎语不能少于15个字");
            return;
        }

        if (getViewModel().getGoods().size() > 0) {
            JsonArray jsonArray = new JsonArray();
            JsonObject item;
            for (ItemSystemGreetingGoodsViewModel bean : getViewModel().getGoods()) {
                item = new JsonObject();
                item.addProperty("imageUrl", bean.getGoodsImageUrl());
                item.addProperty("merchandiseName", bean.getGoodsName());
                item.addProperty("merchandisePrice", bean.getGoodsPrice());
                item.addProperty("merchandiseSku", bean.getGoodsId());
                jsonArray.add(item);
            }
            params.add("recommendatoryMerchandise", jsonArray);
        }

        params.addProperty("enable", getViewModel().getEnable().get() ? 1 : 0);
        mLogImInteraction.saveOrUpdateGreetingReply(tag, params, ReplyInfoBean.GreetingReplyVoListBean.class,
                new DefaultCallback<ReplyInfoBean.GreetingReplyVoListBean>(this) {
                    @Override
                    public void onSuccess(ReplyInfoBean.GreetingReplyVoListBean bean) {
                        super.onSuccess(bean);
                        ReplyInfoBean replyInfoBean = JsonUtil.fromJson(Repository.getLocalValue(LocalKey.REPLY_INFO)
                                , ReplyInfoBean.class);
                        if (replyInfoBean != null
                                && replyInfoBean.getGreetingReplyVoList() != null
                                && replyInfoBean.getGreetingReplyVoList().size() > 0) {
                            if (bean != null && listener != null) {
                                replyInfoBean.getGreetingReplyVoList().set(0, bean);
                                Repository.setLocalValue(LocalKey.REPLY_INFO, JsonUtil.toJson(replyInfoBean));
                                listener.onSuccess();
                            }
                        }
                        getViewModel().setChanged(true);
                        refreshUI();
                    }
                });
    }

    public void switchOpen(String tag, final boolean open) {
        if (open == getViewModel().getEnable().get()) return;
        setOpen(open);
        JsonObject params = new JsonObject();
        params.addProperty("ownerId", getViewModel().getOwnerId());
        params.addProperty("id", getViewModel().getGreetingId());
        params.addProperty("enable", getViewModel().getEnable().get() ? 1 : 0);
        mLogImInteraction.updateGreetingReplyStatus(tag, params, SimpleBean.class,
                new DefaultSubmitCallback<SimpleBean>(this) {
                    @Override
                    public void onSuccess(SimpleBean bean) {
                        super.onSuccess(bean);
                        ReplyInfoBean replyInfoBean = JsonUtil.fromJson(Repository.getLocalValue(LocalKey.REPLY_INFO)
                                , ReplyInfoBean.class);
                        if (replyInfoBean != null
                                && replyInfoBean.getGreetingReplyVoList() != null
                                && replyInfoBean.getGreetingReplyVoList().size() > 0 &&
                                replyInfoBean.getGreetingReplyVoList().get(0) != null) {
                            replyInfoBean.getGreetingReplyVoList().get(0).setEnable(getViewModel().getEnable()
                                    .get() ? 1 : 0);
                            Repository.setLocalValue(LocalKey.REPLY_INFO, JsonUtil.toJson(replyInfoBean));
                        }
                        getViewModel().setChanged(true);
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
