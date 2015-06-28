package com.imissq.activity;

import com.google.gson.Gson;
import com.imissq.R;
import com.imissq.base.BaseApplication;
import com.imissq.config.Commons;
import com.imissq.config.Constants;
import com.imissq.http.PostMap;
import com.imissq.http.Urls;
import com.imissq.model.LoginBean;
import com.imissq.model.LoginInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

	@ViewInject(R.id.et_name)
	private EditText et_name;
	@ViewInject(R.id.et_password)
	private EditText et_password;
	@ViewInject(R.id.btn_enter)
	private Button btn_login;
	@ViewInject(R.id.btn_register)
	private Button btn_register;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.act_login);
		ViewUtils.inject(this);

		init();
	}

	private void init() {
		// test data
		et_name.setText("13751015906");
		et_password.setText("abc123");

		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_enter:
			login();
			break;
		case R.id.btn_register:
			register();
			break;

		}
	}

	private void login() {
		String name = et_name.getText().toString();
		String password = et_password.getText().toString();
		if (TextUtils.isEmpty(name)) {
			Commons.showToast(this, "手机号不能为空");
			return;
		}
		if (!Commons.isMobile(name)) {
			Commons.showToast(this, "手机号不正确");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			Commons.showToast(this, "请输入密码");
			return;
		}
		login(name, password);
	}

	private void login(String name, String pwd) {
		showLoadDialog();
		PostMap map = new PostMap();
		map.put("username", name);
		map.put("password", pwd);
		getHttp().send(HttpRequest.HttpMethod.POST, Urls.getLoginUrl(),
				map.getRequestParams(), new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						dismissLoadDialog();
						Commons.showToast(LoginActivity.this, "网络出错");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						dismissLoadDialog();
						String result = arg0.result;
						Log.d(Constants.TAG, "response:" + arg0.toString());
						Log.d(Constants.TAG, "result:" + result);
						Gson gson = new Gson();
						LoginBean brResult = new LoginBean();
						brResult = gson.fromJson(result, LoginBean.class);
						if (brResult.getCode() == 0) {
							LoginInfo info = new LoginInfo();
							info.setUser(et_name.getText().toString());
							info.setPassword(et_password.toString());
							info.setToken(brResult.getMsg());
							BaseApplication.setLoginInfo(info);
							startActivity(new Intent(LoginActivity.this,HomeActivity.class));
							finish();
						} else {
							Commons.showToast(LoginActivity.this, "登录失败");
						}
					}

				});
	}

	private void register() {

	}

}
