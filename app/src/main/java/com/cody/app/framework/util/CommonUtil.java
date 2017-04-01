/*
 * Copyright (c)  Created by Cody.yi on 2016/8/29.
 */

package com.cody.app.framework.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by chy
 * 通用的一些工具类
 */
public class CommonUtil {

    /**
     * 验证有效是否有效
     *
     * @param strEmail 邮箱String
     * @return true 邮箱正确 false 邮箱有误
     */
    public static boolean isEmail(String strEmail) {
        String strPattern =
                "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 判断有效的验证码
     *
     * @param code 验证码
     * @return 判断结果
     */

    public static boolean isAuthCode(String code) {

/*        if (TextUtils.isEmpty(code)) {
            ToastUtils.getInstance().showFail(R.string.no_smsCode);
            return false;
        } else {
            if (code.length() == 6) {
                return true;
            } else {
                ToastUtils.getInstance().showFail(R.string.code_valid);
                return false;
            }
        }*/
        return false;
    }

    /**
     * 判断是否是有效手机号
     *
     * @param mobiles 要判断的手机号
     * @return 判断结果
     */
    public static boolean isMobileNumber(String mobiles) {
        //Pattern p = Pattern.compile("^((13[0-9])|(145)|(15[^7,\\D])|(18[0-2,5-9]))\\d{8}$");
       /* if (TextUtils.isEmpty(mobiles)) {
            ToastUtils.getInstance().showFail(R.string.mobile_no_empty);
            return false;
        } else {
            Pattern p =
                    Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            if (m.matches()) {
                return true;
            } else {
                ToastUtil.showFailure(R.string.mobile_valid);
                return false;
            }
        }*/
        return false;
    }

    //    @BindString(R.string.pwd_invalid)
    //    static String pwd_tip;

    /**
     * 密码同时包括数字和字母 ^[A-Za-z0-9]+$  [^a-zA-Z0-9]
     */
    public static boolean isAccordPassword(String password) {
       /* if (TextUtils.isEmpty(password)) {
            ToastUtil.showFailure(R.string.pwd_no_empty);
            return false;
        }
        if (password.length() < 6) {
            ToastUtil.showFailure(R.string.pwd_short);
            return false;
        }
        Pattern patrn = Pattern.compile("[^%&',;=?$\\x22]+");
        Matcher m = patrn.matcher(password);
        if (m.matches()) { //zhy 数字或者字母都可以作为密码
            return true;
        }
        ToastUtil.showFailure(R.string.pwd_valid);*/
        return false;
    }

    /**
     * 比较新旧密码
     *
     * @param pwdNew   新密码
     * @param pwdAgain 旧密码
     * @return 比较结果
     */
    public static boolean comparePWd(String pwdNew, String pwdAgain) {
        if (isAccordPassword(pwdNew)) {
            /*if (pwdNew.equals(pwdAgain)) {
                return true;
            } else {
                ToastUtils.getInstance().showShort(LongGuoApp.getContext().getString(R.string.pwd_error));
                return false;
            }*/
        } else {
            return false;
        }
        return false;
    }

    /**
     * 根据手机分辨率从dp转成px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return 返回转换后的px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f) - 15;
    }

    /**
     * 按钮连续点击（500毫秒内不能同时起效）
     */
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 过滤emoj表情符号
     *
     * @return 过滤后的结果
     */
    public static InputFilter getEmojFilter() {
        return new InputFilter() {

            Pattern emoji =
                    Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
                                       int dend) {

                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }

                return null;
            }
        };
    }

    /**
     * 禁止用户输入空格
     *
     * @return 返回InputFilter
     */
    public static InputFilter getSpaceFilter() {

        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
                                       int dend) {
                //返回null表示此字符可以输入,返回空字符串表示禁止输入此字符
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
    }

    /**
     * 调用系统应用打开文件
     *
     * @param context 上下文
     * @param file    文件地址
     */
    public static void openFile(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        //        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        //跳转
        try {
            context.startActivity(intent);
        } catch (Exception e) {
//            LogUtil.e("FileUtil", e);
            Toast.makeText(context, "找不到打开此文件的应用！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 生成二维码方法
     *
     * @param qrCodeString 二维码生成规则
     * @return 返回生成的二维码的Bitmap
     */

    public static Bitmap generateQRCode(String qrCodeString) {
        Bitmap bmp = null;    //二维码图片
        /*QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix =
                    writer.encode(qrCodeString, BarcodeFormat.QR_CODE, 512, 512); //参数分别表示为: 条码文本内容，条码格式，宽，高
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            //绘制每个像素
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(x, y)) {
                        bmp.setPixel(x, y, Color.BLACK);
                    } else {
                        bmp.setPixel(x, y, Color.WHITE);
                    }

                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }*/

        return bmp;
    }

    public static String getVersionName() {
        /*Context context = LongGuoApp.getContext();
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }*/
        return "";
    }

    /**
     * 判断输入的金额是否有效
     *
     * @param money 输入的金额
     * @return 返回金额是否有效
     */
    public static boolean isCashNum(String money) {
        /*if (TextUtils.isEmpty(money)) {
            ToastUtil.showFailure(R.string.enter_price);
            return false;
        }
        double money1 = Double.parseDouble(money);
        if (money1 == 0) {
            ToastUtil.showFailure(R.string.cash_zero_toast);
            return false;
        } else {
            return true;
        }*/
        return false;
    }
}
