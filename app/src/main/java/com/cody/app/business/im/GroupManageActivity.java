package com.cody.app.business.im;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivityGroupAddBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.google.gson.JsonObject;
import com.cody.handler.business.presenter.GroupModifyPresenter;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.business.viewmodel.GroupModifyViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.ToastUtil;

/**
 * dong.wang 添加分组
 */
public class GroupManageActivity extends WithHeaderActivity<GroupModifyPresenter, GroupModifyViewModel, ActivityGroupAddBinding> {

    public static void start(String groupId, String groupName) {
        Bundle bundle = new Bundle();
        bundle.putString("groupId", groupId);
        bundle.putString("groupName", groupName);
        ActivityUtil.navigateTo(GroupManageActivity.class, bundle);
    }

    public static void start(int code) {
        ActivityUtil.navigateToForResult(GroupManageActivity.class, code);
    }

    private String groupName;
    private String groupId;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_group_add;
    }

    @Override
    protected GroupModifyPresenter buildPresenter() {
        return new GroupModifyPresenter();
    }

    @Override
    protected GroupModifyViewModel buildViewModel(Bundle savedInstanceState) {
        return new GroupModifyViewModel();
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setVisible(true);
        header.setLeft(true);
        if (getIntent() != null) {
            groupName = getIntent().getStringExtra("groupName");
            groupId = getIntent().getStringExtra("groupId");
            if (TextUtils.isEmpty(groupName)) {
                header.setTitle(getString(R.string.create_group));
            } else {
                header.setTitle(getString(R.string.modify_group));
                getViewModel().setNewGroupName(groupName);
                getBinding().etGroup.post(new Runnable() {
                    @Override
                    public void run() {
                        getBinding().etGroup.setSelection(getBinding().etGroup.getText().length());
                    }
                });
            }
        } else {
            header.setTitle(getString(R.string.create_group));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.confirm:
                if (TextUtils.isEmpty(getViewModel().getNewGroupName().get())) {
                    ToastUtil.showToast("请输入分组名");
                    return;
                }

                JsonObject params = new JsonObject();
                params.addProperty("ownerId", UserInfoManager.getImId());
                params.addProperty("groupName", getViewModel().getNewGroupName().get());
                if (!TextUtils.isEmpty(groupId))
                    params.addProperty("groupId", groupId);
                getPresenter().modifyOrAddGroup(TAG, params, new OnActionListener() {
                    @Override
                    public void onSuccess() {
                        setResult(RESULT_OK);
                        if (TextUtils.isEmpty(groupName)) {
                            ToastUtil.showToast(getString(R.string.toast_create_group));
                        } else {
                            ToastUtil.showToast(getString(R.string.toast_modify_group));
                        }
                        finish();
                    }
                });
                break;
        }
    }
}
