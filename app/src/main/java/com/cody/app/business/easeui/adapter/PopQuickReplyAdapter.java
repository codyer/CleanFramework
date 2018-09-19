package com.cody.app.business.easeui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cody.app.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.cody.app.business.widget.PopItemEditCallBack;
import com.cody.repository.business.bean.im.ReplyInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  qiaoping.xiao on 2017/9/18.
 */

public class PopQuickReplyAdapter extends RecyclerView.Adapter<PopQuickReplyAdapter.QuickRelpyViewHolder> {


    private PopItemEditCallBack callBack;
    private String targetUser;
    private Context context;
    private List<ReplyInfoBean.QuickReplyVoListBean> mDatas;

    public PopQuickReplyAdapter(Context context, List<ReplyInfoBean.QuickReplyVoListBean> datas, String targetUser, PopItemEditCallBack callBack) {
        this.context = context;
        mDatas = datas;
        this.targetUser = targetUser;
        this.callBack = callBack;
    }

    public void updateDatas(List<ReplyInfoBean.QuickReplyVoListBean> datas) {
        mDatas = new ArrayList<>();
        if (datas != null) {
            for (ReplyInfoBean.QuickReplyVoListBean data : datas) {
                if (data.getEnable() == 1){
                    mDatas.add(data);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public QuickRelpyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuickRelpyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_quick_reply_detail, null, false));
    }

    @Override
    public void onBindViewHolder(QuickRelpyViewHolder holder, int position) {
        holder.disPlay(mDatas, position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class QuickRelpyViewHolder extends RecyclerView.ViewHolder {

        private final TextView content;
        private final ImageView ivQuickReplyEdit;

        public QuickRelpyViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
            ivQuickReplyEdit = (ImageView) itemView.findViewById(R.id.iv_quick_reply_edit);
        }

        public void disPlay(final List<ReplyInfoBean.QuickReplyVoListBean> datas, final int position) {
            final ReplyInfoBean.QuickReplyVoListBean quickReplyVoListBean = datas.get(position);
            content.setText(quickReplyVoListBean.getReplyContent());
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EMMessage toSend = EMMessage.createTxtSendMessage(datas.get(position).getReplyContent(),
                            targetUser);
                    EMClient.getInstance().chatManager().saveMessage(toSend);
                    callBack.onMessageSend();
                }
            });
            ivQuickReplyEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.goToOtherActivity(quickReplyVoListBean);
                }
            });
        }
    }

}
