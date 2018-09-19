package com.cody.xf.widget.pickerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cody.xf.R;
import com.cody.xf.widget.pickerview.view.BasePickerView;
import com.cody.xf.widget.pickerview.view.WheelTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择器
 * Created by Sai on 15/11/22.
 */
public class TimePickerView extends BasePickerView implements View.OnClickListener {
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private WheelTime wheelTime;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private OnTimeSelectListener timeSelectListener;
    private int startYear, endYear;

    public TimePickerView(Context context, Type type, int startYear, int endYear) {
        super(context);
        this.startYear = startYear;
        this.endYear = endYear;
        initParam(context, type);
    }

    public TimePickerView(Context context, Type type) {
        super(context);
        initParam(context, type);
    }

    private void initParam(Context context, Type type) {
        LayoutInflater.from(context).inflate(R.layout.xf_picker_view_time, contentContainer);
        // -----确定和取消按钮
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----时间转轮
        final View timePikerView = findViewById(R.id.timePicker);
        wheelTime = new WheelTime(timePikerView, type);

        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (startYear > 0 && endYear > 0) {
            wheelTime.setStartYear(startYear);
            wheelTime.setEndYear(endYear);
        }
        wheelTime.setPicker(year, month, day, hours, minute);
        setCancelable(true);
    }

    /**
     * 设置可以选择的时间范围
     * 要在setTime之前调用才有效果
     *
     * @param startYear 开始年份
     * @param endYear   结束年份
     */
    public TimePickerView setRange(int startYear, int endYear) {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
        return this;
    }

    /**
     * 设置选中时间
     *
     * @param date 时间
     */
    public TimePickerView setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);
        return this;
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public TimePickerView setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
        return this;
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
        } else {
            if (timeSelectListener != null) {
                try {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                    timeSelectListener.onTimeSelect(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dismiss();
        }
    }

    public TimePickerView setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
        return this;
    }

    public TimePickerView setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH, MONTH, YEAR
    }// 六种选择模式，年月日时分，年月日，时分，月日时分,年月，月

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
    }
}
