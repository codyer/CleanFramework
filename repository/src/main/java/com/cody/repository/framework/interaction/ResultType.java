package com.cody.repository.framework.interaction;

import com.cody.xf.utils.http.SimpleBean;

/**
 * Created by cody.yi on 2017/3/28.
 * 返回数据类型
 *
 * @see SimpleBean
 * SIMPLE： original json from server and the whole json is a simpleBean
 * ORIGINAL： original json from server
 * BEAN： dataMap in original json which type is normal bean
 * LIST_BEAN： dataMap in original json which type is list bean
 * UPLOAD_BEAN： dataMap in original json which type is bean for upload image
 * UPLOAD_LIST_BEAN： dataMap in original json which type is bean for upload images
 */
public enum ResultType {
    SIMPLE,
    ORIGINAL,
    BEAN,
    LIST_BEAN,
    UPLOAD_BEAN,
    UPLOAD_LIST_BEAN;

    private ResultType() {
    }
}
