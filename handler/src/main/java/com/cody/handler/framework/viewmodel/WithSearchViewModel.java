/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.handler.framework.viewmodel;

/**
 * Created by cody.yi on 2016/8/27.
 * 包含搜索的ViewModel
 */
public class WithSearchViewModel extends ViewModel implements IWithSearchViewModel {
    private final SearchViewModel mSearchViewModel = SearchViewModel.createDefaultSearch();

    @Override
    public SearchViewModel getSearchViewModel() {
        return mSearchViewModel;
    }
}