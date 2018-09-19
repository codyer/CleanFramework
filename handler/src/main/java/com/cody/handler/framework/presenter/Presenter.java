package com.cody.handler.framework.presenter;

import com.cody.handler.framework.IView;
import com.cody.handler.framework.viewmodel.IViewModel;
import com.cody.xf.utils.HttpUtil;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

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
public class Presenter<VM extends IViewModel> implements IPresenter<VM> {
    private Reference<IView> mViewRef;
    private Reference<VM> mViewModelRef;

    @Override
    public void cancel(Object tag) {
        HttpUtil.cancel(tag);
    }

    @Override
    public void attachView(Object tag, IView view, VM viewModel) {
        mViewRef = new WeakReference<>(view);
        mViewModelRef = new WeakReference<>(viewModel);
    }

    @Override
    public void detachView(Object tag) {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    @Override
    public IView getView() {
        if (isViewAttached()) {
            return mViewRef.get();
        }
        return null;
    }

    @Override
    public VM getViewModel() {
        if (isViewAttached()) {
            return mViewModelRef.get();
        }
        return null;
    }

    /**
     * 刷新界面，并不是必须调用的，如果ViewModel不是observable变量需要调用强制刷新，否则不需要调用，框架会自动刷新
     */
    @Override
    public void refreshUI(Object... args) {
        if (isViewAttached()) {
            mViewRef.get().onUpdate(args);
        }
    }

    @Override
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }
}
