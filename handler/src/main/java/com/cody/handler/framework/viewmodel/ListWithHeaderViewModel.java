/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.framework.viewmodel;

/**
 * Created by cody.yi on 2016/8/24.
 * <p>
 * 包含头部的list view
 *
 * @param <ItemViewModel> ListView中的item ViewModel
 */
public class ListWithHeaderViewModel<ItemViewModel extends XItemViewModel> extends ListViewModel<ItemViewModel>
        implements IWithHeaderViewModel {
    private final HeaderViewModel mHeaderViewModel = HeaderViewModel.createDefaultHeader();//list view的头部

    @Override
    public HeaderViewModel getHeaderViewModel() {
        return mHeaderViewModel;
    }
}
