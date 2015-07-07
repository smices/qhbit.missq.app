package com.idkin.android.idkclient.misc;

/**
 * Created by syxc on 15-2-4.
 */
public class SessionService {

    private static SessionService instance = null;

    public static synchronized SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        return instance;
    }

    public String getSession() {
        return AppPreference.getSession();
    }

}
