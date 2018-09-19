/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.framework.viewmodel;

/**
 * Created by cody.yi on 2016/8/24.
 * <p>
 * 包含头部和搜索的list view
 *
 * @param <ItemViewModel> ListView中的item ViewModel
 */
public class ListWithHeaderAndSearchViewModel<ItemViewModel extends XItemViewModel> extends ListViewModel<ItemViewModel>
        implements IWithHeaderViewModel, IWithSearchViewModel {
    private final HeaderViewModel mHeaderViewModel = HeaderViewModel.createDefaultHeader();//list view的头部
    private final SearchViewModel mSearchViewModel = SearchViewModel.createDefaultSearch();

    @Override
    public HeaderViewModel getHeaderViewModel() {
        return mHeaderViewModel;
    }

    @Override
    public SearchViewModel getSearchViewModel() {
        return mSearchViewModel;
    }
}
