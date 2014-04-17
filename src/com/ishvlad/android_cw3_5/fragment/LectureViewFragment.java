package com.ishvlad.android_cw3_5.fragment;

import java.util.LinkedList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ishvlad.android_cw3_5.LectureViewAdapter;
import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.layer.LectureView;
import com.ishvlad.android_cw3_5.layer.StudentFull;

public class LectureViewFragment extends Fragment {

	
	private LinkedList<LectureView> mData = null;
	
	public interface onChange {
		public void startChangeLecture(int lectureId);
	}
	
	private ListView mView;
	private onChange mOnChange;
	public LectureViewFragment() {}
	
	@Override
	public void onAttach(Activity activity) {
		mOnChange = (onChange)activity;
		super.onAttach(activity);
	}
	
	public LectureViewFragment(StudentFull student) {
		mData = student.lectures;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_lecture_view, null);
		
		mView = (ListView)v.findViewById(R.id.list_lecture_view);
		if (mData == null || mData.size() == 0) {
			return inflater.inflate(R.layout.empty_view, null);
		}
		
		LectureViewAdapter adapter = new LectureViewAdapter(getActivity(), mData);
		mView.setAdapter(adapter);
		mView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				mOnChange.startChangeLecture(mData.get(arg2).lectureId);
			}
		});
		return (View)mView.getParent();
	}
	
}
