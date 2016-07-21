package com.mrabel.helper;

import java.nio.channels.SelectableChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.R.integer;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.MediaStore.Video;
import android.widget.Toast;

import com.mrabel.entity.Classes;
import com.mrabel.entity.HistoryInf;
import com.mrabel.entity.SignIn;
import com.mrabel.entity.Student;
import com.mrabel.utils.ConstUtil;


/**
 * 用于对数据库的操作
 * @author zzg
 *
 */
public class DatabaseManager extends SQLiteOpenHelper {

	// 定义常量
	private static final String TAG = DatabaseManager.class.getSimpleName();
	
	// 定义成员变量
	private Context mContext = null;
	private static DatabaseManager mDatabaseManager = null;
	private SQLiteDatabase mSqLiteDatabase = null;
	
	/**
	 * 构造函数
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DatabaseManager(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	
	public static DatabaseManager getInstant(Context context, String name, 
			CursorFactory factory, int version){
		
		if (mDatabaseManager == null){
			mDatabaseManager = new DatabaseManager(context, name, factory, version);
		}
		return mDatabaseManager;
	}

	public void openDatabase(){
		mSqLiteDatabase = mDatabaseManager.getWritableDatabase();
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(ConstUtil.CREATE_STUDENT);
		db.execSQL(ConstUtil.CREATE_COURSE);
		db.execSQL(ConstUtil.CREATE_TEACHER);
		db.execSQL(ConstUtil.CREATE_CLASSES);
		db.execSQL(ConstUtil.CREATE_CLASS_STUDENT);
		db.execSQL(ConstUtil.CREATE_SIGN_IN);
		db.execSQL(ConstUtil.CREATE_SIGN_IN_STATISTICS);
		Toast.makeText(mContext, "create success", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 添加课程
	 * @param classNum
	 * @param className
	 * @param times
	 */
	public void insertCourse(String courseNum, String courseName, int times){
		ContentValues values = new ContentValues();            		
		values.put("num", courseNum);
		values.put("name", courseName);
		values.put("times", times);
		mSqLiteDatabase.insert("course", null, values);
	}

	/**
	 * 添加学生
	 * @param studentNum
	 * @param studentName
	 * @param sex
	 * @param className
	 * @param department
	 */
	public void insertStudent(String studentNum, String studentName, 
			String sex, String className, String department){
		ContentValues values = new ContentValues();   
		values = new ContentValues();
		values.put("num", studentNum);
		values.put("name", studentName);
		values.put("sex", sex);
		values.put("class_name", className);
		values.put("department", department);
		mSqLiteDatabase.insert("student", null, values);
	}
	
	/**
	 * 添加班级
	 * @param className
	 * @param courseNum
	 * @param studentCount
	 * @param signInTimes
	 */
	public void insertClass(String className, String courseNum, 
			int studentCount, int signInTimes){
		
		UUID uuid = UUID.randomUUID();
		ContentValues values = new ContentValues();
		values.put("num", uuid.toString().replace("-", ""));
		values.put("class_name", className);
		values.put("course_num", courseNum);
		values.put("student_count", studentCount);
		values.put("sign_in_times", signInTimes);
		mSqLiteDatabase.insert("classes", null, values);
	}
	
	/**
	 * 向学生班级对应表中插入数据
	 * @param studentNum
	 * @param classNum
	 */
	public void insertClassesToStudent(String studentNum, String classNum){
		ContentValues values = new ContentValues();
		values.put("student_num", studentNum);
		values.put("class_num", classNum);
		mSqLiteDatabase.insert("classes_student", null, values);
	}
	
	/**
	 * 更具班级名称获得班级号
	 * @param className
	 * @return
	 */
	public String getClassNum(String className){
		Cursor cursor = mSqLiteDatabase.rawQuery("select * from classes where class_name = ?", 
				new String[] {className});
		String num = null;
		if (cursor.moveToFirst()){
			do {
				num = cursor.getString(cursor.getColumnIndex("num"));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return num;
	}
	
	/**
	 * 返回所有的班级
	 * @return
	 */
	public List<Classes> getClasses(){
		List<Classes> classList = new ArrayList<Classes>();
		Cursor cursor = mSqLiteDatabase.rawQuery("select * from classes", null);
		if (cursor.moveToFirst()) {
			do {
				// 遍历Cursor对象，取出数据并打印
				String classNum = cursor.getString(cursor.getColumnIndex("num"));
				String className = cursor.getString(cursor.getColumnIndex("class_name"));
				String courseNum = cursor.getString(cursor.getColumnIndex("course_num"));
				int studentCount = cursor.getInt(cursor.getColumnIndex("student_count"));
				int signInTimes = cursor.getInt(cursor.getColumnIndex("sign_in_times"));
				Classes classes = new Classes(
						classNum, className, courseNum, studentCount, signInTimes);
				classList.add(classes);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return classList;
	}
	
	/**
	 * 根据班级号获得学生
	 * @param classNum
	 * @return
	 */
	public ArrayList<Student> getStudentByClassNum(String classNum){
		
		ArrayList<Student> list = new ArrayList<Student>();
		
		String sql = "select * " +
				"from student " +
				"where num in " +
				"(select student_num " +
				"from classes_student " +
				"where class_num = ?)";
		
		Cursor cursor = mSqLiteDatabase.rawQuery(sql,new String[] {classNum});
		
		if (cursor.moveToFirst()) {
			do {
				// 遍历Cursor对象，取出数据并打印
				String studentNum = cursor.getString(cursor.getColumnIndex("num"));
				String studentName = cursor.getString(cursor.getColumnIndex("name"));
				String studentSex = cursor.getString(cursor.getColumnIndex("sex"));
				String studentClassName = cursor.getString(cursor.getColumnIndex("class_name"));
				String department = cursor.getString(cursor.getColumnIndex("department"));
				Student student = new Student(studentNum, 
						studentName, studentSex, studentClassName, department);
				list.add(student);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	
	public Student getStudentByNum(String studentNum){
		
		String sql = "select * " +
				"from student " +
				"where num = ? ";
		
		Student student = null;
		Cursor cursor = mSqLiteDatabase.rawQuery(sql, new String[] {studentNum});
		if (cursor.moveToFirst()) {
				// 遍历Cursor对象，取出数据并打印
			String studentName = cursor.getString(cursor.getColumnIndex("name"));
			String className = cursor.getString(cursor.getColumnIndex("class_name"));
			String sex = cursor.getString(cursor.getColumnIndex("sex"));
			String department = cursor.getString(cursor.getColumnIndex("department"));
			student = new Student(studentNum, studentName, sex, className, department);
		}
		cursor.close();
		return student;
	}
	
	/**
	 * 插入签到信息 
	 * @param signInStatisticId
	 * @param studentNum
	 * @param time
	 * @param isSign
	 */
	public void insertSignIn(String signInStatisticId, 
			String studentNum, String time, int isSign){
		
		ContentValues values = new ContentValues();

		values.put("sign_in_statistic_id", signInStatisticId);
		values.put("student_num", studentNum);
		values.put("time", time);
		values.put("sign", isSign);
		
		mSqLiteDatabase.insert("sign_in", null, values);
	}
	
	/**
	 * 获得签到的信息
	 * @param classNum
	 * @return
	 */
	public List<HistoryInf> getHistoryInf(){
		
		String sql = "select * " +
				"from sign_in_statistic " +
				"group by date";
		
		List<HistoryInf> historyInfList = new ArrayList<HistoryInf>();
		
		Cursor cursor = mSqLiteDatabase.rawQuery(sql, null);
		
		if (cursor.moveToFirst()) {
			do {
				// 遍历Cursor对象，取出数据并打印
				String id = cursor.getString(cursor.getColumnIndex("id"));
				String classNum = cursor.getString(cursor.getColumnIndex("class_num"));
				String beginTime = cursor.getString(cursor.getColumnIndex("begin_time"));
				String endTime = cursor.getString(cursor.getColumnIndex("end_time"));
				String date =  cursor.getString(cursor.getColumnIndex("date"));
				HistoryInf historyInf = new HistoryInf(id, classNum, beginTime, endTime, date);
				historyInfList.add(historyInf);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return historyInfList;
	}

	public List<SignIn> getSignIns(String signInTableId){
		
		String sql = "select * " +
				"from sign_in " +
				"where sign_in_statistic_id = ?";
		
		List<SignIn> signInList = new ArrayList<SignIn>();
		Cursor cursor = mSqLiteDatabase.rawQuery(sql, 
				new String[] {signInTableId});
		Student student = null;
		if (cursor.moveToFirst()) {
			do {
				// 遍历Cursor对象，取出数据并打印
				String studentNum = cursor.getString(cursor.getColumnIndex("student_num"));
				String time = cursor.getString(cursor.getColumnIndex("time"));
				student = mDatabaseManager.getStudentByNum(studentNum);
				int sign = cursor.getInt(cursor.getColumnIndex("sign"));
				SignIn signIn = new SignIn(signInTableId, student, time, sign);
				signInList.add(signIn);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return signInList;
	}
	
	/**
	 * 用来更新班级人数
	 * @param count
	 * @param classNum
	 */
	public void updateClassesCount(int count, String classNum){
		mSqLiteDatabase.execSQL("update classes set student_count = ? where num = ?",
				new String[] { Integer.toString(count), classNum});
	}
	
	/**
	 * 用来更新班级的签到次数
	 * @param times
	 * @param classNum
	 */
	public void updateClassesSignIn(String classNum){
		
		String sql = "select sign_in_times " +
				"from classes " +
				"where num = ? ";
		Cursor cursor = mSqLiteDatabase.rawQuery(sql, new String[] {classNum});
		int times = 0;
		if (cursor.moveToFirst()) {
			do {
				// 遍历Cursor对象，取出数据并打印
				times = cursor.getInt(cursor.getColumnIndex("sign_in_times"));
			} while (cursor.moveToNext());
		}
		times += 1;
		
		mSqLiteDatabase.execSQL("update classes set sign_in_times = ? where num = ?",
				new String[] { Integer.toString(times), classNum});
	}
	
	/**
	 * 向签名统计表中插入数据
	 * @param classNum
	 */
	public String insertSignInStatistic(String classNum, 
			String beginTime, String endTime, String date){
		
		String uid = null;
		UUID uuid = UUID.randomUUID();
		uid = uuid.toString().replace("-", "");
		ContentValues values = new ContentValues();
		
		values.put("id", uid);
		values.put("class_num", classNum);
		values.put("begin_time", beginTime);
		values.put("end_time", endTime);
		values.put("date", date);
		mSqLiteDatabase.insert("sign_in_statistic", null, values);
		return uid;
	}
	
	public void updateSignInTable(String signInStatisticId, 
			String studentNum, String time, int isSign){
		
		String sql = "update sign_in " +
				"set time = ?, sign = ? " +
				"where sign_in_statistic_id = ? and student_num = ?";
		
		mSqLiteDatabase.execSQL(sql,
				new String[] { time, Integer.toString(isSign), signInStatisticId, studentNum});
	}
	
}
