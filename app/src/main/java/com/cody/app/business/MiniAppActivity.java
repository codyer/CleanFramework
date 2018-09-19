package com.cody.app.business;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivityMiniAppBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.app.utils.ImageCacheUtil;
import com.cody.handler.business.presenter.MiniAppPresenter;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.business.viewmodel.MiniAppViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ImageUtil;
import com.cody.xf.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;

/**
 * 微信小程序二维码
 */
public class MiniAppActivity extends WithHeaderActivity<MiniAppPresenter, MiniAppViewModel, ActivityMiniAppBinding> {

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setLeft(true);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_mini_app;
    }

    @Override
    protected MiniAppPresenter buildPresenter() {
        return new MiniAppPresenter();
    }

    @Override
    protected MiniAppViewModel buildViewModel(Bundle savedInstanceState) {
        return new MiniAppViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().getWxQrCode(TAG, new OnActionListener() {
            @Override
            public void onSuccess() {
                Bitmap bm = ImageUtil.base64ToBitmap(getViewModel().getMiniAppUrl());
                if (bm != null) {
                    getBinding().qrCode.setImageBitmap(bm);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.shareMiniApp:
                BuryingPointUtils.build(MiniAppActivity.class,4226).submitF();
                getPresenter().getShopDetail(TAG, new OnActionListener() {
                    @Override
                    public void onSuccess() {
                        shareMiniApp();
                    }
                });
                break;
            case R.id.saveToAlbum:
                BuryingPointUtils.build(MiniAppActivity.class,4227).submitF();
                new ImageCacheUtil(this).execute(getViewModel().getMiniAppUrl());
                break;
        }
    }

    private void shareMiniApp() {
        //兼容低版本的网页链接
        UMMin umMin = new UMMin(getViewModel().getWapUrl());
        // 小程序消息封面图片
        umMin.setThumb(new UMImage(this, getViewModel().getShopPic()));
        // 小程序消息title
        umMin.setTitle(getViewModel().getShopName());
        // 小程序消息描述
        umMin.setDescription(getViewModel().getShopAddress());
        //小程序页面路径
        umMin.setPath(getViewModel().getMiniPage());
        // 小程序原始id,在微信平台查询
        umMin.setUserName(getViewModel().getGid());
        new ShareAction(this)
                .withMedia(umMin)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(new UMShareListener() {

                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ToastUtil.showToast(R.string.share_mini_app_success);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        if (throwable != null) {
                            ToastUtil.showToast(throwable.getMessage());
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtil.showToast(R.string.share_mini_app_cancel);
                    }
                }).share();
    }
}
