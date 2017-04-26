package com.cody.xf.widget.banner;

import android.view.View;

/**
 * Created by cody.yi on 2017/4/26.
 *
 * Interface definition for a callback to be invoked when an item in this
 * banner item has been clicked.
 */
public interface OnBannerClickListener {
    void onBannerItemClick(View view, int position, BannerViewModel viewModel);
}