package com.example.finalapp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.finalapp.CameraPreview;
import com.example.finalapp.R;
import com.example.finalapp.utility.CameraUtility;

public class CatcherActivity extends Activity {

	public static final String TAG = "CatcherActivity";

	private Button takePhoto = null;
	private CameraUtility mCameraUtility;
    private CameraPreview mPreview; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catcher_activity);
		
		// 创建CameraUtility实例 
        mCameraUtility = new CameraUtility(CatcherActivity.this);
        
        // 创建Preview view并将其设为activity中的内容
        mPreview = new CameraPreview(this, mCameraUtility);
        
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview); 
        preview.addView(mPreview); 
        
        // 在Capture按钮中加入listener 
        Button captureButton = (Button) findViewById(R.id.button_capture); 
            captureButton.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View v) { 
                    // 从摄像头获取图片
                    mCameraUtility.takePicture(null, null, mPreview); 
                } 
            });
        
        takePhoto = (Button) findViewById(R.id.take_photo);
		
      
		takePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCameraUtility.destory();
			}
		});
	}
}
