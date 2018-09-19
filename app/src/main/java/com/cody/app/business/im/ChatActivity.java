package com.cody.app.business.im;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.PathUtil;
import com.lzy.imagepicker.bean.ImageItem;
import com.cody.app.R;
import com.cody.app.business.UserInfoActivity;
import com.cody.app.business.chat.ChooseCouponImActivity;
import com.cody.app.business.chat.RecommendGoodsActivity;
import com.cody.app.business.chat.SwitchSaleActivity;
import com.cody.app.business.easeui.EaseConstant;
import com.cody.app.business.easeui.ImManager;
import com.cody.app.business.easeui.domain.EMMessageManager;
import com.cody.app.business.easeui.ui.EaseGaodeMapActivity;
import com.cody.app.business.easeui.ui.EaseShowNormalFileActivity;
import com.cody.app.business.easeui.ui.RecorderVideoActivity;
import com.cody.app.business.easeui.ui.VideoCallActivity;
import com.cody.app.business.im.adapter.ChatAdapter;
import com.cody.app.business.personal.PersonalInformationActivity;
import com.cody.app.business.widget.EaseChatExtendMenu;
import com.cody.app.business.widget.EaseChatInputMenu;
import com.cody.app.business.widget.EaseChatRowVoicePlayer;
import com.cody.app.business.widget.EaseVoiceRecorderView;
import com.cody.app.business.widget.QuickReplyPopupWindow;
import com.cody.app.databinding.ActivityChatBinding;
import com.cody.app.framework.EventBus.SimpleEventBus;
import com.cody.app.framework.EventBus.SimpleEventBusKey;
import com.cody.app.framework.activity.AbsListActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.app.framework.widget.image.ImageViewDelegate;
import com.cody.app.framework.widget.image.OnImageViewListener;
import com.cody.app.utils.FileUtil;
import com.cody.handler.business.presenter.ChatPresenter;
import com.cody.handler.business.viewmodel.ChatViewModel;
import com.cody.handler.business.viewmodel.ItemMessageViewModel;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.bean.im.CustomMessage;
import com.cody.repository.business.database.DataCallBack;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.KeyboardChangeListener;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.NetUtil;
import com.cody.xf.utils.NetworkUtil;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 聊天页面
 * 作者:chenhuarong
 */
public class ChatActivity extends AbsListActivity<ChatPresenter, ChatViewModel, ItemMessageViewModel,
        ActivityChatBinding> implements EMMessageListener, KeyboardChangeListener.KeyBoardListener,
        SimpleEventBus.EventReceiver {

    private static final int PERMISSION_CODE = 0;
    private static final int PERMISSION_CODE_TAKE_PICTURE = 2;
    private static final int PERMISSION_CODE_VIDEO = 3;
    public static final int REQUEST_CODE_CAMERA = 2;
    public final static int REQUEST_RECOMMEND_GOODS = 4;
    public static final int REQUEST_CODE_FILE = 5;
    public final static int REQUEST_SWITCH_GUIDER = 6;
    public final static int REQUEST_CHOOSE_COUPON = 7;
    public static final int REQUEST_CODE_MAP = 8;

    public static final int ITEM_PICTURE = 1;//照片
    public static final int ITEM_TAKE_PICTURE = 2;//拍摄
    public static final int ITEM_VIDEO = 3;//视频聊天
    public static final int ITEM_GOODS = 4;//推荐商品
    public static final int ITEM_FILE = 5;//文件
    public static final int ITEM_GUIDE = 6;//切换导购
    public static final int ITEM_COUPON = 7;//优惠券
    public static final int ITEM_LOCATION = 8;//定位

    protected int[] itemStrings = {
            R.string.attach_picture
            , R.string.attach_take_pic
            , R.string.attach_video
            , R.string.attach_goods
//            , R.string.attach_file
            , R.string.attach_guide
            , R.string.attach_coupon};
    //            , R.string.attach_location};
    protected int[] itemDrawables = {
            R.drawable.ic_photo
            , R.drawable.ic_paishe
            , R.drawable.ic_shipin
            , R.drawable.ic_tuijianshangp
//            , R.drawable.ic_wenjian
            , R.drawable.ic_qiehuandaogou
            , R.drawable.ic_youhuiquan};
    //            , R.drawable.ic_weizhi};
    protected int[] itemIds = {ITEM_PICTURE
            , ITEM_TAKE_PICTURE
            , ITEM_VIDEO
            , ITEM_GOODS
//            , ITEM_FILE
            , ITEM_GUIDE
            , ITEM_COUPON};
//            , ITEM_LOCATION};

    private QuickReplyPopupWindow popwindow;
    protected ProgressDialog mProgressDialog;
    private EaseChatRowVoicePlayer mVoicePlayer;
    private AnimationDrawable voiceAnimation;
    private KeyboardChangeListener keyboardChangeListener;
    private ImageViewDelegate mImageViewDelegate;

    private EaseChatExtendMenu.EaseChatExtendMenuItemClickListener mMenuItemClickListener = new EaseChatExtendMenu
            .EaseChatExtendMenuItemClickListener() {


        @Override
        public void onClick(int itemId, View view) {
            Intent intent;
            hideExtendMenuContainer();
            switch (itemId) {
                case ITEM_PICTURE://照片
                    //埋点-龙果APP咨询页照片
                    BuryingPointUtils.build(ChatActivity.class, 4004).submitF();
                    mImageViewDelegate.selectImage(9);
                    break;
                case ITEM_TAKE_PICTURE://拍摄
                    //埋点-龙果APP咨询页拍摄
                    BuryingPointUtils.build(ChatActivity.class, 4005).submitF();
                    if (!EasyPermissions.hasPermissions(ChatActivity.this
                            , Manifest.permission.RECORD_AUDIO
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE
                            , Manifest.permission.CAMERA)) {
                        EasyPermissions.requestPermissions(ChatActivity.this
                                , getString(R.string.permission_record)
                                , PERMISSION_CODE_TAKE_PICTURE
                                , Manifest.permission.RECORD_AUDIO
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.CAMERA);
                        return;
                    }
                    intent = new Intent(ChatActivity.this, RecorderVideoActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    break;
                case ITEM_VIDEO://视频聊天
                    //埋点-龙果APP咨询页视频聊天
                    BuryingPointUtils.build(ChatActivity.class, 4006).submitF();
                    startVideoCall();
                    break;
                case ITEM_GOODS://推荐商品
                    //埋点-龙果APP咨询页推荐商品
                    BuryingPointUtils.build(ChatActivity.class, 4007).submitF();
                    RecommendGoodsActivity.startRecommendGoods(5, REQUEST_RECOMMEND_GOODS);
                    break;
                case ITEM_FILE://文件
                    //埋点-龙果APP咨询页文件
                    BuryingPointUtils.build(ChatActivity.class, 4008).submitF();
                    selectFileFromLocal();
                    break;
                case ITEM_GUIDE://切换导购
                    //埋点-龙果APP咨询页切换导购
                    BuryingPointUtils.build(ChatActivity.class, 4009).submitF();
                    //隐藏自定义键盘
                    intent = new Intent(ChatActivity.this, SwitchSaleActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("userName", getViewModel().getCurrentLoginUser());
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, REQUEST_SWITCH_GUIDER);
                    break;
                case ITEM_COUPON://优惠券
                    //埋点-龙果APP咨询页优惠券
                    BuryingPointUtils.build(ChatActivity.class, 4010).submitF();
                    UserInfoManager.getUserInfo(TAG
                            , getViewModel().getToChatUsername()
                            , new DataCallBack<UserInfoBean>() {
                                @Override
                                public void onSuccess(UserInfoBean userInfoBean) {
                                    super.onSuccess(userInfoBean);
                                    Intent intent = new Intent(ChatActivity.this, ChooseCouponImActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("userOpenId", userInfoBean.getOpenId());
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, REQUEST_CHOOSE_COUPON);
                                }
                            });
                    break;
                case ITEM_LOCATION://位置
                    //埋点-龙果APP咨询页位置
                    BuryingPointUtils.build(ChatActivity.class, 4011).submitF();
                    startActivityForResult(new Intent(ChatActivity.this, EaseGaodeMapActivity.class),
                            REQUEST_CODE_MAP);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * @param username 用户名
     */
    public static void startChat(String username) {
        Bundle bundle = new Bundle();
        bundle.putString(EaseConstant.EXTRA_USER_ID, username);
        ActivityUtil.navigateTo(ChatActivity.class, bundle);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImManager.getInstance().getNotifier().reset();
        registerExtendMenuItem();
        //添加消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        mImageViewDelegate = new ImageViewDelegate(new OnImageViewListener() {
            @Override
            public void onPreview(int id, List<ImageItem> images) {

            }

            @Override
            public void onPickImage(int id, List<ImageItem> images) {
                getPresenter().sendImageListMessage(images);
            }
        }, this);
        mImageViewDelegate.setCanDelete(false);
        //添加键盘打开收起监听
        keyboardChangeListener = new KeyboardChangeListener(this);
        keyboardChangeListener.setKeyBoardListener(this);
        //添加连接状态监听
        SimpleEventBus.register(this, this);
        getBinding().inputMenu.init();
        getBinding().inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {

            @Override
            public void onSendMessage(String content) {
                getPresenter().sendTextMessage(content);
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                if (!EasyPermissions.hasPermissions(ChatActivity.this, Manifest.permission.RECORD_AUDIO, Manifest
                        .permission.WRITE_EXTERNAL_STORAGE)) {
                    EasyPermissions.requestPermissions(ChatActivity.this
                            , getString(R.string.permission_record),
                            PERMISSION_CODE, Manifest.permission.RECORD_AUDIO
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    return false;
                }

                return getBinding().voiceRecorder.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView
                        .EaseVoiceRecorderCallback() {

                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        getPresenter().sendVoiceMessage(voiceFilePath, voiceTimeLength);
                    }
                });
            }

            @Override
            public void onQuickMsgClicked() {
                //埋点-龙果app快捷回复
                BuryingPointUtils.build(ChatActivity.class, 4003).submitF();
                showQuickMsgPopWindow();
            }

            @Override
            public void onToggleExtendClicked() {
                scrollBottom();
            }
        });
        //检查网络连接
        checkConnected();
        getBinding().fwList.setPushRefreshEnable(false);
        //recyclerView加载完成后滚动到最底部
        getBinding().fwList.getRecyclerView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollBottom();
                getBinding().fwList.getRecyclerView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        getBinding().fwList.getRecyclerView().setFocusable(false);
    }

    /**
     * register extend menu, item id need > 3 if you override this method and keep exist item
     */
    protected void registerExtendMenuItem() {
        for (int i = 0; i < itemStrings.length; i++) {
            getBinding().inputMenu.registerExtendMenuItem(itemStrings[i], itemDrawables[i], itemIds[i],
                    mMenuItemClickListener);
        }
    }

    @Override
    protected ChatPresenter buildPresenter() {
        return new ChatPresenter();
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.chat_empty_view;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_chat;
    }

    @Override
    protected ChatViewModel buildViewModel(Bundle savedInstanceState) {
        final ChatViewModel chatViewModel = new ChatViewModel();
        chatViewModel.setHasEndInfo(false);
        Intent intent = getIntent();
        if (intent != null) {
            chatViewModel.setToChatUsername(intent.getStringExtra(EaseConstant.EXTRA_USER_ID));
        }

        chatViewModel.setHasEndInfo(false);
        return chatViewModel;
    }

    @Override
    protected BaseRecycleViewAdapter<ItemMessageViewModel> buildRecycleViewAdapter() {
        return new ChatAdapter(this, getViewModel());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.headerRightBtn:
                //埋点-龙果APP咨询页客户信息
                BuryingPointUtils.build(ChatActivity.class, 4002).submitF();
                UserInfoActivity.startUserInfo(getViewModel().getToChatUsername());
                break;
//            case R.id.groupBtn:
//                //埋点-龙果APP咨询页分组设置
//                BuryingPointUtils.build(ChatActivity.class, 4001).submitF();
//                if (TextUtils.isEmpty(getViewModel().getGroupId().get())) {
//                    GroupModifyActivity.addToGroup(getViewModel().getToChatUsername());
//                } else {
//                    GroupModifyActivity.editGroup(getViewModel().getToChatUsername(), getViewModel().getGroupId().get
//                            ());
//                }
//                break;
            case R.id.ll_im_tips:
                reConnect();
                break;

        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onUpdate(Object... args) {
        if (args != null
                && args.length > 0
                && ChatPresenter.TAG_SCROLL_BOTTOM == args[0]) {//不是加载更多的时候才要滑动到底部
            scrollBottom();
        }
        ImManager.getInstance().getNotifier().reset();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageViewDelegate.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_PHOTO
//                && resultCode == 1004) {//照片
//            if (data != null) {
//                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
//                        .EXTRA_RESULT_ITEMS);
//
//            }
//            return;
//        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA://拍摄
                    if (data != null) {
                        String filePath = data.getStringExtra("path");
                        int duration = data.getIntExtra("dur", 0);
                        if (duration == 0) {//照片
                            getPresenter().sendPicByUri(this, Uri.parse(filePath), filePath);
                        } else { //视频
                            File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System
                                    .currentTimeMillis());
                            try {
                                FileOutputStream fos = new FileOutputStream(file);
                                Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(filePath, 3);
                                ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                fos.close();
                                getPresenter().sendVideoMessage(filePath, file.getAbsolutePath(), duration);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case REQUEST_SWITCH_GUIDER://切换导购
                case REQUEST_CHOOSE_COUPON:  //优惠券
                    String couponBean = data.getStringExtra("chatbean");
                    if (!TextUtils.isEmpty(couponBean)) {
                        EMMessage message = EMMessageManager.createCustomMessage(couponBean, getViewModel()
                                .getToChatUsername());
                        getPresenter().sendMessage(message);
                    }
                    break;
                case REQUEST_RECOMMEND_GOODS://推荐商品
                    String beanArray = data.getStringExtra("chatbeanArray");
                    getPresenter().sendRecommendGoodsMessage(beanArray, getViewModel().getToChatUsername());
                    break;
                case REQUEST_CODE_FILE://文件
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            getPresenter().sendFileByUri(this, uri);
                        }
                    }
                    break;
                case REQUEST_CODE_MAP://地图
                    double latitude = data.getDoubleExtra("latitude", 0);
                    double longitude = data.getDoubleExtra("longitude", 0);
                    String locationAddress = data.getStringExtra("address");
                    if (locationAddress != null && !locationAddress.equals("")) {
                        getPresenter().sendLocationMessage(latitude, longitude, locationAddress);
                    } else {
                        ToastUtil.showToast(R.string.unable_to_get_loaction);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, final View view, final int position, long id) {
        final EMMessage message = getViewModel().get(position).getMessage();
        ItemMessageViewModel itemMessageViewModel = getViewModel().get(position);
        if (id == R.id.iv_userhead) {//头像
            String userImid = getViewModel().get(position).getMessage().getFrom();
            if (getViewModel().getCurrentLoginUser()
                    .equals(userImid)) {//自己
                ActivityUtil.navigateTo(PersonalInformationActivity.class);
            } else {//聊天对象
                UserInfoActivity.startUserInfo(userImid);
            }
        } else if (id == R.id.bubble) {//消息体
            switch (getViewModel().get(position).itemIMType) {
                case TXT://文本
                    if (itemMessageViewModel.diyMessageType == CustomMessage.PersonVideo) {//视频通话状态
                        startVideoCall();
                    }
                    break;
                case LOCATION://位置
                    break;
                case FILE://文件
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
                case IMAGE://图片
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
                case VOICE://语音
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
                case VIDEO://视频
                    getPresenter().playVideo(this, message, position);
                    break;
                default:
                    break;

            }
        } else if (id == R.id.msg_status) {//重发
            if (!NetworkUtil.isConnected(this)) {
                ToastUtil.showToast(R.string.network_unavailable);
                return;
            }

            if (!EMClient.getInstance().isConnected()) {
                ToastUtil.showToast("您已掉线，请重新连接");
                return;
            }
            EMMessage reSendMessage = getViewModel().get(position).getMessage();
            reSendMessage.setStatus(EMMessage.Status.CREATE);
            reSendMessage.setMsgTime(System.currentTimeMillis());
            if (position != 0 && position != getViewModel().size() - 1) {
                getViewModel().remove(position);
                getPresenter().sendMessage(reSendMessage);
            } else {
                EMClient.getInstance().chatManager().sendMessage(reSendMessage);
            }
        }
    }

    private void showQuickMsgPopWindow() {
        if (popwindow == null) {
            popwindow = new QuickReplyPopupWindow(ChatActivity.this, getViewModel().getToChatUsername());
        }
        popwindow.updateMessage();
        popwindow.setBackgroundAlpha(0.5f);
        popwindow.showAtLocation(findViewById(R.id.container), Gravity
                .BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        popwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popwindow.setBackgroundAlpha(1.0f);
                if (popwindow.isNeedRefresh()) {
                    updateList();
                }
            }
        });
    }


    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        for (final EMMessage message : messages) {
            String username;

            username = message.getFrom();
            // if the message is for current conversation
            if (username.equals(getViewModel().getToChatUsername())
                    || message.getTo().equals(getViewModel().getToChatUsername())
                    || message.conversationId().equals(getViewModel().getToChatUsername())) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getPresenter().addMessage(message);
//                        EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
                        getPresenter().markMessageAsRead(message.getMsgId());
                    }
                });
            }
        }
//        updateList();
        LogUtil.d(TAG, "接收到的消息数：" + messages.size());
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
//        updateList();
    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                updateList();
//            }
//        });
    }

    @Override
    public void onMessageDelivered(List<EMMessage> messages) {
//        updateList();
    }

    @Override
    public void onMessageRecalled(List<EMMessage> messages) {
//        updateList();
    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                updateList();
//            }
//        });
    }

    private void updateList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getPresenter().refreshList();
            }
        });
    }

    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EasyPermissions.hasPermissions(this
                , Manifest.permission.RECORD_AUDIO
                , Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this
                    , getString(R.string.permission_record)
                    , PERMISSION_CODE_VIDEO
                    , Manifest.permission.RECORD_AUDIO
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }

        if (!EMClient.getInstance().isConnected())
            ToastUtil.showToast(R.string.not_connect_to_server);
        else {
            startActivity(new Intent(this
                    , VideoCallActivity.class)
                    .putExtra("username", getViewModel().getToChatUsername())
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            hideExtendMenuContainer();
        }
    }

    /**
     * 检查连接状态
     */
    private void checkConnected() {
        handlerStateChange(EMClient.getInstance().isConnected());
    }

    /**
     * 连接状态改变
     */
    private void handlerStateChange(final boolean isOnline) {
        if (mProgressDialog != null
                && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        getViewModel().setIsOnline(isOnline);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(this);
        keyboardChangeListener.destroy();
        SimpleEventBus.unRegister(this, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        if (requestCode == PERMISSION_CODE_TAKE_PICTURE) {//拍摄
            startActivityForResult(new Intent(this, RecorderVideoActivity.class)
                    , REQUEST_CODE_CAMERA);
        } else if (requestCode == PERMISSION_CODE_VIDEO) {//视频聊天
            startVideoCall();
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
            try {
                EMClient.getInstance().chatManager().setVoiceMessageListened(message);
                EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
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
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_FILE);
    }

    /**
     * 重新连接IM
     */
    private void reConnect() {
        if (!NetUtil.isNetworkConnected(this)) {//网络不可用
            ToastUtil.showToast(R.string.network_unavailable);
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(ChatActivity.this);
            mProgressDialog.setMessage("重新连接中...");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
        String imid = UserInfoManager.getImId();
        if (TextUtils.isEmpty(imid)) {
            ToastUtil.showToast("用户名为空，请重新登录");
            mProgressDialog.dismiss();
        } else {
            EMClient.getInstance().login(imid, imid, new EMCallBack() {
                @Override
                public void onSuccess() {
                    handlerStateChange(true);
                }

                @Override
                public void onError(int i, final String s) {
                    if (i == EMError.USER_ALREADY_LOGIN) {
                        handlerStateChange(false);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.dismiss();
                                ToastUtil.showToast(s);
                            }
                        });
                    }
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }

    /**
     * 列表滚动到最底部
     */
    private void scrollBottom() {
        if (getViewModel().size() - 1 > 0 && getBinding().fwList.getLayoutManager() != null) {//不是加载更多的时候才要滑动到底部
            getBinding().fwList.getLayoutManager().scrollToPosition(getViewModel().size() - 1);
            LogUtil.d(TAG, "滚动到底部");
        }
    }

    private void hideExtendMenuContainer() {
        getBinding().inputMenu.hideExtendMenuContainer();
    }

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        if (isShow) {
            scrollBottom();
        }
    }

    @Override
    public void onEventReceiver(String key) {
        if (SimpleEventBusKey.ON_CONNECTED.equals(key)) {
            handlerStateChange(true);
        } else if (SimpleEventBusKey.ON_DISCONNECTED.equals(key)) {
            handlerStateChange(false);
        } else if (SimpleEventBusKey.REFRESH_CHAT.equals(key)) {
            getPresenter().refreshList();
        }
    }
}
