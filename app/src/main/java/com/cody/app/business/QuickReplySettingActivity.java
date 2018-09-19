package com.cody.app.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ListWithHeaderBinding;
import com.cody.app.framework.activity.ListWithHeaderBaseActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.presenter.QuickReplySettingPresenter;
import com.cody.handler.business.viewmodel.ItemQuickReplyViewModel;
import com.cody.handler.business.viewmodel.QuickReplySettingViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

public class QuickReplySettingActivity extends ListWithHeaderBaseActivity<QuickReplySettingPresenter,
        QuickReplySettingViewModel, ItemQuickReplyViewModel, ListWithHeaderBinding> {
    private static final int REQUEST_COED = 100;

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setVisible(true);
        header.setLeft(true);
        header.setTitle(getString(R.string.quick_reply_setting_title));
    }

    @Override
    protected BaseRecycleViewAdapter<ItemQuickReplyViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemQuickReplyViewModel>(getViewModel()) {
            @Override
            public int getItemLayoutId(int viewType) {
                if (viewType == 0) {
                    return R.layout.item_quick_reply_setting_system_welcome;
                } else {
                    return R.layout.item_quick_reply_setting_reply;
                }
            }
        };
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fw_list_with_header;
    }

    @Override
    protected QuickReplySettingPresenter buildPresenter() {
        return new QuickReplySettingPresenter();
    }

    @Override
    protected QuickReplySettingViewModel buildViewModel(Bundle savedInstanceState) {
        QuickReplySettingViewModel viewModel = new QuickReplySettingViewModel();
        viewModel.add(new ItemQuickReplyViewModel());
        viewModel.setHasEndInfo(false);
        return viewModel;
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        if (getViewModel().getReplyInfoBean() != null) {
            if (getViewModel().get(position).getItemType() == 0) {
                if (getViewModel().getReplyInfoBean().getGreetingReplyVoList() != null
                        && getViewModel().getReplyInfoBean().getGreetingReplyVoList().size() > 0) {
                    SystemGreetingActivity.startSystemGreeting(getViewModel()
                            .getReplyInfoBean().getGreetingReplyVoList().get(0), REQUEST_COED);
                }
            } else {
                if (getViewModel().getReplyInfoBean().getQuickReplyVoList() != null
                        && getViewModel().getReplyInfoBean().getQuickReplyVoList().size() > position - 1) {
                    QuickReplyActivity.startQuickReply(getViewModel()
                            .getReplyInfoBean().getQuickReplyVoList().get(position - 1), REQUEST_COED);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_COED) {
            onRefresh();
        }
    }
}
