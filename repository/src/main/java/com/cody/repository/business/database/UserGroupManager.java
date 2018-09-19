package com.cody.repository.business.database;

import android.support.annotation.NonNull;

import com.cody.repository.business.bean.entity.UserGroupBean;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.framework.Repository;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


/**
 * Created by cody.yi on 2018/7/20.
 * 用户分组数据库
 */
public class UserGroupManager {

    /**
     * 保存分组信息 等结果
     */
    public static void saveUserGroup(final List<UserGroupBean> newGroups, final DataCallBack<String> callback) {
        final Realm realm = Repository.getDataBase();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmResults<UserGroupBean> groups = realm.where(UserGroupBean.class).findAll();
                groups.deleteAllFromRealm();
                realm.copyToRealmOrUpdate(newGroups);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (callback != null) {
                    callback.onSuccess("更新成功！");
                }
                if (!realm.isClosed()) {
                    realm.close();
                }

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (callback != null) {
                    callback.onError(error.toString());
                }
                if (!realm.isClosed()) {
                    realm.close();
                }
            }
        });
    }

    /**
     * 保存分组信息 不等结果
     */
    public static void saveUserGroup(final List<UserGroupBean> newGroups) {
        saveUserGroup(newGroups, null);
    }

    /**
     * @param once     是否要不监听数据变化 once :true 不监听
     * @param callback 回调
     * @return 取消监听
     */
    public static DataChangeListener getUserGroup(final boolean once, final DataCallBack<List<UserGroupBean>> callback) {
        final Realm realm = Repository.getDataBase();
        if (once) {
            final RealmResults<UserGroupBean> groups = realm.where(UserGroupBean.class).findAll();
            if (groups.size() == 0) {
                callback.onError("无分组信息");
            } else {
                if (groups.isLoaded() && groups.isValid() && groups.isManaged()) {
                    callback.onSuccess(realm.copyFromRealm(groups));
                }
            }
            if (!realm.isClosed()) {
                realm.close();
            }
            return null;
        } else {
            final RealmResults<UserGroupBean> groups = realm.where(UserGroupBean.class).findAllAsync();
            final RealmChangeListener<RealmResults<UserGroupBean>> changeListener;
            groups.addChangeListener(changeListener = new RealmChangeListener<RealmResults<UserGroupBean>>() {
                @Override
                public void onChange(RealmResults<UserGroupBean> groups) {
                    if (groups != null && groups.isLoaded() && groups.isValid() && groups.isManaged()) {
                        callback.onSuccess(realm.copyFromRealm(groups));
                    }
                }
            });
            return new DataChangeListener() {
                @Override
                public void cancel() {
                    groups.removeChangeListener(changeListener);
                    if (!realm.isClosed()) {
                        realm.close();
                    }
                }
            };
        }
    }

    /**
     * 删除分组
     */
    public static void deleteUserGroup(final String groupId, final String imId, final DataCallBack<String> callback) {
        final Realm realm = Repository.getDataBase();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<UserGroupBean> groups = realm.where(UserGroupBean.class).findAll();
                for (UserGroupBean group : groups) {
                    if (groupId.equals(group.getGroupId())) {
                        for (int i = 0; i < group.getCount(); i++) {
                            UserInfoBean u = group.getCustomerList().get(i);
                            if (u.getImId().equals(imId)) {
                                u.setGroupId("");
                                u.setGroupName("");
                                group.getCustomerList().remove(i);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (callback != null) {
                    callback.onSuccess("修改成功");
                }
                if (!realm.isClosed()) {
                    realm.close();
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (callback != null) {
                    if (error != null) {
                        callback.onError(error.toString());
                    } else {
                        callback.onError("修改失败");
                    }
                }
                if (!realm.isClosed()) {
                    realm.close();
                }
            }
        });
    }
}
