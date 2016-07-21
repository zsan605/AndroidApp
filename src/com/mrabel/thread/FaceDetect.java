package com.mrabel.thread;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.faceplusplus.api.FaceDetecter;
import com.faceplusplus.api.FaceDetecter.Face;
import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.mrabel.face.FaceManager;
import com.mrabel.utils.ConstUtil;
import com.mrabel.utils.ImageUtil;

/**
 * 用于向人脸库中添加人脸
 * @author zzg
 *
 */
public class FaceDetect implements Runnable{

	// 定义常量
	private static final String TAG = FaceDetect.class.getSimpleName();
	
	// 定义成员变量
	private Handler mHandler;
	private Context mContext;
	private Bitmap mBitmap;
	private FaceManager mFaceManager;
	private ImageUtil imageUtil;
	private int width = 320;
	private int height = 240;

	public FaceDetect(Context context, Handler handler, Bitmap bitmap){
		
		mHandler = handler;
		mContext = context;
		mBitmap = bitmap;
		mFaceManager = new FaceManager();
		imageUtil = new ImageUtil();
		
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {  
			// 模拟执行某项任务，下载等
			Log.d(TAG, "run face detect");
			
			byte[] data = null;
			data = imageUtil.bitmapToByte(mBitmap);
            mFaceManager.addFace(data, "hello");
        	
            // 任务完成后通知activity更新UI
            Message msg = prepareMessage("FaceDetect");	
            // message将被添加到主线程的MQ中
            mHandler.sendMessage(msg);
        } catch (Exception e) {
            Log.d(TAG, "interrupted!");
        }
		
	} 
	
	private Message prepareMessage(String str) {
        
		Message msg = mHandler.obtainMessage();
        msg.what = ConstUtil.MSG_FACE_DETECT;
		Bundle data = new Bundle();
        data.putString("message", str);
        msg.setData(data);
        return msg;
    }
}
