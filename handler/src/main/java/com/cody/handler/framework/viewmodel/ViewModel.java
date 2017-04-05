package com.cody.handler.framework.viewmodel;

import java.io.Serializable;

/**
 * Created by cody.yi on 2016/8/4.
 * view model只是视图模型，每个view对应一个视图模型
 * 复杂的View通过组合嵌套的方式实现
 */
public abstract class ViewModel implements Serializable {
    static final long serialVersionUID = 1L;
}
