package com.cody.handler.business.presenter;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.cody.handler.business.EaseCommonUtils;
import com.cody.handler.business.viewmodel.MainImItemViewModel;
import com.cody.handler.business.viewmodel.MainViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.IView;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.repository.business.bean.RankBean;
import com.cody.repository.business.bean.entity.UserGroupBean;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.UserGroupManager;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.business.interaction.LongInteraction;
import com.cody.repository.business.interaction.SystemMsgInteraction;
import com.cody.repository.business.interaction.constant.Constant;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.http.SimpleBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cody.yi on 2018/7/18.
 * 首页
 */
public class MainPresenter extends Presenter<MainViewModel> {
    private LogImInteraction mImInteraction;
    private LongInteraction mInteraction;
    private SystemMsgInteraction mPushInteraction;

    public MainPresenter() {
        mImInteraction = Repository.getInteraction(LogImInteraction.class);
        mInteraction = Repository.getInteraction(LongInteraction.class);
        mPushInteraction = Repository.getInteraction(SystemMsgInteraction.class);
    }

    @Override
    public void attachView(Object tag, IView view, MainViewModel viewModel) {
        super.attachView(tag, view, viewModel);
        updateUserGroup(tag);
    }

    @Override
    public void detachView(Object tag) {
        super.detachView(tag);
    }

    public void registerPush(Object tag) {
        Map<String, String> params = new HashMap<>();
        String openId = Repository.getLocalValue(LocalKey.OPEN_ID);
        if (TextUtils.isEmpty(openId)) return;
        openId = openId.replaceAll("-", "_");
        params.put("appCode", "LG");//字典数据：
        params.put("accountId", openId);//用户id
        params.put("tag", openId);//用户标签，标记用户
        params.put("platformType", "android");//设备类型：iso/android
        mPushInteraction.registerPush(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onBegin(Object tag) {
            }

            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                LogUtil.d("J_PUSH", "register success");
            }

            @Override
            public void onFailure(SimpleBean simpleBean) {
                super.onFailure(simpleBean);
                LogUtil.d("J_PUSH", "register fail");
            }
        });
    }

    //更新用户分组
    private void updateUserGroup(Object tag) {
        mImInteraction.getGroupList(tag, UserInfoManager.getImId(), UserGroupBean.class, new DefaultCallback<List<UserGroupBean>>(this) {
            @Override
            public void onSuccess(final List<UserGroupBean> groups) {
                super.onSuccess(groups);
                if (groups != null && groups.size() > 0) {
                    UserGroupManager.saveUserGroup(groups);
                }
            }
        });
    }

    public void getRank(Object tag) {
        mInteraction.getRank(tag, 3, RankBean.class, new DefaultCallback<RankBean>(this) {
            @Override
            public void onSuccess(RankBean bean) {
                super.onSuccess(bean);
                if (bean != null && getViewModel() != null) {
                    getViewModel().setRank(bean.getRank());
                }
                refreshUI();
            }
        });
    }

    public void online(Object tag, final boolean online) {
        if (online == getViewModel().getOnline().get())return;
        setOnline(online);
        JsonObject params = new JsonObject();
        params.addProperty("imId", UserInfoManager.getImId());
        params.addProperty("userStatus", Repository.getLocalInt(LocalKey.USER_STATUS));
        mImInteraction.updateIMStatus(tag, params, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onBegin(Object tag) {
//                super.onBegin(tag);
            }

            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
            }

            @Override
            public void onFailure(SimpleBean simpleBean) {
                super.onFailure(simpleBean);
                setOnline(!online);
            }

            @Override
            public void onError(SimpleBean simpleBean) {
                super.onError(simpleBean);
                setOnline(!online);
            }
        });
    }

    private void setOnline(boolean online) {
        if (online == getViewModel().getOnline().get())return;
        getViewModel().getOnline().set(online);
        if (online) {
            Repository.setLocalInt(LocalKey.USER_STATUS, Constant.Status.online);
        } else {
            Repository.setLocalInt(LocalKey.USER_STATUS, Constant.Status.leave);
        }
    }

    /**
     * 刷新未读消息数
     */
    public void updateUnreadMsgCount(Object tag) {
        ListViewModel<MainImItemViewModel> items = getViewModel().getItems();
        items.clear();
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        int count = 0;
        if (conversations != null) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getUnreadMsgCount() > 0) {
                    EMMessage message = conversation.getLastMessage();
                    if (message == null || message.getFrom().equals(UserInfoManager.getImId()))
                        continue;
                    final MainImItemViewModel item = new MainImItemViewModel();
                    item.setId(message.getFrom());
                    item.setTime(message.getMsgTime());
                    item.setContent(EaseCommonUtils.getMessageDigest(message, XFoundation.getContext()));
                    UserInfoManager.getUserInfo(tag, message.getFrom(),
                            new DataCallBack<UserInfoBean>() {
                                @Override
                                public void onSuccess(UserInfoBean userInfoBean) {
                                    super.onSuccess(userInfoBean);
                                    item.getImageUrl().set(userInfoBean.getAvatar());
                                    item.getName().set(UserInfoManager.handleUserName(userInfoBean));
                                }
                            });
                    if (!items.contains(item)) {
                        items.add(item);
                    }
                    count += conversation.getUnreadMsgCount();
                }
            }
            Collections.sort(getViewModel().getItems(), new Comparator<MainImItemViewModel>() {
                @Override
                public int compare(final MainImItemViewModel con1, final MainImItemViewModel con2) {

                    if (con1.getTime() == con2.getTime()) {
                        return 0;
                    } else if (con2.getTime() > con1.getTime()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
        }
        getViewModel().getUnreadMsgCount().set(count);
    }
}
