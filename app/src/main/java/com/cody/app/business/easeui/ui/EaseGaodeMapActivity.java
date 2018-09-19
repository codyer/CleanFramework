/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cody.app.business.easeui.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.cody.xf.utils.LogUtil;
import com.cody.app.R;
import com.cody.app.business.im.adapter.EaseGaoDeMapAdapter;

public class EaseGaodeMapActivity extends EaseBaseActivity implements LocationSource,
        AMapLocationListener, PoiSearch.OnPoiSearchListener {

    private final static String TAG = "map";
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private MapView mMapView;
    private ListView mListView;
    private AMap aMap;
    private Marker mLocMarker;
    private boolean mFirstFix = false;

    private PoiSearch.Query mQuery;
    private PoiSearch mPoiSearch;
    private EaseGaoDeMapAdapter mEaseGaoDeMapAdapter;
    static AMapLocation lastLocation = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ease_activity_gaodemap);
        if (!checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(this
                    , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        }
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
        mListView = (ListView) findViewById(R.id.listView);
        mEaseGaoDeMapAdapter = new EaseGaoDeMapAdapter(this, 0);
        mListView.setAdapter(mEaseGaoDeMapAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mEaseGaoDeMapAdapter.setSelectPositioin(position);
                mEaseGaoDeMapAdapter.notifyDataSetChanged();
            }
        });
        findViewById(R.id.sendBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLocation();
            }
        });
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle
        // .LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType
        // ，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
//        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
//                R.drawable.ic_dingwei);
//        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
//        myLocationStyle.myLocationIcon(des);
//        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
//        myLocationStyle.strokeColor(Color.TRANSPARENT);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setLocationSource(this);// 设置定位监听
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        uiSettings.setZoomControlsEnabled(false);// 设置是否显示缩放按钮
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                try {
                    mLocMarker.setPosition(new LatLng(cameraPosition.target.latitude
                            , cameraPosition.target.longitude));
                } catch (Exception e) {
                    LogUtil.e(e.getMessage());
                }
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                try{
                    mPoiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(cameraPosition.target.latitude
                            , cameraPosition.target.longitude), 1000));
                    mPoiSearch.searchPOIAsyn();
                } catch (Exception e){
                    LogUtil.e(e.getMessage());
                }
            }
        });
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == 0) {
//                initViews();
            } else {
                showToast("权限被禁止，无法获取位置");
//                finish();
                ActivityCompat.requestPermissions(this
                        , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            }
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                LatLng location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                if (!mFirstFix) {
                    mFirstFix = true;
//                    addCircle(location, amapLocation.getAccuracy());//添加定位精度圆
                    addMarker(location);//添加定位图标
                } else {
//                    mLocMarker.setPosition(location);
                }

                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));

                if (mPoiSearch == null) {
                    PoiSearch.Query mQuery = new PoiSearch.Query("", "", amapLocation.getCity());
                    //keyWord表示搜索字符串，
                    //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
                    //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
                    mQuery.setPageSize(20);// 设置每页最多返回多少条poiitem
                    mQuery.setPageNum(0);//设置查询页码
                    mPoiSearch = new PoiSearch(this, mQuery);
                    mPoiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(amapLocation.getLatitude()
                            , amapLocation.getLongitude()), 1000));
                    mPoiSearch.setOnPoiSearchListener(this);
                    mPoiSearch.searchPOIAsyn();
                }
                lastLocation = amapLocation;
                mlocationClient.stopLocation();
            } else {
//                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
//                Log.e("AmapErr",errText);
//                mLocationErrText.setVisibility(View.VISIBLE);
//                mLocationErrText.setText(errText);
            }
        }
    }

    private void addMarker(LatLng latlng) {
        if (mLocMarker != null) {
            return;
        }
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = 2;
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.ic_dingwei, bitmapOptions);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);

        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        options.draggable(false);
        mLocMarker = aMap.addMarker(options);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
        deactivate();
        mFirstFix = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    //
    @Override
    protected void onDestroy() {
        super.onDestroy();
//		if (mLocClient != null)
//			mLocClient.stop();
        mMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
//		unregisterReceiver(mBaiduReceiver);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (poiResult != null) {
            mEaseGaoDeMapAdapter.clear();
            mEaseGaoDeMapAdapter.addAll(poiResult.getPois());
            mEaseGaoDeMapAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public void sendLocation() {
        if (mEaseGaoDeMapAdapter != null) {
            sendLocation(mEaseGaoDeMapAdapter.getItem(mEaseGaoDeMapAdapter.getSelectPositioin()));
        } else if (lastLocation != null) {
            Intent intent = this.getIntent();
            intent.putExtra("latitude", lastLocation.getLatitude());
            intent.putExtra("longitude", lastLocation.getLongitude());
            intent.putExtra("address", lastLocation.getPoiName());
            this.setResult(RESULT_OK, intent);
        }
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    public void sendLocation(PoiItem item) {
        if (item != null
                && item.getLatLonPoint() != null
                && item.getIndoorData() != null
                && mEaseGaoDeMapAdapter != null) {
            Intent intent = this.getIntent();
            intent.putExtra("latitude", item.getLatLonPoint().getLatitude());
            intent.putExtra("longitude", item.getLatLonPoint().getLongitude());
            intent.putExtra("address", item.getTitle());
            this.setResult(RESULT_OK, intent);
        }
    }

}
