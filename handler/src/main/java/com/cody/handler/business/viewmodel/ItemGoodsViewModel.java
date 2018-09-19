/*
 * Copyright (c)  Created by Cody.yi on 2016/9/5.
 */

package com.cody.handler.business.viewmodel;

import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by chy on 2016/9/5.
 * 创建报价订单搜索商品
 */
public class ItemGoodsViewModel extends XItemViewModel {


    /**
     * base_category_name : 家具
     * brand_name : 鼎联
     * city_name : 上海市
     * color_id : 19 //这个怎么用
     * material : 香樟 玻璃、香樟、五金件 聚酯漆、柚木单板
     * model_number : 255
     * pdt_name : 书柜
     * sale_price : 53380.000000
     * series_name : 爵凡尼
     * shop_ids : 9007
     * sub_category_name : 成套家具
     * title : 鼎联 书柜 黑色 家具
     */

    private String base_category_name;
    private String brand_name;
    private String city_name;
    private String color;
    private String id;
    private String material;
    private String pdt_name;
    private String sale_price;
    private String series_name;
    private String title;
    private String pic_url;
    private String standard;

//    public int getCheckImage() {
//        return checkImage.get();
//    }

//    private ObservableField<Integer> checkImage = new ObservableField<>(R.drawable.un_check);

    public boolean getChecked() {
        return checked.get();
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    private ObservableField<Boolean> checked = new ObservableField<>(false);


    public String getBase_category_name() {
        return base_category_name;
    }

    public void setBase_category_name(String base_category_name) {
        this.base_category_name = base_category_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
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

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }


    public String getPdt_name() {
        return pdt_name;
    }

    public void setPdt_name(String pdt_name) {
        this.pdt_name = pdt_name;
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStandard() {
        return standard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemGoodsViewModel that = (ItemGoodsViewModel) o;

        if (base_category_name != null ? !base_category_name.equals(that.base_category_name) : that
                .base_category_name != null)
            return false;
        if (brand_name != null ? !brand_name.equals(that.brand_name) : that.brand_name != null)
            return false;
        if (city_name != null ? !city_name.equals(that.city_name) : that.city_name != null)
            return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (material != null ? !material.equals(that.material) : that.material != null)
            return false;
        if (pdt_name != null ? !pdt_name.equals(that.pdt_name) : that.pdt_name != null)
            return false;
        if (sale_price != null ? !sale_price.equals(that.sale_price) : that.sale_price != null)
            return false;
        if (series_name != null ? !series_name.equals(that.series_name) : that.series_name != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (pic_url != null ? !pic_url.equals(that.pic_url) : that.pic_url != null) return false;
        return standard != null ? standard.equals(that.standard) : that.standard == null;

    }

    @Override
    public int hashCode() {
        int result = base_category_name != null ? base_category_name.hashCode() : 0;
        result = 31 * result + (brand_name != null ? brand_name.hashCode() : 0);
        result = 31 * result + (city_name != null ? city_name.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (material != null ? material.hashCode() : 0);
        result = 31 * result + (pdt_name != null ? pdt_name.hashCode() : 0);
        result = 31 * result + (sale_price != null ? sale_price.hashCode() : 0);
        result = 31 * result + (series_name != null ? series_name.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (pic_url != null ? pic_url.hashCode() : 0);
        result = 31 * result + (standard != null ? standard.hashCode() : 0);
        return result;
    }
}
