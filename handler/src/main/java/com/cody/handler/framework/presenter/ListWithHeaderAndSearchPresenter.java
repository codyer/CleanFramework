/*
 * Copyright (c)  Created by Cody.yi on 2016/9/14.
 */
package com.cody.handler.framework.presenter;


import com.cody.handler.framework.viewmodel.ListWithHeaderAndSearchViewModel;
import com.cody.handler.framework.viewmodel.ViewModel;

/**
 * Created by Cody.yi on 2016/9/14.
 * 使用默认的ListWithHeaderAndSearchViewModel
 * 通过ItemViewModel 获取列表
 */
public abstract class ListWithHeaderAndSearchPresenter<ItemViewModel extends ViewModel> extends
        AbsListPresenter<ListWithHeaderAndSearchViewModel<ItemViewModel>, ItemViewModel> {
}
