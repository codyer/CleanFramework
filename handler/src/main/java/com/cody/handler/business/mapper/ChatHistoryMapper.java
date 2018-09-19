package com.cody.handler.business.mapper;

import android.media.MediaPlayer;

import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.PathUtil;
import com.cody.handler.business.viewmodel.ItemMessageViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.im.BaseMessageBean;
import com.cody.repository.business.bean.im.ChatLogBean;
import com.cody.repository.business.bean.im.CustomMessage;
import com.cody.repository.business.bean.im.IMUserLikeBean;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.xf.utils.FileUtils;
import com.cody.xf.utils.JsonUtil;

import java.io.File;

/**
 * Created by chen.huarong on 2018/7/20.
 */
public class ChatHistoryMapper extends ModelMapper<ItemMessageViewModel, ChatLogBean> {
    private String toChatUsername = "";
    private ChatMapper mChatMapper;

    public ChatHistoryMapper() {
        mChatMapper = new ChatMapper();
    }

    @Override
    public ItemMessageViewModel mapper(ChatLogBean dataModel, int position) {
        ItemMessageViewModel itemMessageViewModel = new ItemMessageViewModel();
        return mapper(itemMessageViewModel, dataModel);
    }

    @Override
    public ItemMessageViewModel mapper(ItemMessageViewModel viewModel, ChatLogBean dataModel) {
        if (dataModel == null) return viewModel;
        EMMessage message = null;
        boolean isSend = toChatUsername.equals(dataModel.getTo());
        if ("txt".equals(dataModel.getType())) {//文本消息
            message = EMMessage.createTxtSendMessage(dataModel.getMsg(), toChatUsername);
        } else if ("audio".equals(dataModel.getType())) {//语音消息
            message = getAudioMessage(dataModel);
        } else if ("video".equals(dataModel.getType())) {
            message = getVideoMessage(dataModel);
        } else if ("img".equals(dataModel.getType())) {
            message = getImageMessage(dataModel);
        } else if ("file".equals(dataModel.getType())) {
            message = getFileMessage(dataModel);
        } else if ("loc".equals(dataModel.getType())) {
            message = getLocMessage(dataModel);
        } else if ("custom".equals(dataModel.getType())) {//亲加的自定义消息
            message = getCustomMessage(dataModel);
        }

        if (message != null) {
            if (isSend) {
                message.setFrom(UserInfoManager.getImId());
                message.setTo(toChatUsername);
                message.setDirection(EMMessage.Direct.SEND);
            } else {
                message.setFrom(toChatUsername);
                message.setTo(UserInfoManager.getImId());
                message.setDirection(EMMessage.Direct.RECEIVE);
            }
            message.setMsgTime(dataModel.getTimestamp());
            message.setStatus(EMMessage.Status.SUCCESS);
        }
        viewModel.setUserAvatar(dataModel.getFromAvatar());
        return mChatMapper.mapper(viewModel, message);
    }

    private int getAudioLength(String url) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            return mediaPlayer.getDuration();
        } catch (Exception e) {
            return 0;
        }
    }

    private EMMessage getAudioMessage(final ChatLogBean bean) {
        int length = bean.getLength();
        length = length == 0 ? getAudioLength(bean.getFastDFSUrl()) : length;
        final boolean isGotye = length == 0;
        final String path = PathUtil.getInstance().getVoicePath().getPath() + "/";
        final File file = new File(path + new File(bean.getFastDFSUrl()).getName());
        if (!file.exists()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtils.downFile(bean.getFastDFSUrl()
                            , path
                            , file.getName()
                            , isGotye);
                }
            }).start();
        }

        return EMMessage.createVoiceSendMessage(file.getPath(), length,
                toChatUsername);
    }

    private EMMessage getVideoMessage(final ChatLogBean bean) {
        int length = bean.getLength();
        final String videoPath = PathUtil.getInstance().getVideoPath().getPath() + "/";
        final File video = new File(videoPath + bean.getFilename());
        if (!video.exists()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtils.downFile(bean.getUrl()
                            , videoPath
                            , bean.getFilename()
                            , false);
                }
            }).start();
        }

        final String imagePath = PathUtil.getInstance().getImagePath().getPath() + "/";
        final File thumb = new File(imagePath + new File(bean.getFastDFSThumbUrl()).getName());
        if (!thumb.exists()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtils.downFile(bean.getFastDFSThumbUrl()
                            , imagePath
                            , thumb.getName()
                            , false);
                }
            }).start();
        }

        return EMMessage.createVideoSendMessage(video.getAbsolutePath(), thumb.getAbsolutePath(), length,
                toChatUsername);
    }

    private EMMessage getImageMessage(final ChatLogBean bean) {
        final String path = PathUtil.getInstance().getImagePath().getPath() + "/";
        final File file = new File(path + new File(bean.getUrl()).getName());
        if (!file.exists()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtils.downFile(bean.getUrl()
                            , path
                            , file.getName()
                            , false);
                }
            }).start();
        }

        return EMMessage.createImageSendMessage(file.getPath(), false,
                toChatUsername);
    }

    private EMMessage getFileMessage(final ChatLogBean bean) {
        final String path = PathUtil.getInstance().getFilePath().getPath() + "/";
        final File file = new File(path + bean.getFilename());
        if (!file.exists()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtils.downFile(bean.getUrl()
                            , path
                            , bean.getFilename()
                            , false);
                }
            }).start();
        }

        return EMMessage.createFileSendMessage(file.getPath(),
                toChatUsername);
    }

    private EMMessage getLocMessage(final ChatLogBean bean) {
        return EMMessage.createLocationSendMessage(bean.getLat()
                , bean.getLng()
                , bean.getAddr()
                , toChatUsername);
    }

    private EMMessage getCustomMessage(ChatLogBean chatLogBean) {
        if (chatLogBean != null) {
            String msg = chatLogBean.getMsg();
            msg = msg.substring(msg.indexOf("{"));
            BaseMessageBean bean = JsonUtil.fromJson(msg, BaseMessageBean.class);
            if (bean != null && bean.getType() == CustomMessage.UserLike) {
                IMUserLikeBean imUserLikeBean = bean.getBean(IMUserLikeBean.class);
                return EMMessage.createTxtSendMessage(imUserLikeBean.getReminder(), toChatUsername);
            }
            String jsonObject = new Gson().toJson(bean);
            EMMessage message = EMMessage.createTxtSendMessage(" ", toChatUsername);
            message.setAttribute(BaseMessageBean.MESSAGE_ATTR_KEY, jsonObject);
            return message;
        }

        return null;
    }

    public void setToChatUsername(String toChatUsername) {
        this.toChatUsername = toChatUsername;
    }
}
