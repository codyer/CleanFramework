package com.cody.app.framework;

import com.cody.repository.framework.Repository;
import com.cody.xf.XFApplication;

/**
 * Created by cody.yi on 2017/3/7.
 *
 */

public class BaseApplication extends XFApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Repository.install(this);
    }
}
