package com.cody.xf.widget.popup;

import com.cody.xf.widget.pickerview.adapter.WheelAdapter;

import java.util.List;

public class WheelRegionAdapter implements WheelAdapter {

    private List<String> data;

    public WheelRegionAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getItemsCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int index) {
        return data.get(index);
    }


    @Override
    public int indexOf(Object o) {
        return data.indexOf(o);
    }
}