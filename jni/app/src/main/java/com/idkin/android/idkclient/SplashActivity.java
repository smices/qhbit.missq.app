package com.idkin.android.idkclient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import com.library.decrawso.DecRawso;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static com.idkin.android.idkclient.Config.DEBUG;


public class SplashActivity extends BaseActivity {

    protected int sync_async = 0;

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DecRawso.HDL_MSGDECEND:
                    if (!DecRawso.GetInstance().ShowError(SplashActivity.this, msg.arg1)) { // if no error, go on
                        if (DEBUG) {
                            showShortToast("DecRawso ok");
                        }
                        switchToContent();
                        finish();
                    } else {
                        if (DEBUG) {
                            showShortToast("DecRawso fail");
                        }
                    }
                    // if you don't use ProgressDialog, you must stop the application by your self, and until this message.
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // test for x86
                //DecRawso.ConfigureFilter("libffmpeg", "libffmpeg_x86.so");

                if (sync_async == 0) {
                    progressBar.setVisibility(View.VISIBLE);
                    DecRawso.NewInstance(getApplicationContext(), mHandler, true);
                } else {
                    // you can also use sync call
                    DecRawso.NewInstance(getApplicationContext(), null, false);
                    // you can also add here, but if decoding use too much time, it will make application no response
                    // if you loadlib just after initial, it will change to sync call (not recommend)
                    DecRawso.GetInstance().waitdecoding();
                    switchToContent();
                    finish();
                }
            }
        }, 2000);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void switchToContent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

}
