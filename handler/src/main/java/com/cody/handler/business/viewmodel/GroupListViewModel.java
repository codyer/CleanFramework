package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.ListViewModel;

/**
 * 客户分组
 */
public class GroupListViewModel extends ListViewModel<ItemGroupViewModel> {
    private int mStickyPosition = -1;
    private int mTotal;

    public int getStickyPosition() {
        return mStickyPosition;
    }

    public void setStickyPosition(int currentPosition) {
        mStickyPosition = currentPosition;
    }

    public int getGroupPositionBeforePosition(int position) {
        for (int i = position; i < size() && i >= 0; i--) {
            if (get(i).isGroup()) {
                return i;
            }
        }
        return -1;
    }

    public int getGroupPositionAfterPosition(int position) {
        for (int i = position + 1; i < size() && i > 0; i++) {
            if (get(i).isGroup()) {
                return i;
            }
        }
        return -1;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }
}
