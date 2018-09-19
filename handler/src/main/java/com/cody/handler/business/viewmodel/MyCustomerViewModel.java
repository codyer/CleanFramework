package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.ViewModel;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.ScreenUtil;

/**
 * Created by cody.yi on 2018/8/16.
 * 我的客户
 */
public class MyCustomerViewModel extends ViewModel {
    private int mTotalCustomer;//客户总数
    private int mNewCustomerInMonth;//最近30天新增
    private int mMarketingInMonth;//最近30天营销
    private int mSalesConversion;//促成销售转化

    public int getImageHeight() {
        return (ScreenUtil.getScreenWidth(XFoundation.getContext()) * 213 / 375);
    }

    final private ItemCustomerViewModel mPotentialCustomer = new ItemCustomerViewModel(ItemCustomerViewModel.POTENTIAL_CUSTOMER);
    final private ItemCustomerViewModel mIntentCustomer = new ItemCustomerViewModel(ItemCustomerViewModel.INTENT_CUSTOMER);
    final private ItemCustomerViewModel mBargainCustomer = new ItemCustomerViewModel(ItemCustomerViewModel.BARGAIN_CUSTOMER);

    public String getTotalCustomer() {
        return mTotalCustomer + "";
    }

    public void setTotalCustomer(int totalCustomer) {
        mTotalCustomer = totalCustomer;
    }

    public String getNewCustomerInMonth() {
        return mNewCustomerInMonth + "";
    }

    public void setNewCustomerInMonth(int newCustomerInMonth) {
        mNewCustomerInMonth = newCustomerInMonth;
    }

    public String getMarketingInMonth() {
        return mMarketingInMonth + "";
    }

    public void setMarketingInMonth(int marketingInMonth) {
        mMarketingInMonth = marketingInMonth;
    }

    public String getSalesConversion() {
        return mSalesConversion + "";
    }

    public void setSalesConversion(int salesConversion) {
        mSalesConversion = salesConversion;
    }

    //潜在
    public ItemCustomerViewModel getPotentialCustomer() {
        return mPotentialCustomer;
    }

    //意向
    public ItemCustomerViewModel getIntentCustomer() {
        return mIntentCustomer;
    }

    //成交
    public ItemCustomerViewModel getBargainCustomer() {
        return mBargainCustomer;
    }
}
