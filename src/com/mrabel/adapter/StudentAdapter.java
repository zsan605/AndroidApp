package com.mrabel.adapter;

import java.util.List;

import com.example.androidapp.R;
import com.mrabel.entity.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentAdapter extends ArrayAdapter<Student> {

	private int mResourceId;
	
	public StudentAdapter(Context context, int resource, List<Student> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		mResourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Student student = getItem(position); // 获取当前项的Class实例
		View view = null;
		ViewHolder viewHolder = null;
		
		if (convertView == null){
			view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.studentImage = (ImageView) view.findViewById(R.id.student_image);
			viewHolder.studentName = (TextView) view.findViewById(R.id.student_name);
			view.setTag(viewHolder); 	// 将ViewHolder存储在View中
		}else{
			view = convertView;
			viewHolder = (ViewHolder)view.getTag();
		}
		
		viewHolder.studentImage.setImageResource(student.getmImageId());
		viewHolder.studentName.setText(student.getmName());
		return view;
	}
	
	class ViewHolder {
		ImageView studentImage;
		TextView studentName;
	}

}
