package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;

import com.cody.handler.R;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.DeviceUtil;
import com.cody.xf.utils.ResourceUtil;

/**
 * 客户分组item
 */
public class ItemGroupViewModel extends XItemViewModel {
    public final static int TYPE_GROUP = 0x001;//组
    public final static int TYPE_ITEM = 0x002;//成员

    private int[] mColors = {R.color.h_avatar_bg_one, R.color.h_avatar_bg_two, R.color.h_avatar_bg_three, R.color.h_avatar_bg_four
            , R.color.h_avatar_bg_five, R.color.h_avatar_bg_six, R.color.h_avatar_bg_seven, R.color.h_avatar_bg_eight};
    private final ObservableBoolean mIsExpand = new ObservableBoolean(false);//是否展开
    private String mGroupId;//分组id
    private String mGroupName;//分组名
    private GradientDrawable mDrawableColor;
    private int mGroupType;//分组类型 自定义--0  已成交--1 待跟进--2 有意向--3 默认分组--4;
    private String mName;//真名
    private String mAvatar;//头像
    private String mImId;
    private ListViewModel<ItemGroupViewModel> mItems = new ListViewModel<>();//关闭组时临时存储组item

    public ObservableBoolean isExpand() {
        setExpand(mIsExpand.get());
        return mIsExpand;
    }

    public boolean isGroup() {
        return getItemType() == TYPE_GROUP;
    }

    public void setExpand(boolean expand) {
        mIsExpand.set(expand && mItems.size() > 0);
    }

    public ListViewModel<ItemGroupViewModel> getItems() {
        return mItems;
    }

    public String getGroupId() {
        return mGroupId;
    }

    public void setGroupId(String groupId) {
        this.mGroupId = groupId;
    }

    public String getGroupName() {
        return getChildCount() > 0 ? mGroupName + "(" + getChildCount() + ")" : mGroupName;
    }

    public String getGroupNameStr() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        this.mGroupName = groupName;
    }

    public int getGroupType() {
        return mGroupType;
    }

    public void setGroupType(int groupType) {
        mGroupType = groupType;
    }

    public int getChildCount() {
        return mItems.size();
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        this.mAvatar = avatar;
    }

    public String getImId() {
        return mImId;
    }

    public void setImId(String imId) {
        this.mImId = imId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setCustomPos(int customPos) {
        int index = customPos - 4;
        if (index >= 0 && index <= 7) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(ResourceUtil.getColor(mColors[index]));
            drawable.setCornerRadius(DeviceUtil.dip2px(XFoundation.getContext(), 18));
            drawable.setBounds(0, 0, DeviceUtil.dip2px(XFoundation.getContext(), 36), DeviceUtil.dip2px(XFoundation.getContext(), 36));
            setDrawableColor(drawable);
        }
    }

    public String getCustomGroupName() {
        if (!TextUtils.isEmpty(mGroupName)) {
            return String.valueOf(mGroupName.charAt(0)).toUpperCase();
        }
        return "";
    }

    public boolean isCustomGroup() {
        return mGroupType == 0;
    }

    public GradientDrawable getDrawableColor() {
        return mDrawableColor;
    }

    public void setDrawableColor(GradientDrawable drawableColor) {
        mDrawableColor = drawableColor;
    }

    public int getArrowResId() {
        if (mItems.size() > 0)
            return R.drawable.xf_ic_arrow_black;
        else
            return R.drawable.xf_ic_arrow_gray;
    }

    public int getImgResId() {
        if (mGroupType == 1)
            return R.drawable.icon_group_bargain;
        else if (mGroupType == 2)
            return R.drawable.icon_group_follow;
        else if (mGroupType == 3)
            return R.drawable.icon_group_intent;
        else
            return R.drawable.icon_group_default;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemGroupViewModel viewModel = (ItemGroupViewModel) o;

        if (mGroupId != null ? !mGroupId.equals(viewModel.mGroupId) : viewModel.mGroupId != null)
            return false;
        if (mGroupName != null ? !mGroupName.equals(viewModel.mGroupName) : viewModel.mGroupName != null)
            return false;
        if (mName != null ? !mName.equals(viewModel.mName) : viewModel.mName != null) return false;
        if (mAvatar != null ? !mAvatar.equals(viewModel.mAvatar) : viewModel.mAvatar != null)
            return false;
//        if (mIsExpand != null ? !mIsExpand.equals(viewModel.mIsExpand) : viewModel.mIsExpand != null)
//            return false; 保留展开状态
        if (mItems != null ? !mItems.equals(viewModel.mItems) : viewModel.mItems != null)
            return false;
        return mImId != null ? mImId.equals(viewModel.mImId) : viewModel.mImId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mGroupId != null ? mGroupId.hashCode() : 0);
        result = 31 * result + (mGroupName != null ? mGroupName.hashCode() : 0);
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mAvatar != null ? mAvatar.hashCode() : 0);
//        result = 31 * result + (mIsExpand != null ? mIsExpand.hashCode() : 0);
        result = 31 * result + (mItems != null ? mItems.hashCode() : 0);
        result = 31 * result + (mImId != null ? mImId.hashCode() : 0);
        return result;
    }
}
