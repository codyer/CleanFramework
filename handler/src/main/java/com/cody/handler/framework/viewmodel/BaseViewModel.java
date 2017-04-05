/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.handler.framework.viewmodel;

/**
 * Created by cody.yi on 2016/8/11.
 * 继承foundation中的ViewModel，统一添加ID
 * 需要异步显示的变量可以使用Observable类型，否则应该优先使用正常类型
 */
public class BaseViewModel extends ViewModel {

    /**
     * 可以用来做key键
     */
    private Object mId;

    public Object getId() {
        if (mId == null) {
            mId = new Object();
        }
        return mId;
    }

    public void setId(Object id) {
        mId = id;
    }

/**
 * 界面控制变量
 * 控制界面某个view显示或者不显示，一般为ObservableBoolean 类型的变量
 */

/**
 * 界面直接显示变量
 * 变量的值直接显示在界面上，如果是一开始就确定的显示值（不需要异步网络获取的）可以定义为简单的
 * 变量，（一般为String类型，,图片比较特殊，设置的是url地址)，否则应该定义为Observable的变量，
 * 否则需要在界面数据返回后进行ViewModel重设才能促使界面更新（现阶段使用的是这种模式，应该重构）
 */

/**
 * 界面间接显示变量
 * 变量的值通过在get函数中进行一定的转换间接显示在界面上
 * 这种变量尽量不用，应该在mapper的时候就已经进行了转换
 */
}
