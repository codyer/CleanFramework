package com.cody.app.business.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cody.app.R;
import com.cody.app.business.QuickReplyActivity;
import com.cody.app.business.SystemGreetingActivity;
import com.cody.app.business.easeui.adapter.PopQuickReplyAdapter;
import com.cody.app.business.easeui.adapter.PopSysWelcomeAdapter;
import com.cody.app.business.easeui.adapter.VpAdapter;
import com.cody.handler.business.presenter.QuickReplyPresenter;
import com.cody.handler.framework.DefaultCallback;
import com.cody.repository.business.bean.im.ReplyInfoBean;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.DeviceUtil;
import com.cody.xf.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by  qiaoping.xiao on 2017/9/14.
 */

public class QuickReplyPopupWindow extends PopupWindow {

    private static final String TAG = QuickReplyPopupWindow.class.getName();
    private String targetUser;
    private Context context;
    private PopSysWelcomeAdapter popSysWelcomeAdapter;
    private PopQuickReplyAdapter popQuickReplyAdapter;
    private LinearLayout sysEmptyView;
    private LinearLayout quickEmptyView;
    private RecyclerView rvSysWelcome;
    private RecyclerView rvQuickReply;
    private View sysWelcome;
    private View quickReply;
    private TextView sysTvEmpty;
    private TextView quickTvEmpty;
    private boolean needRefresh;
    private QuickReplyPresenter mPresenter;

    public QuickReplyPopupWindow(Context context, String targetUser) {
        super(context);
        this.context = context;
        this.targetUser = targetUser;
        needRefresh = false;
        mPresenter = new QuickReplyPresenter();
        init();
    }

    private void init() {
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_quick_reply, null);

        LinearLayout llPop = (LinearLayout) popupView.findViewById(R.id.ll_pop);
        ViewPager vp = (ViewPager) popupView.findViewById(R.id.viewpager);
        TabLayout tl = (TabLayout) popupView.findViewById(R.id.tabLayout);

        this.setContentView(popupView);// 设置View
        this.setWidth(ViewPager.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewPager.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        setBackgroundAlpha(0.5f);
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画

        this.setBackgroundDrawable(new BitmapDrawable());// 设置灰度背景

        List<View> views = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        sysWelcome = LayoutInflater.from(context).inflate(R.layout.view_sys_welcome, null);
        quickReply = LayoutInflater.from(context).inflate(R.layout.view_quick_reply, null);
        initView(sysWelcome, quickReply);
        views.add(sysWelcome);
        titles.add("系统欢迎语");
        views.add(quickReply);
        titles.add("快捷回复语");
        vp.setAdapter(new VpAdapter(views, titles));
        tl.setupWithViewPager(vp);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = (int) (DeviceUtil.getScreenHeight() - DeviceUtil.dip2px(context, 300));
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        setBackgroundAlpha(1.0f);
                        dismiss();
                    }
                }
                return true;
            }
        });

        this.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });

    }

    public void updateMessage() {
        initView(sysWelcome, quickReply);
    }

    public boolean isNeedRefresh() {
        return needRefresh;
    }

    private void initView(View sysWelcome, View quickReply) {
        ReplyInfoBean replyInfoBean = JsonUtil.fromJson(Repository.getLocalValue(LocalKey.REPLY_INFO)
                , ReplyInfoBean.class);
        //系统欢迎语
        initSysView(sysWelcome, replyInfoBean != null ? replyInfoBean.getGreetingReplyVoList() : null);
        //快捷回复语
        initQuickView(quickReply, replyInfoBean != null ? replyInfoBean.getQuickReplyVoList() : null);
        if (replyInfoBean == null
                || replyInfoBean.empty()) {
            mPresenter.getReplyInfo(TAG, new DefaultCallback<ReplyInfoBean>(mPresenter) {
                @Override
                public void onSuccess(ReplyInfoBean data) {
                    super.onSuccess(data);
                    if (data != null) {
                        if (data.getGreetingReplyVoList().size() > 0) {
                            sysEmptyView.setVisibility(GONE);
                            rvSysWelcome.setVisibility(View.VISIBLE);
                        }
                        if (data.getQuickReplyVoList().size() > 0) {
                            boolean isShowEmptyView = true;
                            for (ReplyInfoBean.QuickReplyVoListBean bean : data.getQuickReplyVoList()) {
                                if (bean.getEnable() == 1) {
                                    isShowEmptyView = false;
                                }
                            }
                            if (isShowEmptyView) {
                                quickEmptyView.setVisibility(View.VISIBLE);
                                rvQuickReply.setVisibility(View.GONE);
                            } else {
                                quickEmptyView.setVisibility(GONE);
                                rvQuickReply.setVisibility(View.VISIBLE);
                            }
                        }
                        if (popSysWelcomeAdapter != null)
                            popSysWelcomeAdapter.updateDatas(data.getGreetingReplyVoList());
                        if (popQuickReplyAdapter != null)
                            popQuickReplyAdapter.updateDatas(data.getQuickReplyVoList());
                    }
                }
            });
        } else {
            if (popSysWelcomeAdapter != null)
                popSysWelcomeAdapter.updateDatas(replyInfoBean.getGreetingReplyVoList());
            if (popQuickReplyAdapter != null)
                popQuickReplyAdapter.updateDatas(replyInfoBean.getQuickReplyVoList());
        }
    }

    private void setEmptyTextViewColor(TextView quickTvEmpty, String contentStr) {
        SpannableStringBuilder builder = new SpannableStringBuilder(contentStr);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.main_blue));
        builder.setSpan(redSpan, 0, contentStr.length() - 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(redSpan, contentStr.length() - 7, contentStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        quickTvEmpty.setText(builder);
    }

    private void initSysView(View sysWelcome, final List<ReplyInfoBean.GreetingReplyVoListBean> greetingReplyVoList) {
        if (sysEmptyView == null)
            sysEmptyView = sysWelcome.findViewById(R.id.empty_view);
        if (sysTvEmpty == null)
            sysTvEmpty = sysWelcome.findViewById(R.id.tv_empty);
        if (rvSysWelcome == null)
            rvSysWelcome = sysWelcome.findViewById(R.id.rv_sys_welcome);
        setEmptyTextViewColor(sysTvEmpty, context.getResources().getString(R.string.create_sys_welcome_words));
        sysTvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(context, "创建系统欢迎语", Toast.LENGTH_SHORT).show();
                if (greetingReplyVoList != null && greetingReplyVoList.size() > 0) {
                    SystemGreetingActivity.startSystemGreeting(greetingReplyVoList.get(0), 0);
                }else {
                    SystemGreetingActivity.startSystemGreeting(new ReplyInfoBean.GreetingReplyVoListBean(), 0);
                }
                dismiss();
            }
        });
        List<ReplyInfoBean.GreetingReplyVoListBean> arrayList = new ArrayList<>();
        if (greetingReplyVoList == null || greetingReplyVoList.size() <= 0) {
            sysEmptyView.setVisibility(View.VISIBLE);
            rvSysWelcome.setVisibility(GONE);
        } else {
            if (greetingReplyVoList.get(0).getEnable() != 1) {//系统欢迎语关闭
                rvSysWelcome.setVisibility(View.GONE);
                sysEmptyView.setVisibility(View.VISIBLE);
                sysTvEmpty.setVisibility(View.VISIBLE);
                return;
            }
            rvSysWelcome.setVisibility(View.VISIBLE);
            sysEmptyView.setVisibility(View.GONE);
            sysTvEmpty.setVisibility(View.GONE);
            arrayList = greetingReplyVoList;
        }
        if (popSysWelcomeAdapter == null) {
            popSysWelcomeAdapter = new PopSysWelcomeAdapter(context, new ArrayList<ReplyInfoBean
                    .GreetingReplyVoListBean>(), targetUser, new PopItemEditCallBack<ReplyInfoBean
                    .GreetingReplyVoListBean>() {
                @Override
                public void goToOtherActivity(ReplyInfoBean.GreetingReplyVoListBean dataBean) {
                    SystemGreetingActivity.startSystemGreeting(dataBean,0);
                    dismiss();
                }

                @Override
                public void onMessageSend() {
                    needRefresh = true;
                    dismiss();
                }
            });
            rvSysWelcome.setLayoutManager(new LinearLayoutManager(context));
            rvSysWelcome.setAdapter(popSysWelcomeAdapter);
        }
        popSysWelcomeAdapter.updateDatas(arrayList);
    }

    private void initQuickView(View quickReply, List<ReplyInfoBean.QuickReplyVoListBean> quickReplyVoList) {
        if (quickEmptyView == null)
            quickEmptyView = (LinearLayout) quickReply.findViewById(R.id.empty_view);
        if (quickTvEmpty == null)
            quickTvEmpty = (TextView) quickReply.findViewById(R.id.tv_quick_reply);
        if (rvQuickReply == null)
            rvQuickReply = (RecyclerView) quickReply.findViewById(R.id.rv_quick_reply);
        quickTvEmpty.setText(context.getResources().getString(R.string.create_quick_reply_words));

        List<ReplyInfoBean.QuickReplyVoListBean> arrayList = new ArrayList<>();
        if (quickReplyVoList == null || quickReplyVoList.size() <= 0) {
            quickEmptyView.setVisibility(View.VISIBLE);
            rvQuickReply.setVisibility(GONE);
        } else {
            boolean isShowEmptyView = true;
            for (ReplyInfoBean.QuickReplyVoListBean bean : quickReplyVoList) {
                if (bean.getEnable() == 1) {
                    isShowEmptyView = false;
                }
            }
            if (isShowEmptyView) {
                rvQuickReply.setVisibility(View.GONE);
                quickEmptyView.setVisibility(View.VISIBLE);
            } else {
                rvQuickReply.setVisibility(View.VISIBLE);
                quickEmptyView.setVisibility(GONE);
            }
            arrayList = quickReplyVoList;
        }
        if (popQuickReplyAdapter == null) {
            popQuickReplyAdapter = new PopQuickReplyAdapter(context, new ArrayList<ReplyInfoBean
                    .QuickReplyVoListBean>(), targetUser, new PopItemEditCallBack<ReplyInfoBean.QuickReplyVoListBean>
                    () {
                @Override
                public void goToOtherActivity(ReplyInfoBean.QuickReplyVoListBean bean) {
                    QuickReplyActivity.startQuickReply(bean, 0);
                    dismiss();
                }

                @Override
                public void onMessageSend() {
                    needRefresh = true;
                    dismiss();
                }
            });
            rvQuickReply.setLayoutManager(new LinearLayoutManager(context));
            rvQuickReply.setAdapter(popQuickReplyAdapter);
        }
        popQuickReplyAdapter.updateDatas(arrayList);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }

}
