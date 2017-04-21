/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.framework.viewmodel;

/**
 * Created by cody.yi on 2016/8/24.
 *
 * 不包含头部的list view
 *
 * @param <ItemViewModel> ListView中的item ViewModel
 */
public class ListWithSearchViewModel<ItemViewModel extends ViewModel> extends ListViewModel<ItemViewModel> {
    private final SearchViewModel mSearchViewModel = SearchViewModel.createDefaultSearch();

    public SearchViewModel getSearchViewModel() {
        return mSearchViewModel;
    }
}
