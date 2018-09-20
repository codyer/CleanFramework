package com.cody.handler.business.presenter;

import com.cody.handler.business.mapper.CaseMapper;
import com.cody.handler.business.viewmodel.CaseViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.bean.CaseBean;
import com.cody.repository.business.interaction.JzInteraction;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cody.yi on 2017/3/30.
 * 案例Presenter
 */
public class CasePresenter extends Presenter<CaseViewModel> {
    private CaseMapper mMapper;
    private DemoInteraction mInteraction;

    public CasePresenter() {
        mMapper = new CaseMapper();
        mInteraction = Repository.getInteraction(DemoInteraction.class);
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
                refreshUI();
            }
        });
        LogUtil.d("CasePresenter", getView().toString());
        LogUtil.d("CasePresenter", getViewModel().toString());
    }
}
