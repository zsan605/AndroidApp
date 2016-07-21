package com.mrabel.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.androidapp.R;
import com.mrabel.adapter.SignInAdapter;
import com.mrabel.entity.HistoryInf;
import com.mrabel.entity.SignIn;
import com.mrabel.helper.DatabaseManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SignInActivity extends Activity{

	private static final String TAG = SignInActivity.class.getSimpleName();

	private List<SignIn> signList = new ArrayList<SignIn>();
	private DatabaseManager mDatabaseManager = null;
	private int mClassNum;
	private String mSignInTableId = null;
	private String mDate = null;
	private ListView listView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		initUI();
		initEvent();
		Intent intent = getIntent();
		mClassNum = intent.getIntExtra("class_num", 0);
		mSignInTableId = intent.getStringExtra("sign_in_table_id");
		mDate = intent.getStringExtra("date");
		initSignIn();
		SignInAdapter adapter = new SignInAdapter(
				SignInActivity.this, R.layout.item_sign_in, signList);
		listView.setAdapter(adapter);
	}
	
	
	private void initUI(){
		listView = (ListView)findViewById(R.id.list_view);
	}
	
	private void initEvent(){
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				SignIn signIn = signList.get(position);
				Toast.makeText(SignInActivity.this, "hello",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void initSignIn(){
		mDatabaseManager = DatabaseManager.getInstant(SignInActivity.this, "SignIn.db", null, 1);
		mDatabaseManager.openDatabase();
		signList = mDatabaseManager.getSignIns(mSignInTableId);
	}
	
}
