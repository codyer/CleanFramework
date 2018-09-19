package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.XItemViewModel;

/**
 * Create by jiquan.zhong  on 2018/7/26.
 * description:
 */
public class ItemSysMsgViewModel extends XItemViewModel {
    private String title;
    private String content;
    private String time;
    private String category;
    private String extras;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getExtras() {
        return extras;
    }
}
