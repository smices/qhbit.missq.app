package com.idkin.android.idkclient.misc;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.idkin.android.idkclient.Config;
import com.idkin.android.idkclient.model.Session;
import com.idkin.android.idkclient.utils.AppUtil;

/**
 * Created by syxc on 15-2-4.
 */
public class AppPreference {


    static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(AppUtil.getAppContext());
    }


    /**
     * 保存 Session
     *
     * @param session
     */
    public static void setSession(final Session session) {
        String _session = new Gson().toJson(session);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(Config.KEY_SID, _session != null ? _session : "");
        editor.apply();
    }

    /**
     * 获取 Session
     *
     * @return
     */
    public static String getSession() {
        return getSharedPreferences().getString(Config.KEY_SID, "");
    }

    public static void setToken(String token) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(Config.KEY_TOKEN, token != null ? token : "");
        editor.apply();
    }

    public static String getToken() {
        return getSharedPreferences().getString(Config.KEY_TOKEN, "");
    }

    /**
     * 清除设置
     */
    public static void clearPreference() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear().apply();
    }

}
