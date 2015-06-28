package com.imissq.activity.fragment;

import com.imissq.R;
import com.imissq.utils.MaterialUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MeFragment extends BaseFragment {

	private Activity act;
	
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

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		act = activity;
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
	private void initView(){
		rlMe.setBackgroundDrawable(MaterialUtils.createSolidStrokeBg(act
				.getResources().getColor(R.color.black_c)));
		rlMyFriends.setBackgroundDrawable(MaterialUtils.createSolidStrokeBg(act
				.getResources().getColor(R.color.black_c)));
		llMeMainInfo.setBackgroundDrawable(MaterialUtils.createSolidStrokeBg(act
				.getResources().getColor(R.color.black_c)));
		rlUpdate.setBackgroundDrawable(MaterialUtils.createSolidStrokeBg(act
				.getResources().getColor(R.color.black_c)));
		rlAbout.setBackgroundDrawable(MaterialUtils.createSolidStrokeBg(act
				.getResources().getColor(R.color.black_c)));
	}
	
}
