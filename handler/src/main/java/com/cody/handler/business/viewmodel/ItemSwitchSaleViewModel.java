/*
 * Copyright (c)  Created by Cody.yi on 2016/8/30.
 */

package com.cody.handler.business.viewmodel;


import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by chy on 2016/8/29.
 * IM中导购员切换的viewmodel
 */
public class ItemSwitchSaleViewModel extends XItemViewModel {
    private String imageUrl;
    private String name;
    private String role;
    private String imId;

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

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public String getImId() {
        return imId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemSwitchSaleViewModel that = (ItemSwitchSaleViewModel) o;

        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        return imId != null ? imId.equals(that.imId) : that.imId == null;

    }

    @Override
    public int hashCode() {
        int result = imageUrl != null ? imageUrl.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (imId != null ? imId.hashCode() : 0);
        return result;
    }
}
