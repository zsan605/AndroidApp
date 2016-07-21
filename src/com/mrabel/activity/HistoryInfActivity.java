package com.mrabel.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.androidapp.R;
import com.mrabel.adapter.HistoryInfAdapter;
import com.mrabel.entity.HistoryInf;
import com.mrabel.helper.DatabaseManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HistoryInfActivity extends Activity {
	
	private static final String TAG = HistoryInfActivity.class.getSimpleName();
	
	
	private List<HistoryInf> historyInfList = new ArrayList<HistoryInf>();
	private DatabaseManager mDatabaseManager = null;
	private String mClassName = null;
	
	private ListView listView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_inf);
		
		initUI();
		initEvent();
		Intent intent = getIntent();
		mClassName = intent.getStringExtra("class_name");
		
		initHistoryInf();
		HistoryInfAdapter adapter = new HistoryInfAdapter(HistoryInfActivity.this,
				R.layout.item_history_inf, historyInfList);
		
		
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
				Toast.makeText(HistoryInfActivity.this, "hello",
						Toast.LENGTH_SHORT).show();
				HistoryInf historyInf = historyInfList.get(position);
				
				Intent intent = new Intent(HistoryInfActivity.this, SignInActivity.class);
				intent.putExtra("class_num", historyInf.getmClassNum());
				intent.putExtra("sign_in_table_id", historyInf.getmId());
				intent.putExtra("date", historyInf.getmDate());
				startActivity(intent);
				
			}
		});
	}
	
	private void initHistoryInf(){
		mDatabaseManager = DatabaseManager.getInstant(HistoryInfActivity.this, "SignIn.db", null, 1);
		mDatabaseManager.openDatabase();
		historyInfList = mDatabaseManager.getHistoryInf();
	}

	
	
}
