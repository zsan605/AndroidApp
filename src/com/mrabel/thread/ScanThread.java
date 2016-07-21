package com.mrabel.thread;

import android.util.Log;

import com.mrabel.camera.CameraManager;
import com.mrabel.camera.CameraPreview;

/** 
 * 类名：CameraManager
 * 作用：对摄像头的使用进行管理,包括打开摄像头,释放摄像头等
 * @author MrAbel
 * */
public class ScanThread implements Runnable {

	private static final String TAG = ScanThread.class.getSimpleName();
	
	private CameraManager mCameraManager;
	private CameraPreview mCameraPreview;
	
	public ScanThread(CameraManager cameraManager, CameraPreview preview){
		mCameraManager = cameraManager;
		mCameraPreview = preview;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
        while(!Thread.currentThread().isInterrupted()){  
            try {  
                if(null != mCameraManager.getmCamera()){        
                   // mCameraManager.getmCamera().setPreviewCallback(mCameraPreview);  
                    Log.d(TAG, "setOneShotPreview...");  
                }  
                Thread.sleep(1500);  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
                Thread.currentThread().interrupt();  
            }  
        }  
    }  
}
