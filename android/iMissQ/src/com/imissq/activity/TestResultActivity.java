package com.imissq.activity;

import com.imissq.R;
import com.imissq.config.Commons;
import com.imissq.model.MisqDataBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.widget.TextView;

public class TestResultActivity extends BaseActivity{
	
	private MisqDataBean data;
	
	@ViewInject(R.id.tvOil)
	TextView tvOil;
	@ViewInject(R.id.tvWater)
	TextView tvWater;
	@ViewInject(R.id.tvTotal)
	TextView tvTotal;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.act_test_result);
		ViewUtils.inject(this);
		
		Bundle b = getIntent().getExtras();
		if(b != null){
			data = (MisqDataBean) b.getSerializable("key");
		}
		if(data == null){
			Commons.showToast(this, "数据为空");
			return;
		}
		
		tvOil.setText("肌肤油值： "+data.getOil()+"");
		tvWater.setText("肌肤含水量： "+data.getBase()+"");
		tvTotal.setText(data.getOil()+data.getAge()+"");
	}

}
