package com.mrabel.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class ImageUtil {

	private static final String TAG = ImageUtil.class.getSimpleName();

	/**
	 * 旋转Bitmap
	 * @param b
	 * @param rotateDegree
	 * @return
	 */
	public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree){
		Matrix matrix = new Matrix();
		matrix.postRotate((float)rotateDegree);
		Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
		return rotaBitmap;
	}
	
	/**
	 * 将bitmap转换成byte数据
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap bitmap){
		byte[] data = null;
		Bitmap bmSmall = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmSmall.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		data = stream.toByteArray();
		return data;
	}
}
