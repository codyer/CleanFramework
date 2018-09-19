package com.cody.repository.business.bean.im;

/**
 * Created by  qiaoping.xiao on 2017/9/13.
 */

public class QuickReplyBean {


    /**
     * ownerId : 2_c1f6148b-b68d-448b-941c-69185a7e0a68
     * id : 59cf11cc-c97d-4728-86f7-549e32c0c97c
     * replyContent : 您好，有什么需要帮助的吗
     * enable : 1
     */

    private String ownerId;
    private String id;
    private String replyContent;
    private String title;
    private int enable;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
