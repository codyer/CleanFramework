package com.cody.xf;

import android.app.Application;
import android.content.Context;

import com.cody.xf.common.NormalException;
import com.cody.xf.interaction.InteractionProxy;
import com.cody.xf.utils.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cody.yi on 2017/3/30.
 * 初始化AppProfile
 */
public class Repository {

    private static Repository mInstance;
    private LocalProfile mProfile;
    private final Map<String, String> mLocalStringCache = new ConcurrentHashMap<>();
    private final Map<String, Integer> mLocalIntegerCache = new ConcurrentHashMap<>();
    private final Map<String, Boolean> mLocalBooleanCache = new ConcurrentHashMap<>();
    private final Map<String, Float> mLocalFloatCache = new ConcurrentHashMap<>();
    private final Map<String, Map<String, String>> mLocalMapCache = new ConcurrentHashMap<>();

    private Repository(LocalProfile profile) {
        mProfile = profile;
    }

    public static void install(Context context) {
        if (mInstance != null) {
            throw new NormalException("please only call install Repository one time.");
        } else if (context != null && context instanceof Application) {
            mInstance = new Repository(new LocalProfile(context.getApplicationContext()));
        } else {
            throw new NormalException("context is invalid when call Repository.install.");
        }
    }

    public static <T> T getInteraction(final Class<T> clazz) {
        return new InteractionProxy().create(clazz);
    }

    public static String getLocalValue(String localKey) {
        String value;
        if (!mInstance.mLocalStringCache.containsKey(localKey)) {
            value = mInstance.mProfile.getValue(localKey, "");
            mInstance.mLocalStringCache.put(localKey, value);
        } else {
            value = mInstance.mLocalStringCache.get(localKey);
        }
        return value;
    }

    public static void setLocalValue(String localKey, String localValue) {
        mInstance.mLocalStringCache.put(localKey, localValue);
        if (StringUtil.isEmpty(localValue)) {
            mInstance.mProfile.remove(localKey);
        } else {
            mInstance.mProfile.setValue(localKey, localValue);
        }
    }

    public static int getLocalInt(String localKey) {
        int value;
        if (!mInstance.mLocalIntegerCache.containsKey(localKey)) {
            value = mInstance.mProfile.getValue(localKey, 0);
            mInstance.mLocalIntegerCache.put(localKey, value);
        } else {
            value = mInstance.mLocalIntegerCache.get(localKey);
        }
        return value;
    }

    public static void setLocalInt(String localKey, int localValue) {
        mInstance.mLocalIntegerCache.put(localKey, localValue);
        if (localValue == 0) {
            mInstance.mProfile.remove(localKey);
        } else {
            mInstance.mProfile.setValue(localKey, localValue);
        }
    }

    public static float getLocalFloat(String localKey) {
        float value;
        if (!mInstance.mLocalFloatCache.containsKey(localKey)) {
            value = mInstance.mProfile.getValue(localKey, 0f);
            mInstance.mLocalFloatCache.put(localKey, value);
        } else {
            value = mInstance.mLocalIntegerCache.get(localKey);
        }
        return value;
    }

    public static void setLocalFloat(String localKey, float localValue) {
        mInstance.mLocalFloatCache.put(localKey, localValue);
        if (localValue == 0f) {
            mInstance.mProfile.remove(localKey);
        } else {
            mInstance.mProfile.setValue(localKey, localValue);
        }
    }

    public static boolean getLocalBoolean(String localKey) {
        boolean value;
        if (!mInstance.mLocalBooleanCache.containsKey(localKey)) {
            value = mInstance.mProfile.getValue(localKey, false);
            mInstance.mLocalBooleanCache.put(localKey, value);
        } else {
            value = mInstance.mLocalBooleanCache.get(localKey);
        }
        return value;
    }

    public static void setLocalBoolean(String localKey, boolean localValue) {
        mInstance.mLocalBooleanCache.put(localKey, localValue);
        if (!localValue) {
            mInstance.mProfile.remove(localKey);
        } else {
            mInstance.mProfile.setValue(localKey, true);
        }
    }

    public static Map<String, String> getLocalMap(String localKey) {
        Map<String, String> value;
        if (!mInstance.mLocalMapCache.containsKey(localKey)) {
            value = mInstance.mProfile.getMap(localKey);
            if (value != null && !value.isEmpty()) {
                mInstance.mLocalMapCache.put(localKey, value);
            }
        } else {
            value = mInstance.mLocalMapCache.get(localKey);
        }
        return value;
    }

    public static void setLocalMap(String localKey, Map<String, String> localMap) {
        mInstance.mLocalMapCache.put(localKey, localMap);
        if (localMap == null) {
            mInstance.mProfile.remove(localKey);
        } else {
            mInstance.mProfile.setMap(localKey, localMap);
        }
    }
}
