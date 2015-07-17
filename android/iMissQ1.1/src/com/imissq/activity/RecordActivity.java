package com.imissq.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.imissq.base.BaseApplication;
import com.imissq.http.Urls;

public class RecordActivity extends BaseActivity{
	
	WebView webView;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		webView = new WebView(this);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new myWebViewClient());
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				if(newProgress == 100){
					dismissLoadDialog();
				}
			}
		});
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		setContentView(webView);
		getData();
	}
	
	private void getData() {
		showLoadDialog();
		String url = Urls.getRecordUrl(BaseApplication.getLoginInfo()
				.getToken());
		webView.loadUrl(url);
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
