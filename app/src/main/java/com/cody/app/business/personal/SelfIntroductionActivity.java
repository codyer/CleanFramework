/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.business.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.widget.dialog.AlertDialog;
import com.cody.app.R;
import com.cody.app.databinding.ActivitySelfIntroductionBinding;
import com.cody.handler.business.presenter.PersonalInformationPresenter;
 import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.handler.business.viewmodel.PersonalInformationViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;

/**
 * Created by cody.yi on 2016/8/9.
 * 自我介绍
 */
public class SelfIntroductionActivity extends WithHeaderActivity<PersonalInformationPresenter,
        PersonalInformationViewModel, ActivitySelfIntroductionBinding> {

    @NonNull
    @Override
    protected PersonalInformationViewModel buildViewModel(Bundle savedInstanceState) {
        PersonalInformationViewModel viewModel = new PersonalInformationViewModel();
        viewModel.setId(1);
        return viewModel;
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getString(R.string.self_introduction));
        header.setLeft(true);
        header.setRightIsText(true);
    }

    @NonNull
    @Override
    protected PersonalInformationPresenter buildPresenter() {
        return new PersonalInformationPresenter();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_self_introduction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().getUserInfo(TAG);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.headerRightText:
                final String selfIntroduction = getViewModel().getSelfIntroduction().get();
                new AlertDialog(SelfIntroductionActivity.this).builder().setTitle("自我介绍")
                        .setMsg("确定要将自我介绍改为‘" + selfIntroduction + "'吗？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getPresenter().modifySelfIntroduction(TAG);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
        }
    }

    @Override
    public void onUpdate(Object... args) {
        super.onUpdate(args);
        if (args != null && args.length > 0) {
            String introduction = getViewModel().getSelfIntroduction().get();
            Repository.setLocalValue(LocalKey.INTRODUCTION, introduction);
            setResult(RESULT_OK);
            finish();
        }
    }
}
