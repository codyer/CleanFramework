package com.cody.repository.framework.database.realm;

import android.content.Context;

import com.cody.repository.framework.database.BaseDatabase;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by cody.yi on 2018/7/20.
 * Realm 数据库
 */
public class RealmDatabase extends BaseDatabase {

    private static BaseDatabase mInstance;
    private static boolean install;

    public static Realm getDatabase(String name) {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(getInstance(name).name())
                .encryptionKey(getInstance(name).key())
                .schemaVersion(getInstance(name).version())
//                .modules(new MySchemaModule())
//                .migration(new MyMigration())
                .deleteRealmIfMigrationNeeded()
                .build();
        return Realm.getInstance(config);
    }

    public static void install(Context context){
        Realm.init(context);
        install = true;
    }

    private static BaseDatabase getInstance(String name) {
        if (mInstance == null) {
            mInstance = new RealmDatabase(name);
        }
        return mInstance;
    }

    private RealmDatabase(String name) {
        super(name);
        if (!install) {
            throw new NullPointerException("you should call BaseDatabase.install(context) in you Application first.");
        }
    }

    @Override
    public long version() {
        return 1;
    }

    @Override
    public String suffix() {
        return "realm.realm";
    }
}
