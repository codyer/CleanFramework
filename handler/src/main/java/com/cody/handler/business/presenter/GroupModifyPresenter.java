package com.cody.handler.business.presenter;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.cody.handler.business.viewmodel.GroupModifyViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.IView;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.bean.entity.UserGroupBean;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.UserGroupManager;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.business.interaction.LongInteraction;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.http.SimpleBean;

import java.util.List;

/**
 * 客户分组
 */
public class GroupModifyPresenter extends Presenter<GroupModifyViewModel> {
    private LongInteraction mInteraction;
    private LogImInteraction mImInteraction;

    public GroupModifyPresenter() {
        mInteraction = Repository.getInteraction(LongInteraction.class);
        mImInteraction = Repository.getInteraction(LogImInteraction.class);
    }

    @Override
    public void attachView(Object tag, IView view, GroupModifyViewModel viewModel) {
        super.attachView(tag, view, viewModel);
        updateUserGroup(tag, null);
    }

    @Override
    public void detachView(Object tag) {
        super.detachView(tag);
    }

    // 修改用户分组
    public void modifyGroup(final String tag, OnActionListener actionListener) {
        switch (getViewModel().getType()) {
            case GroupModifyViewModel.ADD_TO_GROUP:
                addUserToGroup(tag, actionListener);
                break;
            case GroupModifyViewModel.EDIT_TO_GROUP:
                editUserGroup(tag, actionListener);
                break;
        }
    }

    private void addUserToGroup(final String tag, final OnActionListener actionListener) {
        if (TextUtils.isEmpty(getViewModel().getNewGroupId())) {
            if (actionListener != null) {
                actionListener.onSuccess();
            }
            return;
        }
        JsonObject params = new JsonObject();
        params.addProperty("bimId", Repository.getLocalValue(LocalKey.IM_ID));
        params.addProperty("groupId", getViewModel().getNewGroupId());
        params.addProperty("cimId", getViewModel().getImId());
        mInteraction.addUserToGroup(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                updateUserGroup(tag, actionListener);
            }
        });
    }

    //修改用户至分组
    private void editUserGroup(final String tag, final OnActionListener actionListener) {
        if (TextUtils.isEmpty(getViewModel().getOldGroupId()) || TextUtils.isEmpty(getViewModel().getNewGroupId())
                || getViewModel().getOldGroupId().equals(getViewModel().getNewGroupId())) {
            if (actionListener != null) {
                actionListener.onSuccess();
            }
            return;
        }
        JsonObject params = new JsonObject();
        params.addProperty("bimId", Repository.getLocalValue(LocalKey.IM_ID));
        params.addProperty("groupId", getViewModel().getOldGroupId());
        params.addProperty("cimId", getViewModel().getImId());
        params.addProperty("toGroupId", getViewModel().getNewGroupId());
        mInteraction.editUserToGroup(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                updateUserGroup(tag, actionListener);
            }
        });
    }

    //更新用户分组
    public void updateUserGroup(Object tag, final OnActionListener actionListener) {
        mImInteraction.getGroupList(tag, UserInfoManager.getImId(), UserGroupBean.class, new DefaultCallback<List<UserGroupBean>>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(final List<UserGroupBean> groups) {
                super.onSuccess(groups);
                if (groups != null && groups.size() > 0) {
                    if (actionListener == null) {
                        UserGroupManager.saveUserGroup(groups);
                    } else {
                        UserGroupManager.saveUserGroup(groups, new DataCallBack<String>() {
                            @Override
                            public void onSuccess(String s) {
                                super.onSuccess(s);
                                actionListener.onSuccess();
                            }

                            @Override
                            public void onError(String error) {
                                super.onError(error);
                                actionListener.onSuccess();
                            }
                        });
                    }
                }
                if (actionListener != null) {
                    actionListener.onSuccess();
                }
                refreshUI();
            }
        });
    }

    /**
     * 修改或者添加分组
     */
    public void modifyOrAddGroup(final Object tag, JsonObject params, final OnActionListener actionListener) {
        mImInteraction.saveOrUpdateUserGroup(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                updateUserGroup(tag, actionListener);
            }
        });
    }
}
