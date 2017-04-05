package com.cody.handler.framework;

/**
 * @author codyer .
 *         base activity and base fragment 需要实现的接口
 *         16/8/22.
 */
public interface IDataBinding<P, VM, B> {

    boolean isBound();

    P getPresenter();

    VM getViewModel();

    B getBinding();

}
