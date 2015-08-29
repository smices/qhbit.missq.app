package com.imissq.base;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.ambytw.android.missq.lib.IMissQLib;
import com.ambytw.android.missq.lib.MissQLib;
import com.imissq.config.Commons;
import com.imissq.config.Constants;
import com.imissq.http.PostMap;
import com.imissq.http.Urls;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class MyService extends Service implements
        IMissQLib.IMissQLibListener {

    public static boolean START_FLAG = true;

    private static MissQLib mQLib;

    private static boolean mMeasurable = true;

    private static IMissQLib.IMissQLibListener mListener;

    public static MissQCallBack missqCallback;

    public int connectCount = 0;

    private static DbUtils db;

    @SuppressLint("HandlerLeak")
    private final Handler statushandler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d(Constants.TAG, " msg arg1:" + msg.arg1);
            if (msg.arg1 == IMissQLib.IMissQLibListener.UPDATE_SUCCESSED) {//连接成功

                float water = mQLib.calcSkinLevel();
                int b[] = mQLib.getSkinValue(18, 1, water);
                mMeasurable = true;

                //Log.d(Constants.TAG,"connect success, b0:"+b[0]+",b1:"+b[1]+",b2:"+b[2]);
                if (missqCallback != null) {
                    missqCallback.waterCallBack(b, water);
                    //uploadTestData(b,water);
                    missqCallback.connectSuccess();
                }
            } else if ((msg.arg1 == IMissQLib.IMissQLibListener.UPDATE_FAILED)
                    || (msg.arg1 == IMissQLib.IMissQLibListener.UPDATE_UNSUPPORTED)) {//连接失败
                mMeasurable = true;
                if (missqCallback != null) {
                    missqCallback.connectFailed();
                }
            } else {//未连接测量仪
                //Log.d(Constants.TAG,"test connect failed,nothing");
                mMeasurable = true;
            }
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mQLib = new MissQLib(this);
        setMisqValue(new float[]{1.0f});
        mListener = this;
        db = BaseApplication.getInstance().getDb();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        connectClient();
    }

    @Override
    public void onUpdate(int arg0) {
        // TODO Auto-generated method stub
        Message msg = statushandler.obtainMessage();
        msg.arg1 = arg0;
        statushandler.sendMessage(msg);
    }

    public static void connectClient() {
        if (mMeasurable) {
            mMeasurable = false;
            boolean isStop = mQLib.stop();
            Log.d(Constants.TAG, "qlib stop status :" + isStop);
            if (isStop) {
                mQLib.start(mListener);
            }

        }
    }

    public static interface MissQCallBack {
        public void waterCallBack(int water[], float base);

        public void connectFailed();

        public void connectSuccess();

        public MissQLib getMissQLib();
    }

    public static void setMisqValue(float[] sensity) {
        MyService.mQLib.setSensitivityRaw(sensity);
        MyService.mQLib.setObjectStatus();
        MyService.mQLib.setAutoSensitivity();
        MyService.mQLib.loadDefault();
    }

    public static void uploadTestData(int b[], float water) {
        //插入本次测试时间
        BaseApplication.getInstance().getSettings().TEST_TIME.setValue(System.currentTimeMillis());

        HttpUtils http = new HttpUtils();
        PostMap map = new PostMap();
        map.put("uid", "111");
        map.put("hardware", "1");
        map.put("hardware_ver", "6");
        map.put("sensitivity", "1");
        map.put("body_part", "1");
        map.put("body_part_position", "1,2,3");
        map.put("oil", b[1] + "");
        map.put("age", b[0] + "");
        map.put("skin_level", b[2] + "");
        map.put("water", water + "");
        if (BaseApplication.getLoginInfo() == null) {
            return;
        }
        String token = BaseApplication.getLoginInfo().getToken();
        String url = Urls.getTestUploadUrl(token);

        http.send(HttpRequest.HttpMethod.POST, url,
                map.getRequestParams(), new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        // TODO Auto-generated method stub
                        String result = arg0.result;
                        //Log.d(Constants.TAG,"update test data result:"+result);
                    }

                });
        if (db != null) {
            Commons.saveTestData(db, Commons.getUserTestData(b, water));
        }

    }

}
