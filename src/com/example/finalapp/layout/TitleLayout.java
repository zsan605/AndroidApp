package com.example.finalapp.layout;

import com.example.finalapp.R;
import com.example.finalapp.R.id;
import com.example.finalapp.R.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class TitleLayout extends LinearLayout {

	private Button titleBack = null;
	private Button titleEdit = null;
	
 	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.title, this);
		
		titleBack = (Button)findViewById(R.id.title_back);
		titleEdit = (Button)findViewById(R.id.title_edit);
		
		//titleBack按钮的点击事件
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((Activity)getContext()).finish();
			}
		});
		
		//titleEdit按钮的点击事件
		titleEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
 	}

}
