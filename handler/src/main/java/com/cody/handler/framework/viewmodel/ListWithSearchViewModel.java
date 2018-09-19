/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.framework.viewmodel;

/**
 * Created by cody.yi on 2016/8/24.
 * <p>
 * 不包含头部的list view
 *
 * @param <ItemViewModel> ListView中的item ViewModel
 */
public class ListWithSearchViewModel<ItemViewModel extends XItemViewModel> extends ListViewModel<ItemViewModel> implements IWithSearchViewModel {
    private final SearchViewModel mSearchViewModel = SearchViewModel.createDefaultSearch();

    @Override
    public SearchViewModel getSearchViewModel() {
        return mSearchViewModel;
    }
}
