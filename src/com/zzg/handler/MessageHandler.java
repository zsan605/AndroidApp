package com.zzg.handler;

import java.util.ArrayList;

import com.mrabel.camera.CameraPreview;
import com.mrabel.utils.ConstUtil;

import android.R.string;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * 用来处理CameraPreview中的消息
 * @author zzg
 *
 */
public class MessageHandler extends Handler {

	private static final String TAG = MessageHandler.class.getSimpleName();
	private Context mContext;
	
	public MessageHandler(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
	
		String result = null;
		// 更新UI
		switch (msg.what) {
		case ConstUtil.MSG_FACE_DETECT:
			result = msg.getData().getString("message");
			Log.d(TAG, result);
			Toast.makeText(mContext, "上传成功", Toast.LENGTH_LONG).show();
			Log.d(TAG, "detect is finished");
			break;
		case ConstUtil.MSG_FACE_TRAIN:
			result = msg.getData().getString("message");
			Log.d(TAG, result);
			Toast.makeText(mContext, "训练完成", Toast.LENGTH_LONG).show();
			Log.d(TAG, "train is finished");
			break;
		case ConstUtil.MSG_FACE_IDENT:
			result = msg.getData().getString("message");
			Log.d(TAG, result);
			Toast.makeText(mContext, "签到成功", Toast.LENGTH_LONG).show();
			Log.d(TAG, "ident is finished");
			break;
		default:
			break;
		}
	}
}
