package com.cody.handler.framework.viewmodel;

import android.databinding.ObservableList;

/**
 * Created by cody.yi on 2017/5/11.
 * 列表
 */
public interface IListViewModel<ItemViewModel extends XItemViewModel> extends IViewModel, ObservableList<ItemViewModel> {
    int getPageSize();

    void setPageSize(int pageSize);

    int getPageNO();

    void setPageNO(int pageNO);

    //下次数据开始的位置
    int getPosition();

    boolean getHasMore();

    void setHasMore(boolean hasMore);

    boolean getHasEndInfo();

    void setHasEndInfo(boolean hasEndInfo);

    boolean isRefresh();

    void setRefresh(boolean mIsRefresh);
}
