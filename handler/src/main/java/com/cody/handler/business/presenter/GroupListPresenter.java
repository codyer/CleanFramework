package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.cody.handler.business.mapper.GroupListMapper;
import com.cody.handler.business.viewmodel.GroupListViewModel;
import com.cody.handler.business.viewmodel.ItemGroupViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.IView;
import com.cody.handler.framework.presenter.AbsListPresenter;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.repository.business.bean.entity.UserGroupBean;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.DataChangeListener;
import com.cody.repository.business.database.UserGroupManager;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.http.SimpleBean;

import java.util.List;
import java.util.Map;

/**
 * 客户分组
 */
public class GroupListPresenter extends AbsListPresenter<GroupListViewModel, ItemGroupViewModel> {
    private LogImInteraction mImInteraction;
    private GroupListMapper mMapper;
    private DataChangeListener mGroupChangeListener;

    public GroupListPresenter() {
        mImInteraction = Repository.getInteraction(LogImInteraction.class);
        mMapper = new GroupListMapper();
    }

    @Override
    public void attachView(Object tag, IView view, GroupListViewModel viewModel) {
        super.attachView(tag, view, viewModel);
        getFromDataBase();
    }

    @Override
    public void detachView(Object tag) {
        super.detachView(tag);
        if (mGroupChangeListener != null) {
            mGroupChangeListener.cancel();
            mGroupChangeListener = null;
        }
    }

    @Override
    public void getRecycleList(final Object tag, @NonNull Map<String, String> params) {
        mImInteraction.getGroupList(tag, UserInfoManager.getImId(), UserGroupBean.class, new DefaultCallback<List<UserGroupBean>>(this) {
            @Override
            public void onBegin(Object tag) {
//                super.onBegin(tag);
            }

            @Override
            public void onSuccess(final List<UserGroupBean> groups) {
                super.onSuccess(groups);
                if (groups != null && groups.size() > 0) {
                    UserGroupManager.saveUserGroup(groups);
                }
                refreshUI();
            }
        });
    }

    private void getFromDataBase() {
        if (mGroupChangeListener != null) return;
        mGroupChangeListener = UserGroupManager.getUserGroup(false, new DataCallBack<List<UserGroupBean>>() {
            @Override
            public void onSuccess(List<UserGroupBean> groups) {
                super.onSuccess(groups);
                if (groups != null) {
                    getViewModel().setTotal(groups.size());
                    mMapper.mapperModel(getViewModel(), groups);
                }
                refreshUI();
            }
        });
    }

    public void deleteUserGroup(Object tag, JsonObject params, final OnActionListener actionListener) {
        mImInteraction.deleteUserGroup(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onBegin(Object tag) {
//                super.onBegin(tag);
            }

            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                actionListener.onSuccess();
            }

            @Override
            public void onFailure(SimpleBean simpleBean) {
                super.onFailure(simpleBean);
            }

            @Override
            public void onError(SimpleBean simpleBean) {
                super.onError(simpleBean);
            }
        });
    }
}
