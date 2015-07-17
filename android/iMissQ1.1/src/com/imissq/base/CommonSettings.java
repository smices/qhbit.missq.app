package com.imissq.base;

import com.imissq.model.LoginInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class CommonSettings extends AppSettings{
	
	private SharedPreferences globalePrefs;
	
	 // 用户登陆使用的登陆名称
    public StringPreference LOGIN_ACCOUNT_NAME = new StringPreference("account_name", "");
    public StringPreference LOGIN_ENCRYPT_PASSWORD = new StringPreference("account_encrypt_pswd", "");
    public StringPreference LOGIN_TOKEN = new StringPreference("account_token", "");
    public LongPreference LOGIN_TIME = new LongPreference("login_time",0);
	
	public CommonSettings(Context context){
		globalePrefs = context.getSharedPreferences(AppSettings.SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
	}

	@Override
	protected SharedPreferences getGlobalPreferences() {
		// TODO Auto-generated method stub
		return globalePrefs;
	}
	
	public void saveAccountInfo(LoginInfo loginInfo){
		this.LOGIN_ACCOUNT_NAME.setValue(loginInfo.getUser());
		this.LOGIN_ENCRYPT_PASSWORD.setValue(loginInfo.getPassword());
		this.LOGIN_TOKEN.setValue(loginInfo.getToken());
		this.LOGIN_TIME.setValue(loginInfo.getTime());
	}
	
	public LoginInfo getAccountInfo(){
		String name = this.LOGIN_ACCOUNT_NAME.getValue();
		String pwd = this.LOGIN_ENCRYPT_PASSWORD.getValue();
		String token = this.LOGIN_TOKEN.getValue();
		long time = this.LOGIN_TIME.getValue();
		if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(token)){
			return null;
		}else{
			LoginInfo info = new LoginInfo();
			info.setUser(name);
			info.setPassword(pwd);
			info.setToken(token);
			info.setTime(time);
			return info;
		}
	}

}
