package com.cody.app.framework.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cody.app.BR;
import com.cody.handler.framework.IDataBinding;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.handler.framework.viewmodel.IViewModel;


/**
 * MVVM架构的基类，将ViewModel的属性和行为进行拆分，行为交由P处理，属性由VM持有
 *
 * @param <P>  处理逻辑的类，从ViewModel拆出来的行为
 * @param <VM> 所有ViewModel中原来的属性；
 * @param <B>  和V（XML）进行绑定的自动生成的类，可以通过data节点添加class自定义binding的类名
 */
public abstract class BaseBindingFragment<P extends Presenter<VM>,
        VM extends IViewModel,
        B extends ViewDataBinding>
        extends BaseLazyFragment
        implements View.OnClickListener, IDataBinding<P, VM, B> {

    private P mPresenter;
    private VM mViewModel;
    private B mBinding;

    /**
     * 子类提供有binding的资源ID
     */
    @LayoutRes
    protected abstract int getLayoutID();

    /**
     * 每个view保证只有一个Presenter
     */
    protected abstract P buildPresenter();

    /**
     * 每个view保证只有一个ViewModel，当包含其他ViewModel时使用根ViewModel包含子ViewModel
     * ViewModel可以在创建的时候进行初始化，也可以在正在进行绑定（#setBinding）的时候初始化
     */
    protected abstract VM buildViewModel(Bundle savedInstanceState);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        /**
         * 绑定view
         */
        if (!isBound()) {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false);
            mViewModel = buildViewModel(savedInstanceState);
            mPresenter = buildPresenter();
            if (mPresenter != null && mViewModel != null) {
                mPresenter.attachView(TAG, this, mViewModel);
            }
        }

        if (mBinding != null && mViewModel != null) {
            mBinding.setVariable(BR.viewModel, mViewModel);
            mBinding.setVariable(BR.onClickListener, this);
            return mBinding.getRoot();
        }

        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter = null;
        mViewModel = null;
        mBinding = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mPresenter != null && isBound()) {
            mPresenter.attachView(TAG, this, mViewModel);
        }
    }

    @Override
    public void onDetach() {
        if (mPresenter != null) {
            mPresenter.cancel(TAG);
            mPresenter.detachView(TAG);
        }
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.cancel(TAG);
            mPresenter.detachView(TAG);
        }
        super.onDestroy();
    }

    @CallSuper
    @Override
    public void onUpdate(Object... args) {
        super.onUpdate(args);
        if (mBinding != null && mViewModel != null) {
            mBinding.setVariable(BR.viewModel, mViewModel);
        }
    }

    @CallSuper
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mPresenter != null) {
            mPresenter.cancel(TAG);
        }
    }

    @Override
    public VM getViewModel() {
        if (mViewModel == null) {
            throw new NullPointerException("You should setViewModel first!");
        }
        return mViewModel;
    }

    @Override
    public B getBinding() {
        if (mBinding == null) {
            throw new NullPointerException("You should setBinding first!");
        }
        return mBinding;
    }

    @Override
    public P getPresenter() {
        if (this.mPresenter == null) {
            throw new NullPointerException("You should createPresenter first!");
        }
        return mPresenter;
    }

    /**
     * 是否已经设置bind
     */
    @Override
    public boolean isBound() {
        return mBinding != null;
    }

}
