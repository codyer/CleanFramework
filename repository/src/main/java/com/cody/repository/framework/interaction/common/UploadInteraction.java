package com.cody.repository.framework.interaction.common;

import android.graphics.Bitmap;

import com.cody.repository.framework.Repository;
import com.cody.repository.framework.local.BaseLocalKey;
import com.cody.xf.utils.HttpUtil;
import com.cody.xf.utils.ImageUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by cody.yi on 2017/4/28.
 * 上传图片文件interaction
 */
public class UploadInteraction {

    private final static String NAME = "files"; // 与后台约定好的上传 form-data 的 name

    /**
     * 上传bitmap列表，成功后返回所有bitmap对应的url
     * @param clazz == String.class 大部分情况只是url的字符串
     */
    public <T> void uploadPictures(Object tag,
                                  String url,
                                  Map<String, String> params,
                                  List<Bitmap> bitmaps,
                                  Class<T> clazz,
                                  HttpUtil.Callback<List<T>> callback) {
        Map<String,String> token = Repository.getLocalMap(BaseLocalKey.HEADERS);
        HttpUtil.uploadImagesWithUrlsMultipart(tag,
                url,
                NAME,
                bitmaps,
                token,
                params,
                clazz,
                callback);
    }

    /**
     * 上传一个bitmap 返回clazz
     */
    public <T> void uploadPictures(Object tag,
                                  String url,
                                  Map<String, String> params,
                                  Bitmap bitmap,
                                  Class<T> clazz,
                                  HttpUtil.Callback<T> callback) {
        Map<String,String> token = Repository.getLocalMap(BaseLocalKey.HEADERS);
        HttpUtil.uploadImageMultipart(tag,
                url,
                NAME,
                bitmap,
                token,
                params,
                clazz,
                callback);
    }


    /**
     * 上传指定路径的一个bitmap 返回clazz
     */
    public <T> void uploadPictures(Object tag,
                                   String url,
                                   Map<String, String> params,
                                   String imgPath,
                                   int size,
                                   Class<T> clazz,
                                   HttpUtil.Callback<T> callback) {
        Bitmap bitmap = ImageUtil.getBitmap(imgPath,size,size);
        Map<String,String> token = Repository.getLocalMap(BaseLocalKey.HEADERS);
        HttpUtil.uploadImageMultipart(tag,
                url,
                NAME,
                bitmap,
                token,
                params,
                clazz,
                callback);
    }
}
