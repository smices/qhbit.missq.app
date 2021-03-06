package com.imissq.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.imissq.base.BaseApplication;

/**
 * @author ShuLQ
 * @content 获取设备的相关信息
 */
public class DeviceConfiger {

    public static float sDensity;
    public static float sFontScale;
    public static int sWidth;
    public static int sHeight;
    public static Context sContext;
    public static String sDeviceId;

    static {
        init();
    }

    /**
     * @content 获取设备的屏幕尺寸和屏幕密度
     */
    public static void init() {
        sContext = BaseApplication.getInstance();
        DisplayMetrics dm = sContext.getResources().getDisplayMetrics();
        TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
        sDeviceId = "" + tm.getDeviceId();
        sDensity = dm.density;
        sWidth = dm.widthPixels;
        sHeight = dm.heightPixels;
        sFontScale = dm.scaledDensity;
    }

    /**
     * 获取
     *
     * @return
     */
    public static String getVersionName() {
        PackageManager manager = sContext.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(sContext.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取
     *
     * @return
     */
    public static int getAPPVersion() {
        PackageManager manager = sContext.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(sContext.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 屏幕像素宽
     *
     * @return
     */
    public static int getScreenWidth() {
        return sWidth;
    }

    /**
     * 屏幕像素高
     *
     * @return
     */
    public static int getScreenHeight() {
        return sHeight;
    }

    /**
     * 屏幕密度
     *
     * @return
     */
    public static float getScreenDensity() {
        return sDensity;
    }

    /**
     * @return
     */
    public static float getFontScale() {
        return sFontScale;
    }

    /**
     * 获取屏幕方向： 1 、横屏 ORIENTATION_LANDSCAPE 2 、竖屏 ORIENTATION_PORTRAIT
     *
     * @return
     */
    public static int getScreenOrientation() {
        Configuration config = sContext.getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
            return 1;
        } else if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
            return 2;
        }
        return 0;
    }

    /**
     * @param context
     * @param dpValue dp值
     * @return
     * @content dp转px
     * @author ShuLQ
     */
    public static int dp2px(float dpValue) {
        return (int) (dpValue * sDensity + 0.5f);
    }

    /**
     * @param context
     * @param pxValue px值
     * @return
     * @content px转dp
     * @author ShuLQ
     */
    public static int px2dp(float pxValue) {
        return (int) (pxValue / sDensity + 0.5f);
    }

    /**
     * @param context
     * @param px
     * @return
     * @content px转换为sp，保证文字大小不变
     * @author ShuLQ
     */
    public static int px2sp(float pxValue) {

        return (int) (pxValue / sFontScale + 0.5f);
    }

    /**
     * @param context
     * @param dpValue
     * @return
     * @content dp转换为sp，保证文字大小不变
     * @author ShuLQ
     */
    public static int dp2sp(float dpValue) {
        return px2sp(dp2px(dpValue));
    }

    /**
     * 手机IMEI号
     */
    public static String getDeviceId() {
        String deviceId = "000000000000";
        try {
            TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } catch (Exception e) {
        }
        return deviceId;
    }

    /**
     * @return
     * @content 返回设备mac地址
     */
    public static String getMacAddress() {
        if (sContext == null) {
            return "";
        }
        WifiManager wifi = (WifiManager) sContext.getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return "";
        }
        WifiInfo info = wifi.getConnectionInfo();
        if (info == null) {
            return "";
        }
        return info.getMacAddress();
    }

    /**
     * @return
     * @content 获得语言编码
     */
    public static String getLanguage() {
        return sContext.getResources().getConfiguration().locale.getLanguage();
    }

    /**
     * @return
     * @content 获得语言编码
     */
    public static String getCountry() {
        return sContext.getResources().getConfiguration().locale.getCountry();
    }

    /**
     * @return
     * @content 获取android的版本
     * @author ShuLQ
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * @param context
     * @return
     * @content 获取联网类型
     * @author ShuLQ
     */
    public static String getNetType() {
        ConnectivityManager connectionManager = (ConnectivityManager) sContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return networkInfo.getTypeName();
        }
        return null;
    }

    /**
     * 获取联网类型 2G 3G 4G WIFI UNKNOWN NONE
     *
     * @return
     */
    public static String getNetTypeInChina() {
        ConnectivityManager cm = (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return "WIFI";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                        return "2G";
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 12:
                    case 15:
                        return "3G";
                    case 13:
                        return "4G";
                    default:
                        return "UNKNOWN";
                }
            } else {
                return "UNKNOWN";
            }
        }
        return "NONE";
    }

    /**
     * 设备是否ROOT
     *
     * @return
     */
//    public static String getCrack() {
//        if (ShellUtils.isRootPossible()) {
//            return "1";
//        } else {
//            return "0";
//        }
//    }

    /**
     * 设备运营商
     *
     * @return
     */
    public static String getOperators() {
        TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            String imsi = tm.getSubscriberId();
            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            if (imsi == null) {
                return "NONE";
            }
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                return "移动";
            } else if (imsi.startsWith("46001")) {
                return "联通";
            } else if (imsi.startsWith("46003")) {
                return "电信";
            } else {
                return "UNKNOWN";
            }
        } else {
            return "NONE";
        }
    }

    /**
     * 屏幕宽的比例对应的宽度
     */
    public static int getScreenWPcr(float pcr) {
        return (int) (sWidth * pcr);
    }
}
