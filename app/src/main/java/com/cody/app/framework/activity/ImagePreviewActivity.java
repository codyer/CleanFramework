package com.cody.app.framework.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ImagePreviewActivity extends com.lzy.imagepicker.ui.ImagePreviewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnOk = (Button) topBar.findViewById(com.lzy.imagepicker.R.id.btn_ok);
        btnOk.setVisibility(View.INVISIBLE);
        View bottomBar = findViewById(com.lzy.imagepicker.R.id.bottom_bar);
        bottomBar.setVisibility(View.INVISIBLE);
    }

    /**
     * 单击时，隐藏头和尾
     */
    @Override
    public void onImageSingleTap() {
        onBackPressed();
    }
}
