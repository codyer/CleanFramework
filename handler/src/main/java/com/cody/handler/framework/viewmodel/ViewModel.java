package com.cody.handler.framework.viewmodel;

import java.io.Serializable;

/**
 * Created by cody.yi on 2016/8/4.
 * view model只是视图模型，每个view对应一个视图模型
 * 复杂的View通过组合嵌套的方式实现
 * 界面运行期间值会有变更时使用 observable 成员变量
 * 界面初始化后就不再变更的可以使用非 observable 成员变量
 * 尽量少使用 observable 成员变量
 */
public abstract class ViewModel implements Serializable {
    static final long serialVersionUID = 1L;
    /**
     * 可以用来做key键
     */
    private Object mId;

    public Object getId() {
        if (mId == null) {
            mId = new Object();
        }
        return mId;
    }

    public void setId(Object id) {
        mId = id;
    }
}
