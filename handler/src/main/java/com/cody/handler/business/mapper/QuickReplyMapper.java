package com.cody.handler.business.mapper;

import android.text.TextUtils;

import com.cody.handler.business.viewmodel.QuickReplyViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.handler.framework.viewmodel.IViewModel;
import com.cody.repository.business.bean.im.ReplyInfoBean;

/**
 * Created by chen.huarong on 2018/7/30.
 */
public class QuickReplyMapper extends ModelMapper {

    @Override
    public IViewModel mapper(Object dataModel, int position) {
        return null;
    }

    @Override
    public IViewModel mapper(IViewModel viewModel, Object dataModel) {
        return null;
    }

    public void mapper(QuickReplyViewModel viewModel, ReplyInfoBean.QuickReplyVoListBean dataModel) {
        if (viewModel == null) {
            viewModel = new QuickReplyViewModel();
        }
        if (dataModel == null) {
            return;
        }
        viewModel.setQrId(TextUtils.isEmpty(dataModel.getId()) ? "" : dataModel.getId());
        viewModel.setContent(dataModel.getReplyContent());
        viewModel.setCreatedAt(dataModel.getCreatedAt());
        viewModel.setEnable(dataModel.getEnable() == 1);
        viewModel.setOwnerId(dataModel.getOwnerId());
        viewModel.setTitle(dataModel.getTitle());
    }
}
