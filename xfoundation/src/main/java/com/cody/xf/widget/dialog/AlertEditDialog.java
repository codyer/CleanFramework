/*
 * Copyright (c)  Created by Cody.yi on 2016/9/4.
 */

package com.cody.xf.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cody.xf.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chy on 8/29/16.
 * 弹出编辑框的dialog
 */
public class AlertEditDialog {
    InputFilter emojiFilter = new InputFilter() {

        Pattern emoji = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
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
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private TextView txt_msg;
    private EditText et_content;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;

    public AlertEditDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertEditDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.xf_view_edit_alertdialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        et_content = (EditText) view.findViewById(R.id.et_content);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        img_line = (ImageView) view.findViewById(R.id.img_line);

        InputFilter[] Emojfilters = {emojiFilter};
        et_content.setFilters(Emojfilters);

        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.xf_AlertDialogStyle);
        dialog.setContentView(view);
        if (context instanceof DialogInterface.OnCancelListener) {
            dialog.setOnCancelListener((DialogInterface.OnCancelListener) context);
        }

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    public AlertEditDialog setTitle(String title) {
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public AlertEditDialog setMaxLenth(int maxlength) {
        if (maxlength >= 0) {
            et_content.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxlength) });
        }
        return this;
    }

    public AlertEditDialog setEditHint(String msg) {
        if ("".equals(msg)) {
            et_content.setHint("内容");
        } else {
            et_content.setHint(msg);
        }
        return this;
    }

    public AlertEditDialog setEditText(String content) {
        if (!TextUtils.isEmpty(content)) {
            et_content.setText(content);
            et_content.setSelection(content.length());
        }
        return this;
    }

    public AlertEditDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }


    public AlertEditDialog setCompleteListener(final OnEditCompleteListener listener) {
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onComplete(et_content.getText().toString());
                dialog.dismiss();
            }
        });
        return this;
    }

    public void show() {
        dialog.show();
    }

    public interface OnEditCompleteListener {
        void onComplete(String result);
    }
}
