package com.cody.repository.framework.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.DeviceUtil;
import com.cody.xf.utils.LogUtil;

import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by cody.yi on 2016/7/26.
 * 图片加载绑定
 */
public class DataBindingAdapters {

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter({"gifSrc"})
    public static void setGifSrc(ImageView view, int gif) {
        Glide.with(view.getContext()).load(gif).asGif().diskCacheStrategy(DiskCacheStrategy.NONE).into(view);
    }

    @BindingAdapter(value = {"imageUrl", "error", "placeholder"}, requireAll = false)
    public static void setImageUrl(ImageView view, String imageUrl, Drawable error, Drawable placeholder) {
        Context context = view.getContext();
        //考虑C端有第三方授权登录，所以头像由后台统一处理
/*        if (StringUtil.isNotEmpty(imageUrl) && imageUrl.startsWith("http")) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (imageUrl.endsWith("!")) {
                imageUrl = imageUrl.substring(0, imageUrl.length() - 1);
            }
            imageUrl = imageUrl + "." + (layoutParams.width > 0 ? layoutParams.width : view.getWidth()) + "x" +
                    (layoutParams.height > 0 ? layoutParams.height : view.getHeight()) + ".jpg!";//头像加感叹号去掉水印
        }*/
        LogUtil.d("setImageUrl = " + imageUrl);
        Glide.with(context)
                .load(imageUrl)      //设置图片路径
                .error(error)           //设置错误图片
                .placeholder(placeholder)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(view);
    }

    //添加兼容resourceId的error和placeholder
    @BindingAdapter(value = {"imageUrl", "error", "placeholder"}, requireAll = false)
    public static void setImageUrl(ImageView view, String imageUrl, int errorResourceId, int placeholderResourceId) {
        Context context = view.getContext();
        //考虑C端有第三方授权登录，所以头像由后台统一处理
/*        if (StringUtil.isNotEmpty(imageUrl) && imageUrl.startsWith("http")) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (imageUrl.endsWith("!")) {
                imageUrl = imageUrl.substring(0, imageUrl.length() - 1);
            }
            imageUrl = imageUrl + "." + (layoutParams.width > 0 ? layoutParams.width : view.getWidth()) + "x" +
                    (layoutParams.height > 0 ? layoutParams.height : view.getHeight()) + ".jpg!";//头像加感叹号去掉水印
        }*/
        LogUtil.d("setImageUrl = " + imageUrl);
        Glide.with(context)
                .load(imageUrl)      //设置图片路径
                .error(errorResourceId)           //设置错误图片
                .placeholder(placeholderResourceId)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(view);
    }
    @BindingAdapter(value = {"rectImageUrl", "error", "placeholder"}, requireAll = false)
    public static void setRectImageUrl(ImageView view, String rectImageUrl, Drawable error, Drawable placeholder) {
        Context context = view.getContext();
        if (!TextUtils.isEmpty(rectImageUrl) && rectImageUrl.startsWith("http")) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            //现在后台服务器会配置头像的后缀为.jpeg!
            if (rectImageUrl.endsWith("!")) {
                rectImageUrl = rectImageUrl.substring(0, rectImageUrl.length() - 1);
            }
            int width = layoutParams.width > 0 ? layoutParams.width : view.getWidth();
            int height = layoutParams.height > 0 ? layoutParams.height : view.getHeight();
            if (width > 0 && height > 0 && !rectImageUrl.endsWith("jpeg!")) {
                rectImageUrl = rectImageUrl + "." + width + "x" + height + ".jpg!";//头像加感叹号去掉水印
            } else {
                rectImageUrl = rectImageUrl + "!";//头像加感叹号去掉水印
            }
        }
        LogUtil.d("setRectImageUrl = " + rectImageUrl);
        Glide.with(context)
                .load(rectImageUrl)      //设置图片路径
                .error(error)           //设置错误图片
                .placeholder(placeholder)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    @BindingAdapter(value = {"resizeImageUrl", "error", "placeholder"}, requireAll = false)
    public static void setResizeImageUrl(ImageView view, String rectImageUrl, Drawable error, Drawable placeholder) {
        Context context = view.getContext();
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        LogUtil.d("resizeImageUrl = " + rectImageUrl);
        Glide.with(context)
                .load(rectImageUrl)      //设置图片路径
                .error(error)           //设置错误图片
                .placeholder(placeholder)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new GlideDrawableImageViewTarget(view) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable>
                            animation) {
                        super.onResourceReady(resource, animation);
                        int width = resource.getIntrinsicWidth();
                        int height = resource.getIntrinsicHeight();
                        if (width != 0
                                && height != 0) {
                            int maxWH = DeviceUtil.dip2px(view.getContext(), 160);
                            float ratio = ((float) width) / ((float) height);
                            if (width > maxWH || height > maxWH) {
                                if (width > height) {
                                    width = maxWH;
                                    height = (int) (width / ratio);
                                } else {
                                    height = maxWH;
                                    width = (int) (height * ratio);
                                }
                            }
                            if (width != 0
                                    && height != 0) {
                                layoutParams.width = width;
                                layoutParams.height = height;
                            }
                            LogUtil.d("setResizeImageUrl: width = " + width + "|| height = " + height);
                        }
                    }
                });
    }

    /**
     * Url携带了尺寸信息
     */
    @BindingAdapter(value = {"badImageUrl", "error", "placeholder"}, requireAll = false)
    public static void setBadImageUrl(ImageView view, String badImageUrl, Drawable error, Drawable placeholder) {
        Context context = view.getContext();
        LogUtil.d("setBadImageUrl = " + badImageUrl);
        Glide.with(context)
                .load(badImageUrl)//设置图片路径
                .error(error)           //设置错误图片
                .placeholder(placeholder)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }
	
    @BindingAdapter(value = {"imageCodeUrl", "error", "placeholder"}, requireAll = false)
    public static void setImageCodeUrl(final ImageView view, String imageCodeUrl, Drawable error, Drawable placeholder) {
        if (TextUtils.isEmpty(imageCodeUrl)) return;
        Context context = view.getContext();
        GlideUrl glideUrl = null;
        final Map<String, String> headers = Repository.getLocalMap(LocalKey.X_TOKEN);
        LogUtil.d("setImageCodeUrl = " + imageCodeUrl);

        if (imageCodeUrl.startsWith("http") && (headers != null)) {
            glideUrl = new GlideUrl(imageCodeUrl, new Headers() {
                @Override
                public Map<String, String> getHeaders() {
                    return headers;
                }
            });
        }

        Glide.with(context)
                .load(glideUrl == null ? imageCodeUrl : glideUrl)      //设置图片路径
                .error(error)           //设置错误图片
                .placeholder(placeholder)     //设置占位图片
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view);
    }

    @BindingAdapter("android:layout_width")
    public static void setLayoutWidth(View view, float width) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) width;
        view.setLayoutParams(params);
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View view, float height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) height;
        view.setLayoutParams(params);
    }
}
