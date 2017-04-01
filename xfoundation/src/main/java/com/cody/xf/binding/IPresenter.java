package com.cody.xf.binding;

import com.cody.xf.binding.handler.ViewModel;

/**
 * Created by cody.yi on 2016/8/5.
 * <p>
 * 负责实际业务处理
 * 可以通过tag取消业务处理
 * 需要在特定的PresenterImpl中进行取消方法的实现
 * <p>
 * Presenter是从ViewModel层抽取出来的一层，在实际应用中可以根据
 * 业务复杂程度删除这层。
 * <p>
 * Presenter和ViewModel，Binding，View相关，因此将其接口添加泛型支持
 * 在继承此类时，根据情况将ViewModel，Binding，View（Activity，Fragment）在合适的地方指定
 */
public interface IPresenter<VM extends ViewModel> {
    /**
     * 取消相关操作
     *
     * @param tag tag
     */
    void cancel(Object tag);

    /**
     * 建立关联
     *
     * @param view ui
     */
    void attachView(IView view, VM viewModel);

    /**
     * 取消关联，避免内存泄漏
     */
    void detachView();

    /**
     * 获得当前view
     *
     * @return IView
     */
    IView getView();

    /**
     * 获得当前viewModel
     *
     * @return VM
     */
    VM getViewModel();

    /**
     * 刷新界面，并不是必须调用的，如果用的是
     */
    void refreshUI(Object... args);

    /**
     * 判断是否和view有关联
     *
     * @return boolean
     */
    boolean isViewAttached();
}
