package com.cody.app.business;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;

import com.cody.app.BR;
import com.cody.app.R;
import com.cody.app.business.customer.MyCustomerActivity;
import com.cody.app.business.im.ImTabActivity;
import com.cody.app.business.order.BusinessOrderListActivity;
import com.cody.app.business.personal.PersonalInformationActivity;
import com.cody.app.business.promotion.EventMarketingActivity;
import com.cody.app.business.scan.ScanActivity;
import com.cody.app.databinding.ActivityMainBinding;
import com.cody.app.business.easeui.ImManager;
import com.cody.app.framework.activity.BaseBindingActivity;
import com.cody.app.framework.activity.HtmlActivity;
import com.cody.app.framework.receiver.TagAliasOperatorHelper;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.cody.handler.business.presenter.MainPresenter;
import com.cody.handler.business.viewmodel.MainImItemViewModel;
import com.cody.handler.business.viewmodel.MainViewModel;
import com.cody.repository.business.interaction.constant.Constant;
import com.cody.repository.business.interaction.constant.H5Url;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.SystemBarUtil;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.widget.marqueen.MarqueeFactory;
import com.cody.xf.widget.marqueen.MarqueeView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 首页
 */
public class MainActivity extends BaseBindingActivity<MainPresenter, MainViewModel, ActivityMainBinding> {
    private static Boolean isExit = false;
    private static final int REQUEST_PERMISSION = 0X1;
    private MessageListener mMessageListener = new MessageListener();
    private MarqueeFactory<MainImItemViewModel> mMarqueeFactory;

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EasyPermissions.hasPermissions(this, com.cody.xf.common.Constant.PERMISSIONS)) {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_remind), REQUEST_PERMISSION, com.cody.xf.common.Constant.PERMISSIONS);
        }
        getPresenter().getRank(TAG);
        //极光推送设置别名
        TagAliasOperatorHelper.getInstance().setAlias(Repository.getLocalValue(LocalKey.OPEN_ID));
        getPresenter().registerPush(TAG);
        getBinding().switchOnline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ImManager.getInstance().isReconnectIM();
                getPresenter().online(TAG, isChecked);
                BuryingPointUtils.build(MainActivity.class, 3969).submitF();
            }
        });
        mMarqueeFactory = new MarqueeFactory<MainImItemViewModel>(this, getBinding().mainMarqueeView) {
            @Override
            public View createItemView(int type, MainImItemViewModel item) {
                ViewDataBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main_item,
                        null, false);
                if (binding != null) {
                    binding.setVariable(BR.viewModel, item);
                    return binding.getRoot();
                }
                return null;
            }

            @Override
            public int getViewType(int position) {
                return position;
            }
        };
        getBinding().mainMarqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                getBinding().mainCustomerManage.performClick();
            }
        });
        EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
    }

    @Override
    protected void onImmersiveReady() {
        SystemBarUtil.setStatusBarDarkMode(this, false);
        SystemBarUtil.tintStatusBar(this, Color.TRANSPARENT);
        SystemBarUtil.immersiveStatusBar(this, 0.0f);
        SystemBarUtil.setPadding(this, getBinding().mainSetting);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewModel().setImageUrl(Repository.getLocalValue(LocalKey.PICTURE_URL));
        updateItemChat();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMarqueeFactory != null) {
            mMarqueeFactory.getMarqueeView().stopFlipping();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mMessageListener);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter buildPresenter() {
        return new MainPresenter();
    }

    @Override
    protected MainViewModel buildViewModel(Bundle savedInstanceState) {
        MainViewModel viewModel = new MainViewModel();
        viewModel.setImageUrl(Repository.getLocalValue(LocalKey.PICTURE_URL));
        if (!TextUtils.isEmpty(Repository.getLocalValue(LocalKey.NICK_NAME))) {
            viewModel.setUserName(Repository.getLocalValue(LocalKey.NICK_NAME));
        } else {
            viewModel.setUserName(Repository.getLocalValue(LocalKey.REAL_NAME));
        }
        String role = Repository.getLocalValue(LocalKey.IDENTITY);
        if (!TextUtils.isEmpty(role) && role.equals(Constant.Role.SHOP_MANAGER)) {
            viewModel.setShopManager(true);
        } else {
            viewModel.setShopManager(false);
        }
        int status = Repository.getLocalInt(LocalKey.USER_STATUS);
        viewModel.getOnline().set(status == Constant.Status.online);
        return viewModel;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mainSetting:
                ActivityUtil.navigateTo(SettingActivity.class);
                break;
            case R.id.mainMyCustomer:
                BuryingPointUtils.build(MainActivity.class, 4216).submitF();
                ActivityUtil.navigateTo(MyCustomerActivity.class);
                break;
            case R.id.imagePortrait:
            case R.id.imagePortraitMask:
                BuryingPointUtils.build(MainActivity.class, 3968).submitF();
                ActivityUtil.navigateTo(PersonalInformationActivity.class);
                break;
            case R.id.mainCustomerManage:
                BuryingPointUtils.build(MainActivity.class, 3970).submitF();
                getViewModel().getItems().clear();// 清空数据
                ActivityUtil.navigateTo(ImTabActivity.class);
                break;
            case R.id.mainOrderManage:
                BuryingPointUtils.build(MainActivity.class, 3971).submitF();
                ActivityUtil.navigateTo(BusinessOrderListActivity.class);
                break;
            case R.id.mainShopManage:
                BuryingPointUtils.build(MainActivity.class, 3972).submitF();
                HtmlActivity.startHtml(null, H5Url.SHOP_MANAGE);
                break;
            case R.id.mainActivityMarketing:
                ActivityUtil.navigateTo(EventMarketingActivity.class);
                BuryingPointUtils.build(MainActivity.class, 4217).submitF();
                break;
            case R.id.mainScanButton:
                BuryingPointUtils.build(MainActivity.class, 3973).submitF();
                ScanActivity.startScanActivity();
                break;
            case R.id.mainManualButton:
                BuryingPointUtils.build(MainActivity.class, 3974).submitF();
                HtmlActivity.startHtml(null, H5Url.MANUAL_ORDER);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && requestCode == ScanActivity.SCAN_REQUEST_CODE) {
            String result = intent.getStringExtra(ScanActivity.SCAN_RESULT);
            if (!TextUtils.isEmpty(result)) {
                try {
                    result = URLEncoder.encode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                LogUtil.d("扫码结果：" + result);
                String url = H5Url.SCAN_ORDER + result;
                HtmlActivity.startHtml(null, url);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByDoubleClick(); //调用双击退出函数
        }
        return false;
    }

    //监听消息变化
    private class MessageListener implements EMMessageListener {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            updateItemChat();
            LogUtil.d("MessageListener onMessageReceived");
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            updateItemChat();
            LogUtil.d("MessageListener onCmdMessageReceived");
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            updateItemChat();
            LogUtil.d("MessageListener onMessageRead");
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
            LogUtil.d("MessageListener onMessageDelivered");
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
            updateItemChat();
            LogUtil.d("MessageListener onMessageRecalled");
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            updateItemChat();
            LogUtil.d("MessageListener onMessageChanged");
        }
    }

    private void updateItemChat() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getPresenter().updateUnreadMsgCount(TAG);
                if (getViewModel().getItems().size() == 0) return;
                if (mMarqueeFactory != null) {
                    mMarqueeFactory.setData(getViewModel().getItems());
                }
            }
        });
    }

    private void exitByDoubleClick() {
        Timer tExit;
        if (!isExit) {
            isExit = true;
            // 准备退出
            ToastUtil.showToast(getString(R.string.click_back_two_times));
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000);
            // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
        }
    }
}
