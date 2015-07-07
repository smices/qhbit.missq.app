package com.idkin.android.idkclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.umeng.message.PushAgent;

import org.syxc.util.CrashHandler;


/**
 * Created by syxc on 15-1-30.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppData.getInstance().setupStrictMode();

        setContentView(getLayoutResource());

        try {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (null != mToolbar) {
                setSupportActionBar(mToolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }

        // 统计应用启动数据
        PushAgent.getInstance(this).onAppStart();

        // Process global uncaughtException
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    /**
     * Get child Activity layout resource id.
     *
     * @return layout resource id.
     */
    protected abstract int getLayoutResource();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 关于
     */
    protected void goAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    // ---------------------------------------------------------

    protected void setActionBarIcon(int iconRes) {
        if (null != mToolbar) {
            mToolbar.setNavigationIcon(iconRes);
        }
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }


    // ---------------------------------------------------------

    protected void showShortToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
