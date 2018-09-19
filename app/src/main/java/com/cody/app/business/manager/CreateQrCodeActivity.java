/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.business.manager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivityCreateQrCodeBinding;
import com.cody.handler.business.presenter.CommonHeaderPresenter;
 import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.handler.business.viewmodel.CommonHeaderViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.app.utils.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建二维码界面
 */
public class CreateQrCodeActivity extends WithHeaderActivity<CommonHeaderPresenter, CommonHeaderViewModel,
        ActivityCreateQrCodeBinding> {
    String mContent;
    private String mOfferId;
    private String mTitle;

    @NonNull
    @Override
    protected CommonHeaderViewModel buildViewModel(Bundle savedInstanceState) {
        CommonHeaderViewModel viewModel = new CommonHeaderViewModel();
        viewModel.setId(1);
        return new CommonHeaderViewModel();
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getString(R.string.create_qr));
        header.setLeft(true);
    }

    @NonNull
    @Override
    protected CommonHeaderPresenter buildPresenter() {
        return new CommonHeaderPresenter();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_create_qr_code;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            mContent = getIntent().getExtras().getString("Content");
            mTitle = getIntent().getExtras().getString("title");
            Bitmap qrCode = CommonUtil.generateQRCode(mContent);
            getBinding().qrBitmap.setImageBitmap(qrCode);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(mContent);
                mOfferId = jsonObject.getString("pramKey1");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
