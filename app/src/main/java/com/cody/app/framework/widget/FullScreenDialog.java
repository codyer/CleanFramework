package com.cody.app.framework.widget;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.cody.app.BR;
import com.cody.app.R;
import com.cody.xf.utils.LogUtil;

/**
 * Created by cody.yi on 2016/9/26.
 * 自定义dialog
 */
public class FullScreenDialog extends Dialog implements View.OnClickListener {

    @LayoutRes
    private int mLayoutId = R.layout.dialog_agent_guide_rob_order;
    private View.OnClickListener mOnClickListener;
    private Activity mContext;

    public FullScreenDialog(Activity context) {
        super(context, R.style.Dialog_FullScreen);
        mContext = context;
    }

    public FullScreenDialog setLayoutId(@LayoutRes int layoutId) {
        mLayoutId = layoutId;
        return this;
    }

    public FullScreenDialog setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(mContext, mLayoutId, null);
        setContentView(view);
        ViewDataBinding binding = DataBindingUtil.bind(view);
        if (binding != null) {
            binding.setVariable(BR.onClickListener, this);
        }
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View v) {
        LogUtil.d("FullScreenDialog onClick " + v.toString());
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }
    }
}
