package com.mrabel.camera;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mrabel.listener.FaceTracker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

/** 
 * 类名：CameraManager
 * 作用：对摄像头的使用进行管理,包括打开摄像头,释放摄像头等
 * 作者：MrAbel
 * */
public class CameraManager {

	//定义常量
	public static final String TAG = CameraManager.class.getSimpleName();
	public static final int REAR_CAMERA = 0;
	public static final int FRONT_CAMERA = 1; 
	public static final int MEDIA_TYPE_IMAGE = 1; 
	public static final int MEDIA_TYPE_VIDEO = 2; 
	private static final int MIN_FRAME_WIDTH = 240;
	private static final int MIN_FRAME_HEIGHT = 240;
	private static final int MAX_FRAME_WIDTH = 480;
	private static final int MAX_FRAME_HEIGHT = 360;
	
	//定义成员变量
	private Context mContext;				//应用程序环境
	private Camera mCamera;					//摄像头对象
	private CameraPreview mCameraPreview;	//摄像头预览类
	private CameraConfig mCameraConfig;		//摄像头配置类
	
	private Rect mPreviewRect;
	private boolean mIsPreview;				//是否开启预览，ture：开启，false：不开启
		
	/** 构造函数
	 *  @context : 当前应用程序环境
	 *  */	
	public CameraManager(Context context){
		mContext = context;
		mCamera = null;
		mCameraConfig = new CameraConfig(context);
	    mCameraPreview = new CameraPreview(context, mCameraConfig, this);
	    mIsPreview = false;
	}
	
	/** 打开摄像头
	 *  @cameraFlag : 用来设置开启哪个摄像头（前/后）
	 *  */
	public void openCamera(int cameraFlag){
		// 检查摄像头是否可用
	    checkCameraHardware();

	    // 检查摄像头是否已经打开
	    if (mCamera != null){
	    	return ;
	    }
	    
		try { 
			// 打开摄像头,试图获取Camera实例
			mCamera = Camera.open(cameraFlag);  
		} 
	    catch (Exception e){ 
	        // 摄像头不可用（正被占用或不存在）
	    	Log.d(TAG, "摄像头暂时不可用");
	    }  
	}
	/** 释放摄像头
	 * */
	public void closeCamera(){	
		if (mCamera != null){ 
			// 为其它应用释放摄像头
			mCamera.release();        
			mCamera = null;
		} 
	}
	
	/** 为摄像头开启预览
	 * */
	public void startPreview(){
		if (mCamera != null && !mIsPreview){
			mCamera.startPreview();
			mIsPreview = true;
		}
	}
	
	/** 为摄像头关闭预览
	 * */
	public void stopPreview() {
		if (mCamera != null && mIsPreview) {
	    
			mCamera.stopPreview();
			mIsPreview = false;
		}
	}
	
	/** 将surface用于实时预览显示
	 *  @holder : 控制Surface的SurfaceHolder
	 *  */
	public void setPreviewDisplay(SurfaceHolder holder){
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** 获得摄像头实例
	 * */
	public Camera getmCamera() {
		return mCamera;
	}

	/** 获得摄像头预览类的实例
	 * */
	public CameraPreview getmCameraPreview() {
		return mCameraPreview;
	}
	
	/**
	 * 用于开启人脸检测功能
	 * @param listener   人脸检测监听器
	 */
	public void startFaceDetection(FaceTracker listener){
		
		// 为摄像头设置检测监听器
		mCamera.setFaceDetectionListener(listener);
		Camera.Parameters parameters = mCamera.getParameters();
		
		if (parameters.getMaxNumDetectedFaces() > 0){
			mCamera.startFaceDetection();
		}
	}

	/**
	 * 为摄相机设置预览回调
	 * @param callback
	 */
	public void setPreviewCallback(Camera.PreviewCallback callback){
		mCamera.setPreviewCallback(callback);
	}
	
	/** 检查设备是否提供摄像头
	 * */  
	private boolean checkCameraHardware() { 
	    if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){ 
	        // 摄像头存在 
	    	Log.d(TAG, "摄像头存在");
	    	return true; 
	    } else { 
	        // 摄像头不存在  
	    	Log.d(TAG, "摄像头不存在");
	        return false; 
	    } 
	}
	
	/**
	 * 用来捕获图片
	 * @param shutter
	 * @param raw
	 * @param jpeg
	 */
	public void takePicture(Camera.ShutterCallback shutter, 
			Camera.PictureCallback raw, 
			Camera.PictureCallback jpeg){
			mCamera.takePicture(shutter, raw, jpeg);
	}
	
	/**
	 * 
	 * @param shutter
	 * @param raw
	 * @param postview
	 * @param jpeg
	 */
	public void takePicture(Camera.ShutterCallback shutter, 
			Camera.PictureCallback raw, 
			Camera.PictureCallback postview, 
			Camera.PictureCallback jpeg){
			mCamera.takePicture(shutter, raw, postview, jpeg);
	}
}


