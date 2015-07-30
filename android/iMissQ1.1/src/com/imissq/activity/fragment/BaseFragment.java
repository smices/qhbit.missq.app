package com.imissq.activity.fragment;

import com.ambytw.android.missq.lib.MissQLib;
import com.imissq.base.MyService;
import com.imissq.base.MyService.MissQCallBack;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment implements MissQCallBack{
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		MyService.missqCallback = this;
	}

	@Override
	public void waterCallBack(int[] water, float base) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectSuccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MissQLib getMissQLib() {
		// TODO Auto-generated method stub
		return null;
	}

}
