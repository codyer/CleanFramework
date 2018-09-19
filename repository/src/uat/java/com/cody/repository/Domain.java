package com.cody.repository;

/**
 * Created by cody.yi on 2017/3/8.
 * 服务器地址
 */
public interface Domain {
    //文件上传
    String UPLOAD_FILE = "http://file-yun.uat1.rs.com";
    // 系统消息
    String SYSTEM_URL = "http://sms.uat1.rs.com";
    // APPICON
    String MMALL_URL = "http://m.uat1.rs.com";
    // Wap服务器
    String WAP_URL = "http://jzwap.uat1.rs.com";
    //埋点
    String STAT_DOMAIN = "http://aureuma.uat1.rs.com";
    //登录
    String LOGIN_URL = "http://api-partner.uat1.rs.com";
    //C端客户订单
    String ORDER_CUSTOMER_URL = "http://api-order.uat1.rs.com";
    //B端订单
    String ORDER_BUSINESS_URL = "http://api-order-bweb.uat1.rs.com";
    //导购用户信息
    String LONG_GUO_URL = "http://s-api-longguo.uat1.rs.com";
    //H5
    String WEB_URL = "http://lg-app.uat1.rs.com";
    //IM
    String IM_URL = "http://im.uat1.rs.com";
    // app配置
    String APP_CONFIG_URL = "http://pb.uat1.rs.com";
    // 优惠券
    String PROMOTION_URL = "http://static-promotion.uat1.rs.com";
    // 优惠券渠道
    String COUPON_CHANNEL_URL = "http://api-promotion.uat1.rs.com";
    // 商品搜索
    String GOODS_SEARCH_URL = "http://search.uat1.rs.com";
    // 内容配置
    String CRM_URL = "http://api-crm.uat1.rs.com";
    //搜索
    String BESEARCH_URL = "http://besearch.uat1.rs.com";
    //活动管理
    String ACTIVITY_URL = "http://aps.uat1.rs.com";
    //零售
    String RETAIL_URL = "http://rtapi.uat1.rs.com";
    //店铺
    String M_API_LONGGUO_URL = "http://m-api-longguo.uat1.rs.com";
    //内链host后缀
    String HOST_SUFFIX = ".uat1.rs.com";
    //发券失败列表
    String OCEN_BIZ_URL = "http://ocean-biz.uat1.rs.com";
}
