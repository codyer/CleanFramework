package com.cody.repository.business.bean.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by  dong.wang
 * Date: 2017/3/6
 * Time: 10:58
 * Description: 置顶用户
 */

public class TopUserBean extends RealmObject {

    public TopUserBean() {

    }

    public TopUserBean(String conversationId) {
        mConversationId = conversationId;
    }

    @PrimaryKey
    private String mConversationId;

    public String getConversationId() {
        return mConversationId;
    }

    public void setConversationId(String conversationId) {
        this.mConversationId = conversationId;
    }
}
