package com.cody.app.business.im;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.cody.app.R;
import com.cody.app.business.SystemMessageActivity;
import com.cody.app.databinding.FragmentConversationListBinding;
import com.cody.app.business.easeui.ImManager;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.app.framework.fragment.AbsListFragment;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.cody.handler.business.presenter.ConversationListPresenter;
import com.cody.handler.business.viewmodel.ConversationViewModel;
import com.cody.handler.business.viewmodel.ItemConversationViewModel;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.widget.dialog.AlertDialog;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

/**
 * Created by dong.wang
 * Date: 2018/7/19
 * Time: 18:11
 * Description: 最近联系人
 */
public class ConversationListFragment extends AbsListFragment<ConversationListPresenter, ConversationViewModel, ItemConversationViewModel, FragmentConversationListBinding> {

    private MessageListener mMessageListener = new MessageListener();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
        getBinding().rtPullListView.getEmptyViewContainer().setVisibility(View.GONE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (menuInfo instanceof AdapterView.AdapterContextMenuInfo) {
            int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            LogUtil.d(TAG, "onCreateContextMenu : " + position);
            ItemConversationViewModel itemConversationViewModel = getViewModel().get(position);
            menu.add(ImTabActivity.CONTEXT_MENU_TYPE_CONVERSATION, position, 1, (itemConversationViewModel.getIsTop().get()) ? "取消置顶" : "置顶聊天");
            menu.add(ImTabActivity.CONTEXT_MENU_TYPE_CONVERSATION, position, 2, "删除会话");
            if (TextUtils.isEmpty(itemConversationViewModel.getGroupId())) {
                menu.add(ImTabActivity.CONTEXT_MENU_TYPE_CONVERSATION, position, 3, "添加分组");
            } else {
                menu.add(ImTabActivity.CONTEXT_MENU_TYPE_CONVERSATION, position, 3, "修改分组");
            }
        }
    }

    @Override
    protected void onUserVisible() {
        super.onUserVisible();
        updateItemChat();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateItemChat();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getGroupId() == ImTabActivity.CONTEXT_MENU_TYPE_GROUP) return false;
        final int position = item.getItemId();
        ItemConversationViewModel itemConversationViewModel = getViewModel().get(position);
        switch (item.getOrder()) {
            //置顶、取消置顶
            case 1:
                getPresenter().isTopItem(TAG, position, ConversationListFragment.class);
                return true;
            //删除会话
            case 2:
                new AlertDialog(getActivity()).builder()
                        .setTitle("是否要删除会话？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getPresenter().removeItem(position);
                            }
                        })
                        .setNegativeButton("取消", null).show();
                return true;
            //添加或修改分组
            case 3:
                BuryingPointUtils.build(ConversationListFragment.class, 3988).submitF();
                if (TextUtils.isEmpty(itemConversationViewModel.getGroupId())) {
                    GroupModifyActivity.addToGroup(itemConversationViewModel.getConversationId());
                } else {
                    GroupModifyActivity.editGroup(itemConversationViewModel.getConversationId(), itemConversationViewModel.getGroupId());
                }
                updateItemChat();
                return true;
        }
        return false;
    }

    @Override
    protected BaseRecycleViewAdapter<ItemConversationViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemConversationViewModel>(getViewModel()) {
            @Override
            public int getItemViewType(int position) {
                return getViewModel().get(position).getItemType();
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }

            @Override
            public int getItemLayoutId(int viewType) {
                switch (viewType) {
                    case ConversationListPresenter.ITEM_TYPE_COMPUTER:
                        return R.layout.item_conversation_computer;
                    case ConversationListPresenter.ITEM_TYPE_SYSTEM:
                        return R.layout.item_conversation_system;
                    default:
                        return R.layout.item_conversation_list;
                }
            }
        };
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().rtPullListView;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        ImManager.getInstance().isReconnectIM();
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.empty_view;
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        if (parent.getAdapter().getItemViewType(position) == ConversationListPresenter.ITEM_TYPE_NORMAL) {
            getViewModel().get(position).getUnreadMsgCount().set(0);// 隐藏未读数
            ChatActivity.startChat(getViewModel().get(position).getConversationId());
            BuryingPointUtils.build(ConversationListFragment.class, 3986).addTag(getViewModel().get(position).getGroupName()).submitF();
        } else if (parent.getAdapter().getItemViewType(position) == ConversationListPresenter.ITEM_TYPE_SYSTEM) {
            ActivityUtil.navigateTo(SystemMessageActivity.class);
            BuryingPointUtils.build(ConversationListFragment.class, 3985).submitF();
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_conversation_list;
    }

    @Override
    protected ConversationListPresenter buildPresenter() {
        return new ConversationListPresenter(UserInfoManager.getImId());
    }

    @Override
    protected ConversationViewModel buildViewModel(Bundle savedInstanceState) {
        ConversationViewModel conversationViewModels = new ConversationViewModel();
        conversationViewModels.setHasEndInfo(false);
        // 系统消息站位
        ItemConversationViewModel sysMsg = new ItemConversationViewModel();
        sysMsg.setItemType(ConversationListPresenter.ITEM_TYPE_SYSTEM);
        conversationViewModels.add(sysMsg);
        return conversationViewModels;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mMessageListener);
    }

    //监听消息变化
    private class MessageListener implements EMMessageListener {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            updateItemChat();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            updateItemChat();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            updateItemChat();
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
            updateItemChat();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            updateItemChat();
        }
    }

    private void updateItemChat() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getPresenter().isLoginComputer(TAG);
                }
            });
        }
    }
}
