package com.mrabel.entity;

import com.example.androidapp.R;

public class SignIn {

	private static final String TAG = SignIn.class.getSimpleName();
	
	private String mSignInTableId = null;
	private Student mStudent = null;
	private String mTime = null;
	private String mSign = null;
	private Boolean isSign = false;
	private int mImageId;
	
	public SignIn(String signInTableId, Student student, String time, int sign){
		
		mSignInTableId = signInTableId;
		mStudent = student;
		mTime = time;
		if (sign == 0){
			isSign = false;
			mSign = "未签到";
		}else {
			isSign = true;
			mSign = "已签到";
		}
		mImageId = R.drawable.ic_lishi;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public int getmImageId() {
		return mImageId;
	}

	public void setmImageId(int mImageId) {
		this.mImageId = mImageId;
	}

	public String getmSignInTableId() {
		return mSignInTableId;
	}

	public void setmSignInTableId(String mSignInTableId) {
		this.mSignInTableId = mSignInTableId;
	}

	public Boolean getIsSign() {
		return isSign;
	}

	public void setIsSign(Boolean isSign) {
		this.isSign = isSign;
	}

	public Student getmStudent() {
		return mStudent;
	}

	public void setmStudent(Student mStudent) {
		this.mStudent = mStudent;
	}

	public String getmSign() {
		return mSign;
	}

	public void setmSign(String mSign) {
		this.mSign = mSign;
	}
	
}
