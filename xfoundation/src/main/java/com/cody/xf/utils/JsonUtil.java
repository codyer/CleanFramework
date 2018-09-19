package com.cody.xf.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

/**
 * Created by shijia on 2017/7/20.
 */

public class JsonUtil {
    private static final Gson gson = new Gson();

    /**
     * 转成Json字符串
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * 转成
     */
    public static JsonObject toJsonObject(Object object) {
        String jsonStr = gson.toJson(object);
        return new JsonParser().parse(jsonStr).getAsJsonObject();
    }

    /**
     * Json转对象
     */
    public static <T> T fromJson(String json, Type type) {
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Json转对象
     */
    public static <T> T fromJson(String json, Class<T> clz) {
        try {
            return gson.fromJson(json, clz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
