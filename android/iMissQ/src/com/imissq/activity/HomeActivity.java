package com.imissq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.imissq.R;
import com.imissq.activity.fragment.MeFragment;
import com.imissq.activity.fragment.ProductFragment;
import com.imissq.activity.fragment.TestFragment;
import com.imissq.activity.fragment.TopicFragment;
import com.imissq.base.MyService;
import com.imissq.controller.RadioTabManager;
import com.imissq.controller.RadioTabManager.BuildViewFactory;
import com.imissq.controller.RadioTabManager.TabChangeListener;
import com.imissq.controller.RadioTabManager.TabInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HomeActivity extends BaseActivity implements TabChangeListener, BuildViewFactory{
	@ViewInject(R.id.tab_contain)
	private LinearLayout mTabLayout;
	@ViewInject(R.id.main_bottom_layout)
	private FrameLayout mBottomLayut;
	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;
	
	private RadioTabManager mTabManager;

	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.act_home);
		ViewUtils.inject(this);
		
		mTabManager = new RadioTabManager(this, getSupportFragmentManager(), radioGroup, R.id.content, this);
        mTabManager.setTabChangeListener(this);
        //add tab
        mTabManager.addTab(TestFragment.class.getName(), TestFragment.class, null);
        mTabManager.addTab(TopicFragment.class.getName(), TopicFragment.class, null);
        mTabManager.addTab(ProductFragment.class.getName(), ProductFragment.class, null);
        mTabManager.addTab(MeFragment.class.getName(), MeFragment.class, null);
        
        mTabManager.setCurrFragmentByTag(TestFragment.class.getName());
        
        startService(new Intent(this,MyService.class));
        
	}


	@Override
	public void onChange(TabInfo mLastTab) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View tabView(String tag, ViewGroup parent) {
		// TODO Auto-generated method stub
//		View tabView = LayoutInflater.from(this).inflate(R.layout.tab_unit, null);
//		TextView tvTab = (TextView)tabView.findViewById(R.id.tv_tab);
//		ImageView ivTab = (ImageView)tabView.findViewById(R.id.iv_tab);
//		if (tag.equals(TestFragment.class.getName())) {
//			tvTab.setText("测试");
//			ivTab.setBackgroundResource(R.drawable.tab_test);
//		}else if(tag.equals(TopicFragment.class.getName())) {
//			tvTab.setText("话题");
//			ivTab.setBackgroundResource(R.drawable.tab_topic);
//		}else if(tag.equals(ProductFragment.class.getName())) {
//			tvTab.setText("产品");
//			ivTab.setBackgroundResource(R.drawable.tab_product);
//		}else if(tag.equals(MeFragment.class.getName())) {
//			tvTab.setText("我");
//			ivTab.setBackgroundResource(R.drawable.tab_me);
//		}
		RadioButton tab = (RadioButton) LayoutInflater.from(this).inflate(R.layout.tab_text, null);
		if (tag.equals(TestFragment.class.getName())) {
			tab.setText("测试");
		}else if(tag.equals(TopicFragment.class.getName())) {
			tab.setText("话题");
		}else if(tag.equals(ProductFragment.class.getName())) {
			tab.setText("产品");
		}else if(tag.equals(MeFragment.class.getName())) {
			tab.setText("我");
		}
		
		return tab;
	}

}
