package com.mrabel.entity;

import java.util.ArrayList;

import com.example.androidapp.R;

/**
 * 班级类
 * @author zzg
 *
 */
public class Classes {

	private static final String TAG = Classes.class.getSimpleName();
	
	private String mClassNum = null;
	private String mClassName = null;
	private String mCourseId = null;
	private int mStudentCount = 0;
	private int mSignInTimes = 0;
	private int mImageId = 0;
		
	public Classes(String classNum, String className, 
			String courseId, int studentCount, int signInTimes){
		mClassNum = classNum;
		mClassName = className;
		mCourseId = courseId;
		mStudentCount = studentCount;
		mSignInTimes = signInTimes;
		mImageId = R.drawable.class_image;
	}


	public String getmClassNum() {
		return mClassNum;
	}

	public void setmClassNum(String mClassNum) {
		this.mClassNum = mClassNum;
	}

	public int getmStudentCount() {
		return mStudentCount;
	}

	public void setmStudentCount(int mStudentCount) {
		this.mStudentCount = mStudentCount;
	}

	public int getmSignInTimes() {
		return mSignInTimes;
	}

	public void setmSignInTimes(int mSignInTimes) {
		this.mSignInTimes = mSignInTimes;
	}

	public String getmClassName() {
		return mClassName;
	}

	public void setmClassName(String mClassName) {
		this.mClassName = mClassName;
	}

	public String getmCourseId() {
		return mCourseId;
	}

	public void setmCourseId(String mCourseId) {
		this.mCourseId = mCourseId;
	}

	public int getmImageId() {
		return mImageId;
	}

	public void setmImageId(int mImageId) {
		this.mImageId = mImageId;
	}
}
