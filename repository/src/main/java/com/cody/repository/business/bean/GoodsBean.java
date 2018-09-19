/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.repository.business.bean;

import java.util.List;

/**
 * Created by chy on 2016/9/5.
 * 创建报价订单搜索商品的bean类
 */
public class GoodsBean {


    /**
     * totalCount : 50914
     * type : goods
     * data : [{"base_category_id":"1","base_category_name":"家具","brand_id":"5597","brand_name":"鼎联","category_id":"202","city_name":"上海市","color_id":"19","id":"360785","market_ids":"19","material":"香樟 玻璃、香樟、五金件 聚酯漆、柚木单板","model_number":"255","pdt_name":"书柜","product_goods_id":"1623741","product_id":"1623641","sale_price":"53380.000000","series_name":"爵凡尼","shop_ids":"9007","sub_category_id":"20","sub_category_name":"成套家具","title":"鼎联 书柜 黑色 家具","update_date":"2016-07-04 16:02:55.0"}]
     */
    private List<DataBean> data;
    private int totalCount;
    private String type;

    /**
     * base_category_id : 1
     * base_category_name : 家具
     * brand_id : 5597
     * brand_name : 鼎联
     * category_id : 202
     * city_name : 上海市
     * color_id : 19
     * id : 360785
     * market_ids : 19
     * material : 香樟 玻璃、香樟、五金件 聚酯漆、柚木单板
     * model_number : 255
     * pdt_name : 书柜
     * product_goods_id : 1623741
     * product_id : 1623641
     * sale_price : 53380.000000
     * series_name : 爵凡尼
     * shop_ids : 9007
     * sub_category_id : 20
     * sub_category_name : 成套家具
     * title : 鼎联 书柜 黑色 家具
     * update_date : 2016-07-04 16:02:55.0
     */


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "base_category_id='" + base_category_id + '\'' +
                    ", base_category_name='" + base_category_name + '\'' +
                    ", brand_id='" + brand_id + '\'' +
                    ", brand_name='" + brand_name + '\'' +
                    ", category_id='" + category_id + '\'' +
                    ", city_name='" + city_name + '\'' +
                    ", color='" + color + '\'' +
                    ", id='" + id + '\'' +
                    ", market_ids='" + market_ids + '\'' +
                    ", material='" + material + '\'' +
                    ", model_number='" + model_number + '\'' +
                    ", pdt_name='" + pdt_name + '\'' +
                    ", product_goods_id='" + product_goods_id + '\'' +
                    ", product_id='" + product_id + '\'' +
                    ", sale_price='" + sale_price + '\'' +
                    ", series_name='" + series_name + '\'' +
                    ", shop_ids='" + shop_ids + '\'' +
                    ", sub_category_id='" + sub_category_id + '\'' +
                    ", sub_category_name='" + sub_category_name + '\'' +
                    ", title='" + title + '\'' +
                    ", update_date='" + update_date + '\'' +
                    ", pdt_sku='" + pdt_sku + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    ", show_category_name='" + show_category_name + '\'' +
                    '}';
        }

        private String base_category_id;
        private String base_category_name;
        private String brand_id;
        private String brand_name;
        private String category_id;
        private String city_name;
        private String color;
        private String id;
        private String market_ids;
        private String material;
        private String model_number;
        private String pdt_name;
        private String product_goods_id;
        private String product_id;
        private String sale_price;
        private String series_name;
        private String shop_ids;
        private String sub_category_id;
        private String sub_category_name;
        private String title;
        private String update_date;
        private String pdt_sku; //创建报价单所需的商品ID
        private String pic_url;
        private String standard;
        private String show_category_name;

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getStandard() {
            return standard;
        }

        public String getBase_category_id() {
            return base_category_id;
        }

        public void setBase_category_id(String base_category_id) {
            this.base_category_id = base_category_id;
        }

        public String getBase_category_name() {
            return base_category_name;
        }

        public void setBase_category_name(String base_category_name) {
            this.base_category_name = base_category_name;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color_id) {
            this.color = color_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMarket_ids() {
            return market_ids;
        }

        public void setMarket_ids(String market_ids) {
            this.market_ids = market_ids;
        }

        public String getMaterial() {
            return material;
        }

        public void setMaterial(String material) {
            this.material = material;
        }

        public String getModel_number() {
            return model_number;
        }

        public void setModel_number(String model_number) {
            this.model_number = model_number;
        }

        public String getPdt_name() {
            return pdt_name;
        }

        public void setPdt_name(String pdt_name) {
            this.pdt_name = pdt_name;
        }

        public String getProduct_goods_id() {
            return product_goods_id;
        }

        public void setProduct_goods_id(String product_goods_id) {
            this.product_goods_id = product_goods_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getSeries_name() {
            return series_name;
        }

        public void setSeries_name(String series_name) {
            this.series_name = series_name;
        }

        public String getShop_ids() {
            return shop_ids;
        }

        public void setShop_ids(String shop_ids) {
            this.shop_ids = shop_ids;
        }

        public String getSub_category_id() {
            return sub_category_id;
        }

        public void setSub_category_id(String sub_category_id) {
            this.sub_category_id = sub_category_id;
        }

        public String getSub_category_name() {
            return sub_category_name;
        }

        public void setSub_category_name(String sub_category_name) {
            this.sub_category_name = sub_category_name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public void setPdt_sku(String pdt_sku) {
            this.pdt_sku = pdt_sku;
        }

        public String getPdt_sku() {
            return pdt_sku;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setShow_category_name(String show_category_name) {
            this.show_category_name = show_category_name;
        }

        public String getShow_category_name() {
            return show_category_name;
        }
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "data=" + data +
                ", totalCount=" + totalCount +
                ", type='" + type + '\'' +
                '}';
    }
}
