package com.idkin.android.idkclient.model;

/**
 * Created by syxc on 15-2-4.
 */
public class Session {

    public String username;

    public String password;

    public String inid;

    public String token;

    public Session(String name, String pwd, String id, String token) {
        this.username = name;
        this.password = pwd;
        this.inid = id;
        this.token = token;
    }

}
