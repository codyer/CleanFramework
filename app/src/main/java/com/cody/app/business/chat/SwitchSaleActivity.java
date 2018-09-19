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
import com.cody.handler.business.presenter.SwitchSalePresenter;
import com.cody.app.business.adapter.SwitchSaleAdapter;
import com.google.gson.Gson;
import com.cody.handler.business.viewmodel.ItemSwitchSaleViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.bean.im.BaseMessageBean;
import com.cody.repository.business.bean.im.CustomMessage;
import com.cody.repository.business.bean.im.IMSwitchGuideBean;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.widget.dialog.AlertEditDialog;

//import com.alibaba.fastjson.JSON;

/**
 * Created by chy on 2016/8/30.
 * IM中切换导购界面
 */
public class SwitchSaleActivity extends ListWithHeaderActivity<SwitchSalePresenter, ItemSwitchSaleViewModel> {
    private AlertEditDialog mAlertEdite;
    private ItemSwitchSaleViewModel mItem;
    private String mCustomerName;

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getString(R.string.switch_sale));
        header.setVisible(true);
        header.setLeft(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            mCustomerName = getIntent().getExtras().getString("userName");
        }
        mAlertEdite = new AlertEditDialog(this).builder().setTitle(getString(R.string.remark_info)).setCompleteListener(new AlertEditDialog.OnEditCompleteListener() {
            @Override
            public void onComplete(String result) {
                //用户输入备注后，处理切换导购的结果
                BaseMessageBean message = new BaseMessageBean();
                message.setType(CustomMessage.PersonGuideSwitch);
                IMSwitchGuideBean guide = new IMSwitchGuideBean();
                guide.setType(CustomMessage.PersonGuideSwitch);
                guide.setTargetIMID(mItem.getImId());
                guide.setTargetName(mItem.getName());
                guide.setMerchandise("");
                //添加导购员昵称
                String name;
                if (!TextUtils.isEmpty(Repository.getLocalValue(LocalKey.NICK_NAME))) {
                    name = Repository.getLocalValue(LocalKey.NICK_NAME);
                } else {
                    name = Repository.getLocalValue(LocalKey.REAL_NAME);
                }
                guide.setSGRemark("导购员" + name + ":" + result);
//                message.setJSONContent(JSON.toJSONString(guide));
                Gson gson = new Gson();
                message.setJSONContent(gson.toJson(guide));
                Intent intent = new Intent();
//                intent.putExtra("chatbean", JSON.toJSONString(message));
                intent.putExtra("chatbean", gson.toJson(message));
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

    @Override
    protected BaseRecycleViewAdapter<ItemSwitchSaleViewModel> buildRecycleViewAdapter() {
        return new SwitchSaleAdapter(getViewModel());
    }

    @Override
    protected SwitchSalePresenter buildPresenter() {
        return new SwitchSalePresenter();
    }

    @Override
    public void onClick(View view) {
        LogUtil.d("onClick" + view);
        switch (view.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        mItem = getViewModel().get(position);
        mAlertEdite.show();
    }
}
