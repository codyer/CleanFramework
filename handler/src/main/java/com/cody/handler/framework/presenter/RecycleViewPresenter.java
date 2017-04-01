/*
 * Copyright (c)  Created by Cody.yi on 2016/9/14.
 */
package com.cody.handler.framework.presenter;


import com.cody.handler.framework.viewmodel.BaseViewModel;
import com.cody.handler.framework.viewmodel.common.ListViewModel;

/**
 * Created by chy on 2016/9/14.
 * 使用默认的ListViewModel
 * 通过ItemViewModel 获取列表
 */
public abstract class RecycleViewPresenter<ItemViewModel extends BaseViewModel> extends
        AbstractRecycleViewPresenter<ListViewModel<ItemViewModel>,ItemViewModel> {
}
