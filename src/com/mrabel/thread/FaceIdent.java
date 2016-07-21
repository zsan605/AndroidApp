package com.mrabel.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mrabel.face.FaceManager;
import com.mrabel.helper.DatabaseManager;
import com.mrabel.utils.ConstUtil;
import com.mrabel.utils.ImageUtil;

/**
 * 用来人脸识别
 * @author zzg
 *
 */
public class FaceIdent implements Runnable{

	private static final String TAG = FaceIdent.class.getSimpleName();
	
	private Context mContext = null;
	private Handler mHandler = null;
	private Bitmap mBitmap = null;
	private String mSignInTableId = null;
	private String mGroupName = null;
	private FaceManager mFaceManager = null;
	private JSONObject mJsonObject = null;
	private ImageUtil mImageUtil = null;
	private DatabaseManager mDatabaseManager = null;
	private String mStudentNum = null;
	
	public FaceIdent(Context context, Handler handler, Bitmap bitmap, 
			String signInTableId, String className){
		
		mContext = context;
		mHandler = handler;
		mBitmap = bitmap;
		mSignInTableId = signInTableId;
		mGroupName = className;
		mFaceManager = new FaceManager();
		mImageUtil = new ImageUtil();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {  
			// 模拟执行某项任务，下载等
			Log.d(TAG, "run face detect");
			
			byte[] data = null;
			data = mImageUtil.bitmapToByte(mBitmap);
			mJsonObject = mFaceManager.identFace(data, mGroupName);
			mStudentNum = mJsonObject.getString("person_name");
			Log.d(TAG, mStudentNum);
			mDatabaseManager = DatabaseManager.getInstant(mContext, "SignIn.db", null, 1);
			mDatabaseManager.openDatabase();
			String classNum = mDatabaseManager.getClassNum(mGroupName);
			
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
			mDatabaseManager.updateSignInTable(mSignInTableId, mStudentNum, df.format(date), 1);

			// 任务完成后通知activity更新UI
            Message msg = prepareMessage("FaceIdent");	
            // message将被添加到主线程的MQ中
            mHandler.sendMessage(msg);
        } catch (Exception e) {
            Log.d(TAG, "interrupted!");
        }
	}
	
	private Message prepareMessage(String str) {
        
		Message msg = mHandler.obtainMessage();
        msg.what = ConstUtil.MSG_FACE_IDENT;
		Bundle data = new Bundle();
        data.putString("message", str);
        msg.setData(data);
        return msg;
    }
}
