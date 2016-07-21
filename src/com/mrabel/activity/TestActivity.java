package com.mrabel.activity;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.androidapp.R;
import com.mrabel.face.FaceManager;


public class TestActivity extends Activity{

	private Button createGroupBtn;
	private Button createPersonBtn;
	private Button addFaceBtn;
	private Button deleteFaceBtn;
	private FaceManager mFaceManager;
	private JSONObject mResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
	
		createGroupBtn = (Button)findViewById(R.id.create_group);
		createPersonBtn = (Button)findViewById(R.id.create_Person);
		addFaceBtn = (Button)findViewById(R.id.add_face);
		deleteFaceBtn = (Button)findViewById(R.id.delete_face);
		
		mFaceManager = new FaceManager();
		createGroupBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mFaceManager.createGroup("first_group");
						mResult = mFaceManager.getmResult();
					}
				}).start();
				
				//Log.d("a", mResult.toString());
			}
		});
		
		createPersonBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mFaceManager.createPerson("hello", "first_group");
						mResult = mFaceManager.getmResult();
					}
				}).start();
			}
		});
		
		addFaceBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				final String url = "D:\\workspace\\AndroidApp\\assets\\img\\1.jpg";
				final String url = "http://cn.faceplusplus.com/wp-content/themes/faceplusplus/assets/img/demo/9.jpg";
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//mFaceManager.addFace(url, "hello");
						mResult = mFaceManager.getmResult();
					}
				}).start();
			}
		});
		
		deleteFaceBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						// mFaceManager.identyFace("a");
						mResult = mFaceManager.getmResult();
					}
				}).start();
			}
		});
	}

}
