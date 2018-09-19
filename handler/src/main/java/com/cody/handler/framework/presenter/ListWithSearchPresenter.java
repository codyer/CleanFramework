/*
 * Copyright (c)  Created by Cody.yi on 2016/9/14.
 */
package com.cody.handler.framework.presenter;


import com.cody.handler.framework.viewmodel.ListWithSearchViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by Cody.yi on 2016/9/14.
 * 使用默认的ListWithSearchViewModel
 * 通过ItemViewModel 获取列表
 */
public abstract class ListWithSearchPresenter<ItemViewModel extends XItemViewModel> extends
        AbsListPresenter<ListWithSearchViewModel<ItemViewModel>, ItemViewModel> {
}
