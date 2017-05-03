package com.cody.app.business.worksite;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cody.app.R;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.app.framework.fragment.ListFragment;
import com.cody.handler.business.presenter.TaskPresenter;
import com.cody.handler.business.viewmodel.TaskViewModel;

/**
 * 工地-进行中
 */
public class WorkGoingFragment extends ListFragment<TaskPresenter, TaskViewModel> {

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
    protected TaskPresenter buildPresenter() {
        return new TaskPresenter();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {

    }
}
