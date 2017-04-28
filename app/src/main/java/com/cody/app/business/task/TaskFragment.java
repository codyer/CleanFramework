package com.cody.app.business.task;


import android.support.v4.app.Fragment;

import com.cody.app.R;
import com.cody.app.framework.fragment.WithChildTabPageAndHeaderFragment;
import com.cody.handler.framework.presenter.DefaultWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务
 */
public class TaskFragment extends WithChildTabPageAndHeaderFragment<DefaultWithHeaderPresenter> {

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle("任务");
        header.setVisible(true);
    }

    /**
     * 每个view保证只有一个Presenter
     */
    @Override
    protected DefaultWithHeaderPresenter buildPresenter() {
        return new DefaultWithHeaderPresenter();
    }

    @Override
    protected int getChildTabTitles() {
        return R.array.task_tab;
    }

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TaskWeekFragment());
        fragments.add(new TaskTodayFragment());
        return fragments;
    }

}
