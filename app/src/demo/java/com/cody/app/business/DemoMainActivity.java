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
import com.cody.app.business.binding.CaseActivity;
import com.cody.app.business.binding.ImageViewActivity;
import com.cody.app.business.hybrid.HtmlActivity;
import com.cody.app.framework.activity.BaseActivity;

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
        ItemBean item = new ItemBean("Hybrid调用", "演示Hybrid调用", HtmlActivity.class);
        ItemBean item1 = new ItemBean("Binding调用", "演示Binding调用", CaseActivity.class);
        ItemBean item2 = new ItemBean("Https调用", "演示Https调用", ImageViewActivity.class);
        ItemBean item3 = new ItemBean("HTMLActivity调用", "演示HTMLActivity调用", com.cody.app.framework.activity.HtmlActivity.class);
        mDemoList.add(item);
        mDemoList.add(item1);
        mDemoList.add(item2);
        mDemoList.add(item3);

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
                    if (bean.mClazz == com.cody.app.framework.activity.HtmlActivity.class){
                        String url = "http://mp.weixin.qq.com/s/_jdVkQ1Jxx62IojPLpbyHA";
                        com.cody.app.framework.activity.HtmlActivity.startHtml(bean.mTitle,url);
                    }else {
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
