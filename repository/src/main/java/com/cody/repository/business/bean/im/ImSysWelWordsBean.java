package com.cody.repository.business.bean.im;

import java.util.List;

/**
 * Created by  qiaoping.xiao on 2017/10/11.
 */

public class ImSysWelWordsBean {

    public int type;

    public String sysWordsStr;                              ///< 系统欢迎语文本消息
    public List<SysGreetingWordsGoodsBean> sysGoodsList;    ///系统欢迎语推荐商品列表
    public String extraStr1;                                ///扩展字段1
    public String extraStr2;                                ///扩展字段2

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSysWordsStr() {
        return sysWordsStr;
    }

    public void setSysWordsStr(String sysWordsStr) {
        this.sysWordsStr = sysWordsStr;
    }

    public List<SysGreetingWordsGoodsBean> getSysGoodsList() {
        return sysGoodsList;
    }

    public void setSysGoodsList(List<SysGreetingWordsGoodsBean> sysGoodsList) {
        this.sysGoodsList = sysGoodsList;
    }

    public String getExtraStr1() {
        return extraStr1;
    }

    public void setExtraStr1(String extraStr1) {
        this.extraStr1 = extraStr1;
    }

    public String getExtraStr2() {
        return extraStr2;
    }

    public void setExtraStr2(String extraStr2) {
        this.extraStr2 = extraStr2;
    }
}
