package com.cody.handler.framework.presenter;

import android.support.annotation.NonNull;

import com.cody.handler.framework.viewmodel.IListViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cody.yi on 2016/12/23.
 * 列表页抽象Presenter
 */
public abstract class AbsListPresenter<AbsListViewModel extends IListViewModel<ItemViewModel>,
        ItemViewModel extends XItemViewModel> extends Presenter<AbsListViewModel>
        implements IListPresenter {
    /**
     * 获取初始页数据
     *
     * @param tag Http请求标识
     */
    @Override
    final public void getInitPage(Object tag) {
        if (getView() != null) {
            getView().showLoading(null);
        }
        this.getRefreshPage(tag);
    }

    /**
     * 获取最新数据
     *
     * @param tag Http请求标识
     */
    @Override
    final public void getRefreshPage(Object tag) {
        if (getViewModel() != null) {
            getViewModel().setRefresh(true);
//            getViewModel().setHasMore(true);
        }
        getDefaultRecycleList(tag, new HashMap<String, String>());
    }

    /**
     * 获取下一页数据
     *
     * @param tag Http请求标识
     */
    @Override
    final public void getNextPage(Object tag) {
        if (getViewModel() != null) {
            getViewModel().setRefresh(false);
        }
        getDefaultRecycleList(tag, new HashMap<String, String>());
    }

    /**
     * 获取列表
     *
     * @param tag    请求tag，用来取消请求
     * @param params 参数
     *               page:1
     *               pageSize:20
     */
    @Override
    public void getDefaultRecycleList(Object tag, @NonNull Map<String, String> params) {
        params.put("pageNo", getViewModel().getPageNO() + "");
        params.put("pageSize", getViewModel().getPageSize() + "");
        getRecycleList(tag, params);
    }

}
