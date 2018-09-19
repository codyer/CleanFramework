package com.cody.app.business;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cody.app.R;
import com.cody.app.business.order.BusinessOrderListActivity;
import com.cody.app.business.promotion.CouponFailListActivity;
import com.cody.app.framework.activity.HtmlActivity;
import com.cody.app.framework.activity.ListWithHeaderActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.presenter.SystemMsgPresenter;
import com.cody.handler.business.viewmodel.ItemSysMsgViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.interaction.constant.H5Url;
import com.cody.xf.utils.ActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Create by jiquan.zhong  on 2018/7/26.
 * description:
 */
public class SystemMessageActivity extends ListWithHeaderActivity<SystemMsgPresenter, ItemSysMsgViewModel> {
    private String currentUser;

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setLeft(true);
        header.setTitle("系统消息");
    }

    @Override
    protected BaseRecycleViewAdapter<ItemSysMsgViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemSysMsgViewModel>(getViewModel()) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_system_detail;
            }
        };
    }

    @Override
    protected SystemMsgPresenter buildPresenter() {
        currentUser = UserInfoManager.getImId();
        return new SystemMsgPresenter(currentUser);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        String cateGory = getViewModel().get(position).getCategory();
        String data = getViewModel().get(position).getExtras();
        if (TextUtils.isEmpty(cateGory)) {
            return;
        }
        switch (cateGory) {
            case "orderPaid":
                if (TextUtils.isEmpty(data)) {
                    ActivityUtil.navigateTo(BusinessOrderListActivity.class);
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        int orderType = jsonObject.optInt("orderType");
                        String serialNumber = jsonObject.optString("serialNumber");
                        if (orderType != 0 && !TextUtils.isEmpty(serialNumber)) {
                            String url = H5Url.PUSH_ORDER_DETAIL;
                            url += "&serialNumber=" + serialNumber + "&orderTypeValue=" + orderType + "&mainState=1";
                            HtmlActivity.startHtml(null, url);
                        } else {
                            ActivityUtil.navigateTo(BusinessOrderListActivity.class);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "ocean_coupon_message":
                if (!TextUtils.isEmpty(data)) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(data);
                        String version = jsonObject.optString("version");
                        boolean isClick = jsonObject.optBoolean("isClick");
                        String sendTime = jsonObject.optString("sendTime");
                        if (!TextUtils.isEmpty(version) && isClick) {
                            Bundle bundle = new Bundle();
                            bundle.putString(CouponFailListActivity.VERSION, version);
                            bundle.putString(CouponFailListActivity.SEND_TIME,sendTime);
                            ActivityUtil.navigateTo(CouponFailListActivity.class, bundle);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                break;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
        }
    }
}
