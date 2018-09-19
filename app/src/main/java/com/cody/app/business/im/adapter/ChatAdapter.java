package com.cody.app.business.im.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.cody.app.R;
import com.cody.app.databinding.EaseRowSentSystemGreetingWordsBinding;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.viewmodel.ChatViewModel;
import com.cody.handler.business.viewmodel.ItemMessageViewModel;
import com.cody.handler.framework.viewmodel.ListViewModel;

/**
 * Created by chen.huarong on 2018/7/23.
 */
public class ChatAdapter extends BaseRecycleViewAdapter<ItemMessageViewModel> {

    private Context mContext;

    public ChatAdapter(Context context, @NonNull ListViewModel<ItemMessageViewModel> listViewModel) {
        super(listViewModel);
        mContext = context;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        switch (viewType) {
            case ChatViewModel.MESSAGE_TYPE_RECV_TXT://文本
                return R.layout.ease_row_received_message;
            case ChatViewModel.MESSAGE_TYPE_SEND_TXT:
                return R.layout.ease_row_sent_message;

            case ChatViewModel.MESSAGE_TYPE_RECV_IMAGE://图片
                return R.layout.ease_row_received_picture;
            case ChatViewModel.MESSAGE_TYPE_SEND_IMAGE:
                return R.layout.ease_row_sent_picture;

            case ChatViewModel.MESSAGE_TYPE_SEND_LOCATION://位置
                return R.layout.ease_row_received_location;
            case ChatViewModel.MESSAGE_TYPE_RECV_LOCATION:
                return R.layout.ease_row_sent_location;

            case ChatViewModel.MESSAGE_TYPE_RECV_VOICE://语音
                return R.layout.ease_row_received_voice;
            case ChatViewModel.MESSAGE_TYPE_SEND_VOICE:
                return R.layout.ease_row_sent_voice;

            case ChatViewModel.MESSAGE_TYPE_RECV_VIDEO://视频
                return R.layout.ease_row_received_video;
            case ChatViewModel.MESSAGE_TYPE_SEND_VIDEO:
                return R.layout.ease_row_sent_video;

            case ChatViewModel.MESSAGE_TYPE_RECV_FILE://文件
                return R.layout.ease_row_received_file;
            case ChatViewModel.MESSAGE_TYPE_SEND_FILE:
                return R.layout.ease_row_sent_file;

            case ChatViewModel.MESSAGE_TYPE_RECV_GOODS://商品
                return R.layout.ease_row_received_goods;
            case ChatViewModel.MESSAGE_TYPE_SEND_GOODS:
                return R.layout.ease_row_sent_goods;

            case ChatViewModel.MESSAGE_TYPE_RECV_GUIDE://切换导购
                return R.layout.ease_row_received_switch_guide;
            case ChatViewModel.MESSAGE_TYPE_SEND_GUIDE:
                return R.layout.ease_row_sent_switch_guide;

            case ChatViewModel.MESSAGE_TYPE_SEND_COUPON://优惠券
                return R.layout.ease_row_sent_coupon;

            case ChatViewModel.MESSAGE_TYPE_RECV_VIDEO_CALL://视频通话状态
                return R.layout.ease_row_received_video_call;
            case ChatViewModel.MESSAGE_TYPE_SEND_VIDEO_CALL:
                return R.layout.ease_row_sent_video_call;

            case ChatViewModel.MESSAGE_TYPE_SYSGREETINGWORDS://系统欢迎语
                return R.layout.ease_row_sent_system_greeting_words;
            case ChatViewModel.MESSAGE_TYPE_RECV_USERODER://用户订单
                return R.layout.ease_row_received_order;
        }
        return R.layout.ease_row_received_message;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        if (viewHolder.getItemViewType() == ChatViewModel.MESSAGE_TYPE_SYSGREETINGWORDS) {//系统欢迎语
            EaseRowSentSystemGreetingWordsBinding binding = (EaseRowSentSystemGreetingWordsBinding) viewHolder
                    .getItemBinding();
            binding.rvSysGoods.setLayoutManager(new LinearLayoutManager(mContext
                    , LinearLayoutManager.VERTICAL
                    , false));
            binding.rvSysGoods.setAdapter(new BaseRecycleViewAdapter<ItemMessageViewModel
                    .SysWelWordsMessageViewModel
                    .ItemSysWelWordsMessageViewModel>(getListViewModel()
                    .get(position)
                    .getImSysWelWordsViewModel()
                    .sysGoodsList) {
                @Override
                public int getItemLayoutId(int viewType) {
                    return R.layout.item_im_wel_words_msg_goods;
                }
            });
        }
    }
}
