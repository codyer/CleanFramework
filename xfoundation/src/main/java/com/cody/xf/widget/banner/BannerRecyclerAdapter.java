package com.cody.xf.widget.banner;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cody.xf.BR;
import com.cody.xf.R;

import java.util.Collection;

/**
 * Created by cody.yi on 2017/4/26.
 * RecyclerView适配器，假无限循环列表
 */
public class BannerRecyclerAdapter extends RecyclerView.Adapter<BannerRecyclerAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private OnBannerClickListener mOnBannerClickListener;
    private ObservableList<BannerViewModel> mListItems;

    public int getItemLayoutId(int viewType) {
        return R.layout.xf_default_banner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View v = inflater.inflate(getItemLayoutId(viewType), parent, false);
        return new ViewHolder(v);
    }

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        mOnBannerClickListener = onBannerClickListener;
    }

    public BannerRecyclerAdapter(@Nullable Collection<BannerViewModel> items) {
        if (mListItems == items) {
            return;
        }
        if (items instanceof ObservableList) {
            mListItems = (ObservableList<BannerViewModel>) items;
            notifyItemRangeInserted(0, mListItems.size());
        } else if (items != null) {
            mListItems = new ObservableArrayList<>();
            mListItems.addAll(items);
        } else {
            mListItems = null;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.getItemBinding().setVariable(BR.viewModel, getViewModel(viewHolder.getAdapterPosition()));
        viewHolder.getItemBinding().setVariable(BR.onClickListener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBannerClickListener != null) {
                    int position = getPosition(viewHolder.getAdapterPosition());
                    mOnBannerClickListener.onBannerItemClick(v, position, getViewModel(position));
                }
            }
        });
        viewHolder.getItemBinding().executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(getPosition(position));
    }

    private BannerViewModel getViewModel(int position) {
        return mListItems.get(getPosition(position));
    }

    private int getPosition(int position) {
        return position % getBannerSize();
    }

    public int getBannerSize() {
        return mListItems == null ? 0 : mListItems.size();
    }

    @Override
    public int getItemCount() {
        return mListItems == null ? 0 : mListItems.size() < 2 ? mListItems.size() : Integer.MAX_VALUE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mItemBinding;

        ViewHolder(View itemView) {
            super(itemView);
            mItemBinding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getItemBinding() {
            return mItemBinding;
        }
    }
}
