package com.imissq.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
import com.imissq.utils.MaterialUtils;
import com.imissq.views.TagView;
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
		TagView tagVew = new TagView(this);
		
		if (tag.equals(TestFragment.class.getName())) {
			tagVew.setText("测试");
			tagVew.setIconBg(R.drawable.tag_test_selector);
		}else if(tag.equals(TopicFragment.class.getName())) {
			tagVew.setText("话题");
			tagVew.setIconBg(R.drawable.tag_topic_selector);
		}else if(tag.equals(ProductFragment.class.getName())) {
			tagVew.setText("产品");
			tagVew.setIconBg(R.drawable.tag_product_selector);
		}else if(tag.equals(MeFragment.class.getName())) {
			tagVew.setText("我");
			tagVew.setIconBg(R.drawable.tag_me_selector);
		}
		
		return tagVew;
	}

}
