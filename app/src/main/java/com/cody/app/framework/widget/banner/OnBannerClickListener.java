package com.cody.app.framework.widget.banner;

import android.view.View;

import com.cody.handler.framework.viewmodel.BannerViewModel;

/**
 * Created by cody.yi on 2017/4/26.
 * <p>
 * Interface definition for a callback to be invoked when an item in this
 * banner item has been clicked.
 */
public interface OnBannerClickListener {
    void onBannerItemClick(View view, int position, BannerViewModel viewModel);
}