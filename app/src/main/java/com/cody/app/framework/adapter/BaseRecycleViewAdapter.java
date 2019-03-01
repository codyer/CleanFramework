/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.app.framework.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cody.app.BR;
import com.cody.app.R;
import com.cody.app.databinding.FwListWithEndBinding;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;

import java.lang.ref.WeakReference;

/**
 * Created by cody.yi on 2016/8/10.
 * Recycle View 的统一处理
 * RecycleView.Adapter有一个职责：把数据适配到View中，并展示在屏幕上。
 * 解析视图
 * 创建ViewHolder
 * 回收ViewHolder
 * 提供数据集等等
 */
public abstract class BaseRecycleViewAdapter<ItemViewModel extends XItemViewModel> extends RecyclerView
        .Adapter<BaseRecycleViewAdapter<ItemViewModel>.ViewHolder> {

    private final WeakReferenceOnListChangedCallback<ItemViewModel> mOnListChangedCallback;
    private LayoutInflater mInflater;
    private RecyclerView mRecyclerView;
    private ListViewModel<ItemViewModel> mListViewModel;
    private OnItemClickListener mItemClickListener;//item 事件监听
    private View.OnCreateContextMenuListener mItemLongClickListener;//item 长按事件监听

    public BaseRecycleViewAdapter(@NonNull ListViewModel<ItemViewModel> listViewModel) {
        this.mOnListChangedCallback = new WeakReferenceOnListChangedCallback<>(this);
        mListViewModel = listViewModel;
        notifyItemRangeInserted(0, mListViewModel.size());
    }

    /**
     * 获取List中的Item的Layout ID
     *
     * @param viewType 支持多个itemType
     * @return LayoutId
     */
    public abstract int getItemLayoutId(int viewType);

    public ListViewModel<ItemViewModel> getListViewModel() {
        return mListViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        if (mListViewModel.getHasEndInfo()) {
            FwListWithEndBinding withEndBinding = DataBindingUtil.inflate(mInflater, R.layout.fw_list_with_end, parent, false);
            ViewHolder viewHolder = new ViewHolder(withEndBinding.getRoot());
            viewHolder.setXItemBinding(withEndBinding);
            viewHolder.setItemBinding(DataBindingUtil.inflate(mInflater, getItemLayoutId(viewType), withEndBinding.itemViewStub, true));
            return viewHolder;
        } else {
            View v = mInflater.inflate(getItemLayoutId(viewType), parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            viewHolder.setItemBinding(DataBindingUtil.bind(v));
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //item上按钮事件监听，将按钮事件转交给item点击事件
        if (mListViewModel.getHasEndInfo()) {
            viewHolder.getXItemBinding().setVariable(BR.viewModel, mListViewModel.get(position));
            viewHolder.getXItemBinding().setVariable(BR.onClickListener, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(mRecyclerView, v, viewHolder.getAdapterPosition(), v.getId());
                    }
                }
            });
            viewHolder.getXItemBinding().executePendingBindings();
        }
        viewHolder.getItemBinding().setVariable(BR.viewModel, mListViewModel.get(position));
        viewHolder.getItemBinding().setVariable(BR.onClickListener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(mRecyclerView, v, viewHolder.getAdapterPosition(), v.getId());
                }
            }
        });
        viewHolder.getItemBinding().executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        if (mListViewModel != null) {
            return mListViewModel.get(position).getItemType();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mListViewModel == null ? 0 : mListViewModel.size();
    }

    /**
     * 设置监听item点击
     */
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * 设置监听item长按
     */
    public void setItemLongClickListener(View.OnCreateContextMenuListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (mRecyclerView == null && mListViewModel != null) {
            mListViewModel.addOnListChangedCallback(mOnListChangedCallback);
        }
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView != null && mListViewModel != null) {
            mListViewModel.removeOnListChangedCallback(mOnListChangedCallback);
        }
        mRecyclerView = null;
    }

    private static class WeakReferenceOnListChangedCallback<ItemViewModel extends XItemViewModel> extends ObservableList
            .OnListChangedCallback<ObservableList<ItemViewModel>> {

        private final WeakReference<BaseRecycleViewAdapter<ItemViewModel>> mAdapterWeakReference;

        WeakReferenceOnListChangedCallback(BaseRecycleViewAdapter<ItemViewModel> bindingRecyclerViewAdapter) {
            this.mAdapterWeakReference = new WeakReference<>(bindingRecyclerViewAdapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            BaseRecycleViewAdapter adapter = mAdapterWeakReference.get();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
                adapter.getListViewModel().changeValid();
            }
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            BaseRecycleViewAdapter adapter = mAdapterWeakReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
                adapter.getListViewModel().changeValid();
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            BaseRecycleViewAdapter adapter = mAdapterWeakReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
                adapter.getListViewModel().changeValid();
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            BaseRecycleViewAdapter adapter = mAdapterWeakReference.get();
            if (adapter != null) {
                for (int i = 0; i < itemCount; i++) {
                    adapter.notifyItemMoved(fromPosition + i, toPosition + i);
                }
                adapter.getListViewModel().changeValid();
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            BaseRecycleViewAdapter adapter = mAdapterWeakReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);
                adapter.notifyItemRangeChanged(positionStart, adapter.getItemCount());
                adapter.getListViewModel().changeValid();
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View
            .OnCreateContextMenuListener {

        private FwListWithEndBinding mXItemBinding;
        private ViewDataBinding mItemBinding;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public FwListWithEndBinding getXItemBinding() {
            return mXItemBinding;
        }

        public void setXItemBinding(FwListWithEndBinding XItemBinding) {
            mXItemBinding = XItemBinding;
        }

        public ViewDataBinding getItemBinding() {
            return mItemBinding;
        }

        public void setItemBinding(ViewDataBinding itemBinding) {
            mItemBinding = itemBinding;
            if (mItemClickListener != null && mItemBinding != null) {
                mItemBinding.getRoot().setOnClickListener(this);
            }
            if (mItemLongClickListener != null && mItemBinding != null) {
                mItemBinding.getRoot().setOnCreateContextMenuListener(this);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menuInfo = new AdapterView.AdapterContextMenuInfo(v, getAdapterPosition(), v.getId());
            if (mItemLongClickListener != null) {
                mItemLongClickListener.onCreateContextMenu(menu, v, menuInfo);
            }
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(mRecyclerView, v, getAdapterPosition(), v.getId());
            }
        }
    }
}
