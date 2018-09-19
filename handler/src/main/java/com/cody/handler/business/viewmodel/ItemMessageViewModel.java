package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.DateUtils;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.handler.framework.viewmodel.XItemViewModel;
import com.cody.repository.business.bean.im.CustomMessage;
import com.cody.xf.utils.LogUtil;

import java.util.Date;


/**
 * Created by chen.huarong on 2018/7/20.
 */
public class ItemMessageViewModel extends XItemViewModel {

    private static final String TAG = ItemMessageViewModel.class.getSimpleName();
    public EMMessage.Type itemIMType = EMMessage.Type.TXT;
    private EMMessage mMessage;
    public int diyMessageType = CustomMessage.NO_DIY;//自定义消息类型
    public final ObservableField<String> userAvatar = new ObservableField<>();//用户头像
    public long preTimeStamp;//上一条消息的时间
    private final ObservableField<EMMessage.Status> messageStatus = new ObservableField<>(EMMessage.Status.SUCCESS);
    //消息发送状态
    public final ObservableField<String> timeStamp = new ObservableField<>();//当前消息的时间
    private SysWelWordsMessageViewModel mImSysWelWordsViewModel;//系统欢迎语
    private ImageMessageViewModel mImageMessageViewModel;//图片
    private GoodsViewModel mGoodsViewModel;//商品
    private VoiceViewModel mVoiceViewModel;//语音
    private VideoViewModel mVideoViewModel;//视频
    private FileViewModel mFileViewModel;//文件
    private CouponViewModel mCouponViewModel;//优惠券
    private SwitchGuideViewModel mSwitchGuideViewModel;//切换导购
    private MessageStatusViewModel mMessageStatusViewModel;//消息状态
    private OrderViewModel mOrderViewModel;//用户订单
    private String textMessage;//文本消息

    private String userLikeMessage;//点赞

    public OrderViewModel getOrderViewModel() {
        if (mOrderViewModel == null) {
            mOrderViewModel = new OrderViewModel();
        }
        return mOrderViewModel;
    }

    public void setOrderViewModel(OrderViewModel orderViewModel) {
        mOrderViewModel = orderViewModel;
    }

    public MessageStatusViewModel getMessageStatusViewModel() {
        if (mMessageStatusViewModel == null) {
            mMessageStatusViewModel = new MessageStatusViewModel();
        }
        return mMessageStatusViewModel;
    }

    public void setMessageStatusViewModel(MessageStatusViewModel messageStatusViewModel) {
        mMessageStatusViewModel = messageStatusViewModel;
    }

    public ObservableField<EMMessage.Status> getMessageStatus() {
        return messageStatus;
    }

    public SwitchGuideViewModel getSwitchGuideViewModel() {
        if (mSwitchGuideViewModel == null) {
            mSwitchGuideViewModel = new SwitchGuideViewModel();
        }
        return mSwitchGuideViewModel;
    }

    public void setSwitchGuideViewModel(SwitchGuideViewModel switchGuideViewModel) {
        mSwitchGuideViewModel = switchGuideViewModel;
    }

    public EMMessage getMessage() {
        return mMessage;
    }

    public void setMessage(final EMMessage message) {
        mMessage = message;
        messageStatus.set(message.status());
        if (mMessage.isAcked()) {
            getMessageStatusViewModel().ackText.set("已读");
        } else {
            getMessageStatusViewModel().ackText.set
                    ("未读");
        }

        mMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                messageStatus.set(EMMessage.Status.SUCCESS);
                getMessageStatusViewModel().isAckShow.set(true);
                getMessageStatusViewModel().isLoadShow.set(false);
                getMessageStatusViewModel().isMsgStatusShow.set(false);
                LogUtil.d(TAG, "消息发送成功");
            }

            @Override
            public void onError(int code, String error) {
                messageStatus.set(EMMessage.Status.FAIL);
                getMessageStatusViewModel().isMsgStatusShow.set(true);
                getMessageStatusViewModel().isAckShow.set(false);
                getMessageStatusViewModel().isLoadShow.set(false);
                LogUtil.d(TAG, "消息发送错误");
            }

            @Override
            public void onProgress(int progress, String status) {
                messageStatus.set(EMMessage.Status.INPROGRESS);
                getMessageStatusViewModel().progress.set(progress + "%");
                getMessageStatusViewModel().isLoadShow.set(true);
                getMessageStatusViewModel().isMsgStatusShow.set(false);
                getMessageStatusViewModel().isAckShow.set(false);
            }
        });

        //发送的消息需要设置消息状态显示
        if (message.direct() == EMMessage.Direct.SEND) {
            getMessageStatusViewModel().setMessageStatus(message);
        }
    }

    public SysWelWordsMessageViewModel getImSysWelWordsViewModel() {
        if (mImSysWelWordsViewModel == null) {
            mImSysWelWordsViewModel = new SysWelWordsMessageViewModel();
        }
        return mImSysWelWordsViewModel;
    }

    public void setImSysWelWordsViewModel(SysWelWordsMessageViewModel imSysWelWordsViewModel) {
        mImSysWelWordsViewModel = imSysWelWordsViewModel;
    }

    public ImageMessageViewModel getImageMessageViewModel() {
        if (mImageMessageViewModel == null) {
            mImageMessageViewModel = new ImageMessageViewModel();
        }
        return mImageMessageViewModel;
    }

    public void setImageMessageViewModel(ImageMessageViewModel imageMessageViewModel) {
        mImageMessageViewModel = imageMessageViewModel;
    }

    public GoodsViewModel getGoodsViewModel() {
        if (mGoodsViewModel == null) {
            mGoodsViewModel = new GoodsViewModel();
        }
        return mGoodsViewModel;
    }

    public void setGoodsViewModel(GoodsViewModel goodsViewModel) {
        mGoodsViewModel = goodsViewModel;
    }

    public VoiceViewModel getVoiceViewModel() {
        if (mVoiceViewModel == null) {
            mVoiceViewModel = new VoiceViewModel();
        }
        return mVoiceViewModel;
    }

    public void setVoiceViewModel(VoiceViewModel voiceViewModel) {
        mVoiceViewModel = voiceViewModel;
    }

    public VideoViewModel getVideoViewModel() {
        if (mVideoViewModel == null) {
            mVideoViewModel = new VideoViewModel();
        }
        return mVideoViewModel;
    }

    public void setVideoViewModel(VideoViewModel videoViewModel) {
        mVideoViewModel = videoViewModel;
    }

    public FileViewModel getFileViewModel() {
        if (mFileViewModel == null) {
            mFileViewModel = new FileViewModel();
        }
        return mFileViewModel;
    }

    public void setFileViewModel(FileViewModel fileViewModel) {
        mFileViewModel = fileViewModel;
    }

    public CouponViewModel getCouponViewModel() {
        if (mCouponViewModel == null) {
            mCouponViewModel = new CouponViewModel();
        }
        return mCouponViewModel;
    }

    public void setCouponViewModel(CouponViewModel couponViewModel) {
        mCouponViewModel = couponViewModel;
    }

    public void setUserAvatar(String avatar) {
        this.userAvatar.set(avatar);
    }

    public boolean isShowTimeStamp() {
        if (mMessage == null) return false;
        timeStamp.set(DateUtils.getTimestampString(new Date(mMessage.getMsgTime())));
        if (isFirstItem().get()) {
            return true;
        } else {
            // show time stamp if interval with last message is > 30 seconds
            if (preTimeStamp != 0
                    && isCloseEnough(mMessage.getMsgTime(), preTimeStamp)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    /**
     * 获取文本消息
     *
     * @return
     */
    public String getTextMessage() {
        return textMessage;
    }

    //系统欢迎语
    public static class SysWelWordsMessageViewModel extends XItemViewModel {
        public int type;
        public String sysWordsStr;                              ///< 系统欢迎语文本消息
        public ListViewModel<ItemSysWelWordsMessageViewModel> sysGoodsList = new ListViewModel<>();    ///系统欢迎语推荐商品列表
        public String extraStr1;                                ///扩展字段1
        public String extraStr2;                                ///扩展字段2

        public static class ItemSysWelWordsMessageViewModel extends XItemViewModel {
            public String imageUrl;               ///< 商品的图片URL
            public String merchandiseName;        ///< 商品的名称
            public String merchandisePrice;           ///< 商品的价格
            public String merchandiseId;          ///< 商品的ID
            public String merchandiseSku;          ///< 商品的sku

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                if (!super.equals(o)) return false;

                ItemSysWelWordsMessageViewModel that = (ItemSysWelWordsMessageViewModel) o;

                if (merchandisePrice != null ? !merchandisePrice.equals(that.merchandisePrice) : that
                        .merchandisePrice != null)
                    return false;
                if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
                    return false;
                if (merchandiseName != null ? !merchandiseName.equals(that.merchandiseName) : that.merchandiseName !=
                        null)
                    return false;
                if (merchandiseId != null ? !merchandiseId.equals(that.merchandiseId) : that.merchandiseId != null)
                    return false;
                return merchandiseSku != null ? merchandiseSku.equals(that.merchandiseSku) : that.merchandiseSku ==
                        null;
            }

            @Override
            public int hashCode() {
                int result = super.hashCode();
                long temp;
                result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
                result = 31 * result + (merchandiseName != null ? merchandiseName.hashCode() : 0);
                result = 31 * result + (merchandisePrice != null ? merchandisePrice.hashCode() : 0);
                result = 31 * result + (merchandiseId != null ? merchandiseId.hashCode() : 0);
                result = 31 * result + (merchandiseSku != null ? merchandiseSku.hashCode() : 0);
                return result;
            }
        }

        public boolean hasGoods() {
            return sysGoodsList != null && sysGoodsList.size() > 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            SysWelWordsMessageViewModel that = (SysWelWordsMessageViewModel) o;

//            if (type != that.type) return false;
            if (sysWordsStr != null ? !sysWordsStr.equals(that.sysWordsStr) : that.sysWordsStr != null)
                return false;
            return sysGoodsList != null ? sysGoodsList.equals(that.sysGoodsList) : that.sysGoodsList == null;
//            if (extraStr1 != null ? !extraStr1.equals(that.extraStr1) : that.extraStr1 != null)
//                return false;
//            return extraStr2 != null ? extraStr2.equals(that.extraStr2) : that.extraStr2 == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
//            result = 31 * result + type;
            result = 31 * result + (sysWordsStr != null ? sysWordsStr.hashCode() : 0);
            result = 31 * result + (sysGoodsList != null ? sysGoodsList.hashCode() : 0);
//            result = 31 * result + (extraStr1 != null ? extraStr1.hashCode() : 0);
//            result = 31 * result + (extraStr2 != null ? extraStr2.hashCode() : 0);
            return result;
        }
    }

    //图片
    public static class ImageMessageViewModel extends XItemViewModel {
        public String remoteUrl;
        public String localUrl;//本地原图路径
        public String thumbnailLocalPath;//本地缩略图路径
        public String thumbnailRemoteUrl;//远程缩略图路径
        private ObservableField<Integer> width = new ObservableField<>();
        private ObservableField<Integer> height = new ObservableField<>();

        public ObservableField<Integer> getWidth() {
            LogUtil.d(TAG, "图片宽：" + width.get());
            return width;
        }

        public void setWidth(int width) {
            this.width.set(width);
        }

        public ObservableField<Integer> getHeight() {
            LogUtil.d(TAG, "图片高：" + height.get());
            return height;
        }

        public void setHeight(int height) {
            this.height.set(height);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            ImageMessageViewModel that = (ImageMessageViewModel) o;

            if (remoteUrl != null ? !remoteUrl.equals(that.remoteUrl) : that.remoteUrl != null) return false;
            if (localUrl != null ? !localUrl.equals(that.localUrl) : that.localUrl != null) return false;
            if (thumbnailLocalPath != null ? !thumbnailLocalPath.equals(that.thumbnailLocalPath) : that
                    .thumbnailLocalPath != null)
                return false;
            if (thumbnailRemoteUrl != null ? !thumbnailRemoteUrl.equals(that.thumbnailRemoteUrl) : that
                    .thumbnailRemoteUrl != null)
                return false;
            if (width.get() != null ? !width.get().equals(that.width.get()) : that.width.get() != null) return false;
            return height.get() != null ? height.get().equals(that.height.get()) : that.height.get() == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (remoteUrl != null ? remoteUrl.hashCode() : 0);
            result = 31 * result + (localUrl != null ? localUrl.hashCode() : 0);
            result = 31 * result + (thumbnailLocalPath != null ? thumbnailLocalPath.hashCode() : 0);
            result = 31 * result + (thumbnailRemoteUrl != null ? thumbnailRemoteUrl.hashCode() : 0);
            result = 31 * result + (width.get() != null ? width.get().hashCode() : 0);
            result = 31 * result + (height.get() != null ? height.get().hashCode() : 0);
            return result;
        }
    }

    //商品
    public static class GoodsViewModel extends XItemViewModel {
        public int type;                        ///< 自定义消息类型
        public String imageUrl;               ///< 商品的图片URL
        public String merchandiseName;        ///< 商品的名称
        public String merchandisePrice;           ///< 商品的价格
        public String merchandiseID;          ///< 商品的ID

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            GoodsViewModel that = (GoodsViewModel) o;

//            if (type != that.type) return false;
            if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
                return false;
            if (merchandiseName != null ? !merchandiseName.equals(that.merchandiseName) : that.merchandiseName != null)
                return false;
            if (merchandisePrice != null ? !merchandisePrice.equals(that.merchandisePrice) : that.merchandisePrice !=
                    null)
                return false;
            return merchandiseID != null ? merchandiseID.equals(that.merchandiseID) : that.merchandiseID == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
//            result = 31 * result + type;
            result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
            result = 31 * result + (merchandiseName != null ? merchandiseName.hashCode() : 0);
            result = 31 * result + (merchandisePrice != null ? merchandisePrice.hashCode() : 0);
            result = 31 * result + (merchandiseID != null ? merchandiseID.hashCode() : 0);
            return result;
        }
    }

    //语音
    public static class VoiceViewModel extends XItemViewModel {
        public ObservableBoolean isListened = new ObservableBoolean(false);
        public String length;//长度
//        public String messageId;//equals用（其实字段没办法作差别）

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            VoiceViewModel that = (VoiceViewModel) o;

//            if (isListened != null ? !isListened.equals(that.isListened) : that.isListened != null) return false;
            return length != null ? length.equals(that.length) : that.length == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
//            result = 31 * result + (isListened != null ? isListened.hashCode() : 0);
            result = 31 * result + (length != null ? length.hashCode() : 0);
            return result;
        }
    }

    //视频
    public static class VideoViewModel extends XItemViewModel {
        public String localThumb;//本地图片
        public String remoteThumb;//远程图片
        public String localUrl;//视频地址
        public int duration;//时长
        public final ObservableBoolean isDowning = new ObservableBoolean(false);

        public String getDurationText() {
            return duration + "秒";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            VideoViewModel that = (VideoViewModel) o;

            if (duration != that.duration) return false;
            if (localThumb != null ? !localThumb.equals(that.localThumb) : that.localThumb != null) return false;
            if (remoteThumb != null ? !remoteThumb.equals(that.remoteThumb) : that.remoteThumb != null) return false;
            if (localUrl != null ? !localUrl.equals(that.localUrl) : that.localUrl != null) return false;
            return isDowning.get() == that.isDowning.get();
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (localThumb != null ? localThumb.hashCode() : 0);
            result = 31 * result + (remoteThumb != null ? remoteThumb.hashCode() : 0);
            result = 31 * result + (localUrl != null ? localUrl.hashCode() : 0);
            result = 31 * result + duration;
            result = 31 * result + (isDowning.get() ? isDowning.hashCode() : 0);
            return result;
        }
    }

    //文件
    public static class FileViewModel extends XItemViewModel {
        public String localUrl;
        public String fileName;
        public String fileSize;
        public int imageResId;
        public String state;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            FileViewModel that = (FileViewModel) o;

            if (imageResId != that.imageResId) return false;
            if (localUrl != null ? !localUrl.equals(that.localUrl) : that.localUrl != null)
                return false;
            if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null)
                return false;
            if (fileSize != null ? !fileSize.equals(that.fileSize) : that.fileSize != null)
                return false;
            return state != null ? state.equals(that.state) : that.state == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (localUrl != null ? localUrl.hashCode() : 0);
            result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
            result = 31 * result + (fileSize != null ? fileSize.hashCode() : 0);
            result = 31 * result + imageResId;
            result = 31 * result + (state != null ? state.hashCode() : 0);
            return result;
        }
    }

    //优惠券
    public static class CouponViewModel extends XItemViewModel {
        public String couponSubName;
        public String couponName;
        public String couponScope;
        public String couponBound;
        public String endDate;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            CouponViewModel that = (CouponViewModel) o;

            if (couponSubName != null ? !couponSubName.equals(that.couponSubName) : that.couponSubName != null)
                return false;
            if (couponName != null ? !couponName.equals(that.couponName) : that.couponName != null)
                return false;
            if (couponScope != null ? !couponScope.equals(that.couponScope) : that.couponScope != null)
                return false;
            if (couponBound != null ? !couponBound.equals(that.couponBound) : that.couponBound != null)
                return false;
            return endDate != null ? endDate.equals(that.endDate) : that.endDate == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (couponSubName != null ? couponSubName.hashCode() : 0);
            result = 31 * result + (couponName != null ? couponName.hashCode() : 0);
            result = 31 * result + (couponScope != null ? couponScope.hashCode() : 0);
            result = 31 * result + (couponBound != null ? couponBound.hashCode() : 0);
            result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
            return result;
        }
    }

    //切换导购（C端发过来的消息）
    public static class SwitchGuideViewModel extends XItemViewModel {
        public int type;                        ///< 自定义消息类型
        public String imageUrl;               ///< 商品的图片URL
        public String merchandiseName;        ///< 商品的名称
        public String merchandisePrice;           ///< 商品的价格
        public String merchandiseID;          ///< 商品的ID
        public String remark;//备注

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            SwitchGuideViewModel that = (SwitchGuideViewModel) o;

//            if (type != that.type) return false;
            if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
                return false;
            if (merchandiseName != null ? !merchandiseName.equals(that.merchandiseName) : that.merchandiseName != null)
                return false;
            if (merchandisePrice != null ? !merchandisePrice.equals(that.merchandisePrice) : that.merchandisePrice !=
                    null)
                return false;
//            if (merchandiseID != null ? !merchandiseID.equals(that.merchandiseID) : that.merchandiseID != null)
//                return false;
            return remark != null ? remark.equals(that.remark) : that.remark == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
//            result = 31 * result + type;
            result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
            result = 31 * result + (merchandiseName != null ? merchandiseName.hashCode() : 0);
            result = 31 * result + (merchandisePrice != null ? merchandisePrice.hashCode() : 0);
//            result = 31 * result + (merchandiseID != null ? merchandiseID.hashCode() : 0);
            result = 31 * result + (remark != null ? remark.hashCode() : 0);
            return result;
        }
    }

    //消息状态
    public static class MessageStatusViewModel extends XItemViewModel {
        public final ObservableBoolean isAckShow = new ObservableBoolean(false);//已读未读显示
        public final ObservableBoolean isMsgStatusShow = new ObservableBoolean(false);//重发消息显示
        public final ObservableField<String> ackText = new ObservableField<>("未读");
        public final ObservableBoolean isLoadShow = new ObservableBoolean(false);//加载圈显示
        public final ObservableField<String> progress = new ObservableField<>("0%");//进度

        void setMessageStatus(EMMessage message) {
            switch (message.status()) {
                case CREATE:
                    isLoadShow.set(true);
                    isMsgStatusShow.set(false);
                    isAckShow.set(false);
                    //未发送的消息需要发送
                    EMClient.getInstance().chatManager().sendMessage(message);
                    LogUtil.d("未发送的消息发送");
                    break;
                case SUCCESS:
                    isAckShow.set(true);
                    isLoadShow.set(false);
                    isMsgStatusShow.set(false);
                    break;
                case FAIL:
                    isMsgStatusShow.set(true);
                    isAckShow.set(false);
                    isLoadShow.set(false);
                    break;
                case INPROGRESS:
                    isLoadShow.set(true);
                    isMsgStatusShow.set(false);
                    isAckShow.set(false);
                    break;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            MessageStatusViewModel that = (MessageStatusViewModel) o;

            if (isAckShow.get() != that.isAckShow.get()) return false;
            if (isMsgStatusShow.get() != that.isMsgStatusShow.get())
                return false;
            if (ackText.get() != null ? !ackText.get().equals(that.ackText.get()) : that.ackText.get() != null)
                return false;
            if (isLoadShow.get() != that.isLoadShow.get()) return false;
            return progress.get() != null ? progress.get().equals(that.progress.get()) : that.progress.get() == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (isAckShow.get() ? isAckShow.hashCode() : 0);
            result = 31 * result + (isMsgStatusShow.get() ? isMsgStatusShow.hashCode() : 0);
            result = 31 * result + (ackText.get() != null ? ackText.get().hashCode() : 0);
            result = 31 * result + (isLoadShow.get() ? isLoadShow.hashCode() : 0);
            result = 31 * result + (progress.get() != null ? progress.get().hashCode() : 0);
            return result;
        }
    }

    //订单
    public static class OrderViewModel extends XItemViewModel {
        public String shopId;       //店铺id
        public int orderId;         //订单id
        public String serialNumber; //订单编号
        public String payableAmount; //订单金额
        public String orderStatus; //订单状态
        public String imgUrl;        //商品图片
        public String createDate;  //创建时间
        public int extendType;     //订单类型
        public boolean stage;      //是否是阶段订单

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            OrderViewModel that = (OrderViewModel) o;

//            if (orderId != that.orderId) return false;
//            if (extendType != that.extendType) return false;
//            if (stage != that.stage) return false;
//            if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
            if (serialNumber != null ? !serialNumber.equals(that.serialNumber) : that.serialNumber != null)
                return false;
            if (payableAmount != null ? !payableAmount.equals(that.payableAmount) : that.payableAmount != null)
                return false;
//            if (orderStatus != null ? !orderStatus.equals(that.orderStatus) : that.orderStatus != null)
//                return false;
            if (imgUrl != null ? !imgUrl.equals(that.imgUrl) : that.imgUrl != null) return false;
            return createDate != null ? createDate.equals(that.createDate) : that.createDate == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
//            result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
//            result = 31 * result + orderId;
            result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
            result = 31 * result + (payableAmount != null ? payableAmount.hashCode() : 0);
//            result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
            result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
            result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
//            result = 31 * result + extendType;
//            result = 31 * result + (stage ? 1 : 0);
            return result;
        }
    }

    /**
     * 间隔5分钟才显示消息发送时间
     *
     * @param var0
     * @param var2
     * @return
     */
    public static boolean isCloseEnough(long var0, long var2) {
        long var4 = var0 - var2;
        if (var4 < 0L) {
            var4 = -var4;
        }

        return var4 < 300000L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemMessageViewModel that = (ItemMessageViewModel) o;

//        if (diyMessageType != that.diyMessageType) return false;
        if (preTimeStamp != that.preTimeStamp) return false;
        if (itemIMType != that.itemIMType) return false;
//        if (mMessage != null ? !mMessage.equals(that.mMessage) : that.mMessage != null)
//            return false;
        if (userAvatar.get() != null ? !userAvatar.get().equals(that.userAvatar.get()) : that.userAvatar.get() != null)
            return false;
        if (messageStatus.get() != null ? !messageStatus.get().equals(that.messageStatus.get()) : that.messageStatus
                .get() != null)
            return false;
        if (timeStamp.get() != null ? !timeStamp.get().equals(that.timeStamp.get()) : that.timeStamp.get() != null)
            return false;
        if (mImSysWelWordsViewModel != null ? !mImSysWelWordsViewModel.equals(that.mImSysWelWordsViewModel) : that
                .mImSysWelWordsViewModel != null)
            return false;
        if (mImageMessageViewModel != null ? !mImageMessageViewModel.equals(that.mImageMessageViewModel) : that
                .mImageMessageViewModel != null)
            return false;
        if (mGoodsViewModel != null ? !mGoodsViewModel.equals(that.mGoodsViewModel) : that.mGoodsViewModel != null)
            return false;
        if (mVoiceViewModel != null ? !mVoiceViewModel.equals(that.mVoiceViewModel) : that.mVoiceViewModel != null)
            return false;
        if (mVideoViewModel != null ? !mVideoViewModel.equals(that.mVideoViewModel) : that.mVideoViewModel != null)
            return false;
        if (mFileViewModel != null ? !mFileViewModel.equals(that.mFileViewModel) : that.mFileViewModel != null)
            return false;
        if (mCouponViewModel != null ? !mCouponViewModel.equals(that.mCouponViewModel) : that.mCouponViewModel != null)
            return false;
        if (mSwitchGuideViewModel != null ? !mSwitchGuideViewModel.equals(that.mSwitchGuideViewModel) : that
                .mSwitchGuideViewModel != null)
            return false;
        if (mMessageStatusViewModel != null ? !mMessageStatusViewModel.equals(that.mMessageStatusViewModel) : that
                .mMessageStatusViewModel != null)
            return false;
        if (mOrderViewModel != null ? !mOrderViewModel.equals(that.mOrderViewModel) : that.mOrderViewModel != null)
            return false;
        if (textMessage != null ? !textMessage.equals(that.textMessage) : that.textMessage != null)
            return false;
        return userLikeMessage != null ? userLikeMessage.equals(that.userLikeMessage) : that.userLikeMessage == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (itemIMType != null ? itemIMType.hashCode() : 0);
//        result = 31 * result + (mMessage != null ? mMessage.hashCode() : 0);
//        result = 31 * result + diyMessageType;
        result = 31 * result + (userAvatar.get() != null ? userAvatar.get().hashCode() : 0);
        result = 31 * result + (int) (preTimeStamp ^ (preTimeStamp >>> 32));
        result = 31 * result + (messageStatus.get() != null ? messageStatus.get().hashCode() : 0);
        result = 31 * result + (timeStamp.get() != null ? timeStamp.get().hashCode() : 0);
        result = 31 * result + (mImSysWelWordsViewModel != null ? mImSysWelWordsViewModel.hashCode() : 0);
        result = 31 * result + (mImageMessageViewModel != null ? mImageMessageViewModel.hashCode() : 0);
        result = 31 * result + (mGoodsViewModel != null ? mGoodsViewModel.hashCode() : 0);
        result = 31 * result + (mVoiceViewModel != null ? mVoiceViewModel.hashCode() : 0);
        result = 31 * result + (mVideoViewModel != null ? mVideoViewModel.hashCode() : 0);
        result = 31 * result + (mFileViewModel != null ? mFileViewModel.hashCode() : 0);
        result = 31 * result + (mCouponViewModel != null ? mCouponViewModel.hashCode() : 0);
        result = 31 * result + (mSwitchGuideViewModel != null ? mSwitchGuideViewModel.hashCode() : 0);
        result = 31 * result + (mMessageStatusViewModel != null ? mMessageStatusViewModel.hashCode() : 0);
        result = 31 * result + (mOrderViewModel != null ? mOrderViewModel.hashCode() : 0);
        result = 31 * result + (textMessage != null ? textMessage.hashCode() : 0);
        result = 31 * result + (userLikeMessage != null ? userLikeMessage.hashCode() : 0);
        return result;
    }
}

