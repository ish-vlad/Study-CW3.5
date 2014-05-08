package com.ishvlad.android_cw3_5.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.adapter.DataAdapter;
import com.ishvlad.android_cw3_5.adapter.DataAdapter.OnItemClick;
import com.ishvlad.android_cw3_5.adapter.LabAdapter;
import com.ishvlad.android_cw3_5.adapter.LectureAdapter;
import com.ishvlad.android_cw3_5.dialog.ChangeLectureDialog;
import com.ishvlad.android_cw3_5.helper.DBHelper;
import com.ishvlad.android_cw3_5.helper.DataHelper;
import com.ishvlad.android_cw3_5.helper.LabHelper;
import com.ishvlad.android_cw3_5.helper.LectureHelper;
import com.ishvlad.android_cw3_5.helper.VariantHelper;
import com.ishvlad.android_cw3_5.layer.Base;
import com.ishvlad.android_cw3_5.layer.DBNames;

public class ViewActivity extends ActionBarActivity implements  OnClickListener {
	public static final String TAG = "com.ishvlad.androidCW3.5.ViewActivity";
	public static final String EXTRA_GROUP = TAG + "_group";
	public static final String EXTRA_OBJECT = TAG + "_object";
	public static final String EXTRA_INDEX = TAG + "_index";
	public static final String EXTRA_GLOBAL = TAG + "_global_index";
	public static final String EXTRA_TITLE_CLICK = TAG + "_title_click";
	public static final String EXTRA_MARK_CLICK = TAG + "_mark_click";
	public static final String EXTRA_START_CLICK = TAG + "_start_click";
	public static final String EXTRA_POSITION = TAG + "_position";
	public static final String EXTRA_SEMAFOR = TAG + "_student_semafor";
	public static final String[] STATES = {
		"Лекции",
		"Лабы",
		"РК",
		"ДЗ",
		"Курсовые"
	};
	
	private boolean mTitleClick = false, mEndClick = false, mStartClick = false;
	private AlertDialog titleDialog, markDialog;
	
	
	private int mGroupId = -1;
	private int mObjectId = -1;
	
	private boolean studentSemafor = true;
	
	private int navigateIndex = 0;
	private int modeIndex = 0;
	private int mPosition = -1;
	
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
		outState.putBoolean(EXTRA_MARK_CLICK, mEndClick);
		outState.putBoolean(EXTRA_START_CLICK, mStartClick);
		outState.putBoolean(EXTRA_SEMAFOR, studentSemafor);
		outState.putInt(EXTRA_POSITION, mPosition);
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.clear();
		if (modes.size() > 0) {
			for(String mode:modes) {
				MenuItem item = menu.add(mode);
				item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
			}
			return super.onCreateOptionsMenu(menu);
		} else {
			return false;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		for (String mode:modes) {
			if (item.getTitle().toString().equals(mode)) {
				modeIndex = modes.indexOf(mode);
				if (studentSemafor) {
					navigateIndex = 0;
					mPosition = -1;
				}
				startBurn(mode);
				return true;
			}
		}
		return true;
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
			mEndClick = savedInstanceState.getBoolean(EXTRA_MARK_CLICK);
			mPosition = savedInstanceState.getInt(EXTRA_POSITION);
			mStartClick = savedInstanceState.getBoolean(EXTRA_START_CLICK);
		} 
		
		mGroupId = getIntent().getExtras().getInt(EXTRA_GROUP);
		mObjectId = getIntent().getExtras().getInt(EXTRA_OBJECT);
		
		if (DBHelper.getInstance(this).sizeInObject(DBNames.TABLES.LECTURE.toString(), mObjectId) != 0) {
			modes.add(STATES[0]);
		} 
		if (DBHelper.getInstance(this).sizeInObject(DBNames.TABLES.LAB.toString(), mObjectId) != 0) {
			modes.add(STATES[1]);
		} 
		/*if (DBHelper.getInstance(this).sizeInObject(DBNames.TABLES.LECTURE.toString(), mObjectId) != 0) {
			modes.add(STATES[2]);
		} 
		if (DBHelper.getInstance(this).sizeInObject(DBNames.TABLES.LECTURE.toString(), mObjectId) != 0) {
			modes.add(STATES[3]);
		} 
		if (DBHelper.getInstance(this).sizeInObject(DBNames.TABLES.LECTURE.toString(), mObjectId) != 0) {
			modes.add(STATES[4]);
		} */
		
		
		
		if(modes.size() > 0) {
			startBurn(modes.get(modeIndex));
		}
	}
	
	private void startBurn(String mode) {
		navigate.clear();
		listItems.clear();
		
		
		if (mode.equals(STATES[0])) {
			dataHelper = new LectureHelper(this);
		} else if (mode.equals(STATES[1])) {
			dataHelper = new LabHelper(this);
		} /*else if (mode.equals(STATES[2])) {
			dataHelper = new LabHelper(this);
		} else if (mode.equals(STATES[3])) {
			dataHelper = new LabHelper(this);
		} else if (mode.equals(STATES[4])) {
			dataHelper = new LabHelper(this);
		}*/
		
		
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
		
		
		if (mode.equals(STATES[0])) {
			adapter = new LectureAdapter(this, listItems);
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
			
		} else if (mode.equals(STATES[1])) {
			adapter = new LabAdapter(this, listItems);
			adapter.setClick(new OnItemClick() {
				
				@Override
				public void onBtnLongClick() {
					if (studentSemafor && ((LabHelper)dataHelper).canStartAll(navigate.get(navigateIndex).id) ) {
						boolean change = false;
						for(Base student:listItems) {
							if (student.dateFromId == 0) {
								dataHelper.insert(student, navigate.get(navigateIndex).id, mGroupId);
								change = true;
							}
							
							if (change) {
								adapter.setData(listItems);
							}
						}
					}
				}
				
				@Override
				public void onBtnClick(int position) {
					final Base student;
					final Base lab;
					if (studentSemafor) {
						student = listItems.get(position);
						lab = navigate.get(navigateIndex);
					} else {
						student = dataHelper.upgradeStudent(navigate.get(navigateIndex), listItems.get(position).id);
						lab = listItems.get(position);
					}
						
					mPosition = position;
					if (student.dateFromId != 0) {
						show(STATES[1], student, lab.id);
					} else {
						if ( ((LabHelper)dataHelper).canStartAll(lab.id) ) {
							dataHelper.insert(student, lab.id, mGroupId);
							if (!studentSemafor) {
								lab.date = student.date;
								lab.dateFromId = student.dateFromId;
								lab.other = student.other;
								lab.otherId = student.otherId;
							}
							adapter.setData(listItems);
						} else {
							mStartClick = true;
							showVariant();
						}
					}
				}
			
				
				@Override
				public void onBodyClick(int position) {
					studentSemafor = !studentSemafor;
					navigateIndex = position;
					startBurn(modes.get(modeIndex));
				}
			});
		}
		//TODO рк, дз, курсач
		
		
		((ListView)findViewById(R.id.view_body)).setAdapter(adapter);
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
		} else if (mStartClick){
			mStartClick = false;
			showVariant();
		} else if (mEndClick) {
			mEndClick = false;
			if (mPosition >= 0) {
				if (studentSemafor) {
					show(modes.get(modeIndex), listItems.get(mPosition), navigate.get(navigateIndex).id);
				} else {
					show(
							modes.get(modeIndex),
							dataHelper.upgradeStudent(navigate.get(navigateIndex), listItems.get(mPosition).id),
							listItems.get(mPosition).id
					);
				}
			}
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
		
		if (studentSemafor) {
			adb.setTitle(R.string.title_dialog_choose_with_semafor);
		} else {
			adb.setTitle(R.string.title_dialog_choose);
		}
		
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
		if (markDialog != null && markDialog.isShowing()) {
			mEndClick = true;
		}
		
		super.onPause();
	}
	
	private void show(String mode, final Base student, final int whatId) {
		if (mode.equals(STATES[0])) {
			new ChangeLectureDialog(student, whatId).show(getFragmentManager(), null);
		} else if (mode.equals(STATES[1])) {
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			
			adb.setTitle(R.string.title_dialog_mark);
			
			final LinkedList<String> marks = ((LabHelper)dataHelper).getMarks(whatId);
			
			ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
					this, 
					android.R.layout.simple_list_item_1, 
					marks
			);

			adb.setAdapter(mAdapter, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mEndClick = false;
					
					Base studentNew = student.copy();
					studentNew.mark = Integer.parseInt(marks.get(which));
					try {
						dataHelper.update(studentNew, student);
					} catch (ParseException e) {}
					listItems.get(mPosition).mark = studentNew.mark;
					mPosition = -1;
					
					adapter.setData(listItems);
					adapter.notifyDataSetChanged();
				}
			});
			
			
			
			markDialog = adb.create(); 
			markDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dataHelper.delete(dataHelper.upgradeStudent(student, whatId));
					listItems.get(mPosition).dateFromId = 0;
					listItems.get(mPosition).date = "";
					listItems.get(mPosition).other = "";
					listItems.get(mPosition).otherId = 0;
					listItems.get(mPosition).mark = -1;
					
					adapter.setData(listItems);
					adapter.notifyDataSetChanged();
					
					mEndClick = false;
					mPosition = -1;
					
					dialog.dismiss();
				}
			});
			
			markDialog.show();
		}
	}
	
	public void showVariant(){
		VariantHelper vh = new VariantHelper(this, modeIndex);
		
		final Base what;
		final Base student;
		if (studentSemafor) {
			what = navigate.get(navigateIndex);
			student = listItems.get(mPosition);
		} else {
			student = navigate.get(navigateIndex);
			what = listItems.get(mPosition);
		}
		
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		
		final LinkedList<HashMap<String, String>> data = vh.getData(what.id, mGroupId);
		
		adb.setTitle(R.string.title_dialog_variant);
		adb.setAdapter(new SimpleAdapter(
				this, 
				data, 
				vh.getResourse(), 
				vh.getFrom(), 
				vh.getTo()
		), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				student.other = data.get(which).get(VariantHelper.NUMBER);
				student.otherId = Integer.parseInt(data.get(which).get(VariantHelper.ID));
				dataHelper.insert(student, what.id, mGroupId);
				if (!studentSemafor) {
					listItems.get(mPosition).other = data.get(which).get(VariantHelper.NUMBER);
					listItems.get(mPosition).otherId = Integer.parseInt(data.get(which).get(VariantHelper.ID));
					listItems.get(mPosition).dateFromId = student.dateFromId;
					listItems.get(mPosition).date = student.date;
				}
				
				mPosition = -1;
				mStartClick = false;
				dialog.dismiss();
				adapter.setData(listItems);
				adapter.notifyDataSetChanged();
			}
		});
		
		AlertDialog dialog = adb.create(); 
		dialog.show();
		
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