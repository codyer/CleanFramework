package com.cody.app.business.chat;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cody.app.R;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.app.framework.activity.ListWithHeaderAndSearchActivity;
import com.cody.handler.business.presenter.GoodsListPresenter;
import com.cody.app.business.adapter.RecommendGoodsAdapter;
import com.cody.handler.business.viewmodel.ItemGoodsViewModel;
import com.google.gson.Gson;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.bean.im.BaseMessageBean;
import com.cody.repository.business.bean.im.CustomMessage;
import com.cody.repository.business.bean.im.RecommendGoodsBean;
import com.cody.xf.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by  qiaoping.xiao on 2016/12/22.
 * 推荐商品
 */
public class RecommendGoodsActivity extends ListWithHeaderAndSearchActivity<GoodsListPresenter,
        ItemGoodsViewModel> {
    private static final String KEY_LIMIT = "LIMIT";
    private static final int DEFAULT_LIMIT = 3;//最大选择数量
    private Gson gson = new Gson();
    int startPosition;
    private int limit = DEFAULT_LIMIT;

    public static void startRecommendGoods(int limit, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_LIMIT, limit);
        ActivityUtil.navigateToForResult(RecommendGoodsActivity.class, requestCode, bundle);
    }

    @Override
    protected BaseRecycleViewAdapter<ItemGoodsViewModel> buildRecycleViewAdapter() {
        limit = getIntent().getIntExtra(KEY_LIMIT, DEFAULT_LIMIT);
        return new RecommendGoodsAdapter(getViewModel(), limit);
    }

    @Override
    protected int getEmptyViewId() {
        return R.layout.empty_view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.headerRightText:
                List<ItemGoodsViewModel> selectPositions = ((RecommendGoodsAdapter) mRecyclerViewAdapter)
                        .getSelectPositions();
                List<BaseMessageBean> list = new ArrayList<>();
                for (ItemGoodsViewModel selectPosition : selectPositions) {
                    BaseMessageBean bean = new BaseMessageBean();
                    bean.setType(CustomMessage.PersonRecommendGoods);
                    RecommendGoodsBean item = new RecommendGoodsBean();
                    item.setType(CustomMessage.PersonRecommendGoods);
                    item.setImageUrl(selectPosition.getPic_url());
                    item.setMerchandiseID(selectPosition.getId());
                    item.setMerchandiseName(selectPosition.getBrand_name());
                    //去除¥号
                    String sale_price = selectPosition.getSale_price();
                    if (sale_price.contains("¥")) {
                        startPosition = sale_price.indexOf("¥") + 1;
                    }
                    item.setMerchandisePrice(sale_price.contains("¥") ? sale_price.substring
                            (startPosition) : sale_price);
                    String jsonObject = gson.toJson(item);
                    bean.setJSONContent(jsonObject);
                    list.add(bean);
                }
                String goods = gson.toJson(list);
                //从聊天页面创建报价单，直接设置回调 finish
                Intent intent = new Intent();
                intent.putExtra("chatbeanArray", goods);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, final int position, long id) {
        ((RecommendGoodsAdapter) mRecyclerViewAdapter).setSelectPosition(position);
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle("推荐商品");
        header.setLeft(true);
        header.setRight(true);
        header.setRightIsText(true);
        header.setRightText("确定");
        header.setLineVisible(false);
    }

    @Override
    protected GoodsListPresenter buildPresenter() {
        return new GoodsListPresenter();
    }

    @Override
    public void onSearchClick(View view) {
        getPresenter().tapSearch(TAG);
    }
}
