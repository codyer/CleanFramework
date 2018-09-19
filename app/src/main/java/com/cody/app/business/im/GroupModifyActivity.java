package com.cody.app.business.im;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cody.app.R;
import com.cody.app.databinding.ActivityGroupModifyBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.app.business.im.adapter.GroupDialogAdapter;
import com.cody.handler.business.presenter.GroupModifyPresenter;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.business.viewmodel.GroupModifyViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.bean.entity.UserGroupBean;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.UserGroupManager;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.ToastUtil;

import java.util.List;

/**
 * Cody.yi 修改分组
 */
public class GroupModifyActivity extends WithHeaderActivity<GroupModifyPresenter, GroupModifyViewModel, ActivityGroupModifyBinding> {
    public final static String IM_ID = "im_id";
    public final static String MODIFY_TYPE = "modify_type";
    public final static String OLD_GROUP_ID = "old_group_id";
    private final static int REQUEST_CODE = 111;
    public final static int MAX_GROUP_SUM = 12;
    private GroupDialogAdapter mGroupDialogAdapter;

    /**
     * 添加到分组
     *
     * @param imId C端用户imId
     */
    public static void addToGroup(String imId) {
        Bundle bundle = new Bundle();
        bundle.putString(IM_ID, imId);
        bundle.putInt(MODIFY_TYPE, GroupModifyViewModel.ADD_TO_GROUP);
        ActivityUtil.navigateTo(GroupModifyActivity.class, bundle);
    }

    /**
     * 编辑分组
     *
     * @param imId    C端用户imId
     * @param groupId 原来的groupId
     */
    public static void editGroup(String imId, String groupId) {
        Bundle bundle = new Bundle();
        bundle.putString(IM_ID, imId);
        bundle.putString(OLD_GROUP_ID, groupId);
        bundle.putInt(MODIFY_TYPE, GroupModifyViewModel.EDIT_TO_GROUP);
        ActivityUtil.navigateTo(GroupModifyActivity.class, bundle);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_group_modify;
    }

    @Override
    protected GroupModifyPresenter buildPresenter() {
        return new GroupModifyPresenter();
    }

    @Override
    protected GroupModifyViewModel buildViewModel(Bundle savedInstanceState) {
        GroupModifyViewModel viewModel = new GroupModifyViewModel();
        String imId = getIntent().getStringExtra(IM_ID);
        String oldGroupId = getIntent().getStringExtra(OLD_GROUP_ID);
        int type = getIntent().getIntExtra(MODIFY_TYPE, 0);
        viewModel.setImId(imId);
        viewModel.setOldGroupId(oldGroupId);
        viewModel.setType(type);
        return viewModel;
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setVisible(true);
        header.setTitle(getString(R.string.choice_group));
        header.setLeft(true);
        header.setLeftResId(R.drawable.xf_ic_close_black);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //单选模式
        getBinding().groupItems.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getBinding().groupItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getBinding().groupItems.setItemChecked(position, true);
                if (mGroupDialogAdapter != null && mGroupDialogAdapter.getItem(position) != null) {
                    getViewModel().setNewGroupId(mGroupDialogAdapter.getItem(position).getGroupId());
                    getViewModel().setNewGroupName(mGroupDialogAdapter.getItem(position).getGroupName());
                }
            }
        });
        refreshData();
    }

    /**
     * 刷新分组信息
     */
    private void refreshData() {
        UserGroupManager.getUserGroup(true, new DataCallBack<List<UserGroupBean>>() {
            @Override
            public void onSuccess(List<UserGroupBean> groupBeans) {
                super.onSuccess(groupBeans);
                if (groupBeans != null && groupBeans.size() > 0) {
                    getBinding().groupItems.setAdapter(mGroupDialogAdapter = new GroupDialogAdapter(GroupModifyActivity.this, groupBeans));

                    for (int i = 0; i < groupBeans.size(); i++) {
                        UserGroupBean groupBean = groupBeans.get(i);
                        //自定义--0  已成交--1 待跟进--2 有意向--3 默认分组--4;
                        if (TextUtils.isEmpty(getViewModel().getOldGroupId())) {
                            // 选中默认分组
                            if (groupBean.getGroupType() == 4) {
                                getViewModel().setNewGroupId(groupBean.getGroupId());
                                getViewModel().setNewGroupName(groupBean.getGroupName());
                                getBinding().groupItems.setItemChecked(i, true);
                                break;
                            }
                        } else if (groupBean.getGroupId().equals(getViewModel().getOldGroupId())) {
                            // 选中已有分组
                            getViewModel().setOldGroupName(groupBean.getGroupName());
                            getBinding().groupItems.setItemChecked(i, true);
                            break;
                        }
                    }
                } else {
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                super.onError(error);
                ToastUtil.showToast(error);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            refreshData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.tv_create_group:
                if (mGroupDialogAdapter != null && mGroupDialogAdapter.getCount() >= MAX_GROUP_SUM) {
                    ToastUtil.showToast(R.string.toast_max_group);
                    return;
                }
                BuryingPointUtils.build(GroupModifyActivity.class, 4364).submitF();
                GroupManageActivity.start(REQUEST_CODE);
                break;
            case R.id.confirm:
                String tag = getViewModel().getNewGroupName().get();
                if (TextUtils.isEmpty(tag)) {
                    tag = getViewModel().getOldGroupName();
                }
                BuryingPointUtils.build(GroupModifyActivity.class, 3998).addTag(tag).submitF();
                getPresenter().modifyGroup(TAG, new OnActionListener() {
                    @Override
                    public void onSuccess() {
                        ToastUtil.showToast(R.string.toast_modify_group);
                        finish();
                    }
                });
                break;
        }
    }
}
