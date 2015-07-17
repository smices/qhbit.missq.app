package com.imissq.activity.fragment;

import com.imissq.activity.BaseActivity;
import com.imissq.activity.fragment.ProductFragment.myWebViewClient;
import com.imissq.base.BaseApplication;
import com.imissq.config.Commons;
import com.imissq.config.Constants;
import com.imissq.http.Urls;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class TopicFragment extends BaseFragment {
	private BaseActivity act;

	private WebView webView;

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
		webView = new WebView(act);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new myWebViewClient());
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				if(newProgress == 100){
					act.dismissLoadDialog();
				}
			}
		});
		return webView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		getData();
	}

	private void getData() {
		act.showLoadDialog();
		String url = Urls
				.getTopicUrl(BaseApplication.getLoginInfo().getToken());
		Log.d(Constants.TAG, "topic url :" + url);
		webView.loadUrl(url);//.loadData(url);
//		
//		act.getHttp().send(HttpRequest.HttpMethod.GET, url,
//				new RequestCallBack<String>() {
//
//					@Override
//					public void onFailure(HttpException arg0, String arg1) {
//						// TODO Auto-generated method stub
//						act.dismissLoadDialog();
//						Commons.showToast(act, "网络出错");
//					}
//
//					@Override
//					public void onSuccess(ResponseInfo<String> arg0) {
//						// TODO Auto-generated method stub
//						act.dismissLoadDialog();
//						Log.d(Constants.TAG, "topic result:" + arg0.result);
//						webView.loadData(arg0.result, "text/html", "utf-8");
//					}
//
//				});

	}

	class myWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);
			return true;
		}

	}
}
