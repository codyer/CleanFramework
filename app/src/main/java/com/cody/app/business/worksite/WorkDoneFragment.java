package com.cody.app.business.worksite;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cody.app.R;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.app.framework.fragment.ListFragment;
import com.cody.handler.business.presenter.TaskPresenter;
import com.cody.handler.business.viewmodel.TaskViewModel;
import com.cody.handler.framework.viewmodel.ListWithHeaderViewModel;

/**
 * 工地-完成
 */
public class WorkDoneFragment extends ListFragment<TaskPresenter, TaskViewModel> {

    @Override
    protected BaseRecycleViewAdapter<TaskViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<TaskViewModel>(getViewModel().getItemViewModels()) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_task;
            }
        };
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.fw_empty_view;
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {

    }

    @Override
    protected TaskPresenter buildPresenter() {
        return new TaskPresenter();
    }

    @Override
    protected ListWithHeaderViewModel<TaskViewModel> buildViewModel(Bundle savedInstanceState) {
        return new ListWithHeaderViewModel<>();
    }

    @Override
    public void onClick(View v) {

    }
}
