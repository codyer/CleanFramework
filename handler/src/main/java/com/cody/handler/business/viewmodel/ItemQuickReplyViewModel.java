package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Created by chen.huarong on 2018/8/4.
 */
public class ItemQuickReplyViewModel extends XItemViewModel {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemQuickReplyViewModel viewModel = (ItemQuickReplyViewModel) o;

        return text != null ? text.equals(viewModel.text) : viewModel.text == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
