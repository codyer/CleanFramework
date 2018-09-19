package com.cody.app.business.im;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cody.app.R;
import com.cody.app.framework.activity.TabWithHeaderActivity;
import com.cody.handler.framework.presenter.DefaultWithHeaderPresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dong.wang
 * Date: 2018/7/19
 * Time: 18:11
 * Description: 客户管理
 */
public class ImTabActivity extends TabWithHeaderActivity<DefaultWithHeaderPresenter> {

    public final static int CONTEXT_MENU_TYPE_CONVERSATION = 0x005;
    public final static int CONTEXT_MENU_TYPE_GROUP = 0x006;
    private GroupListFragment mGroupListFragment;

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getString(R.string.customer_management));
        header.setLeft(true);
        header.setRightResId(R.drawable.xf_ic_search_black);
        header.setToLeftRightResId(R.drawable.xf_ic_add_black);
        header.setLineVisible(false);
    }

    @Override
    protected DefaultWithHeaderPresenter buildPresenter() {
        return new DefaultWithHeaderPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getViewModel().getHeaderViewModel().setToLeftRight(position != 0);
                getViewModel().getHeaderViewModel().setRight(position != 0);
                if (position != 0) {
                    BuryingPointUtils.build(ImTabActivity.class, 3989).submitF();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.headerRightBtn:
                BuryingPointUtils.build(ImTabActivity.class, 4019).submitF();
                ActivityUtil.navigateTo(SearchContactActivity.class);
                break;
            case R.id.headerToLeftRightBtn:
                if (mGroupListFragment != null && mGroupListFragment.getViewModel() != null &&
                        mGroupListFragment.getViewModel().getTotal() >= GroupModifyActivity.MAX_GROUP_SUM) {
                    ToastUtil.showToast(R.string.toast_max_group);
                    return;
                }
                ActivityUtil.navigateTo(GroupManageActivity.class);
                break;
        }
    }

    @Override
    protected int getChildTabTitles() {
        return R.array.names_chat_tab;
    }

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        mGroupListFragment = new GroupListFragment();
        fragments.add(new ConversationListFragment());
        fragments.add(mGroupListFragment);
        return fragments;
    }
}
