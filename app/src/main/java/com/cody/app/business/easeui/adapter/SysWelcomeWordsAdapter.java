package com.cody.app.business.easeui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cody.repository.business.bean.im.WelWordsItemBean;
import com.cody.app.R;
import com.cody.app.business.easeui.domain.ImageLoaderHelper;

import java.util.List;

/**
 * Created by  qiaoping.xiao on 2017/9/28.
 */

public class SysWelcomeWordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<WelWordsItemBean> mGreetingInfoList;
    private Context mContext;
    private final SysWelWordsAdapterCallBack mCallBack;

    public SysWelcomeWordsAdapter(Context context, List<WelWordsItemBean> greetingInfoList,
                                  SysWelWordsAdapterCallBack callBack) {
        mContext = context;
        mGreetingInfoList = greetingInfoList;
        mCallBack = callBack;
    }

    public void updateDatas(List<WelWordsItemBean> greetingInfoList) {
        if (greetingInfoList == null) return;
        mGreetingInfoList = greetingInfoList;
        notifyDataSetChanged();
    }

    public void addOneData(WelWordsItemBean bean) {
        mGreetingInfoList.add(bean);
        notifyItemInserted(mGreetingInfoList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new ViewHolder1(LayoutInflater.from(mContext).inflate(R.layout.item_wel_words_text, parent,
                        false));
            case 2:
                return new ViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.wel_words_msg_goods, parent,
                        false));
            default:
                return new DefalutViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_wel_words_default,
                        parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WelWordsItemBean welWordsItemBean = mGreetingInfoList.get(position);
        switch (welWordsItemBean.getType()) {
            case 1:
                if (holder instanceof ViewHolder1) {
                    ((ViewHolder1) holder).disPlay(mGreetingInfoList, welWordsItemBean, position);
                }
                break;
            case 2:
                if (holder instanceof ViewHolder2) {
                    ((ViewHolder2) holder).disPlay(mGreetingInfoList, welWordsItemBean, position);
                }
                break;
            default:
                if (holder instanceof DefalutViewHolder) {
                    ((DefalutViewHolder) holder).disPlay(mGreetingInfoList, welWordsItemBean, position);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mGreetingInfoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mGreetingInfoList == null) return 0;
        switch (mGreetingInfoList.get(position).getType()) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        public final EditText etContent;

        ViewHolder1(View itemView) {
            super(itemView);
            etContent = (EditText) itemView.findViewById(R.id.et_content);
        }

        void disPlay(final List<WelWordsItemBean> datas, WelWordsItemBean bean, final int position) {
            etContent.setText(bean.getContent());
            if (mCallBack != null) {
                mCallBack.getWelWordsAndData(bean.getContent(), datas, position);
            }
            etContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String content = s.toString();
                    if (mCallBack != null) {
                        mCallBack.getWelWordsAndData(content, datas, position);
                    }
                }
            });
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        public final TextView tvGoodsTitle;
        public final ImageView ivGoodsIcon;
        public final TextView tvGoodsPrice;
        private final LinearLayout welWordsGoods;

        ViewHolder2(View itemView) {
            super(itemView);
            welWordsGoods = (LinearLayout) itemView.findViewById(R.id.layout_wel_words_goods);
            tvGoodsTitle = (TextView) itemView.findViewById(R.id.tv_goods_title);
            ivGoodsIcon = (ImageView) itemView.findViewById(R.id.iv_goods_icon);
            tvGoodsPrice = (TextView) itemView.findViewById(R.id.tv_goods_price);
        }

        void disPlay(List<WelWordsItemBean> datas, WelWordsItemBean bean, int position) {
            if (tvGoodsPrice != null && tvGoodsTitle != null) {
                welWordsGoods.setVisibility(View.VISIBLE);
                tvGoodsTitle.setText(bean.getMerchandiseName());
                tvGoodsPrice.setText(String.valueOf(bean.getMerchandisePrice()));
                //    final float scale = mContext.getResources().getDisplayMetrics().density;
                //  int pic_px =  (int) (90 * (mContext.getResources().getDisplayMetrics().density) + 0.5f);
                ImageLoaderHelper.getInstance().displayImage(mContext, bean.getImageUrl(), ivGoodsIcon, R.mipmap
                        .default_image);
                // ivGoodsIcon.setBackgroundResource(bean.getImageUrl());

            } else {
                welWordsGoods.setVisibility(View.GONE);
            }
        }
    }

    public class DefalutViewHolder extends RecyclerView.ViewHolder {

        DefalutViewHolder(View itemView) {
            super(itemView);
        }

        void disPlay(List<WelWordsItemBean> datas, WelWordsItemBean bean, int position) {

        }
    }

    public interface SysWelWordsAdapterCallBack {
        void getWelWordsAndData(String content, List<WelWordsItemBean> datas, int position);
    }
}
