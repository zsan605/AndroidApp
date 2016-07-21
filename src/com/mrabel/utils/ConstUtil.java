package com.mrabel.utils;

public class ConstUtil {

	public static final String API_KEY = "4f112fc58269b5bc9682ffe788efee84";
	public static final String API_SECRET = "kXMbh1ZL-9xayYfvlPMWbq4UIQ4uv7Oz";
	public static final int MSG_FACE_TRAIN = 1;
	public static final int MSG_FACE_DETECT = 2;
	public static final int MSG_FACE_IDENT = 3;
	
	// activity request code
	public static final int CAPTURE_BROWSECLASS = 1;
	
	// activity result code
	public static final int RESULT_BROWSECLASS = 1;
	
	
	// sql语句
	public static final String CREATE_STUDENT = "create table student(" +
			"num varchar(12) primary key not null, " +
			"name varchar(12) not null, " +
			"sex varchar(4) not null, " +
			"class_name varchar(12) not null, " +
			"department varchar(24) not null);";
	public static final String CREATE_TEACHER = "create table teacher(" +
			"num varchar(12) primary key not null, " +
			"name varchar(12) not null, " +
			"sex varchar(4) not null, " +
			"department varchar(24) not null);";
	public static final String CREATE_COURSE = "create table course(" +
			"num varchar(12) primary key not null, " +
			"name varchar(12) not null, " +
			"times integer not null);";
	public static final String CREATE_CLASSES = "create table classes(" +
			"num varchar(32) primary key not null, " +
			"class_name varchar(64) not null, " +
			"course_num varchar(12) not null, " +
			"student_count integer not null, " +
			"sign_in_times integer not null);";
	public static final String CREATE_CLASS_STUDENT = "create table classes_student(" +
			"id integer primary key autoincrement, " +
			"student_num varchar(12) not null, " +
			"class_num varchar(12) not null);";
	public static final String CREATE_SIGN_IN = "create table sign_in(" +
			"id integer primary key autoincrement, " +
			"sign_in_statistic_id varchar(32) not null, " +
			"student_num varchar(12) not null, " +
			"time varchar(24) not null, " +
			"sign int not null);";
	public static final String CREATE_SIGN_IN_STATISTICS = "create table sign_in_statistic(" +
			"id varchar(32) primary key not null, " +
			"class_num varchar(32) not null, " +
			"begin_time varchar(24) not null, " +
			"end_time varchar(24) not null, " +
			"date varchar(24) not null);";
	
}
