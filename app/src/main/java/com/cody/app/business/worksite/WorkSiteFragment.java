package com.cody.app.business.worksite;


import android.support.v4.app.Fragment;

import com.cody.app.R;
import com.cody.app.framework.fragment.WithChildTabPageAndHeaderFragment;
import com.cody.handler.framework.presenter.DefaultWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 工地
 */
public class WorkSiteFragment extends WithChildTabPageAndHeaderFragment<DefaultWithHeaderPresenter> {

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle("工地");
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
        return R.array.work_site_tab;
    }

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new WorkGoingFragment());
        fragments.add(new WorkDoneFragment());
        return fragments;
    }

}
