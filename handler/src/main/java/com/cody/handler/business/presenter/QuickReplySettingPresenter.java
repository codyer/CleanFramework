package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;

import com.cody.handler.business.mapper.QuickReplySettingMapper;
import com.cody.handler.business.viewmodel.ItemQuickReplyViewModel;
import com.cody.handler.business.viewmodel.QuickReplySettingViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.ListWithHeaderBasePresenter;
import com.cody.repository.business.bean.im.ReplyInfoBean;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.JsonUtil;

import java.util.Map;

/**
 * Created by chen.huarong on 2018/8/4.
 */
public class QuickReplySettingPresenter extends ListWithHeaderBasePresenter<QuickReplySettingViewModel
        , ItemQuickReplyViewModel> {

    private LogImInteraction mInteraction;
    private QuickReplySettingMapper mMapper;

    public QuickReplySettingPresenter() {
        mInteraction = Repository.getInteraction(LogImInteraction.class);
        mMapper = new QuickReplySettingMapper();
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        mInteraction.getReplyInfo(tag
                , UserInfoManager.getImId()
                , ReplyInfoBean.class
                , new DefaultCallback<ReplyInfoBean>(this) {

                    @Override
                    public void onBegin(Object tag) {

                    }

                    @Override
                    public void onSuccess(ReplyInfoBean bean) {
                        super.onSuccess(bean);

                        if (bean != null) {
                            mMapper.mapperList(getViewModel(), bean.getQuickReplyVoList(), 1, false);
                            getViewModel().setReplyInfoBean(bean);
                            Repository.setLocalValue(LocalKey.REPLY_INFO, JsonUtil.toJson(bean));
                        }

                        refreshUI();
                    }
                });
    }
}
