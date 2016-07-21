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
import com.mrabel.adapter.ClassAdapter;
import com.mrabel.entity.Classes;
import com.mrabel.helper.DatabaseManager;
import com.mrabel.utils.ConstUtil;

public class BrowseClassActivity extends Activity{
	
	private static final String TAG = BrowseClassActivity.class.getSimpleName();
	
	private List<Classes> classList = new ArrayList<Classes>();
	private DatabaseManager mDatabaseManager = null;
	private Boolean mIsDisp = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_browse_class);
		
		Intent intent = getIntent();
		mIsDisp = intent.getBooleanExtra("data", false);
		
		// 初始化班级数据
		initClasses(); 				
		ClassAdapter adapter = new ClassAdapter(BrowseClassActivity.this,
				R.layout.item_class, classList);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Classes classes = classList.get(position);
				if (mIsDisp){
					Toast.makeText(BrowseClassActivity.this, classes.getmClassName(),
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(BrowseClassActivity.this, DispStudentActivity.class);
					intent.putExtra("class_num", classes.getmClassNum());
					startActivity(intent);
				}else{
					Intent resultIntent = new Intent();
					resultIntent.putExtra("class_name", classes.getmClassName());
					setResult(ConstUtil.RESULT_BROWSECLASS, resultIntent);
					finish();
				}
			}
			
		});
	}
	
	
	private void initClasses(){
		mDatabaseManager = DatabaseManager.getInstant(BrowseClassActivity.this, "SignIn.db", null, 1);
		mDatabaseManager.openDatabase();
		classList = mDatabaseManager.getClasses();
	}


}
