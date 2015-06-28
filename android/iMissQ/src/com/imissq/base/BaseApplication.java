package com.imissq.base;



import com.imissq.model.LoginInfo;

import android.app.Application;

public class BaseApplication extends Application{

	private static BaseApplication instance;
	private static LoginInfo loginInfo;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
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
	
	
}
