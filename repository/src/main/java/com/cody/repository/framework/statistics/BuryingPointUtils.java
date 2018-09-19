package com.cody.repository.framework.statistics;

import android.app.Activity;
import android.app.Application;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;

import com.cody.xf.XFoundation;
import com.cody.xf.utils.LogUtil;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Emcy-fu ;
 * on data:  2018/4/17 ;
 * <p>
 * 用于F点埋点
 * BuryingPointUtils.build(XXX.class,(mId)2847) --当前页面的class 和id
 * .addXXX() //传参
 * .submitF(); // 提交F点
 * <p>
 * 用于P点弹框类型埋点（需传id）
 * BuryingPointUtils.build(XXX.class,(mId)2847) --当前页面的class 和id
 * .addXXX() //传参
 * .submitP(XXX.class);//
 * <p>
 * 用于P点在activity或者fragment中埋点（适用于id写在xml的类型，不传id）
 * BuryingPointUtils.build()// 如果在xml中配置了id， id可传可不传，会自动覆盖
 * .addXXX() //传参
 * .submitParameter(HashMap hashMap2); //传onStatResume里面的hashMap2
 */
public class BuryingPointUtils {
    private HashMap<String, String> mParams = new HashMap<>();
    private Class mClass;
    private String mId;
    private String mPid;
    private int mActionPosition = 0;
    private int mActionTotal = 1;
    private int mTagSize = 0;

    private BuryingPointUtils() {
    }

    private BuryingPointUtils(Class mClass, int id) {
        this.mClass = mClass;
        this.mId = String.valueOf(id);
    }

    /**
     * 构建埋点对象
     * 在xml中配置过id的使用此构建函数，否则使用带参数的
     *
     * @return 埋点对象
     * @see #build(Class, int)
     */
    public static BuryingPointUtils build() {
        return new BuryingPointUtils();
    }

    /**
     * 构建埋点对象
     * 在xml中没有配置过id的使用此构建函数，否则使用不带参数的
     *
     * @param mClass 当前页面的class
     * @param id     当前页面的id
     * @return 埋点对象
     * @see #build()
     */
    public static BuryingPointUtils build(Class mClass, int id) {
        return new BuryingPointUtils(mClass, id);
    }

    /***
     * 大数据埋点--S点
     * @param height 浏览屏数埋点 （下拉滚动深度） 页面深度/高度
     */
    public void submitS(int height) {
        HashMap<String, String> params = new HashMap<>();
        params.put(StatKey.Parameter.id, mId);
        params.put(StatKey.Parameter.p_scene_nb, String.valueOf(height));
        String pid = buildParams();
        params.put(StatKey.Parameter.id, mId);
        if (mPid != null) {
            params.put(StatKey.Parameter.p_id, mPid);
        }
        if (!TextUtils.isEmpty(pid)) {
            params.put(StatKey.Parameter.p_action_id, pid);
        }
        if (mClass == null)throw new ExceptionInInitializerError("mClass == null");
        HxStat.statSHeight(mClass.getName(), null, params);
    }

    /***
     * 大数据埋点--Z点
     * @param time  页面停留时间埋点 单位：秒
     */
    public void submitZ(long time) {
        if (time <= 0) return;
        HashMap<String, String> params = new HashMap<>();
        String pid = buildParams();
        params.put(StatKey.Parameter.id, mId);
        if (mPid != null) {
            params.put(StatKey.Parameter.p_id, mPid);
        }
        if (!TextUtils.isEmpty(pid)) {
            params.put(StatKey.Parameter.p_action_id, pid);
        }
        if (mClass == null)throw new ExceptionInInitializerError("mClass == null");
        HxStat.statTime(mClass.getName(), null, params, time);
    }

    /***
     * 提交F点
     * 列表性质的提交F点需要提交Position和totalSize
     * @see #addPosition(int)
     * @see #addTotalSize(int)
     */
    public void submitF() {
        HashMap<String, String> params = new HashMap<>();
        String pid = buildParams();
        params.put(StatKey.Parameter.id, mId);
        params.put(StatKey.Parameter.p_action_pos, String.valueOf(mActionPosition));
        params.put(StatKey.Parameter.p_action_total, String.valueOf(mActionTotal));

        if (mPid != null) {
            params.put(StatKey.Parameter.p_id, mPid);
        }
        if (!TextUtils.isEmpty(pid)) {
            params.put(StatKey.Parameter.p_action_id, pid);
        }
        if (mClass == null)throw new ExceptionInInitializerError("mClass == null");
        HxStat.statClick(mClass.getName(), null, params);
    }

    /***
     * 大数据埋点--P点
     * 只用于弹层类似页面，需要自己控制P点上报
     * @param prePage 上一个页面的class name
     */
    public void submitP(String prePage) {
        HashMap<String, String> params = new HashMap<>();
        String pTitle = buildParams();
        params.put(StatKey.Parameter.id, mId);
        if (mPid != null) {
            params.put(StatKey.Parameter.p_id, mPid);
        }
        if (!TextUtils.isEmpty(pTitle)) {
            params.put(StatKey.Parameter.p_title, pTitle);
        }
        if (mClass == null)throw new ExceptionInInitializerError("mClass == null");
        HxStat.reportPvUvDirectly(mClass.getName(), prePage, null, params);
    }

    /***
     * 大数据埋点--I点
     * 曝光数据埋点 （进入屏幕持续一定时间触发） 单位：秒
     * @param view 曝光统计对应的view
     *
     * 列表性质的提交I点需要提交Position和totalSize
     * @see #addPosition(int)
     * @see #addTotalSize(int)
     */
    public void submitI(final View view) {
        if (view == null) return;
//        if (BuildConfig.DEBUG) {
//            view.setBackgroundColor(Color.BLUE);
//        }
        new PointI(view).register(new PointI.OnExposeListener() {
            @Override
            public void onExpose(long time) {
                HashMap<String, String> params = new HashMap<>();
                String actionId = buildParams();
                params.put(StatKey.Parameter.id, mId);
                params.put(StatKey.Parameter.p_action_pos, String.valueOf(mActionPosition));
                params.put(StatKey.Parameter.p_action_total, String.valueOf(mActionTotal));
                params.put(StatKey.Parameter.p_live_time, String.valueOf(time));

                if (mPid != null) {
                    params.put(StatKey.Parameter.p_id, mPid);
                }
                if (!TextUtils.isEmpty(actionId)) {
                    params.put(StatKey.Parameter.p_action_id, actionId);
                }
                if (mClass == null)throw new ExceptionInInitializerError("mClass == null");
                HxStat.statExpo(mClass.getName(), null, params);
            }
        });
    }

    /**
     * 用于主页面Fragment或者Activity内P点传值
     * 重载函数
     *
     * @param params2 重载函数的第二个参数
     * 或者
     */
    public void submitParameter(HashMap<String, String> params2) {
        String pTitle = buildParams();
        if (mPid != null) {
            params2.put(StatKey.Parameter.p_id, mPid);
        }
        if (!TextUtils.isEmpty(pTitle)) {
            params2.put(StatKey.Parameter.p_title, pTitle);
        }
    }

    public BuryingPointUtils addPid(Object pid) {
        this.mPid = String.valueOf(pid);
        return this;
    }

    public BuryingPointUtils addPosition(int position) {
        this.mActionPosition = position;
        return this;
    }

    public BuryingPointUtils addTotalSize(int size) {
        this.mActionTotal = size;
        return this;
    }

    public BuryingPointUtils addSkuid(Object skuid) {
        if (isObjectNull(skuid))
            return this;
        mParams.put(PointKey.Parameter.skuid, String.valueOf(skuid));
        return this;
    }

    public BuryingPointUtils addSpuid(Object spuid) {
        if (isObjectNull(spuid))
            return this;
        mParams.put(PointKey.Parameter.spuid, String.valueOf(spuid));
        return this;
    }

    public BuryingPointUtils addBrandid(Object brandid) {
        if (isObjectNull(brandid))
            return this;
        mParams.put(PointKey.Parameter.brandid, String.valueOf(brandid));
        return this;
    }

    public BuryingPointUtils addContentid(Object contentid) {
        if (isObjectNull(contentid))
            return this;
        mParams.put(PointKey.Parameter.contentid, String.valueOf(contentid));
        return this;
    }

    public BuryingPointUtils addBannerid(Object bannerid) {
        if (isObjectNull(bannerid))
            return this;
        mParams.put(PointKey.Parameter.bannerid, String.valueOf(bannerid));
        return this;
    }

    public BuryingPointUtils addCouponid(Object couponid) {
        if (isObjectNull(couponid))
            return this;
        mParams.put(PointKey.Parameter.couponid, String.valueOf(couponid));
        return this;
    }

    public BuryingPointUtils addTag(Object tag) {
        if (isObjectNull(tag))
            return this;
        if (mParams.containsKey(PointKey.Parameter.tag)) {
            mTagSize++;
            mParams.put(PointKey.Parameter.tag + mTagSize, String.valueOf(tag));
        } else {
            mParams.put(PointKey.Parameter.tag, String.valueOf(tag));
        }
        return this;
    }

    public BuryingPointUtils addSortid(Object sortid) {
        if (isObjectNull(sortid))
            return this;
        mParams.put(PointKey.Parameter.sortid, String.valueOf(sortid));
        return this;
    }

    public BuryingPointUtils addShopid(Object shopid) {
        if (isObjectNull(shopid))
            return this;
        mParams.put(PointKey.Parameter.shopid, String.valueOf(shopid));
        return this;
    }

    public BuryingPointUtils addMallid(Object mallid) {
        if (isObjectNull(mallid))
            return this;
        mParams.put(PointKey.Parameter.mallid, String.valueOf(mallid));
        return this;
    }

    public BuryingPointUtils addCityid(Object cityid) {
        if (isObjectNull(cityid))
            return this;
        mParams.put(PointKey.Parameter.cityid, String.valueOf(cityid));
        return this;
    }

    public BuryingPointUtils addWikiid(Object wikiid) {
        if (isObjectNull(wikiid))
            return this;
        mParams.put(PointKey.Parameter.wikiid, String.valueOf(wikiid));
        return this;
    }

    public BuryingPointUtils addPhone(Object phone) {
        if (isObjectNull(phone))
            return this;
        mParams.put(PointKey.Parameter.phone, String.valueOf(phone));
        return this;
    }

    public BuryingPointUtils addOrderid(Object orderid) {
        if (isObjectNull(orderid))
            return this;
        mParams.put(PointKey.Parameter.orderid, String.valueOf(orderid));
        return this;
    }

    public BuryingPointUtils addCateg_lv1_id(Object categ_lv1_id) {
        if (isObjectNull(categ_lv1_id))
            return this;
        mParams.put(PointKey.Parameter.categ_lv1_id, String.valueOf(categ_lv1_id));
        return this;
    }

    public BuryingPointUtils addCateg_lv2_id(Object categ_lv2_id) {
        if (isObjectNull(categ_lv2_id))
            return this;
        mParams.put(PointKey.Parameter.categ_lv2_id, String.valueOf(categ_lv2_id));
        return this;
    }

    public BuryingPointUtils addCompany_id(Object company_id) {
        if (isObjectNull(company_id))
            return this;
        mParams.put(PointKey.Parameter.company_id, String.valueOf(company_id));
        return this;
    }

    public BuryingPointUtils addDesigner_id(Object designer_id) {
        if (isObjectNull(designer_id))
            return this;
        mParams.put(PointKey.Parameter.designer_id, String.valueOf(designer_id));
        return this;
    }

    public BuryingPointUtils addMessage_type_id(Object message_type_id) {
        if (isObjectNull(message_type_id))
            return this;
        mParams.put(PointKey.Parameter.message_type_id, String.valueOf(message_type_id));
        return this;
    }

    public BuryingPointUtils addUrl(Object url) {
        if (isObjectNull(url))
            return this;
        mParams.put(PointKey.Parameter.url, String.valueOf(url));
        return this;
    }

    public BuryingPointUtils addOpenid(Object openid) {
        if (isObjectNull(openid))
            return this;
        mParams.put(PointKey.Parameter.openid, String.valueOf(openid));
        return this;
    }

    public BuryingPointUtils addKeyword(Object keyword) {
        if (isObjectNull(keyword))
            return this;
        mParams.put(PointKey.Parameter.keyword, String.valueOf(keyword));
        return this;
    }

    private boolean isObjectNull(Object skuid) {
        return skuid == null || (skuid instanceof String && TextUtils.isEmpty((String) skuid));
    }

    private String buildParams() {
        Iterator var11;
        StringBuilder sb = new StringBuilder();
        String key;
        int position = 0;
        for (var11 = mParams.keySet().iterator(); var11.hasNext(); ++position) {
            key = (String) var11.next();
            sb.append(key).append("=").append(mParams.get(key));
            if (position < mParams.size() - 1) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    /**
     * I点辅助类
     */
    private static class PointI {
        private final static long TIME_LIMIT = 500L;//时间超过500ms
        private final static float IN_PERCENT = 0.75F;//view进入超出3/4算进入
        private final static float OUT_PERCENT = 0.25F;//view退出1/4算离开

        private long mStartTime = 0L;
        private long mEndTime = 0L;
        private boolean mExpose = false;
        private boolean mAttached = false;
        private boolean mPaused = false;
        private View mView;
        private OnExposeListener mOnExposeListener;

        public interface OnExposeListener {
            void onExpose(long time);
        }

        PointI(View view) {
            mView = view;
        }

        public void register(OnExposeListener listener) {
            mOnExposeListener = listener;
            final Application application = XFoundation.getApp();
            if (application != null) {
                application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                    }

                    @Override
                    public void onActivityResumed(Activity activity) {
                        if (mView.getContext() == activity) {
                            mPaused = false;
                            checkExpose();
                        }
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {
                        if (mView.getContext() == activity) {
                            mPaused = true;
                            checkExpose();
                        }
                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                        if (mView.getContext() == activity) {
                            application.unregisterActivityLifecycleCallbacks(this);
                        }
                    }
                });
            }

            final ViewTreeObserver.OnScrollChangedListener scrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    checkExpose();
                }
            };

            mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    mAttached = true;
                    checkExpose();
                    ViewTreeObserver observer = mView.getViewTreeObserver();
                    if (observer != null && observer.isAlive()) {
                        observer.addOnScrollChangedListener(scrollChangedListener);
                    }
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    checkExpose();
                    mAttached = false;
                    ViewTreeObserver observer = mView.getViewTreeObserver();
                    if (observer != null && observer.isAlive()) {
                        observer.removeOnScrollChangedListener(scrollChangedListener);
                    }
                }
            });
        }

        /**
         * 曝光逻辑
         */
        private void checkExpose() {
            if (!mAttached) return;
            float percent = onScreenPercent(mView);
            if (mExpose) {
                if (percent < OUT_PERCENT) {//显示比例达到不可见要求，结束计时
                    mExpose = false;
                    mEndTime = System.currentTimeMillis();
                    long time = mEndTime - mStartTime;
                    LogUtil.d("submitI  checkExpose time =" + time);
                    if (time > TIME_LIMIT) {
                        if (mOnExposeListener != null) {
                            mOnExposeListener.onExpose(time);
                        }
                    }
                }
            } else {
                if (percent >= IN_PERCENT) {//显示比例达到可见要求，开始计时
                    mExpose = true;
                    mStartTime = System.currentTimeMillis();
                }
            }
        }

        /**
         * @param view view
         * @return view 在屏幕中占的比例
         */
        private float onScreenPercent(View view) {
            if (view == null || view.getVisibility() != View.VISIBLE || !view.isShown() || mPaused) {
                return 0.0F;
            }

            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            if (width <= 0 || height <= 0) return 0.0F;

            Rect rect = new Rect();
            boolean visibility = view.getGlobalVisibleRect(rect);
            if (!visibility) return 0.0F;
            double area = width * height;
            double areaInside = rect.width() * rect.height();
            return (float) (areaInside / area);
        }
    }
}
