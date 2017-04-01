package com.cody.repository.framework.interaction;

/**
 * Created by cody.yi on 2017/3/29.
 * 可以根据具体项目需求实现自己的CallAdapter
 */
public abstract class CallAdapter {
    public final InteractionMethod mMethod;

    public CallAdapter(InteractionMethod interactionMethod) {
        mMethod = interactionMethod;
    }

    /**
     * 返回服务器定义的原始对象，且原始对象为simpleBean
     * @see com.cody.xf.common.SimpleBean
     */
    abstract void invokeSimple();

    /**
     * 返回服务器定义的原始对象
     */
    abstract void invokeOriginal();

    /**
     * 返回Result里面的DataMap对象，且DataMap为JsonObject
     */
    abstract void invokeBean();

    /**
     * 返回Result里面的DataMap对象List，且DataMap为JsonArray
     */
    abstract void invokeListBean();
}
