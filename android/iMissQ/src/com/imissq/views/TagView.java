package com.imissq.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TagView extends LinearLayout{
	
	private TextView tvTag;
	private ImageView ivTag;

	public TagView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public TagView(Context context,AttributeSet attr){
		super(context,attr);
	}
	
	@SuppressLint("NewApi")
	public TagView(Context context, AttributeSet attributeSet, int defStyle){
		super(context, attributeSet, defStyle);
	}
	
	private void init(){
		//tvTag = findViewById(R.id.)
	}

}
