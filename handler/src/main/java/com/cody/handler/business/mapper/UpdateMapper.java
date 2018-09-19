package com.cody.handler.business.mapper;


import com.cody.handler.framework.viewmodel.UpdateViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.UpdateBean;
import com.cody.xf.BuildConfig;
import com.cody.xf.utils.CommonUtil;
import com.cody.xf.utils.DateUtil;
import com.cody.xf.utils.StringUtil;

/**
 * Created by cody.yi on 2016/8/24.
 * 应用升级
 */
public class UpdateMapper extends ModelMapper<UpdateViewModel, UpdateBean> {

    @Override
    public UpdateViewModel mapper(UpdateBean dataModel, int position) {
        UpdateViewModel viewModel = new UpdateViewModel();
        return mapper(viewModel, dataModel);
    }

    @Override
    public UpdateViewModel mapper(UpdateViewModel viewModel, UpdateBean dataModel) {
        if (viewModel == null || dataModel == null) return viewModel;
        viewModel.setApkName(dataModel.fromObject + DateUtil.getDateString() + BuildConfig.BUILD_TYPE + BuildConfig.VERSION_CODE + ".apk");
        viewModel.setApkUrl(dataModel.url);
        if (StringUtil.compareVersion(dataModel.version, CommonUtil.getVersionName())) {
            if (dataModel.needUpdate) {
                viewModel.setForceUpdate(true);
            }
            if (StringUtil.compareVersion(dataModel.lastVersion, CommonUtil.getVersionName())) {
                viewModel.setForceUpdate(true);
            } else {
                viewModel.setOptionalUpdate(true);
            }
        } else {
            viewModel.setForceUpdate(false);
            viewModel.setOptionalUpdate(false);
        }

        viewModel.setVersionChecked(true);
        viewModel.setUpdateInfo(dataModel.updateInfo);

        return viewModel;
    }
}
