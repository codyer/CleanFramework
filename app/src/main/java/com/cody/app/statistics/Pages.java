package com.cody.app.statistics;

import com.cody.app.business.SettingActivity;
import com.cody.app.business.LoginNewActivity;
import com.cody.app.business.MainActivity;
import com.cody.app.business.MiniAppActivity;
import com.cody.app.business.UserInfoActivity;
import com.cody.app.business.customer.BargainCustomerActivity;
import com.cody.app.business.customer.CouponsActivity;
import com.cody.app.business.customer.IntentionCustomerActivity;
import com.cody.app.business.customer.MyCustomerActivity;
import com.cody.app.business.customer.PotentialCustomerActivity;
import com.cody.app.business.im.ChatActivity;
import com.cody.app.business.im.ConversationListFragment;
import com.cody.app.business.im.GroupListFragment;
import com.cody.app.business.im.GroupModifyActivity;
import com.cody.app.business.promotion.CouponListActivity;
import com.cody.app.business.promotion.EventMarketingActivity;
import com.cody.app.business.promotion.PromotionListActivity;
import com.cody.repository.framework.statistics.PVManager;

/**
 * Created by cody.yi on 2018/7/30.
 * 所有的埋点pv z点配置类
 */
public class Pages {
    private static PVManager.P[] mActivityP = new PVManager.P[]{
//            new P(SettingActivity.class, 10,2,"测试"),
            new PVManager.P(LoginNewActivity.class, 3964, 3965, "登录页"),
            new PVManager.P(MainActivity.class, 3966, 3967, "首页"),
            new PVManager.P(UserInfoActivity.class, 4012, 4013, "客户信息管理"),
            new PVManager.P(ChatActivity.class, 3999, 4000, "龙果APP咨询页"),
            new PVManager.P(GroupModifyActivity.class,3996,3997,"选择分组"),
            new PVManager.P(SettingActivity.class,4221,4222,"设置"),
            new PVManager.P(MiniAppActivity.class,4224,4225,"小程序二维码"),
            new PVManager.P(EventMarketingActivity.class,4265,4266,"活动营销"),
            new PVManager.P(PromotionListActivity.class,4269,4270,"活动列表页"),
            new PVManager.P(CouponListActivity.class,4274,4275,"优惠券列表"),
            new PVManager.P(MyCustomerActivity.class,4228,4229,"B端龙果APP_我的客户"),
            new PVManager.P(PotentialCustomerActivity.class,4233,4234,"B端龙果APP我的客户_潜在客户列表页"),
            new PVManager.P(IntentionCustomerActivity.class,4240,4241,"B端龙果APP我的客户_意向客户"),
            new PVManager.P(BargainCustomerActivity.class,4248,4249,"B端龙果APP我的客户_成交客户"),
            new PVManager.P(CouponsActivity.class,4258,4259,"B端龙果APP我的客户_优惠券列表")
    };

    private static PVManager.P[] mFragmentP = new PVManager.P[]{
//            new P(PersonalTabNewFragment.class, 10,2,"测试"),
            new PVManager.P(ConversationListFragment.class, 3977, 3978),
            new PVManager.P(GroupListFragment.class,3991,3992,"客户分组")
    };

    /**
     * 要在HxStat.install(this);之前调用
     */
    public static void install() {
        new Pages();
    }

    public Pages() {
        PVManager.setActivityP(mActivityP);
        PVManager.setFragmentP(mFragmentP);
    }
}
