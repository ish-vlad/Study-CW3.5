package com.ishvlad.android_cw3_5.dialog;

import java.text.SimpleDateFormat;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.activity.ViewActivity;
import com.ishvlad.android_cw3_5.helper.LectureHelper;
import com.ishvlad.android_cw3_5.helper.StudentHelper;
import com.ishvlad.android_cw3_5.layer.LectureView;

public class ViewLectureDialog extends DialogFragment implements OnClickListener {
	private View mainView;
	private LectureView mLecture;
	private int mLectureId, mStudentId;
	
	public ViewLectureDialog() {}
	
	public ViewLectureDialog(int lectureId, int studentId) {
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
		
		mainView = inflater.inflate(R.layout.dialog_view_lecture, null);
		mainView.findViewById(R.id.dialog_lect_btn_change).setOnClickListener(this);
		mainView.findViewById(R.id.dialog_lect_btn_ok).setOnClickListener(this);
		mainView.findViewById(R.id.dialog_lect_btn_delete).setOnClickListener(this);
		((TextView)mainView.findViewById(R.id.dialog_view_lecture_name)).setText(mLecture.name);
		((TextView)mainView.findViewById(R.id.dialog_view_lecture_date)).setText(new SimpleDateFormat("dd.MM.yyyy").format(mLecture.date));
		
		
		
		((TextView)mainView.findViewById(R.id.dialog_view_lecture_comment)).setText(mLecture.comment);
		
	    return mainView;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.dialog_lect_btn_ok:
			dismiss();
            break;
		case R.id.dialog_lect_btn_change:
			new ChangeLectureDialog(mLectureId, mStudentId).show(getFragmentManager(), "openDialog");
			dismiss();
			break;
		case R.id.dialog_lect_btn_delete:
			new StudentHelper(getActivity()).deletePresence(mLectureId, mStudentId);
			((ViewActivity)getActivity()).refresh("Лекции");
			dismiss();
		}
	}
	

}
