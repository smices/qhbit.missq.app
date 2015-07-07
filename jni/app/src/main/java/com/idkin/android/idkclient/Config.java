package com.idkin.android.idkclient;

/**
 * Created by syxc on 15-1-30.
 */
public class Config {

    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static final String SECRET = "54c8be4ffd98c5d42e0004e2";

    public static final String KEY_SID = "session";
    public static final String KEY_TOKEN = "token";

    // 资源包下载地址
    public static final String RES_UPDATE_URL = "http://121.201.11.123:20001/mobile/update/v1/latest?channel=stable&platform=android&pkg_type=res";

    // 版本更新地址
    public static final String UPDATE_URL = "http://121.201.11.123:20001/mobile/update/v1/latest?channel=stable&platform=android&pkg_type=app";


    // -------------------- API Url --------------------

    public static final String BASE_URL = "http://www.idkin.com";
    public static final String PUSH_URL = "http://121.201.11.123:20001";

    // 登录
    public static final String API_LOGIN = "/login/mobilLogin";

    // 登出
    public static final String API_LOGOUT = "/login/mobilLoginOut";

    // 续期token
    public static final String API_KEEP_TOKEN = "/login/mobilToken";

    // 上报设备信息
    public static final String API_REPORT_DEVICE = "/mobile/push/device";

}
