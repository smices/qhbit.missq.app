package com.imissq.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ambytw.android.missq.lib.MissQLib;
import com.imissq.R;
import com.imissq.base.MyService;
import com.imissq.base.MyService.MissQCallBack;
import com.imissq.config.Commons;
import com.imissq.views.CommonLoadDialog;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

public class BaseActivity extends FragmentActivity implements MissQCallBack{
	
	public static boolean IS_STARTED = false;

	
	
	private Dialog mLoadDialog;
	public static final int PRASSAGE_DIALOG = 0x01;
	
	private static HttpUtils http;
	
	private static BitmapUtils bitmapUtils;
	
	private boolean reConnectFlag = true;

	
	
	public HttpUtils getHttp(){
		if(http == null ){
			http = new HttpUtils();
		}
		return http;
	}


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		bitmapUtils = new BitmapUtils(this);
        MyService.missqCallback = this;
	}
	
	public BitmapUtils getBitmapUtil(){
		return bitmapUtils;
	}

	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
        case PRASSAGE_DIALOG:
            mLoadDialog = new CommonLoadDialog(this, R.style.dialogTheme);
            return mLoadDialog;
        }
        return super.onCreateDialog(id);
	}
	
	public void dismissLoadDialog() {
        if (mLoadDialog != null && mLoadDialog.isShowing()) {
            dismissDialog(PRASSAGE_DIALOG);
        }
    }
	
	public void showLoadDialog() {
        showDialog(PRASSAGE_DIALOG);
    }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//mQLib.stop();
		//mQLib = null;
	}
	
	@Override
	public void waterCallBack(int[] water,float base) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void connectFailed() {
		// TODO Auto-generated method stub
		Commons.showToast(this, "连接失败,请确认接入检测器");
	}


	@Override
	public void connectSuccess() {
		// TODO Auto-generated method stub
		//Commons.showToast(this, "连接成功");
	}


	@Override
	public MissQLib getMissQLib() {
		// TODO Auto-generated method stub
		return null;
	}

}
