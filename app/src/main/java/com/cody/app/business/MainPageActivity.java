package com.cody.app.business;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.cody.app.R;
import com.cody.app.business.mine.MineFragment;
import com.cody.app.databinding.ActivityMainPageBinding;
import com.cody.app.framework.activity.BaseBindingActivity;
import com.cody.handler.framework.presenter.MainPagePresenter;
import com.cody.handler.framework.viewmodel.MainPageViewModel;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class MainPageActivity extends BaseBindingActivity<MainPagePresenter,
        MainPageViewModel, ActivityMainPageBinding> implements TabHost.OnTabChangeListener {

    private static final int REQUEST_PERMISSION = 0X1;
    private static Boolean isExit = false;
    private FragmentTabHost fragmentTabHost;

    /**
     * 获取对应的icons
     */
    private final static int[] icons = new int[]{
            R.drawable.item_main_tab_mine_ico_selector,
            R.drawable.item_main_tab_mine_ico_selector,
            R.drawable.item_main_tab_mine_ico_selector,
            R.drawable.item_main_tab_mine_ico_selector,
    };
    private final static Class[] fragments = new Class[]{
            MineFragment.class,
            MineFragment.class,//无tab
            MineFragment.class,
            MineFragment.class,
    };
    /**
     * 子类提供有binding的资源ID
     */
    @Override
    protected int getLayoutID() {
        return R.layout.activity_main_page;
    }

    /**
     * 每个view保证只有一个Presenter
     */
    @Override
    protected MainPagePresenter buildPresenter() {
        return new MainPagePresenter();
    }

    /**
     * 每个view保证只有一个ViewModel，当包含其他ViewModel时使用根ViewModel包含子ViewModel
     * ViewModel可以在创建的时候进行初始化，也可以在正在进行绑定（#setBinding）的时候初始化
     */
    @Override
    protected MainPageViewModel buildViewModel(Bundle savedInstanceState) {
        return new MainPageViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MainPageActivity.REQUEST_PERMISSION);
        }
        initTabHost();
    }

    //初始化主页选项卡视图
    private void initTabHost() {
        //实例化FragmentTabHost对象
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        //去掉分割线
        TabWidget tabWidget = fragmentTabHost.getTabWidget();
        tabWidget.setDividerDrawable(null);

        String[] names = getResources().getStringArray(R.array.fragments_main_tab);

        for (int i = 0; i < names.length; i++) {
            LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.fw_item_main_tab, null);
            ImageView item_icon = (ImageView) view.findViewById(R.id.item_icon);
            item_icon.setImageResource(icons[i]);
            TextView item_name = (TextView) view.findViewById(R.id.item_name);
            item_name.setText(names[i]);
            TabHost.TabSpec mTabSpec = fragmentTabHost.newTabSpec(names[i]).setIndicator(view);
            fragmentTabHost.addTab(mTabSpec, fragments[i], null);
        }

        fragmentTabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String tabId) {
        LogUtil.d("tag", tabId + "");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LogUtil.d(TAG + "权限申请成功！");
            } else {
                LogUtil.d(TAG + "权限申请失败！");
            }
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByDoubleClick(); //调用双击退出函数
        }
        return false;
    }

    private void exitByDoubleClick() {
        Timer tExit;
        if (!isExit) {
            isExit = true;
            // 准备退出
            ToastUtil.showToast(getString(R.string.fw_click_back_two_times));
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

    private boolean checkPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
