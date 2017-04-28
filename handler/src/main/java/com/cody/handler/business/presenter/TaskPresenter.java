/*
 * Copyright (c)  Created by Cody.yi on 2016/9/14.
 */
package com.cody.handler.business.presenter;


import android.support.annotation.NonNull;

import com.cody.handler.business.viewmodel.TaskViewModel;
import com.cody.handler.framework.presenter.ListPresenter;

import java.util.Map;

/**
 * Created by Cody.yi on 2017/4/128.
 * 任务
 */
public class TaskPresenter extends ListPresenter<TaskViewModel> {
    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {

        // TODO debug
        for (int i = 0; i < 50; i++) {
            TaskViewModel item = new TaskViewModel();
            item.setId("test-"+i);
            getViewModel().add(item);
        }
        refreshUI();
    }
}
