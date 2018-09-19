package com.cody.repository.business.bean.im;

import android.text.TextUtils;

import com.hyphenate.chat.EMMessage;
import com.cody.xf.utils.JsonUtil;

public class BaseMessageBean {
    //自定义消息
    public static final String MESSAGE_ATTR_KEY = "extraText";

    public int type = CustomMessage.NO_DIY;
    public String JSONContent = "";

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getJSONContent() {
        return JSONContent;
    }

    public void setJSONContent(String JSONContent) {
        this.JSONContent = JSONContent;
    }

    /**
     * 获取自定义消息bean类
     *
     * @param clazz
     * @return
     */
    public <E> E getBean(Class<E> clazz) {
        if (clazz == null) throw new NullPointerException("clazz is null");
        switch (type) {
            case CustomMessage.PersonRecommendGoods:
            case CustomMessage.SystemMerchandise:
                if (clazz.equals(RecommendGoodsBean.class)) {
                    break;
                }
            case CustomMessage.PersonGuideSwitch:
                if (clazz.equals(IMSwitchGuideBean.class)) {
                    break;
                }
            case CustomMessage.PersonCoupon:
                if (clazz.equals(IMCouponBean.class)) {
                    break;
                }
            case CustomMessage.SysGreetingWords:
                if (clazz.equals(ImSysWelWordsBean.class)) {
                    break;
                }
            case CustomMessage.UserLike:
                if (clazz.equals(IMUserLikeBean.class)) {
                    break;
                }
            case CustomMessage.UserOrder:
                if (clazz.equals(IMOrderBean.class)) {
                    break;
                }
            case CustomMessage.PersonVideo:
                if (clazz.equals(IMVideoCallMessageBean.class)) {
                    break;
                }
            default:
                throw new NullPointerException("clazz is null");
        }
        return JsonUtil.fromJson(JSONContent, clazz);
    }

    /**
     * 获取自定义消息bean类
     */
    public static BaseMessageBean getExtraMsgBean(EMMessage message) {
        BaseMessageBean bean = new BaseMessageBean();
        if (message != null) {
            String msg = message.getStringAttribute(BaseMessageBean.MESSAGE_ATTR_KEY, null);
            if (!TextUtils.isEmpty(msg)) {
                bean = JsonUtil.fromJson(msg, BaseMessageBean.class);
            }
        }
        return bean;
    }
}
