package com.imissq.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imissq.activity.BaseActivity;
import com.imissq.activity.HomeActivity;
import com.imissq.activity.LoginActivity;
import com.imissq.base.BaseApplication;
import com.imissq.base.LoginCallBack;
import com.imissq.http.PostMap;
import com.imissq.http.Urls;
import com.imissq.model.LoginBean;
import com.imissq.model.LoginInfo;
import com.imissq.model.UserTestBean;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commons {

    public static void showToast(Context c, String s, int time) {
        Toast.makeText(c, s, time).show();
    }

    public static void showToast(Context c, String s) {
        showToast(c, s, Toast.LENGTH_SHORT);
    }

    public static boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取机器ip地址
     *
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        String ip = null;
        if (isWifiEnabled(context)) {
            ip = getWifiIpAddress(context);
        } else {
            ip = get3GIp();
        }
        return ip;
    }

    private static String get3GIp() {
        String ipaddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipaddress = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.toString();
        }
        return ipaddress;
    }

    public static boolean isWifiEnabled(Context context) {
        WifiManager mWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
        if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
            return true;
        } else {
            return false;
        }
    }

    private static String getWifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();

        // 返回整型地址转换成“*.*.*.*”地址
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
    }

    public static boolean IsNetwork(Context context) {
        boolean flag = true;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public static int getAppVersion(Context c) {
        PackageManager manager;
        PackageInfo info = null;
        manager = c.getPackageManager();
        try {
            info = manager.getPackageInfo(c.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return 0;
    }

    public static void setAccountInfo(LoginInfo info, Activity act) {
        BaseApplication.setLoginInfo(info);
        ((BaseApplication) act.getApplication()).getSettings().saveAccountInfo(
                info);
    }

    public static LoginInfo getAccountInfo(Activity act) {
        return ((BaseApplication) act.getApplication()).getSettings()
                .getAccountInfo();
    }

    public static void userLogin(HttpUtils http, final String user,
                                 final String pwd, final LoginCallBack callBack) {
        PostMap map = new PostMap();
        map.put("username", user);
        map.put("password", pwd);

//        Log.e("YII", Urls.getLoginUrl());
        http.send(HttpRequest.HttpMethod.POST, Urls.getLoginUrl(),
                map.getRequestParams(), new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        // TODO Auto-generated method stub
                        callBack.loginFailed(arg1);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        // TODO Auto-generated method stub
//                        Log.e("yii22",arg0.result);

                        String result = arg0.result;
                        callBack.loginSuccess(result);
                    }

                });
    }

    public static boolean saveUserInfo(String user, String pwd, String result, BaseActivity act) {
        Log.d(Constants.TAG, "result:" + result);
        Gson gson = new Gson();
        LoginBean brResult = new LoginBean();
        brResult = gson.fromJson(result, LoginBean.class);
        if (brResult.getCode() == 0) {
            LoginInfo info = new LoginInfo();
            info.setUser(user);
            info.setPassword(pwd);
            info.setToken(brResult.getMsg());
            info.setTime(System.currentTimeMillis());
            setAccountInfo(info, act);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLoginTimeOut(long lastTime) {
        long time = System.currentTimeMillis() - lastTime;
        if (time < 20 * 60 * 1000) {
            return false;
        }
        return true;
    }

    public static UserTestBean getUserTestData(int[] b, float water) {
        UserTestBean testBean = new UserTestBean();
        testBean.setAge(b[0]);
        testBean.setOil(b[1]);
        testBean.setSkin_level(b[2]);
        testBean.setWater(water);
        testBean.setTime(System.currentTimeMillis());
        return testBean;
    }

    public static UserTestBean getTodayData() {
        DbUtils db = BaseApplication.getInstance().getDb();
        List<UserTestBean> list = new ArrayList<UserTestBean>();
        if (db != null) {
            long time = System.currentTimeMillis();
            time = time - 1000 * 60 * 60 * 24;

            try {
                list = db.findAll(Selector.from(UserTestBean.class).where("time", ">", time));
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public static boolean saveTestData(DbUtils db, UserTestBean testBean) {
        try {
            db.save(testBean);
            return true;
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static List<UserTestBean> getThreeDaysData(DbUtils db) {
        List<UserTestBean> list = new ArrayList<UserTestBean>();
        try {
            long time = System.currentTimeMillis() - 24 * 60 * 60 * 1000 * 3;//三天前的时间
            list = db.findAll(Selector.from(UserTestBean.class).where("time", ">", time));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static ColorStateList getCommonColorStateList() {
        int[] states = {0, 1, 2};
        int white = Color.parseColor("#ffffff");
        int black = Color.parseColor("#333333");
        int[] colors = {black, white, white};
        return createColorStateList(states, colors);
    }

    public static ColorStateList createColorStateList(int[] states, int[] colors) {
        int[] tempColors = colors;
        int[][] tempStates = new int[colors.length][];

        for (int i = 0; i < tempColors.length; i++) {
            if (states[i] == -1) {
                tempStates[i] = new int[]{android.R.attr.state_enabled};
            } else {
                tempStates[i] = new int[]{android.R.attr.state_enabled, states[i]};
            }
        }
        ColorStateList colorList = new ColorStateList(tempStates, tempColors);
        return colorList;
    }

    public static void startHomeAct(Activity act) {
        LoginInfo info = Commons.getAccountInfo(act);
        BaseApplication.setLoginInfo(info);
        if (info == null) {
            act.startActivity(new Intent(act, LoginActivity.class));
        } else {
            if (Commons.isLoginTimeOut(info.getTime())) {
                act.startActivity(new Intent(act, LoginActivity.class));
            } else {
                act.startActivity(new Intent(act, HomeActivity.class));
            }
        }
        act.finish();
    }
}
