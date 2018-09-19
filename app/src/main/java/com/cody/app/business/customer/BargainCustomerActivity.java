package com.cody.app.business.customer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cody.app.R;
import com.cody.app.business.im.ChatActivity;
import com.cody.app.databinding.ActivityBargainCustomerBinding;
import com.cody.app.framework.activity.ListWithHeaderBaseActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.presenter.BargainCustomerPresenter;
import com.cody.handler.business.viewmodel.BargainCustomerViewModel;
import com.cody.handler.business.viewmodel.ItemPotentialCustomerViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.bean.SendCouponBean;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.ResourceUtil;
import com.cody.xf.widget.dialog.AlertDialog;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 成交客户页面
 */
public class BargainCustomerActivity extends ListWithHeaderBaseActivity<BargainCustomerPresenter,
        BargainCustomerViewModel, ItemPotentialCustomerViewModel, ActivityBargainCustomerBinding> {

    /**
     * 筛选项
     * 注意:修改筛选项的话也要把{@link BargainCustomerViewModel#setFilterType(int)}里的逻辑改掉
     */
    private static final String[] filterArray = {"全部", "IM咨询", "浏览店铺", "浏览单品", "加购物车", "加心愿单", "领券"};

    public static void startBargainCustomer() {
        ActivityUtil.navigateTo(BargainCustomerActivity.class);
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.customer_empty_view;
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setVisible(true);
        header.setTitle("成交客户");
        header.setLeft(true);
        header.setLeftResId(R.drawable.xf_ic_back_black);
        header.setRightIsText(true);
        header.setRightText("发券");
        header.setRightColorId(ResourceUtil.getColor(R.color.main_blue));
        header.setLineVisible(false);
    }

    @Override
    protected BaseRecycleViewAdapter<ItemPotentialCustomerViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemPotentialCustomerViewModel>(getViewModel()) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_bargain_customer;
            }
        };
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_bargain_customer;
    }

    @Override
    protected BargainCustomerPresenter buildPresenter() {
        return new BargainCustomerPresenter();
    }

    @Override
    protected BargainCustomerViewModel buildViewModel(Bundle savedInstanceState) {
        BargainCustomerViewModel viewModel = new BargainCustomerViewModel();
        return viewModel;
    }

    @Override
    public void onUpdate(Object... args) {
        super.onUpdate(args);
        if (args != null) {
            if (args.length > 1) {
                if (BargainCustomerPresenter.TAG_CALL_USER.equals(args[0])) {//打电话
                    callUser(String.valueOf(args[1]));
                } else if (BargainCustomerPresenter.TAG_SEND_MESSAGE.equals(args[0])) {//发短信
                    sendSMS(String.valueOf(args[1]));
                }
            }
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        if (id == R.id.callBtn) {//打电话
            //埋点 B端龙果APP成交客户_营销按钮（打电话、发消息、IM咨询）
            BuryingPointUtils.build(BargainCustomerActivity.class, 4252)
                    .addTag("打电话").submitF();
            getPresenter().getUserMobileByOpenId(BargainCustomerPresenter.TAG_CALL_USER, position);
        } else if (id == R.id.sendMessage) {//发消息
            //埋点 B端龙果APP成交客户_营销按钮（打电话、发消息、IM咨询）
            BuryingPointUtils.build(BargainCustomerActivity.class, 4252)
                    .addTag("发消息").submitF();
            getPresenter().getUserMobileByOpenId(BargainCustomerPresenter.TAG_SEND_MESSAGE, position);
        } else if (id == R.id.imConsult) {//IM咨询
            //埋点 B端龙果APP成交客户_营销按钮（打电话、发消息、IM咨询）
            BuryingPointUtils.build(BargainCustomerActivity.class, 4252)
                    .addTag("IM咨询").submitF();
            ChatActivity.startChat(getViewModel().get(position).getImId());
        }
        if (getViewModel().getIsSendCoupons().get()) {
            getViewModel().get(position).toggleCheck();
            if (getViewModel().get(position).getCheck().get()) {
                getViewModel().addCheckCount();
            } else {
                getViewModel().subtractCheckCount();
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        hideFilterPopWindow();
        switch (v.getId()) {
            case R.id.headerRightText:
                //埋点 B端龙果APP成交客户_发券按钮
                BuryingPointUtils.build(BargainCustomerActivity.class, 4250).submitF();
                getViewModel().toggleIsSendCoupons();
                if (getViewModel().getIsSendCoupons().get()) {
                    getViewModel().getHeaderViewModel().setRightColorId(ResourceUtil.getColor(R.color.gray_333333));
                } else {
                    getViewModel().getHeaderViewModel().setRightColorId(ResourceUtil.getColor(R.color.main_blue));
                }
                getPresenter().toggleIsSendCoupon();
                break;
            case R.id.orderTime:
                //埋点 B端龙果APP成交客户_Tab（下单时间、累计消费、最近行为）
                BuryingPointUtils.build(BargainCustomerActivity.class, 4251).addTag("下单时间").submitF();
                switch (getViewModel().getOrderTimeStatus().get()) {
                    case -1:
                        getViewModel().setOrderTimeStatus(1);
                        setTextViewDrawableRight(getBinding().orderTime, R.drawable.ic_arrows_up_down_2);
                        break;
                    case 1:
                        getViewModel().setOrderTimeStatus(-1);
                        setTextViewDrawableRight(getBinding().orderTime, R.drawable.ic_arrows_up_down_1);
                        break;
                }
                onRefresh();
                scrollToTop();
                break;
            case R.id.orderAmount:
                //埋点 B端龙果APP成交客户_Tab（下单时间、累计消费、最近行为）
                BuryingPointUtils.build(BargainCustomerActivity.class, 4251).addTag("累计消费").submitF();
                getBinding().orderAmount.setChecked(true);
                switch (getViewModel().getOrderAmountStatus().get()) {
                    case -1:
                        getViewModel().setOrderAmountStatus(1);
                        setTextViewDrawableRight(getBinding().orderAmount, R.drawable.ic_arrows_up_down_2);
                        break;
                    case 0:
                    case 1:
                        getViewModel().setOrderAmountStatus(-1);
                        setTextViewDrawableRight(getBinding().orderAmount, R.drawable.ic_arrows_up_down_1);
                        break;
                }
                onRefresh();
                scrollToTop();
                break;
            case R.id.recentBehavior:
                getViewModel().setTabChecked(BargainCustomerViewModel.TAB_CHECK_ORDER_RECENT_BEHAVIOR);
                toggleFilterPopWindow();
                break;
            case R.id.checkAll:
                //埋点 B端龙果APP成交客户发券状态_全选
                BuryingPointUtils.build(BargainCustomerActivity.class, 4253).submitF();
                getPresenter().toggleCheckAll();
                onRefresh();
                break;
            case R.id.unSendCoupons:
                //埋点 B端龙果APP成交客户发券状态_未发券
                BuryingPointUtils.build(BargainCustomerActivity.class, 4254).submitF();
                getPresenter().toggleUnSendCoupon();
                onRefresh();
                break;
            case R.id.confirm:
                //埋点 B端龙果APP成交客户发券状态_确认按钮
                BuryingPointUtils.build(BargainCustomerActivity.class, 4255).submitF();
                SendCouponBean sendCouponBean = new SendCouponBean();
                sendCouponBean.setQueryField(getViewModel().getQueryField());
                sendCouponBean.setUserList(getViewModel().getUserListBean());
                if (getViewModel().getUncouponCheck().get()) {
                    sendCouponBean.setType("1");
                }
                sendCouponBean.setSelectedUserCount(getViewModel().getCheckCount().get());

                CouponsActivity.startCoupons(sendCouponBean
                        , getViewModel().getTotalCheck().get()
                                || getViewModel().getUncouponCheck().get());
                break;
        }
    }

    /**
     * 切换最近行为筛选弹框
     */
    public void toggleFilterPopWindow() {

        if (getBinding().filterListView.getAdapter() == null) {
            final List<Map<String, String>> datas = new ArrayList<>();
            for (String value : filterArray) {
                Map<String, String> data = new HashMap<>();
                data.put("text", value);
                datas.add(data);
            }
            final SimpleAdapter adapter = new SimpleAdapter(this
                    , datas
                    , R.layout.item_bargain_filter
                    , new String[]{"text"}
                    , new int[]{R.id.itemValue}) {

            };

            getBinding().filterListView.setAdapter(adapter);

            getBinding().filterListView.setSelection(0);

            getBinding().filterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //埋点 B端龙果APP成交客户_Tab（下单时间、累计消费、最近行为）
                    BuryingPointUtils.build(BargainCustomerActivity.class, 4251)
                            .addTag(filterArray[position]).submitF();
                    getBinding().filterListView.setItemChecked(position, true);

                    for (int i = 0; i < getBinding().filterListView.getChildCount(); i++) {
                        View child = getBinding().filterListView.getChildAt(i);
                        if (child instanceof CheckedTextView) {
                            if (position == i) {
                                ((CheckedTextView) child).setChecked(true);
                            } else {
                                ((CheckedTextView) child).setChecked(false);
                            }
                        }
                    }

                    getViewModel().toggleFilterLayoutShow();

                    if (position == 0) {
                        getViewModel().setRecentBehavior("最近行为");
                        getViewModel().setRecentBehaviorStatus(0);
                        setTextViewDrawableRight(getBinding().recentBehavior, R.drawable.ic_arrows_down_4);
                    } else {
                        getViewModel().setRecentBehavior(filterArray[position]);
                        getViewModel().setRecentBehaviorStatus(1);
                        setTextViewDrawableRight(getBinding().recentBehavior, R.drawable.ic_arrows_down_5);
                    }

                    getViewModel().setFilterType(position);

                    onRefresh();
                    scrollToTop();
                }
            });
        }

        getViewModel().toggleFilterLayoutShow();

        if (getViewModel().getFilterLayoutShow().get()) {
            setTextViewDrawableRight(getBinding().recentBehavior, R.drawable.ic_arrows_up_6);
            getViewModel().setRecentBehaviorStatus(1);
        } else {
            hideFilterPopWindow();
        }
    }

    /**
     * 隐藏最近行为筛选弹框
     */
    private void hideFilterPopWindow() {
        getViewModel().setFilterLayoutShow(false);
        if (!isRecentBehaviorChecked()) {
            setTextViewDrawableRight(getBinding().recentBehavior, R.drawable.ic_arrows_down_4);
            getViewModel().setRecentBehaviorStatus(0);
        } else {
            setTextViewDrawableRight(getBinding().recentBehavior, R.drawable.ic_arrows_down_5);
            getViewModel().setRecentBehaviorStatus(1);
        }
    }

    /**
     * 最近行为是否选中
     *
     * @return
     */
    private boolean isRecentBehaviorChecked() {
        return getBinding().filterListView.getCheckedItemPosition() > 0;
    }

    /**
     * 设置textView drawableRight
     *
     * @param textView
     * @param id
     */
    private void setTextViewDrawableRight(TextView textView, @DrawableRes int id) {
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null
                , ResourceUtil.getDrawable(id), null);
    }

    /**
     * 打电话
     *
     * @param phone
     */
    private void callUser(final String phone) {
        new AlertDialog(this).builder()
                .setTitle(phone)
                .setNegativeButton("取消", null)
                .setPositiveButton("呼叫", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + phone));
                        startActivity(intent);
                    }
                }).show();
    }

    /**
     * 发送短信
     *
     * @param phone
     */

    private void sendSMS(String phone) {
        Uri smsToUri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        startActivity(intent);
    }
}
