package com.cody.app.business.im;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.cody.app.R;
import com.cody.app.databinding.ActivitySearchContactBinding;
import com.cody.app.framework.activity.AbsListActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.presenter.SearchContactPresenter;
import com.cody.handler.business.viewmodel.ItemSearchContactViewModel;
import com.cody.handler.business.viewmodel.SearchContactViewModel;
import com.cody.xf.utils.SoftKeyboardUtil;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/***
 * 搜索联系人
 */
public class SearchContactActivity extends AbsListActivity<SearchContactPresenter, SearchContactViewModel, ItemSearchContactViewModel, ActivitySearchContactBinding> {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_search_contact;
    }

    @Override
    protected SearchContactPresenter buildPresenter() {
        return new SearchContactPresenter();
    }

    @Override
    protected SearchContactViewModel buildViewModel(Bundle savedInstanceState) {
        SearchContactViewModel viewModel = new SearchContactViewModel();
        viewModel.setHasEndInfo(false);
        return viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TextView) (getBinding().fwList.getEmptyViewContainer().findViewById(R.id.tv_message))).setText("暂无联系人");
        getBinding().header.searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == MotionEvent.ACTION_DOWN) {
                    SoftKeyboardUtil.hiddenSoftInput(SearchContactActivity.this);
                }
                return false;
            }
        });

        getBinding().header.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().getInitPage(TAG);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                finish();
                break;
        }
    }

    @Override
    protected BaseRecycleViewAdapter<ItemSearchContactViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemSearchContactViewModel>(getViewModel()) {

            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_serch_contact;
            }
        };
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.empty_view;
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        ChatActivity.startChat(getViewModel().get(position).getImId());
    }
}
