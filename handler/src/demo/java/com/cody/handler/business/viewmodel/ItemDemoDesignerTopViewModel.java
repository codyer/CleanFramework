/*
 * Copyright (c) 2017.   Cody.yi Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cody.handler.business.viewmodel;

import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.ListViewModel;

/**
 * Created by cody.yi on 2017/7/20.
 * some useful information
 */
public class ItemDemoDesignerTopViewModel extends ListViewModel<ItemDemoDesignerNearlyViewModel> {
    private final ObservableField<String> mCursor = new ObservableField<>("1/5");

    public void setCursor(int current) {
        if (current > size()) current = size();
        mCursor.set(current + "/" + size());
    }

    public ObservableField<String> getCursor() {
        return mCursor;
    }
}
