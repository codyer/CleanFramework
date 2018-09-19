package com.cody.handler.business.viewmodel;

import com.cody.handler.R;
import com.cody.handler.framework.viewmodel.ViewModel;

/**
 * 我的客户类别item
 */
public class ItemCustomerViewModel extends ViewModel {
    public final static String POTENTIAL_CUSTOMER = "潜在客户";
    public final static String INTENT_CUSTOMER = "意向客户";
    public final static String BARGAIN_CUSTOMER = "成交客户";

    private String mItemTitle;//item 标题
    private int mTotalCount;//总数
    private int mNewCount;//新增

    public ItemCustomerViewModel(String itemTitle) {
        mItemTitle = itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        mItemTitle = itemTitle;
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(int totalCount) {
        mTotalCount = totalCount;
    }

    public String getNewCount() {
        return mNewCount + "";
    }

    public boolean hideLine(){
        return BARGAIN_CUSTOMER.equals(mItemTitle);
    }

    public void setNewCount(int newCount) {
        mNewCount = newCount;
    }

    public String getItemTitle() {
        return mItemTitle + "(" + mTotalCount + ")";
    }

    public int getImgResId() {
        if (POTENTIAL_CUSTOMER.equals(mItemTitle))
            return R.drawable.icon_group_follow;
        else if (INTENT_CUSTOMER.equals(mItemTitle))
            return R.drawable.icon_group_intent;
        else if (BARGAIN_CUSTOMER.equals(mItemTitle))
            return R.drawable.icon_group_bargain;
        else
            return R.drawable.icon_group_default;
    }

}
