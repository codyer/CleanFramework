/*
 * Copyright (c)  Created by Cody.yi on 2016/8/27.
 */

package com.cody.app.framework.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cody.handler.framework.viewmodel.BaseViewModel;
import com.cody.app.BR;

import java.lang.ref.WeakReference;
import java.util.Collection;

/**
 * Created by cody.yi on 2016/8/10.
 * Recycle View 的统一处理
 * RecycleView.Adapter有一个职责：把数据适配到View中，并展示在屏幕上。
 * 解析视图
 * 创建ViewHolder
 * 回收ViewHodler
 * 提供数据集等等
 */
public abstract class BaseRecycleViewAdapter<ItemViewModel extends BaseViewModel> extends RecyclerView
        .Adapter<BaseRecycleViewAdapter<ItemViewModel>.ViewHolder> {

    private final WeakReferenceOnListChangedCallback<ItemViewModel> onListChangedCallback;
    private LayoutInflater inflater;
    protected ObservableList<ItemViewModel> mListItems;
    private OnItemClickListener mItemClickListener;//item 事件监听
    private View.OnCreateContextMenuListener mItemLongClickListener;//item 长按事件监听
    protected View.OnClickListener mOnClickListener;//item中的按钮事件分发

    public BaseRecycleViewAdapter(@Nullable Collection<ItemViewModel> listItems) {
        this.onListChangedCallback = new WeakReferenceOnListChangedCallback<>(this);
        setListItems(listItems);
    }

    /**
     * 获取List中的Item的Layout ID
     *
     * @param viewType 支持多个itemType
     * @return LayoutId
     */
    public abstract int getItemLayoutId(int viewType);

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        /**
         * item上按钮事件监听，将按钮事件转交给item点击事件
         */
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(null, v, viewHolder.getAdapterPosition(), v.getId());
            }
        };
        viewHolder.getItemBinding().setVariable(BR.viewModel, mListItems.get(position));
        viewHolder.getItemBinding().setVariable(BR.onClickListener, mOnClickListener);
        viewHolder.getItemBinding().executePendingBindings();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View v = inflater.inflate(getItemLayoutId(viewType), parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mListItems == null ? 0 : mListItems.size();
    }

    public void addAll(Collection<ItemViewModel> items) {
        mListItems.addAll(items);
        this.notifyDataSetChanged();
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

    /**
     * 获取item
     */
    public ItemViewModel getItem(int position) {
        return mListItems.get(position);
    }

    /**
     * 添加item
     */
    @SuppressWarnings("unused")
    public void addItem(int position, ItemViewModel item) {
        mListItems.add(position, item);
        this.notifyItemInserted(position);
        this.notifyItemRangeChanged(position, getItemCount());
    }

    /**
     * 更新item
     */
    @SuppressWarnings("unused")
    public void setItem(int position, ItemViewModel item) {
        mListItems.set(position, item);
        this.notifyItemChanged(position);
    }

    /**
     * 删除某个item
     */
    public void removeItem(int position) {
        if (position != -1 && position < mListItems.size()) {
            mListItems.remove(position);
            this.notifyItemRemoved(position);
            this.notifyItemRangeChanged(position, getItemCount());
        }
    }

    public void clear() {
        mListItems.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View
            .OnCreateContextMenuListener {

        private ViewDataBinding mItemBinding;

        ViewHolder(View itemView) {
            super(itemView);
            mItemBinding = DataBindingUtil.bind(itemView);
            if (mItemClickListener != null) {
                itemView.setOnClickListener(this);
            }
            if (mItemLongClickListener != null) {
                itemView.setOnCreateContextMenuListener(this);
            }
        }

        public ViewDataBinding getItemBinding() {
            return mItemBinding;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menuInfo = new AdapterView.AdapterContextMenuInfo(v, getAdapterPosition(), v.getId());
            mItemLongClickListener.onCreateContextMenu(menu, v, menuInfo);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(null, v, getAdapterPosition(), v.getId());
        }
    }

    private void setListItems(@Nullable Collection<ItemViewModel> items) {
        if (mListItems == items) {
            return;
        }

        if (mListItems != null) {
            mListItems.removeOnListChangedCallback(onListChangedCallback);
            notifyItemRangeRemoved(0, mListItems.size());
        }

        if (items instanceof ObservableList) {
            mListItems = (ObservableList<ItemViewModel>) items;
            notifyItemRangeInserted(0, mListItems.size());
            mListItems.addOnListChangedCallback(onListChangedCallback);
        } else if (items != null) {
            mListItems = new ObservableArrayList<>();
            mListItems.addOnListChangedCallback(onListChangedCallback);
            mListItems.addAll(items);
        } else {
            mListItems = null;
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (mListItems != null) {
            mListItems.removeOnListChangedCallback(onListChangedCallback);
        }
    }

    private static class WeakReferenceOnListChangedCallback<ItemViewModel extends BaseViewModel> extends ObservableList
            .OnListChangedCallback<ObservableList<ItemViewModel>> {

        private final WeakReference<BaseRecycleViewAdapter<ItemViewModel>> adapterReference;

        WeakReferenceOnListChangedCallback(BaseRecycleViewAdapter<ItemViewModel> bindingRecyclerViewAdapter) {
            this.adapterReference = new WeakReference<>(bindingRecyclerViewAdapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                for (int i = 0; i < itemCount; i++) {
                    adapter.notifyItemMoved(fromPosition + i, toPosition + i);
                }
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);
                adapter.notifyItemRangeChanged(positionStart, adapter.getItemCount());
            }
        }
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * AdapterView has been clicked.
     */
    public interface OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this AdapterView has
         * been clicked.
         * <p>
         * Implementers can call getItemAtPosition(position) if they need
         * to access the data associated with the selected item.
         *
         * @param parent   The AdapterView where the click happened.
         * @param view     The view within the AdapterView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         * @param id       The row id of the item that was clicked.
         */
        void onItemClick(RecyclerView parent, View view, int position, long id);
    }
}
