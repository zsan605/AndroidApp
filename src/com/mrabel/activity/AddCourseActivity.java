package com.mrabel.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.auth.NTCredentials;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidapp.R;
import com.mrabel.dialog.CallbackBundle;
import com.mrabel.dialog.OpenFileDialog;
import com.mrabel.helper.DatabaseManager;
import com.mrabel.utils.CSVFileUtil;

/**
 * 添加班级
 * @author zzg
 *
 */
public class AddCourseActivity extends Activity implements OnClickListener {
	
	private static final String TAG = AddCourseActivity.class.getSimpleName();
	private static  int openfileDialogId = 0;
	
	private Button importStudentBtn = null;
	private Button addClassBtn = null;
	private EditText classNameEdit = null;
	private EditText courseNameEdit = null;
	private EditText courseIdEdit = null;
	private EditText timesEdit = null;
	
	private String mfilePath = null;
	private String mfileName = null;
	private String mClassName = null;
	private String mCourseName = null;
	private String mCourseId = null;
	private int mTimes = 0;
	private DatabaseManager mDbManager;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_course);
		
		initViewParams();
		initUI();
		initEvents();
		
	}
	
	/**
	 * 初始化控件
	 */
	private void initUI(){
		importStudentBtn = (Button)findViewById(R.id.importStu);
		addClassBtn = (Button)findViewById(R.id.addCourse);
		classNameEdit = (EditText)findViewById(R.id.className);
		courseNameEdit = (EditText)findViewById(R.id.courseName);
		courseIdEdit = (EditText)findViewById(R.id.courseId);
		timesEdit = (EditText)findViewById(R.id.times);
	}

	/**
	 * 初始化点击事件
	 */
	private void initEvents(){
		importStudentBtn.setOnClickListener(this);
		addClassBtn.setOnClickListener(this);
	}
	
	/**
	 * 初始化布局控件
	 */
	private void initViewParams(){
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setLogo(R.drawable.ic_tianjia);
		actionBar.setDisplayUseLogoEnabled(true);

		//view control
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("添加班级信息");
		actionBar.setSubtitle("请添加班级信息");
	}
	
	@Override  
    protected Dialog onCreateDialog(int id) {  
        if(id==openfileDialogId){  
            Map<String, Integer> images = new HashMap<String, Integer>();  
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹  
            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);   // 根目录图标  
            images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);    //返回上一层的图标  
            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);   //文件夹图标  
            images.put("wav", R.drawable.filedialog_wavfile);//wav文件图标  
            images.put("txt", R.drawable.filedialog_txtfile);
            images.put("csv", R.drawable.filedialog_wavfile);
            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);  
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {  
                @Override  
                public void callback(Bundle bundle) {  
                	mfilePath = bundle.getString("path");  
            		mfileName = bundle.getString("name");
                }  
            },   
            ".txt;,.wav;,.csv;",  
            images);  
            return dialog;  
        }  
        return null;  
    }  
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.importStu:
			mDbManager = DatabaseManager.getInstant(AddCourseActivity.this, "SignIn.db", null, 1);
    		mDbManager.openDatabase();
    		showDialog(openfileDialogId);
			break;
		case R.id.addCourse:
			
			String[] items = null;
			CSVFileUtil csvFileUtil = null;
        	String contentString = null;
        	
        	mCourseId = courseIdEdit.getText().toString();
        	mCourseName = courseNameEdit.getText().toString();
        	mClassName = classNameEdit.getText().toString();
        	mTimes = Integer.parseInt(timesEdit.getText().toString());
        	
        	if (mCourseId.isEmpty() || mCourseName.isEmpty() 
        			|| mClassName.isEmpty() || mTimes == 0){
        		Toast.makeText(AddCourseActivity.this, "请完善信息", Toast.LENGTH_LONG).show();
        		return ;
        	}
        	
        	// 插入课程
        	mDbManager.insertCourse(mCourseId, mCourseName, mTimes);
        	
        	// 插入班级默认签到次数为零
        	mDbManager.insertClass(mClassName, mCourseId, 0, 0);
        	
        	// 获得班级号
        	String classNum = mDbManager.getClassNum(mClassName);
        	int count = 0;
        	try {
				csvFileUtil = new CSVFileUtil(mfilePath);
				while((contentString = csvFileUtil.readLine()) != null){
					items = csvFileUtil.fromCSVLine(contentString, 4);
					mDbManager.insertStudent(items[0], items[1], items[2], items[3], items[4]);
					mDbManager.insertClassesToStudent(items[0], classNum);
					count++;
				}
				// 更新班级人数
				mDbManager.updateClassesCount(count, classNum);
				Toast.makeText(AddCourseActivity.this, "创建班级成功", Toast.LENGTH_LONG).show();
				classNameEdit.setText("");
        		courseIdEdit.setText("");
        		courseNameEdit.setText("");
        		timesEdit.setText("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

}
