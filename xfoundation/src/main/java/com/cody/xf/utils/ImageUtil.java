package com.cody.xf.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by cody.yi on 2016/7/25.
 * 图像工具
 */
public class ImageUtil {
    private static Context context;
    private static int id;

    /**
     * Turn drawable resource into byte array.
     *
     * @param context parent context
     * @param id      drawable resource id
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, int id) {
        ImageUtil.context = context;
        ImageUtil.id = id;
        Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Turn drawable into byte array.
     *
     * @param drawable data
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Turn drawable into byte array.
     *
     * @param bitmap bitmap
     * @return byte array
     */
    public static byte[] getFileDataFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    public static Bitmap compressBySize(String pathName) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
        // 得到图片的宽度、高度；
        int imgWidth = opts.outWidth;
        int imgHeight = opts.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) 500);
        int heightRatio = (int) Math.ceil(imgHeight / (float) 500);
        if (widthRatio > 1 || heightRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }
        // 设置好缩放比例后，加载图片进内容；
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(pathName, opts);
        return bitmap;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    public static byte[] getFileDataFromPath(String filePath) {
        return getFileDataFromPath(filePath, 800, 800);
    }

    public static byte[] getFileDataFromPath(String filePath, int reqWidth, int reqHeight) {
        Bitmap bitmap = getBitmap(filePath, reqWidth, reqHeight);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        /*if (!bitmap.isRecycled()) {
            bitmap.recycle();
            System.gc();
        }*/
        return byteArrayOutputStream.toByteArray();
    }

    // 根据路径获得图片，返回bitmap用于显示
    public static Bitmap getBitmap(String filePath) {
        return getBitmap(filePath, 800, 800);
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getBitmap(String filePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        if (reqWidth > 0 && reqHeight > 0) {
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;


        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }

        if (bm != null && bm != returnBm) {
            bm.recycle();
        }

        return returnBm;
    }

    public static Bitmap base64ToBitmap(String base64Str) {
        Bitmap bitmap = null;
        try {
            if (TextUtils.isEmpty(base64Str)) return null;
            byte[] data;
            if (base64Str.contains(",") && base64Str.split(",").length > 1) {
                data = Base64.decode(base64Str.split(",")[1], Base64.DEFAULT);
            } else {
                data = Base64.decode(base64Str, Base64.DEFAULT);
            }
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * base64编码字符集转化成图片文件。
     *
     * @return 是否成功
     */
    public static boolean base64ToFile(String base64Str, File file) {
        if (TextUtils.isEmpty(base64Str)) return false;
        byte[] data;
        if (base64Str.contains(",") && base64Str.split(",").length > 1) {
            data = Base64.decode(base64Str.split(",")[1], Base64.DEFAULT);
        } else {
            data = Base64.decode(base64Str, Base64.DEFAULT);
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
