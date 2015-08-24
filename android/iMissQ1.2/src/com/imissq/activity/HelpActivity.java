package com.imissq.activity;

import com.imissq.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HelpActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.act_help);
		
		ImageView iv = (ImageView) findViewById(R.id.iv_help);
		iv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
