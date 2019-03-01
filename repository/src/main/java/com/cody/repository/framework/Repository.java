package com.cody.repository.framework;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.cody.repository.framework.database.realm.RealmDatabase;
import com.cody.repository.framework.interaction.CallAdapter;
import com.cody.repository.framework.interaction.InteractionProxy;
import com.cody.repository.framework.local.BaseLocalKey;
import com.cody.repository.framework.local.LocalProfile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.realm.Realm;

/**
 * Created by cody.yi on 2017/3/30.
 * 数据仓库，所有数据通过此类进行管理
 */
public class Repository {
    private static Repository sRepository;
    private final Map<String, String> mLocalStringCache = new ConcurrentHashMap<>();
    private final Map<String, Integer> mLocalIntegerCache = new ConcurrentHashMap<>();
    private final Map<String, Boolean> mLocalBooleanCache = new ConcurrentHashMap<>();
    private final Map<String, Float> mLocalFloatCache = new ConcurrentHashMap<>();
    private final Map<String, Map<String, String>> mLocalMapCache = new ConcurrentHashMap<>();
    private final Map<String, List<String>> mLocalListCache = new ConcurrentHashMap<>();
    private LocalProfile mProfile;
    private CallAdapter mCallAdapter;

    private Repository(LocalProfile profile) {
        mProfile = profile;
    }

    private static Repository getRepository() {
        if (sRepository == null) {
            throw new NullPointerException("you should call Repository.install(context) in you Application first.");
        } else {
            return sRepository;
        }
    }

    /**
     * 定义自己的CallAdapter
     */
    public static void installWithCallAdapter(Context context, CallAdapter callAdapter) {
        install(context);
        getRepository().mCallAdapter = callAdapter;
    }

    public static void install(Context context) {
        if (sRepository != null) {
            throw new NullPointerException("please only call install Repository one time.");
        } else if (context != null && context instanceof Application) {
            sRepository = new Repository(new LocalProfile(context.getApplicationContext()));
            RealmDatabase.install(context.getApplicationContext());
        } else {
            throw new NullPointerException("context is invalid when call Repository.install.");
        }
    }

    public static Realm getDataBase() {
        String name = Repository.getLocalValue(BaseLocalKey.OPEN_ID);
        return RealmDatabase.getDatabase(name);
    }

    public static <T> T getInteraction(final Class<T> clazz) {
        return new InteractionProxy().setCallAdapter(getRepository().mCallAdapter).create(clazz);
    }

    public static String getLocalValue(String localKey) {
        String value;
        checkKey(localKey);
        if (!getRepository().mLocalStringCache.containsKey(localKey)) {
            value = getRepository().mProfile.getValue(localKey, "");
            getRepository().mLocalStringCache.put(localKey, value);
        } else {
            value = getRepository().mLocalStringCache.get(localKey);
        }
        return value;
    }

    public static void setLocalValue(String localKey, String localValue) {
        checkKey(localKey);
        if (TextUtils.isEmpty(localValue)) {
            getRepository().mProfile.remove(localKey);
            if (getRepository().mLocalStringCache.containsKey(localKey)) {
                getRepository().mLocalStringCache.remove(localKey);
            }
        } else {
            getRepository().mProfile.setValue(localKey, localValue);
            getRepository().mLocalStringCache.put(localKey, localValue);
        }
    }

    public static int getLocalInt(String localKey) {
        int value;
        checkKey(localKey);
        if (!getRepository().mLocalIntegerCache.containsKey(localKey)) {
            value = getRepository().mProfile.getValue(localKey, 0);
            getRepository().mLocalIntegerCache.put(localKey, value);
        } else {
            value = getRepository().mLocalIntegerCache.get(localKey);
        }
        return value;
    }

    public static void setLocalInt(String localKey, int localValue) {
        checkKey(localKey);
        if (localValue == 0) {
            getRepository().mProfile.remove(localKey);
            if (getRepository().mLocalIntegerCache.containsKey(localKey)) {
                getRepository().mLocalIntegerCache.remove(localKey);
            }
        } else {
            getRepository().mProfile.setValue(localKey, localValue);
            getRepository().mLocalIntegerCache.put(localKey, localValue);
        }
    }

    public static float getLocalFloat(String localKey) {
        float value;
        checkKey(localKey);
        if (!getRepository().mLocalFloatCache.containsKey(localKey)) {
            value = getRepository().mProfile.getValue(localKey, 0f);
            getRepository().mLocalFloatCache.put(localKey, value);
        } else {
            value = getRepository().mLocalIntegerCache.get(localKey);
        }
        return value;
    }

    public static void setLocalFloat(String localKey, float localValue) {
        checkKey(localKey);
        if (localValue == 0f) {
            getRepository().mProfile.remove(localKey);
            if (getRepository().mLocalFloatCache.containsKey(localKey)) {
                getRepository().mLocalFloatCache.remove(localKey);
            }
        } else {
            getRepository().mProfile.setValue(localKey, localValue);
            getRepository().mLocalFloatCache.put(localKey, localValue);
        }
    }

    public static boolean getLocalBoolean(String localKey) {
        boolean value;
        checkKey(localKey);
        if (!getRepository().mLocalBooleanCache.containsKey(localKey)) {
            value = getRepository().mProfile.getValue(localKey, false);
            getRepository().mLocalBooleanCache.put(localKey, value);
        } else {
            value = getRepository().mLocalBooleanCache.get(localKey);
        }
        return value;
    }

    public static boolean getLocalBoolean(String localKey, boolean defaultValue) {
        boolean value;
        checkKey(localKey);
        if (!getRepository().mLocalBooleanCache.containsKey(localKey)) {
            value = getRepository().mProfile.getValue(localKey, defaultValue);
            getRepository().mLocalBooleanCache.put(localKey, value);
        } else {
            value = getRepository().mLocalBooleanCache.get(localKey);
        }
        return value;
    }

    public static void setLocalBoolean(String localKey, boolean localValue) {
        checkKey(localKey);
        if (!localValue) {
            getRepository().mProfile.remove(localKey);
            if (getRepository().mLocalBooleanCache.containsKey(localKey)) {
                getRepository().mLocalBooleanCache.remove(localKey);
            }
        } else {
            getRepository().mProfile.setValue(localKey, true);
            getRepository().mLocalBooleanCache.put(localKey, true);
        }
    }

    public static Map<String, String> getLocalMap(String localKey) {
        Map<String, String> value;
        checkKey(localKey);
        if (!getRepository().mLocalMapCache.containsKey(localKey)) {
            value = getRepository().mProfile.getMap(localKey);
            if (value != null && !value.isEmpty()) {
                getRepository().mLocalMapCache.put(localKey, value);
            }
        } else {
            value = getRepository().mLocalMapCache.get(localKey);
        }
        return value;
    }

    public static void setLocalMap(String localKey, Map<String, String> localMap) {
        checkKey(localKey);
        if (localMap == null) {
            if (getRepository().mLocalMapCache.containsKey(localKey)) {
                getRepository().mLocalMapCache.remove(localKey);
            }
            getRepository().mProfile.remove(localKey);
        } else {
            getRepository().mLocalMapCache.put(localKey, localMap);
            getRepository().mProfile.setMap(localKey, localMap);
        }
    }

    public static <T> void setViewModel(String localKey, T viewModel) {
        checkKey(localKey);
        getRepository().mProfile.setViewModel(localKey, viewModel);
    }

    public static <T> T getViewModel(String localKey, Class viewModel) {
        checkKey(localKey);
        return getRepository().mProfile.getViewModel(localKey, viewModel);
    }

    public static List<String> getLocalList(String localKey) {
        List<String> value;
        checkKey(localKey);
        if (!getRepository().mLocalListCache.containsKey(localKey)) {
            value = getRepository().mProfile.getList(localKey);
            if (value != null && !value.isEmpty()) {
                getRepository().mLocalListCache.put(localKey, value);
            }
        } else {
            value = getRepository().mLocalListCache.get(localKey);
        }
        return value;
    }

    public static void setLocalList(String localKey, List<String> localList) {
        checkKey(localKey);
        if (localList == null) {
            if (getRepository().mLocalListCache.containsKey(localKey)) {
                getRepository().mLocalListCache.remove(localKey);
            }
            getRepository().mProfile.remove(localKey);
        } else {
            getRepository().mLocalListCache.put(localKey, localList);
            getRepository().mProfile.setList(localKey, localList);
        }
    }

    private static void checkKey(String localKey) {
        if (localKey == null) {
            throw new NullPointerException("localKey should not be null.");
        }
    }
}
