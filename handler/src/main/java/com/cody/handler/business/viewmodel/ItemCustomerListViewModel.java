/*
 * Copyright (c)  Created by Cody.yi on 2016/9/14.
 */

package com.cody.handler.business.viewmodel;


import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by chy on 2016/8/29.
 * IM中导购员切换的viewmodel
 */
public class ItemCustomerListViewModel extends XItemViewModel {
    private String imageUrl;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemCustomerListViewModel that = (ItemCustomerListViewModel) o;

        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = imageUrl != null ? imageUrl.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
