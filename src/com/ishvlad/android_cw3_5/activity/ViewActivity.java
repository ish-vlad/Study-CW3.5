package com.ishvlad.android_cw3_5.activity;

import java.util.ArrayList;
import java.util.LinkedList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.adapter.DataAdapter;
import com.ishvlad.android_cw3_5.adapter.DataAdapter.OnItemClick;
import com.ishvlad.android_cw3_5.dialog.ChangeLectureDialog;
import com.ishvlad.android_cw3_5.helper.DBHelper;
import com.ishvlad.android_cw3_5.helper.DataHelper;
import com.ishvlad.android_cw3_5.helper.LectureHelper;
import com.ishvlad.android_cw3_5.layer.Base;
import com.ishvlad.android_cw3_5.layer.DBNames;

public class ViewActivity extends ActionBarActivity implements  OnClickListener {
	public static final String TAG = "com.ishvlad.androidCW3.5.ViewActivity";
	public static final String EXTRA_GROUP = TAG + "_group";
	public static final String EXTRA_OBJECT = TAG + "_object";
	public static final String EXTRA_INDEX = TAG + "_index";
	public static final String EXTRA_GLOBAL = TAG + "_global_index";
	public static final String EXTRA_TITLE_CLICK = TAG + "_title_click";
	public static final String EXTRA_SEMAFOR = TAG + "_student_semafor";
	
	private boolean mTitleClick = false;
	private AlertDialog titleDialog;
	private int mGroupId = -1;
	private int mObjectId = -1;
	
	private boolean studentSemafor = true;
	
	private int navigateIndex = 0;
	private int modeIndex = 0;
	
	private final ArrayList<String> modes = new ArrayList<String>(5);
	private LinkedList<Base> listItems = new LinkedList<Base>();
	private LinkedList<Base> navigate = new LinkedList<Base>();
	
	private DataHelper dataHelper;
	
	private DataAdapter adapter;
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(EXTRA_INDEX, navigateIndex);
		outState.putInt(EXTRA_GLOBAL, modeIndex);
		outState.putBoolean(EXTRA_TITLE_CLICK, mTitleClick);
		outState.putBoolean(EXTRA_SEMAFOR, studentSemafor);
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		
		if (savedInstanceState != null) {
			navigateIndex = savedInstanceState.getInt(EXTRA_INDEX);
			modeIndex = savedInstanceState.getInt(EXTRA_GLOBAL);
			mTitleClick = savedInstanceState.getBoolean(EXTRA_TITLE_CLICK);
			studentSemafor = savedInstanceState.getBoolean(EXTRA_SEMAFOR);
		} 
		
		mGroupId = getIntent().getExtras().getInt(EXTRA_GROUP);
		mObjectId = getIntent().getExtras().getInt(EXTRA_OBJECT);
		
		if (DBHelper.getInstance(this).sizeInObject(DBNames.TABLES.LECTURE.toString(), mObjectId) != 0) {
			modes.add("Лекции");
		}
		//TODO лабы, рк, дз, курсач
		
		if(modes.size() > 0) {
			startBurn(modes.get(modeIndex));
		}
	}
	
	private void startBurn(String mode) {
		navigate.clear();
		listItems.clear();
		
		
		if (mode.equals(modes.get(0))) {
			dataHelper = new LectureHelper(this);
		}
		
		if (studentSemafor) {
			navigate.addAll(dataHelper.getAllBase(mObjectId));
			listItems.addAll(dataHelper.getAllStudents(mGroupId));
			for(int i = 0; i < listItems.size(); i++) {
				listItems.set(i, dataHelper.upgradeStudent(listItems.get(i), navigate.get(navigateIndex).id));
			}
		} else {
			listItems.addAll(dataHelper.getAllBase(mObjectId));
			navigate.addAll(dataHelper.getAllStudents(mGroupId));
			for(int i = 0; i < listItems.size(); i++) {
				listItems.set(i, dataHelper.upgradeBase(listItems.get(i), navigate.get(navigateIndex).id));
			}
		}
		
		adapter = new DataAdapter(this, listItems);
		adapter.setClick(new OnItemClick() {
			@Override
			public void onBtnClick(int position) {
				if (studentSemafor) {
					Base student = listItems.get(position);
					if (student.dateFromId == 0) {
						dataHelper.insert(student, navigate.get(navigateIndex).id, mGroupId);
						adapter.setData(listItems);
					} else {
						show(modes.get(modeIndex), student, navigate.get(navigateIndex).id);
					}
				} else {
					Base what = listItems.get(position);
					if (what.dateFromId == 0) {
						dataHelper.insert(navigate.get(navigateIndex), what.id, mGroupId);
						listItems.set(position, dataHelper.upgradeBase(listItems.get(position), navigate.get(navigateIndex).id));
						adapter.setData(listItems);
					} else {
						show(modes.get(modeIndex), dataHelper.upgradeStudent(navigate.get(navigateIndex), listItems.get(position).id), navigate.get(navigateIndex).id);
					}
				}
			}
			
			@Override
			public void onBodyClick(int position) {
				studentSemafor = !studentSemafor;
				navigateIndex = position;
				startBurn(modes.get(modeIndex));
			}

			@Override
			public void onBtnLongClick() {
				if (studentSemafor) {
					boolean change = false;
					for(Base student:listItems) {
						if (student.dateFromId == 0) {
							dataHelper.insert(student, navigate.get(navigateIndex).id, mGroupId);
							change = true;
						}
					}
					
					if (change) {
						adapter.setData(listItems);
					}
				}
			}
		});
		((ListView)findViewById(R.id.view_body)).setAdapter(adapter);
		
		//TODO лабы, рк, дз, курсач
		
		
		
		((TextView)findViewById(R.id.view_header_title)).setText(navigate.get(navigateIndex).name);
		if (navigate.size() > 1) {
			findViewById(R.id.view_header_title).setOnClickListener(this);
			findViewById(R.id.view_header_btn_left).setOnClickListener(this);
			findViewById(R.id.view_header_btn_right).setOnClickListener(this);
		
			findViewById(R.id.view_header_title).setEnabled(true);
			findViewById(R.id.view_header_btn_left).setEnabled(true);
			findViewById(R.id.view_header_btn_right).setEnabled(true);
		}
		
		if (mTitleClick) {
			mTitleClick = false;
			showList();
		}
	}

	@Override
	public void onClick(View v) {
		findViewById(R.id.view_header_title).setEnabled(false);
		findViewById(R.id.view_header_btn_left).setEnabled(false);
		findViewById(R.id.view_header_btn_right).setEnabled(false);
		
		switch (v.getId()) {
		case R.id.view_header_btn_left:
			navigateIndex = (navigateIndex-1) < 0 ? navigate.size() - 1 : navigateIndex-1;
			break;
		case R.id.view_header_btn_right:
			navigateIndex = (navigateIndex + 1) % navigate.size();
			break;
		case R.id.view_header_title:
			showList();
			break;
		}
		
		startBurn(modes.get(modeIndex));
	}
	
	private void showList() {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		
		adb.setTitle(R.string.title_dialog_choose);
		
		ArrayAdapter<Base> adapter = new ArrayAdapter<Base>(
				this, 
				android.R.layout.simple_list_item_1, 
				navigate
		);

		adb.setAdapter(adapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mTitleClick = false;
				navigateIndex = which;
				startBurn(modes.get(modeIndex));
			}
		});
		
		titleDialog = adb.create(); 
		titleDialog.show();
	}
	
	@Override
	protected void onPause() {
		if (titleDialog != null && titleDialog.isShowing()) {
			mTitleClick = true;
		}
		
		super.onPause();
	}
	
	private void show(String mode, Base student, int whatId) {
		if (mode.equals(modes.get(0))) {
			new ChangeLectureDialog(student, whatId).show(getFragmentManager(), null);
		}
	}
	
	public void refresh() {
		if (studentSemafor) {
			for(int i = 0; i < listItems.size(); i++) {
				listItems.set(i, dataHelper.upgradeStudent(listItems.get(i), navigate.get(navigateIndex).id));
			}
		} else {
			for(int i = 0; i < listItems.size(); i++) {
				listItems.set(i, dataHelper.upgradeBase(listItems.get(i), navigate.get(navigateIndex).id));
			}
		}
		adapter.setData(listItems);
		adapter.notifyDataSetChanged();
	}
}
		
//		
//		
//		
//		
//		final LinkedList<StudentBase> students = classHelper.getStudents(mGroupId);
//		
//		ArrayAdapter<StudentBase> adapter = new ArrayAdapter<StudentBase>(
//				this, 
//				android.R.layout.simple_list_item_1, 
//				students
//			);
//		
//		ListView list = new ListView(this);
//		list.setAdapter(adapter);
//		list.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
//				mStudentBase = students.get(pos);
//				mTag = "Лекции";
//				startAnalize();
//			}
//		});
//		
//		menu.setMenu(list);
//		
//		getSupportActionBar().setDisplayShowCustomEnabled(true);
//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		
//		if (savedInstanceState != null) {
//			int id = savedInstanceState.getInt(EXTRA_STUDENT);
//			for(StudentBase item:students) {
//				if(item.studentId == id) {
//					mStudentBase = item;
//					break;
//				} 
//			}
//			startAnalize();
//		}
//	}
//	
//	
//	

//	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.layout.menu, menu);
//		return true;
//	}
//	
//	private void startAnalize() {
//		mStudent = null;
//		asyncDownloadStudent();
//		
//		
//		if (tabs == null) {
//			setContentView(R.layout.activity_view);
//			tabs = (TabHost)findViewById(R.id.view_tab_host);
//			tabs.setup();
//			
//			tabs.setOnTabChangedListener(new OnTabChangeListener() {
//				
//				@Override
//				public void onTabChanged(String tabId) {
//					if (mStudent != null) {
//						showFragment(tabId);
//					} 
//				}
//			});
//			
//			
//			lectFrame =  tabs.newTabSpec("Лекции");
//			lectFrame.setContent(android.R.id.tabcontent);
//			lectFrame.setIndicator("Лекции");
//			tabs.addTab(lectFrame);
//			
//		} 
//		
//	}
//	
//	private void showFragment(String tabId) {
//		Fragment fragment = null;
//		if (tabId.equals("Лекции")) {
//			fragment = new LectureViewFragment(mStudent);
//			
//			((TextView)findViewById(R.id.view_head_name)).setText(mStudent.name);
//			((TextView)findViewById(R.id.view_head_procent)).setText(Integer.toString(mStudent.procentLectures));
//			((TextView)findViewById(R.id.view_head_points)).setText(Integer.toString(mStudent.pointSum));
//		}
//		
//		((FrameLayout)findViewById(android.R.id.tabcontent)).removeAllViews(); 
//		
//		getFragmentManager()
//			.beginTransaction()
//			.add(android.R.id.tabcontent, fragment)
//			.commit();
//	}
//	
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) { // если нажата кнопка "Назад"
//            if(menu.isMenuShowing()){ // и если SlidingMenu открыто
//        		menu.toggle(true); // закрываем его
//                        return false;
//        	}
//        }
//             return super.onKeyDown(keyCode, event);
//	}
//
//	@Override
//	public void startChangeLecture(int lectureId) {
//		new ViewLectureDialog(lectureId, mStudent.studentId).show(getFragmentManager(), "start view");
//	}
//
//	public void refresh(String startTag) {
//		mTag = startTag;
//		startAnalize();
//	} 
//	

