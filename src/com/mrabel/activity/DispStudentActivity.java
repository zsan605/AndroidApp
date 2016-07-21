package com.mrabel.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidapp.R;
import com.mrabel.adapter.StudentAdapter;
import com.mrabel.entity.Student;
import com.mrabel.helper.DatabaseManager;

public class DispStudentActivity extends Activity {

	private static final String TAG = DispStudentActivity.class.getSimpleName();
	
	private List<Student> studentList = new ArrayList<Student>();
	private DatabaseManager mDatabaseManager = null;
	private String mClassNum = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disp_student);
		
		Intent intent = getIntent();
		mClassNum = intent.getStringExtra("class_num");
		
		// 初始化学生数据
		initStudent();
		StudentAdapter adapter = new StudentAdapter(DispStudentActivity.this,
				R.layout.item_student, studentList);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Student student = studentList.get(position);
				
				String msg = "学生姓名：" + student.getmName() + "\n" +
							"学生学号：" + student.getmNum() + "\n" +
							"学生性别：" + student.getmSex() + "\n";
				Toast.makeText(DispStudentActivity.this, msg, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	
	private void initStudent(){
		mDatabaseManager = DatabaseManager.getInstant(DispStudentActivity.this, "SignIn.db", null, 1);
		mDatabaseManager.openDatabase();
		studentList = mDatabaseManager.getStudentByClassNum(mClassNum);
	}
	
}
