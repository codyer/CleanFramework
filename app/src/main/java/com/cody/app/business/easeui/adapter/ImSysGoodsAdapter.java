package com.cody.app.business.easeui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cody.app.business.easeui.domain.ImageLoaderHelper;
import com.cody.repository.business.bean.im.ReplyInfoBean;
import com.cody.repository.business.bean.im.SysGreetingWordsGoodsBean;
import com.cody.app.R;

import java.util.List;

/**
 * Created by  qiaoping.xiao on 2017/10/11.
 */

public class ImSysGoodsAdapter<T> extends RecyclerView.Adapter<ImSysGoodsAdapter.SysGoodsViewHolder> {


    //    private final List<SysGreetingWordsGoodsBean> mDatas;
    private final List<T> mDatas;
    private final Context mContext;

    public ImSysGoodsAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public SysGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SysGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.wel_words_msg_goods, parent, false));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(SysGoodsViewHolder holder, int position) {
        holder.disPlay(mDatas, mDatas.get(position), position);
    }


    class SysGoodsViewHolder<T> extends RecyclerView.ViewHolder {
        private final TextView tvGoodsTitle;
        private final ImageView ivGoodsIcon;
        private final TextView tvGoodsPrice;
        private final LinearLayout layoutWelWordsGoods;

        public SysGoodsViewHolder(View itemView) {
            super(itemView);

            layoutWelWordsGoods = (LinearLayout) itemView.findViewById(R.id.layout_wel_words_goods);
            tvGoodsTitle = (TextView) itemView.findViewById(R.id.tv_goods_title);
            ivGoodsIcon = (ImageView) itemView.findViewById(R.id.iv_goods_icon);
            tvGoodsPrice = (TextView) itemView.findViewById(R.id.tv_goods_price);
        }

        void disPlay(List<T> datas, T bean, int position) {
            if (tvGoodsPrice != null && tvGoodsTitle != null) {
                layoutWelWordsGoods.setVisibility(View.VISIBLE);
                if (bean instanceof SysGreetingWordsGoodsBean) {
                    SysGreetingWordsGoodsBean bean1 = (SysGreetingWordsGoodsBean) bean;
                    tvGoodsTitle.setText(bean1.getMerchandiseName());
                    tvGoodsPrice.setText("¥" + bean1.getMerchandisePrice());
                    ImageLoaderHelper.getInstance().displayImage(mContext, bean1.getImageUrl(), ivGoodsIcon, R.mipmap
                            .default_image);
                } else if (bean instanceof ReplyInfoBean.GreetingReplyVoListBean.RecommendatoryMerchandiseBean) {
                    ReplyInfoBean.GreetingReplyVoListBean.RecommendatoryMerchandiseBean bean2 = (ReplyInfoBean
                            .GreetingReplyVoListBean.RecommendatoryMerchandiseBean) bean;
                    tvGoodsTitle.setText(bean2.getMerchandiseName());
                    tvGoodsPrice.setText("¥" +bean2.getMerchandisePrice());
                    ImageLoaderHelper.getInstance().displayImage(mContext, bean2.getImageUrl(), ivGoodsIcon, R.mipmap
                            .default_image);
                }
            } else {
                layoutWelWordsGoods.setVisibility(View.GONE);
            }
        }
    }
}
