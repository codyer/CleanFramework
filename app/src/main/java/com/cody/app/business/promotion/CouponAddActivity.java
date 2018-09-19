package com.cody.app.business.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.cody.app.R;
import com.cody.app.databinding.ActivityAddCouponBinding;
import com.cody.app.databinding.ItemAddCouponBinding;
import com.cody.app.framework.activity.ListWithHeaderBaseActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.handler.business.presenter.CouponAddPresenter;
import com.cody.handler.framework.presenter.OnActionListener;
import com.cody.handler.business.viewmodel.CouponAddViewModel;
import com.cody.handler.business.viewmodel.ItemCouponChannelViewModel;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Create by jiquan.zhong  on 2018/8/16.
 * description:优惠券增发
 */
public class CouponAddActivity extends ListWithHeaderBaseActivity<CouponAddPresenter,
        CouponAddViewModel, ItemCouponChannelViewModel, ActivityAddCouponBinding> {
    private static final String COUPON_ID = "couponId";

    public static void startAddCoupon(Fragment fragment, int couponId, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putInt(COUPON_ID, couponId);
        ActivityUtil.navigateToForResult(fragment, CouponAddActivity.class, requestCode, bundle);
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setLeft(true);
        header.setLeftResId(R.drawable.xf_ic_close_black);
        header.setTitle("增发优惠券");
        header.setLineVisible(false);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_coupon;
    }

    @Override
    protected CouponAddPresenter buildPresenter() {
        return new CouponAddPresenter();
    }

    @Override
    protected CouponAddViewModel buildViewModel(Bundle savedInstanceState) {
        CouponAddViewModel viewModel = new CouponAddViewModel();
        viewModel.setHasEndInfo(false);
        int couponId = getIntent().getIntExtra(COUPON_ID, -1);
        if (couponId == -1) {
            ToastUtil.showToast("优惠券Id无效");
            finish();
        }
        viewModel.setCouponId(couponId);
        return viewModel;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btnAdd) {
            int count = 0;
            String number;
            for (int i = 0; i < getViewModel().size(); i++) {
                number = getViewModel().get(i).getCount().get();
                if (TextUtils.isEmpty(number)) continue;
                if (!TextUtils.isDigitsOnly(number) || number.equals("0") || Integer.parseInt(number) > 10000) {
                    ToastUtil.showToast(R.string.input_add_counts);
                    return;
                }
                count += Integer.parseInt(number);
            }
            if (count == 0) {
                ToastUtil.showToast(R.string.input_add_counts_hint);
                return;
            }
            final int finalCount = count;
            getPresenter().addCoupon(TAG, new OnActionListener() {
                @Override
                public void onSuccess() {
                    Intent intent = new Intent();
                    intent.putExtra(CouponListFragment.COUPON_ADD_COUNT, finalCount);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {

    }

    @Override
    protected BaseRecycleViewAdapter<ItemCouponChannelViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemCouponChannelViewModel>(getViewModel()) {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
                ItemAddCouponBinding binding;
                if (viewHolder.getItemBinding() instanceof ItemAddCouponBinding) {
                    binding = (ItemAddCouponBinding) viewHolder.getItemBinding();
                    binding.addCounts.addTextChangedListener(new NumberTextWatcher(binding.addCounts));
                }
            }

            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_add_coupon;
            }
        };
    }

    @Override
    protected PullLoadMoreRecyclerView buildPullLoadMoreRecyclerView() {
        return getBinding().fwList;
    }

    class NumberTextWatcher implements TextWatcher {
        private EditText mTextView;
        private boolean mChanged = false;

        public NumberTextWatcher(EditText textView) {
            mTextView = textView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String str = mTextView.getText().toString();
            while (!TextUtils.isEmpty(str) && str.startsWith("0") && str.length() > 1) {
                str = str.substring(1);
                mChanged = true;
            }
            if (mChanged) {
                mChanged = false;
                mTextView.setText(str);
                mTextView.setSelection(str.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
