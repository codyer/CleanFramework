package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.cody.handler.R;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.handler.framework.viewmodel.ViewModel;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.ScreenUtil;

/**
 * Created by cody.yi on 2018/7/18.
 * 首页
 */
public class MainViewModel extends ViewModel {

    private ListViewModel<MainImItemViewModel> mItems = new ListViewModel<>();

    public ListViewModel<MainImItemViewModel> getItems() {
        return mItems;
    }

    public void setItems(ListViewModel<MainImItemViewModel> items) {
        mItems = items;
    }

    public int getImageHeight() {
        return (ScreenUtil.getScreenWidth(XFoundation.getContext()) * 213 / 375);
    }

    private final ObservableBoolean mOnline = new ObservableBoolean(false);
    private final ObservableInt mUnreadMsgCount = new ObservableInt(0);
    private boolean mShopManager = false;//是店长
    private int mRank = 0;//1：金:2：银:3：铜、-1：无四种
    private String mUserName;
    private final ObservableField<String> mImageUrl = new ObservableField<>();

    public ObservableBoolean getOnline() {
        return mOnline;
    }

    public ObservableInt getUnreadMsgCount() {
        return mUnreadMsgCount;
    }

    public int getRank() {
        if (!mOnline.get() && mRank > 0) return R.drawable.main_rank_0;
        switch (mRank) {
            case 1:
                return R.drawable.main_rank_1;
            case 2:
                return R.drawable.main_rank_2;
            case 3:
                return R.drawable.main_rank_3;
            default:
                return R.color.transparent;
        }
    }

    public void setRank(int rank) {
        mRank = rank;
    }

    public boolean isShopManager() {
        return mShopManager;
    }

    public void setShopManager(boolean shopManager) {
        mShopManager = shopManager;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public ObservableField<String> getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl.set(imageUrl);
    }
}
