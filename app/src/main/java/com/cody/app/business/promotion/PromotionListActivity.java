package com.cody.app.business.promotion;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;

import com.cody.app.R;
import com.cody.app.business.block.ShareBlock;
import com.cody.app.databinding.ItemPromotionListBinding;
import com.cody.app.framework.activity.HtmlActivity;
import com.cody.app.framework.activity.ListWithHeaderActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.presenter.PromotionListPresenter;
import com.cody.handler.business.viewmodel.ItemPromotionViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.bean.ShareBean;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Create by jiquan.zhong  on 2018/8/14.
 * description:活动页列表页
 */
public class PromotionListActivity extends ListWithHeaderActivity<PromotionListPresenter, ItemPromotionViewModel> {

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getString(R.string.promotion_list));
        header.setLeft(true);
    }

    @Override
    protected BaseRecycleViewAdapter<ItemPromotionViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemPromotionViewModel>(getViewModel()) {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, final int position) {
                super.onBindViewHolder(viewHolder, position);
                if (viewHolder.getItemBinding() instanceof ItemPromotionListBinding){
                    ItemPromotionListBinding binding = (ItemPromotionListBinding) viewHolder.getItemBinding();
                    binding.switchOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            BuryingPointUtils.build(PromotionListActivity.class,4271).submitF();
                            getPresenter().onLine(TAG, getViewModel().get(position), isChecked);
                        }
                    });
                }
            }

            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_promotion_list;
            }
        };
    }

    @Override
    protected PromotionListPresenter buildPresenter() {
        return new PromotionListPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, final int position, long id) {
        final ItemPromotionViewModel viewModel = getViewModel().get(position);
        switch (view.getId()) {
            case R.id.share:
                BuryingPointUtils.build(PromotionListActivity.class,4273).submitF();
                ShareBean shareBean = new ShareBean();
                shareBean.setTitle(viewModel.getShareTitle());
                shareBean.setDesc(viewModel.getShareDesc());
                shareBean.setImgUrl(viewModel.getShareImageUrl());
                shareBean.setUrl(viewModel.getUrl());
                ShareBlock.share(PromotionListActivity.this, shareBean, new ShareBlock.ShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA var1) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA var1) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA var1, Throwable var2) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA var1) {

                    }
                });
                break;
            case R.id.preview://预览
                BuryingPointUtils.build(PromotionListActivity.class,4272).submitF();
                String url = viewModel.getUrl();
                url += url.contains("?") ? "&preview=true" : "?preview=true";
                HtmlActivity.startHtml(viewModel.getPageTitle(), url);
                break;
        }
    }
}
