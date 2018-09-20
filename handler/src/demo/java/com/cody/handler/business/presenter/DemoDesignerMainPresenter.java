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

package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;

import com.cody.handler.business.viewmodel.DemoDesignerMainViewModel;
import com.cody.handler.business.viewmodel.ItemDemoDesignerMainViewModel;
import com.cody.handler.business.viewmodel.ItemDemoDesignerNearlyViewModel;
import com.cody.handler.business.viewmodel.ItemDemoDesignerTopViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.AbsListPresenter;
import com.cody.repository.framework.MockRepository;
import com.cody.xf.utils.http.SimpleBean;

import java.util.Map;

/**
 * Created by cody.yi on 2017/7/20.
 * some useful information
 */
public class DemoDesignerMainPresenter extends AbsListPresenter<DemoDesignerMainViewModel, ItemDemoDesignerMainViewModel> {

    public DemoDesignerMainPresenter() {
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        MockRepository.getData(com.cody.xf.R.raw.xf_simple_bean, SimpleBean.class, new DefaultCallback<SimpleBean>(this) {
            @Override
            public void onSuccess(SimpleBean bean) {
                super.onSuccess(bean);
                for (int i = 0; i < 20; i++) {
                    ItemDemoDesignerMainViewModel viewModel = new ItemDemoDesignerMainViewModel();
                    if (i == 0) {
                        ItemDemoDesignerTopViewModel topItem = new ItemDemoDesignerTopViewModel();
                        for (int j = 0; j < 5; j++) {
                            ItemDemoDesignerNearlyViewModel item = new ItemDemoDesignerNearlyViewModel();
                            topItem.add(item);
                        }
                        topItem.setCursor(1);
                        topItem.setHasEndInfo(false);
                        viewModel.setTopItem(topItem);
                    } else {
                        ItemDemoDesignerNearlyViewModel nearlyItem = new ItemDemoDesignerNearlyViewModel();
                        viewModel.setNearlyItem(nearlyItem);
                    }
                    getViewModel().add(viewModel);
                }
                getViewModel().setHasMore(getViewModel().size() < 60);
                refreshUI();
            }
        });
    }
}
