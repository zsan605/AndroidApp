package com.mrabel.face;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.mrabel.utils.ConstUtil;

/**
 * 封装Face++在线API
 * @author zzg
 *
 */
public class FaceManager {
	
	// 定义常量
	private static final String TAG = FaceManager.class.getSimpleName();
	
	// 定义成员变量
	private HttpRequests mHttpRequests;
	private JSONObject mResult;

	/**
	 * 构造函数
	 */
	public FaceManager(){
		mHttpRequests = new HttpRequests(ConstUtil.API_KEY, ConstUtil.API_SECRET, true, true);
		mResult = null;
	}

	/**
	 * 检测给定图片(Image)中的所有人脸(Face)的位置和相应的面部属性（通过url）
	 * @param url	图片的url
	 * @return		返回JSONObject对象
	 */
	public JSONObject detectFace(String url){
		JSONObject result = null;
		
		try {
			PostParameters params = new PostParameters();
			params.setUrl(url);
			result = mHttpRequests.detectionDetect(params);
			String status = getState(result);
			Log.d(TAG, status);
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 检测给定图片(Image)中的所有人脸(Face)的位置和相应的面部属性（通过image数据）
	 * @param data
	 * @return
	 */
	public JSONObject detectFace(byte[] data){
		JSONObject result = null;
		try {
			PostParameters params = new PostParameters();
			params.setImg(data);
			result = mHttpRequests.detectionDetect(params);
			String status = getState(result);
			Log.d(TAG, status);
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 创建一个Group,Group是Person的集合
	 * @param groupName	Group名
	 * @return
	 */
	public JSONObject createGroup(String groupName){
		JSONObject result = null;
		try {
			PostParameters params = new PostParameters();
			params.setGroupName(groupName);
			result = mHttpRequests.groupCreate(params);
			String status = getState(result);
			Log.d(TAG, status);
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}    
		return result;
	}

	/**
	 * 在一个Group中创建一个Person
	 * @param personName	person的名字
	 * @param groupName		group的名字
	 * @return
	 */
	public JSONObject createPerson(String personName, String groupName){
		JSONObject result = null;
		try {
			PostParameters params = new PostParameters();
			params.setGroupName(groupName);
			params.setPersonName(personName);
			result = mHttpRequests.personCreate(params);
			String status = getState(result);
			Log.d(TAG, status);
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 向指定person中添加一个人脸
	 * @param data
	 * @param personName
	 * @return
	 */
	public JSONObject addFace(byte[] data, String personName){
		JSONObject result = null;
		String faceId = null;
		result = detectFace(data);
		if (result == null){
			Log.d(TAG, "result is empty, detectFace error");
		}
		try {
			faceId = result.getJSONArray("face").getJSONObject(0).getString("face_id");
			Log.d(TAG, faceId);
			result = null;
			PostParameters params = new PostParameters();
			params.setFaceId(faceId);
			params.setPersonName(personName);
			result = mHttpRequests.personAddFace(params);
			if (result.getBoolean("success")){
				Log.d(TAG, "添加人脸成功");
			}else{
				Log.d(TAG, "添加人脸失败");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void trainIdenty(String groupName){
		JSONObject result = null;
		try {
			PostParameters params = new PostParameters();
			params.setGroupName(groupName);
			result = mHttpRequests.trainIdentify(params);
			String status = getState(result);
			Log.d(TAG, status);
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 给定人脸数据在给定群组中识别
	 * @param data
	 * @param groupName
	 * @return
	 */
	public JSONObject identFace(byte[] data, String groupName){
		JSONObject result = null;
		JSONObject identResult = null;
		
		try {
			PostParameters params = new PostParameters();
			params.setGroupName(groupName);
			params.setImg(data);
			identResult = mHttpRequests.recognitionIdentify(params);
			String status = getState(identResult);
			Log.d(TAG, "identify" + status);
			Log.d(TAG, identResult.getString("session_id"));
			identResult.getJSONArray("face").getJSONObject(0).getString("face_id");
			
			result = identResult.getJSONArray("face").getJSONObject(0);
			result = result.getJSONArray("candidate").getJSONObject(0);
					
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject getmResult() {
		return mResult;
	}
	
	/**
	 * 获得会话状态
	 * @param result
	 * @return
	 */
	public String getState(JSONObject result){
		String status = null;
		try {
			JSONObject res = mHttpRequests.getSessionSync(result.getString("session_id"));
			status = res.getString("status");
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "session error");
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "session error");
			e.printStackTrace();
		}
		return status;
	}
}
			
			