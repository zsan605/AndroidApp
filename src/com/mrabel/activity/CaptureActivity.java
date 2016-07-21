package com.mrabel.activity;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.example.androidapp.R;
import com.mrabel.camera.CameraManager;
import com.mrabel.camera.CameraPreview;
import com.mrabel.face.FaceManager;
import com.mrabel.thread.ScanThread;
import com.mrabel.utils.ConstUtil;

/** 
 * 类名：CameraManager
 * 作用：摄像头的预览activity
 * @author MrAbel
 * */
public class CaptureActivity extends Activity implements OnClickListener{

	// 定义常量
	public static final String TAG = CaptureActivity.class.getSimpleName();

	// 控件引用
	private Button captureBtn = null;
	private Button closeCameraBtn = null;
	private FrameLayout preview = null;
	private TextView dispClassTextView = null;
	
	private CameraManager mCameraManager;
	private CameraPreview mCameraPreview;
	private FaceManager mFaceManager;

	private Boolean isIdent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		
		Intent intent = getIntent();
		
		// 创建CameraUtility实例 
		mCameraManager = new CameraManager(CaptureActivity.this);
   
        // 创建Preview view并将其设为activity中的内容
        mCameraPreview = mCameraManager.getmCameraPreview();
        mCameraPreview.setIsIdent(intent.getBooleanExtra("data", false));
        
        initUI();
        initEvents();
        initViewParams();
	}
	
	/**
	 * 初始化activity布局控件
	 */
	private void initUI(){
		// 获得activity布局控件的引用
        preview  = (FrameLayout) findViewById(R.id.camera_preview); 
        captureBtn = (Button) findViewById(R.id.button_capture); 
        closeCameraBtn = (Button) findViewById(R.id.close_camera);
        dispClassTextView = (TextView)findViewById(R.id.disp_class);
	}

	/**
	 * 初始化点击事件
	 */
	private void initEvents(){
		captureBtn.setOnClickListener(this);
		closeCameraBtn.setOnClickListener(this);
	}
	
	/**
	 * 为布局控件设置参数
	 */
	private void initViewParams(){
        
		LayoutParams para = new LayoutParams(480, 480/9*16);
		para.addRule(RelativeLayout.CENTER_IN_PARENT);
        preview.setLayoutParams(para);
        preview.addView(mCameraPreview); 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.capture_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.select_class:
			Intent intent = new Intent(CaptureActivity.this, BrowseClassActivity.class);
			startActivityForResult(intent, ConstUtil.CAPTURE_BROWSECLASS);
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mCameraManager.closeCamera();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_capture:
			// 从摄像头获取图片，开启检测图片帧线程
            // new Thread(new ScanThread(mCameraManager, mCameraPreview)).start();
    		
    		// 从摄像头获取图片
            mCameraManager.takePicture(null, null, mCameraPreview);
			break;

		case R.id.close_camera:
			mCameraManager.closeCamera();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		switch (requestCode) {
		case ConstUtil.CAPTURE_BROWSECLASS:
			if (resultCode ==  ConstUtil.RESULT_BROWSECLASS){
				String className = data.getStringExtra("class_name");
				dispClassTextView.setText(className);
				mCameraPreview.setmClassName(className);
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}

