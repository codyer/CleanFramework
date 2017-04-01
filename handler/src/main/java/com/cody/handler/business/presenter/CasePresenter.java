package com.cody.handler.business.presenter;

import com.cody.handler.business.mapper.CaseMapper;
import com.cody.handler.business.viewmodel.CaseViewModel;
import com.cody.repository.bean.CaseBean;
import com.cody.repository.interaction.JzInteraction;
import com.cody.handler.framework.DefaultCallback;
import com.cody.xf.binding.handler.Presenter;
import com.cody.xf.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cody.yi on 2017/3/30.
 * 案例Presenter
 */
public class CasePresenter extends Presenter<CaseViewModel> {
    private CaseMapper mMapper;
    private JzInteraction mInteraction;

    public CasePresenter() {
        mMapper = new CaseMapper();
        mInteraction = com.cody.xf.Repositoryq.getInteraction(JzInteraction.class);
    }

    public void getCaseList(final Object tag) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", "1");
        params.put("pageSize", "20");
        mInteraction.getCase(tag, params, CaseBean.class, new DefaultCallback<CaseBean>(this) {
            @Override
            public void onSuccess(CaseBean bean) {
                super.onSuccess(bean);
                mMapper.mapper(getViewModel(), bean);
                if (getView() != null) getView().onUpdate();
            }
        });
        LogUtil.d("CasePresenter",getView().toString());
        LogUtil.d("CasePresenter",getViewModel().toString());
    }

}
