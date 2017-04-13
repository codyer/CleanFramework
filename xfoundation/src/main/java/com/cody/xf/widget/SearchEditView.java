/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.xf.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.cody.xf.R;


/**
 * Created by chen_haiyan on 2016/5/4.
 * 搜索框自动清空 hint 键盘添加搜索按钮
 */
public class SearchEditView extends ClearEditText implements View.OnKeyListener {

    /**
     * 是否点击软键盘搜索
     */
    private boolean pressSearch = false;
    /**
     * 软键盘搜索键监听
     */
    private OnSearchClickListener listener;

    public SearchEditView(Context context) {
        this(context, null);
    }

    public SearchEditView(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public SearchEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init() {
        setOnKeyListener(this);
        setSingleLine();
        setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.xf_ic_search, 0, 0, 0);
        this.setCompoundDrawablePadding(20);
        this.setPadding(40, 10, 20, 10);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        pressSearch = (keyCode == KeyEvent.KEYCODE_ENTER);
        if (pressSearch && listener != null) {
            /*隐藏软键盘*/
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
            listener.onSearchClick(v);
        }
        return false;
    }


    public void setOnSearchClickListener(OnSearchClickListener listener) {
        this.listener = listener;
    }

    public interface OnSearchClickListener {
        void onSearchClick(View view);
    }
}
