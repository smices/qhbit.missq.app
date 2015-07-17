package com.imissq.activity.fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.imissq.R;
import com.imissq.activity.BaseActivity;
import com.imissq.activity.TestActivity;
import com.imissq.base.AppMaterial;
import com.imissq.base.BaseApplication;
import com.imissq.config.Commons;
import com.imissq.config.Constants;
import com.imissq.http.Urls;
import com.imissq.model.WeatherBean;
import com.imissq.model.WeatherBean.WeatherData;
import com.imissq.utils.MaterialUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TestFragment extends BaseFragment implements View.OnClickListener{

	private BaseActivity act;

	@ViewInject(R.id.tvWenDu)
	TextView tvWenDu;
	@ViewInject(R.id.tvCity)
	TextView tvCity;
	@ViewInject(R.id.tvHighstWenDu)
	TextView tvHighstWenDu;
	@ViewInject(R.id.tvWater)
	TextView tvWater;
	@ViewInject(R.id.tvWind)
	TextView tvWind;
	@ViewInject(R.id.ivTest)
	ImageView ivTest;
	@ViewInject(R.id.ivWeatherBg)
	ImageView ivWeatherBg;
	// 测试数据部分
	@ViewInject(R.id.rlTodayTest)
	RelativeLayout rlTodayTest;
	@ViewInject(R.id.llHistoryInfo)
	LinearLayout llHistoryInfo;
	@ViewInject(R.id.rlTPart)
	RelativeLayout rlTPart;
	@ViewInject(R.id.rlUPart)
	RelativeLayout rlUPart;
	@ViewInject(R.id.rlHPart)
	RelativeLayout rlHPart;
	@ViewInject(R.id.rlFPart)
	RelativeLayout rlFPart;

	@ViewInject(R.id.tvTPartTest)
	TextView tvTPart;
	@ViewInject(R.id.tvUPartTest)
	TextView tvUPart;
	@ViewInject(R.id.tvHPartTest)
	TextView tvHPart;
	@ViewInject(R.id.tvFPartTest)
	TextView tvFPart;

	private WeatherBean weatherBean;

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
		View view = inflater.inflate(R.layout.frg_test, null);
		ViewUtils.inject(this, view);
		initView();
		return view;
	}
	
	

	@SuppressWarnings("deprecation")
	private void initView() {
		rlTodayTest.setBackgroundDrawable(MaterialUtils.createSolidStrokeBg(act
				.getResources().getColor(R.color.black_c)));
		llHistoryInfo.setBackgroundDrawable(MaterialUtils
				.createSolidStrokeBg(act.getResources().getColor(
						R.color.black_c)));

		tvTPart.setBackgroundDrawable(AppMaterial.BUTTON_BG_SOLID_CORNER(act
				.getResources().getColor(R.color.bluegreen), act.getResources()
				.getColor(R.color.white)));
		tvUPart.setBackgroundDrawable(AppMaterial.BUTTON_BG_SOLID_CORNER(act
				.getResources().getColor(R.color.orange), act.getResources()
				.getColor(R.color.white)));
		tvHPart.setBackgroundDrawable(AppMaterial.BUTTON_BG_SOLID_CORNER(act
				.getResources().getColor(R.color.orange), act.getResources()
				.getColor(R.color.white)));
		tvFPart.setBackgroundDrawable(AppMaterial.BUTTON_BG_SOLID_CORNER(act
				.getResources().getColor(R.color.orange), act.getResources()
				.getColor(R.color.white)));
		
		tvTPart.setOnClickListener(this);
		tvUPart.setOnClickListener(this);
		tvHPart.setOnClickListener(this);
		tvFPart.setOnClickListener(this);
		ivTest.setOnClickListener(this);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		getData();
	}

	private void getData() {
		if (weatherBean != null) {
			setWeatherData();
			return;
		}
		act.showLoadDialog();
		String url = Urls.getWeatherUrl();
		url = url + "?location=202.96.134.133";
		Log.d(Constants.TAG, "weather url :" + url);
		act.getHttp().send(HttpRequest.HttpMethod.GET, url,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						act.dismissLoadDialog();
						Commons.showToast(act, "网络出错");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						act.dismissLoadDialog();
						dealWeatherResult(arg0.result);
					}

				});
		getUserHistoryData();
	}
	
	private void getUserHistoryData(){
		String uid = "1";
		String record = "7";
		String timelimit = "7";
		String uploadUrl = Urls.getTestUploadUrl(BaseApplication.getLoginInfo().getToken());
		uploadUrl = uploadUrl+"&uid="+uid+"&record="+record+"&timelimit="+timelimit;
		Log.d(Constants.TAG,"upload url:"+uploadUrl);
		act.getHttp().send(HttpRequest.HttpMethod.GET, uploadUrl, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Commons.showToast(act, "网络出错");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						Log.d(Constants.TAG,"get user history data :"+arg0.result);
					}

				});
	}

	private void dealWeatherResult(String result) {
		if (TextUtils.isEmpty(result)) {
			Commons.showToast(act, "网络出错");
			return;
		}
		try {
			JSONObject resultJson = new JSONObject(result);
			int error = resultJson.getInt("error");
			if (error == 0) {
				JSONArray array = resultJson.getJSONArray("results");
				if (array != null && array.length() > 0) {
					JSONObject json = array.getJSONObject(0);
					Gson gson = new Gson();
					weatherBean = gson.fromJson(json.toString(),
							WeatherBean.class);
					setWeatherData();
				} else {
					Commons.showToast(act, "数据出错");
					return;
				}
			} else {
				Commons.showToast(act, "数据出错");
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Commons.showToast(act, "数据出错");
			return;
		}
	}

	private void setWeatherData() {
		if (weatherBean == null) {
			Log.d(Constants.TAG, "data error, wawther bean is null");
			return;
		}
		tvCity.setText(weatherBean.getCurrentCity());
		tvWater.setText("PM25：" + weatherBean.getPm25());
		if (weatherBean.getWeather_data() != null
				&& weatherBean.getWeather_data().size() > 0) {
			WeatherData timeData = weatherBean.getWeather_data().get(0);
			String hightWD = timeData.getTemperature();
			hightWD = hightWD.substring(0, 2);
			tvHighstWenDu.setText("最高：" + hightWD);
			String wind = timeData.getWeather() + timeData.getWind();
			tvWind.setText(wind);
			String url = timeData.getDayPictureUrl();
			act.getBitmapUtil().display(ivWeatherBg, url);
		} else {
			Log.d(Constants.TAG, "data error, wawther data is null");

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(act,TestActivity.class);
		startActivity(intent);
		switch(v.getId()){
		case R.id.tvTPartTest:
			
			break;
		case R.id.tvUPartTest:
			
			break;
		case R.id.tvHPartTest:
			
			break;
		case R.id.tvFPartTest:
			
			break;
		}
	}

}
