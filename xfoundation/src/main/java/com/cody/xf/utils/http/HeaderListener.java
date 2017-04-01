/*
 * Copyright (c)  Created by Cody.yi on 2016/9/10.
 */

package com.cody.xf.utils.http;

/**
 * @author codyer .
 * @description Callback interface for delivering header responses.
 * @date 16/9/10.
 */

import java.util.Map;

/**
 * Callback interface for delivering header responses.
 */
public interface HeaderListener {
    /**
     * Called when a response header is received.
     */
    void onHeaderResponse(Map<String, String> header);
}
