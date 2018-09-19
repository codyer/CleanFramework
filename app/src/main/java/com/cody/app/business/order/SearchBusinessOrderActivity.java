package com.cody.app.business.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivitySearchBusinessOrderBinding;
import com.cody.app.framework.activity.AbsListActivity;
import com.cody.app.framework.activity.HtmlActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.app.business.widget.OrderQrCodePopWindow;
import com.cody.handler.business.presenter.order.BusinessOrderSearchPresenter;
import com.cody.handler.business.viewmodel.ItemBusinessOrderListViewModel;
import com.cody.handler.business.viewmodel.SearchBusinessOrderViewModel;
import com.cody.repository.business.interaction.constant.H5Url;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.SoftKeyboardUtil;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import static com.cody.xf.XFoundation.getContext;


/**
 * 订单搜索
 */
public class SearchBusinessOrderActivity extends AbsListActivity<BusinessOrderSearchPresenter,
        SearchBusinessOrderViewModel,
        ItemBusinessOrderListViewModel, ActivitySearchBusinessOrderBinding> {

    private boolean isFirst = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideLoading();
        getBinding().header.searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == MotionEvent.ACTION_DOWN) {
                    String key = getBinding().header.searchView.getText().toString().trim();
                    if (!TextUtils.isEmpty(key)) {
                        goSearch();
                    } else {
                        ToastUtil.showToast("请输入关键字");
                    }
                    return true;
                }
                return false;
            }
        });
        getBinding().fwList.setPullRefreshEnable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getRefreshPage(TAG);
    }

    private void goSearch() {
        if (isFirst) {
            getBinding().fwList.setEmptyView(getLayoutInflater().inflate(R.layout.empty_view, null));
            isFirst = true;
        }
        SoftKeyboardUtil.hiddenSoftInput(SearchBusinessOrderActivity.this);
        getBinding().fwList.setPullRefreshEnable(true);
        getPresenter().getRefreshPage(TAG);
    }

    @Override
    public void onItemClick(RecyclerView parent, final View view, int position, long id) {
        switch ((int) id) {
            case R.id.tv_pay:
                getPresenter().getQrCode(TAG, getViewModel().get(position).getSerialNumber(), 300, 300, new BusinessOrderSearchPresenter.OrderQrCodeListener() {
                    @Override
                    public void onQrCodeSuccess(com.cody.repository.business.bean.order.OrderQrCodeBean qrCodeBean) {
                        OrderQrCodePopWindow splashPopWindow = new OrderQrCodePopWindow(getContext(), qrCodeBean);
                        splashPopWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                    }
                });
                break;
            case R.id.mobile:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + getViewModel().get(position).getUserMobile());
                intent.setData(data);
                startActivity(intent);
                break;
            default:
                int type = getViewModel().get(position).getOrderType();
                String serialNumber = getViewModel().get(position).getSerialNumber();
                String url = H5Url.ORDER_DETAIL + getViewModel().get(position).getOrderId();
                url += "&serialNumber=" + serialNumber + "&orderTypeValue=" + type;
                if ("UNPAID".equals(getViewModel().get(position).getOrderStatus())) {
                    url += "&flag=1";
                }
                HtmlActivity.startHtml(null, url);
                break;
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_search_business_order;

    }

    @Override
    protected BusinessOrderSearchPresenter buildPresenter() {
        return new BusinessOrderSearchPresenter(Repository.getLocalValue(LocalKey.SHOP_ID));
    }

    @Override
    protected SearchBusinessOrderViewModel buildViewModel(Bundle savedInstanceState) {
        return new SearchBusinessOrderViewModel();
    }

    @Override
    protected BaseRecycleViewAdapter<ItemBusinessOrderListViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemBusinessOrderListViewModel>(getViewModel()) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_business_order_list;
            }
        };
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.empty_order;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                finish();
                break;
        }
    }
}
