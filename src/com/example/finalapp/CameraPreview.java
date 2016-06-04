package com.example.finalapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.finalapp.utility.CameraUtility;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class CameraPreview extends SurfaceView 
implements SurfaceHolder.Callback ,Camera.PictureCallback{ 
	/*
	 * 基本的摄像头预览类
	 * 
    */ 
	
	
	//定义常量
	private static final String TAG = "CameraPreview";
	private static final int PREVIEW_WIDTH = 320;
	private static final int PREVIEW_HEIGHT = 240;

	//定义成员变量
    private SurfaceHolder mHolder; 
    private CameraUtility mCameraUtility; 
    private Camera mCamera;

    //构造函数
    public CameraPreview(Context context, CameraUtility cameraUtility) { 
        super(context); 
        mCameraUtility = cameraUtility;
        mCamera = cameraUtility.getmCamera();
        
        // 安装一个SurfaceHolder.Callback， 这样创建和销毁底层surface时能够获得通知。
        mHolder = getHolder(); 
        mHolder.addCallback(this); 
        
        //
        
    } 
  
    public void surfaceCreated(SurfaceHolder holder) { 
        // surface已被创建，现在把预览画面的位置通知摄像头
        try { 
            mCamera.setPreviewDisplay(holder); 
            mCamera.startPreview(); 
        } catch (IOException e) { 
            Log.d(TAG, "Error setting camera preview: " + e.getMessage()); 
        } 
        
        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
      //  mCamera.setPreviewCallback(this);
    } 

    public void surfaceDestroyed(SurfaceHolder holder) { 
        // 空代码。注意在activity中释放摄像头预览对象    
    } 

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) { 
        // 如果预览无法更改或旋转，注意此处的事件
        // 确保在缩放或重排时停止预览
 
        if (mHolder.getSurface() == null){ 
          // 预览surface不存在
          return; 
        } 

        // 更改时停止预览 
        try { 
            mCamera.stopPreview(); 
        } catch (Exception e){ 
          // 忽略：试图停止不存在的预览
        } 

        // 在此进行缩放、旋转和重新组织格式
        // 以新的设置启动预览

        try { 
            mCamera.setPreviewDisplay(mHolder); 
            mCamera.startPreview(); 
        } catch (Exception e){ 
            Log.d(TAG, "Error starting camera preview: " + e.getMessage()); 
        } 
    }

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		// TODO Auto-generated method stub
		File pictureFile = mCameraUtility.getOutputMediaFile(CameraUtility.MEDIA_TYPE_IMAGE); 
        if (pictureFile == null){ 
            Log.d(TAG, "Error creating media file, check storage permissions: "); 
            return; 
        } 
        try { 
            FileOutputStream fos = new FileOutputStream(pictureFile); 
            fos.write(data); 
            fos.close(); 
        } catch (FileNotFoundException e) { 
            Log.d(TAG, "File not found: " + e.getMessage()); 
        } catch (IOException e) { 
            Log.d(TAG, "Error accessing file: " + e.getMessage()); 
        } 
    } 

	
}