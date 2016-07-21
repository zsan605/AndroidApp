package com.mrabel.listener;

import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.util.Log;

/**
 * 利用Android自带的人脸检测API进行人脸追踪
 * @author MrAbel
 *
 */
public class FaceTracker implements FaceDetectionListener{

	// 定义常量
	private static final String TAG = FaceTracker.class.getSimpleName();
	
	/**
	 * 实现FaceDetectionListener的方法，当检测到人类是调用
	 */
	@Override
	public void onFaceDetection(Face[] faces, Camera camera) {
		// TODO Auto-generated method stub
		if (faces.length > 0){
			
			if (faces.length == 1){
				//Log.d(TAG, "get a face");
			}else if(faces.length == 2){
				//Log.d(TAG, "get two face");
			}
		}
	}

}
