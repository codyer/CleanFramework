package com.cody.app.business.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cody.app.R;
import com.cody.app.databinding.FragmentGroupListBinding;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.app.framework.fragment.AbsListFragment;
import com.google.gson.JsonObject;
import com.cody.handler.business.presenter.GroupListPresenter;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.business.viewmodel.GroupListViewModel;
import com.cody.handler.business.viewmodel.ItemGroupViewModel;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.widget.dialog.AlertDialog;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by Cody.yi
 * Date: 2018/7/25
 * Time: 16:04
 * Description: 客户分组
 */
public class GroupListFragment extends AbsListFragment<GroupListPresenter, GroupListViewModel, ItemGroupViewModel,
        FragmentGroupListBinding> {
    private RecyclerView mRecyclerView;
    private View mStickyHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mRecyclerView = getBinding().fwList.getRecyclerView();
        mStickyHeader = getBinding().stickyInclude.stickyHeader;
        return view;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_group_list;
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.empty_view;
    }

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                final int firstVisible = layoutManager.findFirstVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING && firstVisible == 0) {
                    mStickyHeader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisible = layoutManager.findFirstVisibleItemPosition();

                final int position = getViewModel().getGroupPositionBeforePosition(firstVisible);
                if (position != -1 && position != getViewModel().getStickyPosition()) {
                    getViewModel().setStickyPosition(position);
                    final ItemGroupViewModel viewModel = getViewModel().get(position);
                    getBinding().stickyInclude.setViewModel(viewModel);
                    getBinding().stickyInclude.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onItemClick(mRecyclerView, mStickyHeader, position, mStickyHeader.getId());
                        }
                    });
                }
                int nextPosition = getViewModel().getGroupPositionAfterPosition(firstVisible) - firstVisible;
                if (nextPosition > 0 && nextPosition < recyclerView.getChildCount()) {
                    View stickyItem = recyclerView.getChildAt(nextPosition);
                    if (stickyItem != null) {
                        int dealtY = stickyItem.getTop() - mStickyHeader.getMeasuredHeight();
                        if (stickyItem.getTop() > 0 && dealtY < 0) {
                            mStickyHeader.setTranslationY(dealtY);
                        } else {
                            mStickyHeader.setTranslationY(0);
                        }
                    }
                }
                if (!recyclerView.canScrollVertically(-1)) {
                    mStickyHeader.setVisibility(View.GONE);
                } else {
                    mStickyHeader.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected GroupListPresenter buildPresenter() {
        return new GroupListPresenter();
    }

    @Override
    protected BaseRecycleViewAdapter<ItemGroupViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemGroupViewModel>(getViewModel()) {
            @Override
            public int getItemLayoutId(int viewType) {
                switch (viewType) {
                    case ItemGroupViewModel.TYPE_GROUP:
                        return R.layout.item_group_sticky_header;
                    case ItemGroupViewModel.TYPE_ITEM:
                        return R.layout.item_group_child;
                }
                return R.layout.item_group_sticky_header;
            }
        };
    }

    @Override
    protected GroupListViewModel buildViewModel(Bundle savedInstanceState) {
        final GroupListViewModel viewModel = new GroupListViewModel();
        viewModel.setHasEndInfo(false);
        return viewModel;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (menuInfo instanceof AdapterView.AdapterContextMenuInfo) {
            int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            ItemGroupViewModel item = getViewModel().get(position);
            if (item.getItemType() == ItemGroupViewModel.TYPE_ITEM) {
                menu.add(ImTabActivity.CONTEXT_MENU_TYPE_GROUP, position, 1, "修改分组");
            } else if (item.getItemType() == ItemGroupViewModel.TYPE_GROUP && item.isCustomGroup()) {
                menu.add(ImTabActivity.CONTEXT_MENU_TYPE_GROUP, position, 1, "管理");
                menu.add(ImTabActivity.CONTEXT_MENU_TYPE_GROUP, position, 2, "删除");
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getGroupId() == ImTabActivity.CONTEXT_MENU_TYPE_CONVERSATION) return false;
        int position = item.getItemId();
        final ItemGroupViewModel viewModel = getViewModel().get(position);
        if (viewModel.getItemType() == ItemGroupViewModel.TYPE_ITEM) {
            switch (item.getOrder()) {
                case 1://修改分组
                    GroupModifyActivity.editGroup(viewModel.getImId(), viewModel.getGroupId());
                    return true;
            }
        } else if (viewModel.getItemType() == ItemGroupViewModel.TYPE_GROUP) {
            switch (item.getOrder()) {
                case 1://管理
                    BuryingPointUtils.build(GroupListFragment.class, 4335).submitF();
                    GroupManageActivity.start(viewModel.getGroupId(), viewModel.getGroupNameStr());
                    return true;
                case 2://删除
                    new AlertDialog(getActivity()).builder().setTitle("确定要删除该分组")
                            .setMsg("删除后组员将进入默认分组")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    JsonObject params = new JsonObject();
                                    params.addProperty("ownerId", UserInfoManager.getImId());
                                    params.addProperty("groupId", viewModel.getGroupId());
                                    getPresenter().deleteUserGroup(TAG, params, new OnActionListener() {
                                        @Override
                                        public void onSuccess() {
                                            ToastUtil.showToast("删除分组成功");
                                            BuryingPointUtils.build(GroupListFragment.class, 4346).submitF();
                                            getPresenter().getInitPage(TAG);
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).show();
                    return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        ItemGroupViewModel item = getViewModel().get(position);
        if (item.getItemType() == ItemGroupViewModel.TYPE_ITEM) {
            ChatActivity.startChat(item.getImId());
        } else {
            BuryingPointUtils.build(GroupListFragment.class, 4020).addTag(item.getGroupName()).submitF();
            if (item.isExpand().get()) {
                getViewModel().removeRange(position + 1, item.getChildCount() + position + 1);
                item.setExpand(false);
            } else if (item.getItems().size() > 0) {
                getViewModel().addAll(position + 1, item.getItems());
                item.setExpand(true);
            }
        }
    }
}
