package com.imissq.base;


import android.app.Activity;
import android.app.Application;

import com.imissq.model.LoginInfo;
import com.lidroid.xutils.DbUtils;

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static LoginInfo loginInfo;
    private CommonSettings mSettings;

    private DbUtils db;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
        mSettings = new CommonSettings(this);
        db = DbUtils.create(this);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public static void setLoginInfo(LoginInfo loginInfo) {
        BaseApplication.loginInfo = loginInfo;
    }

    public static void setInstance(BaseApplication instance) {
        BaseApplication.instance = instance;
    }

    public static CommonSettings getSettings(Activity act) {
        if (act.getApplication() instanceof BaseApplication) {
            return ((BaseApplication) act.getApplication()).getSettings();
        }
        return new CommonSettings(act);
    }

    public CommonSettings getSettings() {
        return mSettings;
    }

    public DbUtils getDb() {
        return db;
    }

    public void setDb(DbUtils db) {
        this.db = db;
    }


}
