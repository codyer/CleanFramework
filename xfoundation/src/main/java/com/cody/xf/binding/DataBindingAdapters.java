package com.cody.xf.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.StringUtil;

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


    @BindingAdapter(value = {"rectImageUrl", "error", "placeholder"}, requireAll = false)
    public static void setRectImageUrl(ImageView view, String rectImageUrl, Drawable error, Drawable placeholder) {
        Context context = view.getContext();
        if (StringUtil.isNotEmpty(rectImageUrl) && rectImageUrl.startsWith("http")) {
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

    @BindingAdapter(value = {"badImageUrl", "error", "placeholder"}, requireAll = false)
    public static void setBadImageUrl(ImageView view, String badImageUrl, Drawable error, Drawable placeholder) {
        Context context = view.getContext();
        LogUtil.d("setBadImageUrl = " + badImageUrl);
        Glide.with(context)
                .load(badImageUrl)      //设置图片路径
                .error(error)           //设置错误图片
                .placeholder(placeholder)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }
}
