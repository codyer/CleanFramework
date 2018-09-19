package com.cody.handler.framework.viewmodel;

import android.databinding.ObservableBoolean;

/**
 * Created by cody.yi on 2017/6/16.
 * ListViewModel item 扩展的 itemViewModel
 */
public class XItemViewModel extends ViewModel {
    private final ObservableBoolean mFirstItem = new ObservableBoolean(false);
    private final ObservableBoolean mLastItem = new ObservableBoolean(false);
    private int mItemType = 0;

    public int getItemType() {
        return mItemType;
    }

    public void setItemType(int itemType) {
        mItemType = itemType;
    }

    public ObservableBoolean isFirstItem() {
        return mFirstItem;
    }

    public void setFirstItem(boolean firstItem) {
        mFirstItem.set(firstItem);
    }

    public ObservableBoolean isLastItem() {
        return mLastItem;
    }

    public void setLastItem(boolean lastItem) {
        mLastItem.set(lastItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        XItemViewModel that = (XItemViewModel) o;

        if (mItemType != that.mItemType)
            return false;
        if (mFirstItem.get() != that.mFirstItem.get())
            return false;
        return mLastItem.get() == that.mLastItem.get();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mFirstItem.get() ? 1 : 0);
        result = 31 * result + (mLastItem.get() ? 1 : 0);
        result = 31 * result + mItemType;
        return result;
    }
}
