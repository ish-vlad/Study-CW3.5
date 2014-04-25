package com.ishvlad.android_cw3_5.dialog;

import java.text.ParseException;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.activity.ViewActivity;
import com.ishvlad.android_cw3_5.helper.LectureHelper;
import com.ishvlad.android_cw3_5.layer.Base;

public class ChangeLectureDialog extends DialogFragment implements
		OnClickListener {
	private View mainView;
	private int mLectureId;
	private Base mStudent;
	
	public ChangeLectureDialog() {}
	
	public ChangeLectureDialog(Base student, int whatId) {
		mStudent = student;
		mLectureId = whatId;
	}
	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("student", mStudent);
		outState.putInt("Lecture", mLectureId);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mStudent = (Base) savedInstanceState.getSerializable("student");
			mLectureId = savedInstanceState.getInt("Lecture");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().setTitle(getResources().getText(R.string.title_dialog_lecture));
		
		mainView = inflater.inflate(R.layout.dialog_change_lecture, null);
		mainView.findViewById(R.id.dialog_lect_btn_commit).setOnClickListener(this);
		mainView.findViewById(R.id.dialog_lect_btn_cancel).setOnClickListener(this);
		mainView.findViewById(R.id.dialog_lect_btn_delete).setOnClickListener(this);
		((EditText)mainView.findViewById(R.id.dialog_change_lecture_date)).setText(mStudent.date);
		((EditText)mainView.findViewById(R.id.dialog_change_lecture_comment)).setText(mStudent.other);
		((EditText)mainView.findViewById(R.id.dialog_change_lecture_date)).setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() ==  MotionEvent.ACTION_DOWN) {
					DatePickerDialog dialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {
						
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							((EditText)mainView.findViewById(R.id.dialog_change_lecture_date)).setText(
										Integer.toString(dayOfMonth) + "." +
										Integer.toString(monthOfYear+1) + "." +
										Integer.toString(year)
								);
						}
					}, 	Calendar.getInstance().get(Calendar.YEAR), 
						Calendar.getInstance().get(Calendar.MONTH), 
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
					);
					
					dialog.show();
				}
				return false;
			}
		});
			
	    return mainView;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.dialog_lect_btn_commit:
			String date = mStudent.date;
			try {
				saveChanges();
				((ViewActivity)getActivity()).refresh();
				dismiss();
			} catch (ParseException e) {
				mStudent.date = date;
				Toast.makeText(getActivity(), "Invalid date (format: dd.mm.yyyy).", Toast.LENGTH_LONG).show();
			}
			
            break;
		case R.id.dialog_lect_btn_delete:
			new LectureHelper(getActivity()).delete(mStudent);
			((ViewActivity)getActivity()).refresh();
			dismiss();
			break;
		case R.id.dialog_lect_btn_cancel:
			dismiss();
			break;
		}
	}

	private void saveChanges() throws ParseException {
		Base student = new Base();
		
		student.id = mStudent.id;
		student.name = mStudent.name;
		student.dateFromId = mStudent.dateFromId;
		student.otherId = mStudent.otherId;
		
		student.other = ((EditText)mainView.findViewById(R.id.dialog_change_lecture_comment)).getText().toString();
		
		student.date = ((EditText)mainView.findViewById(R.id.dialog_change_lecture_date)).getText().toString();
		
		new LectureHelper(getActivity()).update(student, mStudent);
	}

}
