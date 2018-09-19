package com.cody.handler.business.presenter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.util.EMLog;
import com.lzy.imagepicker.bean.ImageItem;
import com.cody.handler.business.mapper.ChatMapper;
import com.cody.handler.business.viewmodel.ChatViewModel;
import com.cody.handler.business.viewmodel.ItemMessageViewModel;
import com.cody.handler.framework.IView;
import com.cody.handler.framework.presenter.AbsListPresenter;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.bean.im.BaseMessageBean;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.DataChangeListener;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.xf.utils.FileUtils;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.cody.repository.business.bean.im.BaseMessageBean.MESSAGE_ATTR_KEY;

/**
 * Created by chen.huarong on 2018/7/20.
 */
public class ChatPresenter extends AbsListPresenter<ChatViewModel, ItemMessageViewModel> {

    private static final String TAG = ChatPresenter.class.getSimpleName();
    public final static Object TAG_SCROLL_BOTTOM = ChatPresenter.class.getName();
    private EMConversation conversation;
    private ChatMapper mChatMapper;
    private DataChangeListener mUserInfoChangeListener;

    @Override
    public void attachView(Object tag, IView view, ChatViewModel viewModel) {
        super.attachView(tag, view, viewModel);
        getUserInfo(tag);
    }

    @Override
    public void detachView(Object tag) {
        super.detachView(tag);
        if (mUserInfoChangeListener != null) {
            mUserInfoChangeListener.cancel();
            mUserInfoChangeListener = null;
        }
    }

    public ChatPresenter() {
        mChatMapper = new ChatMapper();
    }

    public void conversationInit() {
        /**
         * 初始化会话，读取本地数据
         */
        if (conversation == null) {
            conversation = EMClient.getInstance().chatManager().getConversation(getViewModel().getToChatUsername()
                    , EMConversation.EMConversationType.Chat
                    , true);
        }
        if (conversation != null) {
            conversation.markAllMessagesAsRead();
            // the number of messages loaded into conversation is getChatOptions().getNumberOfMessagesLoaded
            // you can change this number

            List<EMMessage> msgs = conversation.getAllMessages();
            int msgCount = msgs != null ? msgs.size() : 0;
            if (msgCount < conversation.getAllMsgCount() && msgCount < getViewModel().getPageSize()) {
                String msgId = null;
                if (msgs != null && msgs.size() > 0) {
                    msgId = msgs.get(0).getMsgId();
                    msgs.addAll(0, conversation.loadMoreMsgFromDB(msgId, getViewModel().getPageSize() - msgCount));
                    getViewModel().setHasMore(msgs.size() == getViewModel().getPageSize());
                }
            }
            mChatMapper.mapperList(getViewModel(), msgs, 0, getViewModel().getHasMore());
            refreshUI(TAG_SCROLL_BOTTOM);
            getViewModel().conversationInited = true;
        }
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        getViewModel().setRefresh(false);
        getView().hideLoading();
        if (!getViewModel().conversationInited
                || conversation == null) return;
        if (getViewModel().getHasMore()) {
            List<EMMessage> messages;
            try {
                messages = conversation.loadMoreMsgFromDB(conversation.getAllMessages().size() == 0 ? "" :
                        conversation.getAllMessages().get(0).getMsgId(), getViewModel().getPageSize());
                getViewModel().addAll(0, mChatMapper.<ItemMessageViewModel>mapperList(messages, 0));
            } catch (Exception e1) {
//                swipeRefreshLayout.setRefreshing(false);
//                refreshUI(tag);
                return;
            }
            if (messages.size() > 0) {
//                messageList.refreshSeekTo(messages.size() - 1);
                if (messages.size() != getViewModel().getPageSize()) {
                    getViewModel().setHasMore(false);
                }
            } else {
                getViewModel().setHasMore(false);
            }
//            refreshUI(tag);
        } else {
            ToastUtil.showToast("没有更多消息了");
//            refreshUI(tag);
        }
    }

    /**
     * 消息监听的回调刷新列表有可能并发进行，需求做同步处理，不然mapper的时候会出问题
     */
    public void refreshList() {
        synchronized (this) {
            if (conversation != null) {
                List<EMMessage> msgs = conversation.getAllMessages();
                LogUtil.d("消息数：" + msgs.size());
                conversation.markAllMessagesAsRead();
                mChatMapper.mapperList(getViewModel(), msgs, 0, getViewModel().getHasMore());
                refreshUI(TAG_SCROLL_BOTTOM);
                LogUtil.d(TAG, "刷新消息列表");
            }
        }
    }

    public void markMessageAsRead(String msgId) {
        if (conversation != null) {
            conversation.markMessageAsRead(msgId);
        }
    }

    //send message
    public void sendTextMessage(String content) {
        EMMessage message = EMMessage.createTxtSendMessage(content, getViewModel().getToChatUsername());
        sendMessage(message);
    }

    public void sendVoiceMessage(String filePath, int length) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, getViewModel().getToChatUsername());
        sendMessage(message);
    }

    public void sendImageMessage(String imagePath) {
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, getViewModel().getToChatUsername());
        sendMessage(message);
    }

    public void sendImageListMessage(List<ImageItem> images) {
        List<EMMessage> messages = new ArrayList<>();
        for (ImageItem image : images) {
            if (image != null
                    && !TextUtils.isEmpty(image.path)) {
                EMMessage message = EMMessage.createImageSendMessage(image.path, false, getViewModel()
                        .getToChatUsername());
                if (message != null) {
                    messages.add(message);
                }
            }
        }
        sendListMessage(messages);
    }


    public void sendLocationMessage(double latitude, double longitude, String locationAddress) {
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, getViewModel()
                .getToChatUsername());
        sendMessage(message);
    }

    public void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
        EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, getViewModel()
                .getToChatUsername());
        sendMessage(message);
    }

    public void sendFileMessage(String filePath) {
        EMMessage message = EMMessage.createFileSendMessage(filePath, getViewModel().getToChatUsername());
        sendMessage(message);
    }

    /**
     * 发送推荐商品消息
     *
     * @param beanArray 消息对象
     * @param username  接收者
     */
    public void sendRecommendGoodsMessage(String beanArray, String username) {
        try {
            if (!TextUtils.isEmpty(beanArray)
                    && !TextUtils.isEmpty(username)) {
                Gson gson = new Gson();
                List<BaseMessageBean> list = gson.fromJson(beanArray, new TypeToken<List<BaseMessageBean>>() {
                }.getType());

                List<EMMessage> messages = new ArrayList<>();
                for (BaseMessageBean baseMessageBean : list) {
                    String beanStr = gson.toJson(baseMessageBean);
                    EMMessage message = EMMessage.createTxtSendMessage(" ", username);
                    message.setAttribute(MESSAGE_ATTR_KEY, beanStr);
                    if (message != null) {
                        messages.add(message);
                    }
                }

                sendListMessage(messages);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(EMMessage message) {
        if (message == null) {
            return;
        }

        //保存到会话
        EMClient.getInstance().chatManager().saveMessage(message);
        //添加到消息列表
        addMessage(message);
        LogUtil.d(TAG, "添加到消息列表：" + message.getMsgId());
    }

    public void sendListMessage(List<EMMessage> messages) {
        if (messages == null
                || messages.size() == 0) {
            return;
        }

        //保存到会话
        for (EMMessage message : messages) {
            EMClient.getInstance().chatManager().saveMessage(message);
        }
        //添加到消息列表
        addListMessage(messages);
        LogUtil.d(TAG, "发送消息列表并添加到消息列表：消息数->" + messages.size());
    }


    //===================================================================================


    /**
     * send image
     *
     * @param selectedImage
     * @param filePath
     */
    public void sendPicByUri(Context context, Uri selectedImage, String filePath) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                ToastUtil.showToast("找不到图片");
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(filePath);
            if (!file.exists()) {
                ToastUtil.showToast("找不到图片");
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }

    }

    /**
     * send file
     *
     * @param uri
     */
    public void sendFileByUri(Context context, Uri uri) {
        String filePath = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        if (filePath == null) {
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            ToastUtil.showToast("文件不存在");
            return;
        }
        sendFileMessage(filePath);
    }

    /**
     * download video file
     */
    public void downloadVideo(final Context context, EMMessage message, final String localFilePath, final int
            position) {
        getViewModel().get(position).getVideoViewModel().isDowning.set(true);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                getViewModel().get(position).getVideoViewModel().isDowning.set(false);
                FileUtils.startActionFile(context, new File(localFilePath), "video/mp4");
            }

            @Override
            public void onProgress(final int progress, String status) {
                Log.d("ease", "video progress:" + progress);

            }

            @Override
            public void onError(int error, String msg) {
                Log.e("###", "offline file transfer error:" + msg);
                getViewModel().get(position).getVideoViewModel().isDowning.set(false);
                File file = new File(localFilePath);
                if (file.exists()) {
                    file.delete();
                }
            }
        });
        EMClient.getInstance().chatManager().downloadAttachment(message);
        LogUtil.d(TAG, "正在下载视频");
    }

    /**
     * 播放视频
     *
     * @param context
     * @param message
     * @param position
     */
    public void playVideo(Context context, EMMessage message, int position) {
        String localFilePath = getViewModel().get(position).getVideoViewModel().localUrl;
        if (localFilePath.startsWith("/data/user/0")) {//将私有路径替换成公有路径
            localFilePath = localFilePath.replace("/data/user/0", "/storage/emulated/0/Android/data");
        }
        ((EMVideoMessageBody) message.getBody()).setLocalUrl(localFilePath);
        LogUtil.d(TAG, "视频文件本地路径:" + localFilePath);
        EMMessage message1 = getViewModel().get(position).getMessage();
        if (localFilePath != null && new File(localFilePath).exists()) {
            try {
                FileUtils.startActionFile(context, new File(localFilePath), "video/mp4");
            } catch (Exception e) {
                boolean deleted = new File(localFilePath).delete();
                if (deleted) {
                    downloadVideo(context, message1, localFilePath, position);
                } else {
                    ToastUtil.showToast("请重试");
                }
            }
        } else {
            EMLog.d(TAG, "从服务器下载视频文件");
            downloadVideo(context, message1, localFilePath, position);
        }
    }

    private void getUserInfo(Object tag) {
        if (mUserInfoChangeListener != null) return;
        mUserInfoChangeListener = UserInfoManager.getUserInfo(tag, false, true, getViewModel().getToChatUsername(),
                new DataCallBack<UserInfoBean>() {
                    @Override
                    public void onSuccess(UserInfoBean user) {
                        super.onSuccess(user);
                        if (user != null) {
                            getViewModel().setGroupId(user.getGroupId());
                            getViewModel().setUsername(UserInfoManager.handleUserName(user));
                            mChatMapper.setAvatar(user.getAvatar());
                            getViewModel().setGroupName("分组:" + user.getGroupName());
                            if (!getViewModel().conversationInited) {
                                conversationInit();
                            } else {
//                                refreshList();
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                        super.onError(error);
                        if (!getViewModel().conversationInited) {
                            conversationInit();
                        } else {
//                            refreshList();
                        }
                    }
                });
    }

    public void addMessage(EMMessage message) {
        getViewModel().add(mChatMapper.<ItemMessageViewModel>mapper(new ItemMessageViewModel(), message));
        refreshUI(TAG_SCROLL_BOTTOM);
    }

    private void addListMessage(List<EMMessage> messages) {
        mChatMapper.mapperList(getViewModel(), messages, getViewModel().getPosition(), getViewModel().getHasMore());
        refreshUI(TAG_SCROLL_BOTTOM);
    }
}
