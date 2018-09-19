package com.cody.app.framework.EventBus;

/**
 * Created by dong.wang
 * Date: 2018/8/2
 * Time: 17:51
 * Description:
 */
public interface SimpleEventBusKey {
    String USER_LOGIN_ANOTHER_DEVICE = "user_login_another_device";// IM其他设备登录
    String ON_CONNECTED = "on_connected";// IM重连
    String ON_DISCONNECTED = "on_disconnected";// IM掉线
    String REFRESH_CHAT = "refresh_chat";// 刷新聊天页面
}
