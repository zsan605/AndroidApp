package com.mrabel.camera;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.os.Message;
import android.provider.MediaStore.Video;
import android.support.annotation.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.faceplusplus.api.FaceDetecter;
import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.mrabel.entity.Student;
import com.mrabel.helper.DatabaseManager;
import com.mrabel.listener.FaceTracker;
import com.mrabel.thread.FaceDetect;
import com.mrabel.thread.FaceIdent;
import com.mrabel.utils.ConstUtil;
import com.mrabel.utils.FileUtil;
import com.mrabel.utils.ImageUtil;
import com.zzg.handler.MessageHandler;

/** 
 * 类名：CameraManager
 * 作用：用于预览摄像捕获的数据，实现SurfaceHolder.Callback接口（为了捕捉view创建和销毁时的回调事件）
 * 	         实现Camera.PreviewCallback接口（为了实时处理摄像头预览帧）
 * @author MrAbel
 * */
public class CameraPreview extends SurfaceView 
implements SurfaceHolder.Callback, PictureCallback {

	//定义常量
	public static final String TAG = CameraPreview.class.getSimpleName();
	
	//定义成员变量
	private Context mContext;
	private CameraManager mCameraManager;
	private CameraConfig mCameraConfig;
	private SurfaceHolder mHolder; 	
	private FaceTracker mFaceTracker;
	private FaceDetecter mFaceDetector;
	private MessageHandler mMessageHandler;
	private ImageUtil imageUtil;
	private FileUtil fileUtil;
	private String mCurPhoto;
	private Boolean isIdent;
	private int mWidth;
	private int mHeight;
	private String mClassName;
	
	/** 构造函数*/
	public CameraPreview(Context context, CameraConfig config, CameraManager cameraManager){
		super(context);
		mCameraConfig = config;
		mCameraManager = cameraManager;
		mContext = context;
	 	mWidth = 320;
		mHeight = 240;
		
		mHolder = getHolder();			// 通过SurfaceView类的getHolder得到SurfaceHolder
		mHolder.addCallback(this);		// 给SurfaceHolder添加回调函数，用来监听surface的改变
		mFaceTracker = new FaceTracker();		// 创建人脸检测监听器
		
		// face++接口
		mFaceDetector = new FaceDetecter();		// 创建人脸检测器
		mFaceDetector.init(context, ConstUtil.API_KEY);	
		
		mMessageHandler = new MessageHandler(mContext);
		imageUtil = new ImageUtil();
		fileUtil = new FileUtil();
		
	}
	
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		// TODO Auto-generated method stub

		/* 取得相片 */
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        bitmap = imageUtil.getRotateBitmap(bitmap, 270.0f);
        fileUtil.saveBitmap(bitmap);
        
		if (isIdent){
			
			String signInTableId = initSignIn();
			Thread faceIdent = new Thread(
					new FaceIdent(mContext, mMessageHandler, bitmap, signInTableId, mClassName));
			faceIdent.start();
		}else{
			Thread faceDetect = new Thread(new FaceDetect(mContext, mMessageHandler, bitmap));
			faceDetect.start();
		}
	}
	
		
	
	private String initSignIn(){
		DatabaseManager dbManager = DatabaseManager.getInstant(mContext, "Sign.db", null, 1);
		dbManager.openDatabase();
		
		// 获得日期
		Date date=new Date();
		SimpleDateFormat df1 = new SimpleDateFormat("hh:mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		
		// 获得班级号
		String classNum = dbManager.getClassNum(mClassName);
		
		// 向签名统计表中插入数据
		String signInStatisticId = dbManager.insertSignInStatistic(classNum,
				df1.format(date), df1.format(date), df2.format(date));
		
		// 初始化签名表
		List<Student> list = dbManager.getStudentByClassNum(classNum);
		for (Student student : list){
			
			// 等到真正签名时更新签名表
			dbManager.insertSignIn(signInStatisticId, student.getmNum(), df1.format(date), 0);
		}
		
		// 更新班级签名次数
		dbManager.updateClassesSignIn(classNum);
		
		return signInStatisticId;
	}
	
	/** 实现SurfaceHolder.Callback接口的方法
	 *  在surface创建时被调用，一般在这个方法里面开启渲染屏幕的线程。
	 **/
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// 打开摄像头
		mCameraManager.openCamera(CameraManager.FRONT_CAMERA);
		
		// 初始化摄像头的配置
		mCameraConfig.initCameraConfig(mCameraManager.getmCamera());
		
		// 初始化话摄像头的属性：如方向，大小
		mCameraConfig.setDisplayOrientation(90);
		
		// 设置预览图片格式
		mCameraConfig.setPreviewFormat();
		
		// 将Surface用于实时预览
		mCameraManager.setPreviewDisplay(holder);
		
		// 开启预览
		mCameraManager.startPreview();
		
		// 开启人脸检测
		mCameraManager.startFaceDetection(mFaceTracker);

		mCameraConfig.setPreviewSize(mWidth, mHeight);
	}

	/** 实现SurfaceHolder.Callback接口的方法
	 *  当Surface内容改变室调用
	 *  */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		// 如果预览无法更改或旋转，注意此处的事件
        // 确保在缩放或重排时停止预览
 
        if (holder.getSurface() == null){ 
        	// 预览surface不存在
        	Log.d(TAG, "surface不存在");
        	return; 
        } 

        // 更改时停止预览 
        try { 
            mCameraManager.stopPreview(); 
        } catch (Exception e){ 
        	// 忽略：试图停止不存在的预览
        } 

        // 在此进行缩放、旋转和重新组织格式
        // 以新的设置启动预览
        try { 
            mCameraManager.setPreviewDisplay(holder); 
            mCameraManager.startPreview(); 
        } catch (Exception e){ 
            Log.d(TAG, "Error starting camera preview: " + e.getMessage()); 
        } 
        
		// 开启人脸检测
		mCameraManager.startFaceDetection(mFaceTracker);
	}

	/** 实现SurfaceHolder.Callback接口的方法
	 *  销毁时被调用，一般在这个方法里将渲染的线程停止
	 *  */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// 空代码。注意在activity中释放摄像头预览对象    
	}
	
	/** 获得preview的显示的框架宽度
	 * */
	public int getmWidth() {
		return mWidth;
	}

	/** 设置preview的显示的框架宽度
	 * 	@mWidth ： 预设宽度
	 * */
	public void setmWidth(int mWidth) {
		this.mWidth = mWidth;
	}

	/** 获得preview的显示的框架高度
	 * */
	public int getmHeight() {
		return mHeight;
	}

	/** 设置preview的显示的框架高度
	 * 	@mWidth ： 预设高度
	 * */
	public void setmHeight(int mHeight) {
		this.mHeight = mHeight;
	}

	public Boolean getIsIdent() {
		return isIdent;
	}

	public void setIsIdent(Boolean isIdent) {
		this.isIdent = isIdent;
	}
	
	public String getmClassName() {
		return mClassName;
	}

	public void setmClassName(String mClassName) {
		this.mClassName = mClassName;
	}

}


