package com.cody.xf.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cody.xf.R;


/**
 * Created by liuqiang on 10/23/15.
 * 提示框
 */
public class AlertDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private TextView txt_msg;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public AlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.xf_view_alertdialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

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

    public AlertDialog setTitle(String title) {
        showTitle = true;
        if (TextUtils.isEmpty(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public AlertDialog setMsg(String msg) {
        showMsg = true;
        if (TextUtils.isEmpty(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public AlertDialog setMsgGravity(int gravity) {
        if (txt_msg != null) {
            txt_msg.setGravity(gravity);
        }
        return this;
    }

    public AlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        dialog.setOnKeyListener(onKeyListener);
    }

    public Context getContext() {
        return context;
    }

    public AlertDialog setPositiveButton(String text,
                                         final View.OnClickListener listener) {
        showPosBtn = true;
        if (TextUtils.isEmpty(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertDialog setNegativeButton(String text,
                                         final View.OnClickListener listener) {
        showNegBtn = true;
        if (TextUtils.isEmpty(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.xf_alert_dialog_single_selector);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.xf_alert_dialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.xf_alert_dialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.xf_alert_dialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.xf_alert_dialog_single_selector);
        }
    }

    public AlertDialog setPositiveButtonColor(int colorId) {
        if (btn_pos != null) {
            btn_pos.setTextColor(ContextCompat.getColor(getContext(), colorId));
        }
        return this;
    }

    public AlertDialog setNegativeButtonColor(int colorId) {
        if (btn_neg != null) {
            btn_neg.setTextColor(ContextCompat.getColor(getContext(), colorId));
        }
        return this;
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void cancel() {
        if (dialog.isShowing()) {
            dialog.cancel();
        }
    }

    public void disMiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
