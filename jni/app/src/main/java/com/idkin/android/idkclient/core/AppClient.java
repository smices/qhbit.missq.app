package com.idkin.android.idkclient.core;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.idkin.android.idkclient.Config;
import com.idkin.android.idkclient.model.LoginJSON;
import com.idkin.android.idkclient.model.NormalResult;
import com.idkin.android.idkclient.model.TokenJSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.syxc.util.Logger;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.sql.Timestamp;

/**
 * Created by syxc on 15-1-30.
 */
public class AppClient {

    private static final String TAG = AppClient.class.getSimpleName();

    private static final Gson gson;
    private static final AsyncHttpClient client;

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Timestamp.class, new TimestampDeserializer());
        gson = gsonBuilder.create();
        client = new AsyncHttpClient();
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static synchronized AsyncHttpClient getHttpClient() {
        return client;
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return Config.BASE_URL + relativeUrl;
    }


    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param callback
     */
    public static void login(String username, String password, final Callback<LoginJSON> callback) {

        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        AppClient.post(Config.API_LOGIN, params, new BaseJsonHttpResponseHandler<LoginJSON>() {

            @Override
            public void onSuccess(int i, Header[] headers, String s, LoginJSON loginJSON) {
                if (loginJSON != null) {
                    Logger.i(TAG, "loginJSON: " + gson.toJson(loginJSON));
                    callback.success(loginJSON);
                } else {
                    callback.failure("LoginJSON is null");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, Throwable throwable, String s, LoginJSON loginJSON) {

            }

            @Override
            protected LoginJSON parseResponse(String s, boolean b) throws Throwable {
                return gson.fromJson(processDataBody(s), LoginJSON.class);
            }
        });
    }

    /**
     * 登出
     *
     * @param token
     * @param callback
     */
    public static void logout(String token, final Callback<LoginJSON> callback) {

        RequestParams params = new RequestParams();
        params.put("token", token);

        AppClient.get(Config.API_LOGOUT, params, new BaseJsonHttpResponseHandler<LoginJSON>() {

            @Override
            public void onSuccess(int i, Header[] headers, String s, LoginJSON loginJSON) {
                if (loginJSON != null) {
                    Logger.i(TAG, "loginJSON: " + gson.toJson(loginJSON));
                    callback.success(loginJSON);
                } else {
                    callback.failure("LoginJSON is null");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, Throwable throwable, String s, LoginJSON loginJSON) {

            }

            @Override
            protected LoginJSON parseResponse(String s, boolean b) throws Throwable {
                return gson.fromJson(processDataBody(s), LoginJSON.class);
            }
        });
    }

    /**
     * 续期 token
     *
     * @param token    需要续期的 token
     * @param callback
     */
    public static void keepToken(String token, final Callback<TokenJSON> callback) {

        RequestParams params = new RequestParams();
        params.put("token", token);

        AppClient.post(Config.API_KEEP_TOKEN, params, new BaseJsonHttpResponseHandler<TokenJSON>() {

            @Override
            public void onSuccess(int i, Header[] headers, String s, TokenJSON tokenJSON) {
                if (tokenJSON != null) {
                    Logger.i(TAG, "tokenJSON: " + gson.toJson(tokenJSON));
                    callback.success(tokenJSON);
                } else {
                    callback.failure("TokenJSON is null");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, Throwable throwable, String s, TokenJSON tokenJSON) {

            }

            @Override
            protected TokenJSON parseResponse(String s, boolean b) throws Throwable {
                return gson.fromJson(processDataBody(s), TokenJSON.class);
            }
        });
    }

    /**
     * 上报推送设备的信息 device
     *
     * @param device   上报推送设备的信息
     * @param callback
     */
    public static void reportDevice(String user, String device, final Callback<NormalResult> callback) {

        RequestParams params = new RequestParams();
        params.put("user", user);
        params.put("device", device);
        params.put("appkey", Config.SECRET);

        client.post(Config.PUSH_URL + Config.API_REPORT_DEVICE, params, new BaseJsonHttpResponseHandler<NormalResult>() {

            @Override
            public void onSuccess(int i, Header[] headers, String s, NormalResult result) {
                if (result != null) {
                    Logger.i(TAG, "reportDevice: " + gson.toJson(result));
                    callback.success(result);
                } else {
                    callback.failure("NormalResult is null");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, Throwable throwable, String s, NormalResult result) {
                Logger.i(TAG, "reportDevice err: " + s);
                callback.failure(s);
            }

            @Override
            protected NormalResult parseResponse(String s, boolean b) throws Throwable {
                return gson.fromJson(processDataBody(s), NormalResult.class);
            }
        });
    }


    /**
     * Processes a conference data body and calls the appropriate data type handlers
     * to process each of the objects represented therein.
     *
     * @param dataBody The body of data to process
     * @throws IOException If there is an error parsing the data.
     */
    private static JsonElement processDataBody(String dataBody) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(dataBody));
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = null;
        try {
            reader.setLenient(true); // To err is human
            // the whole file is a single JSON object
            /*reader.beginObject();*/
            jsonElement = parser.parse(dataBody);
            /*reader.endObject();*/
        } finally {
            reader.close();
        }
        return jsonElement;
    }


    private static class TimestampDeserializer implements JsonDeserializer<Timestamp> {

        public Timestamp deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
            final long time = Long.parseLong(json.getAsString());
            return new Timestamp(time * 1000);
        }
    }

}
