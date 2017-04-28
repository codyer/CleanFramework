package com.cody.repository.framework.interaction;

import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.http.HeaderListener;
import com.google.gson.JsonObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cody.yi on 2017/3/28.
 * Interaction 方法类
 */
class InteractionMethod {
    private final Object mTag;
    private final Map<String, String> mParams;
    private final JsonObject mJsonObject;
    private final Class<?> mClazz;
    private final HeaderListener mHeaderListener;
    private final ICallback<Object> mCallback;
    private final String mDomain;
    private final String mUrl;
    private final RequestMethod mRequestMethod;

    private final ResultType mResultType;
    private final CallAdapter mCallAdapter;

    private InteractionMethod(Builder builder) {
        this.mTag = builder.mTag;
        this.mParams = builder.mParams;
        this.mJsonObject = builder.mJsonObject;
        this.mClazz = builder.mClazz;
        this.mHeaderListener = builder.mHeaderListener;
        this.mCallback = builder.mCallback;
        this.mDomain = builder.mDomain;
        this.mUrl = builder.mUrl;
        this.mRequestMethod = builder.mRequestMethod;
        this.mResultType = builder.mResultType;

        if (builder.mCallAdapter != null) {
            this.mCallAdapter = builder.mCallAdapter;
        } else {
            this.mCallAdapter = new DefaultCallAdapter(this);
        }
    }

    Object invoke() {
        switch (mResultType) {
            case SIMPLE:
                mCallAdapter.invokeSimple();
                break;
            case ORIGINAL:
                mCallAdapter.invokeOriginal();
                break;
            case BEAN:
                mCallAdapter.invokeBean();
                break;
            case LIST_BEAN:
                mCallAdapter.invokeListBean();
                break;
        }
        return null;
    }

    static final class Builder {
        private Object mTag;
        private String mUrl;
        private Class<?> mClazz;
        private ResultType mResultType;
        private HeaderListener mHeaderListener;
        private ICallback<Object> mCallback;
        private JsonObject mJsonObject;
        private Map<String, String> mParams;
        private RequestMethod mRequestMethod;
        private CallAdapter mCallAdapter;

        private final Method mMethod;
        private final String mDomain;
        private final Annotation[] mMethodAnnotations;
        private final Annotation[][] mParameterAnnotationsArray;

        Builder(Method method, String domain) {
            this.mDomain = domain;
            this.mMethod = method;
            this.mMethodAnnotations = method.getAnnotations();
            this.mParameterAnnotationsArray = method.getParameterAnnotations();
        }

        Builder setCallAdapter(CallAdapter callAdapter) {
            mCallAdapter = callAdapter;
            return this;
        }

        public InteractionMethod build(Object... args) {
            for (Annotation annotation : mMethodAnnotations) {
                parseMethodAnnotation(annotation);
            }

            int parameterCount = mParameterAnnotationsArray.length;
            for (int i = 0; i < parameterCount; i++) {
                Annotation[] parameterAnnotations = mParameterAnnotationsArray[i];
                parseParameterAnnotation(i, parameterAnnotations, args);
            }

            return new InteractionMethod(this);
        }

        @SuppressWarnings("unchecked")
        private void parseParameterAnnotation(int i, Annotation[] parameterAnnotations, Object[] args) {
            if (parameterAnnotations != null) {
                for (Annotation methodParameterAnnotation : parameterAnnotations) {
                    if (methodParameterAnnotation instanceof QueryTag) {
                        mTag = args[i];
                    } else if (methodParameterAnnotation instanceof QueryMap) {
                        if (args[i] instanceof Map) {
                            mParams = (Map<String, String>) args[i];
                        } else {
                            throw parameterError(i, "parseParameterAnnotation", "err", "invalid QueryMap params !");
                        }
                    } else if (methodParameterAnnotation instanceof QueryJson) {
                        if (args[i] instanceof JsonObject) {
                            mJsonObject = (JsonObject) args[i];
                        } else {
                            throw parameterError(i, "parseParameterAnnotation", "err", "invalid QueryJson params !");
                        }
                    } else if (methodParameterAnnotation instanceof QueryClass) {
                        if (args[i] instanceof Class) {
                            mClazz = (Class<?>) args[i];
                        } else {
                            throw parameterError(i, "parseParameterAnnotation", "err", "invalid QueryClass params !");
                        }
                    } else if (methodParameterAnnotation instanceof QueryCallBack) {
                        if (args[i] instanceof ICallback) {
                            mCallback = (ICallback<Object>) args[i];
                        } else if (args[i] instanceof HeaderListener) {
                            mHeaderListener = (HeaderListener) args[i];
                        } else {
                            throw parameterError(i, "parseParameterAnnotation", "err", "invalid QueryCallBack params !");
                        }
                    } else if (methodParameterAnnotation instanceof QueryHeaderListener) {
                        if (args[i] instanceof HeaderListener) {
                            mHeaderListener = (HeaderListener) args[i];
                        } else {
                            throw parameterError(i, "parseParameterAnnotation", "err", "you should use HeaderListener !");
                        }
                    } else if (methodParameterAnnotation instanceof QueryString) {
                        if (mParams == null) {
                            mParams = new HashMap<>();
                        }
                        QueryString queryString = (QueryString) methodParameterAnnotation;

                        if (args[i] instanceof String) {
                            mParams.put(queryString.value(), (String) args[i]);
                        } else {
                            mParams.put(queryString.value(), String.valueOf(args[i]));
                        }
                    } else {
                        LogUtil.d("InteractionMethod", "parseParameterAnnotation args[" + (i + 1) + "] has an annotation " + methodParameterAnnotation);
                    }
                }
            }
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof RequestMapping) {
                RequestMapping requestMapping = (RequestMapping) annotation;
                mUrl = requestMapping.value();
                mRequestMethod = requestMapping.method();
                mResultType = requestMapping.type();
            } else {
                throw methodError("parseMethodAnnotation", "err", "you should use RequestMapping !");
            }
        }

        private RuntimeException methodError(String message, Object... args) {
            return methodError(null, message, args);
        }

        private RuntimeException methodError(Throwable cause, String message, Object... args) {
            message = String.format(message, args);
            return new IllegalArgumentException(message
                    + "\n    for method "
                    + mMethod.getDeclaringClass().getSimpleName()
                    + "."
                    + mMethod.getName(), cause);
        }

        private RuntimeException parameterError(Throwable cause, int p, String message, Object... args) {
            return methodError(cause, message + " (parameter #" + (p + 1) + ")", args);
        }

        private RuntimeException parameterError(int p, String message, Object... args) {
            return parameterError(null, p, message, args);
        }
    }

    public Object getTag() {
        return mTag;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public JsonObject getJsonObject() {
        return mJsonObject;
    }

    Class<?> getClazz() {
        return mClazz;
    }

    public ICallback<Object> getCallback() {
        return mCallback;
    }

    String getDomain() {
        return mDomain;
    }

    String getUrl() {
        return mUrl;
    }

    RequestMethod getRequestMethod() {
        return mRequestMethod;
    }

    HeaderListener getHeaderListener() {
        return mHeaderListener;
    }
}
