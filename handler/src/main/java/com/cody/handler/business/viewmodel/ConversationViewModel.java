package com.cody.handler.business.viewmodel;

import android.databinding.ObservableField;

import com.cody.handler.framework.viewmodel.ListViewModel;

public class ConversationViewModel extends ListViewModel<ItemConversationViewModel> {
    private String mTitle;
    private ObservableField<String> mContent = new ObservableField<>("暂无数据");
    private String time;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public ObservableField<String> getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent.set(content);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ConversationViewModel that = (ConversationViewModel) o;

        if (mTitle != null ? !mTitle.equals(that.mTitle) : that.mTitle != null) return false;
        if (mContent.get() != null ? !mContent.get().equals(that.mContent.get()) : that.mContent.get() != null)
            return false;
        return time != null ? time.equals(that.time) : that.time == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mTitle != null ? mTitle.hashCode() : 0);
        result = 31 * result + (mContent.get() != null ? mContent.get().hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
