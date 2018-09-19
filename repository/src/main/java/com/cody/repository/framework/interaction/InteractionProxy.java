package com.cody.repository.framework.interaction;

import com.cody.xf.utils.http.IHeaderListener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by cody.yi on 2017/3/28.
 * <p>
 * 使用动态代理生成Interaction实现类
 * 约定接口的第一个参数是Tag参数
 * 接口的最后一个参数是Callback参数
 * 所有的参数都需要用注解标识，只接收指定类型参数
 * 具体类型如下
 *
 * @see QueryTag 用于取消请求的标识
 * @see QueryMap 请求参数 类型 Map<String,String>
 * @see QueryString 请求参数 类型 String
 * @see QueryJson 请求参数 类型 com.google.gson.JsonObject
 * @see QueryHeaderListener 回调函数类型
 * @see IHeaderListener
 * @see QueryClass 返回bean的类类型 Class<T>
 * @see QueryCallBack 回调函数类型
 * @see ICallback <>
 */
public class InteractionProxy {
    // TODO 各种异常检测需要添加
    private String mDomain;
    private CallAdapter mCallAdapter;

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> interaction) {

        Server server = interaction.getAnnotation(Server.class);
        mDomain = server.value();

        return (T) Proxy.newProxyInstance(
                interaction.getClassLoader(),
                new Class<?>[]{interaction},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object... args) throws Throwable {

                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }

                        InteractionMethod interactionMethod = loadInteractionMethod(method, args);
                        return interactionMethod.invoke();
                    }
                });
    }

    public InteractionProxy setCallAdapter(CallAdapter callAdapter) {
        mCallAdapter = callAdapter;
        return this;
    }

    private InteractionMethod loadInteractionMethod(Method method, Object... args) {
        return new InteractionMethod
                .Builder(method, mDomain)
                .setCallAdapter(mCallAdapter)
                .build(args);
    }
}
