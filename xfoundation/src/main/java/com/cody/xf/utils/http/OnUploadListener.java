package com.cody.xf.utils.http;

/**
 * Created by cody.yi on 2017/6/1.
 * 上传进度
 */
public interface OnUploadListener {
    void onProgress(int progress, int max);
}
