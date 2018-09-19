package com.cody.app.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cody.app.R;
import com.cody.app.business.binding.BannerActivity;
import com.cody.app.business.binding.CaseActivity;
import com.cody.app.business.binding.ConstraintDemoActivity;
import com.cody.app.business.binding.DemoDesignerMainActivity;
import com.cody.app.business.binding.DemoImageViewActivity;
import com.cody.app.business.hybrid.DemoHtmlActivity;
import com.cody.app.business.launch.LauncherPageActivity;
import com.cody.app.framework.activity.BaseActivity;
import com.cody.app.framework.activity.HtmlActivity;

import java.util.ArrayList;
import java.util.List;

public class DemoMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_demo_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.demo_list);

        List<ItemBean> mDemoList = new ArrayList<>();
        mDemoList.add(new ItemBean("Launcher demo", "Launcher", LauncherPageActivity.class));
        mDemoList.add(new ItemBean("设计师主页面", "设计师主页面", DemoDesignerMainActivity.class));
        mDemoList.add(new ItemBean("HTMLActivity调用", "file:///android_asset/hybrid_demo.html", HtmlActivity.class));
        mDemoList.add(new ItemBean("HTMLActivity调用", "http://mp.weixin.qq.com/s/_jdVkQ1Jxx62IojPLpbyHA", HtmlActivity.class));
        mDemoList.add(new ItemBean("DemoHtmlActivity调用", "http://mp.weixin.qq.com/s/_jdVkQ1Jxx62IojPLpbyHA", DemoHtmlActivity.class));
        mDemoList.add(new ItemBean("Binding调用", "演示Binding调用", CaseActivity.class));
        mDemoList.add(new ItemBean("Https调用", "演示Https调用", DemoImageViewActivity.class));
        mDemoList.add(new ItemBean("Banner调用", "演示Banner调用", BannerActivity.class));
        mDemoList.add(new ItemBean("ConstraintLayout调用", "演示ConstraintLayout调用", ConstraintDemoActivity.class));

        recyclerView.setAdapter(new DemoRecycleViewAdapter(this, mDemoList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_back) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DemoRecycleViewAdapter extends RecyclerView.Adapter<DemoViewHolder> {

        private Context mContext;
        private LayoutInflater mInflater;
        private List<ItemBean> mDemoList;

        public DemoRecycleViewAdapter(Context context, List<ItemBean> list) {
            this.mContext = context;
            mInflater = LayoutInflater.from(mContext);
            if (list == null) {
                mDemoList = new ArrayList<>();
            } else {
                mDemoList = list;
            }
        }

        @Override
        public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DemoViewHolder(mInflater.inflate(R.layout.demo_item, parent, false), mContext);
        }

        @Override
        public void onBindViewHolder(DemoViewHolder holder, int position) {
            holder.onBindDemoViewHolder(mDemoList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDemoList.size();
        }
    }

    public static class DemoViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private Button mButton;
        private TextView mTextView;

        public DemoViewHolder(View itemView, Context context) {
            super(itemView);
            mContext = context;
            mButton = (Button) itemView.findViewById(R.id.item_btn);
            mTextView = (TextView) itemView.findViewById(R.id.item_detail);
        }

        public void onBindDemoViewHolder(final ItemBean bean) {
            mButton.setText(bean.mTitle);
            mTextView.setText(bean.mDetail);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.mClazz == HtmlActivity.class) {
                        HtmlActivity.startHtml(bean.mTitle, bean.mDetail);
                    } else {
                        Intent intent = new Intent(mContext, bean.mClazz);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    public static class ItemBean {
        String mTitle;
        String mDetail;
        Class<?> mClazz;

        public ItemBean(String title, String detail, Class<?> clazz) {
            mTitle = title;
            mDetail = detail;
            mClazz = clazz;
        }
    }
}
