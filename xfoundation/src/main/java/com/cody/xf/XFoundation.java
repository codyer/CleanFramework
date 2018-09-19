package com.cody.xf;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.view.CropImageView;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.LocationUtil;
import com.cody.xf.utils.LogUtil;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/**
 * Created by cody.yi on 2017/4/1.
 * 基础框架
 */
public class XFoundation {
    private static XFoundation sInstance;
    private Reference<XFApplication> mAppRef;

    private XFoundation(XFApplication application) {
        mAppRef = new SoftReference<>(application);
        initImagePicker();
    }

    private static XFoundation getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("You should call Foundation.install() in you application first!");
        } else {
            return sInstance;
        }
    }

    public static void install(XFApplication application) {
        sInstance = new XFoundation(application);
        ActivityUtil.install();
        LocationUtil.install();
    }

    public static void uninstall() {
        sInstance = null;
        ActivityUtil.uninstall();
        LocationUtil.uninstall();
    }

    /**
     * 获取系统上下文
     *
     * @return 系统上下文
     */
    public static XFApplication getApp() {
        return getInstance().getXFApp();
    }

    /**
     * 获取系统上下文
     *
     * @return 系统上下文
     */
    public static Context getContext() {
        return getInstance().getXFApp().getApplicationContext();
    }

    private XFApplication getXFApp() {
        if (mAppRef == null || mAppRef.get() == null) {
            throw new NullPointerException("You should call Foundation.install() in you application first!");
        } else {
            return mAppRef.get();
        }
    }

    /**
     * 初始化imagePicker
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(9);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(800);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(800);                         //保存文件的高度。单位像素
    }

    private final class GlideImageLoader implements ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, ImageView imageView, int width,
                                 int height) {
            if (TextUtils.isEmpty(path)) return;
            if (path.startsWith("http")) {
                Glide.with(activity)                             //配置上下文
                        .load(path)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .error(R.drawable.xf_ic_placeholder_square)           //设置错误图片
                        .placeholder(R.drawable.xf_ic_placeholder_square)     //设置占位图片
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                        .into(imageView);
            } else {
                Glide.with(activity)                             //配置上下文
                        .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .error(R.drawable.xf_ic_placeholder_square)           //设置错误图片
                        .placeholder(R.drawable.xf_ic_placeholder_square)     //设置占位图片
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                        .into(imageView);
            }
            LogUtil.d("displayImage = " + path);
        }

        @Override
        public void clearMemoryCache() {
        }
    }
}
