package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.cody.handler.business.mapper.PromotionListMapper;
import com.cody.handler.business.viewmodel.ItemPromotionViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.DefaultSubmitCallback;
import com.cody.handler.framework.presenter.ListWithHeaderPresenter;
import com.cody.repository.business.bean.ActivitiesListBean;
import com.cody.repository.business.interaction.ActivityInteraction;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.http.SimpleBean;

import java.util.Map;

/**
 * Create by jiquan.zhong  on 2018/8/14.
 * description:
 */
public class PromotionListPresenter extends ListWithHeaderPresenter<ItemPromotionViewModel> {
    private ActivityInteraction mInteraction;
    private PromotionListMapper mListMapper;

    public PromotionListPresenter() {
        mInteraction = Repository.getInteraction(ActivityInteraction.class);
        mListMapper = new PromotionListMapper();
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pageSize", getViewModel().getPageSize() + "");
        jsonObject.addProperty("pageNo", getViewModel().getPageNO() + "");
        mInteraction.getActivityList(tag, jsonObject, ActivitiesListBean.class, new DefaultCallback<ActivitiesListBean>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(ActivitiesListBean bean) {
                super.onSuccess(bean);
                if (null != bean && null != bean.getRecords()) {
                    mListMapper.mapperList(getViewModel(), bean.getRecords(), getViewModel().getPosition(), bean.getPageNo() < bean.getTotalPages());
                }
                refreshUI();
            }
        });
    }

    public void onLine(Object tag, final ItemPromotionViewModel viewModel, final boolean online) {
        if (online == viewModel.getChecked().get()) return;
        setOnline(viewModel, online);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", viewModel.getActivityId());
        jsonObject.addProperty("operating", online ? 1 : 0);
        mInteraction.activityOn_OffLine(tag, jsonObject, SimpleBean.class, new DefaultSubmitCallback<SimpleBean>(this) {
            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                refreshUI();
            }

            @Override
            public void onFailure(SimpleBean simpleBean) {
                super.onFailure(simpleBean);
                setOnline(viewModel, !online);
            }

            @Override
            public void onError(SimpleBean simpleBean) {
                super.onError(simpleBean);
                setOnline(viewModel, !online);
            }
        });
    }

    private void setOnline(ItemPromotionViewModel viewModel, boolean online) {
        if (online == viewModel.getChecked().get()) return;
        viewModel.getChecked().set(online);
    }
}
