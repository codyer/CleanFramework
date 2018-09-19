package com.cody.app.business.block;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cody.app.R;
import com.cody.repository.business.bean.ShareBean;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ResourceUtil;
import com.cody.xf.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;


/**
 * 友盟分享
 */

public class ShareBlock {
    /**
     * attention to this below ,must add this
     * 已经写在父类了
     * UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
     * <p>
     * <p>UMShareAPI.get(this).release();
     * <p>
     * <p>
     * <p>
     * 屏幕横竖屏切换时避免出现window leak的问题
     * public void onConfigurationChanged(Configuration newConfig) {
     * super.onConfigurationChanged(newConfig);
     * mShareAction.close();
     * }
     */
    public static void share(Activity activity, ShareBean share, @Nullable ShareListener listener) {
        try {
            final UMWeb web = new UMWeb(share.getUrl());
            String imgUrl = share.getImgUrl();
            UMImage thumb = null;
            if (TextUtils.isEmpty(imgUrl)) {
                thumb = new UMImage(activity, R.mipmap.ic_launcher);
            } else {
                thumb = new UMImage(activity, imgUrl);
            }
            web.setTitle(TextUtils.isEmpty(share.getTitle()) ? "红星美凯龙-龙果" : share.getTitle());//标题
            web.setThumb(thumb);//缩略图
            web.setDescription(TextUtils.isEmpty(share.getDesc()) ? "龙果" : share.getDesc());//描述
            ShareBoardConfig shareBoardConfig = new ShareBoardConfig();
            shareBoardConfig.setCancelButtonTextColor(ResourceUtil.getColor(R.color.gray_333333));
            shareBoardConfig.setCancelButtonBackground(ResourceUtil.getColor(R.color.transparent));
            shareBoardConfig.setCancelButtonText(ResourceUtil.getString(R.string.cancel));
            shareBoardConfig.setMenuItemTextColor(ResourceUtil.getColor(R.color.gray_333333));
            shareBoardConfig.setShareboardBackgroundColor(ResourceUtil.getColor(R.color.main_white));
            shareBoardConfig.setIndicatorVisibility(false);
            shareBoardConfig.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
            shareBoardConfig.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
            shareBoardConfig.setTitleVisibility(false);
            new ShareAction(activity)
                    .setDisplayList(SHARE_MEDIA.WEIXIN,
                            SHARE_MEDIA.WEIXIN_CIRCLE,
                            SHARE_MEDIA.SINA,
                            SHARE_MEDIA.QQ,
                            SHARE_MEDIA.QZONE)
                    .withMedia(web)
                    .setCallback(new UMShareHandlerListener(activity, listener)).open(shareBoardConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class UMShareHandlerListener implements UMShareListener {
        final Activity mActivity;
        final ShareListener mListener;

        UMShareHandlerListener(Activity activity, ShareListener listener) {
            mActivity = activity;
            mListener = listener;
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            if (mListener != null)
                mListener.onStart(share_media);
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            if (mListener != null) {
                mListener.onResult(share_media);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (mListener != null) {
                mListener.onError(share_media, throwable);
            }
            if (throwable != null) {
                LogUtil.d(throwable.getMessage());
            }
            ToastUtil.showToast("分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            if (mListener != null)
                mListener.onCancel(share_media);
            ToastUtil.showToast("分享取消了");
        }
    }

    public interface ShareListener {
        void onStart(SHARE_MEDIA var1);

        void onResult(SHARE_MEDIA var1);

        void onError(SHARE_MEDIA var1, Throwable var2);

        void onCancel(SHARE_MEDIA var1);
    }
}

