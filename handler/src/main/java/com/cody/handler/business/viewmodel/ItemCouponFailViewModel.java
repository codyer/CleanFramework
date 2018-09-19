package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by liuliwei on 2018-09-03.
 */

public class ItemCouponFailViewModel extends XItemViewModel{
    private String phoneStr;
    private boolean showViewLine;

    public String getPhoneStr() {
        return phoneStr;
    }

    public void setPhoneStr(String phoneStr) {
        this.phoneStr = phoneStr;
    }

    public void setShowViewLine(boolean showViewLine) {
        this.showViewLine = showViewLine;
    }

    public boolean isShowViewLine() {
        return showViewLine;
    }
}
