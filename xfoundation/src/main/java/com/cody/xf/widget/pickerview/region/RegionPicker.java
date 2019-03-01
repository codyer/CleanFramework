package com.cody.xf.widget.pickerview.region;

import android.content.Context;
import android.text.TextUtils;

import com.cody.xf.R;
import com.cody.xf.utils.FetchRawUtil;
import com.cody.xf.utils.LocationUtil;
import com.cody.xf.widget.pickerview.OptionsPickerView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cody.yi on 2018/12/3.
 * 地址选择
 */
public class RegionPicker extends OptionsPickerView<Region> {
    private static Boolean sInit = false;
    private static int sOption1 = 0;
    private static int sOption2 = 0;
    private static int sOption3 = 0;
    private static ArrayList<Region> sProvince = new ArrayList<>();
    private static ArrayList<ArrayList<Region>> sCity = new ArrayList<>();
    private static ArrayList<ArrayList<ArrayList<Region>>> sDistrict = new ArrayList<>();
    private OnRegionSelectListener mRegionSelectListener;

    public interface OnRegionSelectListener {
        void onSelect(Region province, Region city, Region district);
    }

    public RegionPicker(Context context) {
        super(context);
        if (!sInit) {
            throw new ExceptionInInitializerError("请先初始化地址数据");
        }
        setTextSize(15);
        setPicker(sProvince, sCity, sDistrict, true);
        setCyclic(false);
        setCancelable(true);
        setOnOptionSelectListener(new OnOptionSelectListener() {
            @Override
            public void onOptionSelect(int option1, int option2, int option3) {
                //快速操作，联动执行过慢导致错误数据，直接忽略
                if (sProvince.size() <= option1 || sCity.size() <= option1 || sDistrict.size() <= option1 ||
                        sCity.get(option1).size() <= option2 || sDistrict.get(option1).size() <= option2 ||
                        sDistrict.get(option1).get(option2).size() <= option3) return;
                sOption1 = option1;
                sOption2 = option2;
                sOption3 = option3;
                if (mRegionSelectListener != null) {
                    mRegionSelectListener.onSelect(
                            sProvince.get(option1),
                            sCity.get(option1).get(option2),
                            sDistrict.get(option1).get(option2).get(option3)
                    );
                }
            }
        });
    }

    /**
     * 地址数据初始化
     */
    public static void initData() {
        if (sInit) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (sInit) {
                    if (sInit) return;
                    try {
                        final ArrayList<ProvinceBean> data = (ArrayList<ProvinceBean>) FetchRawUtil.getInstance()
                                .fetchListBean(R.raw.xf_province_bean, ProvinceBean.class);
                        ArrayList<Region> city0;
                        ArrayList<ArrayList<Region>> district1;
                        ArrayList<Region> district0;
                        String province = null;
                        String city = null;
                        String district = null;
                        if (LocationUtil.getLocation() != null) {
                            province = LocationUtil.getLocation().getProvince();
                            city = LocationUtil.getLocation().getCity();
                            district = LocationUtil.getLocation().getDistrict();
                        }
                        for (int i = 0; i < data.size(); i++) {
                            city0 = new ArrayList<>();
                            district1 = new ArrayList<>();
                            for (int j = 0; j < data.get(i).getCity().size(); j++) {
                                district0 = new ArrayList<>();
                                for (int k = 0; k < data.get(i).getCity().get(j).getDistrict().size(); k++) {
                                    district0.add(new Region(data.get(i).getCity().get(j).getDistrict().get(k)));
                                    if (sOption3 == 0 &&
                                            TextUtils.equals(district, data.get(i).getCity().get(j).getDistrict().get(k)
                                                    .getName())) {
                                        sOption3 = k;
                                    }
                                }
                                city0.add(new Region(data.get(i).getCity().get(j)));
                                if (sOption2 == 0 &&
                                        TextUtils.equals(city, data.get(i).getCity().get(j).getName())) {
                                    sOption2 = j;
                                }
                                district1.add(district0);
                            }
                            if (sOption1 == 0 && TextUtils.equals(province, data.get(i).getName())) {
                                sOption1 = i;
                            }
                            sProvince.add(new Region(data.get(i)));
                            sCity.add(city0);
                            sDistrict.add(district1);
                        }
                        sInit = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        sInit = false;
                    }
                }
            }
        }).start();
    }

    /**
     * 更新地址选择器
     */
    public static void setLocationChanged() {
        if ((sOption1 + sOption2 + sOption3) > 0) return;
        String province = null;
        String city = null;
        String district = null;
        if (LocationUtil.getLocation() != null) {
            province = LocationUtil.getLocation().getProvince();
            city = LocationUtil.getLocation().getCity();
            district = LocationUtil.getLocation().getDistrict();
        }
        if (sInit) {
            for (int i = 0; i < sProvince.size(); i++) {
                if (sOption1 == 0 && TextUtils.equals(province, sProvince.get(i).getName())) {
                    sOption1 = i;
                    for (int j = 0; j < sCity.get(sOption1).size(); j++) {
                        if (sOption2 == 0 && TextUtils.equals(city, sCity.get(sOption1).get(j).getName())) {
                            sOption2 = j;
                            for (int k = 0; k < sDistrict.get(sOption1).get(sOption2).size(); k++) {
                                if (sOption3 == 0 && TextUtils.equals(district, sDistrict.get(sOption1).get
                                        (sOption2)

                                        .get(k).getName())) {
                                    sOption3 = k;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    public RegionPicker setRegionSelectListener(OnRegionSelectListener regionSelectListener) {
        mRegionSelectListener = regionSelectListener;
        return this;
    }

    @Override
    public void show() {
        if (sInit) {
            setSelectOptions(sOption1, sOption2, sOption3);
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (sInit) {
            super.dismiss();
        }
    }
}
