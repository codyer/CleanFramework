package com.cody.repository.business.database;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.interaction.LogImInteraction;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.xf.utils.http.SimpleBean;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by cody.yi on 2018/7/20.
 * 用户数据库
 */
public class UserInfoManager {

    public static void setImId(String imId) {
        Repository.setLocalValue(LocalKey.IM_ID, imId);
    }

    public static String getImId() {
        return Repository.getLocalValue(LocalKey.IM_ID);
    }

    /**
     * 保存用户信息到数据库，无分组的才会调用
     */
    public static void saveUserInfo(final UserInfoBean user) {
        final Realm realm = Repository.getDataBase();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (user != null) {
                    realm.copyToRealmOrUpdate(user);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (!realm.isClosed()) {
                    realm.close();
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (!realm.isClosed()) {
                    realm.close();
                }
            }
        });
    }

    /**
     * 保存用户信息到数据库，无分组的才会调用
     */
    public static void saveUserInfo(final List<UserInfoBean> users) {
        final Realm realm = Repository.getDataBase();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (users != null) {
                    realm.copyToRealmOrUpdate(users);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (!realm.isClosed()) {
                    realm.close();
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (!realm.isClosed()) {
                    realm.close();
                }
            }
        });
    }

    /**
     * 通过关键字搜索用户 可以监听变化
     */
    public static DataChangeListener getUserByNameKeyWord(String keyWord, final DataCallBack<List<UserInfoBean>>
            callback) {
        final Realm realm = Repository.getDataBase();
        final RealmChangeListener<RealmResults<UserInfoBean>> listener;
        final RealmResults<UserInfoBean> users = realm.where(UserInfoBean.class)
                .contains("remark", keyWord).or()
                .contains("nickName", keyWord).or()
                .contains("name", keyWord).or()
                .contains("userName", keyWord).findAllAsync();
        users.addChangeListener(listener = new RealmChangeListener<RealmResults<UserInfoBean>>() {
            @Override
            public void onChange(RealmResults<UserInfoBean> users) {
                if (users.isLoaded() && callback != null && users.isValid() && users.isManaged()) {
                    callback.onSuccess(realm.copyFromRealm(users));
                }
            }
        });
        return new DataChangeListener() {
            @Override
            public void cancel() {
                users.removeChangeListener(listener);
                if (!realm.isClosed()) {
                    realm.close();
                }
            }
        };
    }

    /**
     * 通过关键字搜索用户 可以监听变化
     *
     * @param tag      网络请求
     * @param once     是否只要一次，不做监听
     * @param sync     是否要从服务器同步数据
     * @param imId     im id
     * @param callback 回调
     * @return 监听引用
     */
    public static DataChangeListener getUserInfo(final Object tag, boolean once, boolean sync, final String imId, final DataCallBack<UserInfoBean>
            callback) {
        if (sync && tag != null) {
            //从网络获取
            getUserInfoFromServer(tag, imId, callback);
        }

        final Realm realm = Repository.getDataBase();
        if (once) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    UserInfoBean user = realm.where(UserInfoBean.class).equalTo("imId", imId).findFirst();
                    if (user != null && user.isValid() && user.isManaged()) {
                        callback.onSuccess(realm.copyFromRealm(user));

                    } else {//从网络获取
                        getUserInfoFromServer(tag, imId, callback);
                    }
                }
            });
            if (!realm.isClosed()) {
                realm.close();
            }
            return null;
        } else {
            final RealmChangeListener<UserInfoBean> listener;
            final UserInfoBean user = realm.where(UserInfoBean.class).equalTo("imId", imId).findFirstAsync();
            user.addChangeListener(listener = new RealmChangeListener<UserInfoBean>() {
                @Override
                public void onChange(UserInfoBean user) {
                    if (user.isLoaded() && callback != null && user.isValid() && user.isManaged()) {
                        callback.onSuccess(realm.copyFromRealm(user));
                    }
                }
            });
            return new DataChangeListener() {
                @Override
                public void cancel() {
                    user.removeChangeListener(listener);
                    if (!realm.isClosed()) {
                        realm.close();
                    }
                }
            };
        }
    }

    /**
     * 同步从数据库取，数据库中没有取网络的，同步执行。数据变化了不会再通知
     */
    public static void getUserInfo(final Object tag, final String imId, final DataCallBack<UserInfoBean> callback) {
        getUserInfo(tag, true, false, imId, callback);
    }

    /**
     * 同步从数据库取，数据库中没有取网络的，同步执行。数据变化了不会再通知
     */
    public static UserInfoBean getUserInfo(final String imId) {
        final Realm realm = Repository.getDataBase();
        realm.beginTransaction();
        UserInfoBean bean = realm.where(UserInfoBean.class).equalTo("imId", imId).findFirst();
        realm.commitTransaction();
        UserInfoBean user = null;
        if (bean != null && bean.isManaged() && bean.isValid()) {
            user = realm.copyFromRealm(bean);
        }

        if (!realm.isClosed()) {
            realm.close();
        }
        return user;
    }

    //从网络获取
    private static void getUserInfoFromServer(Object tag, String imId, final DataCallBack<UserInfoBean> callback) {
        List<String> ids = new ArrayList<>();
        ids.add(imId);
        JsonObject params = new JsonObject();
        JsonElement jsonElement = new Gson().toJsonTree(ids);
        params.add("imIdList", jsonElement);
        params.addProperty("userAccount", getImId());
        Repository.getInteraction(LogImInteraction.class).getUserInfo(tag, params, UserInfoBean.class, new
                ICallback<List<UserInfoBean>>() {
                    @Override
                    public void onBegin(Object tag) {

                    }

                    @Override
                    public void onSuccess(final List<UserInfoBean> list) {
                        if (list != null && list.size() > 0) {
                            saveUserInfo(list.get(0));
                            callback.onSuccess(list.get(0));
                        } else {
                            callback.onError("失败！");
                        }
                    }

                    @Override
                    public void onFailure(SimpleBean simpleBean) {
                        callback.onError(simpleBean.getMessage());
                    }

                    @Override
                    public void onError(SimpleBean simpleBean) {
                        callback.onError(simpleBean.getMessage());
                    }

                    @Override
                    public void onProgress(int progress, int max) {

                    }
                });
    }

    /**
     * 添加/更新用户备注
     */
    public static void addOrUpdateUserRemark(final String imId, final String remark, final DataCallBack<String> callback) {
        final Realm realm = Repository.getDataBase();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserInfoBean user = realm.where(UserInfoBean.class).equalTo("imId", imId).findFirst();
                if (user != null && user.isValid() && user.isManaged()) {
                    user.setRemark(remark);
                    if (callback != null) {
                        callback.onSuccess("修改成功");
                    }
                } else {
                    if (callback != null) {
                        callback.onError("修改失败");
                    }
                }
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    /**
     * 名称优先显示逻辑 remark>nickName>name>userName
     */

    public static String handleUserName(UserInfoBean userInfoBean) {
        String name = userInfoBean.getRemark();
        if (TextUtils.isEmpty(name)) {
            name = userInfoBean.getNickName();
        }
        if (TextUtils.isEmpty(name)) {
            name = userInfoBean.getName();
        }
        if (TextUtils.isEmpty(name)) {
            name = userInfoBean.getUserName();
        }
        return name;
    }
}
