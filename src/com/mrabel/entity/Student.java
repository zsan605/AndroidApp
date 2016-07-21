package com.mrabel.entity;

import com.example.androidapp.R;

/**
 * 学生实体类
 * @author zzg
 *
 */
public class Student {

	private static final String TAG = Student.class.getSimpleName();
	
	private String mNum = null;
	private String mName = null;
	private String mSex = null;
	private String mClassName = null;
	private String mDepartment = null;
	private int mImageId = 0;

	public Student(String num, String name, String sex, String className, String department) {
		// TODO Auto-generated constructor stub
		mName = name;
		mNum  = num;
		mSex = sex;
		mClassName = className;
		mDepartment = department;
		mImageId = R.drawable.student_image;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmNum() {
		return mNum;
	}

	public void setmNum(String mNum) {
		this.mNum = mNum;
	}

	public String getmSex() {
		return mSex;
	}

	public void setmSex(String mSex) {
		this.mSex = mSex;
	}

	public String getmClassName() {
		return mClassName;
	}

	public void setmClassName(String mClassName) {
		this.mClassName = mClassName;
	}

	public int getmImageId() {
		return mImageId;
	}

	public void setmImageId(int mImageId) {
		this.mImageId = mImageId;
	}

	public String getmDepartment() {
		return mDepartment;
	}

	public void setmDepartment(String mDepartment) {
		this.mDepartment = mDepartment;
	}
	
}
