package com.imissq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.imissq.R;
import com.imissq.base.BaseApplication;
import com.imissq.base.LoginCallBack;
import com.imissq.config.Commons;
import com.imissq.model.UserTestBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

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

    private void login(final String name, final String pwd) {
        showLoadDialog();

        Commons.userLogin(getHttp(), name, pwd, new LoginCallBack() {

            @Override
            public void loginSuccess(String result) {
                // TODO Auto-generated method stub

//                Log.e("YII",result);
//                setTestData();
                boolean saveSuccess = Commons.saveUserInfo(name, pwd, result, LoginActivity.this);
                if (saveSuccess) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Commons.showToast(LoginActivity.this, "登录失败");
                }
            }

            @Override
            public void loginFailed(String msg) {
                // TODO Auto-generated method stub
                Commons.showToast(LoginActivity.this, "登录失败");
            }
        });
    }

    private void setTestData() {
        if (Commons.getAccountInfo(LoginActivity.this) != null) {
            return;
        }
        int water = 85;
        long time = System.currentTimeMillis();
        for (int i = 1; i < 5; i++) {
            UserTestBean testData = new UserTestBean();
            testData.setWater(water);
            testData.setTime(time);
            water = water - 10;
            time = time - 1000 * 60 * 60 * 8;
            Commons.saveTestData(BaseApplication.getInstance().getDb(), testData);
        }
    }

    private void register() {

    }

}
