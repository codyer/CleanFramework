package com.cody.xf.binding.handler;

import com.android.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cody.yi on 2016/8/24.
 * 将 DataModel 映射到 ViewModel
 * 当获取的数据和ViewModel有差距时需要使用mapper
 */
public abstract class ModelMapper<VM, DM> {

    /**
     * 将dataModel装饰成viewModel
     *
     * @param dataModel 数据模型，对应网络请求获取的bean或entity
     * @return 视图模型，对应data binding中的viewModel
     */
    public abstract VM mapper(DM dataModel);

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
    public List<VM> mapperList(@NonNull List<DM> dataModels) {
        return mapperList(dataModels, 0);
    }

    /**
     * 将dataModel装饰成viewModel
     *
     * @param dataModels 数据模型，对应网络请求获取的bean或entity, dataModel最好是已经排好序的
     * @param start      viewModels 中需要mapper的开始的位置，默认从0开始
     * @return 视图模型，对应data binding中的viewModel
     */
    public List<VM> mapperList(@NonNull List<DM> dataModels, int start) {
        List<VM> items = new ArrayList<>();
        return mapperList(items, dataModels, start);
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
    public List<VM> mapperList(@NonNull List<VM> viewModels, @NonNull List<DM> dataModels) {
        return mapperList(viewModels, dataModels, 0);
    }

    /**
     * 将dataModel装饰成viewModel
     *
     * @param viewModels 页面模型
     * @param dataModels 数据模型，对应网络请求获取的bean或entity, dataModel最好是已经排好序的
     * @param start      viewModels 中需要mapper的开始的位置，默认从0开始，getViewModel().getPosition()分页时调用
     * @return 视图模型，对应data binding中的viewModel
     */
    public List<VM> mapperList(@NonNull List<VM> viewModels, @NonNull List<DM> dataModels, int start) {

        if (viewModels == null) {
            return mapperList(dataModels, start);
        }

        if (dataModels == null) {
            dataModels = new ArrayList<>();
        }

        VM vm;
        int vmSize = viewModels.size();
        int dmSize = dataModels.size() + start;//所有的dataModel

        //移除多余的项
        while (vmSize > dmSize) {
            viewModels.remove(--vmSize);
        }

        for (int i = start; i < dmSize; i++) {
            vm = mapper(dataModels.get(i - start));
            if (i < vmSize) {
                if (vm.equals(viewModels.get(i))) {
                    continue;
                }
                viewModels.set(i, vm);
            } else {
                viewModels.add(vm);
            }
        }

        return viewModels;
    }
}
