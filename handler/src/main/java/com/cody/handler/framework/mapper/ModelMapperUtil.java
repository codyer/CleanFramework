package com.cody.handler.framework.mapper;


import com.cody.xf.binding.handler.ModelMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cody.yi on 2016/8/24.
 * 模型装饰，映射,
 * 当获取的数据和ViewModel有差距时需要使用mapper
 */
public class ModelMapperUtil {

    private static ModelMapperUtil sInstance;
    private Map<Class<? extends ModelMapper>, ModelMapper> mapperList = new HashMap<>();

    private ModelMapperUtil() {
    }

    public static synchronized ModelMapperUtil getInstance() {
        if (sInstance == null)
            sInstance = new ModelMapperUtil();
        return sInstance;
    }
}
