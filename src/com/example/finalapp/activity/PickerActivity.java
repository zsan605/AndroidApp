package com.example.finalapp.activity;

import com.example.finalapp.R;
import com.example.finalapp.R.id;
import com.example.finalapp.R.layout;
import com.example.finalapp.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class PickerActivity extends Activity {

	//定义常量
	final private static String TAG = "PickerActivity";	
	final private int PICTURE_CHOOSE = 1;
	
	//定义变量
	private Bitmap image = null;
	private TextView stateText = null;
	private ImageView imageView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.picker_layout);
		
		stateText = (TextView)findViewById(R.id.stateText);
		imageView = (ImageView)findViewById(R.id.imageView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.picker_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case R.id.fromCamera:
			
			break;

		case R.id.fromPictures:
			//启动其它系统的活动(启动图库)
			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
			photoPickerIntent.setType("image/*");
	        startActivityForResult(photoPickerIntent, PICTURE_CHOOSE);
			break;
		case R.id.detect:
			
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		//处理返回的照片
	    if (requestCode == PICTURE_CHOOSE) {
	    	if (data != null) {
	    		//通过内容提供者访问外部数据
	    		Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
	    		
	    		if (cursor == null){			
	    			return;
	    		}
	    			
	    		cursor.moveToFirst();
	    		int idx = cursor.getColumnIndex(ImageColumns.DATA);
	    		String fileSrc = cursor.getString(idx); 
	 
	    		//获取图片的 长宽
	    		Options options = new Options();
	    		options.inJustDecodeBounds = true;
	    		image = BitmapFactory.decodeFile(fileSrc, options);

    			//设置图片加载比例
    			options.inSampleSize = Math.max(1, 
    						(int)Math.ceil(Math.max((double)options.outWidth / 1024f, 
	    					(double)options.outHeight / 1024f)));
	    		options.inJustDecodeBounds = false;
	    		image = BitmapFactory.decodeFile(fileSrc, options);
	    		stateText.setText("Clik Detect. ==>");
	    			
	    		imageView.setImageBitmap(image);
	    	}
	    	else {
	    		Log.d(TAG, "idButSelPic Photopicker canceled");
	    	}
	    }
	}
}
