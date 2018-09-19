package com.cody.app.business.im.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.cody.app.R;
import com.cody.repository.business.bean.entity.UserGroupBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/22.
 * 修改分组
 */
public class GroupDialogAdapter extends BaseAdapter {
    private Context mContext;
    private List<UserGroupBean> mData;

    public GroupDialogAdapter(Context context, List<UserGroupBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public UserGroupBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group_dialog, parent, false);
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(mData.get(position).getGroupName());
        return convertView;
    }

    public static class ViewHolder {
        CheckedTextView tvName;
    }

}
