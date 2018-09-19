package com.cody.app.business.widget;

/**
 * Created by  qiaoping.xiao on 2017/10/10.
 */

public interface PopItemEditCallBack<T> {

    void goToOtherActivity(T dataBean);

    void onMessageSend();

}
