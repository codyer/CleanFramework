package com.cody.app.business.easeui.domain;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cody.app.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class ImageLoaderHelper {
    private static ImageLoaderHelper instance;

    public static ImageLoaderHelper getInstance() {
        if (instance == null) {
            synchronized (ImageLoaderHelper.class) {
                if (instance == null) {
                    instance = new ImageLoaderHelper();
                }
            }
        }
        return instance;
    }

    public void displayImage(Context context,Integer resourceId, ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .error(R.drawable.ic_touxiang)
                .placeholder(R.drawable.ic_touxiang)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }


    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .error(R.drawable.ic_touxiang)
                .placeholder(R.drawable.ic_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    public void displayImage2(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .error(R.drawable.icon_pic)
                .placeholder(R.drawable.icon_pic)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    public void displayImage(Context context, String path, ImageView imageView,int placeholderId) {
        Glide.with(context)
                .load(path)
                .error(placeholderId)
                .placeholder(placeholderId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


    public void displayImage(Context context, String path, ImageView imageView, int width, int height) {
        Glide.with(context)
                .load(path)
                .override(width, height)
                .error(R.mipmap.default_image)
                .placeholder(R.mipmap.default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

    }


}
