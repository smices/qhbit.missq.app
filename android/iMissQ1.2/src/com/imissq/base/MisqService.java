package com.imissq.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ambytw.android.missq.lib.IMissQLib;
import com.ambytw.android.missq.lib.MissQLib;
import com.imissq.base.MyService.MissQCallBack;
import com.imissq.config.Constants;
import com.imissq.http.PostMap;
import com.imissq.http.Urls;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class MisqService implements IMissQLib.IMissQLibListener {
	

	private static MissQLib mQLib;

	private static boolean mMeasurable = true;

	private static IMissQLib.IMissQLibListener mListener;
	
	public static MissQCallBack missqCallback;
	
	public int connectCount = 0;
	
	private static MisqService service;
	private Context context;
	
	public MisqService(Context context){
		this.context = context;
	}
	
	public static MisqService getInstance(Context c){
		if(service == null){
			service = new MisqService(c);
		}
		return service;
	}
	
	public void start(){
		mQLib = new MissQLib(context);
		setMisqValue(new float[] {1.0f});
        mListener = this;
        connectClient();
	}

	@Override
	public void onUpdate(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressLint("HandlerLeak")
	private final Handler statushandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.d(Constants.TAG," msg arg1:"+msg.arg1);
			if (msg.arg1 == IMissQLib.IMissQLibListener.UPDATE_SUCCESSED) {//连接成功

				float water = mQLib.calcSkinLevel();
				int b[] = mQLib.getSkinValue(18, 1, water);
				mMeasurable = true;
				
				Log.d(Constants.TAG,"connect success, b0:"+b[0]+",b1:"+b[1]+",b2:"+b[2]);
				if(missqCallback != null){
					missqCallback.waterCallBack(b);
					uploadTestData(b,water);
					missqCallback.connectSuccess();
				}
			} else if ((msg.arg1 == IMissQLib.IMissQLibListener.UPDATE_FAILED)
					|| (msg.arg1 == IMissQLib.IMissQLibListener.UPDATE_UNSUPPORTED)) {//连接失败
				mMeasurable = true;
				missqCallback.connectFailed();
				connectError();
			} else {//未连接测量仪
				Log.d(Constants.TAG,"test connect failed,nothing");
				mMeasurable = true;
				missqCallback.connectFailed();
				connectError();
			}
		}

	};
	
	private void connectError(){
		if(connectCount >= 10){
			if(missqCallback != null){
				missqCallback.connectFailed();
			}
			connectClient();
			connectCount = 0;
		}
		connectCount++;
	}
	
	private void connectClient(){
		if (mMeasurable) {
			mMeasurable = false;
			mQLib.start(mListener);
		}
	}

	public static interface MissQCallBack{
		public void waterCallBack(int water[]);
		public void connectFailed();
		public void connectSuccess();
		public MissQLib getMissQLib();
	}
	
	public static void setMisqValue(float[] sensity){
		mQLib.setSensitivityRaw(sensity);
		mQLib.setObjectStatus();
		mQLib.setAutoSensitivity();
		mQLib.loadDefault();
	}
	
	public static void uploadTestData(int b[],float water){
		HttpUtils http = new HttpUtils();
		PostMap map = new PostMap();
		map.put("uid", "111");
		map.put("hardware", "1");
		map.put("hardware_ver", "6");
		map.put("sensitivity", "1");
		map.put("body_part", "1");
		map.put("body_part_position", "1,2,3");
		map.put("oil", b[1]+"");
		map.put("age", b[0]+"");
		map.put("skin_level", b[2]+"");
		map.put("water", water+"");
		if(BaseApplication.getLoginInfo() == null){
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
						Log.d(Constants.TAG,"update test data result:"+result);
					}

				});
	}

}
