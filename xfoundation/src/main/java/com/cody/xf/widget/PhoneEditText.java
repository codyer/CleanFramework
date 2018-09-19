package com.cody.xf.widget;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Emcy-fu ;
 * on data:  2018/7/26 ;
 * 344 格式电话号码
 */

public class PhoneEditText extends ClearEditText implements TextWatcher {

    private static final int PHONE_INDEX_3 = 3;
    private static final int PHONE_INDEX_4 = 4;
    private static final int PHONE_INDEX_8 = 8;
    private static final int PHONE_INDEX_9 = 9;


    public PhoneEditText(Context context) {
        super(context);
    }

    public PhoneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhoneEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        super.onTextChanged(s, start, count, after);
        if (s == null || s.length() == 0) {
            return;
        }
        boolean check = false;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                if (i < 1) {
                    s = "";
                } else {
                    s = s.subSequence(0, i);
                }
                check = true;
            }else {
                break;
            }
        }
        if (check){
            setText(s);
            setSelection(s.length());
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != PHONE_INDEX_3 && i != PHONE_INDEX_8 && s.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == PHONE_INDEX_4 || sb.length() == PHONE_INDEX_9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        if (!sb.toString().equals(s.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == ' ') {
                if (count == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (count == 1) {
                    index--;
                }
            }
            setText(sb.toString());
            setSelection(index);
        }
    }

    // 获得不包含空格的手机号
    public String getPhoneText() {
        String str = getText().toString();
        return replaceBlank(str);
    }

    private String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            if (m.find()) {
                dest = m.replaceAll("");
            }
        }
        return dest;
    }

}
