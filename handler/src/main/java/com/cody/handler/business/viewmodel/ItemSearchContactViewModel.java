package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.XItemViewModel;

import java.util.Objects;

public class ItemSearchContactViewModel extends XItemViewModel {

    private String name;//真名
    private String avatar;//头像
    private String imId;
    private String keyWord;

    public ItemSearchContactViewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ItemSearchContactViewModel that = (ItemSearchContactViewModel) o;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (avatar != null ? !avatar.equals(that.avatar) : that.avatar != null)
            return false;
        if (imId != null ? !imId.equals(that.imId) : that.imId != null)
            return false;
        return true;
    }
}
