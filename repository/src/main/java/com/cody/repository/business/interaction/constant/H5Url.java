package com.cody.repository.business.interaction.constant;

import com.cody.repository.Domain;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;

/**
 * Created by cody.yi on 2018/7/25.
 * H5链接
 */
public interface H5Url {
    //手工开单
    String MANUAL_ORDER = Domain.WEB_URL + "/order.html#/Main?shopId=" + Repository.getLocalValue(LocalKey.SHOP_ID);
    //扫码开单 需要拼参数 ?result=http://result
    String SCAN_ORDER = Domain.WEB_URL + "/order.html#/Main?shopId=" + Repository.getLocalValue(LocalKey.SHOP_ID) +"&result=";
    //店铺管理
    String SHOP_MANAGE = Domain.WEB_URL + "/shopManagement.html?shopId=" + Repository.getLocalValue(LocalKey.SHOP_ID);
    //订单详情
    String ORDER_DETAIL = Domain.WEB_URL + "/orderManagement.html?shopId=" + Repository.getLocalValue(LocalKey.SHOP_ID) + "&id=";
    //订单详情不加id
    String PUSH_ORDER_DETAIL = Domain.WEB_URL + "/orderManagement.html?shopId=" + Repository.getLocalValue(LocalKey.SHOP_ID);
    //向导页
    String GUIDE = Domain.WEB_URL + "/wizardPage.html";
    //意见反馈
    String FEEDBACK = Domain.WEB_URL + "/feedback.html";
}
