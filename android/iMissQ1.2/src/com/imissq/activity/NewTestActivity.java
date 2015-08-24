package com.imissq.activity;

import com.imissq.R;
import com.imissq.activity.fragment.TPartTestFragment;
import com.imissq.activity.fragment.UPartTestFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

public class NewTestActivity extends BaseActivity implements View.OnClickListener{
	
	public static final String INDEX_FLAG = "index_flag";
	
	@ViewInject(R.id.tvTpart)
	TextView tvTpart;
	@ViewInject(R.id.tvUpart)
	TextView tvUpart;
	
	Fragment[] frgs = {TPartTestFragment.getInstance(),UPartTestFragment.getInstance()};
	
	private int index = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.act_new_test);
		ViewUtils.inject(this);
		
		tvTpart.setOnClickListener(this);
		tvUpart.setOnClickListener(this);
		
		index = getIntent().getIntExtra(INDEX_FLAG, 0);
		
		init();
	}
	
	private void init(){
		replaceFragment(index);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.tvTpart:
			replaceFragment(0);
			break;
		case R.id.tvUpart:
			replaceFragment(1);
			break;
		}
	}
	
	private void replaceFragment(int index){
		FragmentManager frgManager = getSupportFragmentManager();
		FragmentTransaction ft = frgManager.beginTransaction();
		ft.replace(R.id.content, frgs[index]);
		ft.commitAllowingStateLoss();
		if(index == 0){
			tvTpart.setBackgroundResource(R.drawable.part_press_shape);
			tvUpart.setBackgroundColor(getResources().getColor(R.color.transparent));
		}else{
			tvUpart.setBackgroundResource(R.drawable.part_press_shape);
			tvTpart.setBackgroundColor(getResources().getColor(R.color.transparent));
		}
	}

}
