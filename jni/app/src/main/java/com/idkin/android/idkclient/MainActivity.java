package com.idkin.android.idkclient;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceResponse;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.idkin.android.idkclient.core.AppClient;
import com.idkin.android.idkclient.core.Callback;
import com.idkin.android.idkclient.misc.AppPreference;
import com.idkin.android.idkclient.model.LoginJSON;
import com.idkin.android.idkclient.model.NormalResult;
import com.idkin.android.idkclient.model.Session;
import com.idkin.android.idkclient.model.TokenJSON;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import net.yoojia.android.autoupdate.AppUpdate;
import net.yoojia.android.autoupdate.AppUpdateService;
import net.yoojia.android.autoupdate.internal.SimpleJsonParser;

import org.syxc.util.Logger;
import org.xwalk.core.XWalkJavascriptResult;
import org.xwalk.core.XWalkNavigationHistory;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.idkin.android.idkclient.Config.DEBUG;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.layout_login)
    ScrollView mScrollView;

    @InjectView(R.id.text_name)
    EditText mTextUsername;

    @InjectView(R.id.text_pwd)
    EditText mTextPassword;

    @InjectView(R.id.progressBar)
    ProgressBar mProgressBar;

    @InjectView(R.id.xwalkview)
    XWalkView mXWalkView;

    private final Handler mHandler = new Handler();

    private long touchTime = 0;

    private PushAgent mPushAgent;

    private Session mSession;

    private Menu optionsMenu;
    private int OPTIONS_TYPE = 0;

    private final IUmengRegisterCallback mUmengRegisterCallback = new IUmengRegisterCallback() {

        @Override
        public void onRegistered(String registrationId) {
            System.out.println("registrationId: " + registrationId + "\n"
                    + mPushAgent.getRegistrationId());
        }
    };

    private final Runnable keepTokenRunnable = new Runnable() {
        @Override
        public void run() {
            if (!TextUtils.isEmpty(AppPreference.getToken())) {
                AppClient.keepToken(AppPreference.getToken(), new Callback<TokenJSON>() {
                    @Override
                    public void success(TokenJSON data) {
                        Logger.i(TAG, "token: " + data.token);
                        AppPreference.setToken(data.token);
                    }

                    @Override
                    public void failure(Object error) {
                        Logger.e(TAG, "keepToken: " + error);
                    }
                });
            }
            mHandler.postDelayed(keepTokenRunnable, 20 * 60 * 1000);
            Logger.i(TAG, "Schedule task running...");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (isFinishing()) {
            return;
        }

        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // 初始化友盟组件
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.onAppStart();
        mPushAgent.enable(mUmengRegisterCallback);

        configWebView();

        mSession = new Gson().fromJson(AppPreference.getSession(), Session.class);

        setupView();

        mHandler.postDelayed(keepTokenRunnable, 1 * 60 * 1000);

        // 显示指明：不需要提示已是最新版本
        setupAppUpdater(false);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();

        if (OPTIONS_TYPE == 0) {
            getMenuInflater().inflate(R.menu.menu_null, menu);
        } else if (OPTIONS_TYPE == 1) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }

        optionsMenu = menu;

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_update) {
            setupAppUpdater(true);
            return true;
        } else if (id == R.id.action_about) {
            goAbout();
            return true;
        } else if (id == R.id.action_logout) {
            logoutHandler();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ResourceType")
    @Override
    public void onBackPressed() {
        if (mXWalkView != null && mXWalkView.getNavigationHistory().canGoBack()) {
            mXWalkView.getNavigationHistory().navigate(XWalkNavigationHistory.Direction.BACKWARD, 1);
        } else {
            long currentTime = System.currentTimeMillis();
            long waitTime = 2000;
            if ((currentTime - touchTime) >= waitTime) {
                // 让Toast的显示时间和等待时间相同
                Toast.makeText(this, getString(R.string.tip_exiting), (int) waitTime).show();
                touchTime = currentTime;
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mXWalkView != null) {
            mXWalkView.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (mXWalkView != null) {
            mXWalkView.onNewIntent(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mXWalkView != null) {
            mXWalkView.pauseTimers();
            mXWalkView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mXWalkView != null) {
            mXWalkView.resumeTimers();
            mXWalkView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        if (mXWalkView != null) {
            mXWalkView.onDestroy();
        }
        mHandler.removeCallbacks(keepTokenRunnable);
        super.onDestroy();
    }

    private void configWebView() {
        mXWalkView.setResourceClient(new ResourceClient(mXWalkView));
        mXWalkView.setUIClient(new UIClient(mXWalkView));

        XWalkPreferences.setValue(XWalkPreferences.SUPPORT_MULTIPLE_WINDOWS, false);
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        XWalkPreferences.setValue(XWalkPreferences.JAVASCRIPT_CAN_OPEN_WINDOW, false);
    }


    private void setupView() {
        if (mSession != null && !TextUtils.isEmpty(mSession.token)) {
            mScrollView.setVisibility(View.GONE);
            mXWalkView.setVisibility(View.VISIBLE);
            asyncLogin(mSession.username, mSession.password);
        } else {
            mScrollView.setVisibility(View.VISIBLE);
            mXWalkView.setVisibility(View.GONE);
        }
    }

    private void setupAppUpdater(boolean flag) {
        AppUpdate appUpdate = AppUpdateService.getAppUpdate(getApplicationContext());
        appUpdate.showLatestVersionTip(flag);
        appUpdate.checkLatestVersion(Config.UPDATE_URL, new SimpleJsonParser());
    }


    @OnClick(R.id.btn_login)
    void loginHandler(View view) {
        loginHandler();

        if (mPushAgent.isRegistered()) {
            if (DEBUG) {
                showLongToast(mPushAgent.getRegistrationId());
            }
        } else {
            showShortToast("Push service not register!");
        }
    }


    private void loginHandler() {
        final String username = String.valueOf(mTextUsername.getText());
        final String password = String.valueOf(mTextPassword.getText());
        asyncLogin(username, password);
    }

    private void asyncLogin(final String username, final String password) {

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            showShortToast("用户名 & 密码不能为空");
            return;
        }

        AppClient.login(username, password, new Callback<LoginJSON>() {
            @Override
            public void success(final LoginJSON data) {
                if (0 == data.error) {

                    if (mPushAgent.isRegistered()) {
                        // 初始化个人推送
                        new AddAliasTask().execute(data.inid);

                        // 把设备信息发送到推送中心
                        AppClient.reportDevice(data.inid, mPushAgent.getRegistrationId(), new Callback<NormalResult>() {
                            @Override
                            public void success(final NormalResult data) {
                                if (data.code != 0) {
                                    if (DEBUG) {
                                        showShortToast(String.valueOf(data.code) + "--" + data.msg);
                                    }
                                } else {
                                    if (DEBUG) {
                                        showShortToast(data.msg);
                                    }
                                }
                            }

                            @Override
                            public void failure(Object error) {
                                showShortToast("reportDevice err: " + error.toString());
                                Logger.e(TAG, error.toString());
                            }
                        });

                    } else {
                        if (DEBUG) {
                            showShortToast("Push service not register!");
                        }
                    }

                    AppPreference.setToken(data.token);

                    if (data.inid != null && data.token != null) {
                        Session session = new Session(username, password, data.inid, data.token); // FIXME: 密码加密（配合服务端）
                        AppPreference.setSession(session);
                    }

                    mXWalkView.load("file:///android_asset/www/index.html#" + data.urlPart, null);

                    mScrollView.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mXWalkView.setVisibility(View.VISIBLE);

                    OPTIONS_TYPE = 1;
                    if (optionsMenu != null) {
                        onPrepareOptionsMenu(optionsMenu);
                    }
                } else {
                    showShortToast(data.result);
                }
            }

            @Override
            public void failure(Object error) {
                Logger.e(TAG, error.toString());
                mScrollView.setVisibility(View.VISIBLE);
                mXWalkView.setVisibility(View.GONE);
            }
        });
    }

    private void logoutHandler() {
        AppClient.logout(AppPreference.getToken(), new Callback<LoginJSON>() {
            @Override
            public void success(LoginJSON data) {
                try {
                    if (0 == data.error) {
                        showShortToast("成功登出");

                        OPTIONS_TYPE = 0;
                        if (optionsMenu != null) {
                            onPrepareOptionsMenu(optionsMenu);
                        }

                        mSession = null;
                        AppPreference.clearPreference();
                        setupView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(Object error) {

            }
        });
    }


    class ResourceClient extends XWalkResourceClient {

        public ResourceClient(XWalkView xwalkView) {
            super(xwalkView);
        }

        public void onLoadStarted(XWalkView view, String url) {
            super.onLoadStarted(view, url);
            Logger.i(TAG, "Load Started:" + url);
        }

        /*@Override
        public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
            view.load(url, null);
            Logger.i(TAG, "url: " + url);
            return true;
        }*/

        public void onLoadFinished(XWalkView view, String url) {
            mProgressBar.setVisibility(View.GONE);
            super.onLoadFinished(view, url);
            Logger.i(TAG, "Load Finished:" + url);
        }

        public void onProgressChanged(XWalkView view, int progressInPercent) {
            super.onProgressChanged(view, progressInPercent);
            Logger.i(TAG, "Loading Progress:" + progressInPercent);
        }

        public WebResourceResponse shouldInterceptLoadRequest(XWalkView view, String url) {
            Logger.i(TAG, "Intercept load request");
            return super.shouldInterceptLoadRequest(view, url);
        }

        public void onReceivedLoadError(XWalkView view, int errorCode, String description,
                                        String failingUrl) {
            Logger.i(TAG, "Load Failed:" + description);
            super.onReceivedLoadError(view, errorCode, description, failingUrl);
        }
    }

    class UIClient extends XWalkUIClient {

        public UIClient(XWalkView xwalkView) {
            super(xwalkView);
        }

        public void onJavascriptCloseWindow(XWalkView view) {
            super.onJavascriptCloseWindow(view);
            Logger.i(TAG, "Window closed.");
        }

        public boolean onJavascriptModalDialog(XWalkView view, JavascriptMessageType type,
                                               String url,
                                               String message, String defaultValue, XWalkJavascriptResult result) {
            Logger.i(TAG, "Show JS dialog.");
            return super.onJavascriptModalDialog(view, type, url, message, defaultValue, result);
        }

        public void onFullscreenToggled(XWalkView view, boolean enterFullscreen) {
            super.onFullscreenToggled(view, enterFullscreen);
            if (enterFullscreen) {
                Logger.i(TAG, "Entered fullscreen.");
            } else {
                Logger.i(TAG, "Exited fullscreen.");
            }
        }

        public void openFileChooser(XWalkView view, ValueCallback<Uri> uploadFile,
                                    String acceptType, String capture) {
            super.openFileChooser(view, uploadFile, acceptType, capture);
            Logger.i(TAG, "Opened file chooser.");
        }

        public void onScaleChanged(XWalkView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
            Logger.i(TAG, "Scale changed.");
        }
    }


    private final class AddAliasTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                return mPushAgent.addAlias(params[0], Config.SECRET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (DEBUG) {
                showShortToast("Add Alias " + (result ? "Success" : "Fail"));
            }
        }
    }

}
