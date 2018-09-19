package com.cody.app.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import com.cody.app.R;
import com.cody.app.business.chat.RecommendGoodsActivity;
import com.cody.app.databinding.ActivitySystemGreetingBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.google.gson.reflect.TypeToken;
import com.cody.handler.business.presenter.SystemGreetingPresenter;
import com.cody.handler.business.viewmodel.ItemSystemGreetingGoodsViewModel;
import com.cody.handler.business.viewmodel.SystemGreetingViewModel;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.bean.im.BaseMessageBean;
import com.cody.repository.business.bean.im.RecommendGoodsBean;
import com.cody.repository.business.bean.im.ReplyInfoBean;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.JsonUtil;

import java.util.List;

/**
 * 系统欢迎词设置页
 */
public class SystemGreetingActivity extends WithHeaderActivity<SystemGreetingPresenter, SystemGreetingViewModel,
        ActivitySystemGreetingBinding> {
    private final static int SYSTEM_GREETING_GOOD_CODE = 5;
    private final static String SYSTEM_GREETING_BEAN = "system_greeting_bean";

    public static void startSystemGreeting(ReplyInfoBean.GreetingReplyVoListBean bean, int requestCode) {
        Bundle bundle = new Bundle();
        if (bean != null)
            bundle.putSerializable(SYSTEM_GREETING_BEAN, bean);
        ActivityUtil.navigateToForResult(SystemGreetingActivity.class, requestCode, bundle);
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setLeft(true);
        header.setVisible(true);
        header.setTitle(getString(R.string.sys_welcome_words));
        header.setRight(true);
        header.setRightIsText(true);
        header.setRightText(getString(R.string.save_now));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_system_greeting;
    }

    @Override
    protected SystemGreetingPresenter buildPresenter() {
        return new SystemGreetingPresenter();
    }

    @Override
    protected SystemGreetingViewModel buildViewModel(Bundle savedInstanceState) {
        return new SystemGreetingViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReplyInfoBean.GreetingReplyVoListBean bean = (ReplyInfoBean.GreetingReplyVoListBean) getIntent()
                .getSerializableExtra(SYSTEM_GREETING_BEAN);
        if (bean == null) {
            finish();
            return;
        }
        getViewModel().setGreetingId(bean.getId());
        getViewModel().setOwnerId(bean.getOwnerId());
        getViewModel().getEnable().set(bean.getEnable() == 1);
        getViewModel().getText().set(bean.getReplyContent());
        for (ReplyInfoBean.GreetingReplyVoListBean.RecommendatoryMerchandiseBean item : bean
                .getRecommendatoryMerchandise()) {
            ItemSystemGreetingGoodsViewModel good = new ItemSystemGreetingGoodsViewModel();
            good.setGoodsId(item.getMerchandiseId());
            good.setGoodsName(item.getMerchandiseName());
            good.setGoodsPrice(item.getMerchandisePrice());
            good.setGoodsImageUrl(item.getImageUrl());
            getViewModel().getGoods().add(good);
        }
        getBinding().recommendGoods.setLayoutManager(new LinearLayoutManager(this));
        getBinding().recommendGoods.setAdapter(new BaseRecycleViewAdapter<ItemSystemGreetingGoodsViewModel>
                (getViewModel().getGoods()) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_system_greeting_goods;
            }
        });
        getBinding().switchOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getPresenter().switchOpen(TAG, b);
            }
        });
        getBinding().replyContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getViewModel().setTextCount(charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.headerRightText:
                getPresenter().save(TAG, new OnActionListener() {
                    @Override
                    public void onSuccess() {
                        hideLoading();
                        finish();
                    }
                });
                break;
            case R.id.addGoods:
                RecommendGoodsActivity.startRecommendGoods(3, SYSTEM_GREETING_GOOD_CODE);
                break;
        }
    }

    @Override
    public void finish() {
        if (getViewModel().isChanged()) {
            setResult(RESULT_OK);
        }
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SYSTEM_GREETING_GOOD_CODE && resultCode == RESULT_OK) {
            String beanArray = data.getStringExtra("chatbeanArray");
            if (!TextUtils.isEmpty(beanArray)) {
                List<BaseMessageBean> list = JsonUtil.fromJson(beanArray, new TypeToken<List<BaseMessageBean>>() {
                }.getType());
                if (list != null) {
                    getViewModel().getGoods().clear();
                    for (BaseMessageBean message : list) {
                        String str = message.getJSONContent();
                        RecommendGoodsBean item = JsonUtil.fromJson(str, RecommendGoodsBean.class);
                        if (item == null) continue;
                        ItemSystemGreetingGoodsViewModel good = new ItemSystemGreetingGoodsViewModel();
                        good.setGoodsId(item.getMerchandiseID());
                        good.setGoodsName(item.getMerchandiseName());
                        good.setGoodsPrice(item.getMerchandisePrice());
                        good.setGoodsImageUrl(item.getImageUrl());
                        getViewModel().getGoods().add(good);
                    }
                }
            }
        }
    }
}
