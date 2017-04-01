package com.cody.app.framework.widget.popup;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.cody.app.R;
import com.cody.xf.widget.pickerview.lib.WheelView;

import java.util.List;

public class WheelRegionPop extends BasePopupWindow implements View.OnClickListener {

    private ICallBackTime iCallBackTime;
    private TextView tvCancel;
    private TextView tvConfirm;
    private WheelView lvOrder;
    private WheelRegionAdapter mAdapter;
    private List<String> mList;

    public WheelRegionPop(Activity activity, List<String> mList, ICallBackTime iCallBackTime) {
        super(activity, R.layout.pop_wheel_region, false);
        this.iCallBackTime = iCallBackTime;
        this.mList = mList;
        initView();
    }


    @Override
    protected void callbackView(View view) {
        tvCancel = (TextView) view.findViewById(R.id.cancel);
        tvConfirm = (TextView) view.findViewById(R.id.confirm);
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        lvOrder = (WheelView) view.findViewById(R.id.wheel);


        mAdapter = new WheelRegionAdapter(mList);
        lvOrder.setAdapter(mAdapter);
        lvOrder.setCyclic(false);
        lvOrder.setCurrentItem(0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                popDismiss();
                break;
            case R.id.confirm:
                iCallBackTime.upDate(mList.get(lvOrder.getCurrentItem()));
                popDismiss();
                break;
        }
    }

    public interface ICallBackTime {
        void upDate(String order);
    }

}