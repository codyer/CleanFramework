package com.cody.xf.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by chen.huarong on 2018/5/17.
 */
public class FileUtils {

    public static final int FILE_TYPE_NO_TYPE = -1;
    public static final int FILE_TYPE_PDF = 0;
    public static final int FILE_TYPE_XLSX = 1;
    public static final int FILE_TYPE_DOC = 2;
    public static final int FILE_TYPE_PPT = 3;

    /**
     * 获取文件类型
     *
     * @param filePath
     * @return
     */
    public static int getFileType(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return FILE_TYPE_NO_TYPE;
        }
        filePath = filePath.toLowerCase();
        if (filePath.endsWith(".xlsx")) {
            return FILE_TYPE_XLSX;
        }

        if (filePath.endsWith(".pdf")) {
            return FILE_TYPE_PDF;
        }

        if (filePath.endsWith(".doc")) {
            return FILE_TYPE_DOC;
        }

        if (filePath.endsWith(".ppt")) {
            return FILE_TYPE_PPT;
        }
        return FILE_TYPE_NO_TYPE;
    }

    public static String toFile(byte[] bfile, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
//int len = bfile.length;
            file = new File(fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                    bos = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] getBytes(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 根据URL下载文件,前提是这个文件当中的内容是文本,函数的返回值就是文本当中的内容
     * 1.创建一个URL对象
     * 2.通过URL对象,创建一个HttpURLConnection对象
     * 3.得到InputStream
     * 4.从InputStream当中读取数据
     *
     * @param urlStr
     * @return
     */
    public static String download(String urlStr) {
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader buffer = null;
        URL url = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String SDPATH = URIUtil.getAppFIlePath();

    public static int FILESIZE = 4 * 1024;

    public static String getSDPATH() {
        return SDPATH;
    }

    /**
     * 在SD卡上创建文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File createSDFile(String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     * @return
     */
    public static File createSDDir(String dirName) {
        File dir = new File(dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     *
     * @param path
     * @param fileName
     * @param input
     * @param isGotye
     * @return
     */
    public static File write2SDFromInput(String path, String fileName, InputStream input, boolean isGotye) {
        File file = null;
        OutputStream output = null;
        try {
            createSDDir(path);
            file = createSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[FILESIZE];

            /*真机测试，这段可能有问题，请采用下面网友提供的
                            while((input.read(buffer)) != -1){
                output.write(buffer);
            }
                            */

            /* 网友提供 begin */
            int length;
            if (isGotye) {
                String header = "#!AMR\n";
                byte[] bytes = header.getBytes();
                output.write(bytes);
            }
            while ((length = (input.read(buffer))) > 0) {
                output.write(buffer, 0, length);
            }
            /* 网友提供 end */

            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     *
     * @param path
     * @param fileName
     * @param input
     * @return
     */
    public static File write2SDFromInput(String path, String fileName, InputStream input, String header) {
        File file = null;
        OutputStream output = null;
        try {
            createSDDir(path);
            file = createSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[FILESIZE];

            /*真机测试，这段可能有问题，请采用下面网友提供的
                            while((input.read(buffer)) != -1){
                output.write(buffer);
            }
                            */

            /* 网友提供 begin */
            int length;
//            String header  = "#!AMR\n";
//            byte[] bytes = header.getBytes();
//            output.write(bytes);
            while ((length = (input.read(buffer))) > 0) {
                output.write(buffer, 0, length);
            }
            /* 网友提供 end */

            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * @param urlStr
     * @param path
     * @param fileName
     * @return -1:文件下载出错
     * 0:文件下载成功
     * 1:文件已经存在
     */
    public static int downFile(String urlStr, String path, String fileName, boolean isGotye) {
        try {

            if (isFileExist(path + fileName)) {
                return 1;
            } else {
                InputStream inputStream = getInputStreamFromURL(urlStr);
                File resultFile = write2SDFromInput(path, fileName, inputStream, isGotye);
                if (resultFile == null) {
                    return -1;
                }
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    /**
     * @param urlStr
     * @param path
     * @param fileName
     * @return -1:文件下载出错
     * 0:文件下载成功
     * 1:文件已经存在
     */
    public static int downFileAddHeader(String urlStr, String path, String fileName, String header) {
        InputStream inputStream;
        try {

            if (isFileExist(path + fileName)) {
                return 1;
            } else {
                inputStream = getInputStreamFromURL(urlStr);
                File resultFile = write2SDFromInput(path, fileName, inputStream, header);
                if (resultFile == null) {
                    return -1;
                }
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    /**
     * 根据URL得到输入流
     *
     * @param urlStr
     * @return
     */
    public static InputStream getInputStreamFromURL(String urlStr) {
        HttpURLConnection urlConn = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(urlStr);
            urlConn = (HttpURLConnection) url.openConnection();
            inputStream = urlConn.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }

    /**
     * 打开文件
     * 兼容7.0
     *
     * @param context activity
     * @param file    File
     */
    public static void startActionFile(Context context, File file) {
        startActionFile(context, file, getMimeType(file.getPath()));
    }

    /**
     * 打开文件
     * 兼容7.0
     *
     * @param context     activity
     * @param file        File
     * @param contentType 文件类型如：文本（text/html）
     *                    当手机中没有一个app可以打开file时会抛ActivityNotFoundException
     */
    public static void startActionFile(Context context, File file, String contentType) throws
            ActivityNotFoundException {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//增加读写权限
        intent.setDataAndType(getUriForFile(context, file), contentType);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 打开相机
     * 兼容7.0
     *
     * @param activity    Activity
     * @param file        File
     * @param requestCode result requestCode
     */
    public static void startActionCapture(Activity activity, File file, int requestCode) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file));
        activity.startActivityForResult(intent, requestCode);
    }

    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context
                    , context.getPackageName() + ".file_provider", file);
        } else {
            uri = Uri.fromFile(file);
        }

        return uri;
    }

    private static String getMimeType(String filePath) {
        String ext = MimeTypeMap.getFileExtensionFromUrl(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
    }

    // 保存图片到手机
    public static void download(final Context context, final String base64String) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    // 首先保存图片
                    File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();

                    File appDir = new File(pictureFolder, "picture");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File destFile = new File(appDir, fileName);

                    ImageUtil.base64ToFile(base64String, destFile);

                    // 最后通知图库更新
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(destFile.getPath()))));
                } catch (Exception e) {
                    LogUtil.e(e.getMessage());
                }
            }
        }).start();
    }

    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    public static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(fileInputStream);
            closeStream(fileOutputStream);
        }
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
