/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.framework.viewmodel;

import android.databinding.ObservableField;

import com.cody.handler.R;
import com.cody.xf.utils.ResourceUtil;

public class SearchViewModel extends ViewModel {
    private final ObservableField<String> mKeyWord = new ObservableField<>("");
    private final ObservableField<String> mHint = new ObservableField<>(ResourceUtil.getString(R.string.xf_search_hint));

    private SearchViewModel() {
    }

    public static SearchViewModel createDefaultSearch() {
        return new SearchViewModel();
    }

    public ObservableField<String> getKeyWord() {
        return mKeyWord;
    }

    public ObservableField<String> getHint() {
        return mHint;
    }

    public void setKeyWord(String keyword){
        mKeyWord.set(keyword);
    }
}
