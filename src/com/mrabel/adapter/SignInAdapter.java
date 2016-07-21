package com.mrabel.adapter;

import java.util.List;

import com.example.androidapp.R;
import com.mrabel.adapter.HistoryInfAdapter.ViewHolder;
import com.mrabel.entity.HistoryInf;
import com.mrabel.entity.SignIn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SignInAdapter extends ArrayAdapter<SignIn> {

	private int mResourceId;
	
	public SignInAdapter(Context context, int resource, List<SignIn> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		mResourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		SignIn signIn = getItem(position); // 获取当前项的Class实例
		View view = null;
		ViewHolder viewHolder = null;
		
		if (convertView == null){
			view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.signInImage = (ImageView) view.findViewById(R.id.sign_in_image);
			viewHolder.signInTime = (TextView) view.findViewById(R.id.sign_in_time);
			viewHolder.signName = (TextView) view.findViewById(R.id.sign_in_name);
			viewHolder.signSign = (TextView) view.findViewById(R.id.sign_in_sign);
			view.setTag(viewHolder); 	// 将ViewHolder存储在View中
		}else{
			view = convertView;
			viewHolder = (ViewHolder)view.getTag();
		}
		
		viewHolder.signInImage.setImageResource(signIn.getmImageId());
		viewHolder.signInTime.setText(signIn.getmTime());
		viewHolder.signName.setText(signIn.getmStudent().getmName());
		viewHolder.signSign.setText(signIn.getmSign());
		return view;
	}
	
	class ViewHolder {
		ImageView signInImage;
		TextView signName;
		TextView signInTime;
		TextView signSign;
	}

}
