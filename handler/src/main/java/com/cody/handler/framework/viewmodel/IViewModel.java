package com.cody.handler.framework.viewmodel;

import android.databinding.ObservableBoolean;

import java.io.Serializable;

/**
 * Created by cody.yi on 2017/5/11.
 * IViewModel
 */
public interface IViewModel extends Serializable {
    Object getId();

    void setId(Object id);

    /**
     * 是否可用，可用控制界面显示默认界面
     *
     * @return valid
     */
    ObservableBoolean isValid();
}
