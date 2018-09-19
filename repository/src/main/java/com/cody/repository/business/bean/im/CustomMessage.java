package com.cody.repository.business.bean.im;

/**
 * Created by cody.yi on 2018/7/28.
 * 自定义消息
 */
public interface CustomMessage {
    int NO_DIY = -1;//非自定义消息
    int SystemMerchandise = 1;      //< 系统消息：商品信息
    int PersonLocation = 2;         //< 个人消息：位置分享消息
    int PersonQuotation = 3;        //< 个人消息：报价单
    int PersonGuideSwitch = 4;      //< 个人消息：切换导购（内含导购员备注）
    int PersonCoupon = 5;           //< 个人消息：优惠券
    int PersonVideo = 6;            //< 个人消息：视频通话状态
    int PersonOrder = 7;            //< 个人消息：订单？
    int SystemHouse = 8;            //< 系统消息：房产信息
    int PersonShoppingGuideBook = 9;//< 个人消息：导购手册
    int SystemLiveLike = 10;          //< 系统消息：直播点赞
    int ChatRoomEnterRoomType = 12;          //< 其它用户进入聊天室的自定义消息
    int ChatRoomLeaveRoomType = 13;          //< 别人离开聊天室消息类型
    int PersonRecommendGoods = 14;   //< 个人消息：推荐商品
    int SysGreetingWords = 15;          //< 系统欢迎语
    int UserOrder = 16;          //< 用户订单
    int Question = 17;          //< 常见问题
    int UserLike = 18;     ///< 系统消息：用户点赞（导购员）

    interface VideoCallStatus {
        int remoteOffline = 1;//被叫方不在线
        int callFailed = 2;//呼叫失败
        int remoteBusy = 3;//被叫方正忙
        int noResponse = 4;//被叫方无应答
        int decline = 5;//被叫方已拒绝
        int callerCancel = 6;//主叫方取消呼叫
        int callerHangup = 7;//主叫方挂断通话（通话中）
        int remoteHangup = 8;//被叫方挂断通话（通话中）
    }
}
