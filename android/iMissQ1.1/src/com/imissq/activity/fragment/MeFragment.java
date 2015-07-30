package com.imissq.activity.fragment;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.imissq.R;
import com.imissq.activity.BaseActivity;
import com.imissq.activity.SplashActivity;
import com.imissq.base.BaseApplication;
import com.imissq.config.Commons;
import com.imissq.config.Constants;
import com.imissq.http.Urls;
import com.imissq.model.UpdateBean;
import com.imissq.model.UserInfo;
import com.imissq.utils.MaterialUtils;
import com.imissq.views.CommonDialog;
import com.imissq.views.CommonDialog.OnFinishInterface;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MeFragment extends BaseFragment implements View.OnClickListener {

	private BaseActivity act;

	@ViewInject(R.id.rl_me)
	RelativeLayout rlMe;
	@ViewInject(R.id.rl_my_friends)
	RelativeLayout rlMyFriends;
	@ViewInject(R.id.llMeMainInfo)
	LinearLayout llMeMainInfo;
	@ViewInject(R.id.rl_update)
	RelativeLayout rlUpdate;
	@ViewInject(R.id.rl_about)
	RelativeLayout rlAbout;
	
	@ViewInject(R.id.tvUserName)
	TextView tvUserName;
	
	private UserInfo info;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		act = (BaseActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frg_me, null);
		ViewUtils.inject(this, view);
		initView();
		return view;
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		rlMe.setBackgroundDrawable(MaterialUtils.createSolidStrokeBg(act
				.getResources().getColor(R.color.black_c)));
		rlMyFriends.setBackgroundDrawable(MaterialUtils.createSolidStrokeBg(act
				.getResources().getColor(R.color.black_c)));
		llMeMainInfo.setBackgroundDrawable(MaterialUtils
				.createSolidStrokeBg(act.getResources().getColor(
						R.color.black_c)));
		rlUpdate.setBackgroundDrawable(MaterialUtils.createSolidStrokeBg(act
				.getResources().getColor(R.color.black_c)));
		rlAbout.setBackgroundDrawable(MaterialUtils.createSolidStrokeBg(act
				.getResources().getColor(R.color.black_c)));
		
		rlMe.setOnClickListener(this);
		rlMyFriends.setOnClickListener(this);
		llMeMainInfo.setOnClickListener(this);
		rlUpdate.setOnClickListener(this);
		rlAbout.setOnClickListener(this);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		getUserInfoData();
	}

	private void getUserInfoData() {
		if(info != null){
			setData();
			return;
		}
		String url = Urls.getUserInfoUrl(BaseApplication.getLoginInfo()
				.getToken());
		Log.d(Constants.TAG,"user info url:"+url);
		act.getHttp().send(HttpRequest.HttpMethod.GET, url,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Commons.showToast(act, "网络出错");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						Log.d(Constants.TAG, "get user history data :"
								+ arg0.result);
						Gson gson = new Gson();
						info = gson.fromJson(arg0.result, UserInfo.class);
						setData();
					}

				});
	}
	
	private void setData(){
		tvUserName.setText("用户名     "+info.getMsg().getBase().getNickname());
	}
	
	int version;
	
	private void getVersionUpdate() {
		version = Commons.getAppVersion(act);
		String url = Urls.getUpdateUrl(version);
		Log.d(Constants.TAG,"update url:"+url);
		act.getHttp().send(HttpRequest.HttpMethod.GET,
				url,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Commons.showToast(act, arg1);
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String result = arg0.result;
						if(!isMsgJson(result)){
							Commons.showToast(act, "暂无新版本");
							return;
						}
						Gson gson = new Gson();
						UpdateBean updateInfo = new UpdateBean();
						updateInfo = gson.fromJson(result, UpdateBean.class);
						dealUpdateResult(updateInfo);
					}

				});
	}
	
	private boolean isMsgJson(String str){
		try {
			JSONObject json = new JSONObject(str);
			JSONObject msg = json.getJSONObject("msg");
			if(msg != null){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	private void dealUpdateResult(final UpdateBean updateInfo){
		if(updateInfo == null || updateInfo.getMsg() == null){
			return;
		}
		if(version < updateInfo.getMsg().getLastVersionCode()){
			CommonDialog dialog = new CommonDialog(act,new OnFinishInterface() {
				
				@Override
				public void onConfirm(String msg) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");    
			        Uri content_url = Uri.parse(updateInfo.getMsg().getDownload());   
			        intent.setData(content_url); 
			        startActivity(intent);
				}
				
				@Override
				public void onCancel(String msg) {
					// TODO Auto-generated method stub
					
				}
			});
			dialog.setTitle("版本更新");
			dialog.setMessage(updateInfo.getMsg().getDescription());
			dialog.show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.rl_me:
			
			break;
		case R.id.rl_my_friends:
			
			break;
		case R.id.llMeMainInfo:
			
			break;
		case R.id.rl_update:
			getVersionUpdate();
			break;
		case R.id.rl_about:
			
			break;
		}
	}

}
