/*
 * Copyright (c) 2017.   Cody.yi Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cody.app.business.binding;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cody.app.R;
import com.cody.app.databinding.ActivityDemoDesignerMainBinding;
import com.cody.app.databinding.ItemDemoDesignerMainTopBinding;
import com.cody.app.framework.activity.AbsListActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.app.framework.adapter.OnItemClickListener;
import com.cody.handler.business.presenter.DemoDesignerMainPresenter;
import com.cody.handler.business.viewmodel.DemoDesignerMainViewModel;
import com.cody.handler.business.viewmodel.ItemDemoDesignerMainViewModel;
import com.cody.handler.business.viewmodel.ItemDemoDesignerNearlyViewModel;
import com.cody.handler.business.viewmodel.ItemDemoDesignerTopViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

public class DemoDesignerMainActivity extends AbsListActivity<DemoDesignerMainPresenter,
        DemoDesignerMainViewModel,
        ItemDemoDesignerMainViewModel,
        ActivityDemoDesignerMainBinding> {

    /**
     * 创建标题
     * 返回空或者默认的HeaderViewModel不会显示头部，必须设置头部的visible
     *
     * @see HeaderViewModel#setVisible
     */
    protected void initHeader(HeaderViewModel header) {
        header.setTitle("Star");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mPullLoadMoreRecyclerView.getRecyclerView().setLayoutManager(layoutManager);
    }

    @Override
    protected DemoDesignerMainViewModel buildViewModel(Bundle savedInstanceState) {
        DemoDesignerMainViewModel listWithHeaderViewModel = new DemoDesignerMainViewModel();
        initHeader(listWithHeaderViewModel.getHeaderViewModel());
        listWithHeaderViewModel.setHasEndInfo(false);
        return listWithHeaderViewModel;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_demo_designer_main;
    }

    @Override
    protected DemoDesignerMainPresenter buildPresenter() {
        return new DemoDesignerMainPresenter();
    }

    @Override
    protected BaseRecycleViewAdapter<ItemDemoDesignerMainViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemDemoDesignerMainViewModel>(getViewModel()) {
            @Override
            public int getItemViewType(int position) {
                if (position == 0) {
                    return 0;
                }
                return 1;
            }

            @Override
            public int getItemLayoutId(int viewType) {
                if (viewType == 0) {
                    return R.layout.item_demo_designer_main_top;
                }
                return R.layout.item_demo_designer_main_nearly;
            }

            @Override
            public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
                ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                if (viewHolder.getItemBinding() instanceof ItemDemoDesignerMainTopBinding && viewType == 0) {
                    ItemDemoDesignerMainTopBinding binding = (ItemDemoDesignerMainTopBinding) viewHolder.getItemBinding();
                    new PagerSnapHelper(){

                        @Override
                        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                            int position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
                            ItemDemoDesignerTopViewModel topViewModel = getViewModel().get(0).getTopItem();
                            topViewModel.setCursor(position+1);
                            return position;
                        }
                    }.attachToRecyclerView(binding.topList);
                }

                return viewHolder;
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
                if (viewHolder.getItemBinding() instanceof ItemDemoDesignerMainTopBinding) {
                    ItemDemoDesignerMainTopBinding binding = (ItemDemoDesignerMainTopBinding) viewHolder.getItemBinding();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(DemoDesignerMainActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    binding.topList.setLayoutManager(layoutManager);
                    binding.topList.setHasFixedSize(true);

                    BaseRecycleViewAdapter<ItemDemoDesignerNearlyViewModel> topItemAdapter;
                    final ListViewModel<ItemDemoDesignerNearlyViewModel> topViewModels = getViewModel().get(position).getTopItem();
                    topItemAdapter = new BaseRecycleViewAdapter<ItemDemoDesignerNearlyViewModel>(topViewModels) {
                        @Override
                        public int getItemLayoutId(int viewType) {
                            return R.layout.item_demo_designer_main_sub_top;
                        }
                    };

                    topItemAdapter.setItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int subPosition, long id) {
                            ToastUtil.showToast("subPosition="+subPosition);
                        }
                    });
                    binding.topList.setAdapter(topItemAdapter);
                }
            }
        };
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    protected int getEmptyViewId() {
        return R.layout.fw_empty_view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerText:
                scrollToTop();
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        ToastUtil.showToast("Position="+position);
    }
}
