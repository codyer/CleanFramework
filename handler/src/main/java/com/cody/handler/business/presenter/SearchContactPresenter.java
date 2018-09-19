package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cody.handler.business.viewmodel.ItemSearchContactViewModel;
import com.cody.handler.business.viewmodel.SearchContactViewModel;
import com.cody.handler.framework.IView;
import com.cody.handler.business.mapper.SearchContactMapper;
import com.cody.handler.framework.presenter.AbsListPresenter;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.DataChangeListener;
import com.cody.repository.business.database.UserInfoManager;

import java.util.List;
import java.util.Map;

public class SearchContactPresenter extends AbsListPresenter<SearchContactViewModel, ItemSearchContactViewModel> {
    private DataChangeListener mDataChangeListener;
    private SearchContactMapper mMapper;

    public SearchContactPresenter() {
        mMapper = new SearchContactMapper();
    }

    @Override
    public void attachView(Object tag, IView view, SearchContactViewModel viewModel) {
        super.attachView(tag, view, viewModel);
        searchInDadaBase();
    }

    @Override
    public void detachView(Object tag) {
        super.detachView(tag);
        if (mDataChangeListener != null) {
            mDataChangeListener.cancel();
            mDataChangeListener = null;
        }
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        searchInDadaBase();
    }

    private void searchInDadaBase() {
        final String keyWord = getViewModel().getSearchViewModel().getKeyWord().get();
        if (TextUtils.isEmpty(keyWord)) {
            getViewModel().clear();
            refreshUI();
            return;
        }

        if (mDataChangeListener != null) {
            mDataChangeListener.cancel();
            mDataChangeListener = null;
        }
        mDataChangeListener = UserInfoManager.getUserByNameKeyWord(keyWord, new DataCallBack<List<UserInfoBean>>() {
            @Override
            public void onSuccess(List<UserInfoBean> userInfoBeans) {
                super.onSuccess(userInfoBeans);

                mMapper.mapperList(getViewModel(), userInfoBeans, 0, false);
                refreshUI();
            }
        });
    }
}
