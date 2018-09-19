package com.cody.app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.cody.xf.utils.ImageUtil;
import com.cody.xf.utils.ToastUtil;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by cody.yi on 2018/8/22.
 * 保存图片到本地
 */
public class ImageCacheUtil extends AsyncTask<String, Void, File> {
    private final WeakReference<Context> mContext;

    public ImageCacheUtil(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    @Override
    protected File doInBackground(String... params) {
        String base64String = params[0];
        File file;
        try {
            // 首先保存图片
            File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();

            File dir = new File(pictureFolder, "picture");
            if (!dir.exists()) {
               boolean exist = dir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            file = new File(dir, fileName);

            //最后一步就是复制文件咯
            boolean result = ImageUtil.base64ToFile(base64String, file);
            if (!result)return null;
            // 最后通知图库更新
            mContext.get().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(file.getPath()))));
        } catch (Exception ex) {
            return null;
        }
        return file;
    }

    @Override
    protected void onPostExecute(File result) {
        if (result != null) {
            ToastUtil.showToast("保存成功："+result.getAbsolutePath());
        }else {
            ToastUtil.showToast("保存失败");
        }
    }
}
