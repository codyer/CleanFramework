package com.cody.handler.business.presenter;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.cody.handler.business.viewmodel.UserInfoViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.IView;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.bean.RemarkBean;
import com.cody.repository.business.bean.entity.UserGroupBean;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.DataChangeListener;
import com.cody.repository.business.database.UserGroupManager;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.business.interaction.LongInteraction;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.utils.http.SimpleBean;

import java.util.List;

/**
 * Create by jiquan.zhong  on 2018/7/30.
 * 用户信息
 */
public class UserInfoPresenter extends Presenter<UserInfoViewModel> {
    private LongInteraction mInteraction;
    private LogImInteraction mImInteraction;
    private DataChangeListener mUserInfoChangeListener;

    public UserInfoPresenter() {
        mImInteraction = Repository.getInteraction(LogImInteraction.class);
        mInteraction = Repository.getInteraction(LongInteraction.class);
    }

    @Override
    public void attachView(Object tag, IView view, UserInfoViewModel viewModel) {
        super.attachView(tag, view, viewModel);
        getUserInfo(tag);
    }

    @Override
    public void detachView(Object tag) {
        super.detachView(tag);
        if (mUserInfoChangeListener != null) {
            mUserInfoChangeListener.cancel();
            mUserInfoChangeListener = null;
        }
    }

    //将用户从组中删除
    public void delUserFromGroup(final String tag, final OnActionListener actionListener) {
        if (TextUtils.isEmpty(getViewModel().getGroupId())) {
            if (actionListener != null) {
                actionListener.onSuccess();
            }
            getViewModel().getShowDelete().set(false);
            return;
        }
        JsonObject params = new JsonObject();
        params.addProperty("bimId", Repository.getLocalValue(LocalKey.IM_ID));
        params.addProperty("groupId", getViewModel().getGroupId());
        params.addProperty("cimId", getViewModel().getImId());
        mInteraction.deleteUserFromGroup(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                UserGroupManager.deleteUserGroup(getViewModel().getGroupId(), getViewModel().getImId(), new DataCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        super.onSuccess(s);
                        if (actionListener != null) {
                            actionListener.onSuccess();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        super.onError(error);
                        updateUserGroup(tag, actionListener);
                    }
                });
            }
        });
    }

    //更新用户分组
    private void updateUserGroup(Object tag, final OnActionListener actionListener) {
        mImInteraction.getGroupList(tag, UserInfoManager.getImId(), UserGroupBean.class, new DefaultCallback<List<UserGroupBean>>(this) {
            @Override
            public void onBegin(Object tag) {
//                super.onBegin(tag);
            }

            @Override
            public void onSuccess(final List<UserGroupBean> groups) {
                super.onSuccess(groups);
                if (actionListener != null) {
                    if (groups != null && groups.size() > 0) {
                        UserGroupManager.saveUserGroup(groups, new DataCallBack<String>() {
                            @Override
                            public void onSuccess(String s) {
                                super.onSuccess(s);
                                actionListener.onSuccess();
                            }

                            @Override
                            public void onError(String error) {
                                super.onError(error);
                                ToastUtil.showToast(error);
                                actionListener.onSuccess();
                            }
                        });
                    } else {
                        ToastUtil.showToast("获取分组失败");
                        actionListener.onSuccess();
                    }
                } else {
                    refreshUI();
                }
            }
        });
    }

    private void getUserInfo(Object tag) {
        if (mUserInfoChangeListener != null) return;
        mUserInfoChangeListener = UserInfoManager.getUserInfo(tag, false, true, getViewModel().getImId(), new DataCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean user) {
                super.onSuccess(user);
                if (user != null) {
                    getViewModel().setNickName(user.getNickName());
                    getViewModel().getRemarkName().set(user.getRemark());
                    getViewModel().getHeaderViewModel().setTitle(UserInfoManager.handleUserName(user));
                    getViewModel().setAvatar(user.getAvatar());
                    getViewModel().setGroupId(user.getGroupId());
                    getViewModel().setGroupName(user.getGroupName());
                }
            }
        });
    }

    public void getRemark(String tag, String userId, final OnActionListener listener) {
        JsonObject params = new JsonObject();
        params.addProperty("ownerId", UserInfoManager.getImId());
        params.addProperty("friendId", userId);
        mImInteraction.getRemark(tag, params, RemarkBean.class, new DefaultCallback<RemarkBean>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(RemarkBean bean) {
                super.onSuccess(bean);
                if (bean != null) {
                    getViewModel().getRemark().set(bean.getRemarkInfo());
                    if (listener != null) {
                        listener.onSuccess();
                    }
                }
            }
        });
    }

    public void saveRemark(String tag, String userId, final OnActionListener listener) {
        final String remark = getViewModel().getRemark().get().trim();
        JsonObject params = new JsonObject();
        params.addProperty("ownerId", UserInfoManager.getImId());
        params.addProperty("friendId", userId);
        params.addProperty("remarkInfo", remark);
        mImInteraction.updateRemark(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                hideLoading();
                ToastUtil.showToast("保存成功");
                if (listener != null) {
                    listener.onSuccess();
                }
            }
        });
    }
}
