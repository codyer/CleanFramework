package com.cody.handler.business.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.hyphenate.chat.EMMessage;
import com.lzy.imagepicker.bean.ImageItem;
import com.cody.handler.framework.viewmodel.ListViewModel;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;

import java.util.ArrayList;

/**
 * Created by chen.huarong on 2018/7/20.
 */
public class ChatViewModel extends ListViewModel<ItemMessageViewModel> {

    public static final int MESSAGE_TYPE_RECV_TXT = 0;
    public static final int MESSAGE_TYPE_SEND_TXT = 1;
    public static final int MESSAGE_TYPE_SEND_IMAGE = 2;
    public static final int MESSAGE_TYPE_RECV_IMAGE = 3;
    public static final int MESSAGE_TYPE_SEND_LOCATION = 4;
    public static final int MESSAGE_TYPE_RECV_LOCATION = 5;
    public static final int MESSAGE_TYPE_SEND_VOICE = 6;
    public static final int MESSAGE_TYPE_RECV_VOICE = 7;
    public static final int MESSAGE_TYPE_SEND_VIDEO = 8;
    public static final int MESSAGE_TYPE_RECV_VIDEO = 9;
    public static final int MESSAGE_TYPE_SEND_FILE = 10;
    public static final int MESSAGE_TYPE_RECV_FILE = 11;
    public static final int MESSAGE_TYPE_RECV_GOODS = 12;
    public static final int MESSAGE_TYPE_SEND_GOODS = 13;
    public static final int MESSAGE_TYPE_RECV_GUIDE = 14;
    public static final int MESSAGE_TYPE_SEND_GUIDE = 15;
    public static final int MESSAGE_TYPE_RECV_COUPON = 16;
    public static final int MESSAGE_TYPE_SEND_COUPON = 17;
    public static final int MESSAGE_TYPE_SEND_VIDEO_CALL = 18;
    public static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 22;
    public static final int MESSAGE_TYPE_RECV_USERODER = 19;
    public static final int MESSAGE_TYPE_SYSGREETINGWORDS = 20;
    public static final int MESSAGE_TYPE_RECV_USELIKE = 21;

    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
    public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";
    public static final String MESSAGE_ATTR_IS_BIG_EXPRESSION = "em_is_big_expression";

    private String mCurrentLoginUser = UserInfoManager.getImId();//当前登录用户IM ID
    private String toChatUsername;
    public boolean conversationInited = false;
    private ObservableBoolean isOnline = new ObservableBoolean(false);
    private ObservableField<String> username = new ObservableField<>();//备注
    private ObservableField<String> groupId = new ObservableField<>();
    private String shopId = Repository.getLocalValue(LocalKey.SHOP_ID);
    private ObservableBoolean isIntGroup = new ObservableBoolean(false);//是否已分组
    public int currentPreviewImagePosition = 0;//当前预览图片在图片预览列表中的位置
    private ObservableField<String> groupName = new ObservableField<>();

    public ObservableField<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName.set(groupName);
    }

    public ObservableBoolean getIsIntGroup() {
        return isIntGroup;
    }

    public void setIsIntGroup(boolean isIntGroup) {
        this.isIntGroup.set(isIntGroup);
    }

    public String getShopId() {
        return shopId;
    }

    public ObservableField<String> getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId.set(groupId);
        setIsIntGroup(!TextUtils.isEmpty(groupId));
    }

    public ObservableField<String> getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getCurrentLoginUser() {
        return mCurrentLoginUser;
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

    public void setToChatUsername(String toChatUsername) {
        this.toChatUsername = toChatUsername;
    }

    public ObservableBoolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline.set(isOnline);
    }

    public ArrayList<ImageItem> getImages(int position) {
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        int index = 0;
        int imagePreviewPosition = 0;
        currentPreviewImagePosition = 0;
        for (ItemMessageViewModel itemMessageViewModel : this) {
            if (itemMessageViewModel.itemIMType == EMMessage.Type.IMAGE) {
                ImageItem imageItem = new ImageItem();
                if (itemMessageViewModel.getMessage().direct() == EMMessage.Direct.RECEIVE) {
                    imageItem.path = itemMessageViewModel.getImageMessageViewModel().remoteUrl;
                } else {
                    imageItem.path = itemMessageViewModel.getImageMessageViewModel().localUrl;
                }
                imageItems.add(imageItem);
                if (index == position) {
                    currentPreviewImagePosition = imagePreviewPosition;
                }
                imagePreviewPosition++;
            }
            index++;
        }
        return imageItems;
    }
}
