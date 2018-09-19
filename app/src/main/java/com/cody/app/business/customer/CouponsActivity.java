package com.cody.app.business.customer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivityCouponsBinding;
import com.cody.app.framework.activity.AbsListActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.google.gson.JsonObject;
import com.cody.handler.business.presenter.SelectCouponListPresenter;
import com.cody.handler.business.viewmodel.CouponViewModel;
import com.cody.handler.business.viewmodel.CouponsListViewModel;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.repository.business.bean.SendCouponBean;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.JsonUtil;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dong.wang
 * Date: 2018/8/18
 * Time: 15:27
 * Description: 选择优惠券
 */
public class CouponsActivity extends AbsListActivity<SelectCouponListPresenter, CouponsListViewModel,
        CouponViewModel, ActivityCouponsBinding> {

    private static final String KEY_BEAN = "bean";
    private static final String KEY_SELECT = "select";
    private SendCouponBean bean;
    private boolean isSelected; // 选中全选或者未发券按钮时是true

    public static void startCoupons(SendCouponBean bean, boolean isSelected) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_BEAN, bean);
        bundle.putBoolean(KEY_SELECT, isSelected);
        ActivityUtil.navigateTo(CouponsActivity.class, bundle);
    }

    @Override
    protected BaseRecycleViewAdapter<CouponViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<CouponViewModel>(getViewModel()) {

            @Override
            public int getItemViewType(int position) {
                return getViewModel().get(position).getItemType();
            }

            @Override
            public int getItemLayoutId(int viewType) {
                switch (viewType) {
                    case CouponViewModel.ITEM_TYPE_BAG:
                        return R.layout.item_coupons_bag;
                    default:
                        return R.layout.item_coupons;
                }
            }
        };
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.empty_view;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_coupons;
    }

    @Override
    protected SelectCouponListPresenter buildPresenter() {
        if (getIntent() != null) {
            bean = getIntent().getParcelableExtra(KEY_BEAN);
            isSelected = getIntent().getBooleanExtra(KEY_SELECT, false);
        }
        return new SelectCouponListPresenter(bean.getSelectedUserCount());
    }

    @Override
    protected CouponsListViewModel buildViewModel(Bundle savedInstanceState) {
        CouponsListViewModel viewModels = new CouponsListViewModel();
        viewModels.getHeaderViewModel().setVisible(true);
        viewModels.getHeaderViewModel().setLeft(true);
        viewModels.getHeaderViewModel().setTitle(getString(R.string.select_coupon));
        return viewModels;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.tv_select_all:
                //埋点 B端龙果APP选择优惠券_全选
                BuryingPointUtils.build(CouponsActivity.class, 4261).submitF();
                boolean isSelectedAll = getViewModel().getIsSelectedAll().get();
                if (isSelectedAll) {
                    getViewModel().getIsSelectedAll().set(false);
                } else {
                    getViewModel().getIsSelectedAll().set(true);
                }

                int sendSum = 0;
                for (int i = 0; i < getViewModel().size(); i++) {
                    if (getViewModel().get(i).isEnough()) {
                        if (!isSelectedAll)
                            sendSum++;
                        getViewModel().get(i).setIsSelected(!isSelectedAll);
                    }
                }
                getViewModel().setSentSum(sendSum);
                break;
            case R.id.tv_ok:
                //埋点 B端龙果APP选择优惠券_发送按钮
                BuryingPointUtils.build(CouponsActivity.class, 4262).submitF();
                sendCoupons();
                break;
        }
    }

    /**
     * 发送
     */
    private void sendCoupons() {
        if (bean != null && getViewModel().size() > 0) {
            List<Integer> couponIds = new ArrayList<>();
            for (CouponViewModel item : getViewModel()) {
                if (item.getIsSelected().get()) {
                    couponIds.add(item.getCouponId());
                }
            }
            bean.setCouponIds(couponIds);
            JsonObject params = JsonUtil.toJsonObject(bean);
            if (isSelected) {
                getPresenter().batchUsersTakeCoupons(TAG, params, new OnActionListener() {
                    @Override
                    public void onSuccess() {
                        callBackActivity();
                    }
                });
            } else {
                getPresenter().usersTakeCoupon(TAG, params, new OnActionListener() {
                    @Override
                    public void onSuccess() {
                        callBackActivity();
                    }
                });
            }
        }
    }

    private void callBackActivity() {
        new AlertDialog.Builder(this)
                .setMessage("正在发券中，稍后可在系统消息中查看发券结果！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityUtil.navigateTo(MyCustomerActivity.class);
                    }
                })
                .setCancelable(false)
                .create().show();
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        CouponViewModel item = getViewModel().get(position);
        if (!item.isEnough()) return;

        //埋点 B端龙果APP我的客户_选择优惠券
        BuryingPointUtils.build(CouponsActivity.class, 4260).submitF();
        if (item.getIsSelected().get()) {
            item.setIsSelected(false);
            getViewModel().setSentSum(getViewModel().getSentSum().get() - 1);
            if (getViewModel().size() > getViewModel().getSentSum().get()) {
                getViewModel().getIsSelectedAll().set(false);
            }
        } else {
            item.setIsSelected(true);
            getViewModel().setSentSum(getViewModel().getSentSum().get() + 1);
            if (getViewModel().size() == getViewModel().getSentSum().get()) {
                getViewModel().getIsSelectedAll().set(true);
            }
        }
    }
}
