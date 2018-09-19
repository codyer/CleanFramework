package com.cody.app.business.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cody.app.R;
import com.cody.repository.business.bean.order.OrderQrCodeBean;
import com.cody.xf.utils.ScreenUtil;
import com.cody.xf.utils.ZXingUtils;

public class OrderQrCodePopWindow extends PopupWindow {

    private OrderQrCodeBean bean;
    private Bitmap bitmap;

    public OrderQrCodePopWindow(Context context, OrderQrCodeBean bean) {
        this.bean = bean;
        View view = initView(context);
        setContentView(view);
        setWidth(ScreenUtil.getScreenWidth(context));
        setHeight(ScreenUtil.getScreenHeight(context));
        setClippingEnabled(false);
        setFocusable(true);
        setOutsideTouchable(false);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (isShowing()) {
                        dismiss();
                    }
                }
                return false;
            }
        });
    }

    private View initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_qrcode, null);
        ImageView ivQrCode = (ImageView) view.findViewById(R.id.iv_qrcode);
        ImageView ivClose = (ImageView) view.findViewById(R.id.iv_close);
        TextView tvShopName = (TextView) view.findViewById(R.id.tv_shopName);
        TextView tvShopId = (TextView) view.findViewById(R.id.tv_shopId);
        tvShopName.setText(bean.getShopName());
        tvShopId.setText(bean.getShopId());


        try {
            bitmap = ZXingUtils.createQRCode1(bean.getQrString(), 500);
            ivQrCode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if(bitmap!=null&&!bitmap.isRecycled()){
                    bitmap.recycle();
                }
            }
        });
        return view;
    }

    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    //获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

}
