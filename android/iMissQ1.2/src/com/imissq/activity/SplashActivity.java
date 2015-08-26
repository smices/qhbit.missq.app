package com.imissq.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.ambytw.android.shell.sample.global._S;
import com.google.gson.Gson;
import com.imissq.R;
import com.imissq.base.BaseApplication;
import com.imissq.config.Commons;
import com.imissq.config.Constants;
import com.imissq.http.Urls;
import com.imissq.model.LoginInfo;
import com.imissq.model.UpdateBean;
import com.imissq.views.CommonDialog;
import com.imissq.views.CommonDialog.OnFinishInterface;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class SplashActivity extends BaseActivity {

	boolean isStart = false;
	int version = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setContentView(R.layout.act_splash);
		if (!Commons.IsNetwork(this)) {
			Commons.showToast(this, "没有网络");
			finish();
			return;
		}
		getVersionUpdate();
		
		
		int _dpi = getResources().getDisplayMetrics().densityDpi;
		_S.setWidth(getWindowManager().getDefaultDisplay().getWidth());
		_S.setHeight(getWindowManager().getDefaultDisplay().getHeight());
		float scale = _dpi / 160;
		float _fsize = _S.w1o30() / scale;
		_S.setF(_fsize);
		
		//_R.resize(findViewById(R.id.buttonStart).getLayoutParams(), _S.w(), _S.h1o4());
        //_R.resize(findViewById(R.id.imageView).getLayoutParams(), _S.w1o2(), _S.w1o2());
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			
			start();
		};
	};
	
	private void start(){
		boolean isFirst = BaseApplication.getInstance().getSettings().FIRST_START.getValue();
		if(isFirst){
			startActivity(new Intent(SplashActivity.this,FirstPageActivity.class));
			finish();
		}else{
			Commons.startHomeAct(SplashActivity.this);
		}
	}
	
	private void getVersionUpdate() {
		version = Commons.getAppVersion(SplashActivity.this);
		String url = Urls.getUpdateUrl(version);
		Log.d(Constants.TAG,"update url:"+url);
		getHttp().send(HttpRequest.HttpMethod.GET,
				url,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						toStart();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String result = arg0.result;
						Log.d(Constants.TAG,"update result:"+result);
//						toStart();
						Gson gson = new Gson();
						UpdateBean updateInfo = new UpdateBean();
						updateInfo = gson.fromJson(result, UpdateBean.class);
						dealUpdateResult(updateInfo);
					}

				});
	}
	
	private void dealUpdateResult(final UpdateBean updateInfo){
		if(updateInfo == null || updateInfo.getMsg() == null){
			toStart();
			return;
		}
		if(version < updateInfo.getMsg().getLastVersionCode()){
			isStart = true;
			CommonDialog dialog = new CommonDialog(SplashActivity.this,new OnFinishInterface() {
				
				@Override
				public void onConfirm(String msg) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");    
			        Uri content_url = Uri.parse(updateInfo.getMsg().getDownload());   
			        intent.setData(content_url); 
					toStart();
				}
				
				@Override
				public void onCancel(String msg) {
					// TODO Auto-generated method stub
					toStart();
				}
			});
			dialog.setTitle("版本更新");
			dialog.setMessage(updateInfo.getMsg().getDescription());
			dialog.show();
		}else{
			toStart();
		}
	}
	
	private void toStart(){
		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendEmptyMessage(1);
			}
		}.start();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
