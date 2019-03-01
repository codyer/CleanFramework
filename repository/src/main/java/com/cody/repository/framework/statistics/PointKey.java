package com.cody.repository.framework.statistics;

/**
 * Created by Emcy-fu ;
 * on data:  2018/4/17 ;
 */

public class PointKey {
    public static final String OS_TYPE_ANDROID = "Android";

    public PointKey() {
    }

    /**
     * 参数参照wiki
     * http://wiki.corp.rs.com/pages/viewpage.action?pageId=16613953
     */
    public static class Parameter {
        public static String skuid = "skuid";//	商品ID	#
        public static String spuid = "spuid";//	产品ID	#
        public static String brandid = "brandid";//	品牌ID	#
        public static String contentid = "contentid";//	内容ID	#	包括文章ID,案例ID，图集ID，搭配ID，合辑ID等
        public static String bannerid = "bannerid";//	广告ID	#
        public static String couponid = "couponid";//	券id	#
        public static String tag = "tag";//	标签	#	筛选器、分类（多个标签可以写为tag1,tag2……）
        public static String sortid = "sortid";//	排序ID	#
        public static String shopid = "shopid";//	店铺id	#
        public static String mallid = "mallid";//	商场id	#
        public static String cityid = "cityid";//	城市编码	#
        public static String wikiid = "wikiid";//	问答id	#
        public static String phone = "phone";//	手机号码	#
        public static String orderid = "orderid";//	订单id	#
        public static String categ_lv1_id = "categ_lv1_id";//	一级类目ID	#
        public static String categ_lv2_id = "categ_lv2_id";//	二级类目ID	#
        public static String company_id = "company_id";//	公司ID	#
        public static String designer_id = "designer_id";//	设计师ID	#
        public static String message_type_id = "message_type_id";//	消息类型ID	#	1系统推送，2订单消息，3活动消息
        public static String url = "url";//	url地址	#
        public static String openid = "openid";//	导购员ID	#	传Openid字符串
        public static String keyword = "s_tag";//	站内搜索词	#
        public static String activity_id="activity_id";//活动id

        public Parameter() {
        }
    }
}
