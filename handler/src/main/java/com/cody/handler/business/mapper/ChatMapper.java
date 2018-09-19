package com.cody.handler.business.mapper;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMNormalFileMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.util.TextFormater;
import com.cody.handler.R;
import com.cody.handler.business.viewmodel.ItemMessageViewModel;
import com.cody.handler.framework.mapper.ModelMapper;
import com.cody.repository.business.bean.im.BaseMessageBean;
import com.cody.repository.business.bean.im.CustomMessage;
import com.cody.repository.business.bean.im.IMCouponBean;
import com.cody.repository.business.bean.im.IMOrderBean;
import com.cody.repository.business.bean.im.IMSwitchGuideBean;
import com.cody.repository.business.bean.im.IMUserLikeBean;
import com.cody.repository.business.bean.im.IMVideoCallMessageBean;
import com.cody.repository.business.bean.im.ImSysWelWordsBean;
import com.cody.repository.business.bean.im.RecommendGoodsBean;
import com.cody.repository.business.bean.im.SysGreetingWordsGoodsBean;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.DateUtil;
import com.cody.xf.utils.DeviceUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ResourceUtil;

import java.io.File;

import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_RECV_COUPON;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_RECV_FILE;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_RECV_GOODS;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_RECV_GUIDE;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_RECV_IMAGE;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_RECV_TXT;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_RECV_USERODER;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_RECV_VIDEO;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_RECV_VIDEO_CALL;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_RECV_VOICE;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_SEND_COUPON;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_SEND_FILE;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_SEND_GOODS;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_SEND_GUIDE;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_SEND_IMAGE;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_SEND_TXT;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_SEND_VIDEO;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_SEND_VIDEO_CALL;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_SEND_VOICE;
import static com.cody.handler.business.viewmodel.ChatViewModel.MESSAGE_TYPE_SYSGREETINGWORDS;

/**
 * Created by chen.huarong on 2018/7/20.
 */
public class ChatMapper extends ModelMapper<ItemMessageViewModel, EMMessage> {

    private static final String TAG = "聊天消息";
    private static final int FILE_TYPE_NO_TYPE = -1;
    private static final int FILE_TYPE_PDF = 0;
    private static final int FILE_TYPE_XLSX = 1;
    private static final int FILE_TYPE_DOC = 2;
    private static final int FILE_TYPE_PPT = 3;
    private long preTimeStamp;
    private String mCustomerAvatar;

    public ChatMapper() {

    }

    public void setAvatar(String avatar) {
        this.mCustomerAvatar = avatar;
    }

    @Override
    public ItemMessageViewModel mapper(EMMessage dataModel, int position) {
        ItemMessageViewModel itemMessageViewModel = new ItemMessageViewModel();
        return mapper(itemMessageViewModel, dataModel);
    }

    @Override
    public ItemMessageViewModel mapper(ItemMessageViewModel viewModel, EMMessage dataModel) {
        if (dataModel == null) return viewModel;
        viewModel.setMessage(dataModel);
        viewModel.itemIMType = dataModel.getType();
        int itemType = -1;
        switch (dataModel.getType()) {
            case TXT:
                itemType = dataModel.direct() == EMMessage.Direct.RECEIVE ?
                        MESSAGE_TYPE_RECV_TXT : MESSAGE_TYPE_SEND_TXT;
                BaseMessageBean baseMessageBean = BaseMessageBean.getExtraMsgBean(dataModel);
                if (baseMessageBean != null) {
                    viewModel.diyMessageType = baseMessageBean.getType();
                    switch (baseMessageBean.getType()) {
                        case CustomMessage.PersonRecommendGoods://推荐商品
                        case CustomMessage.SystemMerchandise://商品信息
                            itemType = dataModel.direct() == EMMessage.Direct.RECEIVE ?
                                    MESSAGE_TYPE_RECV_GOODS : MESSAGE_TYPE_SEND_GOODS;
                            mapperGoods(viewModel, baseMessageBean);
                            break;
                        case CustomMessage.PersonGuideSwitch://切换导购
                            itemType = dataModel.direct() == EMMessage.Direct.RECEIVE ?
                                    MESSAGE_TYPE_RECV_GUIDE : MESSAGE_TYPE_SEND_GUIDE;
                            if (itemType == MESSAGE_TYPE_SEND_GUIDE) {
                                IMSwitchGuideBean switchGuideBean = baseMessageBean.getBean(IMSwitchGuideBean.class);
                                viewModel.setTextMessage(switchGuideBean.SGRemark);
                            } else {
                                mapperSwitchGuide(viewModel, baseMessageBean);
                            }
                            break;
                        case CustomMessage.PersonCoupon://优惠券
                            itemType = dataModel.direct() == EMMessage.Direct.RECEIVE ?
                                    MESSAGE_TYPE_RECV_COUPON : MESSAGE_TYPE_SEND_COUPON;
                            mapperCoupon(viewModel, baseMessageBean);
                            break;
                        case CustomMessage.SysGreetingWords://系统欢迎语
                            itemType = MESSAGE_TYPE_SYSGREETINGWORDS;
                            mapperSysGreetingWords(viewModel, baseMessageBean);
                            break;
                        case CustomMessage.UserLike://点赞
//                            itemType = MESSAGE_TYPE_RECV_USELIKE;
                            IMUserLikeBean userLikeBean = baseMessageBean.getBean(IMUserLikeBean.class);
                            viewModel.setTextMessage(userLikeBean.getReminder());
                            break;
                        case CustomMessage.UserOrder://用户订单
                            itemType = MESSAGE_TYPE_RECV_USERODER;
                            mapperUserOrder(viewModel, baseMessageBean);
                            break;
                        case CustomMessage.Question://常见问题（聊天记录用）
                            viewModel.setTextMessage("常见问题");
                            break;
                        case CustomMessage.PersonVideo://视频通话状态
                            itemType = dataModel.direct() == EMMessage.Direct.RECEIVE ?
                                    MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SEND_VIDEO_CALL;
                            IMVideoCallMessageBean videoCallMessageBean = baseMessageBean.getBean
                                    (IMVideoCallMessageBean.class);
                            String videoCallStatusText = getVideoCallStatusText(
                                    dataModel.direct()
                                            == EMMessage.Direct.SEND, videoCallMessageBean);
                            viewModel.setTextMessage(videoCallStatusText);
                            LogUtil.d(TAG, "视频通话状态=" + videoCallStatusText);
                            break;
                        default:
                            EMTextMessageBody txtBody = (EMTextMessageBody) dataModel.getBody();
                            String text = txtBody.getMessage();
                            viewModel.setTextMessage(text);
                            break;
                    }
                } else {
                    EMTextMessageBody txtBody = (EMTextMessageBody) dataModel.getBody();
                    String text = txtBody.getMessage();
                    viewModel.setTextMessage(text);
                    LogUtil.d(TAG, "文本消息=" + text);
                }

//                if (dataModel.getBooleanAttribute(MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
//                    itemType = dataModel.direct() == EMMessage.Direct.RECEIVE ?
//                            MESSAGE_TYPE_RECV_VIDEO_CALL :
//                            MESSAGE_TYPE_SEND_VIDEO_CALL;
//                }
                break;
            case IMAGE:
                itemType = dataModel.direct() == EMMessage.Direct.RECEIVE ?
                        MESSAGE_TYPE_RECV_IMAGE : MESSAGE_TYPE_SEND_IMAGE;
                ItemMessageViewModel.ImageMessageViewModel imageMessageViewModel = new ItemMessageViewModel
                        .ImageMessageViewModel();
                EMImageMessageBody imageMessageBody = (EMImageMessageBody) dataModel.getBody();
                String filePath = imageMessageBody.getLocalUrl();
                imageMessageViewModel.remoteUrl = imageMessageBody.getRemoteUrl();
                imageMessageViewModel.localUrl = filePath;
                imageMessageViewModel.thumbnailLocalPath = imageMessageBody.thumbnailLocalPath();
                imageMessageViewModel.thumbnailRemoteUrl = imageMessageBody.getThumbnailUrl();
                int width = imageMessageBody.getWidth();
                int height = imageMessageBody.getHeight();
                if (width != 0
                        && height != 0) {//接收图片的时候要处理图片宽高
                    int maxWH = DeviceUtil.dip2px(XFoundation.getContext(), 160);
                    float ratio = ((float) width) / ((float) height);
                    if (width > maxWH || height > maxWH) {
                        if (width > height) {
                            width = maxWH;
                            height = (int) (width / ratio);
                        } else {
                            height = maxWH;
                            width = (int) (height * ratio);
                        }
                    }
                    if (width != 0
                            && height != 0) {
                        imageMessageViewModel.setWidth(width);
                        imageMessageViewModel.setHeight(height);
                    }
                    LogUtil.d("setResizeImageUrl: width = " + width + "|| height = " + height);
                }
                viewModel.setImageMessageViewModel(imageMessageViewModel);
                LogUtil.d(TAG, "图片=" + imageMessageViewModel.remoteUrl);
                break;
            case VOICE:
                itemType = dataModel.direct() == EMMessage.Direct.RECEIVE ?
                        MESSAGE_TYPE_RECV_VOICE : MESSAGE_TYPE_SEND_VOICE;
                ItemMessageViewModel.VoiceViewModel voiceViewModel = new ItemMessageViewModel.VoiceViewModel();
                EMVoiceMessageBody voiceMessageBody = (EMVoiceMessageBody) dataModel.getBody();
                voiceViewModel.length = String.valueOf(voiceMessageBody.getLength()) + "\"";
                voiceViewModel.isListened.set(dataModel.isListened());
//                voiceViewModel.messageId = dataModel.getMsgId();
                viewModel.setVoiceViewModel(voiceViewModel);
                LogUtil.d(TAG, "语音=" + voiceViewModel.length);
                break;
            case VIDEO:
                itemType = dataModel.direct() == EMMessage.Direct.RECEIVE ?
                        MESSAGE_TYPE_RECV_VIDEO : MESSAGE_TYPE_SEND_VIDEO;
                ItemMessageViewModel.VideoViewModel videoViewModel = new ItemMessageViewModel.VideoViewModel();
                EMVideoMessageBody videoMessageBody = (EMVideoMessageBody) dataModel.getBody();
                videoViewModel.localThumb = videoMessageBody.getLocalThumb();
                videoViewModel.remoteThumb = videoMessageBody.getThumbnailUrl();
                videoViewModel.duration = videoMessageBody.getDuration();
                videoViewModel.localUrl = videoMessageBody.getLocalUrl();
                viewModel.setVideoViewModel(videoViewModel);
                LogUtil.d(TAG, "视频=" + videoViewModel.localUrl);
                break;
            case FILE:
                itemType = dataModel.direct() == EMMessage.Direct.RECEIVE ?
                        MESSAGE_TYPE_RECV_FILE : MESSAGE_TYPE_SEND_FILE;
                ItemMessageViewModel.FileViewModel fileViewModel = new ItemMessageViewModel.FileViewModel();
                EMNormalFileMessageBody fileMessageBody = (EMNormalFileMessageBody) dataModel.getBody();
                fileViewModel.localUrl = fileMessageBody.getLocalUrl();
                fileViewModel.fileName = fileMessageBody.getFileName();
                fileViewModel.fileSize = TextFormater.getDataSize(fileMessageBody.getFileSize());
                if (dataModel.direct() == EMMessage.Direct.RECEIVE) {
                    File file = new File(fileViewModel.localUrl);
                    if (file.exists()) {
                        fileViewModel.state = "已下载";
                    } else {
                        fileViewModel.state = "未下载";
                    }
                }

                switch (getFileType(fileViewModel.localUrl)) {
                    case FILE_TYPE_PDF:
                        fileViewModel.imageResId = R.drawable.ic_pdf;
                        break;
                    case FILE_TYPE_XLSX:
                        fileViewModel.imageResId = R.drawable.ic_excle;
                        break;
                    case FILE_TYPE_DOC:
                        fileViewModel.imageResId = R.drawable.ic_word;
                        break;
                    case FILE_TYPE_PPT:
                        fileViewModel.imageResId = R.drawable.ic_ppt;
                        break;
                    default:
                        fileViewModel.imageResId = R.drawable.ic_wjian;
                }
                viewModel.setFileViewModel(fileViewModel);
                LogUtil.d(TAG, "文件=" + fileViewModel.fileName);
                break;
        }
        viewModel.setItemType(itemType);

        if (TextUtils.isEmpty(viewModel.userAvatar.get())) {//聊天记录中取接口返回的数据，所以聊天记录中头像会有值
            if (dataModel.direct() == EMMessage.Direct.RECEIVE) {
                viewModel.setUserAvatar(mCustomerAvatar);
            } else {
                viewModel.setUserAvatar(Repository.getLocalValue(LocalKey.PICTURE_URL));
            }
        }
        viewModel.preTimeStamp = preTimeStamp;
        preTimeStamp = dataModel.getMsgTime();
        return viewModel;
    }

    //用户订单
    private void mapperUserOrder(ItemMessageViewModel viewModel, BaseMessageBean baseMessageBean) {
        ItemMessageViewModel.OrderViewModel orderViewModel = new ItemMessageViewModel.OrderViewModel();
        IMOrderBean orderBean = baseMessageBean.getBean(IMOrderBean.class);
        orderViewModel.createDate = ResourceUtil.getString(R.string.h_order_createdate
                , DateUtil.getTimeString(orderBean.getCreateDate(), "yyyy-MM-dd"));
        orderViewModel.extendType = orderBean.getExtendType();
        orderViewModel.imgUrl = orderBean.getImgUrl();
        orderViewModel.orderId = orderBean.getOrderId();
        orderViewModel.orderStatus = orderBean.getOrderStatus();
        orderViewModel.payableAmount = ResourceUtil.getString(R.string.h_order_payment, orderBean.getPayableAmount());
        orderViewModel.serialNumber = ResourceUtil.getString(R.string.h_order_serialnumber, orderBean.getSerialNumber
                ());
        orderViewModel.shopId = orderBean.getShopId();
        orderViewModel.stage = orderBean.isStage();
        viewModel.setOrderViewModel(orderViewModel);
        LogUtil.d(TAG, "用户订单=" + orderViewModel.serialNumber);
    }

    //系统欢迎语
    private ItemMessageViewModel.SysWelWordsMessageViewModel mapperSysGreetingWords(ItemMessageViewModel viewModel,
                                                                                    BaseMessageBean baseMessageBean) {
        ItemMessageViewModel.SysWelWordsMessageViewModel imSysWelWordsViewModel = new ItemMessageViewModel
                .SysWelWordsMessageViewModel();
        try {
            ImSysWelWordsBean mBean = baseMessageBean.getBean(ImSysWelWordsBean.class);
            if (mBean != null) {
                imSysWelWordsViewModel.type = mBean.type;
                imSysWelWordsViewModel.extraStr1 = mBean.extraStr1;
                imSysWelWordsViewModel.extraStr2 = mBean.extraStr2;
                imSysWelWordsViewModel.sysWordsStr = mBean.sysWordsStr;
                if (mBean.getSysGoodsList() != null) {
                    imSysWelWordsViewModel.sysGoodsList.clear();
                    for (SysGreetingWordsGoodsBean sysGreetingWordsGoodsBean : mBean
                            .getSysGoodsList()) {
                        ItemMessageViewModel
                                .SysWelWordsMessageViewModel
                                .ItemSysWelWordsMessageViewModel itemSysWelWordsMessageViewModel = new
                                ItemMessageViewModel.SysWelWordsMessageViewModel.ItemSysWelWordsMessageViewModel();
                        itemSysWelWordsMessageViewModel.imageUrl = sysGreetingWordsGoodsBean.getImageUrl();
                        itemSysWelWordsMessageViewModel.merchandiseId = sysGreetingWordsGoodsBean.getMerchandiseId();
                        itemSysWelWordsMessageViewModel.merchandiseName = sysGreetingWordsGoodsBean
                                .getMerchandiseName();
                        itemSysWelWordsMessageViewModel.merchandisePrice = sysGreetingWordsGoodsBean
                                .getMerchandisePrice();
                        itemSysWelWordsMessageViewModel.merchandiseSku = sysGreetingWordsGoodsBean.getMerchandiseSku();
                        imSysWelWordsViewModel.sysGoodsList.add(itemSysWelWordsMessageViewModel);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewModel.setImSysWelWordsViewModel(imSysWelWordsViewModel);
        LogUtil.d(TAG, "系统欢迎语=" + imSysWelWordsViewModel.sysWordsStr);
        return imSysWelWordsViewModel;
    }

    //商品
    private void mapperGoods(ItemMessageViewModel viewModel, BaseMessageBean baseMessageBean) {
        ItemMessageViewModel.GoodsViewModel goodsViewModel = new ItemMessageViewModel.GoodsViewModel();
        RecommendGoodsBean recommendGoodsBean = baseMessageBean.getBean(RecommendGoodsBean.class);
        goodsViewModel.imageUrl = recommendGoodsBean.getImageUrl();
        goodsViewModel.merchandiseID = recommendGoodsBean.getMerchandiseID();
        goodsViewModel.merchandiseName = recommendGoodsBean.getMerchandiseName();
        goodsViewModel.merchandisePrice = "¥" + recommendGoodsBean.getMerchandisePrice();
        goodsViewModel.type = recommendGoodsBean.getType();
        viewModel.setGoodsViewModel(goodsViewModel);
        LogUtil.d(TAG, "商品消息=" + goodsViewModel.merchandiseName);
    }

    //优惠券
    private void mapperCoupon(ItemMessageViewModel viewModel, BaseMessageBean baseMessageBean) {
        ItemMessageViewModel.CouponViewModel couponViewModel = new ItemMessageViewModel.CouponViewModel();
        IMCouponBean couponBean = baseMessageBean.getBean(IMCouponBean.class);
        couponViewModel.couponBound = couponBean.couponBound;
        couponViewModel.couponName = couponBean.couponName;
        couponViewModel.couponScope = couponBean.couponScope;
        couponViewModel.couponSubName = couponBean.couponSubName;
        couponViewModel.endDate = couponBean.getEndDate();
        viewModel.setCouponViewModel(couponViewModel);
        LogUtil.d(TAG, "优惠券=" + couponViewModel.couponName);
    }

    //切换导购
    private void mapperSwitchGuide(ItemMessageViewModel viewModel, BaseMessageBean baseMessageBean) {
        ItemMessageViewModel.SwitchGuideViewModel switchGuideViewModel = new ItemMessageViewModel
                .SwitchGuideViewModel();
        IMSwitchGuideBean switchGuideBean = baseMessageBean.getBean(IMSwitchGuideBean.class);
        switchGuideViewModel.remark = switchGuideBean.SGRemark;
        try {
            RecommendGoodsBean recommendGoodsBean = new Gson().fromJson(switchGuideBean.merchandise,
                    RecommendGoodsBean.class);
            switchGuideViewModel.imageUrl = recommendGoodsBean.getImageUrl();
            switchGuideViewModel.merchandiseID = recommendGoodsBean.getMerchandiseID();
            switchGuideViewModel.merchandiseName = recommendGoodsBean.getMerchandiseName();
            switchGuideViewModel.merchandisePrice = "¥" + recommendGoodsBean.getMerchandisePrice();
            switchGuideViewModel.type = recommendGoodsBean.getType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewModel.setSwitchGuideViewModel(switchGuideViewModel);
        LogUtil.d(TAG, "切换导购=" + switchGuideViewModel.remark);
    }

    /**
     * 获取文件类型
     *
     * @param filePath
     * @return
     */
    private static int getFileType(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return FILE_TYPE_NO_TYPE;
        }
        filePath = filePath.toLowerCase();
        if (filePath.endsWith(".xlsx")) {
            return FILE_TYPE_XLSX;
        }

        if (filePath.endsWith(".pdf")) {
            return FILE_TYPE_PDF;
        }

        if (filePath.endsWith(".doc")) {
            return FILE_TYPE_DOC;
        }

        if (filePath.endsWith(".ppt")) {
            return FILE_TYPE_PPT;
        }
        return FILE_TYPE_NO_TYPE;
    }

    /**
     * 获取通话状态文案
     *
     * @param isSender
     * @param videoCallMessageBean
     * @return
     */
    private static String getVideoCallStatusText(boolean isSender, IMVideoCallMessageBean videoCallMessageBean) {
        switch (videoCallMessageBean.getCallMessageType()) {
            case CustomMessage.VideoCallStatus.remoteOffline:
                return isSender ? "对方不在线" : "未应答视频通话";
            case CustomMessage.VideoCallStatus.callFailed:
                return isSender ? "未接通" : "未应答视频通话";
            case CustomMessage.VideoCallStatus.remoteBusy:
                return isSender ? "未接通" : "未应答视频通话";
            case CustomMessage.VideoCallStatus.noResponse:
                return isSender ? "对方无应答" : "未应答视频通话";
            case CustomMessage.VideoCallStatus.decline:
                return isSender ? "已拒绝视频通话" : "对方已拒绝";
            case CustomMessage.VideoCallStatus.callerCancel:
                return isSender ? "已取消" : "对方已取消";
            case CustomMessage.VideoCallStatus.callerHangup:
            case CustomMessage.VideoCallStatus.remoteHangup:
                return String.format("通话时长：%s", getTime(videoCallMessageBean.getTimeLength() * 1000));
        }
        return "";
    }

    /**
     * 获取通话时长时间格式:   00:00:00
     *
     * @param time
     * @return
     */
    private static String getTime(long time) {
        StringBuffer sb = new StringBuffer();
        try {
            long diff = time;
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long hh = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;

            if (hours < 10) {
                sb.append("0" + hours);
            } else {
                sb.append(hours);
            }
            sb.append(":");
            if (minutes < 10) {
                sb.append("0" + minutes);
            } else {
                sb.append(minutes);
            }
            sb.append(":");

            if (hh < 10) {
                sb.append("0" + hh);
            } else {
                sb.append(hh);
            }


            return sb.toString();
        } catch (Exception e) {
        }
        return "00:00:00";
    }
}
