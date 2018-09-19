package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.ViewModel;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.ScreenUtil;

/**
 * Created by cody.yi on 2017/9/4.
 * some useful information
 */
public class LauncherViewModel extends ViewModel {

    private int mScreenHeight = ScreenUtil.getScreenHeight(XFoundation.getContext());//屏幕宽度

    public int getImageHeight() {
        return mScreenHeight * 3 / 5;
    }
}
