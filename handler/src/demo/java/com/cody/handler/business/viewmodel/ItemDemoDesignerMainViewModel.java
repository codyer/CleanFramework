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

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by cody.yi on 2017/7/20.
 * some useful information
 */
public class ItemDemoDesignerMainViewModel extends XItemViewModel {
    private ItemDemoDesignerTopViewModel mTopItem;
    private ItemDemoDesignerNearlyViewModel mNearlyItem;

    public ItemDemoDesignerTopViewModel getTopItem() {
        return mTopItem;
    }

    public void setTopItem(ItemDemoDesignerTopViewModel topItem) {
        mTopItem = topItem;
    }

    public ItemDemoDesignerNearlyViewModel getNearlyItem() {
        return mNearlyItem;
    }

    public void setNearlyItem(ItemDemoDesignerNearlyViewModel nearlyItem) {
        mNearlyItem = nearlyItem;
    }
}
