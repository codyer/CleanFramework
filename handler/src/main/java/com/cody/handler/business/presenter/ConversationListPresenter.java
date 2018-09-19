package com.cody.handler.business.presenter;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.cody.handler.business.mapper.ConversationListMapper;
import com.cody.handler.business.viewmodel.ConversationViewModel;
import com.cody.handler.business.viewmodel.ItemConversationViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.AbsListPresenter;
import com.cody.repository.business.bean.im.SystemMsgBean;
import com.cody.repository.business.interaction.SystemMsgInteraction;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.http.SimpleBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationListPresenter extends AbsListPresenter<ConversationViewModel, ItemConversationViewModel> {

    public static final int ITEM_TYPE_COMPUTER = 0x111; // 电脑登录
    public static final int ITEM_TYPE_SYSTEM = 0x112; // 系统消息
    public static final int ITEM_TYPE_NORMAL = 0x113; // 普通消息

    private SystemMsgInteraction mSystemMsgInteraction;
    private ConversationListMapper mConversationListMapper;
    private String curUsername;
    private final static Handler mHandler = new Handler(Looper.getMainLooper());

    public ConversationListPresenter(String curUsername) {
        this.curUsername = curUsername;
        mSystemMsgInteraction = Repository.getInteraction(SystemMsgInteraction.class);
        mConversationListMapper = new ConversationListMapper();
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        isLoginComputer(tag);
    }

    /**
     * 判断电脑端是否登录
     *
     * @param tag
     */
    public void isLoginComputer(final Object tag) {
        EMClient.getInstance().contactManager().aysncGetSelfIdsOnOtherPlatform(new EMValueCallBack<List<String>>() {
            @Override
            public void onSuccess(final List<String> value) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (value != null && value.size() > 0) {
                            if (firstItemType() != ITEM_TYPE_COMPUTER) {
                                ItemConversationViewModel item = new ItemConversationViewModel();
                                item.setItemType(ITEM_TYPE_COMPUTER);
                                getViewModel().add(0, item);
                            }
                        } else {
                            if (firstItemType() == ITEM_TYPE_COMPUTER) {
                                getViewModel().remove(0);
                            }
                        }
                        getSysMsgData(tag, curUsername);
                        getConversationList(tag);
                    }
                });
            }

            @Override
            public void onError(int error, String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getSysMsgData(tag, curUsername);
                        getConversationList(tag);
                    }
                });
            }
        });
    }

    /**
     * 获取第一个item类型
     *
     * @return
     */
    private int firstItemType() {
        if (null != getViewModel() && getViewModel().size() > 0) {
            if (getViewModel().get(0) != null && getViewModel().get(0).getItemType() == ITEM_TYPE_COMPUTER) {
                return ITEM_TYPE_COMPUTER;
            } else if (getViewModel().get(0) != null && getViewModel().get(0).getItemType() == ITEM_TYPE_SYSTEM) {
                return ITEM_TYPE_SYSTEM;
            } else {
                return ITEM_TYPE_NORMAL;
            }
        } else {
            return -1;
        }
    }

    /**
     * 获取最近联系人列表
     */
    private void getConversationList(Object tag) {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        if (conversations != null) {
            List<EMConversation> dataModels = new ArrayList<>(conversations.values());
            for (int i = dataModels.size() - 1; i >= 0; i--) {
                EMConversation emConversation = dataModels.get(i);
                if (emConversation.getLastMessage() == null) {
                    dataModels.remove(emConversation);
                }
            }
            Collections.sort(dataModels, new Comparator<EMConversation>() {
                @Override
                public int compare(EMConversation con1, EMConversation con2) {
                    boolean item1Top = "true".equals(con1.getExtField());
                    boolean item2Top = "true".equals(con2.getExtField());
                    long time1 = con1.getLastMessage() == null ? 0 : con1.getLastMessage().getMsgTime();
                    long time2 = con2.getLastMessage() == null ? 0 : con2.getLastMessage().getMsgTime();

                    if (item1Top && item2Top) {
                        return compareTime(time2, time1);
                    } else if (item1Top) {
                        return -1;
                    } else if (item2Top) {
                        return 1;
                    } else {
                        return compareTime(time2, time1);
                    }
                }
            });
            if (getViewModel() != null && getViewModel().size() > 0) {
                if (getViewModel().get(0) != null && getViewModel().get(0).getItemType() == ITEM_TYPE_COMPUTER) {
                    mConversationListMapper.mapperList(getViewModel(), dataModels, 2, false);
                } else {
                    mConversationListMapper.mapperList(getViewModel(), dataModels, 1, false);
                }
            } else {
                mConversationListMapper.mapperList(getViewModel(), dataModels, 0, false);
            }
        }
    }

    /**
     * 时间比对
     */
    private int compareTime(long x, long y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    /**
     * 获取系统消息
     *
     * @param curUsername 当前登录用户的imid
     */
    private void getSysMsgData(Object tag, String curUsername) {
        if (TextUtils.isEmpty(curUsername)) {
            getView().hideLoading();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("appCode", "LG");
        params.put("accountId", curUsername.substring(2));
        mSystemMsgInteraction.getSystemMsg(tag, params, SystemMsgBean.class, new DefaultCallback<List<SystemMsgBean>>(this) {
            @Override
            public void onBegin(Object tag) {
//                super.onBegin(tag);
            }

            @Override
            public void onSuccess(List<SystemMsgBean> bean) {
                super.onSuccess(bean);
                if (bean != null && bean.size() > 0) {
                    SystemMsgBean lastMsgBean = bean.get(0);
                    // 如果IM电脑已登录，系统消息add到1位置，否则add到0位置
                    if (firstItemType() == ITEM_TYPE_COMPUTER) {
                        getViewModel().get(1).setSysTitle(lastMsgBean.getTitle());
                        getViewModel().get(1).setSysContent(TextUtils.isEmpty(lastMsgBean.getContent()) ? "暂无内容" : lastMsgBean.getContent());
                        getViewModel().get(1).setSysTime(lastMsgBean.getCreateDate());
                    } else {
                        getViewModel().get(0).setSysTitle(lastMsgBean.getTitle());
                        getViewModel().get(0).setSysContent(TextUtils.isEmpty(lastMsgBean.getContent()) ? "暂无内容" : lastMsgBean.getContent());
                        getViewModel().get(0).setSysTime(lastMsgBean.getCreateDate());
                    }
                }
                hideLoading();
            }

            @Override
            public void onFailure(SimpleBean simpleBean) {
                super.onFailure(simpleBean);
            }
        });
    }

    /**
     * 置顶/取消置顶会话
     */
    public void isTopItem(Object tag, int position, Class cls) {
        ItemConversationViewModel itemConversationViewModel = getViewModel().get(position);
        if (itemConversationViewModel.getIsTop().get()) {
            // 取消置顶
            itemConversationViewModel.getIsTop().set(false);
            itemConversationViewModel.getEMConversation().setExtField("false");
            BuryingPointUtils.build(cls, 3990).submitF();
            getConversationList(tag);
        } else {
            // 置顶
            itemConversationViewModel.getIsTop().set(true);
            itemConversationViewModel.getEMConversation().setExtField("true");

            int index;
            if (firstItemType() == ITEM_TYPE_SYSTEM) {
                index = 1;
            } else if (firstItemType() == ITEM_TYPE_COMPUTER) {
                index = 2;
            } else {
                index = 0;
            }
            if (position > index) {
                getViewModel().remove(position);
                getViewModel().get(index).setFirstItem(false);
                itemConversationViewModel.setFirstItem(true);
                getViewModel().add(index, itemConversationViewModel);
            }
            BuryingPointUtils.build(cls, 3987).submitF();
        }
    }

    /**
     * 删除会话
     */
    public void removeItem(int position) {
        ItemConversationViewModel itemConversationViewModel = getViewModel().get(position);
        EMClient.getInstance().chatManager().deleteConversation(itemConversationViewModel.getConversationId(), false);
        getViewModel().remove(itemConversationViewModel);
    }
}
