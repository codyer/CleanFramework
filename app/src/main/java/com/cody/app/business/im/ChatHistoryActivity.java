package com.cody.app.business.im;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cody.app.R;
import com.cody.app.business.im.adapter.ChatAdapter;
import com.cody.app.databinding.ActivityChatHistoryBinding;
import com.cody.app.business.easeui.ui.EaseShowNormalFileActivity;
import com.cody.app.framework.activity.AbsListActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.app.framework.widget.image.ImageViewDelegate;
import com.cody.app.framework.widget.image.OnImageViewListener;
import com.cody.app.utils.FileUtil;
import com.cody.app.business.widget.EaseChatRowVoicePlayer;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.imagepicker.bean.ImageItem;
import com.cody.handler.business.presenter.ChatHistoryPresenter;
import com.cody.handler.business.viewmodel.ChatViewModel;
import com.cody.handler.business.viewmodel.ItemMessageViewModel;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.io.File;
import java.util.List;

/**
 * 聊天记录页面
 */
public class ChatHistoryActivity extends AbsListActivity<ChatHistoryPresenter
        , ChatViewModel
        , ItemMessageViewModel
        , ActivityChatHistoryBinding> {

    public static final String KEY_USERNAME = "username";
    private EaseChatRowVoicePlayer mVoicePlayer;
    private AnimationDrawable voiceAnimation;
    private ImageViewDelegate mImageViewDelegate;

    public static void startChatHistory(String username) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USERNAME, username);
        ActivityUtil.navigateTo(ChatHistoryActivity.class, bundle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().fwList.setPushRefreshEnable(false);
        mImageViewDelegate = new ImageViewDelegate(new OnImageViewListener() {
            @Override
            public void onPreview(int id, List<ImageItem> images) {

            }

            @Override
            public void onPickImage(int id, List<ImageItem> images) {

            }
        }, this);
        mImageViewDelegate.setCanDelete(false);
    }

    @Override
    public void onUpdate(Object... args) {
        super.onUpdate(args);
        if (args != null
                && args.length > 0
                && ChatHistoryPresenter.TAG_SCROLL_BOTTOM == args[0]) {//不是加载更多的时候才要滑动到底部
            scrollBottom();
        }
    }

    @Override
    protected BaseRecycleViewAdapter<ItemMessageViewModel> buildRecycleViewAdapter() {
        return new ChatAdapter(this, getViewModel()) {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
                View statusLayout = viewHolder.getItemBinding().getRoot()
                        .findViewById(R.id.statusLayout);
                if (statusLayout != null) {
                    statusLayout.setVisibility(View.INVISIBLE);
                }
            }
        };

    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.fw_empty_view;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_chat_history;
    }

    @Override
    protected ChatHistoryPresenter buildPresenter() {
        return new ChatHistoryPresenter();
    }

    @Override
    protected ChatViewModel buildViewModel(Bundle savedInstanceState) {
        ChatViewModel viewModel = new ChatViewModel();
        if (getIntent() != null) {
            viewModel.setToChatUsername(getIntent().getStringExtra(KEY_USERNAME));
        }
        viewModel.setHasEndInfo(false);
        return viewModel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, final View view, int position, long id) {
        final EMMessage message = getViewModel().get(position).getMessage();
        ItemMessageViewModel itemMessageViewModel = getViewModel().get(position);
        Intent intent;
        if (id == R.id.bubble) {
            switch (getViewModel().get(position).itemIMType) {
                case TXT:
                    break;
                case LOCATION:
                    break;
                case FILE:
                    String filePath = itemMessageViewModel.getFileViewModel().localUrl;
                    File file = new File(filePath);
                    if (file.exists()) {
                        FileUtil.startActionFile(this, file);
                    } else {
                        // download the file
                        startActivity(new Intent(this, EaseShowNormalFileActivity.class).putExtra("msg",
                                message));
                    }
                    if (message.direct() == EMMessage.Direct.RECEIVE && !message.isAcked() && message.getChatType()
                            == EMMessage
                            .ChatType.Chat) {
                        try {
                            EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case IMAGE:
                    if (message != null && message.direct() == EMMessage.Direct.RECEIVE && !message.isAcked()) {
                        try {
                            EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mImageViewDelegate.preview(getViewModel().getImages(position)
                            , getViewModel().currentPreviewImagePosition);
                    break;
                case VOICE:
                    if (mVoicePlayer == null) {
                        mVoicePlayer = EaseChatRowVoicePlayer.getInstance(this);
                    }
                    if (mVoicePlayer.isPlaying()) {
                        mVoicePlayer.stop();
                        stopVoicePlayAnimation(message, view);
                    }
                    mVoicePlayer.play(message, new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopVoicePlayAnimation(message, view);
                        }
                    });
                    startVoicePlayAnimation(message, view, position);
                    break;
                case VIDEO:
                    getPresenter().playVideo(this, message, position);
                    break;
                default:
                    break;

            }
        }
    }

    public void startVoicePlayAnimation(EMMessage message, View view, int position) {
        ImageView voiceImageView = view.findViewById(R.id.iv_voice);
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            voiceImageView.setImageResource(R.drawable.voice_from_icon);
        } else {
            voiceImageView.setImageResource(R.drawable.voice_to_icon);
        }
        voiceAnimation = (AnimationDrawable) voiceImageView.getDrawable();
        voiceAnimation.start();

        // Hide the voice item not listened status view.
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            getViewModel().get(position).getVoiceViewModel().isListened.set(true);
        }
    }

    public void stopVoicePlayAnimation(EMMessage message, View view) {
        ImageView voiceImageView = view.findViewById(R.id.iv_voice);
        if (voiceAnimation != null) {
            voiceAnimation.stop();
        }

        if (message.direct() == EMMessage.Direct.RECEIVE) {
            voiceImageView.setImageResource(R.drawable.im_voice_playing_f4);
        } else {
            voiceImageView.setImageResource(R.drawable.ic_yuyin1);
        }
    }

    /**
     * 列表滚动到最底部
     */
    private void scrollBottom() {
        if (getViewModel().size() - 1 > 0 && getBinding().fwList.getLayoutManager() != null) {//不是加载更多的时候才要滑动到底部
            getBinding().fwList.getLayoutManager().scrollToPosition(getViewModel().size() - 1);
        }
    }
}
