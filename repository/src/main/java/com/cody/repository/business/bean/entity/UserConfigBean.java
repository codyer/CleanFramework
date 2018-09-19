package com.cody.repository.business.bean.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by cody.yi on 2018/7/29.
 * 用户配置存数据库
 */
public class UserConfigBean extends RealmObject{
    @PrimaryKey
    private String imId;

    /**
     * 快捷回复 默认false 使用后标记为true
     */
    private boolean quickReply;

    /**
     * 快捷栏设置
     */
    private boolean quickBar;

    /**
     * 系统欢迎语
     */
    private boolean systemWelcome;

    public UserConfigBean() {
    }

    public UserConfigBean(String imId, boolean quickReply, boolean quickBar, boolean systemWelcome) {
        this.imId = imId;
        this.quickReply = quickReply;
        this.quickBar = quickBar;
        this.systemWelcome = systemWelcome;
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public boolean isQuickReply() {
        return quickReply;
    }

    public void setQuickReply(boolean quickReply) {
        this.quickReply = quickReply;
    }

    public boolean isQuickBar() {
        return quickBar;
    }

    public void setQuickBar(boolean quickBar) {
        this.quickBar = quickBar;
    }

    public boolean isSystemWelcome() {
        return systemWelcome;
    }

    public void setSystemWelcome(boolean systemWelcome) {
        this.systemWelcome = systemWelcome;
    }
}
