package com.ishvlad.android_cw3_5.dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.activity.ViewActivity;
import com.ishvlad.android_cw3_5.helper.LectureHelper;
import com.ishvlad.android_cw3_5.helper.StudentHelper;
import com.ishvlad.android_cw3_5.layer.LectureView;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeLectureDialog extends DialogFragment implements
		OnClickListener {
	private View mainView;
	private LectureView mLecture;
	private int mLectureId, mStudentId;
	
	public ChangeLectureDialog() {}
	
	public ChangeLectureDialog(int lectureId, int studentId) {
		mStudentId = studentId;
		mLectureId = lectureId;
	}
	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt("student", mStudentId);
		outState.putInt("Lecture", mLectureId);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mStudentId = savedInstanceState.getInt("student");
			mLectureId = savedInstanceState.getInt("Lecture");
		}
		
		mLecture = new LectureHelper(getActivity()).getLecture(mLectureId, mStudentId);
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().setTitle(getResources().getText(R.string.title_dialog_lecture));
		
		mainView = inflater.inflate(R.layout.dialog_change_lecture, null);
		mainView.findViewById(R.id.dialog_lect_btn_commit).setOnClickListener(this);
		mainView.findViewById(R.id.dialog_lect_btn_cancel).setOnClickListener(this);
		((EditText)mainView.findViewById(R.id.dialog_change_lecture_name)).setText(mLecture.name);
		((EditText)mainView.findViewById(R.id.dialog_change_lecture_date)).setText(new SimpleDateFormat("dd.MM.yyyy").format(mLecture.date));
		((EditText)mainView.findViewById(R.id.dialog_change_lecture_comment)).setText(mLecture.comment);
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
			try {
				saveChanges();
				
				((ViewActivity)getActivity()).refresh("Лекции");
				
				dismiss();
			} catch (ParseException e) {
				Toast.makeText(getActivity(), "Invalid date (format: dd.mm.yyyy).", Toast.LENGTH_LONG).show();
			}
            break;
		case R.id.dialog_lect_btn_cancel:
			dismiss();
			break;
		}
	}

	private void saveChanges() throws ParseException {
		
		
		String comment = ((EditText)mainView.findViewById(R.id.dialog_change_lecture_comment)).getText().toString();
		mLecture.comment = comment;
		
		
		String name = ((EditText)mainView.findViewById(R.id.dialog_change_lecture_name)).getText().toString();
		if (!name.equals("") && !name.equals(mLecture.name)) {
			
			mLecture.name = name;
		}
		
		
		long date = new SimpleDateFormat("dd.MM.yyyy").parse(
				((EditText)mainView.findViewById(R.id.dialog_change_lecture_date)).getText().toString()
															).getTime();
		if (date != mLecture.date) {
			
			mLecture.date = date;
		}
		
		new LectureHelper(getActivity()).update(mLecture, mStudentId);
		
	}

}
