/*
 * Copyright (c)  Created by Cody.yi on 2016/9/14.
 */
package com.cody.handler.framework.presenter;


import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.handler.framework.viewmodel.ViewModel;

/**
 * Created by chy on 2016/9/14.
 * 使用默认的ListViewModel
 * 通过ItemViewModel 获取列表
 */
public abstract class ListPresenter<ItemViewModel extends ViewModel> extends
        AbsListPresenter<ListViewModel<ItemViewModel>, ItemViewModel> {
}
