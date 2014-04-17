package com.ishvlad.android_cw3_5.activity;

import java.util.LinkedList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.dialog.ViewLectureDialog;
import com.ishvlad.android_cw3_5.fragment.LectureViewFragment;
import com.ishvlad.android_cw3_5.fragment.LectureViewFragment.onChange;
import com.ishvlad.android_cw3_5.helper.ClassHelper;
import com.ishvlad.android_cw3_5.helper.DBHelper;
import com.ishvlad.android_cw3_5.helper.LectureHelper;
import com.ishvlad.android_cw3_5.helper.StudentHelper;
import com.ishvlad.android_cw3_5.layer.LectureBase;
import com.ishvlad.android_cw3_5.layer.StudentBase;
import com.ishvlad.android_cw3_5.layer.StudentFull;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class ViewActivity extends ActionBarActivity implements onChange {
	public static final String TAG = "com.ishvlad.androidCW3.5.ViewActivity";
	public static final String EXTRA_GROUP = TAG + "_group";
	public static final String EXTRA_OBJECT = TAG + "_object";
	public static final String EXTRA_STUDENT = TAG + "_student";
	
	private String mTag = "Лекции";
	
	private int mGroupId = -1;
	private int mObjectId = -1;
	private StudentBase mStudentBase = null;
	
	private StudentFull mStudent; 
	
	private ClassHelper classHelper = new ClassHelper(this);
	private LectureHelper lectureHelper = new LectureHelper(this);
	private StudentHelper studentHelper = new StudentHelper(this);
	
	private SlidingMenu menu;
	private TabHost tabs;
	private TabHost.TabSpec lectFrame;
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (mStudentBase != null) {
			outState.putInt(EXTRA_STUDENT, mStudentBase.studentId);
		}
			super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGroupId = getIntent().getExtras().getInt(EXTRA_GROUP);
		mObjectId = getIntent().getExtras().getInt(EXTRA_OBJECT);
		
		//TODO Сделать вьюжку, подсказывающую, что нужно сдвинуть влево
		setContentView(android.R.layout.activity_list_item);
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeBehind(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowDrawable(R.drawable.slidingmenu_shadowgradient);
		menu.setShadowWidth(7);
		menu.setFadeDegree(0.0f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setBehindWidth(300);
		
		
		
		final LinkedList<StudentBase> students = classHelper.getStudents(mGroupId);
		
		ArrayAdapter<StudentBase> adapter = new ArrayAdapter<StudentBase>(
				this, 
				android.R.layout.simple_list_item_1, 
				students
			);
		
		ListView list = new ListView(this);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				mStudentBase = students.get(pos);
				mTag = "Лекции";
				startAnalize();
			}
		});
		
		menu.setMenu(list);
		
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (savedInstanceState != null) {
			int id = savedInstanceState.getInt(EXTRA_STUDENT);
			for(StudentBase item:students) {
				if(item.studentId == id) {
					mStudentBase = item;
					break;
				} 
			}
			startAnalize();
		}
	}
	
	
	private void asyncDownloadStudent() {
		final Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message message) {
	        	mStudent = (StudentFull)message.obj;
	        	showFragment(mTag);
	        }
	    };
	 
	    final Thread thread = new Thread() {
	        @Override
	        public void run() {
	            final Message message = handler.obtainMessage(1, studentHelper.getFull(mStudentBase, mObjectId));
		        handler.sendMessage(message);
	        }
	    };
	    
	    thread.setPriority(3);
	    thread.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.layout.menu, menu);
		return true;
	}
	
	private void startAnalize() {
		mStudent = null;
		asyncDownloadStudent();
		
		
		if (tabs == null) {
			setContentView(R.layout.activity_view);
			tabs = (TabHost)findViewById(R.id.view_tab_host);
			tabs.setup();
			
			tabs.setOnTabChangedListener(new OnTabChangeListener() {
				
				@Override
				public void onTabChanged(String tabId) {
					if (mStudent != null) {
						showFragment(tabId);
					} 
				}
			});
			
			
			lectFrame =  tabs.newTabSpec("Лекции");
			lectFrame.setContent(android.R.id.tabcontent);
			lectFrame.setIndicator("Лекции");
			tabs.addTab(lectFrame);
			
		} 
		
	}
	
	private void showFragment(String tabId) {
		Fragment fragment = null;
		if (tabId.equals("Лекции")) {
			fragment = new LectureViewFragment(mStudent);
			
			((TextView)findViewById(R.id.view_head_name)).setText(mStudent.name);
			((TextView)findViewById(R.id.view_head_procent)).setText(Integer.toString(mStudent.procentLectures));
			((TextView)findViewById(R.id.view_head_points)).setText(Integer.toString(mStudent.pointSum));
		}
		
		((FrameLayout)findViewById(android.R.id.tabcontent)).removeAllViews(); 
		
		getFragmentManager()
			.beginTransaction()
			.add(android.R.id.tabcontent, fragment)
			.commit();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // если нажата кнопка "Назад"
            if(menu.isMenuShowing()){ // и если SlidingMenu открыто
        		menu.toggle(true); // закрываем его
                        return false;
        	}
        }
             return super.onKeyDown(keyCode, event);
	}

	@Override
	public void startChangeLecture(int lectureId) {
		new ViewLectureDialog(lectureId, mStudent.studentId).show(getFragmentManager(), "start view");
	}

	public void refresh(String startTag) {
		mTag = startTag;
		startAnalize();
	} 
	
}
