package com.cody.repository.business.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuliwei on 2018-09-03.
 * 发券失败bean
 */

public class CouponFailBean {
    private boolean hasNextPage;
    private List<String> list = new ArrayList<>();

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
