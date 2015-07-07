package com.idkin.android.idkclient;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.view.ViewConfiguration;

import org.syxc.util.Logger;

import java.io.File;
import java.lang.reflect.Field;

import butterknife.ButterKnife;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.FROYO;
import static com.idkin.android.idkclient.Config.DEBUG;

/**
 * Created by syxc on 15-1-30.
 */
public class AppData extends Application {

    private static AppData instance;

    public AppData() {
        if (SDK_INT <= FROYO) {
            // Disable http.keepAlive on Froyo and below
            System.setProperty("http.keepAlive", "false");
            enableHttpResponseCache();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    private void init() {
        Logger.debug = DEBUG;
        setupStrictMode();
        ButterKnife.setDebug(DEBUG);
        setupOverflowMenu();
    }

    /**
     * @return AppData singleton instance
     */
    public static synchronized AppData getInstance() {
        return instance;
    }

    @SuppressLint("NewApi")
    public void setupStrictMode() {
        // 开发模式下启用严格模式
        if (DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork() // .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }

    /**
     * http://stackoverflow.com/questions/9286822/how-to-force-use-of-overflow-menu-on-devices-with-menu-button
     */
    private void setupOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // -----------------------------------------------------------

    /**
     * 让早期的Android版本支持缓存
     */
    private void enableHttpResponseCache() {
        try {
            long httpCacheSize = 10 * 1024 * 1024; // 10 MB
            File httpCacheDir = new File(getCacheDir(), "http");
            Class.forName("android.net.http.HttpResponseCache")
                    .getMethod("install", File.class, long.class)
                    .invoke(null, httpCacheDir, httpCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // -----------------------------------------------------------

    /**
     * @return getApplicationContext()
     */
    public Context getAppContext() {
        return getApplicationContext();
    }

}
