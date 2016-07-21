package com.mrabel.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.androidapp.R;
import com.mrabel.utils.ConstUtil;

public class MainActivity extends Activity {
	
	private static final String TAG = MainActivity.class.getSimpleName();
	private ArrayList<HashMap<String, Object>> lstImageItem=new ArrayList<HashMap<String,Object>>();
	private Boolean isIdent;
	private Boolean isDisp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GridView gridview = (GridView) findViewById(R.id.gridview);
		
		initUI();

		SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem,
				R.layout.item,

				new String[]{"ItemImage", "ItemText"},
				new int[]{R.id.ItemImage,R.id.ItemText});
		
	     gridview.setAdapter(saImageItems);  
	     gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			    HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);  
			   // setTitle((String)item.get("ItemText"));
			    switch (arg2) {
				case 0:
					Intent addCourseIntent = new Intent(MainActivity.this,AddCourseActivity.class);
					startActivity(addCourseIntent);break;
				case 1:
					isIdent = false;
					Intent captureIntent = new Intent(MainActivity.this,CaptureActivity.class);
				    captureIntent.putExtra("data", isIdent);
					startActivity(captureIntent);break;
				case 2:
					//Intent intent2 = new Intent(MainActivity.this,CurrentInf.class);
					//startActivity(intent2);break;
					break;
				case 3:
					Intent browseClass = new Intent(MainActivity.this, BrowseClassActivity.class);
					startActivityForResult(browseClass, ConstUtil.CAPTURE_BROWSECLASS);
					
					break;
				case 4:
					//Intent intent4 = new Intent(MainActivity.this,ExportData.class);
					//startActivity(intent4);break;
					break;
				case 5:
					//Intent intent5 = new Intent(MainActivity.this,ImportData.class);
					//startActivity(intent5);break;
					break;
				case 6:
					Intent trainIntent = new Intent(MainActivity.this, TrainActivity.class);
				    startActivity(trainIntent);
				    break;
				case 7:
					isIdent = true;
					Intent identIntent = new Intent(MainActivity.this, CaptureActivity.class);
					identIntent.putExtra("data", isIdent);
					startActivity(identIntent);
					break;
				case 8:
					isDisp = true;
					Intent dispClassIntent = new Intent(MainActivity.this, BrowseClassActivity.class);
					dispClassIntent.putExtra("data", isDisp);
					startActivity(dispClassIntent);
					break;
				default:
					Log.v("intent", "no intent");
					break;
				}
			
			}
		});
	     
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case ConstUtil.CAPTURE_BROWSECLASS:
			if (resultCode ==  ConstUtil.RESULT_BROWSECLASS){
				String className = data.getStringExtra("class_name");
				Intent intent = new Intent(MainActivity.this, HistoryInfActivity.class);
				intent.putExtra("class_name", className);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}


	private void initUI(){
		HashMap<String, Object> map0=new HashMap<String,Object>();
		map0.put("ItemImage", R.drawable.ic_tianjia);//add pic id
		map0.put("ItemText",this.getString(R.string.add_course));
		lstImageItem.add(map0);
		
		HashMap<String, Object> map1=new HashMap<String,Object>();
		map1.put("ItemImage", R.drawable.add_face);//add pic id
		map1.put("ItemText",this.getString(R.string.add_face));
		lstImageItem.add(map1);

		HashMap<String, Object> map2=new HashMap<String,Object>();
		map2.put("ItemImage", R.drawable.ic_dangqian);//add pic id
		map2.put("ItemText",this.getString(R.string.current_inf));
		lstImageItem.add(map2);
		
		HashMap<String, Object> map3=new HashMap<String,Object>();
		map3.put("ItemImage", R.drawable.ic_lishi);//add pic id
		map3.put("ItemText",this.getString(R.string.history_inf));
		lstImageItem.add(map3);

		HashMap<String, Object> map4=new HashMap<String,Object>();
		map4.put("ItemImage", R.drawable.ic_daochu);//add pic id
		map4.put("ItemText",this.getString(R.string.export_data));
		lstImageItem.add(map4);

		HashMap<String, Object> map5=new HashMap<String,Object>();
		map5.put("ItemImage", R.drawable.ic_daoru);// 
		map5.put("ItemText",this.getString(R.string.import_data));
		lstImageItem.add(map5);
		
		HashMap<String, Object> map6=new HashMap<String,Object>();
		map6.put("ItemImage", R.drawable.train_image);// 
		map6.put("ItemText",this.getString(R.string.train));
		lstImageItem.add(map6);
		
		HashMap<String, Object> map7=new HashMap<String,Object>();
		map7.put("ItemImage", R.drawable.ic_facedetection);// 
		map7.put("ItemText",this.getString(R.string.start_sign));
		lstImageItem.add(map7);
		
		HashMap<String, Object> map8=new HashMap<String,Object>();
		map8.put("ItemImage", R.drawable.see_student);// 
		map8.put("ItemText",this.getString(R.string.browse_class));
		lstImageItem.add(map8);
		
		
	}
	/*
	 @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	 */
	
}
