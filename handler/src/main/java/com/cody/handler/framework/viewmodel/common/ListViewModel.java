/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.framework.viewmodel.common;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.cody.handler.R;
import com.cody.handler.framework.viewmodel.BaseViewModel;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;
import com.cody.xf.utils.PageUtil;
import com.cody.xf.utils.ResourceUtil;


/**
 * Created by cody.yi on 2016/8/24.
 * 例程 界面运行期间值会有变更时使用 observable 成员变量
 * 界面初始化后就不再变更的可以使用非 observable 成员变量
 * 尽量少使用 observable 成员变量
 * <p>
 * 不包含头部的list view
 *
 * @param <ItemViewModel> ListView中的item ViewModel
 */
public class ListViewModel<ItemViewModel extends BaseViewModel> extends WithHeaderViewModel {
    private final ObservableField<String> keyWord = new ObservableField<>("");
    private String hint = ResourceUtil.getString(R.string.h_search_hint);

    public ObservableField<String> getKeyWord() {
        return keyWord;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * 每个listview都应该有页码和页大小
     */
    private int mPageSize = PageUtil.PageSize;//每个list view的page size是固定的
    private int mPageNO = PageUtil.FirstPage;
    private boolean mIsRefresh;//列表更新状态 刷新为true 加载更多为false
    private boolean mHasMore = true;//是否有更多页
    private final ObservableList<ItemViewModel> mItemViewModels = new ObservableArrayList<>();

    public int getPageSize() {
        return mPageSize;
    }

    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    public int getPageNO() {
        if (mIsRefresh) {
            mPageNO = 1;
        } else {
            mPageNO = (mItemViewModels.size() / mPageSize + 1);
        }

        return mPageNO;
    }

    @SuppressWarnings("unused")
    public void setPageNO(int pageNO) {
        mPageNO = pageNO;
    }

    //下次数据开始的位置
    public int getPosition() {
        return mIsRefresh ? 0 : size();
    }

    public boolean getHasMore() {
        return mHasMore;
    }

    @SuppressWarnings("unused")
    public void setHasMore(boolean hasMore) {
        mHasMore = hasMore;
    }

    public ObservableList<ItemViewModel> getItemViewModels() {
        return mItemViewModels;
    }

    /**
     * h
     * 判断是否已经包含了相同的item
     */
    public boolean contains(ItemViewModel item) {
        if (item == null) return false;
        for (int i = 0; i < mItemViewModels.size(); i++) {
            if (item.getId().equals(mItemViewModels.get(i).getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 重置数据
     */
    public void reset() {
        while (mItemViewModels.size() >= mPageSize) {
            mItemViewModels.remove(mItemViewModels.size() - 1);
        }
        mHasMore = true;
    }

    /**
     * 移除指定id的item
     */
    public void removeById(Object id) {
        if (id == null) return;
        for (int i = 0; i < mItemViewModels.size(); i++) {
            if (id.equals(mItemViewModels.get(i).getId())) {
                mItemViewModels.remove(i);
            }
        }
    }

    /**
     * 移除指定位置的item
     */
    public void remove(int position) {
        if (position < mItemViewModels.size() && position >= 0) {
            mItemViewModels.remove(position);
        }
    }

    /**
     * 获取指定位置的item
     */
    public ItemViewModel get(int position) {
        if (position < mItemViewModels.size() && position >= 0) {
            return mItemViewModels.get(position);
        }
        return null;
    }

    /**
     * 指定位置添加item
     */
    public void add(int position, ItemViewModel item) {
        if (position < mItemViewModels.size() && position >= 0) {
            mItemViewModels.add(position, item);
        }
    }

    public int size() {
        return mItemViewModels.size();
    }

    public boolean isRefresh() {
        return mIsRefresh;
    }

    public void setRefresh(boolean mIsRefresh) {
        this.mIsRefresh = mIsRefresh;
    }
}
