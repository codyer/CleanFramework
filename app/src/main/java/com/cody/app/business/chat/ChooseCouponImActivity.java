/*
 * Copyright (c)  Created by Cody.yi on 2016/8/30.
 */

package com.cody.app.business.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cody.app.R;
import com.cody.app.framework.activity.ListWithHeaderActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.presenter.ChooseCouponPresenter;
import com.cody.app.business.adapter.ChooseCouponAdapter;
import com.google.gson.Gson;
import com.cody.handler.business.viewmodel.ItemCouponImViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.bean.CouponNumBean;
import com.cody.repository.business.bean.im.BaseMessageBean;
import com.cody.repository.business.bean.im.CustomMessage;
import com.cody.repository.business.bean.im.IMCouponBean;
import com.cody.repository.business.interaction.constant.Constant;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.widget.dialog.AlertDialog;

import java.util.HashMap;
import java.util.Map;

//import com.alibaba.fastjson.JSON;

/**
 * Created by chy on 2016/8/30.
 * IM中选择优惠券界面
 */
public class ChooseCouponImActivity extends ListWithHeaderActivity<ChooseCouponPresenter,
        ItemCouponImViewModel> {
    String mUserOpenId;
    private ItemCouponImViewModel itemCouponImViewModel;

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getString(R.string.choose_coupon));
        header.setVisible(true);
        header.setLeft(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mUserOpenId = bundle.getString("userOpenId");
        }
    }

    @Override
    protected BaseRecycleViewAdapter<ItemCouponImViewModel> buildRecycleViewAdapter() {
        return new ChooseCouponAdapter(getViewModel());
    }

    @Nullable
    @Override
    protected ChooseCouponPresenter buildPresenter() {
        return new ChooseCouponPresenter();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        LogUtil.d("onClick" + view);
        switch (view.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        ((ChooseCouponAdapter) mRecyclerViewAdapter).setSelectPosition(position);
        itemCouponImViewModel = getViewModel().get(position);
        new AlertDialog(ChooseCouponImActivity.this).builder()
                .setTitle("确定要发送此优惠劵吗？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, String> map = new HashMap<>();
                        map.put("openId", Repository.getLocalValue(LocalKey.OPEN_ID));
                        map.put("couponId", itemCouponImViewModel.getCouponId() + "");
                        if (!TextUtils.isEmpty(mUserOpenId)) {
                            map.put("userOpenId", mUserOpenId);
                            getPresenter().tapReceiveCoupon(Constant.RequestType.TYPE_1, map);
                        } else {
                            ToastUtil.showToast("没有指定用户！");
                        }
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).show();
    }


    @Override
    public void onUpdate(Object... args) {
        super.onUpdate(args);
        if (args != null && args.length > 1 && args[0].equals(Constant.RequestType.TYPE_1)) {
            //处理返回结果，设置result结果到chat页面
            IMCouponBean imCouponBean = new IMCouponBean();
            imCouponBean.setType(CustomMessage.PersonCoupon);
            imCouponBean.setCouponID(itemCouponImViewModel.getCouponId());
            imCouponBean.setCouponName(itemCouponImViewModel.getPromotionType());
            imCouponBean.setCouponScopeType(itemCouponImViewModel.getOwnerType());
            imCouponBean.setCouponScope(itemCouponImViewModel.getOwnerName());
            imCouponBean.setCoupontype(itemCouponImViewModel.getCupontypeId());
            imCouponBean.setCouponSubName(itemCouponImViewModel.getCouponSubName());
            imCouponBean.setCouponBound(itemCouponImViewModel.getCouponBound());
            imCouponBean.setCouponCode(((CouponNumBean) args[1]).getCuponCode());
            imCouponBean.setStartDate(itemCouponImViewModel.getStartT());
            imCouponBean.setEndDate(itemCouponImViewModel.getEndT());
            BaseMessageBean baseMessageBean = new BaseMessageBean();
            baseMessageBean.setType(CustomMessage.PersonCoupon);
//            baseMessageBean.setJSONContent(JSON.toJSONString(imCouponBean));
            Gson gson = new Gson();
            baseMessageBean.setJSONContent(gson.toJson(imCouponBean));
            Intent intent = new Intent();
//            intent.putExtra("chatbean", JSON.toJSONString(baseMessageBean));
            intent.putExtra("chatbean", gson.toJson(baseMessageBean));
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.empty_view_coupon;
    }

}
