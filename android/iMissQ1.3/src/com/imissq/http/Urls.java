package com.imissq.http;

public class Urls {

    //	private static final String BASE_URL = "http://imissq.com/apiv1/";
    private static final String BASE_URL = "http://missu.link/apiv1/";

    public static final String getLoginUrl() {//登录
        return BASE_URL + "login?type=default";
    }

    public static final String getWeatherUrl() {//天气
        return BASE_URL + "weather";
    }

    public static final String getUpdateUrl(int version) {//版本更新
        return BASE_URL + "update?os=android&version=" + version;
    }

    public static final String getProductUrl(String token) {//产品
        return BASE_URL + "product?token=" + token;
    }

    public static final String getRecordUrl(String token) {//测试记录
        return BASE_URL + "history?token=" + token;
    }

    public static final String getTopicUrl(String token) {//话题
        return BASE_URL + "topic?token=" + token;
    }

    public static final String getTestUploadUrl(String token) {//上传测试数据
        return BASE_URL + "facerecord?token=" + token;
    }

    public static final String getUserInfoUrl(String token) {//用户信息获取
        return BASE_URL + "profile?token=" + token;
    }

}
