package com.cody.handler.framework.viewmodel;

import android.databinding.ObservableBoolean;

/**
 * Created by cody.yi on 2016/8/4.
 * view model只是视图模型，每个view对应一个视图模型
 * 复杂的View通过组合嵌套的方式实现
 * 界面运行期间值会有变更时使用 observable 成员变量
 * 界面初始化后就不再变更的可以使用非 observable 成员变量
 * 尽量少使用 observable 成员变量
 */
public abstract class ViewModel implements IViewModel {
    static final long serialVersionUID = 1L;
    private final ObservableBoolean mViewModelValid = new ObservableBoolean(true);
    /**
     * 可以用来做key键
     */
    private Object mId;

    @Override
    public Object getId() {
        if (mId == null) {
            mId = new Object();
        }
        return mId;
    }

    @Override
    public void setId(Object id) {
        mId = id;
    }

    /**
     * 是否可用，可用控制界面显示默认界面
     *
     * @return valid
     */
    @Override
    public ObservableBoolean isValid() {
        return mViewModelValid;
    }

    public void setValid(boolean viewModelValid) {
        mViewModelValid.set(viewModelValid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewModel viewModel = (ViewModel) o;

        return  mViewModelValid.get() == viewModel.mViewModelValid.get();
//        return mId != null ? mId.equals(viewModel.mId) : viewModel.mId == null;
    }

    @Override
    public int hashCode() {
        return (mViewModelValid.get() ? 1 : 0);
    }
}
