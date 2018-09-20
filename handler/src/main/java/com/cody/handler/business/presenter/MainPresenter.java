package com.cody.handler.business.presenter;

import com.cody.handler.business.viewmodel.MainViewModel;
import com.cody.handler.framework.IView;
import com.cody.handler.framework.presenter.Presenter;

/**
 * Created by cody.yi on 2018/7/18.
 * 首页
 */
public class MainPresenter extends Presenter<MainViewModel> {

    public MainPresenter() {
    }

    @Override
    public void attachView(Object tag, IView view, MainViewModel viewModel) {
        super.attachView(tag, view, viewModel);
    }

    @Override
    public void detachView(Object tag) {
        super.detachView(tag);
    }
}
