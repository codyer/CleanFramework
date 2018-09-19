package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.ListWithHeaderViewModel;
import com.cody.repository.business.bean.im.ReplyInfoBean;

/**
 * Created by chen.huarong on 2018/8/4.
 */
public class QuickReplySettingViewModel extends ListWithHeaderViewModel<ItemQuickReplyViewModel> {

    private ReplyInfoBean mReplyInfoBean;

    public ReplyInfoBean getReplyInfoBean() {
        return mReplyInfoBean;
    }

    public void setReplyInfoBean(ReplyInfoBean replyInfoBean) {
        mReplyInfoBean = replyInfoBean;
    }

}
