package com.cody.handler.business.mapper;

import com.cody.handler.business.viewmodel.CaseViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.CaseBean;

/**
 * Created by cody.yi on 2016/8/24.
 * 模型装饰，映射,
 * 当获取的数据和ViewModel有差距时需要使用mapper
 */
public class CaseMapper extends ModelMapper<CaseViewModel, CaseBean> {

    @Override
    public CaseViewModel mapper(CaseBean dataModel, int position) {
        CaseViewModel viewModel = new CaseViewModel();
        return mapper(viewModel, dataModel);
    }

    @Override
    public CaseViewModel mapper(CaseViewModel viewModel, CaseBean dataModel) {
        if (viewModel == null) return mapper(dataModel, 0);

        viewModel.setInfo(dataModel.toString());

        return viewModel;
    }
}
