package com.mrabel.entity;

import com.example.androidapp.R;

public class HistoryInf {

	private String mId = null;
	private String mClassNum = null;
	private String mBeginTime = null;
	private String mEndTime = null;
	private String mDate = null;
	private int mImageId;
	
	public HistoryInf(String id, String classNum, 
			String beginTime, String endTime, String date){
		
		mId = id;
		mClassNum = classNum;
		mBeginTime = beginTime;
		mEndTime = endTime;
		mDate = date;
		mImageId = R.drawable.history_info;
	}

	public String getmDate() {
		return mDate;
	}

	public void setmDate(String mDate) {
		this.mDate = mDate;
	}

	public int getmImageId() {
		return mImageId;
	}

	public void setmImageId(int mImageId) {
		this.mImageId = mImageId;
	}


	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getmBeginTime() {
		return mBeginTime;
	}

	public void setmBeginTime(String mBeginTime) {
		this.mBeginTime = mBeginTime;
	}

	public String getmClassNum() {
		return mClassNum;
	}

	public void setmClassNum(String mClassNum) {
		this.mClassNum = mClassNum;
	}

	public String getmEndTime() {
		return mEndTime;
	}

	public void setmEndTime(String mEndTime) {
		this.mEndTime = mEndTime;
	}
}
