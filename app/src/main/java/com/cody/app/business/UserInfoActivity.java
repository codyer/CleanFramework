package com.cody.app.business;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.cody.app.R;
import com.cody.app.business.im.ChatHistoryActivity;
import com.cody.app.business.im.GroupModifyActivity;
import com.cody.app.databinding.ActivityUserInfoBinding;
import com.cody.app.business.easeui.ui.ModifyRemarkActivity;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.business.presenter.UserInfoPresenter;
import com.cody.handler.business.viewmodel.UserInfoViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.widget.dialog.AlertDialog;

/**
 * Create by jiquan.zhong  on 2018/7/30.
 * description:客户信息
 */
public class UserInfoActivity extends WithHeaderActivity<UserInfoPresenter, UserInfoViewModel, ActivityUserInfoBinding> {
    private final static int REQUEST_CODE_REMARK = 0;

    private static final String KEY_USER_IM_ID = "USER_IM_ID";
    private static final String KEY_REMARK = "REMARK";
    private String mOldRemark = "";

    public static void startUserInfo(String imId) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USER_IM_ID, imId);
        ActivityUtil.navigateTo(UserInfoActivity.class, bundle);
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setVisible(true);
        header.setLeft(true);
        header.setRightText(getString(R.string.save_now));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_user_info;
    }

    @Override
    protected UserInfoPresenter buildPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    protected UserInfoViewModel buildViewModel(Bundle savedInstanceState) {
        String cImId = getIntent().getStringExtra(KEY_USER_IM_ID);
        UserInfoViewModel viewModel = new UserInfoViewModel();
        viewModel.setImId(cImId);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().remark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getViewModel().setTextCount(charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean same = TextUtils.equals(mOldRemark, getViewModel().getRemark().get());
                getViewModel().getHeaderViewModel().setRightIsText(!same);
            }
        });
        getPresenter().getRemark(TAG, getViewModel().getImId(), new OnActionListener() {
            @Override
            public void onSuccess() {
                mOldRemark = getViewModel().getRemark().get();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.headerRightText:
                getPresenter().saveRemark(TAG, getViewModel().getImId(), new OnActionListener() {
                    @Override
                    public void onSuccess() {
                        mOldRemark = getViewModel().getRemark().get();
                        getViewModel().getHeaderViewModel().setRightIsText(false);
                        getBinding().remark.clearFocus();
                    }
                });
                break;
            case R.id.layout_modify:
                BuryingPointUtils.build(UserInfoActivity.class, 4014).submitF();
                Intent intent = new Intent(UserInfoActivity.this, ModifyRemarkActivity.class);
                intent.putExtra("user", getViewModel().getImId());
                intent.putExtra("remark", getViewModel().getRemarkName().get());
                startActivity(intent);
                break;
            case R.id.group:
                BuryingPointUtils.build(UserInfoActivity.class, 4015).submitF();
                if (TextUtils.isEmpty(getViewModel().getGroupId())) {
                    GroupModifyActivity.addToGroup(getViewModel().getImId());
                } else {
                    GroupModifyActivity.editGroup(getViewModel().getImId(), getViewModel().getGroupId());
                }
                break;
            case R.id.chatLog:
                BuryingPointUtils.build(UserInfoActivity.class, 4016).submitF();
                ChatHistoryActivity.startChatHistory(getViewModel().getImId());
                break;
            case R.id.customer_order:
                BuryingPointUtils.build(UserInfoActivity.class, 4017).submitF();
                Intent orderIntent = new Intent(UserInfoActivity.this, CustomerOrderListActivity.class);
                orderIntent.putExtra("user", getViewModel().getImId());
                startActivity(orderIntent);
                break;
            case R.id.btn_add_remove:
                new AlertDialog(this).builder().setTitle("确定要删除该用户")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BuryingPointUtils.build(UserInfoActivity.class, 4018).submitF();
                                getPresenter().delUserFromGroup(TAG, new OnActionListener() {
                                    @Override
                                    public void onSuccess() {
                                        hideLoading();
                                        finish();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                break;
        }
    }
}
