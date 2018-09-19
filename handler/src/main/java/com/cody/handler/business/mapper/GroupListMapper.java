package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.GroupListViewModel;
import com.cody.handler.business.viewmodel.ItemGroupViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.entity.UserGroupBean;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.database.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组列表
 */
public class GroupListMapper extends ModelMapper<ItemGroupViewModel, UserInfoBean> {
    private List<UserGroupBean> mDataModels;
    private int mPosition = 0;

    public GroupListMapper() {
    }

    @Override
    public ItemGroupViewModel mapper(UserInfoBean dataModel, int position) {
        ItemGroupViewModel viewModel = new ItemGroupViewModel();
        if (dataModel.isGroup() && mDataModels != null) {
            viewModel.setCustomPos(mPosition);
//            viewModel.setCustomGroupName(dataModel.getGroupName());
//            viewModel.setCustomGroup(dataModel.getGroupType() == 0);
            viewModel.setExpand(dataModel.isExpend());
            mapperList(viewModel.getItems(), mDataModels.get(mPosition).getCustomerList(), 0, false);
            mPosition++;
        }
        return mapper(viewModel, dataModel);
    }

    @Override
    public ItemGroupViewModel mapper(ItemGroupViewModel viewModel, UserInfoBean dataModel) {
        if (dataModel == null) return viewModel;
        if (dataModel.isGroup()) {
            viewModel.setItemType(ItemGroupViewModel.TYPE_GROUP);
            viewModel.setExpand(dataModel.isExpend());
        } else {
            viewModel.setItemType(ItemGroupViewModel.TYPE_ITEM);
        }
        viewModel.setGroupId(dataModel.getGroupId());
        viewModel.setGroupName(dataModel.getGroupName());
        viewModel.setGroupType(dataModel.getGroupType());
        viewModel.setName(UserInfoManager.handleUserName(dataModel));
        viewModel.setAvatar(dataModel.getAvatar());
        viewModel.setImId(dataModel.getImId());
        return viewModel;
    }

    public void mapperModel(GroupListViewModel viewModels, List<UserGroupBean> dataModels) {
        if (viewModels == null || dataModels == null) return;
        viewModels.setStickyPosition(-1);
        mDataModels = dataModels;
        mPosition = 0;
        int oldViewModelSize = viewModels.size();
        int groupPosition = 0;
        UserGroupBean groupListBean;
        List<UserInfoBean> users = new ArrayList<>();
        //原有的组
        for (int i = 0; i < oldViewModelSize && groupPosition < dataModels.size(); i++) {
            groupListBean = dataModels.get(groupPosition);
            ItemGroupViewModel item = viewModels.get(i);
            if (item.getItemType() == ItemGroupViewModel.TYPE_GROUP) {
                groupPosition++;
                UserInfoBean groupBean = new UserInfoBean();
                groupBean.setGroupId(groupListBean.getGroupId());
                groupBean.setGroupName(groupListBean.getGroupName());
                groupBean.setGroupType(groupListBean.getGroupType());
                groupBean.setGroup(true);
                groupBean.setExpend(item.isExpand().get());
                users.add(groupBean);
                if (item.isExpand().get()) {
                    users.addAll(groupListBean.getCustomerList());
                }

            }
        }
        mapperList(viewModels, users, 0, false);

        //可能新增的组 默认不展开组
        if (groupPosition < dataModels.size()) {
            for (int i = groupPosition; i < dataModels.size(); i++) {
                groupListBean = dataModels.get(i);
                UserInfoBean groupBean = new UserInfoBean();
                groupBean.setGroupId(groupListBean.getGroupId());
                groupBean.setGroupName(groupListBean.getGroupName());
                groupBean.setGroupType(groupListBean.getGroupType());
                groupBean.setGroup(true);
                groupBean.setExpend(false);
                ItemGroupViewModel groupItem = mapper(groupBean, i);
                viewModels.add(groupItem);
            }
        }
    }
}
