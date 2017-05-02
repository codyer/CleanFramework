package com.cody.app.business.mine;


import android.os.Bundle;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.FragmentMineBinding;
import com.cody.app.framework.fragment.WithHeaderFragment;
import com.cody.handler.framework.presenter.DefaultWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

/**
 * 个人
 */
public class MineFragment extends WithHeaderFragment<DefaultWithHeaderPresenter,WithHeaderViewModel,FragmentMineBinding> {

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle("个人");
        header.setVisible(true);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected DefaultWithHeaderPresenter buildPresenter() {
        return new DefaultWithHeaderPresenter();
    }

    @Override
    protected WithHeaderViewModel buildViewModel(Bundle savedInstanceState) {
        return new WithHeaderViewModel();
    }

    @Override
    public void onClick(View v) {

    }
}
