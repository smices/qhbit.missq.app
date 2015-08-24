package com.imissq.views;

import com.imissq.R;
import com.imissq.config.Commons;
import com.imissq.utils.MaterialUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TagView extends RelativeLayout {

	private TextView tvTag;
	private ImageView ivTag;

	private Context context;

	public TagView(Context context) {
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public TagView(Context context, AttributeSet attr) {
		this(context, attr, 0);
	}

	@SuppressLint("NewApi")
	public TagView(Context context, AttributeSet attributeSet, int defStyle) {
		super(context, attributeSet, defStyle);
		this.context = context;
		init();
	}

	private void init() {
		// tvTag = findViewById(R.id.)
		View view = LayoutInflater.from(context).inflate(R.layout.tab_unit,
				null);
		tvTag = (TextView) view.findViewById(R.id.tv_tab);
		ivTag = (ImageView) view.findViewById(R.id.iv_tab);
		tvTag.setTextColor(MaterialUtils.createTabItemTextColor(Color.BLACK, Color.WHITE));
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		
		addView(view,params);
	}
	
	public void setIconBg(int resId){
		ivTag.setBackgroundResource(resId);
	}
	
	public void setText(String text){
		tvTag.setText(text);
	}

	@Override
	public void setSelected(boolean selected) {
		// TODO Auto-generated method stub
		super.setSelected(selected);
		tvTag.setSelected(selected);
		ivTag.setSelected(selected);
	}

}
