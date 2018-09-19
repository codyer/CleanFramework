package com.cody.xf.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import com.cody.xf.XFoundation;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by cody.yi on 2016/7/29.
 * 通用库
 * 1、获取类型 {@link #getType(Class, Type...)}
 * 2、拼接get请求的url地址 {@link #buildPathUrl(String, Map)}
 * 3、restful接口地址字段替换 {@link #buildRestFulPathUrl(String, Map)}
 * 4、下载文件是否可用{@link #isDownloadManagerAvailable}
 */
public class CommonUtil {
    private static final double BASE = 1024, KB = BASE, MB = KB * BASE, GB = MB * BASE, TB = GB * BASE;
    private static final DecimalFormat df = new DecimalFormat("#.##");
    //    private final static String HEX = "0123456789ABCDEF";
    private final static String AES_KEY = "wZTc98PWEMqqCSCs";
    private final static String AES_IV = "0102030405060708";
    /**
     * 按钮连续点击（500毫秒内不能同时起效）
     */
    private static long lastClickTime;
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    //Original source: https://stackoverflow.com/a/9855338/1389357
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] getKey(String value) {
        byte[] key = new byte[64];
        byte[] b = value.getBytes();
        for (int i = 0; i < 64; i++) {
            key[i] = b[i % b.length];
        }
        return key;
    }
    /**
     * 重构List成String
     *
     * @return "a,b,c"
     */
    public static String reBuildString(List<String> list) {
        String str = "";
        if (list == null || list.size() == 0) return str;
        for (int i = 0; i < list.size() - 1; i++) {
            str += list.get(i) + ",";
        }
        str += list.get(list.size() - 1);
        return str;
    }

    public static String Encrypt(String sSrc) {
        byte[] raw = AES_KEY.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher;// "算法/模式/补码方式"
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | InvalidAlgorithmParameterException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatSize(double size) {
        if (size >= TB) {
            return df.format(size / TB) + " TB";
        }
        if (size >= GB) {
            return df.format(size / GB) + " GB";
        }
        if (size >= MB) {
            return df.format(size / MB) + " MB";
        }
        if (size >= KB) {
            return df.format(size / KB) + " KB";
        }
        return "" + (int) size + " bytes";
    }

    /**
     * 获取版本号
     */
    public static String getVersionName() {
        Context context = XFoundation.getContext();
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取组合类类型
     */
    public static ParameterizedType getType(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    /**
     * 参数拼接都url后面
     * @param url base url
     * @param params 参数
     */
    public static  String formatUrl(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.size() > 0) {
            HashMap<String, String> map = new HashMap<>();
            for (String key : params.keySet()) {
                if (key == null || params.get(key) == null) {
                    continue;
                }
                String value = params.get(key);
                try {
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                map.put(key, value);
            }
            if (!sb.toString().contains("?")) {
                sb.append("?");
            }
            if (!sb.toString().endsWith("?")) {
                sb.append("&");
            }
            int position = 0;
            for (String key : map.keySet()) {
                sb.append(key).append("=").append(map.get(key));
                if (position < map.size() - 1) {
                    sb.append("&");
                }
                position++;
            }
        }
        return sb.toString();
    }

    /**
     * 拼接get请求的url地址
     *
     * @param url    原地址
     * @param params 参数
     * @return 拼接完成的地址
     */
    public static String buildPathUrl(String url, Map<String, String> params) {
        if (params.size() > 0 && !TextUtils.isEmpty(url)) {
            //restful
            if (url.contains("{")) {
                url = buildRestFulPathUrl(url, params);
            }
            url += url.contains("?") ? "&" : "?";
            StringBuilder encodedParams = new StringBuilder();
            try {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    encodedParams.append('&');
                }
                url += encodedParams.toString();
                url = url.substring(0, url.length() - 1);
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: " + "UTF-8", uee);
            }
        }
        return url;
    }

    /**
     * restful接口地址字段替换
     *
     * @param url    包含大括号的地址
     * @param params 需要替换的参数
     * @return 真实的请求地址
     */
    public static String buildRestFulPathUrl(String url, Map<String, String> params) {
        for (Iterator<Map.Entry<String, String>> it = params.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> item = it.next();
            if (url.contains(item.getKey())) {
                url = url.replace("{" + item.getKey() + "}", item.getValue());
                it.remove();
            }
        }
        return url;
    }

    /**
     * @return true if the download manager is available
     */
    public static boolean isDownloadManagerAvailable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * 正则表达式验证名称
     *
     * @param name 名称
     * @return true 正确 false 有误
     */
    public static boolean isUserName(String name) {
        // 昵称格式：限16个字符，支持中英文、数字、减号或下划线
        if (TextUtils.isEmpty(name)) return false;
        String regStr = "^[\\u4e00-\\u9fa5_a-zA-Z0-9-]{1,16}$";
        return name.matches(regStr);
    }

    /**
     * 正则表达式验证信息
     *
     * @param info 信息
     * @return true 正确 false 有误
     */
    public static boolean checkUserInfo(String info) {
        // 信息格式：支持中英文、数字
        if (TextUtils.isEmpty(info)) return true;
        String regStr = "^[A-Za-z0-9_\\u4E00-\\u9FA5]+$";
        return info.matches(regStr);
    }

    /**
     * 正则表达式验证身份证
     *
     * @param name 名称
     * @return true 正确 false 有误
     */
    public static boolean isIdentityCard(String name) {
        // 昵称格式：限16个字符，支持中英文、数字、减号或下划线
        if (TextUtils.isEmpty(name)) return false;
        if (name.length() == 15) {
            String id15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
            return name.matches(id15);
        } else if (name.length() == 18) {
            String id18 = "^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$";
            return name.matches(id18);
        }
        return false;
    }

    /**
     * 验证有效是否有效
     *
     * @param strEmail 邮箱String
     * @return true 邮箱正确 false 邮箱有误
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)" +
                "+[\\w](?:[\\w-]*[\\w])?";
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
        return !TextUtils.isEmpty(code) && code.length() == 6;
    }

    /**
     * 判断是否是有效手机号
     *
     * @param mobiles 要判断的手机号
     * @return 判断结果
     */
    public static boolean isMobileNumber(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        }
    }

    /**
     * 用正则表达式替换手机号为星号*
     */
    public static String hideMobile(String mobile) {
        if (isMobileNumber(mobile)) {
            return mobile.replaceAll("(?<=\\d{3})\\d(?=\\d{3})", "*");
        }
        if (TextUtils.isEmpty(mobile)) {
            return "*";
        }
        return mobile;
    }

    /**
     * 密码必须包括（数字、字母、特殊字符）至少两种，且长度大于6
     */
    public static boolean isAccordPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        if (password.length() < 6) {
            return false;
        }
        if (password.matches("^\\d+$")) {
            return false;
        }

        int symbol = 0;
        int numbers = 0;
        int letter = 0;

        for (String pw : password.split("")) {
            if (pw.matches("[!@#$%&*()_+-=]")) {
                symbol = 1;
                continue;
            }
            if (pw.matches("[0-9]")) {
                numbers = 1;
                continue;
            }
            if (pw.matches("[a-zA-Z]")) {
                letter = 1;
            }
        }

        return symbol + numbers + letter > 1;
    }

    /**
     * 比较新旧密码
     *
     * @param pwd1 新密码
     * @param pwd2 旧密码
     * @return 比较结果
     */
    public static boolean isSamePassword(String pwd1, String pwd2) {
        return isAccordPassword(pwd1) && isAccordPassword(pwd2) && pwd1.equals(pwd2);
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

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 过滤emo表情符号
     *
     * @return 过滤后的结果
     */
    public static InputFilter getEmoFilter() {
        return new InputFilter() {
            Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart,
                                       int dEnd) {

                Matcher filter = p.matcher(source);
                if (filter.find()) {
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
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart,
                                       int dEnd) {
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

    /**
     * 判断输入的金额是否有效
     *
     * @param money 输入的金额
     * @return 返回金额是否有效
     */
    public static boolean isCashNum(String money) {
        if (TextUtils.isEmpty(money)) {
            return false;
        }
        double money1 = Double.parseDouble(money);
        return money1 != 0;
    }

    public static String getUUID() {
        String serial = null;

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 把list集合转换成以某种字符(如：，)拼接的字符串
     *
     * @param list 集合
     * @param c    拼接符
     * @return 字符串
     */
    public static String formatList(List list, String c) {
        String code = "";
        if (list == null || list.size() <= 0) return code;

        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                code += list.get(i);
            } else {
                code += list.get(i) + c;
            }
        }
        return code;
    }

    public static boolean isNotSameApp(Context context) {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(context, pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            LogUtil.e("enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return true;
        }
        return false;
    }

    private static String getAppName(Context context, int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return null;
    }
}
