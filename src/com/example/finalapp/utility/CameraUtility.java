package com.example.finalapp.utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class CameraUtility {

	/**
	 * 封装camera的使用：
	 * 包括检查摄像头是否存在
	 * 打开摄像头的操作
	 * */
	
	//定义常量
	public static final String TAG = "CameraUtility";
	public static final int REAR_CAMERA = 0;
	public static final int FRONT_CAMERA = 1; 
	public static final int MEDIA_TYPE_IMAGE = 1; 
	public static final int MEDIA_TYPE_VIDEO = 2; 
	
	//定义成员变量
	private Camera mCamera;
	private Context mContext;
	private int mWidth;
	private int mHeight; 
	
	
	//构造函数
	public CameraUtility(Context context){
		this(context, 320, 240);
	}
		
	public CameraUtility(Context context, int width, int height){
	    mWidth = width;
	    mHeight = height;
	    mContext = context;
	    
	    //检查摄像头是否可用
	    checkCameraHardware(context);
		try { 
	        mCamera = Camera.open(FRONT_CAMERA); // 试图获取前置Camera实例
	    } 
	    catch (Exception e){ 
	        // 摄像头不可用（正被占用或不存在）
	    	Toast.makeText(null, "摄像头暂时不可用", Toast.LENGTH_LONG).show();
	    }  
	}
	
	// 检查设备是否提供摄像头  
	private boolean checkCameraHardware(Context context) { 
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){ 
	        // 摄像头存在 
	    	Toast.makeText(context, "摄像头可用", Toast.LENGTH_LONG).show();
	    	return true; 
	    } else { 
	        // 摄像头不存在  
	    	Toast.makeText(context, "摄像头不可用", Toast.LENGTH_LONG).show();
	        return false; 
	    } 
	}
	
	public Camera getmCamera() {
		return mCamera;
	}

	// 为保存图片或视频创建文件Uri  
	public Uri getOutputMediaFileUri(int type){ 
	      return Uri.fromFile(getOutputMediaFile(type)); 
	} 

	// 为保存图片或视频创建File 
	public File getOutputMediaFile(int type){ 
		// 检查SD卡的状态,如果不可读返回null
		//if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED){
		//	return null;
		//}
		
		//创建一个图片文件
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory( 
	              Environment.DIRECTORY_PICTURES), "MyCameraApp"); 
	    
	    Log.d(TAG,mediaStorageDir.getPath());
	    // 如果期望图片在应用程序卸载后还存在、且能被其它应用程序共享，
	    // 则此保存位置最合适
	    // 如果不存在的话，则创建存储目录

	    if (! mediaStorageDir.exists()){ 
	        if (! mediaStorageDir.mkdirs()){ 
	            Log.d("MyCameraApp", "failed to create directory"); 
	            return null; 
	        } 
	    } 
	    
	    // 创建媒体文件名
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); 
	    File mediaFile; 
	    if (type == MEDIA_TYPE_IMAGE){ 
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + 
	        "IMG_"+ timeStamp + ".jpg"); 
	       
	    } else if(type == MEDIA_TYPE_VIDEO) { 
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + 
	        "VID_"+ timeStamp + ".mp4"); 
	    } else { 
	        return null; 
	    } 
	    Log.d(TAG,mediaFile.getName());
	    return mediaFile; 
	}

	//捕获图片
	public void takePicture(Camera.ShutterCallback shutter, 
			Camera.PictureCallback raw, 
			Camera.PictureCallback jpeg){
		mCamera.takePicture(shutter, raw, jpeg);
	}
	
	public void takePicture(Camera.ShutterCallback shutter, 
			Camera.PictureCallback raw, 
			Camera.PictureCallback postview, 
			Camera.PictureCallback jpeg){
		
		mCamera.takePicture(shutter, raw, postview, jpeg);
	}
	
	//释放资源
	public void destory(){	
		if (mCamera != null){ 
	           mCamera.release();        // 为其它应用释放摄像头
	           mCamera = null; 
	       } 
	}
}
