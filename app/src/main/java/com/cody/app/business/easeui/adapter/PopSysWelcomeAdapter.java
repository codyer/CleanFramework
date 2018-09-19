package com.cody.app.business.easeui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cody.app.R;
import com.cody.app.business.easeui.domain.EMMessageManager;
import com.cody.repository.business.bean.im.CustomMessage;
import com.cody.repository.business.bean.im.ImSysWelWordsBean;
import com.cody.repository.business.bean.im.ReplyInfoBean;
import com.cody.repository.business.bean.im.SysGreetingWordsGoodsBean;
import com.cody.app.business.widget.PopItemEditCallBack;
import com.google.gson.Gson;
import com.cody.repository.business.bean.im.BaseMessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  qiaoping.xiao on 2017/9/18.
 */

public class PopSysWelcomeAdapter extends RecyclerView.Adapter<PopSysWelcomeAdapter.SysViewHolder> {


    private String targetUser;
    private PopItemEditCallBack callBack;
    private Context context;
    private List<ReplyInfoBean.GreetingReplyVoListBean> mDatas;

    public PopSysWelcomeAdapter(Context context, List<ReplyInfoBean.GreetingReplyVoListBean> datas, String
            targetUser, PopItemEditCallBack callBack) {
        this.context = context;
        mDatas = new ArrayList<>();
        if (datas != null && datas.size() > 0)
            mDatas.add(datas.get(0));
        this.targetUser = targetUser;
        this.callBack = callBack;
    }

    public void updateDatas(List<ReplyInfoBean.GreetingReplyVoListBean> datas) {
        mDatas = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            mDatas.add(datas.get(0));
        }
        notifyDataSetChanged();
    }

    @Override
    public SysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SysViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sys_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(SysViewHolder holder, int position) {
        holder.disPlay(mDatas, position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class SysViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSysWelWords;
        private final RecyclerView rvSysGoods;
        private final View content;

        SysViewHolder(View itemView) {
            super(itemView);
            tvSysWelWords = itemView.findViewById(R.id.tv_sys_wel_words);
            rvSysGoods = itemView.findViewById(R.id.rv_sys_goods);
            content = itemView.findViewById(R.id.contentLayout);
        }

        void disPlay(final List<ReplyInfoBean.GreetingReplyVoListBean> datas, final int position) {
            final ReplyInfoBean.GreetingReplyVoListBean bean = datas.get(position);
            List<ReplyInfoBean.GreetingReplyVoListBean.RecommendatoryMerchandiseBean> goodsBean = bean
                    .getRecommendatoryMerchandise();
            if (goodsBean != null && goodsBean.size() > 0) {
                rvSysGoods.setLayoutManager(new LinearLayoutManager(context));
                rvSysGoods.setAdapter(new ImSysGoodsAdapter<>(context, goodsBean));
            }
            tvSysWelWords.setText(bean.getReplyContent());
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String extraText = generateSysGreetingMessage(bean);
                    EMMessageManager.sendCustomMessage(extraText, targetUser);
                    callBack.onMessageSend();
                }
            });
        }

        private String generateSysGreetingMessage(ReplyInfoBean.GreetingReplyVoListBean bean) {
            if (bean == null) return "";
            Gson gson = new Gson();
            BaseMessageBean baseMessageBean = new BaseMessageBean();
            baseMessageBean.setType(CustomMessage.SysGreetingWords);

            ImSysWelWordsBean imSysWelWordsBean = new ImSysWelWordsBean();
            imSysWelWordsBean.setType(CustomMessage.SysGreetingWords);
            List<SysGreetingWordsGoodsBean> greetingWordsGoodsBeanList = new ArrayList<>();
            if (bean.getRecommendatoryMerchandise() != null && bean.getRecommendatoryMerchandise().size() > 0) {
                for (ReplyInfoBean.GreetingReplyVoListBean.RecommendatoryMerchandiseBean data : bean
                        .getRecommendatoryMerchandise()) {
                    greetingWordsGoodsBeanList.add(new SysGreetingWordsGoodsBean(data.getImageUrl(),
                            data.getMerchandiseName(), data.getMerchandisePrice(), data.getMerchandiseId(), data
                            .getMerchandiseSku()));
                }
            }
            imSysWelWordsBean.setSysGoodsList(greetingWordsGoodsBeanList);
            imSysWelWordsBean.setSysWordsStr(bean.getReplyContent());
            imSysWelWordsBean.setExtraStr1("");
            imSysWelWordsBean.setExtraStr2("");
            baseMessageBean.setJSONContent(gson.toJson(imSysWelWordsBean));

            return gson.toJson(baseMessageBean);
        }
    }

}
