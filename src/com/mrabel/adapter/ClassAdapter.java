package com.mrabel.adapter;

import java.util.List;

import com.example.androidapp.R;
import com.mrabel.entity.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ClassAdapter extends ArrayAdapter<Classes> {

	private int mResourceId;
	
	public ClassAdapter(Context context, int resource, List<Classes> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		mResourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Classes classes = getItem(position); // 获取当前项的Class实例
		View view = null;
		ViewHolder viewHolder = null;
		
		if (convertView == null){
			view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.classImage = (ImageView) view.findViewById(R.id.class_image);
			viewHolder.className = (TextView) view.findViewById(R.id.class_name);
			view.setTag(viewHolder); 	// 将ViewHolder存储在View中
		}else{
			view = convertView;
			viewHolder = (ViewHolder)view.getTag();
		}
		
		viewHolder.classImage.setImageResource(classes.getmImageId());
		viewHolder.className.setText(classes.getmClassName());
		return view;
	}
	
	class ViewHolder {
		ImageView classImage;
		TextView className;
	}

}
