package com.cody.app.framework.receiver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.cody.app.R;
import com.cody.xf.XFoundation;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JPushUtil {
    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }

    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static void showToast(final String toast, final Context context) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * 获取输入的alias
     */
    public static String getInPutAlias(String alias) {
        if (TextUtils.isEmpty(alias)) {
            Toast.makeText(XFoundation.getContext(), R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
            return null;
        }
        if (!JPushUtil.isValidTagAndAlias(alias)) {
            Toast.makeText(XFoundation.getContext(), R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
            return null;
        }
        return alias;
    }

    /**
     * 获取输入的tags
     */
    public static Set<String> getInPutTags(String tag) {
        // 检查 tag 的有效性
        if (TextUtils.isEmpty(tag)) {
            Toast.makeText(XFoundation.getContext(), R.string.error_tag_empty, Toast.LENGTH_SHORT).show();
            return null;
        }

        // ","隔开的多个 转换成 Set
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<String>();
        for (String sTagItme : sArray) {
            if (!JPushUtil.isValidTagAndAlias(sTagItme)) {
                Toast.makeText(XFoundation.getContext(), R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
                return null;
            }
            tagSet.add(sTagItme);
        }
        if (tagSet.isEmpty()) {
            Toast.makeText(XFoundation.getContext(), R.string.error_tag_empty, Toast.LENGTH_SHORT).show();
            return null;
        }
        return tagSet;
    }
}
