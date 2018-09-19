package com.cody.handler.framework.mapper;

import com.cody.handler.framework.viewmodel.IViewModel;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cody.yi on 2016/8/24.
 * 将 DataModel 映射到 ViewModel
 * 当获取的数据和ViewModel有差距时需要使用mapper
 */
public abstract class ModelMapper<VM extends IViewModel, DM> {

    /**
     * 将dataModel装饰成viewModel
     *
     * @param dataModel 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewModel
     */
    public abstract VM mapper(DM dataModel, int position);

    /**
     * 将dataModel装饰成viewModel
     *
     * @param dataModel 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewModel
     */
    public abstract VM mapper(VM viewModel, DM dataModel);

    /**
     * 将dataModel装饰成viewModel
     * 没有分页的时候使用此函数
     *
     * @param dataModels 数据模型，对应网络请求获取的bean或entity, dataModel最好是已经排好序的
     * @return 视图模型，对应data binding中的viewModel
     */
    @Deprecated
    public <XVM extends XItemViewModel> ListViewModel<XVM> mapperList(List<DM> dataModels) {
        return mapperList(dataModels, 0);
    }

    /**
     * 将dataModel装饰成viewModel
     *
     * @param dataModels 数据模型，对应网络请求获取的bean或entity, dataModel最好是已经排好序的
     * @param start      viewModels 中需要mapper的开始的位置，默认从0开始
     * @return 视图模型，对应data binding中的viewModel
     */
    public <XVM extends XItemViewModel> ListViewModel<XVM> mapperList(List<DM> dataModels, int start) {
        ListViewModel<XVM> items = new ListViewModel<>();
        return mapperList(items, dataModels, start, true);
    }

    /**
     * 将dataModel装饰成viewModel
     * 没有分页的时候使用此函数
     *
     * @param viewModels 页面模型
     * @param dataModels 数据模型，对应网络请求获取的bean或entity, dataModel最好是已经排好序的
     * @return 视图模型，对应data binding中的viewModel
     */
    @Deprecated
    public <XVM extends XItemViewModel> ListViewModel<XVM> mapperList(ListViewModel<XVM> viewModels, List<DM> dataModels) {
        return mapperList(viewModels, dataModels, 0, true);
    }

    /**
     * 将dataModel装饰成viewModel
     *
     * @param viewModels 页面模型
     * @param dataModels 数据模型，对应网络请求获取的bean或entity, dataModel最好是已经排好序的
     * @param start      viewModels 中需要mapper的开始的位置，默认从0开始，getViewModel().getPosition()分页时调用
     * @return 视图模型，对应data binding中的viewModel
     */
    @Deprecated
    public <XVM extends XItemViewModel> ListViewModel<XVM> mapperList(ListViewModel<XVM> viewModels, List<DM> dataModels, int start) {
        return mapperList(viewModels, dataModels, start, true);
    }

    /**
     * 将dataModel装饰成viewModel
     *
     * @param viewModels 页面模型
     * @param dataModels 数据模型，对应网络请求获取的bean或entity, dataModel最好是已经排好序的
     * @param start      viewModels 中需要mapper的开始的位置，默认从0开始，getViewModel().getPosition()分页时调用
     * @return 视图模型，对应data binding中的viewModel
     */
    public <XVM extends XItemViewModel> ListViewModel<XVM> mapperList(ListViewModel<XVM> viewModels, List<DM> dataModels, int start, boolean hasMore) {

        if (viewModels == null) {
            return mapperList(dataModels, start);
        }

        if (dataModels == null) {
            dataModels = new ArrayList<>();
        }

        XVM xvm;
        int vmSize = viewModels.size();
        int dmSize = dataModels.size() + start;//所有的dataModel

        //移除多余的项
        while (vmSize > dmSize) {
            viewModels.remove(--vmSize);
        }

        for (int i = start; i < dmSize; i++) {
            xvm = (XVM) mapper(dataModels.get(i - start), i);
            xvm.setId(i);

            xvm.setFirstItem(i == 0);
            xvm.setLastItem(!hasMore && i == dmSize - 1);

            if (i < vmSize) {
                if (xvm.equals(viewModels.get(i))) {
                    continue;
                }
                viewModels.set(i, xvm);
            } else {
                viewModels.add(xvm);
            }
        }

        viewModels.setHasMore(hasMore);
        return viewModels;
    }
}
