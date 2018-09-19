package com.cody.app.business;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.cody.app.R;
import com.cody.app.databinding.ActivityQuickReplyBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.handler.business.presenter.QuickReplyPresenter;
import com.cody.handler.business.viewmodel.QuickReplyViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.bean.im.ReplyInfoBean;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.widget.dialog.AlertEditDialog;

/**
 * Created by chen.huarong on 2017/9/14.
 * 快捷回复
 */
public class QuickReplyActivity extends WithHeaderActivity<QuickReplyPresenter
        , QuickReplyViewModel
        , ActivityQuickReplyBinding> {

    public static final String KEY_QUICK_REPLY_BEAN = "quick_reply_bean";
    private ReplyInfoBean.QuickReplyVoListBean mQuickReplyVoListBean;

    public static void startQuickReply(ReplyInfoBean.QuickReplyVoListBean bean, int requestCoed) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_QUICK_REPLY_BEAN, bean);
        ActivityUtil.navigateToForResult(QuickReplyActivity.class, requestCoed, bundle);
    }

    @Override
    public void finish() {
        if (getViewModel().isChanged()) {
            setResult(RESULT_OK);
        }
        super.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.headerRightText:
                getPresenter().saveOrUpdateQuickReply(TAG);
                break;
            case R.id.quick_reply_title:
                new AlertEditDialog(this).builder()
                        .setTitle("标签")
                        .setEditHint("请输入新的快捷回复语标签")
                        .setMaxLenth(20)
                        .setEditText(getViewModel().getTitle().get().trim())
                        .setCompleteListener(new AlertEditDialog.OnEditCompleteListener() {
                            @Override
                            public void onComplete(String result) {
                                getViewModel().setTitle(result.trim());
                            }
                        }).show();
                break;
        }
    }

    @Override
    public void onUpdate(Object... args) {
        super.onUpdate(args);
        if (args != null
                && args.length > 0) {
            if (TAG == args[0]) {
                finish();
            }
        }
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setVisible(true);
        header.setLeft(true);
        header.setTitle(getString(R.string.quick_reply_title));
        header.setRight(true);
        header.setRightIsText(true);
        header.setRightText(getString(R.string.save_now));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().switchOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getPresenter().switchOpen(TAG, b);
            }
        });
        getPresenter().mapper(mQuickReplyVoListBean);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_quick_reply;
    }

    @Override
    protected QuickReplyPresenter buildPresenter() {
        return new QuickReplyPresenter();
    }

    @Override
    protected QuickReplyViewModel buildViewModel(Bundle savedInstanceState) {
        QuickReplyViewModel viewModel = new QuickReplyViewModel();
        mQuickReplyVoListBean = (ReplyInfoBean.QuickReplyVoListBean) getIntent()
                .getSerializableExtra(KEY_QUICK_REPLY_BEAN);
        viewModel.getEnable().set(mQuickReplyVoListBean.getEnable() == 1);
        return new QuickReplyViewModel();
    }
}
