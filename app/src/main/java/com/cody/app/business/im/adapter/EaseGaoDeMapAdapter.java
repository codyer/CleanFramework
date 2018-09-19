package com.cody.app.business.im.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.cody.app.R;

/**
 * Created by chen.huarong on 2018/5/11.
 */
public class EaseGaoDeMapAdapter extends ArrayAdapter<PoiItem> {

    private LayoutInflater layoutInflater;
    private int selectPositioin = 0;

    public EaseGaoDeMapAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_location, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PoiItem item = getItem(position);
        if (item != null) {
            holder.mTvName.setText(item.getTitle());
            holder.mTvLocation.setText(getAddress(position));
        }

        if (selectPositioin == position) {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    public void setSelectPositioin(int selectPositioin) {
        this.selectPositioin = selectPositioin;
    }

    public int getSelectPositioin() {
        return selectPositioin;
    }

    public String getAddress(int position) {
        PoiItem item = getItem(position);
        return getAddress(item);
    }

    public String getAddress(PoiItem item) {
        if (item != null) {
            String location = item.getProvinceName();
            if (!TextUtils.isEmpty(location)
                    && !location.equals(item.getCityName())) {//直辖市/特别行政区名称情况，不用再添加城市名称
                location += item.getCityName();
            }
            location += item.getAdName();
            location += item.getSnippet();
            return location;
        }
        return "";
    }

    //
    static class ViewHolder {
        TextView mTvName;
        TextView mTvLocation;
        View mCheckBox;

        public ViewHolder(View view) {
            mTvName = (TextView) view.findViewById(R.id.tvName);
            mTvLocation = (TextView) view.findViewById(R.id.tvLocation);
            mCheckBox = view.findViewById(R.id.checkbox);
        }
    }
}
