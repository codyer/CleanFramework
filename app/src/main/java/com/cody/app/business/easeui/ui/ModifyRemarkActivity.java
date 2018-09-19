package com.cody.app.business.easeui.ui;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.cody.app.R;
import com.cody.app.databinding.ActivityRemarkBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.handler.business.presenter.ModifyRemarkPresenter;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.business.viewmodel.ModifyRemarkViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;

/**
 * Create by jiquan.zhong  on 2018/7/30.
 * description:
 */
public class ModifyRemarkActivity extends WithHeaderActivity<ModifyRemarkPresenter, ModifyRemarkViewModel, ActivityRemarkBinding> {
    private String target;
    private String mOldRemark;

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getString(R.string.modify_remark));
        header.setLeft(true);
        header.setRightIsText(true);
        header.setRight(true);
        header.setRightText(getString(R.string.confirm));
        header.setRightColorId(getResources().getColor(R.color.main_blue));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_remark;
    }

    @Override
    protected ModifyRemarkPresenter buildPresenter() {
        return new ModifyRemarkPresenter();
    }

    @Override
    protected ModifyRemarkViewModel buildViewModel(Bundle savedInstanceState) {
        return new ModifyRemarkViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        target = getIntent().getStringExtra("user");
        mOldRemark = getIntent().getStringExtra("remark");
        if (!TextUtils.isEmpty(mOldRemark)) {
            getViewModel().getRemark().set(mOldRemark);
            getBinding().edittext.post(new Runnable() {
                @Override
                public void run() {
                    getBinding().edittext.setSelection(getBinding().edittext.getText().length());
                }
            });
        }
        getBinding().edittext.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        getBinding().edittext.setImeOptions(EditorInfo.IME_ACTION_DONE);
        getBinding().edittext.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        for (int i = start; i < end; i++) {
                            if (!Character.isLetterOrDigit(source.charAt(i))) {
                                return "";
                            }
                        }
                        return null;
                    }
                }, new InputFilter.LengthFilter(10)});
        getBinding().edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    modifyImRemark();
                    return true;
                }
                return false;
            }
        });
    }

    private void modifyImRemark() {
        if (TextUtils.equals(mOldRemark, getViewModel().getRemark().get())){
            finish();
            return;
        }
        getPresenter().modifyImRemark(TAG, target, new OnActionListener() {
            @Override
            public void onSuccess() {
                hideLoading();
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headerRightText:
                modifyImRemark();
                break;
            case R.id.headerLeftBtn:
                finish();
                break;
        }
    }
}
