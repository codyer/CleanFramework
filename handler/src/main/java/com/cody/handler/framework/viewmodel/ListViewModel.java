/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.framework.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;

import com.cody.xf.utils.PageUtil;


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
public class ListViewModel<ItemViewModel extends XItemViewModel> extends ObservableArrayList<ItemViewModel> implements IListViewModel<ItemViewModel> {

    private final ObservableBoolean mViewModelValid = new ObservableBoolean(true);
    /**
     * 可以用来做key键
     */
    private Object mId;
    /**
     * 每个list view都应该有页码和页大小
     */
    private int mPageNO = PageUtil.FirstPage;
    private int mPageSize = PageUtil.PageSize;//每个list view的page size是固定的
    private boolean mIsRefresh;//列表更新状态 刷新为true 加载更多为false
    private boolean mHasMore = true;//是否有更多页
    private boolean mHasEndInfo = true;//是否有尾部没有更多提示信息

    public ListViewModel() {
    }

    @Override
    public void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
    }

    @Override
    public Object getId() {
        if (mId == null) {
            mId = new Object();
        }
        return mId;
    }

    @Override
    public void setId(Object id) {
        mId = id;
    }

    @Override
    public ObservableBoolean isValid() {
        return mViewModelValid;
    }

    @Override
    public int getPageSize() {
        return mPageSize;
    }

    @Override
    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    @Override
    public int getPageNO() {
        if (mIsRefresh) {
            mPageNO = 1;
        } else {
            mPageNO = (size() / mPageSize + 1);
        }
        return mPageNO;
    }

    @Override
    public void setPageNO(int pageNO) {
        mPageNO = pageNO;
    }

    //下次数据开始的位置
    @Override
    public int getPosition() {
        return mIsRefresh ? 0 : size();
    }

    @Override
    public boolean getHasMore() {
        return mHasMore;
    }

    @Override
    public void setHasMore(boolean hasMore) {
        mHasMore = hasMore;
    }

    @Override
    public boolean getHasEndInfo() {
        return mHasEndInfo;
    }

    /**
     * 是否显示没有更多
     *
     * @param hasEndInfo boolean
     */
    @Override
    public void setHasEndInfo(boolean hasEndInfo) {
        mHasEndInfo = hasEndInfo;
    }

    @Override
    public boolean isRefresh() {
        return mIsRefresh;
    }

    @Override
    public void setRefresh(boolean mIsRefresh) {
        this.mIsRefresh = mIsRefresh;
    }

    private ItemViewModel getLastViewModel() {
        if (size() > 0) {
            return get(size() - 1);
        }
        return null;
    }

    public void changeValid() {
        isValid().set(size() > 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ListViewModel<?> that = (ListViewModel<?>) o;

        if (mPageNO != that.mPageNO) return false;
        if (mPageSize != that.mPageSize) return false;
        if (mIsRefresh != that.mIsRefresh) return false;
        if (mHasMore != that.mHasMore) return false;
        if (mHasEndInfo != that.mHasEndInfo) return false;
        return  mViewModelValid.get() == that.mViewModelValid.get();
//            return false;
//        return mId != null ? mId.equals(that.mId) : that.mId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mViewModelValid.get() ? 1 : 0);
//        result = 31 * result + (mId != null ? mId.hashCode() : 0);
        result = 31 * result + mPageNO;
        result = 31 * result + mPageSize;
        result = 31 * result + (mIsRefresh ? 1 : 0);
        result = 31 * result + (mHasMore ? 1 : 0);
        result = 31 * result + (mHasEndInfo ? 1 : 0);
        return result;
    }
}
