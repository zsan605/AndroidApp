package com.mrabel.camera;

import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.view.Display;
import android.view.WindowManager;

/** 
 * 类名：CameraConfig
 * 作用：对摄像头的属性配置进行管理,用于获取或者设置摄像头的的属性
 * @author MrAbel
 * */
public class CameraConfig {

	//定义常量
	public static final String TAG = CameraConfig.class.getSimpleName();
	private static final Pattern COMMA_PATTERN = Pattern.compile(",");

	//定义成员变量
	private Context mContext;				//应用程序环境
	private Camera mCamera;					//摄像头对象
	private CameraPreview mCameraPreview;	//
	private int mPreviewFormat;				//预览图片格式
	private Point mScreenResolution; 		//屏幕的分辨率
	private Point mCameraResolution; 		//摄像头的分辨率
	
	/** 
	 * 构造函数
	 * @param context	当前应用程序的环境
	 */
	public CameraConfig(Context context){
		mContext = context;
	}
	
	/** 
	 * 
	 * @return  返回摄像头的分辨率  
	 */
	public Point getCameraResolution() {
	    return mCameraResolution;
	}
	
	/** 设置当前摄像头的方向
	 * */
	public void setDisplayOrientation(int degree){
		mCamera.setDisplayOrientation(degree);
	} 
	
	/** 获得preview的尺寸
	 * */
	public Size getPreviewSize(){
		Size size = mCamera.getParameters().getPreviewSize(); //获取预览大小
		return size;
	}
	
	/** 获取系统屏幕的分辨率
	 * */
	public Point getScreenResolution(){
		
		Point screenResolution;
		WindowManager windowManager = (WindowManager)
				mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		screenResolution = new Point(display.getWidth(), display.getHeight());
		return screenResolution;
	} 
	
	/** 初始化摄像头的配置信息
	 *  @camera : 摄像头实例
	 * */
	public void initCameraConfig(Camera camera){
		mCamera = camera;
		// 获得摄像头的信息
		getInfoFromCamera();
	}
	
	/** 获得当前camera实例的基本信息
	 * */
	private void getInfoFromCamera(){
		
		// 获得摄像头的Parameters
		Camera.Parameters parameters = mCamera.getParameters();
		
		// 获得当前preview显示的图片的格式(图片来自 Camera.PreviewCallback)
		mPreviewFormat = parameters.getPreviewFormat();
		
		// 获取屏幕的分辨率
		mScreenResolution = getScreenResolution();
		// 获取摄像头的分辨率
		mCameraResolution = getCameraResolution(parameters, mScreenResolution);
		
	}
	
	/** 获得系统摄像头的分辨率
	 * */
	private Point getCameraResolution(Camera.Parameters parameters, Point screenResolution) {
		Point cameraResolution = null;
	    // 确保摄像头的分辨率是8的倍数
	    cameraResolution = new Point((screenResolution.x >> 3) << 3,
	    		(screenResolution.y >> 3) << 3);
	    return cameraResolution;
	}
	
	/**
	 * 获得拍照图片的尺寸
	 * @return
	 */
	public Size getPictureSize(){
		return mCamera.getParameters().getPictureSize();
	}
	
	/**
	 * 获得预览帧的格式
	 * @return
	 */
	public int getPictureFormat(){
		return mCamera.getParameters().getPictureFormat();
	}
	
	/**
	 * 设置预览帧的格式
	 */
	public void setPreviewFormat(){
		mCamera.getParameters().setPreviewFormat(ImageFormat.YV12);
	}
	
	public int getPreviewFormat(){
		return mCamera.getParameters().getPreviewFormat();
	}
	
	public void setPreviewSize(int width, int height){
		Camera.Parameters para = mCamera.getParameters();
		para.setPreviewSize(width, height);
		mCamera.setParameters(para);
	}
	public Size getPreivewSize(){
		return mCamera.getParameters().getPreviewSize();
	}
}
