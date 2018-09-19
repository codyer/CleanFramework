package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.cody.handler.BuildConfig;
import com.cody.handler.business.mapper.ChatHistoryMapper;
import com.cody.handler.business.viewmodel.ChatViewModel;
import com.cody.handler.business.viewmodel.ItemMessageViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.IView;
import com.cody.repository.business.bean.im.ChatLogBean;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.JsonUtil;
import com.cody.xf.utils.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by chen.huarong on 2018/7/25.
 */
public class ChatHistoryPresenter extends ChatPresenter {

    private static final String TAG = ChatHistoryPresenter.class.getSimpleName();
    private LogImInteraction mInteraction;
    private ChatHistoryMapper mMapper;
    private long timestamp = System.currentTimeMillis();
    private boolean needScrollToBottom = true;

    public ChatHistoryPresenter() {
        mInteraction = Repository.getInteraction(LogImInteraction.class);
        mMapper = new ChatHistoryMapper();
    }

    @Override
    public void attachView(Object tag, IView view, ChatViewModel viewModel) {
        super.attachView(tag, view, viewModel);
        mMapper.setToChatUsername(getViewModel().getToChatUsername());
    }

    @Override
    public void getRecycleList(final Object tag, @NonNull Map<String, String> params) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pageSize", String.valueOf(getViewModel().getPageSize()));
        jsonObject.addProperty("imId", getViewModel().getToChatUsername());
        jsonObject.addProperty("shopId", getViewModel().getShopId());
        jsonObject.addProperty("timestamp", timestamp);
        mInteraction.getMessageByShopId(tag, jsonObject, ChatLogBean.class, new DefaultCallback<List<ChatLogBean>>
                (this) {

            @Override
            public void onBegin(Object tag) {

            }

            @Override
            public void onSuccess(List<ChatLogBean> beans) {
                super.onSuccess(beans);
                if (beans != null
                        && beans.size() > 0) {
                    getViewModel().setHasMore(beans.size() == getViewModel().getPageSize());
                    timestamp = beans.get(0).getTimestamp();
                    getViewModel().addAll(0, mMapper.<ItemMessageViewModel>mapperList(beans, 0));
                    if (BuildConfig.DEBUG) {
                        LogUtil.d(TAG, JsonUtil.toJson(beans));
                    }
                }
                if (needScrollToBottom) {
                    needScrollToBottom = false;
                    refreshUI(TAG_SCROLL_BOTTOM);
                } else {
                    refreshUI(tag);
                }
            }
        });
    }


}
