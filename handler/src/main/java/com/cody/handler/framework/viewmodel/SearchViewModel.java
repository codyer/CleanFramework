/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.framework.viewmodel;

import android.databinding.ObservableField;

import com.cody.handler.R;
import com.cody.xf.utils.ResourceUtil;

public class SearchViewModel extends ViewModel {
    public final ObservableField<String> keyWord = new ObservableField<>("");
    public final ObservableField<String> hint = new ObservableField<>(ResourceUtil.getString(R.string.xf_search_hint));

    private SearchViewModel() {
    }

    public static SearchViewModel createDefaultSearch() {
        return new SearchViewModel();
    }
}
