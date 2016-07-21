package com.mrabel.activity;

import com.example.androidapp.R;
import com.mrabel.face.FaceManager;
import com.mrabel.thread.FaceTrain;
import com.zzg.handler.MessageHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 用于识别训练
 * @author zzg
 *
 */
public class TrainActivity extends Activity implements OnClickListener{

	private static final String TAG = TrainActivity.class.getSimpleName();
	
	private Button trainBtn = null;
	private FaceManager mFaceManager = null;
	private MessageHandler mMessageHandler = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train);
		
		mMessageHandler = new MessageHandler(TrainActivity.this);
		
		initUI();
		initEvents();
	}

	private void initUI(){
		trainBtn = (Button)findViewById(R.id.train);
	}
	
	private void initEvents(){
		trainBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.train:
			new Thread(new FaceTrain(TrainActivity.this, mMessageHandler, "first_group")).start();
			break;

		default:
			break;
		}
	}
}
