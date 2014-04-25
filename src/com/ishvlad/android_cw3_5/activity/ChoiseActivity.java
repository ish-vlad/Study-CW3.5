package com.ishvlad.android_cw3_5.activity;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.fragment.ChoiseGroupFragment;
import com.ishvlad.android_cw3_5.fragment.ChoiseGroupFragment.onGroupSelect;
import com.ishvlad.android_cw3_5.fragment.ChoiseObjectFragment;
import com.ishvlad.android_cw3_5.fragment.ChoiseObjectFragment.onObjectSelect;
import com.ishvlad.android_cw3_5.helper.ClassHelper;
import com.ishvlad.android_cw3_5.helper.ObjectHelper;
import com.ishvlad.android_cw3_5.layer.ObjectBase;

public class ChoiseActivity extends Activity implements onGroupSelect, onObjectSelect {
	public static final String TAG = "com.ishvlad.androidCW3.5.choiseActivity";
	public static final String EXTRA_GROUP = TAG + "_group";
	public static final String EXTRA_OBJECT = TAG + "_object";
	
	private int mGroupId = -1;
	private int mObjectId = -1;

	private ClassHelper clsHelp = new ClassHelper(this);
	private ObjectHelper objHelp = new ObjectHelper(this);
//	private LectureHelper lecHelp = new LectureHelper(this);
//	private StudentHelper stdHelp = new StudentHelper(this);
//	
	private ChoiseObjectFragment choiseObjectFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choise);
		
		if (savedInstanceState != null) {
			if (savedInstanceState.getInt(EXTRA_GROUP) != -1) {
				mGroupId = savedInstanceState.getInt(EXTRA_GROUP);
				groupSelect(mGroupId);
				
				if (savedInstanceState.getInt(EXTRA_OBJECT) != -1) {
					mObjectId = savedInstanceState.getInt(EXTRA_OBJECT);
					objectSelect(mObjectId);
				}
			}
		}
		
		ChoiseGroupFragment choiseGroupFragment = new ChoiseGroupFragment(clsHelp.getAllBase());
		
		getFragmentManager().beginTransaction()
			.add(R.id.fragment_choise_group, choiseGroupFragment)
			.commit();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(EXTRA_GROUP, mGroupId);
		outState.putInt(EXTRA_OBJECT, mObjectId);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void groupSelect(int groupId) {
		mGroupId = groupId;
		mObjectId = -1;
		objHelp = new ObjectHelper(getBaseContext());
		LinkedList<ObjectBase> mArrayObject = objHelp.getAllBaseFromGroupId(mGroupId);
		ChoiseObjectFragment choiseObjectFragmentNew = new ChoiseObjectFragment(mArrayObject);
		
		if(choiseObjectFragment != null) 
			getFragmentManager().beginTransaction()
				.hide(choiseObjectFragment)
				.commit();
		
		getFragmentManager().beginTransaction()
			.add(R.id.fragment_choise_object, choiseObjectFragmentNew)
			.commit();
		
		choiseObjectFragment = choiseObjectFragmentNew;
	}
	
	public void objectSelect(int objectId) {
		mObjectId = objectId;
		
		Bundle extras = new Bundle();
		extras.putInt(ViewActivity.EXTRA_GROUP, mGroupId);
		extras.putInt(ViewActivity.EXTRA_OBJECT, mObjectId);
		
		Intent intent = new Intent(this, ViewActivity.class);
		intent.putExtras(extras);
		startActivity(intent);
	}
	
//
//	private void startLect() {
//		AlertDialog.Builder adb = new AlertDialog.Builder(this);
//		
//		adb.setTitle(R.string.title_dialog_choose_lecture);
//		
//		
//		final LinkedList<LectureBase> unreadLect = lecHelp.getUnreadLectures(mGroupId, mObjectId);
//		if(unreadLect.size() == 0) {
//			Toast.makeText(this, getResources().getString(R.string.toast_no_lectures), Toast.LENGTH_LONG).show();
//			
//		} else {
//			ArrayAdapter<LectureBase> adapter = new ArrayAdapter<LectureBase>(
//					this, 
//					android.R.layout.simple_list_item_1, 
//					unreadLect
//				);
//
//				adb.setAdapter(adapter, new DialogInterface.OnClickListener() {
//				
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						//mLectureId = unreadLect.get(which).lectureId;
//						checkPeople();
//					}
//				});
//				
//				adb.create().show();
//		}
//	}
//	
//	protected void checkPeople() {
//		AlertDialog.Builder adb = new AlertDialog.Builder(this);
//		adb.setTitle(R.string.title_dialog_check_people);
//		
//		final LinkedList<StudentBase> students = clsHelp.getStudents(mGroupId);
//		final LinkedList<StudentBase> passedStudents = new LinkedList<StudentBase>();
//		
//		ArrayAdapter<StudentBase> adapter = new ArrayAdapter<StudentBase>(
//				this, 
//				android.R.layout.simple_list_item_multiple_choice, 
//				students
//			);
//		
//		final AlertDialog dialog = adb.setPositiveButton(getResources().getString(R.string.end), new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				saveChange(students, passedStudents);
//				startComment(passedStudents);
//			}
//
//			
//		})
//				.setAdapter(adapter, null)
//			    .setNegativeButton(getResources().getString(R.string.cancel), null)
//			    .create();
//
//		dialog.getListView().setItemsCanFocus(false);
//		dialog.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//		dialog.getListView().setOnItemClickListener(new OnItemClickListener() {
//		    @Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
//					long arg3) {
//		    	CheckedTextView textView = (CheckedTextView) arg1;
//		        
//		    	if(textView.isChecked()) {
//		    		passedStudents.add(students.get(pos));
//		        } else {
//		        	passedStudents.remove(students.get(pos));
//		        }
//			}
//		});
//
//		dialog.show();
//		
//	}
//	
//	private void startComment(final LinkedList<StudentBase> passedStudents) {
//		AlertDialog.Builder adb = new AlertDialog.Builder(this);
//		adb.setTitle(R.string.title_dialog_add_comment);
//		adb.setMessage(R.string.dialog_add_comment_text);
//		
//		adb.setPositiveButton(getResources().getString(R.string.add), new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				addComment(passedStudents);
//			}		
//		})
//				.setNegativeButton(getResources().getString(R.string.cancel), null)
//			    .create().show();
//
//	}
//	
//	protected void addComment(final LinkedList<StudentBase> passedStudents) {
//		AlertDialog.Builder adb = new AlertDialog.Builder(this);
//		adb.setTitle(R.string.title_dialog_add_comment);
//		
//		ArrayAdapter<StudentBase> adapter = new ArrayAdapter<StudentBase>(
//				this, 
//				android.R.layout.simple_list_item_1, 
//				passedStudents
//			);
//		
//		final AlertDialog dialog = adb.setPositiveButton(getResources().getString(R.string.end), new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		})
//				.setAdapter(adapter, null)
//			    .create();
//
//		dialog.getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
//		dialog.getListView().setItemsCanFocus(false);
//		dialog.getListView().setOnItemClickListener(new OnItemClickListener() {
//		    @Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
//					long arg3) {
//		    	
//		    	writeComment(passedStudents.get(pos).studentId);
//		    	
//			}
//		});
//
//		dialog.show();
//	}
//	
//	private void writeComment(final int studentId) {
//		final EditText view = new EditText(this);
//		view.setMinLines(3);
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                										LinearLayout.LayoutParams.MATCH_PARENT);
//		view.setLayoutParams(lp);
//		view.setPadding(16, 16, 16, 16);
//		//view.setText(lecHelp.getLecture(mLectureId, studentId).comment);
//		view.setEnabled(true);
//		view.setGravity(Gravity.CENTER);
//		
//		
//		AlertDialog.Builder adb = new AlertDialog.Builder(this);
//		adb.setTitle(R.string.title_dialog_add_comment);
//		adb.setView(view);
//		
//		adb.setPositiveButton(getResources().getString(R.string.add), new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				String text = view.getText().toString();
//				if (!text.equals("")) {
//					//stdHelp.setComment(studentId, mLectureId, text);
//				}
//			}		
//		})
//				.setNegativeButton(getResources().getString(R.string.cancel), null)
//			    .create().show();
//	}
//
//	private void saveChange(LinkedList<StudentBase> students, LinkedList<StudentBase> passedStudents) {
//		for(StudentBase student:students) {
//			if (passedStudents.contains(student)) {
//				//stdHelp.setPresence(student.studentId, mLectureId);
//			}
//		}
//		
//		//clsHelp.setLecture(mGroupId, mLectureId);
//	}
//
//	private void startLab() {
//		// TODO Auto-generated method stub
//	}
//
//	public void commit(String path, NewData data) {
//		//TODO заполнение нового
//		if (data == NewData.GROUP)
//			Toast.makeText(this, "New group: " + path, Toast.LENGTH_SHORT).show();
//		else 
//			Toast.makeText(this, "New object: " + path, Toast.LENGTH_SHORT).show();
//	}
}
