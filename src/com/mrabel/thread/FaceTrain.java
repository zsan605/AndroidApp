package com.mrabel.thread;

import com.mrabel.face.FaceManager;
import com.mrabel.utils.ConstUtil;
import com.zzg.handler.MessageHandler;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class FaceTrain implements Runnable{
	
	private static final String TAG = FaceTrain.class.getSimpleName();

	private FaceManager mFaceManager = null;
	private String mGroupName = null;
	private Handler mHandler = null;
	private Context mContext = null;
	private MessageHandler mMessageHandler = null;
	
	public FaceTrain(Context context, Handler handler, String groupName){
		mGroupName = groupName;
		mHandler = handler;
		mContext = context;
		mFaceManager = new FaceManager();
		mMessageHandler = new MessageHandler(mContext);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.d(TAG, "begin training");
		mFaceManager.trainIdenty(mGroupName);
		
		// 任务完成后通知activity更新UI
        Message msg = prepareMessage("");	
        // message将被添加到主线程的MQ中
        mHandler.sendMessage(msg);
	}
	
	private Message prepareMessage(String str) {
        
		Message msg = mHandler.obtainMessage();
		msg.what = ConstUtil.MSG_FACE_TRAIN;
        Bundle data = new Bundle();
        data.putString("message", str);
        msg.setData(data);
        return msg;
    }
}
