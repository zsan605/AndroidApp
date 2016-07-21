package com.mrabel.adapter;

import java.util.List;

import com.example.androidapp.R;
import com.mrabel.adapter.StudentAdapter.ViewHolder;
import com.mrabel.entity.HistoryInf;
import com.mrabel.entity.SignIn;
import com.mrabel.entity.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryInfAdapter extends ArrayAdapter<HistoryInf> {

	private int mResourceId;
	
	public HistoryInfAdapter(Context context, int resource, List<HistoryInf> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		mResourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HistoryInf historyInf = getItem(position); // 获取当前项的Class实例
		View view = null;
		ViewHolder viewHolder = null;
		
		if (convertView == null){
			view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.historyInfImage = (ImageView) view.findViewById(R.id.history_inf_image);
			viewHolder.historyInfDate = (TextView) view.findViewById(R.id.history_inf_date);
			view.setTag(viewHolder); 	// 将ViewHolder存储在View中
		}else{
			view = convertView;
			viewHolder = (ViewHolder)view.getTag();
		}
		
		viewHolder.historyInfImage.setImageResource(historyInf.getmImageId());
		viewHolder.historyInfDate.setText(historyInf.getmDate());
		return view;
	}
	
	class ViewHolder {
		ImageView historyInfImage;
		TextView historyInfDate;
	}
	
	
	

}
